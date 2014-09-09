package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.core.ClassInfoVO;

public class PopupSetSaveButtonHandler implements CommonHandler {

	
	@SuppressWarnings("unchecked")
	@Override
	public void invoke(AWTEvent e) {
		if (!isValid()) {
			return;
		}
		
		boolean isSaved = false;
		try {
			save();
			isSaved = true;
		} catch (Exception ee) {
			isSaved = false;
		}
		
		String msg = "message.alert.save.fail";
		if (isSaved) {
			msg = "message.alert.save.success";
			
			PopupSetFrame popupSet = (PopupSetFrame) ResourceManager.get(UIConstants.POPUP_SET_FRAME_NAME);
			JList<ListItem<ClassInfoVO>> jlist = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(PopupSetFrame.LIST_ID);
			JTextField setNameTextField = (JTextField) ResourceManager.get("setName");
			
			// 리스트 새로 그리고 현재꺼 선택
			popupSet.addListData();
			
			DefaultListModel<ListItem<ClassInfoVO>> model = (DefaultListModel<ListItem<ClassInfoVO>>) jlist.getModel();
			int selectedIndex = -1;
			int index=0;
			for (Enumeration<ListItem<ClassInfoVO>> item = model.elements() ; item.hasMoreElements() ; index++) {
				ListItem<ClassInfoVO> vo = item.nextElement();
				
				if (vo.toString().contains(setNameTextField.getText())) {
					selectedIndex = index;
					break;
				}
			}
			
			jlist.setSelectedIndex(selectedIndex);
		}
		
		msg = Message.get(msg);
		JOptionPane.showMessageDialog(null, msg, "", JOptionPane.OK_OPTION);
	}

	@SuppressWarnings("unchecked")
	private void save() {
		JList<ListItem<ClassInfoVO>> jlist = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(PopupSetFrame.LIST_ID);
		
		JTextField setNameTextField = (JTextField) ResourceManager.get("setName");
		JTextField classNameTextField = (JTextField) ResourceManager.get("className");
		JTextField classFilePathTextField = (JTextField) ResourceManager.get("classFilePath");
		
		JComboBox<ComboItem> combo = (JComboBox<ComboItem>) ResourceManager.get("comboSet");
		String newTag = ((ComboItem)combo.getSelectedItem()).getValue();
		
		
		ListItem<ClassInfoVO> orgItem = jlist.getSelectedValue();
		ClassInfoVO orgClassInfo = orgItem.getValue();		
		String orgTag = orgItem.getTag();
		
		ConfigManager configManager = ConfigManager.INSTANCE;
		InformationVO information = configManager.getInformation();
		
		// 원본이 존재하는지 확인 - 있으면 수정
		List<ClassInfoVO> list = getListByTag(orgTag, information);
		
		ClassInfoVO orgConfigVO = null;
		for (ClassInfoVO vo : list) {
			if (vo.getName().equals(orgClassInfo.getName())) {
				orgConfigVO = vo;
				break;
			}
		}
		// 찾았는데 없으면 추가
		if (orgConfigVO == null) {
			orgConfigVO = new ClassInfoVO();
			list.add(orgConfigVO);
		}
		
		// tag가 바뀌었으면, 해당 리스트로 이동
		if (!orgTag.equals(newTag)) {
			list.remove(orgConfigVO);
			
			list = getListByTag(newTag, information);
			list.add(orgConfigVO);
		}
		
		// 새로운 정보 세팅
		orgConfigVO.setName(setNameTextField.getText());
		orgConfigVO.setClassName(classNameTextField.getText());
		orgConfigVO.setClassFilePath(classFilePathTextField.getText());
		
		
		// 저장
		configManager.saveInformation();
	}

	private List<ClassInfoVO> getListByTag(String orgTag, InformationVO information) {
		List<ClassInfoVO> list = null;
		if (ListItem.TAG_INPUT.equals(orgTag)) {
			list = information.getInputReaderInfoList();
		}
		else if (ListItem.TAG_OUTPUT.equals(orgTag)) {
			list = information.getOutputWriterInfoList();
		}
		else if (ListItem.TAG_POST.equals(orgTag)) {
			list = information.getPostProcessorInfoList();
		}
		if (list == null) {
			throw new MetaCException("TAG정보가 잘못되었습니다 : [" + orgTag + "]");
		}
		return list;
	}

	private boolean isValid() {
		JTextField setNameTextField = (JTextField) ResourceManager.get("setName");
		JTextField classNameTextField = (JTextField) ResourceManager.get("className");
		JTextField classFilePathTextField = (JTextField) ResourceManager.get("classFilePath");
		
		if (StringUtil.isEmpty(setNameTextField.getText())) {
			return showError("message.alert.popup.check.setName", setNameTextField);
		}
		
		if (StringUtil.isEmpty(classNameTextField.getText())) {
			return showError("message.alert.popup.check.className", classNameTextField);
		}
		
		if (StringUtil.isEmpty(classFilePathTextField.getText())) {
			return showError("message.alert.popup.check.classFilePath", classFilePathTextField);
		}
		
		return true;
	}

	private boolean showError(String msg, JComponent comp) {
		JOptionPane.showMessageDialog(null, Message.get(msg), "Error", JOptionPane.ERROR_MESSAGE);
		comp.requestFocus();
		return false;
	}

}
