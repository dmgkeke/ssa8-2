package com.sds.metac.ui.swing.event.specific;

import java.awt.event.WindowEvent;

import com.sds.metac.ui.swing.event.CommonHandler;

public class CommonWindowHandler implements CommonHandler {
	public boolean windowActivated(WindowEvent e) {return false;}
	public boolean windowClosed(WindowEvent e) {return false;}
	public boolean windowClosing(WindowEvent e) {return false;}
	public boolean windowDeactivated(WindowEvent e) {return false;}
	public boolean windowDeiconified(WindowEvent e) {return false;}
	public boolean windowIconified(WindowEvent e) {return false;}
	public boolean windowOpened(WindowEvent e) {return false;}
}
