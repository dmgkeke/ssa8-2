package com.sds.metac.ui.swing.tab;

import javax.swing.JTabbedPane;

import com.sds.metac.ui.message.Message;

@SuppressWarnings("serial")
public class MainTab extends JTabbedPane {
	
	public MainTab() {
		addTab(Message.get("message.label.core.tabname"), new CoreTabPanel());
		
	}
}
