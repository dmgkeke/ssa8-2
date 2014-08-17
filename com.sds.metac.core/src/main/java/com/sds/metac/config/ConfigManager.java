package com.sds.metac.config;

import java.util.ArrayList;
import java.util.List;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.schema.information.ClassInformation;
import com.sds.metac.schema.information.Information;
import com.sds.metac.schema.userSetting.Setting;
import com.sds.metac.schema.userSetting.Setting.Extension;
import com.sds.metac.schema.userSetting.Setting.Folder;
import com.sds.metac.schema.userSetting.Setting.Selection;
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
		
		createTempFolder();
	}
	
	

	public UserSettingVO getUserSetting() {
		return userSettingVO;
	}
	
	public InformationVO getInformation() {
		return informationVO;
	}
	
	private void readUserSetting() {
		FileManager fileManager = FileManager.INSTANCE;
		
		Setting setting = fileManager.readConfigXmlFile("user-setting.xml", Setting.class);
		
		userSettingVO = new UserSettingVO();
		
		Selection selection = setting.getSelection();
		userSettingVO.setInputReaderName(selection.getInput().getName());
		userSettingVO.setOutputWriterName(selection.getOutput().getName());
		userSettingVO.setPostProcessorName(selection.getPostProc().getName());
		
		Folder folder = setting.getFolder();
		userSettingVO.setImplementationFolder(folder.getImplementation().getLocation());
		userSettingVO.setTempFileFolder(folder.getTemp().getLocation());
		
		Extension extensions = setting.getExtension();
		userSettingVO.setTempFileExt(extensions.getTempFile().getName());
	}

	private void readInformation() {
		if (userSettingVO == null) {
			readUserSetting();
		}
		
		String implementationFolder = userSettingVO.getImplementationFolder();
		
		FileManager fileManager = FileManager.INSTANCE;
		
		Information information = fileManager.readConfigXmlFile("information.xml", Information.class);
		
		informationVO = new InformationVO();
		List<ClassInfoVO> inputReaderInfoList = new ArrayList<ClassInfoVO>();
		informationVO.setInputReaderInfoList(inputReaderInfoList);
		
		List<ClassInfoVO> outputWriterInfoList = new ArrayList<ClassInfoVO>();
		informationVO.setOutputWriterInfoList(outputWriterInfoList);
		
		List<ClassInfoVO> postProcessorInfoList = new ArrayList<ClassInfoVO>();
		informationVO.setPostProcessorInfoList(postProcessorInfoList);
		
		List<ClassInformation> classInfos = information.getClassInfo();
		for (ClassInformation classInfo : classInfos) {
			ClassInfoVO infoVO = new ClassInfoVO();
			infoVO.setName(classInfo.getName());
			infoVO.setClassName(classInfo.getClassName());
			infoVO.setClassFilePath(implementationFolder + "\\" + classInfo.getClassFilePath());
			
			List<ClassInfoVO> list = null;
			switch(classInfo.getType()) {
			case INPUT : list = inputReaderInfoList; break;
			case OUTPUT : list = outputWriterInfoList; break;
			case POST_PROC : list = postProcessorInfoList; break;
			default :
				continue;
			}
			
			list.add(infoVO);
		}
	}
	
	
	private void createTempFolder() {
		FileManager fileManager = FileManager.INSTANCE;
		
		String folderName = this.userSettingVO.getTempFileFolder();
		
		fileManager.createFolder(folderName);
		fileManager.deleteAllFiles(folderName);
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
		
		if (list == null) {
			return null;
		}
		
		for (ClassInfoVO inputReaderInfoVO : list) {
			if (StringUtil.equals(key, inputReaderInfoVO.getName())) {
				ret = inputReaderInfoVO;
				break;
			}
		}
		
		return ret;
	}

}
