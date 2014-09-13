package com.sds.metac.ui;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.sds.metac.ui.swing.UICreatorSwing;
import com.sds.metac.ui.swing.resource.ResourceCreator;
import com.sds.metac.ui.swing.resource.ResourceManager;

public class CsvUICreator implements UICreatorSwing {
	
	@Override
	public JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setName("csvPanel");
		
		drawLayer(panel);
		setData();
		
		
		return panel;
	}

	private void setData() {
		
	}

	private void drawLayer(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ResourceCreator creator = ResourceManager.getCreator(panel.getName());
		creator.setHeight(40);
		creator.setWidth(600);
		creator.setTextFieldSize(30);
		
		panel.add(creator.createRow("forder", "forder", ResourceCreator.OPT_TEXT_FIELD));
		panel.add(creator.createRow("fileNameWord", "fileNameWord", ResourceCreator.OPT_TEXT_FIELD));
		panel.add(creator.createRow("fileNameCode", "fileNameCode", ResourceCreator.OPT_TEXT_FIELD));
		
		panel.add(creator.createRow("inputType", "inputType", ResourceCreator.OPT_TEXT_FIELD));
		panel.add(creator.createRow("splitter", "splitter", ResourceCreator.OPT_TEXT_FIELD));
		panel.add(creator.createRow("encoding", "encoding", ResourceCreator.OPT_TEXT_FIELD));
	}
}
