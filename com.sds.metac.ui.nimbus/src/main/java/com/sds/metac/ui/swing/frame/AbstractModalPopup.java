package com.sds.metac.ui.swing.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sds.metac.ui.config.UIConfigManager;
import com.sds.metac.ui.swing.resource.ResourceManager;

@SuppressWarnings("serial")
public class AbstractModalPopup extends JDialog {

	protected UIConfigManager uiConfigManager = UIConfigManager.INSTANCE;
	
	public AbstractModalPopup() {
		setLookAndFeel(uiConfigManager.getCurrentLookAndFeel());
		
		setDefaultLookAndFeelDecorated(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ResourceManager.releaseAll(getName());
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	private void setLookAndFeel(String lookAndFeel) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (lookAndFeel.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
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
