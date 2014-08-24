package com.sds.metac.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cache<T> {
	
	private int maxSize = 1000;
	private CacheEvent<T> event;
	
	private ConcurrentHashMap<Object, T> data = new ConcurrentHashMap<Object, T>();
	private ConcurrentLinkedQueue<Object> keyQueue = new ConcurrentLinkedQueue<Object>();
	
	@SuppressWarnings("unused")
	private Cache() {}
	
	protected Cache(int maxSize, CacheEvent<T> event) {
		this.maxSize = maxSize;
		this.event = event;
	}
	
	public void addElement(Object key, T value) {
		keyQueue.add(key);
		data.put(key, value);
		
		if (data.size() > maxSize) {
			removeFirstObject();
		}
	}
	
	private void removeFirstObject() {
		data.remove(keyQueue.poll());
	}
	
	public T getValue(String key) {
		T ret = data.get(key);
		
		if (ret == null) {
			ret = event.doAction(key);
		}
		
		return ret;
	}
}
