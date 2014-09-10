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
		return metaCClassLoader.createInstanceNoError(classInfoVO, PostProcessor.class);
	}

}
