package org.aesgard.caninoengine.awt;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.awt.listener.DefaultMouseWithPopupMenuListener;

public final class Window extends Frame {

	private static final long serialVersionUID = -3520750778458764076L;
	private DefaultMouseWithPopupMenuListener defaultMouselistener;
	
	public volatile boolean closeRequested;
	
	final public Canvas canvas = new Canvas();
	
    public Window(int w, int h) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeRequested = true;
			}
		});
		add(canvas , BorderLayout.CENTER);
		setSize(w, h);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void addPopup(JPopupMenu popup) {
		if (popup==null) return;
        if (defaultMouselistener == null) {
        	defaultMouselistener = new DefaultMouseWithPopupMenuListener();
        }
		defaultMouselistener.addPopup(popup);
	}
	
	public void bind() {
		if (defaultMouselistener != null) {
			canvas.addMouseListener(defaultMouselistener);
		}
	}
	
	public void requestFocus() {
		if (!canvas.isFocusOwner()) {
			canvas.requestFocus();
		}
	}

}
