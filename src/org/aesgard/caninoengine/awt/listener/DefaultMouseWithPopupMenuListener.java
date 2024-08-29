package org.aesgard.caninoengine.awt.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

public class DefaultMouseWithPopupMenuListener implements MouseListener {

	JPopupMenu popup;
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		showPopupOnRightClick(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		showPopupOnRightClick(e);
	}

	private void showPopupOnRightClick(MouseEvent e) {
		if (e.isPopupTrigger()) {
			if (popup != null) {
				popup.show(e.getComponent(),
		                  e.getX(), e.getY());
			}
		}
	}
	
	public void addPopup(JPopupMenu popup) {
		this.popup = popup;
	}
}
