package com.sds.metac.input.reader.poller;

import java.io.File;
import java.io.FileNotFoundException;
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
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.input.reader.vo.CustomVO;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class TestInputJsonFilePoller implements InputPoller {
	// logging
	Logger logger = Logger.getLogger(TestInputJsonFilePoller.class);
	final String filePath = "./inputfiles/생활안내Directory분류코드정보.json";
	
	// VO
	private Map<String, GroupVO> groupVOMap;
	private Map<String, StandardVO> standardVOMap;
	private List<GroupVO> groupVOList;
	private List<StandardVO> standardVOList;
	private Iterator<GroupVO> groupIter;
	private Iterator<StandardVO> standardIter;
	
	// parser
	private static Gson instance;	
	// customeVO
	private List<CustomVO> customList;

	private Gson getGsonInstance() {		
		if(instance == null) {
			GsonBuilder builder = new GsonBuilder();
			instance = builder.create();
		}		
		return instance;
	}
	
	public TestInputJsonFilePoller() {
		Gson gson = getGsonInstance();
		try {
			JsonObject temp = gson.fromJson(new FileReader(new File(filePath)), JsonObject.class);
			JsonArray array = temp.get("DATA").getAsJsonArray();
			CustomVO[] customVOArr = gson.fromJson(array, CustomVO[].class);
			
			customList = new ArrayList<CustomVO>(Arrays.asList(customVOArr));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new MetaCException("!!! JsonSyntaxException - getCustomList()");
		} catch (JsonIOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! JsonSyntaxException - getCustomList()");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException("!!! JsonSyntaxException - getCustomList()");
		}
		this.generateVOList();
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
	
	private void generateVOList() {
		if(customList.isEmpty()) {
			groupVOList 	= new ArrayList<GroupVO>();
			standardVOList 	= new ArrayList<StandardVO>();
		} else {
			Iterator<CustomVO> it = customList.iterator();
			while(it.hasNext()) {
				CustomVO cstTmp 		= it.next();
				String standardKey 		= cstTmp.getCATEGORY_NM_KR();
				String standardValue 	= cstTmp.getCATEGORY_NM_EN();
				String groupKey 		= cstTmp.getSECTION_NM_KR();
				String groupValue 		= cstTmp.getSECTION_NM_EN();
				
				addStandardVO(standardKey, standardValue);
				addStandardVO(groupKey, groupValue);
				
				addGroupVO(standardValue, groupKey, groupValue);				
			}
		}
		standardVOList 	= new ArrayList<StandardVO>(standardVOMap.values());
		Collections.sort(standardVOList, new StnNameAscCompare());
		standardIter 	= standardVOList.iterator();
		groupVOList 	= new ArrayList<GroupVO>(groupVOMap.values());
		Collections.sort(groupVOList, new GrpNameAscCompare());
		groupIter 		= groupVOList.iterator();
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
	
	class StnNameAscCompare implements Comparator<StandardVO> {
		 
		/**
		 * 오름차순(ASC)
		 */
		public int compare(StandardVO arg0, StandardVO arg1) {
			// TODO Auto-generated method stub
			return arg0.getName().compareTo(arg1.getName());
		}
 
	}
	
	class GrpNameAscCompare implements Comparator<GroupVO> {
		 
		/**
		 * 오름차순(ASC)
		 */
		public int compare(GroupVO arg0, GroupVO arg1) {
			// TODO Auto-generated method stub
			return arg0.getName().compareTo(arg1.getName());
		}
 
	}
}
