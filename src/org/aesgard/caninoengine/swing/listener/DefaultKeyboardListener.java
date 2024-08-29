package org.aesgard.caninoengine.swing.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class DefaultKeyboardListener implements KeyListener {
	public CaninoGameEngine2D engine;
	
	public DefaultKeyboardListener(CaninoGameEngine2D engine) {
		this.engine = engine;
	}
	
	public static KeyListener createKeyListener(CaninoGameEngine2D engine) {
		DefaultKeyboardListener listener = new DefaultKeyboardListener(engine);
		return listener;
	}

	public void keyReleased(KeyEvent k) {engine.doKeyReleased();}
    public void keyTyped(KeyEvent k) { }
    public void keyPressed(KeyEvent k) {
        int keyCode = k.getKeyCode();
    	engine.setKeyCode(keyCode);
    	engine.setKeyChar(k.getKeyChar());
    	engine.setKeyEvent(k);

        switch (keyCode) {

	        case KeyEvent.VK_LEFT:
	        	engine.doKeyLeft();
	            break;
	
	        case KeyEvent.VK_RIGHT:
	        	engine.doKeyRight();
	            break;
	
	        case KeyEvent.VK_UP:
	        	engine.doKeyUp();
	            break;
	
	        case KeyEvent.VK_SPACE:
	        	engine.doKeySpace();
	            break;
	            
	        case KeyEvent.VK_CONTROL:
	        	engine.doKeyControl();
	        	break;
	        	
	        case KeyEvent.VK_SHIFT:
	        	engine.doKeyShift();
	        	break;
	        	
	        case KeyEvent.VK_ENTER:
	        	engine.doKeyEnter();
	        	break;
	            
	        case KeyEvent.VK_ESCAPE:
	        	engine.doKeyEsc();
	        	break;
	        	
	        case KeyEvent.VK_Q:
	        	engine.stop();
	        	break;
	        	
	        default:
	        	engine.doKey();
	        	break;
        }
    }
}

