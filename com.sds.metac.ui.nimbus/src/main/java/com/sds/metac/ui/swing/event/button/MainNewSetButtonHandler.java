package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class MainNewSetButtonHandler implements CommonHandler {
	@Override
	public void invoke(AWTEvent e) {
		PopupSetFrame popupSet = new PopupSetFrame();
		ResourceManager.release(UIConstants.MAIN_FRAME_NAME, UIConstants.POPUP_SET_FRAME_NAME);
		ResourceManager.register(UIConstants.MAIN_FRAME_NAME, UIConstants.POPUP_SET_FRAME_NAME, popupSet);
		popupSet.setVisible(true);
	}
}
