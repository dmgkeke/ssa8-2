package com.sds.metac.config;

import java.util.ArrayList;
import java.util.List;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.schema.information.ClassInformation;
import com.sds.metac.schema.information.ClassInformationType;
import com.sds.metac.schema.information.Information;
import com.sds.metac.schema.userSetting.Setting;
import com.sds.metac.schema.userSetting.Setting.Cache;
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
		
		Cache cache = setting.getCache();
		userSettingVO.setMaxCacheSize(cache.getMaxSize());
	}
	
	public void saveUserSetting() {
		FileManager fileManager = FileManager.INSTANCE;
		
		Setting setting = fileManager.readConfigXmlFile("user-setting.xml", Setting.class);
		
		Selection selection = setting.getSelection();
		selection.getInput().setName(userSettingVO.getInputReaderName());
		selection.getOutput().setName(userSettingVO.getOutputWriterName());
		selection.getPostProc().setName(userSettingVO.getPostProcessorName());
		
		Folder folder = setting.getFolder();
		folder.getImplementation().setLocation(userSettingVO.getImplementationFolder());
		folder.getTemp().setLocation(userSettingVO.getTempFileFolder());
		
		Extension extensions = setting.getExtension();
		extensions.getTempFile().setName(userSettingVO.getTempFileExt());
		
		Cache cache = setting.getCache();
		cache.setMaxSize(userSettingVO.getMaxCacheSize());
		
		fileManager.writeConfigXmlFile("user-setting.xml", setting);
	}
	
	private void readInformation() {
		if (userSettingVO == null) {
			readUserSetting();
		}
		
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
			infoVO.setName(StringUtil.trim(classInfo.getName()));
			infoVO.setClassName(StringUtil.trim(classInfo.getClassName()));
			infoVO.setUiClassName(StringUtil.trim(classInfo.getUiClassName()));
			infoVO.setClassFilePath(StringUtil.trim(classInfo.getClassFilePath()));
			
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
	
	public void saveInformation() {
		FileManager fileManager = FileManager.INSTANCE;
				
		Information information = fileManager.readConfigXmlFile("information.xml", Information.class);
		
		List<ClassInformation> list = new ArrayList<ClassInformation>();
		
		list.addAll(createInformationList(informationVO.getInputReaderInfoList(), ClassInformationType.INPUT));
		list.addAll(createInformationList(informationVO.getOutputWriterInfoList(), ClassInformationType.OUTPUT));
		list.addAll(createInformationList(informationVO.getPostProcessorInfoList(), ClassInformationType.POST_PROC));
		
		List<ClassInformation> orgList = information.getClassInfo();
		orgList.clear();
		orgList.addAll(list);
		
		fileManager.writeConfigXmlFile("information.xml", information);
	}



	private List<ClassInformation> createInformationList(List<ClassInfoVO> list, ClassInformationType type) {
		List<ClassInformation> ret = new ArrayList<ClassInformation>();
		
		for (int i=0 ; list!= null && i<list.size() ; i++) {
			ClassInfoVO vo = list.get(i);
			
			ClassInformation info = new ClassInformation();
			info.setType(type);
			info.setName(StringUtil.trim(vo.getName()));
			info.setClassName(StringUtil.trim(vo.getClassName()));
			info.setUiClassName(StringUtil.trim(vo.getUiClassName()));
			info.setClassFilePath(StringUtil.trim(vo.getClassFilePath()));
			
			ret.add(info);
		}
		
		return ret;
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
