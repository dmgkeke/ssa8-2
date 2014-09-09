package com.sds.metac.ui.swing.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.List;

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

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.vo.core.ClassInfoVO;

public class ResourceCreateUtil {

	public static JFormattedTextField createNumberFormatedTextField(String parentName, String name, String value, int defaultSize) {
		JFormattedTextField textField = new JFormattedTextField();
				
		textField.setColumns(defaultSize);
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(0);
		numberFormat.setMaximumIntegerDigits(3);
		
		DefaultFormatterFactory factory = new DefaultFormatterFactory();
		factory.setDefaultFormatter(new NumberFormatter(numberFormat));
		
		textField.setFormatterFactory(factory);
		
		textField.setText(value);
		
		ResourceManager.register(parentName, name, textField);
		
		return textField;
	}

	public static JTextField createTextField(String parentName, String name, String value, int defaultSize) {
		JTextField textField = new JTextField(defaultSize);
		
		textField.setName(name);
		
		textField.setText(value);
		
		ResourceManager.register(parentName, name, textField);
		
		return textField;
	}

	public static JComboBox<ComboItem> createComboBox(String parentName, String name, List<ComboItem> list, String selectedItem) {
		return createComboBox(parentName, name, list, selectedItem, false);
	}
	
	public static JComboBox<ComboItem> createComboBox(String parentName, String name, List<ComboItem> list, String selectedItem, boolean nullable) {
		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
		
		if (nullable) {
			comboBox.addItem(new ComboItem(Message.get("message.label.common.na"), ""));
		}
		
		if (list != null) {
			for (ComboItem vo : list) {
				comboBox.addItem(vo);
			}
		}
		
		if (selectedItem != null) {
			comboBox.setSelectedItem(new ComboItem(selectedItem, selectedItem));
		}
		
		comboBox.setName(name);
		
		ResourceManager.register(parentName, name, comboBox);
		
		return comboBox;
	}
	
	public static <T> JList<ListItem<T>> createList(String parentName, String name, List<ListItem<T>> items, int size) {
		
		
		JList<ListItem<T>> list = new JList<ListItem<T>>();
		
		DefaultListModel<ListItem<T>> model = new DefaultListModel<ListItem<T>>();
		list.setModel(model);
		
		if (items != null) {
			for (ListItem<T> item : items) {
				model.addElement(item);
			}			
		}
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(list);
		
		ResourceManager.register(parentName, name, list);
		
		return list;
	}

	public static void addRow(JPanel target, String messageCode, JComponent component, int width, int height) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(width, height));
		target.add(panel);
				
		JPanel tagPanel = new JPanel();
		tagPanel.setLayout(new BorderLayout());
		tagPanel.setPreferredSize(new Dimension(UIConstants.ROW_LABEL_WIDTH, height));
		
		
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
