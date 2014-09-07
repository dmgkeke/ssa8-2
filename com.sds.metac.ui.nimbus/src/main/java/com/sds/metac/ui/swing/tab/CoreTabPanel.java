package com.sds.metac.ui.swing.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.ui.message.Message;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.ClassInfoVO;

@SuppressWarnings("serial")
public class CoreTabPanel extends JPanel {
	
	private ConfigManager configManager = ConfigManager.INSTANCE;
	
	public CoreTabPanel() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(600, 450));
		
		drawContent(contentPanel);
		
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT, 10, 10);
		buttonPanel.setLayout(flowLayout);
		contentPanel.setPreferredSize(new Dimension(600, 30));
		
		drawButton(buttonPanel);
		
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void drawButton(JPanel buttonPanel) {
		JButton saveButton = new JButton("저장");
		
		buttonPanel.add(saveButton);
	}

	private void drawContent(JPanel panel) {
//		CommonActionListener listener = CommonActionListener.INSTANCE;
		InformationVO informationVO = configManager.getInformation();
		UserSettingVO userSettingVO = configManager.getUserSetting();
		
		JTextField locImplTextField = createTextField(userSettingVO.getImplementationFolder(), 30);
		addRow(panel, "message.label.core.location.impl", locImplTextField, 600, 40);
		
		JTextField locTempTextField = createTextField(userSettingVO.getTempFileFolder(), 30);
		addRow(panel, "message.label.core.location.temp", locTempTextField, 600, 40);
		
		JTextField extTempTextField = createTextField(userSettingVO.getTempFileExt(), 30);
		addRow(panel, "message.label.core.ext.temp", extTempTextField, 600, 40);
		
		JComboBox<?> readerComboBox = createComboBox(informationVO.getInputReaderInfoList(), userSettingVO.getInputReaderName());
		addRow(panel, "message.label.core.reader.name", readerComboBox, 600, 40);
		
		JComboBox<?> writerComboBox = createComboBox(informationVO.getOutputWriterInfoList(), userSettingVO.getOutputWriterName());
		addRow(panel, "message.label.core.writer.name", writerComboBox, 600, 40);
		
		JComboBox<?> postComboBox = createComboBox(informationVO.getPostProcessorInfoList(), userSettingVO.getPostProcessorName(), true);
		addRow(panel, "message.label.core.post.name", postComboBox, 600, 40);
				
		JFormattedTextField cacheSizeFormatTextField = createNumberFormatedTextField(userSettingVO.getMaxCacheSize(), 30);
		
		addRow(panel, "message.label.core.cache.maxSize", cacheSizeFormatTextField, 600, 40);
	}

	private JFormattedTextField createNumberFormatedTextField(Integer maxCacheSize, int defaultSize) {
		JFormattedTextField textField = new JFormattedTextField();
				
		textField.setColumns(defaultSize);
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(0);
		numberFormat.setMaximumIntegerDigits(3);
		
		DefaultFormatterFactory factory = new DefaultFormatterFactory();
		factory.setDefaultFormatter(new NumberFormatter(numberFormat));
		
		textField.setFormatterFactory(factory);
		
		textField.setText(maxCacheSize.toString());
		
		return textField;
	}

	private JTextField createTextField(String value, int defaultSize) {
		JTextField textField = new JTextField(defaultSize);
		
		textField.setText(value);
		
		return textField;
	}

	private JComboBox<ComboItem> createComboBox(List<ClassInfoVO> list, String selectedItem) {
		return createComboBox(list, selectedItem, false);
	}
	
	private JComboBox<ComboItem> createComboBox(List<ClassInfoVO> list, String selectedItem, boolean nullable) {
		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
		
		if (nullable) {
			comboBox.addItem(new ComboItem(Message.get("message.label.common.na"), ""));
		}
		
		if (list != null) {
			for (ClassInfoVO readerVO : list) {
				comboBox.addItem(new ComboItem(readerVO.getName(), readerVO.getName()));
			}
		}
		
		comboBox.setSelectedItem(new ComboItem(selectedItem, selectedItem));
		
		
		
		
		return comboBox;
	}

	private void addRow(JPanel target, String messageCode, JComponent component, int width, int height) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(width, height));
		add(panel);
				
		JPanel tagPanel = new JPanel();
		tagPanel.setLayout(new BorderLayout());
		tagPanel.setPreferredSize(new Dimension(100, height));
		
		
		JLabel tag = new JLabel(Message.get(messageCode) + " : ");
		tagPanel.add(tag);
		
		panel.add(tagPanel);
		
		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		valuePanel.add(component);
		
		panel.add(valuePanel);
		
		
		target.add(panel);
	}
}

class ComboItem {

	private String text;
	private String value;

	public ComboItem(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null
			&& this.value != null
			&& obj instanceof ComboItem) {
			return this.value.equals(((ComboItem)obj).getValue());
		}
		return false;
	}
}
