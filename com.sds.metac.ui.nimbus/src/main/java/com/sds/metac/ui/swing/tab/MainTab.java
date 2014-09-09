package com.sds.metac.ui.swing.tab;

import javax.swing.JTabbedPane;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.resource.ResourceManager;

@SuppressWarnings("serial")
public class MainTab extends JTabbedPane {
	
	public MainTab() {
		setName(UIConstants.MAIN_TAB_NAME);
		
		CoreTabPanel coreTabPanel = new CoreTabPanel();
		ResourceManager.register(getName(), UIConstants.CORE_TAB_PANEL_NAME, coreTabPanel);
		
		addTab(Message.get("message.label.core.tabname"), coreTabPanel);
		addTab("테스트", new ModuleTabPanel());
		
	}
}
