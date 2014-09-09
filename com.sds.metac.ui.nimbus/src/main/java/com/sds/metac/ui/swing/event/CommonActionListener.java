package com.sds.metac.ui.swing.event;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;

import com.sds.metac.exception.MetaCException;

public enum CommonActionListener implements ActionListener, MouseListener {
	INSTANCE;	
	
	Map<Object, CommonHandler> handlerMap = new HashMap<Object, CommonHandler>();
	
	public static <T extends JComponent, E extends EventListener> void addHandler(T component, CommonHandler handler, Class<E> listenrClazz) {
		if (listenrClazz == null) {
			listenrClazz = (Class<E>) ActionListener.class;
		}
		
		Method selectedMethod = null;
		
		Queue<Class<?>> queue = new ConcurrentLinkedQueue<Class<?>>();
		queue.add(component.getClass());
		
		QUEING : while (!queue.isEmpty()) {
			Class<?> clazz = queue.poll();
			
			if (clazz.getSuperclass() != null){
				queue.add(clazz.getSuperclass());
			}
			
			for (Method m : clazz.getDeclaredMethods()) {
				Class<?>[] types = m.getParameterTypes();
				if (types == null || types.length != 1) {
					continue;
				}
				
				if (types[0] != listenrClazz) {
					continue;
				}
				
				selectedMethod = m;
				break QUEING;
			}
		}
		
		
		if (selectedMethod == null) {
			throw new MetaCException("ActionListener를 등록할 수 없습니다. class [" + component.getClass().getName()  + "]");
		}
		
		try {
			selectedMethod.setAccessible(true);
			selectedMethod.invoke(component, INSTANCE);
		} catch (Exception e) {
			throw new MetaCException("ActionListener를 등록할 수 없습니다. class [" + component.getClass().getName()  + "]", e);
		}
		
		INSTANCE.handlerMap.put(component, handler);
	}
	
	public static <T extends JComponent> void addHandler(T component, CommonHandler handler) {
		addHandler(component, handler, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		invokeEvent(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		invokeEvent(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		invokeEvent(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		invokeEvent(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		invokeEvent(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		invokeEvent(e);
	}
	
	private void invokeEvent(AWTEvent e) {
		CommonHandler handler = handlerMap.get(e.getSource());
		
		if (handler != null) {
			handler.invoke(e);
		}
	}
}
