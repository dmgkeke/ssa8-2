package com.sds.metac.output.writer;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.core.MetaCClassLoader;
import com.sds.metac.vo.core.ClassInfoVO;

public enum OutputWriterFactory {
	INSTANCE;

	public OutputWriter getOutputWriter() {
		ConfigManager configManager = ConfigManager.INSTANCE;
		ClassInfoVO classInfoVO = configManager.getOutputWriterClassInfo();
		
		MetaCClassLoader metaCClassLoader = MetaCClassLoader.INSTANCE;
		return metaCClassLoader.createInstance(classInfoVO, OutputWriter.class);
	}

}
