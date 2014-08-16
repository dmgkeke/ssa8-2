package com.sds.metac.output.reader.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.jramoyo.io.IndexedFileReader;
import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.input.writer.impl.InputIndexedFileWriter;
import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.IndexDataVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class OutputIndexedFileReader implements OutputReader {

	Logger logger = Logger.getLogger(OutputIndexedFileReader.class);

	// 테스트용
	int count = 0;
	
	private ConfigManager configManager = ConfigManager.INSTANCE;
	private UserSettingVO userSetting = configManager.getUserSetting();
	private FileManager fileManager = FileManager.INSTANCE;
	
	Stack<File> groupStack = new Stack<File>();
	private Scanner curScanner = null;
	private File tempFolder;
	private String ext;
	
	public OutputIndexedFileReader() {
		ext = userSetting.getTempFileExt();
		tempFolder = fileManager.loadFolder(userSetting.getTempFileFolder());
		
		
		for (File file : tempFolder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			if (!file.getName().startsWith(InputIndexedFileWriter.TAG_GRP)) {
				continue;
			}
			if (!file.getName().endsWith(ext)) {
				continue;
			}
			
			groupStack.push(file);
		}
	}

	public boolean hasNextGroup() {
		if (curScanner == null) {
			if (!readNextScanner()) {
				return false;
			}
		}
		
		return curScanner.hasNextLine();
	}

	private boolean readNextScanner() {
		if (groupStack.isEmpty()) {
			return false;
		}
		
		File file = groupStack.pop();
		
		try {
			curScanner = new Scanner(file);
		} catch (Exception e) {
			throw new MetaCException(e);
		}
		
		return true;
	}

	public GroupVO readGroup() {
		if (curScanner == null) {
			throw new MetaCException("더이상 읽을 그룹VO가 없습니다.");
		}
		
		String line = curScanner.nextLine();
		
		return GroupVO.fromJson(line, GroupVO.class);
	}

	public Map<String, String> getStandardMap(GroupVO groupVO) {
		Map<String, String> retMap = new HashMap<String, String>();
		
		String name = groupVO.getName();
		retMap.put(name, getStandardValue(name));
		
		for (String code : groupVO.getCodes()) {
			retMap.put(code, getStandardValue(code));
		}
		
		return retMap;
	}

	private String getStandardValue(String name) {
		
		File file = getFile(name, InputIndexedFileWriter.TAG_STN);
		File indexFile = fileManager.loadFile(userSetting.getTempFileFolder(), createIndexFileName(file.getName()));
		
		int hash = createHash(name);
		
		try {
			IndexedFileReader indexReader = new IndexedFileReader(indexFile);			
			SortedMap<Integer, String> indexTempValue = indexReader.readLines(hash, hash);			
			
			String[] indexInfos = indexTempValue.get(hash).split(InputIndexedFileWriter.INDEX_SEP);
			
			IndexDataVO indexVO = null;
			for (String indexInfo : indexInfos) {
				if (StringUtil.isEmpty(indexInfo)) {
					continue;
				}
				
				String key = getFirstJsonKey(indexInfo);
				
				if (StringUtil.equals(key, ""+name.hashCode())) {
					indexVO = IndexDataVO.fromJson(indexInfo, IndexDataVO.class);
					break;
				}
			}
			indexReader.close();
			
			Integer index = indexVO.getIndex();
			
			IndexedFileReader dataReader = new IndexedFileReader(file);
			SortedMap<Integer, String> dataTempValue = dataReader.readLines(index, index);
			
			String dataStr = dataTempValue.get(index);
			dataReader.close();
			
			StandardVO vo = StandardVO.fromJson(dataStr, StandardVO.class);
			
			return vo.getValue();
			
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}
	
	private String getFirstJsonKey(String line) {
		if (StringUtil.isEmpty(line)) {
			return null;
		}
		
		String ret = null;
		int first = line.indexOf(":");
		int last = line.indexOf("\"", first+2);
		
		ret = line.substring(first+2, last);
		
		return ret;
	}
	
	private int createHash(String key) {
		int value = 256;
		int hash = value;
		
		for (int i=0 ; i<key.length() ; i++) {
			hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		
		return Math.abs(hash%value)+1;
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
		return InputIndexedFileWriter.TAG_INDEX + fileName;
	}

}
