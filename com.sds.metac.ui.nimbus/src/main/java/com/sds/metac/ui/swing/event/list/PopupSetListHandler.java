package com.sds.metac.ui.swing.event.list;

import java.awt.event.MouseEvent;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.specific.CommonMouseHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class PopupSetListHandler extends CommonMouseHandler {

	@Override
	public boolean mouseClicked(MouseEvent e) {
		PopupSetFrame popupSet = (PopupSetFrame) ResourceManager.get(UIConstants.NAME_FRAME_POPUP_SET);
		
		if (popupSet == null) {
			return false;
		}
		
		popupSet.setContentData();
		
		return true;
	}
}
