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
import com.sds.metac.util.IndexedFileUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.AbstractVO;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.IndexDataVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputIndexedFileWriter implements InputWriter {
	
	
	Logger logger = Logger.getLogger(InputIndexedFileWriter.class);
	
	private ConfigManager configManager;
	private FileManager fileManager;
	
	public InputIndexedFileWriter() {
		configManager  = ConfigManager.INSTANCE;
		fileManager = FileManager.INSTANCE;
	}
	

	@Override
	public void write(GroupVO groupVO) {
		File file = IndexedFileUtil.readTempFile(groupVO.getName(), IndexedFileUtil.TAG_GRP);
		writeVOtoFile(groupVO, file);
	}

	@Override
	public void write(StandardVO standardVO) {
		File file = IndexedFileUtil.readTempFile(standardVO.getName(), IndexedFileUtil.TAG_STN);		
		writeVOtoFile(standardVO, file);
	}
	
	@Override
	public void postProcess() {
		logger.debug("인덱싱 작업을 시작합니다.");
		
		createIndexFiles();
		
		logger.debug("인덱싱 작업을 종료합니다.");
	}

	/**
	 * 인덱스 파일들을 생성한다.
	 */
	private void createIndexFiles() {
		UserSettingVO userSetting = configManager.getUserSetting();
		
		String ext = userSetting.getTempFileExt(); 
				
		File folder = fileManager.loadFolder(userSetting.getTempFileFolder());
		
		int count = 0;
		
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			
			if (!file.getName().endsWith(ext)) {
				continue;
			}
			
			// 표준용어 파일만 생성한다
			if (!file.getName().startsWith(IndexedFileUtil.TAG_STN)) {
				continue;
			}
			
			logger.debug("[" + count++ + "]" + file.getName() + " 파일 인덱싱 작업중입니다.");
			createIndexFile(file);
		}
	}

	/**
	 * 특정파일에 대해서 인덱스파일을 생성한다
	 * @param file
	 */
	private void createIndexFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line = null;
			int lineNumber = 0;
			while ((line=br.readLine()) != null) {
				++lineNumber;
				
				String key = IndexedFileUtil.getFirstJsonKey(line);
				if (key == null) {
					br.close();
					throw new MetaCException("파일이 잘못되었습니다. - 파일명 : [" + file.getName() + "], 라인["+ lineNumber + "] : [" + line + "]");
				}
								
								
				writeIndexFile(file.getName(), key, lineNumber);
			}
			
			br.close();
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}

	/**
	 * 
	 * 파일이름을 기준으로 인덱스 파일을 생성한다
	 *  -인덱스태그명+파일명
	 *  
	 * 인덱스 파일은 JSON 값의 첫번째 값, 실제파일의 저장위치 를 보관한다
	 * 
	 * @param fileName
	 * @param key
	 * @param lineNumber
	 */
	private void writeIndexFile(String fileName, String key, int lineNumber) {		
		int hash = IndexedFileUtil.createHash(key);
		
		UserSettingVO userSetting = configManager.getUserSetting();
		
		String folder = userSetting.getTempFileFolder();
		String indexFileName = IndexedFileUtil.createIndexFileName(fileName);
		
		// 특정위치에 삽입하기 위해선 템프파일을 만들어서
		// 원본데이터를 옴기면서 삽입/확장을 하는 형태를 취한다.
		File file = fileManager.loadFile(folder, indexFileName);
		File newFile = fileManager.loadFile(folder, indexFileName+"_temp");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
			String readLine = null;
			int lineCounter = 0;
			
			// 내가 삽입하고자 하는 위치까지 임시파일에 복사한다
			while (true) {
				readLine = br.readLine();
				++lineCounter;
				
				if (readLine == null || lineCounter == hash) {
					break;
				}
				
				bw.write(readLine);
				bw.newLine();
			}
			
			// 파일양이 작아서 부족하면 엔터를 집어넣는다
			if (lineCounter < hash) {
				while (lineCounter++ < hash) {
					bw.newLine();
				}
			}
			
			if (readLine == null) {
				readLine = StringUtil.EMPTY;
			}
			
			// 파일에 저장할 Index정보를 담은 VO를 생성한다
			IndexDataVO vo = new IndexDataVO();
			vo.setHashCode(StringUtil.toString(key.hashCode()));
			vo.setIndex(lineNumber);
			
			// 기존값에 구분자를 넣어서 write
			readLine += IndexedFileUtil.INDEX_SEP + vo.toJson();
			bw.write(readLine);
			bw.newLine();
			
			// 남은 양을 write한다
			while ((readLine = br.readLine()) != null) {
				bw.write(readLine);
				bw.newLine();
			}
			
			br.close();
			bw.close();
			
			// 파일 스위칭
			file.delete();
			newFile.renameTo(file);
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}

	

	/**
	 * VO를 파일에 쓴다.
	 * @param vo
	 * @param file
	 */
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
	
}
