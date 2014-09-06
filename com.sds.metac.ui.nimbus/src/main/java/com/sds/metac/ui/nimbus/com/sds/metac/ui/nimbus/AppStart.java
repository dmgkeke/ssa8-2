package com.sds.metac.ui.nimbus.com.sds.metac.ui.nimbus;

import javax.swing.SwingUtilities;

public class AppStart {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(NimbusConfigUI.getInstance());
	}
}
