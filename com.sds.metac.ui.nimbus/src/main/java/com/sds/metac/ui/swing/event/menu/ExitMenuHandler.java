package com.sds.metac.ui.swing.event.menu;

import java.awt.AWTEvent;

import com.sds.metac.ui.swing.event.CommonHandler;

public class ExitMenuHandler implements CommonHandler {

	@Override
	public void invoke(AWTEvent e) {
		System.exit(0);
	}

}
