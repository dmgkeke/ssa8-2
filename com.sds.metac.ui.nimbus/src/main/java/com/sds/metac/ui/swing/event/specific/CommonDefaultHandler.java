package com.sds.metac.ui.swing.event.specific;

import java.awt.AWTEvent;

import com.sds.metac.ui.swing.event.CommonHandler;

public abstract class CommonDefaultHandler implements CommonHandler {
	public abstract void invoke(AWTEvent e);
}
