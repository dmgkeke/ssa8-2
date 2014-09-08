package com.sds.metac.ui.swing.frame;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.tab.MainTab;

@SuppressWarnings("serial")
public class MainFrame extends AbstractFrame {
	
	public MainFrame() {
		setName(UIConstants.MAIN_FRAME_NAME);	
		setTitle(Message.get("message.label.title.main"));
		
		setSize(640, 480);
		add(new MainTab());
		
		setVisible(true);
	}

	
}
