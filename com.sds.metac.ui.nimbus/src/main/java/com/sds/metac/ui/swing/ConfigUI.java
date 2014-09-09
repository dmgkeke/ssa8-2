package com.sds.metac.ui.swing;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.frame.MainFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class ConfigUI implements Runnable {
	
	private static final ConfigUI instance = new ConfigUI();
	public static ConfigUI getInstance() {
		return instance;
	}
	private ConfigUI() {}
	
	
	
	@Override
	public void run() {
		MainFrame mainFrame = new MainFrame();
		ResourceManager.register(UIConstants.PROGRAM_NAME, UIConstants.MAIN_FRAME_NAME, mainFrame);
	}
}
