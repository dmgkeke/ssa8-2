package com.sds.metac.ui.swing.frame;

import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET_BUTTON;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET_CONTENT;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET_CONTENT_ROW;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET_LIST;
import static com.sds.metac.ui.constant.UIConstants.HEIGHT_POPUP_SET_LIST_BOTTOM;
import static com.sds.metac.ui.constant.UIConstants.LENGTH_POPUP_SET_INPUT;
import static com.sds.metac.ui.constant.UIConstants.NAME_FRAME_POPUP_SET;
import static com.sds.metac.ui.constant.UIConstants.WIDTH_POPUP_SET;
import static com.sds.metac.ui.constant.UIConstants.WIDTH_POPUP_SET_CONTENT;
import static com.sds.metac.ui.constant.UIConstants.WIDTH_POPUP_SET_LIST;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.schema.information.ClassInformationType;
import com.sds.metac.ui.config.UIConfigManager;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.button.PopupSetDelButtonHandler;
import com.sds.metac.ui.swing.event.button.PopupSetNewButtonHandler;
import com.sds.metac.ui.swing.event.button.PopupSetSaveButtonHandler;
import com.sds.metac.ui.swing.event.list.PopupSetListHandler;
import com.sds.metac.ui.swing.event.window.PopupSetWindowHandler;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceCreator;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.core.ClassInfoVO;

@SuppressWarnings("serial")
public class PopupSetFrame extends AbstractModalPopup {
	
	public static final String LIST_ID = "listInfos";

	public PopupSetFrame() {		
		setName(NAME_FRAME_POPUP_SET);
		setTitle(Message.get("message.button.core.set"));
		
		setSize(WIDTH_POPUP_SET, HEIGHT_POPUP_SET);
		int[] pos = UIConfigManager.getCenterPosition(WIDTH_POPUP_SET, HEIGHT_POPUP_SET);
		setLocation(pos[0], pos[1]);
				
		drawLayer();
		
		addListData();
		setContentData();
		
		CommonActionListener.addHandler(this, new PopupSetWindowHandler(), WindowListener.class);
	}
	
	@SuppressWarnings("unchecked")
	public void addListData() {
		JList<ListItem<ClassInfoVO>> list = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(LIST_ID);
		
		// ConfigManager에 등록된 모든 구성 그리기
		ConfigManager configManager = ConfigManager.INSTANCE;
		InformationVO informationVO = configManager.getInformation();
		
		DefaultListModel<ListItem<ClassInfoVO>> model = (DefaultListModel<ListItem<ClassInfoVO>>) list.getModel();
		model.removeAllElements();
		
		List<ListItem<ClassInfoVO>> items = new ArrayList<ListItem<ClassInfoVO>>();
		items.addAll(createItems(informationVO.getInputReaderInfoList(), ListItem.TAG_INPUT));
		items.addAll(createItems(informationVO.getOutputWriterInfoList(), ListItem.TAG_OUTPUT));
		items.addAll(createItems(informationVO.getPostProcessorInfoList(), ListItem.TAG_POST));
		
		for (ListItem<ClassInfoVO> item : items) {
			model.addElement(item);
		}
		
		list.setSelectedIndex(-1);
	}


	@SuppressWarnings("unchecked")
	public void setContentData() {
		JList<ListItem<ClassInfoVO>> list = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(LIST_ID);
		JComboBox<ComboItem> setComboBox = (JComboBox<ComboItem>) ResourceManager.get("comboSet");
		JTextField setNameTextField = (JTextField) ResourceManager.get("setName");
		JTextField classNameTextField = (JTextField) ResourceManager.get("className");
		JTextField uiClassNameTextField = (JTextField) ResourceManager.get("uiClassName");
		JTextField classFilePathTextField = (JTextField) ResourceManager.get("classFilePath");
		JButton popupSaveButton = (JButton) ResourceManager.get("popupSaveButton");
		
		ListItem<ClassInfoVO> selectedItem = list.getSelectedValue();
		
		if (setComboBox.getItemCount() <= 0) {
			setComboBox.addItem(new ComboItem(ClassInformationType.INPUT.value(), ListItem.TAG_INPUT));
			setComboBox.addItem(new ComboItem(ClassInformationType.OUTPUT.value(), ListItem.TAG_OUTPUT));
			setComboBox.addItem(new ComboItem(ClassInformationType.POST_PROC.value(), ListItem.TAG_POST));
		}
		
		
		// 데이터가 없을때
		if (list == null
			|| selectedItem == null
			) {
			setComboBox.setSelectedIndex(0);
			setComboBox.setEnabled(false);
			
			setNameTextField.setText(StringUtil.EMPTY);
			setNameTextField.setEnabled(false);
			
			classNameTextField.setText(StringUtil.EMPTY);
			classNameTextField.setEnabled(false);
			
			uiClassNameTextField.setText(StringUtil.EMPTY);
			uiClassNameTextField.setEnabled(false);
			
			classFilePathTextField.setText(StringUtil.EMPTY);
			classFilePathTextField.setEnabled(false);
			
			popupSaveButton.setEnabled(false);
		}
		// 선택된 데이터가 있을때
		else {
			ClassInfoVO vo = selectedItem.getValue();
			setComboBox.setEnabled(true);
			setNameTextField.setEnabled(true);
			classNameTextField.setEnabled(true);
			uiClassNameTextField.setEnabled(true);
			classFilePathTextField.setEnabled(true);
			
			if (vo != null) {
				setComboBox.setSelectedItem(new ComboItem(null, selectedItem.getTag()));
				setNameTextField.setText(vo.getName());
				classNameTextField.setText(vo.getClassName());
				uiClassNameTextField.setText(vo.getUiClassName());
				classFilePathTextField.setText(vo.getClassFilePath());
			}
			
			popupSaveButton.setEnabled(true);
		}
	}


