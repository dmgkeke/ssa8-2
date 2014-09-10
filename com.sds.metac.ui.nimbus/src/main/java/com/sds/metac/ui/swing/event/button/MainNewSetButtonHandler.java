package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.specific.CommonDefaultHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class MainNewSetButtonHandler extends CommonDefaultHandler {
	@Override
	public void invoke(AWTEvent e) {
		PopupSetFrame popupSet = new PopupSetFrame();
		ResourceManager.release(UIConstants.NAME_FRAME_MAIN, UIConstants.NAME_FRAME_POPUP_SET);
		ResourceManager.register(UIConstants.NAME_FRAME_MAIN, UIConstants.NAME_FRAME_POPUP_SET, popupSet);
		popupSet.setVisible(true);
	}
}
