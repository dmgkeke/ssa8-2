package com.sds.metac.ui.swing;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sds.metac.ui.swing.frame.MainFrame;

public class ConfigUI implements Runnable {
	
	private static final ConfigUI instance = new ConfigUI();
	public static ConfigUI getInstance() {
		return instance;
	}
	private ConfigUI() {}
	
	
	private MainFrame mainFrame;
	public static MainFrame getMainFrame() {
		return instance.mainFrame;
	}
	
	@Override
	public void run() {
		mainFrame = new MainFrame();
	}
}
