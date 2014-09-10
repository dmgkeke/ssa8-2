package com.sds.metac.ui.swing.resource;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sds.metac.exception.MetaCException;

public enum ResourceManager {
	INSTANCE;
	
	Map<String, Set<String>> parentMap = new HashMap<String, Set<String>>();
	Map<String, Container> objectMap = new HashMap<String, Container>();
	
	public static <T extends Container> T register(String parentName, String objectName, T object) {
		Set<String> set = INSTANCE.parentMap.get(parentName);
		if (set == null) {
			set = new TreeSet<String>();
			INSTANCE.parentMap.put(parentName, set);
		}
		
		if (set.contains(objectName)) {
			throw new MetaCException("이미 등록된 name [" + objectName + "]");
		}		
		
		set.add(objectName);
		
		INSTANCE.objectMap.put(objectName, object);
		
		return object;
	}
	
	public static void releaseAll(String parentName) {
		Set<String> set = INSTANCE.parentMap.remove(parentName);
		if (set == null) {
			return;
		}
		
		for (String key : set) {
			INSTANCE.objectMap.remove(key);
		}
	}
	
	public static void release(String parentName, String key) {
		Set<String> set = INSTANCE.parentMap.get(parentName);
		if (set != null) {
			set.remove(key);
		}
		INSTANCE.objectMap.remove(key);
	}
	
	public static Container get(String objectName) {
		
		return INSTANCE.objectMap.get(objectName);
	}
	
	public static ResourceCreator getCreator(String curName) {
		return new ResourceCreator(curName);
	}
}
