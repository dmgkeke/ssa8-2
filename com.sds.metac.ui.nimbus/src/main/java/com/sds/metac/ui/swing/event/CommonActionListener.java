package com.sds.metac.ui.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public enum CommonActionListener implements ActionListener {
	INSTANCE;	
	
	Map<Object, CommonHandler> handlerMap = new HashMap<Object, CommonHandler>();	
	public <T extends JComponent> CommonActionListener addHandler(T component, CommonHandler handler) {
		handlerMap.put(component, handler);
		return this;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommonHandler handler = handlerMap.get(e.getSource());
		
		if (handler != null) {
			handler.invoke(e);
		}
	}
}
