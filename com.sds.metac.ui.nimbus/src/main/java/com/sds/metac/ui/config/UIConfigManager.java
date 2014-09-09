package com.sds.metac.ui.config;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Properties;

import com.sds.metac.file.FileManager;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.util.StringUtil;

public enum UIConfigManager {
	INSTANCE;
	
	private static final String KEY_LOOK_AND_FEEL = "currentLookAndFeel";
	
	private static final String FILE_NAME = "ui-setting.properties";
	private static final String FOLDER_NAME = "properties";
	
	private FileManager fileManager = FileManager.INSTANCE;
	private Properties properties;
	
	private UIConfigManager() {
		properties = fileManager.readPropertiesFile(FOLDER_NAME, FILE_NAME, true);
	}
	
	public String getCurrentLookAndFeel() {
		return StringUtil.nvl((String)properties.get(KEY_LOOK_AND_FEEL), UIConstants.DEFAULT_LOOK_AND_FEEL);
	}
	
	public void setCurrentLookAndFeel(String value) {
		value = StringUtil.nvl(value, UIConstants.DEFAULT_LOOK_AND_FEEL);
		
		properties.put(KEY_LOOK_AND_FEEL, value);
		
		fileManager.writePropertiesFile(FOLDER_NAME, FILE_NAME, properties);
	}
	
	public static int[] getCenterPosition(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				
		return new int[] {
			 (int) (screenSize.getWidth()-width)/2
			,(int) (screenSize.getHeight()-height)/2
		};
	}
}
