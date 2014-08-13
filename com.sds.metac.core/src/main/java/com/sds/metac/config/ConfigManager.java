package com.sds.metac.config;

import java.util.ArrayList;
import java.util.List;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.file.MetaCFile;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.ClassInfoVO;

public enum ConfigManager {
	INSTANCE;

	private InformationVO informationVO;
	private UserSettingVO userSettingVO;
	
	private ConfigManager() {
		readInformation();
		readUserSetting();
	}
	
	private void readUserSetting() {
		FileManager fileManager = FileManager.INSTANCE;
		
		MetaCFile userSettingFile = fileManager.readFile("userSetting.xml");
		
		CRATE_USER_SETTING : {
			userSettingVO = new UserSettingVO();
			
			userSettingVO.setInputReaderName("jsonReader");
			userSettingVO.setOutputWriterName("enumWriter");
			userSettingVO.setPostProcessorName("postJavaProcessor");
		}
	}

	private void readInformation() {
		FileManager fileManager = FileManager.INSTANCE;
		
		MetaCFile informationFile = fileManager.readFile("information.xml");
		
		// TODO : 임시 코딩임
		CREATE_INFO : {
			informationVO = new InformationVO();
			
			List<ClassInfoVO> inputReaderInfoList = new ArrayList<ClassInfoVO>();
			ClassInfoVO inputReaderInfoVO = new ClassInfoVO();
			inputReaderInfoVO.setName("jsonReader");
			inputReaderInfoVO.setClassName("com.sds.metac.input.reader.poller.InputJsonFilePoller");
			inputReaderInfoVO.setClassFilePath("implementation\\InputJsonFilePoller.jar");
			inputReaderInfoList.add(inputReaderInfoVO);
						
			informationVO.setInputReaderInfoList(inputReaderInfoList);
			
			List<ClassInfoVO> outputWriterInfoList = new ArrayList<ClassInfoVO>();
			ClassInfoVO outputWriterInfoVO = new ClassInfoVO();
			outputWriterInfoVO.setName("enumWriter");
			outputWriterInfoVO.setClassName("com.sds.metac.output.writer.java.OutputEnumWriter");
			outputWriterInfoVO.setClassFilePath("implementation\\OutputEnumWriter.jar");
			outputWriterInfoList.add(outputWriterInfoVO);
			
			informationVO.setOutputWriterInfoList(outputWriterInfoList);
			
			
			List<ClassInfoVO> postProcessorInfoList = new ArrayList<ClassInfoVO>();
			ClassInfoVO postProcessorInfoVO = new ClassInfoVO();
			postProcessorInfoVO.setName("postJavaProcessor");
			postProcessorInfoVO.setClassName("com.sds.metac.post.PostJavaProcessor");
			postProcessorInfoVO.setClassFilePath("implementation\\PostJavaProcessor.jar");
			postProcessorInfoList.add(postProcessorInfoVO);
			
			informationVO.setPostProcessorInfoList(postProcessorInfoList);
		}
	}

	public ClassInfoVO getInputReaderClassInfo() {
		ClassInfoVO classInfo = this.getClassInfo(userSettingVO.getInputReaderName(), informationVO.getInputReaderInfoList());		
		if (classInfo == null) {
			throw new MetaCException("Reader 정보를 읽을수 없습니다. 현재 선택된 Reader : [" + userSettingVO.getInputReaderName() + "]");
		}
		
		return classInfo;
	}

	public ClassInfoVO getOutputWriterClassInfo() {
		ClassInfoVO classInfo = getClassInfo(userSettingVO.getOutputWriterName(), informationVO.getOutputWriterInfoList());
		if (classInfo == null) {
			throw new MetaCException("Writer 정보를 읽을수 없습니다. 현재 선택된 Writer : [" + userSettingVO.getOutputWriterName() + "]");
		}
		
		return classInfo;
	}

	public ClassInfoVO getPostProcessorClassInfo() {
		ClassInfoVO classInfo = getClassInfo(userSettingVO.getPostProcessorName(), informationVO.getPostProcessorInfoList());
		if (classInfo == null) {
			throw new MetaCException("PostProcessor 정보를 읽을수 없습니다. 현재 선택된 Writer : [" + userSettingVO.getPostProcessorName() + "]");
		}
		
		return classInfo;
	}
	
	private ClassInfoVO getClassInfo(String key, List<ClassInfoVO> list) {
		ClassInfoVO ret = null;
		
		for (ClassInfoVO inputReaderInfoVO : list) {
			if (StringUtil.equals(key, inputReaderInfoVO.getName())) {
				ret = inputReaderInfoVO;
				break;
			}
		}
		
		return ret;
	}

}
