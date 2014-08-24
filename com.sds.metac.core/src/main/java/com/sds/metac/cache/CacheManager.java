package com.sds.metac.cache;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;

public enum CacheManager {
	INSTANCE;
	
	ConfigManager configManager = ConfigManager.INSTANCE;
	
	ConcurrentHashMap<String, CacheObject> cacheMap = new ConcurrentHashMap<String, CacheObject>();
	
	
	public CacheObject getCache(String cacheName) {
		if (cacheMap.get(cacheName) == null) {
			throw new MetaCException("캐쉬 : [" + cacheName + "]을 찾을 수 없습니다.");
		}
		
		return cacheMap.get(cacheName);
	}
	
	public void createCache(String cacheName) {
		
	}
	
	private void invalidateCache() {
		
	}
	
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		
		System.out.println(runtime.totalMemory());
		System.out.println(runtime.freeMemory());
		System.out.println(runtime.maxMemory());
		
	}
}


