package com.sds.metac.input.reader.poller;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.samsung.sds.ssa8_2.inputmeta.InputMeta;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.input.reader.poller.InputPoller;
import com.sds.metac.input.reader.vo.CustomVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputJsonFilePoller implements InputPoller {
	// logging
	Logger logger = Logger.getLogger(InputJsonFilePoller.class);
	// config
	private InputMeta inputConfigInfo;
	
	// VO
	private Map<String, GroupVO> groupVOMap;
	private Map<String, StandardVO> standardVOMap;
	private List<GroupVO> groupVOList;
	private List<StandardVO> standardVOList;
	private Iterator<GroupVO> groupIter;
	private Iterator<StandardVO> standardIter;
	
	// parser
	private static Gson instance;
	
	private Gson getGsonInstance() {		
		if(instance == null) {
			GsonBuilder builder = new GsonBuilder();
			instance = builder.create();
		}		
		return instance;
	}
	
	private void initialize() {
		if(inputConfigInfo == null) {
			FileManager fileManger = FileManager.INSTANCE;
			inputConfigInfo = fileManger.readConfigXmlFile("InputMeta.xml", InputMeta.class);
		}
	}
	
	public InputJsonFilePoller() {
		initialize();
		String stnFilePath = inputConfigInfo.getConfiguration().getInputFilesPath()
				+FileManager.FOLDER_SEP+inputConfigInfo.getConfiguration().getStandardFileName()
				+FileManager.DOT+inputConfigInfo.getMetaType().toString().toLowerCase();
		String grpFilePath = inputConfigInfo.getConfiguration().getInputFilesPath()
				+FileManager.FOLDER_SEP+inputConfigInfo.getConfiguration().getCommonCodeFileName()
				+FileManager.DOT+inputConfigInfo.getMetaType().toString().toLowerCase();
		generateStnVOList(getGsonInstance(), stnFilePath);
		generateGrpVOList(getGsonInstance(), grpFilePath);
	}
	
	private void generateStnVOList(Gson gson, String stnFilePath) {
		try {
			System.out.println(stnFilePath);
			JsonObject stnTemp = gson.fromJson(new FileReader(new File(stnFilePath)), JsonObject.class);
			JsonArray stnArray = stnTemp.get("DATA").getAsJsonArray();
			
			CustomVO[] customVOArr = gson.fromJson(stnArray, CustomVO[].class);
			List<CustomVO> customList = new ArrayList<CustomVO>(Arrays.asList(customVOArr));
			
			if(customList.isEmpty()) {
				standardVOList 	= new ArrayList<StandardVO>();
			} else {
				Iterator<CustomVO> it = customList.iterator();
				while(it.hasNext()) {
					CustomVO cstTmp 		= it.next();
//					String standardKey 		= cstTmp.getCATEGORY_NM_KR();
//					String standardValue 	= cstTmp.getCATEGORY_NM_EN();
					
					String standardKey 		= cstTmp.getOBJ_KNM();
					String standardValue 	= cstTmp.getOBJ_ENM();
					
					addStandardVO(standardKey, standardValue);		
				}
			}
			
			standardVOList 	= new ArrayList<StandardVO>(standardVOMap.values());
			Collections.sort(standardVOList, new StnNameAscCompare());
			standardIter 	= standardVOList.iterator();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaCException(this.getClass()+".generateStnVOList()");
		}		
	}
	
	private void addStandardVO(String key, String value) {
		if(standardVOMap == null){
			standardVOMap = new HashMap<String, StandardVO>();
		}
		if(standardVOMap.containsKey(key) == false) {
			StandardVO tmp = new StandardVO();
			tmp.setName(key);
			tmp.setValue(value);
			standardVOMap.put(key, tmp);
		}
	}
	
	private void generateGrpVOList(Gson gson, String grpFilePath) {
		try {
			JsonObject grpTemp = gson.fromJson(new FileReader(new File(grpFilePath)), JsonObject.class);
			JsonArray grpArray = grpTemp.get("DATA").getAsJsonArray();
			
			CustomVO[] customVOArr = gson.fromJson(grpArray, CustomVO[].class);
			List<CustomVO> customList = new ArrayList<CustomVO>(Arrays.asList(customVOArr));
			
			if(customList.isEmpty()) {
				groupVOList 	= new ArrayList<GroupVO>();
			} else {
				Iterator<CustomVO> it = customList.iterator();
				while(it.hasNext()) {
					CustomVO cstTmp 		= it.next();
					
//					String groupName 	= cstTmp.getCATEGORY_NM_KR();
//					String groupKey 		= cstTmp.getSECTION_NM_KR();
//					String groupValue 		= cstTmp.getSECTION_CD();
					
					String groupName 	= cstTmp.getPAR_INFO_TYPE_NM();
					String groupKey 		= cstTmp.getDB_NM();
					String groupValue 		= cstTmp.getINF_ID();
					
					addGroupVO(groupName, groupKey, groupValue);		
				}
			}
			
			groupVOList 	= new ArrayList<GroupVO>(groupVOMap.values());
			Collections.sort(groupVOList, new GrpNameAscCompare());
			groupIter 		= groupVOList.iterator();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaCException(this.getClass()+".generateGrpVOList()");
		}
	}
	
	private void addGroupVO(String name, String key, String value) {
		if(groupVOMap == null){
			groupVOMap = new HashMap<String, GroupVO>();
		}
		if(groupVOMap.containsKey(name) == false) {
			GroupVO tmp = new GroupVO();
			tmp.setName(name);
			Map<String, String> codeMap = new HashMap<String, String>();
			codeMap.put(key, value);
			tmp.setCodeMap(codeMap);
			groupVOMap.put(name, tmp);
		} else {
			GroupVO tmp = groupVOMap.get(name);
			if(tmp.getCodeMap().containsKey(key) == false) {
				tmp.getCodeMap().put(key, value);
			}
		}
	}
	
	public boolean hasNextGroup() {
		return groupIter.hasNext();
	}

	public boolean hasNextStandard() {
		return standardIter.hasNext();
	}

	public GroupVO readGroup() {
		return groupIter.next();
	}

	public StandardVO readStandard() {
		return standardIter.next();
	}
	
	class StnNameAscCompare implements Comparator<StandardVO> {
		public int compare(StandardVO arg0, StandardVO arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
	}
	
	class GrpNameAscCompare implements Comparator<GroupVO> {
		public int compare(GroupVO arg0, GroupVO arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
	}

}