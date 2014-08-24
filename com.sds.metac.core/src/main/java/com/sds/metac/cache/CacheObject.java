package com.sds.metac.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CacheObject {
	
	private int maxCount = 1000;
	
	private ConcurrentHashMap<Object, Object> data = new ConcurrentHashMap<Object, Object>();
	private ConcurrentLinkedQueue<Object> keyQueue = new ConcurrentLinkedQueue<Object>();
	
	public CacheObject(int maxCount) {
		this.maxCount = maxCount;
	}
	
	public void addElement(Object key, Object value) {
		keyQueue.add(key);
		data.put(key, value);
		
		if (data.size() > maxCount) {
			removeFirstObject();
		}
	}
	
	
	private void removeFirstObject() {
		data.remove(keyQueue.poll());
	}
	
	protected void invalidate(int percent) {
		int totalSize = keyQueue.size() * percent / 100;
		
		for (int i=0 ; i<totalSize ; i++) {
			removeFirstObject();
		}
	}
}
