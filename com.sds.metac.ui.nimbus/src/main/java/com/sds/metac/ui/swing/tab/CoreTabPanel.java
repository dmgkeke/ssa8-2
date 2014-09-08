package com.sds.metac.ui.swing.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.button.NewSetButtonHandler;
import com.sds.metac.ui.swing.event.button.SaveButtonHandler;
import com.sds.metac.ui.swing.util.ResourceCreateUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.config.UserSettingVO;

@SuppressWarnings("serial")
public class CoreTabPanel extends JPanel {
	
	private ConfigManager configManager = ConfigManager.INSTANCE;
	
	public CoreTabPanel() {
		setName("corePanel");
				
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(600, 300));
		
		drawContent(contentPanel);
		
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(contentPanel);
		this.add(scroller, BorderLayout.CENTER);
		
		
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		buttonPanel.setLayout(flowLayout);
		buttonPanel.setPreferredSize(new Dimension(600, 35));
		
		drawButton(buttonPanel);
		this.add(buttonPanel, BorderLayout.NORTH);
	}
	

	private void drawButton(JPanel buttonPanel) {
		JButton setButton = new JButton(Message.get("message.button.core.set"));
		CommonActionListener.addHandler(setButton, new NewSetButtonHandler());
		buttonPanel.add(setButton);
		
		JButton saveButton = new JButton(Message.get("message.button.core.save"));
		CommonActionListener.addHandler(saveButton, new SaveButtonHandler());
		buttonPanel.add(saveButton);
	}

	private void drawContent(JPanel panel) {
		InformationVO informationVO = configManager.getInformation();
		UserSettingVO userSettingVO = configManager.getUserSetting();
		
		JTextField locImplTextField = ResourceCreateUtil.createTextField(getName(), "inputLocImpl", userSettingVO.getImplementationFolder(), 30);
		ResourceCreateUtil.addRow(panel, "message.label.core.location.impl", locImplTextField, 600, 40);
		
		JTextField locTempTextField = ResourceCreateUtil.createTextField(getName(), "inputLocTemp", userSettingVO.getTempFileFolder(), 30);
		ResourceCreateUtil.addRow(panel, "message.label.core.location.temp", locTempTextField, 600, 40);
		
		JTextField extTempTextField = ResourceCreateUtil.createTextField(getName(), "inputExtTemp", userSettingVO.getTempFileExt(), 30);
		ResourceCreateUtil.addRow(panel, "message.label.core.ext.temp", extTempTextField, 600, 40);
		
		JComboBox<?> readerComboBox = ResourceCreateUtil.createComboBox(getName(), "comboReader", informationVO.getInputReaderInfoList(), userSettingVO.getInputReaderName());
		ResourceCreateUtil.addRow(panel, "message.label.core.reader.name", readerComboBox, 600, 40);
		
		JComboBox<?> writerComboBox = ResourceCreateUtil.createComboBox(getName(), "comboWriter", informationVO.getOutputWriterInfoList(), userSettingVO.getOutputWriterName());
		ResourceCreateUtil.addRow(panel, "message.label.core.writer.name", writerComboBox, 600, 40);
		
		JComboBox<?> postComboBox = ResourceCreateUtil.createComboBox(getName(), "comboPost", informationVO.getPostProcessorInfoList(), userSettingVO.getPostProcessorName(), true);
		ResourceCreateUtil.addRow(panel, "message.label.core.post.name", postComboBox, 600, 40);
				
		JFormattedTextField cacheSizeFormatTextField = ResourceCreateUtil.createNumberFormatedTextField(getName(), "inputCacheSize", userSettingVO.getMaxCacheSize(), 30);
		
		ResourceCreateUtil.addRow(panel, "message.label.core.cache.maxSize", cacheSizeFormatTextField, 600, 40);
	}
}
