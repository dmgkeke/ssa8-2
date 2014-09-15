package com.sds.metac.ui.swing.tab;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.core.MetaCClassLoader;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.swing.UICreatorSwing;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.core.ClassInfoVO;

@SuppressWarnings("serial")
public class MainTab extends JTabbedPane {
	
	public MainTab() {
		setName(UIConstants.NAME_TAB_MAIN);
		
		CoreTabPanel coreTabPanel = ResourceManager.register(getName(), UIConstants.NAME_TAB_CORE_PANEL, new CoreTabPanel());		
		add(coreTabPanel);
		
		recreateModuleTabs();
	}

	public void recreateModuleTabs() {
		for (int i=getComponents().length-1 ; i>0 ; i--) {
			Component comp = getComponent(i);
			if (comp instanceof JPanel) {
				JPanel panel = (JPanel)comp;
				ResourceManager.releaseAll(panel.getName());
			}
			
			remove(i);
		}
		
		ConfigManager configManager = ConfigManager.INSTANCE;
		
		try {
			createModuleTab(configManager.getInputReaderClassInfo());
		} catch (Exception e) {}
		
		try {
			createModuleTab(configManager.getOutputWriterClassInfo());
		} catch (Exception e) {}
		
		try {
			createModuleTab(configManager.getPostProcessorClassInfo());
		} catch (Exception e) {}
	}

	private void createModuleTab(ClassInfoVO info) {
		MetaCClassLoader loader = MetaCClassLoader.INSTANCE;
		UICreatorSwing creator = loader.createUIInstanceNoError(info, UICreatorSwing.class);		
		if (creator == null) {
			return;
		}
		
		JPanel panel = creator.createPanel();
		if (panel != null) {
			if (StringUtil.isEmpty(panel.getName())) {
				panel.setName(info.getName());
			}
			
			ResourceManager.releaseAll(panel.getName());
			
			add(panel);
		}
	}
}
