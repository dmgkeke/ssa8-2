package com.sds.metac.ui.swing.event.specific;

import java.awt.event.MouseEvent;

import com.sds.metac.ui.swing.event.CommonHandler;

public abstract class CommonMouseHandler implements CommonHandler {
	public boolean mouseClicked(MouseEvent e) {return false;}
	public boolean mouseEntered(MouseEvent e) {return false;}
	public boolean mouseExited(MouseEvent e) {return false;}
	public boolean mousePressed(MouseEvent e) {return false;}
	public boolean mouseReleased(MouseEvent e) {return false;}
}
