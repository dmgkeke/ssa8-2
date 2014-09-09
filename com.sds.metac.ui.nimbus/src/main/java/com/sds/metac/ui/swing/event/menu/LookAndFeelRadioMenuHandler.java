package com.sds.metac.ui.swing.event.menu;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;

import com.sds.metac.ui.config.UIConfigManager;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.MainFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class LookAndFeelRadioMenuHandler implements CommonHandler{
	UIConfigManager uiConfigManager = UIConfigManager.INSTANCE;
	
	@Override
	public void invoke(AWTEvent e) {
		ActionEvent ae = (ActionEvent)e;
		uiConfigManager.setCurrentLookAndFeel(ae.getActionCommand());
		
		MainFrame mainFrame = (MainFrame) ResourceManager.get(UIConstants.MAIN_FRAME_NAME);
		
		mainFrame.changeLookAndFeel(ae.getActionCommand());
	}
}
