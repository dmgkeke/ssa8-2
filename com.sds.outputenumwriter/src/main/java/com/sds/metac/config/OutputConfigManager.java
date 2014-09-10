package com.sds.metac.config;

import com.sds.metac.file.FileManager;
import com.sds.metac.schema.OutputWriterConfig;

public enum OutputConfigManager {
	INSTANCE;
	
	private OutputWriterConfig outputWriterConfig;
	
	private static final String CONFIG_FILE_NAME = "outputWriterConfig.xml";
	
	private OutputConfigManager() {
		readOutputWriterConfig();
		
		createTempFolder();
	}
	
	public OutputWriterConfig getOutputWriterConfig() {
		return outputWriterConfig;
	}
	
	private void readOutputWriterConfig() {
		FileManager fileManager = FileManager.INSTANCE;
		
		outputWriterConfig = fileManager.readConfigXmlFile(CONFIG_FILE_NAME, OutputWriterConfig.class);
	}
	
	public void saveOutputWriterConfig() {
		FileManager fileManager = FileManager.INSTANCE;
		
		if( outputWriterConfig == null ) {
			outputWriterConfig = fileManager.readConfigXmlFile(CONFIG_FILE_NAME, OutputWriterConfig.class);
		}
		
		fileManager.writeConfigXmlFile(CONFIG_FILE_NAME, outputWriterConfig);
	}

	private void createTempFolder() {
		FileManager fileManager = FileManager.INSTANCE;
		
		String folderName = this.outputWriterConfig.getTempFilePath();
		
		fileManager.createFolder(folderName);
		fileManager.deleteAllFiles(folderName);
	}

}
