package com.sds.metac.config;

import com.sds.metac.file.FileManager;
import com.sds.metac.schema.InputConfig;

public enum InputCsvConfigManager {
	INSTANCE;

	private InputConfig inputConfig;
	private static final String INPUT_CONFIG_FILE_NAME = "inputConfig.xml";

	private InputCsvConfigManager() {
		readInputConfig();
		createTmpFolder();
	}

	public InputConfig getInputConfig() {
		return inputConfig;
	}

	private void readInputConfig() {
		FileManager fileManager = FileManager.INSTANCE;
		inputConfig = fileManager.readConfigXmlFile(INPUT_CONFIG_FILE_NAME,
				InputConfig.class);
	}

	public void inputConfig() {
		FileManager fileManager = FileManager.INSTANCE;
		if (inputConfig == null) {
			inputConfig = fileManager.readConfigXmlFile(INPUT_CONFIG_FILE_NAME,
					InputConfig.class);
		}
		fileManager.writeConfigXmlFile(INPUT_CONFIG_FILE_NAME, inputConfig);
	}

	private void createTmpFolder() {
		FileManager fileManager = FileManager.INSTANCE;

		String forderName = this.inputConfig.getTempFilePath();

		fileManager.createFolder(forderName);
		fileManager.deleteAllFiles(forderName);
	}
}
