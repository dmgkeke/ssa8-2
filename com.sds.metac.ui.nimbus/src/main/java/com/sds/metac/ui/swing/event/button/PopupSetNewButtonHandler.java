package com.sds.metac.ui.swing.event.button;

import java.awt.AWTEvent;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.specific.CommonDefaultHandler;
import com.sds.metac.ui.swing.frame.PopupSetFrame;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.vo.core.ClassInfoVO;

public class PopupSetNewButtonHandler extends CommonDefaultHandler {

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(AWTEvent e) {
		JList<ListItem<ClassInfoVO>> list = (JList<ListItem<ClassInfoVO>>) ResourceManager.get(PopupSetFrame.LIST_ID);
		PopupSetFrame popupSet = (PopupSetFrame) ResourceManager.get(UIConstants.NAME_FRAME_POPUP_SET);
		
		if (popupSet == null) {
			return;
		}
		DefaultListModel<ListItem<ClassInfoVO>> model = (DefaultListModel<ListItem<ClassInfoVO>>) list.getModel();
		
		String id = Message.get("message.label.popup.set.newSet");
		
		int count = 0;
		for (Enumeration<ListItem<ClassInfoVO>> item = model.elements() ; item.hasMoreElements() ; ) {
			ListItem<ClassInfoVO> vo = item.nextElement();
			
			if (vo.toString().contains(id)) {
				count++;
			}
		}
		
		ListItem<ClassInfoVO> newItem = new ListItem<ClassInfoVO>((id + (count+1)), ListItem.TAG_INPUT, new ClassInfoVO());
		
		
		model.addElement(newItem);
		
		list.setSelectedIndex(model.getSize()-1);
		popupSet.setContentData();
		
	}

}
