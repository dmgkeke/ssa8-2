package com.sds.metac.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.vo.config.UserSettingVO;

public enum CacheManager {
	INSTANCE;
	
	ConfigManager configManager = ConfigManager.INSTANCE;
	
	ConcurrentHashMap<String, Cache<?>> cacheMap = new ConcurrentHashMap<String, Cache<?>>();
	
	
	public Cache<?> getCache(String cacheName) {
		if (cacheMap.get(cacheName) == null) {
			throw new MetaCException("캐쉬 : [" + cacheName + "]을 찾을 수 없습니다.");
		}
		
		return cacheMap.get(cacheName);
	}
	
	public <T> Cache<T> createCache(String cacheName, CacheEvent<T> event) {
		UserSettingVO userSettingVO = configManager.getUserSetting();
		int maxSize = userSettingVO.getMaxCacheSize();
		
		Cache<T> cacheObject = new Cache<T>(maxSize, event);
		
		cacheMap.put(cacheName, cacheObject);
		
		return cacheObject;
	}
	
}


