package com.sds.metac.output.reader.impl;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.util.IndexedFileUtil;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.domain.GroupVO;

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
}
