package com.sds.metac.ui.nimbus.com.sds.metac.ui.nimbus;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class NimbusConfigUI implements Runnable {
	
	private static final NimbusConfigUI instance = new NimbusConfigUI();
	public static NimbusConfigUI getInstance() {
		return instance;
	}
	private NimbusConfigUI() {}

	public static void setNimbusLookAndFeel() {
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
	
	@Override
	public void run() {
		NimbusConfigUI.setNimbusLookAndFeel();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(640, 480);
		
		JButton button = new JButton("test");
		
		JPanel panel = new JPanel(new GridLayout());
		panel.add(button);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		//frame.pack();
		frame.setVisible(true);
	}
}
