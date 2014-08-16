package com.sds.metac.input.writer.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.log4j.Logger;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.input.writer.InputWriter;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.AbstractVO;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.IndexDataVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputIndexedFileWriter implements InputWriter {

	public static final String INDEX_SEP = "##";
	public static final String TAG_INDEX = "index_";
	public static final String TAG_STN = "stn_";
	public static final String TAG_GRP = "grp_";
	
	Logger logger = Logger.getLogger(InputIndexedFileWriter.class);
	
	private int writeGroupCnt = 0;
	private int writeStandardCnt = 0;

	@Override
	public void write(GroupVO groupVO) {
		File file = getFile(groupVO.getName(), TAG_GRP);
		writeVOtoFile(groupVO, file);
		
		writeGroupCnt++;
	}

	@Override
	public void write(StandardVO standardVO) {
		File file = getFile(standardVO.getName(), TAG_STN);		
		writeVOtoFile(standardVO, file);
		
		writeStandardCnt++;
	}
	
	@Override
	public void postProcess() {
		logger.debug("인덱싱 작업을 시작합니다.");
		
		createIndexFiles();
		
		logger.debug("인덱싱 작업을 종료합니다.");
	}

	private void createIndexFiles() {
		ConfigManager configManager = ConfigManager.INSTANCE;
		UserSettingVO userSetting = configManager.getUserSetting();
		
		String ext = userSetting.getTempFileExt(); 
		
		FileManager fileManager = FileManager.INSTANCE;
		File folder = fileManager.loadFolder(userSetting.getTempFileFolder());
		
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			
			if (!file.getName().endsWith(ext)) {
				continue;
			}
			
			if (!file.getName().startsWith(TAG_STN)) {
				continue;
			}
			
			createIndexFile(file);
		}
	}

	private void createIndexFile(File file) {
		logger.debug(file.getName() + " 파일 인덱싱 작업중입니다.");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			int totalCnt = writeStandardCnt;
			if (file.getName().startsWith(TAG_GRP)) {
				totalCnt = writeGroupCnt;
			}
			
			String line = null;
			int lineNumber = 0;
			while ((line=br.readLine()) != null) {
				++lineNumber;
				
				String key = getFirstJsonKey(line);
				if (key == null) {
					br.close();
					throw new MetaCException("파일이 잘못되었습니다. - 파일명 : [" + file.getName() + "], 라인["+ lineNumber + "] : [" + line + "]");
				}
								
								
				writeIndexFile(file.getName(), key, lineNumber, totalCnt);
			}
			
			br.close();
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}

	private void writeIndexFile(String fileName, String key, int lineNumber, int totalCnt) {		
		int hash = createHash(key);
		
		ConfigManager configManager = ConfigManager.INSTANCE;
		UserSettingVO userSetting = configManager.getUserSetting();
		
		String folder = userSetting.getTempFileFolder();
		String indexFileName = createIndexFileName(fileName);
		
		FileManager fileManager = FileManager.INSTANCE;
				
		File file = fileManager.loadFile(folder, indexFileName);
		File newFile = fileManager.loadFile(folder, indexFileName+"_temp");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
			String readLine = null;
			int lineCounter = 0;
			
			while (true) {
				readLine = br.readLine();
				++lineCounter;
				
				if (readLine == null || lineCounter == hash) {
					break;
				}
				
				bw.write(readLine);
				bw.newLine();
			}
			if (lineCounter < hash) {
				while (lineCounter++ < hash) {
					bw.newLine();
				}
			}
			
			if (readLine == null) {
				readLine = StringUtil.EMPTY;
			}
			
			IndexDataVO vo = new IndexDataVO();
			vo.setHashCode(StringUtil.toString(key.hashCode()));
			vo.setIndex(lineNumber);
			
			readLine += INDEX_SEP + vo.toJson();
			bw.write(readLine);
			bw.newLine();
			
			while ((readLine = br.readLine()) != null) {
				bw.write(readLine);
				bw.newLine();
			}
			
			br.close();
			bw.close();
			
			file.delete();
			newFile.renameTo(file);
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}

	private int createHash(String key) {
		int value = 256;
		int hash = value;
		
		for (int i=0 ; i<key.length() ; i++) {
			hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		
		return Math.abs(hash%value)+1;
	}

	private String getFirstJsonKey(String line) {
		String ret = null;
		int first = line.indexOf(":");
		int last = line.indexOf("\"", first+2);
		
		ret = line.substring(first+2, last);
		
		return ret;
	}

	private void writeVOtoFile(AbstractVO vo, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			
			String json = vo.toJson() + "\n";
			fos.write(json.getBytes());
			
			fos.close();
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}

	private File getFile(String name, String tag) {
		FileManager fileManager = FileManager.INSTANCE;
		ConfigManager configManager = ConfigManager.INSTANCE;
		
		UserSettingVO userSetting = configManager.getUserSetting();
		String folder = userSetting.getTempFileFolder();
		String ext = userSetting.getTempFileExt();
		
		String fileName = createFileName(name, tag) + FileManager.DOT + ext;
		
		File file = fileManager.loadFile(folder, fileName);
		
		return file;
	}

	private String createFileName(String name, String tag) {
		return tag + name.substring(0, 1).hashCode();
	}
	
	public String createIndexFileName(String fileName) {
		return TAG_INDEX + fileName;
	}
}
