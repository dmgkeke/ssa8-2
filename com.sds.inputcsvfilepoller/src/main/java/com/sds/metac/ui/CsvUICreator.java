package com.sds.metac.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sds.metac.config.InputCsvConfigManager;
import com.sds.metac.schema.Encoding;
import com.sds.metac.schema.InputConfig;
import com.sds.metac.schema.InputType;
import com.sds.metac.ui.swing.UICreatorSwing;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.resource.ResourceCreator;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class CsvUICreator implements UICreatorSwing {
	
	@Override
	public JPanel createPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setName("csvPanel");
		
		drawLayer(panel);
		setData();
		
		
		return panel;
	}

	@SuppressWarnings("unchecked")
	private void setData() {
		InputCsvConfigManager configManager = InputCsvConfigManager.INSTANCE;
		
		InputConfig inputConfig = configManager.getInputConfig();
		
		JTextField forderTextField = (JTextField) ResourceManager.get("forder");
		forderTextField.setText(inputConfig.getForder());
		
		JTextField fileNameWordTextField = (JTextField) ResourceManager.get("fileNameWord");
		fileNameWordTextField.setText(inputConfig.getFileNameWord());
		
		JTextField fileNameCodeTextField = (JTextField) ResourceManager.get("fileNameCode");
		fileNameCodeTextField.setText(inputConfig.getFileNameCode());
		
		JComboBox<ComboItem> inputTypeComboBox = (JComboBox<ComboItem>) ResourceManager.get("inputType");
		inputTypeComboBox.removeAllItems();
		List<ComboItem> inputTypeList = convertToComboItemList(InputType.values());
		for (ComboItem item : inputTypeList) {
			inputTypeComboBox.addItem(item);
		}
		inputTypeComboBox.setSelectedItem(new ComboItem(null, inputConfig.getInputType().name()));
		
		JTextField splitterTextField = (JTextField) ResourceManager.get("splitter");
		splitterTextField.setText(inputConfig.getSplitter());
		
		JComboBox<ComboItem> encodingComboBox = (JComboBox<ComboItem>) ResourceManager.get("encoding");
		encodingComboBox.removeAllItems();
		List<ComboItem> encodingList = convertToComboItemList(Encoding.values());
		for (ComboItem item : encodingList) {
			encodingComboBox.addItem(item);
		}
		encodingComboBox.setSelectedItem(new ComboItem(null, inputConfig.getEncoding().name()));
		
	}

	private void drawLayer(JPanel panel) {
		
		
		JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(600, 300));
		
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(contentPanel);
		
		panel.add(scroller, BorderLayout.CENTER);
		
		ResourceCreator creator = ResourceManager.getCreator(panel.getName());
		creator.setHeight(40);
		creator.setWidth(600);
		creator.setTextFieldSize(30);
		
		contentPanel.add(creator.createRow("forder", "forder", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("fileNameWord", "fileNameWord", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("fileNameCode", "fileNameCode", ResourceCreator.OPT_TEXT_FIELD));
		
		contentPanel.add(creator.createRow("inputType", "inputType", ResourceCreator.OPT_COMBO_BOX));
		contentPanel.add(creator.createRow("splitter", "splitter", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("encoding", "encoding", ResourceCreator.OPT_COMBO_BOX));
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(600, 35));
		
		JButton saveButton = new JButton("저장");
		saveButton.addActionListener(new SaveButtonListener());
		buttonPanel.add(saveButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private <T extends Enum<?>> List<ComboItem> convertToComboItemList(T[] list) {
		List<ComboItem> ret = new ArrayList<ComboItem>();
		
		for (Enum<?> vo : list) {
			ret.add(new ComboItem(vo.name(), vo.toString()));
		}
		
		return ret;
	}
}

class SaveButtonListener implements ActionListener {

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		InputCsvConfigManager configManager = InputCsvConfigManager.INSTANCE;
		InputConfig inputConfig = configManager.getInputConfig();
		
		JTextField forderTextField = (JTextField) ResourceManager.get("forder");
		inputConfig.setForder(forderTextField.getText());
		
		JTextField fileNameWordTextField = (JTextField) ResourceManager.get("fileNameWord");
		inputConfig.setFileNameWord(fileNameWordTextField.getText());
		
		JTextField fileNameCodeTextField = (JTextField) ResourceManager.get("fileNameCode");
		inputConfig.setFileNameCode(fileNameCodeTextField.getText());
		
		JComboBox<ComboItem> inputTypeComboBox = (JComboBox<ComboItem>) ResourceManager.get("inputType");
		inputConfig.setInputType(InputType.valueOf(inputTypeComboBox.getSelectedItem().toString()));
				
		JTextField splitterTextField = (JTextField) ResourceManager.get("splitter");
		inputConfig.setSplitter(splitterTextField.getText());
		
		JComboBox<ComboItem> encodingComboBox = (JComboBox<ComboItem>) ResourceManager.get("encoding");
		inputConfig.setEncoding(Encoding.valueOf(encodingComboBox.getSelectedItem().toString()));
		
		
		configManager.saveInputConfig();	
		JOptionPane.showMessageDialog(null, "저장되었습니다.", "", JOptionPane.OK_OPTION);
	}
}