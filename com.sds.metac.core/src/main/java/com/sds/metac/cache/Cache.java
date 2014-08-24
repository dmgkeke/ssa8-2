package com.sds.metac.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<T> {
	
	private int maxSize = 1000;
	private CacheEvent<T> event;
	
	private ConcurrentHashMap<Object, CacheObject<T>> datasMap = new ConcurrentHashMap<Object, CacheObject<T>>();
	
	@SuppressWarnings("unused")
	private Cache() {}
	
	
	
	protected Cache(int maxSize, CacheEvent<T> event) {
		this.maxSize = maxSize;
		this.event = event;
	}
	
	private CacheObject<T> addElement(Object key, T value) {
		if (datasMap.size() > maxSize) {
			removeObject();
		}
		
		CacheObject<T> object = new CacheObject<T>(key, value);
		datasMap.put(key, object);
		
		return object;
	}
	
	private void removeObject() {
		int delCount = maxSize / 10;
		delCount = delCount <= 0 ? 1 : delCount;
		
		Collection<CacheObject<T>> datas = datasMap.values();
		List<CacheObject<T>> listDatas = new ArrayList<CacheObject<T>>(datas);
		
		Collections.sort(listDatas);
		
		for (int i=0 ; i<delCount ; i++) {
			datasMap.remove(listDatas.get(i).getKey());
		}
	}
	
	public T getValue(String key) {
		CacheObject<T> object = datasMap.get(key);
		
		if (object == null) {
			T value = event.doAction(key);
			object = addElement(key, value);
		}
		object.addCount();
		
		return object.getValue();
	}
	
}

class CacheObject<T> implements Comparable<CacheObject<T>>{
	private static long cache_time = 0L;
	private static long getTime() {
		if (cache_time+1 >= Long.MAX_VALUE) {
			cache_time = 0;
		}
		return cache_time++;
	}
	
	private Object key;
	private T value;
	private Long time;
	private Integer count;
	
	public CacheObject(Object key, T value) {
		this.key = key;
		this.value = value;
		this.time = CacheObject.getTime();
		this.count = 0;
	}
	
	public Object getKey() {
		return key;
	}
	
	public T getValue() {
		return value;
	}
	
	public void addCount() {
		this.count++;
	}
	
	@Override
	public int compareTo(CacheObject<T> o) {
		int ret = this.count.compareTo(o.count);
		if (ret == 0) {
			ret = this.time.compareTo(o.time);
		}
		
		return ret;
	}
}
