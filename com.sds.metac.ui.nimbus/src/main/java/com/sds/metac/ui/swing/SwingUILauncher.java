package com.sds.metac.ui.swing;

import javax.swing.SwingUtilities;

public enum SwingUILauncher {
	INSTANCE;
	
	public void run() {
		SwingUtilities.invokeLater(ConfigUI.getInstance());
	}
}
