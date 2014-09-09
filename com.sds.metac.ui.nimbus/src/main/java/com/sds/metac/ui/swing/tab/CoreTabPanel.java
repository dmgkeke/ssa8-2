package com.sds.metac.ui.swing.tab;

import static com.sds.metac.ui.constant.UIConstants.MAIN_BUTTON_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.MAIN_CONTENT_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.MAIN_CONTENT_INPUT_LENGTH;
import static com.sds.metac.ui.constant.UIConstants.MAIN_CONTENT_ROW_HEIGHT;
import static com.sds.metac.ui.constant.UIConstants.MAIN_CONTENT_WIDTH;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.ui.message.Message;
import com.sds.metac.ui.swing.event.CommonActionListener;
import com.sds.metac.ui.swing.event.button.MainNewSetButtonHandler;
import com.sds.metac.ui.swing.event.button.MainSaveButtonHandler;
import com.sds.metac.ui.swing.model.ComboItem;
import com.sds.metac.ui.swing.resource.ResourceManager;
import com.sds.metac.ui.swing.util.ResourceCreateUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.InformationVO;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.ClassInfoVO;

@SuppressWarnings("serial")
public class CoreTabPanel extends JPanel {
	
	private ConfigManager configManager = ConfigManager.INSTANCE;
	
	public CoreTabPanel() {
		setName("corePanel");
				
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		// 본문 그리기
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(MAIN_CONTENT_WIDTH, MAIN_CONTENT_HEIGHT));
		
		drawContent(contentPanel);
		setData();
		
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(contentPanel);
		this.add(scroller, BorderLayout.CENTER);
		
		// 상단 버튼 그리기
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(MAIN_CONTENT_WIDTH, MAIN_BUTTON_HEIGHT));
		
		drawButton(buttonPanel);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	

	@SuppressWarnings("unchecked")
	public void setData() {
		InformationVO informationVO = configManager.getInformation();
		UserSettingVO userSettingVO = configManager.getUserSetting();
		
		JTextField locImplTextField = (JTextField) ResourceManager.get("inputLocImpl");
		locImplTextField.setText(userSettingVO.getImplementationFolder());		
		
		JTextField locTempTextField = (JTextField) ResourceManager.get("inputLocTemp");
		locTempTextField.setText(userSettingVO.getTempFileFolder());
		
		JTextField extTempTextField = (JTextField) ResourceManager.get("inputExtTemp");
		extTempTextField.setText(userSettingVO.getTempFileExt());
		
		JComboBox<ComboItem> readerComboBox = (JComboBox<ComboItem>) ResourceManager.get("comboReader");
		readerComboBox.removeAllItems();
		List<ComboItem> readerList = convertToComboItemList(informationVO.getInputReaderInfoList());
		for (ComboItem item : readerList) {
			readerComboBox.addItem(item);
		}
		readerComboBox.setSelectedItem(new ComboItem(null, userSettingVO.getInputReaderName()));
		
		JComboBox<ComboItem> writerComboBox = (JComboBox<ComboItem>) ResourceManager.get("comboWriter");
		writerComboBox.removeAllItems();
		List<ComboItem> writerList = convertToComboItemList(informationVO.getOutputWriterInfoList());
		for (ComboItem item : writerList) {
			writerComboBox.addItem(item);
		}
		writerComboBox.setSelectedItem(new ComboItem(null, userSettingVO.getOutputWriterName()));
		
		JComboBox<ComboItem> postComboBox = (JComboBox<ComboItem>) ResourceManager.get("comboPost");
		postComboBox.removeAllItems();
		List<ComboItem> postList = convertToComboItemList(informationVO.getPostProcessorInfoList());
		for (ComboItem item : postList) {
			postComboBox.addItem(item);
		}
		postComboBox.setSelectedItem(new ComboItem(null, userSettingVO.getPostProcessorName()));
		
		JFormattedTextField cacheSizeFormatTextField = (JFormattedTextField) ResourceManager.get("inputCacheSize");
		cacheSizeFormatTextField.setText(StringUtil.toString(userSettingVO.getMaxCacheSize()));
	}


	private void drawButton(JPanel buttonPanel) {
		JButton setButton = new JButton(Message.get("message.button.core.set"));
		CommonActionListener.addHandler(setButton, new MainNewSetButtonHandler());
		buttonPanel.add(setButton);
		
		JButton saveButton = new JButton(Message.get("message.button.core.save"));
		CommonActionListener.addHandler(saveButton, new MainSaveButtonHandler());
		buttonPanel.add(saveButton);
	}

	private void drawContent(JPanel panel) {
		JTextField locImplTextField = ResourceCreateUtil.createTextField(getName(), "inputLocImpl", StringUtil.EMPTY, MAIN_CONTENT_INPUT_LENGTH);
		ResourceCreateUtil.addRow(panel, "message.label.core.location.impl", locImplTextField, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
		
		JTextField locTempTextField = ResourceCreateUtil.createTextField(getName(), "inputLocTemp", StringUtil.EMPTY, MAIN_CONTENT_INPUT_LENGTH);
		ResourceCreateUtil.addRow(panel, "message.label.core.location.temp", locTempTextField, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
		
		JTextField extTempTextField = ResourceCreateUtil.createTextField(getName(), "inputExtTemp", StringUtil.EMPTY, MAIN_CONTENT_INPUT_LENGTH);
		ResourceCreateUtil.addRow(panel, "message.label.core.ext.temp", extTempTextField, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
		
		JComboBox<?> readerComboBox = ResourceCreateUtil.createComboBox(getName(), "comboReader", null, null);
		ResourceCreateUtil.addRow(panel, "message.label.core.reader.name", readerComboBox, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
		
		JComboBox<?> writerComboBox = ResourceCreateUtil.createComboBox(getName(), "comboWriter", null, null);
		ResourceCreateUtil.addRow(panel, "message.label.core.writer.name", writerComboBox, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
		
		JComboBox<?> postComboBox = ResourceCreateUtil.createComboBox(getName(), "comboPost", null, null, true);
		ResourceCreateUtil.addRow(panel, "message.label.core.post.name", postComboBox, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
				
		JFormattedTextField cacheSizeFormatTextField = ResourceCreateUtil.createNumberFormatedTextField(getName(), "inputCacheSize", StringUtil.EMPTY, MAIN_CONTENT_INPUT_LENGTH);
		
		ResourceCreateUtil.addRow(panel, "message.label.core.cache.maxSize", cacheSizeFormatTextField, MAIN_CONTENT_WIDTH, MAIN_CONTENT_ROW_HEIGHT);
	}
	
	private List<ComboItem> convertToComboItemList(List<ClassInfoVO> list) {
		List<ComboItem> ret = new ArrayList<ComboItem>();
		
		for (ClassInfoVO vo : list) {
			ret.add(new ComboItem(vo.getName(), vo.getName()));
		}
		
		return ret;
	}
}
