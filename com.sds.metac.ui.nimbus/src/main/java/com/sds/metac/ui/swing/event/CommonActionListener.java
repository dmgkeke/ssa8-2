package com.sds.metac.ui.swing.event;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.ui.swing.event.specific.CommonDefaultHandler;
import com.sds.metac.ui.swing.event.specific.CommonMouseHandler;
import com.sds.metac.ui.swing.event.specific.CommonWindowHandler;

public enum CommonActionListener implements ActionListener, MouseListener, WindowListener {
	INSTANCE;	
	
	Logger logger = Logger.getLogger(CommonActionListener.class);
	
	Map<Object, CommonHandler> handlerMap = new HashMap<Object, CommonHandler>();
	
	@SuppressWarnings("unchecked")
	public static <T extends Container, E extends EventListener> void addHandler(T component, CommonHandler handler, Class<E> listenrClazz) {
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
	
	@Override public void actionPerformed(ActionEvent e) { invokeEvent(e); }
	
	@Override public void mouseClicked(MouseEvent e) { invokeMouseEvent(e, M_CLICKED); }
	@Override public void mouseEntered(MouseEvent e) { invokeMouseEvent(e, M_ENTERED); }
	@Override public void mouseExited(MouseEvent e) { invokeMouseEvent(e, M_EXITED); }
	@Override public void mousePressed(MouseEvent e) { invokeMouseEvent(e, M_PRESSED); }
	@Override public void mouseReleased(MouseEvent e) { invokeMouseEvent(e, M_RELEASED); }
	
	@Override public void windowActivated(WindowEvent e) { invokeWindowEvent(e, W_ACTIVATED); }
	@Override public void windowClosed(WindowEvent e) { invokeWindowEvent(e, W_CLOSED); }
	@Override public void windowClosing(WindowEvent e) { invokeWindowEvent(e, W_CLOSING); }
	@Override public void windowDeactivated(WindowEvent e) { invokeWindowEvent(e, W_DEACTIVATED); }
	@Override public void windowDeiconified(WindowEvent e) { invokeWindowEvent(e, W_DEICONIFIED); }
	@Override public void windowIconified(WindowEvent e) { invokeWindowEvent(e, W_ICONIFIED); }
	@Override public void windowOpened(WindowEvent e) { invokeWindowEvent(e, W_OPENED); }
	
	private void invokeEvent(AWTEvent e) {
		CommonHandler handler = handlerMap.get(e.getSource());
		if (!(handler instanceof CommonDefaultHandler)) {
			return;
		}
		
		CommonDefaultHandler defaultHandler = (CommonDefaultHandler)handler;
		defaultHandler.invoke(e);
		
		setLogging(e);
	}
	
	private static final int M_CLICKED = 0;
	private static final int M_ENTERED = 1;
	private static final int M_EXITED = 2;
	private static final int M_PRESSED = 3;
	private static final int M_RELEASED = 4;
	
	private void invokeMouseEvent(MouseEvent e, int opt) {
		CommonHandler handler = handlerMap.get(e.getSource());
		if (!(handler instanceof CommonMouseHandler)) {
			return;
		}
		CommonMouseHandler mouseHandler = (CommonMouseHandler) handler;
		
		boolean isConcrete = false;
		switch (opt) {
		case M_CLICKED : isConcrete = mouseHandler.mouseClicked(e); break;
		case M_ENTERED : isConcrete = mouseHandler.mouseEntered(e); break;
		case M_EXITED : isConcrete = mouseHandler.mouseExited(e); break;
		case M_PRESSED : isConcrete = mouseHandler.mousePressed(e); break;
		case M_RELEASED : isConcrete = mouseHandler.mouseReleased(e); break;
		default :
			break;
		}
		
		if (isConcrete) {
			setLogging(e);
		}
	}
	
	
	private static final int W_ACTIVATED = 0;
	private static final int W_CLOSED = 1;
	private static final int W_CLOSING = 2;
	private static final int W_DEACTIVATED = 3;
	private static final int W_DEICONIFIED = 4;
	private static final int W_ICONIFIED = 5;
	private static final int W_OPENED = 6;
	
	private void invokeWindowEvent(WindowEvent e, int opt) {
		CommonHandler handler = handlerMap.get(e.getSource());
		if (!(handler instanceof CommonWindowHandler)) {
			return;
		}
		CommonWindowHandler windowHandler = (CommonWindowHandler) handler;
		
		boolean isConcrete = false;
		switch (opt) {
		case W_ACTIVATED : isConcrete = windowHandler.windowActivated(e); break;
		case W_CLOSED : isConcrete = windowHandler.windowClosed(e); break;
		case W_CLOSING : isConcrete = windowHandler.windowClosing(e); break;
		case W_DEACTIVATED : isConcrete = windowHandler.windowDeactivated(e); break;
		case W_DEICONIFIED : isConcrete = windowHandler.windowDeiconified(e); break;
		case W_ICONIFIED : isConcrete = windowHandler.windowIconified(e); break;
		case W_OPENED : isConcrete = windowHandler.windowOpened(e); break;
		default :
			break;
		}
		
		if (isConcrete) {
			setLogging(e);
		}
	}

	private void setLogging(AWTEvent e) {
		logger.debug("행동 기록중 : " + e);
	}
}
