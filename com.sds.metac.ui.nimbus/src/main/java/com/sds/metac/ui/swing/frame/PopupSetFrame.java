package com.sds.metac.ui.swing.frame;

import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_BUTTON_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_CONTENT_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_CONTENT_ROW_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_CONTENT_WIDTH;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_FRAME_NAME;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_INPUT_LENGTH;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_LIST_BOTTOM_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_LIST_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_LIST_LENGTH;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_LIST_WIDTH;
import static com.sds.metac.ui.constant.UIConstants.POPUP_SET_WIDTH;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.button.PopupSetDelButtonHandler;
import com.sds.metac.ui.swing.event.button.PopupSetNewButtonHandler;
import com.sds.metac.ui.swing.event.button.PopupSetSaveButtonHandler;
import com.sds.metac.ui.swing.event.list.PopupSetListHandler;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.ui.swing.tab.CoreTabPanel;
import com.sds.metac.ui.swing.util.ResourceCreateUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.core.ClassInfoVO;

@SuppressWarnings("serial")
public class PopupSetFrame extends AbstractModalPopup {
	
	public static final String LIST_ID = "listInfos";

	public PopupSetFrame() {		
		setName(POPUP_SET_FRAME_NAME);
		setTitle(Message.get("message.button.core.set"));
		
		setSize(POPUP_SET_WIDTH, POPUP_SET_HEIGHT);
		int[] pos = UIConfigManager.getCenterPosition(POPUP_SET_WIDTH, POPUP_SET_HEIGHT);
		setLocation(pos[0], pos[1]);
				
		drawLayer();
		
		addListData();
		setData();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CoreTabPanel coreTabPanel = (CoreTabPanel) ResourceManager.get(UIConstants.CORE_TAB_PANEL_NAME);
				coreTabPanel.setData();
			}
		});
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
	public void setData() {
		JList<ListItem<ClassInfoVO>> list = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(LIST_ID);
		JComboBox<?> setComboBox = (JComboBox<?>) ResourceManager.get("comboSet");
		JTextField setNameTextField = (JTextField) ResourceManager.get("setName");
		JTextField classNameTextField = (JTextField) ResourceManager.get("className");
		JTextField classFilePathTextField = (JTextField) ResourceManager.get("classFilePath");
		JButton popupSaveButton = (JButton) ResourceManager.get("popupSaveButton");
		
		ListItem<ClassInfoVO> selectedItem = list.getSelectedValue();
		
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
			classFilePathTextField.setEnabled(true);
			
			if (vo != null) {
				setComboBox.setSelectedItem(new ComboItem(null, selectedItem.getTag()));
				setNameTextField.setText(vo.getName());
				classNameTextField.setText(vo.getClassName());
				classFilePathTextField.setText(vo.getClassFilePath());
			}
			
			popupSaveButton.setEnabled(true);
		}
	}


	private void drawLayer() {
		this.setLayout(new BorderLayout());
		
		// 좌측 리스트 그리기
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setPreferredSize(new Dimension(POPUP_SET_LIST_WIDTH, POPUP_SET_LIST_HEIGHT));
		drawList(listPanel);		
		this.add(listPanel, BorderLayout.WEST);
		
		// 우측 컨텐트 그리기
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_HEIGHT));
		drawContent(contentPanel);		
		this.add(contentPanel, BorderLayout.CENTER);
		
	}

	private void drawList(JPanel listPanel) {
		JList<ListItem<ClassInfoVO>> implList = ResourceCreateUtil.createList(getName(), LIST_ID, null, POPUP_SET_LIST_LENGTH);	
		CommonActionListener.addHandler(implList, new PopupSetListHandler(), MouseListener.class);
		JScrollPane scroller = new JScrollPane(implList);
		
		listPanel.add(scroller, BorderLayout.CENTER);
		
		
		// 구성 버튼 그리기
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setPreferredSize(new Dimension(POPUP_SET_LIST_WIDTH, POPUP_SET_LIST_BOTTOM_HEIGHT));
		
		Dimension buttonDimension = new Dimension(Integer.MAX_VALUE, (POPUP_SET_LIST_BOTTOM_HEIGHT-5)/2);
		
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
		contentPanel.setPreferredSize(new Dimension(POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_HEIGHT));
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(contentPanel);
		panel.add(scroller, BorderLayout.CENTER);
		
		// 구성 종류 설정
		List<ComboItem> list = new ArrayList<ComboItem>();
		list.add(new ComboItem(ClassInformationType.INPUT.value(), ListItem.TAG_INPUT));
		list.add(new ComboItem(ClassInformationType.OUTPUT.value(), ListItem.TAG_OUTPUT));
		list.add(new ComboItem(ClassInformationType.POST_PROC.value(), ListItem.TAG_POST));
		
		JComboBox<?> setComboBox = ResourceCreateUtil.createComboBox(getName(), "comboSet", list, null);
		ResourceCreateUtil.addRow(contentPanel, "message.label.core.reader.name", setComboBox, POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_ROW_HEIGHT);
		
		// 구성이름
		JTextField setNameTextField = ResourceCreateUtil.createTextField(getName(), "setName", StringUtil.EMPTY, POPUP_SET_INPUT_LENGTH);
		ResourceCreateUtil.addRow(contentPanel, "message.label.popup.set.setName", setNameTextField, POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_ROW_HEIGHT);
		
		// 클래스이름
		JTextField classNameTextField = ResourceCreateUtil.createTextField(getName(), "className", StringUtil.EMPTY, POPUP_SET_INPUT_LENGTH);
		ResourceCreateUtil.addRow(contentPanel, "message.label.popup.set.className", classNameTextField, POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_ROW_HEIGHT);
		
		// 클래스 위치
		JTextField classFilePathTextField = ResourceCreateUtil.createTextField(getName(), "classFilePath", StringUtil.EMPTY, POPUP_SET_INPUT_LENGTH);
		ResourceCreateUtil.addRow(contentPanel, "message.label.popup.set.classFilePath", classFilePathTextField, POPUP_SET_CONTENT_WIDTH, POPUP_SET_CONTENT_ROW_HEIGHT);
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setPreferredSize(new Dimension(POPUP_SET_CONTENT_WIDTH, POPUP_SET_BUTTON_HEIGHT));
		
		JButton saveButton = new JButton(Message.get("message.button.popup.save"));
		ResourceManager.register(getName(), "popupSaveButton", saveButton);
		CommonActionListener.addHandler(saveButton, new PopupSetSaveButtonHandler());
		buttonPanel.add(saveButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
	}
}
