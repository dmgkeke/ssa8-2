package com.sds.metac.output.reader.impl;

import com.sds.metac.cache.Cache;
import com.sds.metac.cache.CacheManager;
import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.vo.domain.StandardVO;

public class StandardFileReader implements StandardReader {
	
	CacheManager cacheManager = CacheManager.INSTANCE;
	Cache<StandardVO> cache;
	
	
	
	public StandardFileReader() {
		cache = cacheManager.createCache("cache-StandardFileReader", new StandardFileEvent());
	}
	
	
	@Override
	public StandardVO getStandardVO(String name) {
		return cache.getValue(name);
	}
}
