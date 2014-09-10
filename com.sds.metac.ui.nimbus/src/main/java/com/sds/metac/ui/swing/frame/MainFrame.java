package com.sds.metac.ui.swing.frame;

import static com.sds.metac.ui.constant.UIConstants.DEFAULT_LOOK_AND_FEEL;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_WINDOW;
import static com.sds.metac.ui.constant.UIConstants.NAME_FRAME_MAIN;
import static com.sds.metac.ui.constant.UIConstants.NAME_TAB_MAIN;
import static com.sds.metac.ui.constant.UIConstants.WIDTH_WINDOW;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sds.metac.ui.config.UIConfigManager;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.menu.ExitMenuHandler;
import com.sds.metac.ui.swing.event.menu.LookAndFeelRadioMenuHandler;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.ui.swing.tab.MainTab;

@SuppressWarnings("serial")
public class MainFrame extends AbstractFrame {
	
	public MainFrame() {
		setName(NAME_FRAME_MAIN);	
		setTitle(Message.get("message.label.title.main"));
		
		setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		int[] pos = UIConfigManager.getCenterPosition(WIDTH_WINDOW, HEIGHT_WINDOW);
		setLocation(pos[0], pos[1]);
		
		MainTab mainTab = ResourceManager.register(NAME_FRAME_MAIN, NAME_TAB_MAIN, new MainTab());
		add(mainTab);
		
		setJMenuBar(createMenubar());
	}

	private JMenuBar createMenubar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu toolMenu = new JMenu(Message.get("message.label.menu.tool"));
		menuBar.add(toolMenu);
		
		JMenu lookAndFeel = new JMenu("(L) " + Message.get("message.label.menu.tool.lookAndFeel"));
		lookAndFeel.setMnemonic('L');
		toolMenu.add(lookAndFeel);
		
		JMenuItem exit = new JMenuItem("(X) " + Message.get("message.label.menu.tool.exit"));
		exit.setMnemonic('X');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		CommonActionListener.addHandler(exit, new ExitMenuHandler());
		toolMenu.add(exit);
		
		createLookAndFeelRadio(lookAndFeel);
		
		return menuBar;
	}

	private void createLookAndFeelRadio(JMenu lookAndFeel) {
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		
		ButtonGroup group = new ButtonGroup();
		
		int index = 1;
		
		for (LookAndFeelInfo info : infos) {
			String name = info.getName();
			
			JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(name);
			if (name.equals(uiConfigManager.getCurrentLookAndFeel())) {
				menuItem.setSelected(true);
			}
			
			CommonActionListener.addHandler(menuItem, new LookAndFeelRadioMenuHandler());
			
			group.add(menuItem);
			
			menuItem.setAccelerator(KeyStroke.getKeyStroke((char)(48+index++), ActionEvent.CTRL_MASK));
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
			uiConfigManager.setCurrentLookAndFeel(DEFAULT_LOOK_AND_FEEL);
		}
	}

	
}
