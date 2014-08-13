package com.sds.metac.post;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.core.MetaCClassLoader;
import com.sds.metac.vo.core.ClassInfoVO;

public enum PostProcessorFactory {
	INSTANCE;

	public PostProcessor getPostProcessor() {
		ConfigManager configManager = ConfigManager.INSTANCE;
		ClassInfoVO classInfoVO = configManager.getPostProcessorClassInfo();
		
		MetaCClassLoader metaCClassLoader = MetaCClassLoader.INSTANCE;
		PostProcessor postProcessor = null;
		try {
			postProcessor = metaCClassLoader.createInstance(classInfoVO, PostProcessor.class);
		} catch (Exception e) {
			postProcessor = null;
		}
		return postProcessor;
	}

}
