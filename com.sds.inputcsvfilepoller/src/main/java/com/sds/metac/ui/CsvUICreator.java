package com.sds.metac.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sds.metac.ui.swing.UICreatorSwing;

public class CsvUICreator implements UICreatorSwing {
	
	@Override
	public JPanel createPanel() {
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("이거 되나"));
		
		return panel;
	}
}
