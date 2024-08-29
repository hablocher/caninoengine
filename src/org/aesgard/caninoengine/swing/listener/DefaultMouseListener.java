package org.aesgard.caninoengine.swing.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class DefaultMouseListener implements MouseListener, MouseMotionListener {
	public CaninoGameEngine2D engine;
	
	public DefaultMouseListener(CaninoGameEngine2D engine) {
		this.engine = engine;
	}
	
	public static MouseListener createMouseListener(CaninoGameEngine2D engine) {
		DefaultMouseListener listener = new DefaultMouseListener(engine);
		return listener;
	}
	
	public static MouseMotionListener createMouseMotionListener(CaninoGameEngine2D engine) {
		DefaultMouseListener listener = new DefaultMouseListener(engine);
		return listener;
	}
	
	private void extractData(MouseEvent m) {
		engine.setMouseButton(m.getButton());
		engine.setMouseX(m.getX());
		engine.setMouseY(m.getY());
	}

	public void mouseEntered(MouseEvent m) { extractData(m); engine.doMouseEntered(); }
    public void mouseExited(MouseEvent m) { extractData(m); engine.doMouseExited(); }
    public void mouseReleased(MouseEvent m) { extractData(m); engine.doMouseReleased(); }
    public void mouseClicked(MouseEvent m) { extractData(m); engine.doMouseClicked(); }
    public void mousePressed(MouseEvent m) {
    	extractData(m);
    	switch(m.getButton()) {
	        case MouseEvent.BUTTON1:
	        	engine.doMouseButton1Pressed();
	        	break;
	        case MouseEvent.BUTTON2:
	        	engine.doMouseButton2Pressed();
	        	break;
	        case MouseEvent.BUTTON3:
	        	engine.doMouseButton3Pressed();
	            break;
        }
    }
	public void mouseDragged(MouseEvent m) { extractData(m); engine.doMouseDragged(); }
	public void mouseMoved(MouseEvent m) { extractData(m); engine.doMouseMoved(); }
}

