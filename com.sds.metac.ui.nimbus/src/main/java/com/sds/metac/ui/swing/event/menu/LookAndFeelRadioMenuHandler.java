package com.sds.metac.ui.swing.event.menu;

import java.awt.event.ActionEvent;

import com.sds.metac.ui.config.UIConfigManager;
import com.sds.metac.ui.swing.ConfigUI;
import com.sds.metac.ui.swing.event.CommonHandler;

public class LookAndFeelRadioMenuHandler implements CommonHandler{
	UIConfigManager uiConfigManager = UIConfigManager.INSTANCE;
	
	@Override
	public void invoke(ActionEvent e) {
		uiConfigManager.setCurrentLookAndFeel(e.getActionCommand());
		
		ConfigUI.getMainFrame().changeLookAndFeel(e.getActionCommand());
	}
}
