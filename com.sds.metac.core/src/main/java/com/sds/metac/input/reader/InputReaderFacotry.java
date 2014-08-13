package com.sds.metac.input.reader;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.core.MetaCClassLoader;
import com.sds.metac.vo.core.ClassInfoVO;

public enum InputReaderFacotry {
	INSTANCE;

	public InputReader getReader() {
		ConfigManager configManager = ConfigManager.INSTANCE;		
		ClassInfoVO classInfoVO = configManager.getInputReaderClassInfo();
		
		MetaCClassLoader metaCClassLoader = MetaCClassLoader.INSTANCE;
		return metaCClassLoader.createInstance(classInfoVO, InputReader.class);
	}

}
