package com.sds.metac.ui.swing.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.ui.constant.UIConstants;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.model.ListItem;
import com.sds.metac.ui.swing.util.ResourceCreateUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.core.ClassInfoVO;



@SuppressWarnings("serial")
public class PopupSetFrame extends AbstractModalPopup {
	
	public PopupSetFrame() {		
		setName(UIConstants.POPUP_SET_FRAME_NAME);
		setTitle(Message.get("message.button.core.set"));
		
		setSize(640, 480);
				
		drawLayer();
		
		setVisible(true);
	}

	private void drawLayer() {
		this.setLayout(new BorderLayout());
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setPreferredSize(new Dimension(200, 480));
		drawList(listPanel);		
		this.add(listPanel, BorderLayout.WEST);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(440, 480));
		drawContent(contentPanel);
		
		this.add(contentPanel, BorderLayout.CENTER);
		
	}

	private void drawList(JPanel listPanel) {
		ConfigManager configManager = ConfigManager.INSTANCE;
		InformationVO informationVO = configManager.getInformation();
		
		
		List<ListItem<ClassInfoVO>> items = new ArrayList<ListItem<ClassInfoVO>>(); 
		
		items.addAll(createItems(informationVO.getInputReaderInfoList(), ListItem.TAG_INPUT));
		items.addAll(createItems(informationVO.getOutputWriterInfoList(), ListItem.TAG_OUTPUT));
		items.addAll(createItems(informationVO.getPostProcessorInfoList(), ListItem.TAG_POST));
		
		JList<ListItem<ClassInfoVO>> implList = ResourceCreateUtil.createList(getName(), "listInfos", items, 30);
		JScrollPane scroller = new JScrollPane(implList);
		
		listPanel.add(scroller, BorderLayout.CENTER);
		
		
		JSeparator seperator = new JSeparator(JSeparator.VERTICAL);
		listPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		listPanel.add(seperator, BorderLayout.LINE_END);
	}

	private List<ListItem<ClassInfoVO>> createItems(List<ClassInfoVO> list, String tag) {
		List<ListItem<ClassInfoVO>> ret = new ArrayList<ListItem<ClassInfoVO>>();
		
		for (int i=0 ; list!=null && i<list.size() ; i++) {
			ClassInfoVO vo = list.get(i);
			
			ret.add(new ListItem<ClassInfoVO>(tag + StringUtil.SPACE + vo.getName(), tag, vo));
		}
		
		return ret;
	}

	private void drawContent(JPanel panel) {
		JTextField locImplTextField = ResourceCreateUtil.createTextField(getName(), "test", "test", 30);
		ResourceCreateUtil.addRow(panel, "message.label.core.location.impl", locImplTextField, 600, 40);
	}
}
