package com.sds.metac.ui.swing.event.button;

import java.awt.event.ActionEvent;

import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;

public class NewSetButtonHandler implements CommonHandler {
	@Override
	public void invoke(ActionEvent e) {
		new PopupSetFrame();
	}
}
