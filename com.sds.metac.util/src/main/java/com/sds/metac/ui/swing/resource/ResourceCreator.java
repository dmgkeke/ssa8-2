package com.sds.metac.ui.swing.resource;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.model.ListItem;

public class ResourceCreator {
	
	private static final int WIDTH_ROW_LABEL = 100;
	
	
	private String curName;
	private int rowWidth = 600;
	private int rowHeight = 300;
	private int textFieldSize = 30;
	private ResourceCreator() {
	}
	ResourceCreator(String name) {
		this.curName = name;
	}
	
	public void setWidth(int width) {
		this.rowWidth = width;
	}
	public void setHeight(int height) {
		this.rowHeight = height;
	}
	public void setTextFieldSize(int size) {
		this.textFieldSize = size;
	}

	public JFormattedTextField createNumberFormatedTextField(String name) {
		JFormattedTextField textField = new JFormattedTextField();
				
		textField.setColumns(textFieldSize);
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(0);
		numberFormat.setMaximumIntegerDigits(3);
		
		DefaultFormatterFactory factory = new DefaultFormatterFactory();
		factory.setDefaultFormatter(new NumberFormatter(numberFormat));
		
		textField.setFormatterFactory(factory);
		
		return ResourceManager.register(curName, name, textField);
	}

	
	public JTextField createTextField(String name) {
		JTextField textField = new JTextField(textFieldSize);
		
		textField.setName(name);
		
		return ResourceManager.register(curName, name, textField);
	}

	public JComboBox<ComboItem> createComboBox(String name) {
		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
						
		comboBox.setName(name);
		
		return ResourceManager.register(curName, name, comboBox);
	}
	
	public <T> JList<ListItem<T>> createList(String name) {
		
		
		JList<ListItem<T>> list = new JList<ListItem<T>>();
		
		DefaultListModel<ListItem<T>> model = new DefaultListModel<ListItem<T>>();
		list.setModel(model);
				
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(list);
		
		return ResourceManager.register(curName, name, list);
	}

	private JPanel createRow(String labelText, JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(rowWidth, rowHeight));
				
		JPanel tagPanel = new JPanel();
		tagPanel.setLayout(new BorderLayout());
		tagPanel.setPreferredSize(new Dimension(WIDTH_ROW_LABEL, rowHeight));
		
		
		JLabel tag = new JLabel(labelText + " : ");
		tagPanel.add(tag);
		
		panel.add(tagPanel);
		
		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		valuePanel.add(component);
		
		panel.add(valuePanel);
		
		return panel;
	}
	
	public static final int OPT_TEXT_FIELD = 0x00001;
	public static final int OPT_COMBO_BOX = 0x00002;
	public static final int OPT_NUM_TEXT_FIELD = 0x00003;
	public JPanel createRow(String labelText, String name, int option) {
		JComponent component = null;
		switch (option) {
		case OPT_TEXT_FIELD :
			component = createTextField(name);
			break;
		case OPT_COMBO_BOX :
			component = createComboBox(name);
			break;
		case OPT_NUM_TEXT_FIELD :
			component = createNumberFormatedTextField(name);
			break;
		default :
			throw new MetaCException("Option 값이 틀렸습니다 : [" + option + "]");
		}
		return createRow(labelText, component);
	}
}
