package com.sds.metac.ui.swing.frame;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.tab.MainTab;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	public MainFrame() {
		setName(UIConstants.MAIN_FRAME_NAME);
		
		setLookAndFeel();		
		setDefaultLookAndFeelDecorated(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(640, 480);
		
		
		add(new MainTab());
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
