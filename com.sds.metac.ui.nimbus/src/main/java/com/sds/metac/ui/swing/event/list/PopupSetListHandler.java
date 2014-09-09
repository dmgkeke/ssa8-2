package com.sds.metac.ui.swing.event.list;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class PopupSetListHandler implements CommonHandler {

	@Override
	public void invoke(AWTEvent e) {
		MouseEvent me = (MouseEvent)e;
		
		switch (me.getID()) {
		case MouseEvent.MOUSE_CLICKED :
			PopupSetFrame popupSet = (PopupSetFrame) ResourceManager.get(UIConstants.POPUP_SET_FRAME_NAME);
			
			if (popupSet == null) {
				return;
			}
			
			popupSet.setData();
			
			break;
		}
	}

}
