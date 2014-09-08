package com.sds.metac.ui.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;

import com.sds.metac.exception.MetaCException;

public enum CommonActionListener implements ActionListener {
	INSTANCE;	
	
	Map<Object, CommonHandler> handlerMap = new HashMap<Object, CommonHandler>();
	
	public static <T extends JComponent> void addHandler(T component, CommonHandler handler) {
	
		Method actionMethod = null;
		
		Queue<Class<?>> queue = new ConcurrentLinkedQueue<Class<?>>();
		queue.add(component.getClass());
		
		QUEING : while (!queue.isEmpty()) {
			Class<?> clazz = queue.poll();
			
			queue.add(clazz.getSuperclass());
			
			for (Method m : clazz.getDeclaredMethods()) {
				Class<?>[] types = m.getParameterTypes();
				if (types == null || types.length != 1) {
					continue;
				}
				
				if (types[0] != ActionListener.class) {
					continue;
				}
				
				actionMethod = m;
				break QUEING;
			}
		}
		
		
		if (actionMethod == null) {
			throw new MetaCException("ActionListener를 등록할 수 없습니다. class [" + component.getClass().getName()  + "]");
		}
		
		try {
			actionMethod.setAccessible(true);
			actionMethod.invoke(component, INSTANCE);
		} catch (Exception e) {
			throw new MetaCException("ActionListener를 등록할 수 없습니다. class [" + component.getClass().getName()  + "]", e);
		}
		
		INSTANCE.handlerMap.put(component, handler);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommonHandler handler = handlerMap.get(e.getSource());
		
		if (handler != null) {
			handler.invoke(e);
		}
	}
}
