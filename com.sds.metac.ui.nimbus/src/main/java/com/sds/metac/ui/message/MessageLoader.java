package com.sds.metac.ui.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.util.StringUtil;

public enum MessageLoader {
	INSTANCE;
	
	Map<String, String> messageMap = new HashMap<String, String>();
	
	protected void loadMessage() {
		FileManager fileManager = FileManager.INSTANCE;
		
		Properties properties = fileManager.readPropertiesFile("properties", "ui-message.properties", false);
		
		if (properties == null) {
			throw new MetaCException("메시지 파일이 존재하지 않습니다.");
		}
		
		messageMap.clear();
		
		for (Object key : properties.keySet()) {
			messageMap.put((String)key, (String)properties.get(key));
		}
		
		if (messageMap.isEmpty()) {
			throw new MetaCException("메시지 파일이 존재하지 않습니다.");
		}
	}
	
	protected boolean isLoaded() {
		return !messageMap.isEmpty();
	}
	
	protected String getMessage(String key) {
		String ret = messageMap.get(key);
		if (ret == null) {
			ret = StringUtil.EMPTY;
		}
		
		return ret;
	}
}
