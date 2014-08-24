package com.sds.metac.cache;

public interface CacheEvent<T> {

	public T doAction(Object key);
}
