package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.core.ClassInfoVO;

public class PopupSetDelButtonHandler implements CommonHandler {

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(AWTEvent e) {
		JList<ListItem<ClassInfoVO>> jlist = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(PopupSetFrame.LIST_ID);
		
		ListItem<ClassInfoVO> selItem = jlist.getSelectedValue();
		if (selItem == null) {
			return;
		}
		
		if (!isConfirm()) {
			return;
		}
		
		boolean isDeleted = false;
		try {
			delete(selItem);
			isDeleted = true;
		} catch (Exception ee) {
			if (ee instanceof MetaCException) {
				throw new MetaCException(ee);
			}
		}
		
		String msg = "message.alert.delete.fail";
		if (isDeleted) {
			msg ="message.alert.delete.success";
			
			PopupSetFrame popupSet = (PopupSetFrame) ResourceManager.get(UIConstants.POPUP_SET_FRAME_NAME);
			popupSet.addListData();
			popupSet.setData();
		}
		
		msg = Message.get(msg);
		JOptionPane.showMessageDialog(null, msg, "", JOptionPane.OK_OPTION);
	}

	private void delete(ListItem<ClassInfoVO> selItem) {
		ClassInfoVO selVO = selItem.getValue();
		String selTag = selItem.getTag();
		
		ConfigManager configManager = ConfigManager.INSTANCE;
		InformationVO information = configManager.getInformation();
		
		List<ClassInfoVO> list = null;
		if (ListItem.TAG_INPUT.equals(selTag)) {
			list = information.getInputReaderInfoList();
		}
		else if (ListItem.TAG_OUTPUT.equals(selTag)) {
			list = information.getOutputWriterInfoList();
		}
		else if (ListItem.TAG_POST.equals(selTag)) {
			list = information.getPostProcessorInfoList();
		}
		
		if (list == null) {
			throw new MetaCException("Tag정보가 잘못되었습니다 : [" + selTag + "]");
		}
		
		
		for (int i=0 ; i<list.size() ; i++) {
			if (list.get(i).getName().equals(selVO.getName())) {
				list.remove(i);
				break;
			}
		}
		
		configManager.saveInformation();
	}

	private boolean isConfirm() {
		return JOptionPane.showConfirmDialog(null, Message.get("message.confirm.popup.check.del"), "Confirm", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION;
	}

}
