package com.sds.metac.ui.swing.event.window;

import java.awt.event.WindowEvent;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.specific.CommonWindowHandler;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.ui.swing.tab.CoreTabPanel;
import com.sds.metac.ui.swing.tab.MainTab;

public class PopupSetWindowHandler extends CommonWindowHandler {
	
	@Override
	public boolean windowClosing(WindowEvent e) {
		CoreTabPanel coreTabPanel = (CoreTabPanel) ResourceManager.get(UIConstants.NAME_TAB_CORE_PANEL);
		coreTabPanel.setContentData();
		
		MainTab mainTab = (MainTab) ResourceManager.get(UIConstants.NAME_TAB_MAIN);
		mainTab.recreateModuleTabs();
		
		return true;
	}
}
