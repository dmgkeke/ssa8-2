package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.specific.CommonDefaultHandler;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.ui.swing.tab.MainTab;
import com.sds.metac.vo.config.UserSettingVO;

public class MainSaveButtonHandler extends CommonDefaultHandler {
	@SuppressWarnings("unchecked")
	@Override
	public void invoke(AWTEvent e) {
		boolean isSaved = true;
		try {
			JTextField inputLocImpl = (JTextField) ResourceManager.get("inputLocImpl");
			JTextField inputLocTemp = (JTextField) ResourceManager.get("inputLocTemp");
			JTextField inputExtTemp = (JTextField) ResourceManager.get("inputExtTemp");
			JComboBox<ComboItem> comboReader = (JComboBox<ComboItem>) ResourceManager.get("comboReader"); 
			JComboBox<ComboItem> comboWriter = (JComboBox<ComboItem>) ResourceManager.get("comboWriter"); 
			JComboBox<ComboItem> comboPost = (JComboBox<ComboItem>) ResourceManager.get("comboPost"); 
			JFormattedTextField inputCacheSize = (JFormattedTextField) ResourceManager.get("inputCacheSize");
			
			
			ConfigManager configManager = ConfigManager.INSTANCE;
			
			UserSettingVO userSettingVO = configManager.getUserSetting();
			
			userSettingVO.setImplementationFolder(inputLocImpl.getText());
			userSettingVO.setTempFileFolder(inputLocTemp.getText());
			userSettingVO.setTempFileExt(inputExtTemp.getText());
			userSettingVO.setInputReaderName(((ComboItem)comboReader.getSelectedItem()).getValue());
			userSettingVO.setOutputWriterName(((ComboItem)comboWriter.getSelectedItem()).getValue());
			userSettingVO.setPostProcessorName(((ComboItem)comboPost.getSelectedItem()).getValue());
			userSettingVO.setMaxCacheSize(Integer.valueOf(inputCacheSize.getText()));
			
			configManager.saveUserSetting();
		} catch (Exception ee) {
			isSaved = false;
		}
		
		String msg = "message.alert.save.fail";
		if (isSaved) {
			msg = "message.alert.save.success";
			
			MainTab mainTab = (MainTab) ResourceManager.get(UIConstants.NAME_TAB_MAIN);
			mainTab.recreateModuleTabs();
		}
		
		msg = Message.get(msg);
		JOptionPane.showMessageDialog(null, msg, "", JOptionPane.OK_OPTION);
	}
}