	private void drawLayer() {
		this.setLayout(new BorderLayout());
		
		// 좌측 리스트 그리기
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setPreferredSize(new Dimension(WIDTH_POPUP_SET_LIST, HEIGHT_POPUP_SET_LIST));
		drawList(listPanel);		
		this.add(listPanel, BorderLayout.WEST);
		
		// 우측 컨텐트 그리기
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(WIDTH_POPUP_SET_CONTENT, HEIGHT_POPUP_SET_CONTENT));
		drawContent(contentPanel);		
		this.add(contentPanel, BorderLayout.CENTER);
		
	}

	private void drawList(JPanel listPanel) {
		ResourceCreator creator = ResourceManager.getCreator(getName());
		
		JList<ListItem<ClassInfoVO>> implList = creator.createList(LIST_ID);	
		CommonActionListener.addHandler(implList, new PopupSetListHandler(), MouseListener.class);
		JScrollPane scroller = new JScrollPane(implList);
		
		listPanel.add(scroller, BorderLayout.CENTER);
		
		
		// 구성 버튼 그리기
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_POPUP_SET_LIST, HEIGHT_POPUP_SET_LIST_BOTTOM));
		
		Dimension buttonDimension = new Dimension(Integer.MAX_VALUE, (HEIGHT_POPUP_SET_LIST_BOTTOM-5)/2);
		
		JButton newButton = new JButton(Message.get("message.button.popup.new"));
		newButton.setMaximumSize(buttonDimension);
		CommonActionListener.addHandler(newButton, new PopupSetNewButtonHandler());
		buttonPanel.add(newButton);
				
		JButton delButton = new JButton(Message.get("message.button.popup.del"));
		delButton.setMaximumSize(buttonDimension);
		CommonActionListener.addHandler(delButton, new PopupSetDelButtonHandler());
		buttonPanel.add(delButton);
		
		listPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	private List<ListItem<ClassInfoVO>> createItems(List<ClassInfoVO> list, String tag) {
		List<ListItem<ClassInfoVO>> ret = new ArrayList<ListItem<ClassInfoVO>>();
		
		for (int i=0 ; list!=null && i<list.size() ; i++) {
			ClassInfoVO vo = list.get(i);
			
			ret.add(new ListItem<ClassInfoVO>(vo.getName(), tag, vo));
		}
		
		return ret;
	}

	private void drawContent(JPanel panel) {
		JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(WIDTH_POPUP_SET_CONTENT, HEIGHT_POPUP_SET_CONTENT));
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(contentPanel);
		panel.add(scroller, BorderLayout.CENTER);
		
		
		ResourceCreator creator = ResourceManager.getCreator(getName());
		creator.setWidth(WIDTH_POPUP_SET_CONTENT);
		creator.setHeight(HEIGHT_POPUP_SET_CONTENT_ROW);
		creator.setTextFieldSize(LENGTH_POPUP_SET_INPUT);
		
		contentPanel.add(creator.createRow("message.label.core.reader.name", "comboSet", ResourceCreator.OPT_COMBO_BOX));
		
		contentPanel.add(creator.createRow("message.label.popup.set.setName", "setName", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("message.label.popup.set.className", "className", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("message.label.popup.set.uiClassName", "uiClassName", ResourceCreator.OPT_TEXT_FIELD));
		contentPanel.add(creator.createRow("message.label.popup.set.classFilePath", "classFilePath", ResourceCreator.OPT_TEXT_FIELD));
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_POPUP_SET_CONTENT, HEIGHT_POPUP_SET_BUTTON));
		
		JButton saveButton = new JButton(Message.get("message.button.popup.save"));
		ResourceManager.register(getName(), "popupSaveButton", saveButton);
		CommonActionListener.addHandler(saveButton, new PopupSetSaveButtonHandler());
		buttonPanel.add(saveButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
	}
}
