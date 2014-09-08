package com.sds.metac.ui.swing.frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

@SuppressWarnings("serial")
public class AbstractModalPopup extends JDialog {

	public AbstractModalPopup() {
		setLookAndFeel();
		
		setDefaultLookAndFeelDecorated(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ee) {
				
			}
		}
	}
}
