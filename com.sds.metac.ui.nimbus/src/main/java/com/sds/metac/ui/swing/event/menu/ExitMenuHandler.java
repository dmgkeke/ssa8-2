package com.sds.metac.ui.swing.event.menu;

import java.awt.AWTEvent;

import com.sds.metac.ui.swing.event.specific.CommonDefaultHandler;

public class ExitMenuHandler extends CommonDefaultHandler {

	@Override
	public void invoke(AWTEvent e) {
		System.exit(0);
	}

}
