package com.sds.metac.ui.swing.frame;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.menu.LookAndFeelRadioMenuHandler;
import com.sds.metac.ui.swing.tab.MainTab;

@SuppressWarnings("serial")
public class MainFrame extends AbstractFrame {
	
	public MainFrame() {
		setName(UIConstants.MAIN_FRAME_NAME);	
		setTitle(Message.get("message.label.title.main"));
		
		setSize(640, 480);
		add(new MainTab());
		
		setJMenuBar(createMenubar());
		
		setVisible(true);
	}

	private JMenuBar createMenubar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu toolMenu = new JMenu(Message.get("message.label.menu.tool"));
		menuBar.add(toolMenu);
		
		JMenu lookAndFeel = new JMenu(Message.get("message.label.menu.tool.lookAndFeel"));
		toolMenu.add(lookAndFeel);
		
		createLookAndFeelRadio(lookAndFeel);
		
		return menuBar;
	}

	private void createLookAndFeelRadio(JMenu lookAndFeel) {
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		
		ButtonGroup group = new ButtonGroup();
		
		for (LookAndFeelInfo info : infos) {
			String name = info.getName();
			
			JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(name);
			if (name.equals(uiConfigManager.getCurrentLookAndFeel())) {
				menuItem.setSelected(true);
			}
			
			CommonActionListener.addHandler(menuItem, new LookAndFeelRadioMenuHandler());
			
			group.add(menuItem);
			
			lookAndFeel.add(menuItem);
		}
	}

	public void changeLookAndFeel(String lookAndFeel) {
		try {
			
			LookAndFeelInfo selInfo = null;
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getName().equals(lookAndFeel)) {
					selInfo = info;
					break;
				}
			}
			
			
			UIManager.setLookAndFeel(selInfo.getClassName());
			SwingUtilities.updateComponentTreeUI(getRootPane());
			
			this.repaint();
		} catch (Exception e) {
			uiConfigManager.setCurrentLookAndFeel(UIConstants.DEFAULT_LOOK_AND_FEEL);
		}
	}

	
}
