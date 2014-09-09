package com.sds.metac.ui.swing.frame;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sds.metac.ui.config.UIConfigManager;

public class AbstractFrame extends JFrame {

	protected UIConfigManager uiConfigManager = UIConfigManager.INSTANCE;
	
	public AbstractFrame() {
		setLookAndFeel(uiConfigManager.getCurrentLookAndFeel());
		setDefaultLookAndFeelDecorated(true);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected void setLookAndFeel(String lookAndFeel) {
		
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (lookAndFeel.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					uiConfigManager.setCurrentLookAndFeel(lookAndFeel);
					
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				uiConfigManager.setCurrentLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ee) {
				
			}
		}
	}
}
