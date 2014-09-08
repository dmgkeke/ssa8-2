package com.sds.metac.ui.swing;

import com.sds.metac.ui.swing.frame.MainFrame;

public class ConfigUI implements Runnable {
	
	private static final ConfigUI instance = new ConfigUI();
	public static ConfigUI getInstance() {
		return instance;
	}
	private ConfigUI() {}
	
	@Override
	public void run() {
		new MainFrame();
	}
}
