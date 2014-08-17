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
import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.util.IndexedFileUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.IndexDataVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class OutputIndexedFileReader implements OutputReader {

	Logger logger = Logger.getLogger(OutputIndexedFileReader.class);

	
	private ConfigManager configManager = ConfigManager.INSTANCE;
	private FileManager fileManager = FileManager.INSTANCE;
	
	// 그룹 템프파일을 보관하고 있을 Stack 
	Stack<File> groupStack = new Stack<File>();
	private Scanner curScanner = null;
	private File tempFolder;
	private String ext;
	
	public OutputIndexedFileReader() {
		UserSettingVO userSetting = configManager.getUserSetting();
		ext = userSetting.getTempFileExt();
		tempFolder = fileManager.loadFolder(userSetting.getTempFileFolder());
		
		
		for (File file : tempFolder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			if (!file.getName().endsWith(ext)) {
				continue;
			}
			if (!file.getName().startsWith(IndexedFileUtil.TAG_GRP)) {
				continue;
			}
			
			groupStack.push(file);
		}
	}

	/**
	 * 다음 읽을 수 있는 통합코드 정보가 있는지 확인
	 */
	public boolean hasNextGroup() {
		if (curScanner == null) {
			// 현재 스캐너가 없으면 다음 스캐너 읽기 시도
			if (!readNextScanner()) {
				return false;
			}
		}
		
		return curScanner.hasNextLine();
	}

	/**
	 * 다음 스캐너 확보를 시도한다
	 * @return
	 */
	private boolean readNextScanner() {
		// 스택에 쌓인게 없으면 false
		if (groupStack.isEmpty()) {
			return false;
		}
		
		// 스택에서 하나 꺼낸다
		File file = groupStack.pop();
		
		try {
			curScanner = new Scanner(file);
		} catch (Exception e) {
			throw new MetaCException(e);
		}
		
		return true;
	}

	/**
	 * 그룹 하나를 읽는다
	 */
	public GroupVO readGroup() {
		if (curScanner == null) {
			throw new MetaCException("더이상 읽을 그룹VO가 없습니다.");
		}
		
		String line = curScanner.nextLine();
		
		return GroupVO.fromJson(line, GroupVO.class);
	}

	/**
	 * 그룹 VO가 가지고 있는 표준용어에 대한 맵을 리턴한다.
	 */
	public Map<String, String> getStandardMap(GroupVO groupVO) {
		Map<String, String> retMap = new HashMap<String, String>();
		
		String name = groupVO.getName();
		retMap.put(name, this.getStandardValue(name));
		
		for (String code : groupVO.getCodes()) {
			retMap.put(code, this.getStandardValue(code));
		}
		
		return retMap;
	}

	private String getStandardValue(String name) {
		UserSettingVO userSetting = configManager.getUserSetting();
		
		// 용어에 해당하는 파일을 읽는다.
		File file = IndexedFileUtil.readTempFile(name, IndexedFileUtil.TAG_STN);
		
		// 인덱스 파일을 읽는다.
		String createIndexFileName = IndexedFileUtil.createIndexFileName(file.getName());
		File indexFile = fileManager.loadFile(userSetting.getTempFileFolder(), createIndexFileName);
		
		// 용어이 해당하는 해쉬값을 구한다.
		int hash = IndexedFileUtil.createHash(name);
		
		
		try {
			// 인덱스 파일중 해쉬값에 해당하는 위치에 실제 위치가 보관되어있다
			IndexedFileReader indexReader = new IndexedFileReader(indexFile);			
			SortedMap<Integer, String> indexTempValue = indexReader.readLines(hash, hash);			
			
			// 구분자로 나눈다음
			String[] indexInfos = indexTempValue.get(hash).split(IndexedFileUtil.INDEX_SEP);
			
			// 표준용어의 hashCode가 일치하는 값을 찾는다.
			IndexDataVO indexVO = null;
			for (String indexInfo : indexInfos) {
				if (StringUtil.isEmpty(indexInfo)) {
					continue;
				}
				
				String key = IndexedFileUtil.getFirstJsonKey(indexInfo);
				
				if (StringUtil.equals(key, ""+name.hashCode())) {
					indexVO = IndexDataVO.fromJson(indexInfo, IndexDataVO.class);
					break;
				}
			}
			indexReader.close();
			
			// indexVO 가 null 이면 에러
			if (indexVO == null) {
				throw new MetaCException("표준용어 [" + name + "]에 해당하는 값을 찾을 수 없습니다.");
			}
			
			// 실제 표준용어 파일에 있는 위치를 읽는다
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

}
