package org.aesgard.caninoengine;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.awt.Window;
import org.aesgard.caninoengine.input.KeyboardUtil;
import org.aesgard.caninoengine.lwgl.DisplayUtils;
import org.aesgard.caninoengine.timer.Timer;
import org.lwjgl.opengl.Display;

public abstract class CaninoGameEngine3D {

	private static final int DEFAULT_FRAME_RATE = 60;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_WIDTH = 800;
	
	// Define a constant for the value of PI
	public final float GL_PI = 3.1415f;

	// Rotation amounts
	protected float xRot = 0.0f;
	protected float yRot = 0.0f;
	
	// Menu
	private Window window;
	private JPopupMenu popup;
	
	private boolean fullscreen = false;
	private boolean useCanvas  = true;

	// Controla se a cena Ž ou n‹o redesenhada
	protected boolean renderScene = true;
	
	// Timers for objects in scene
	private List<Timer> timers = new ArrayList<Timer>();
	
	// Sleep time from main loop
	private long sleepTime = 0;
	
	// Window title
	private String title;
	
	
	public CaninoGameEngine3D() {}
	
	public CaninoGameEngine3D(String title) {
		this.title = title;
	}
	
	/*****
	 * ABSTRACT METHODS
	 */
	public abstract void RenderScene();
	public abstract void SetupRC();
	public abstract void ChangeSize(int w, int h);

	/*****
	 * PUBLIC FINAL METHODS
	 */
	final public boolean isFullscreen() {
		return fullscreen;
	}

	final public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	final public boolean isUseCanvas() {
		return useCanvas;
	}

	final public void setUseCanvas(boolean useCanvas) {
		this.useCanvas = useCanvas;
	}

	final public long getSleepTime() {
		return sleepTime;
	}
	
	final public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	final public void addTimer(Timer timer) {
		if (timer != null)
			timers.add(timer);
	}

	final public void addTaskScheduleAtFixedRate(TimerTask task, long delay, long period) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, delay, period);
		timers.add(timer);
	}
	
	final public void addPopup(JPopupMenu popup) {
		this.popup = popup;
	}
	
	final public void start() {
		this.start(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	final public void start(int w, int h) {
		try {
			if (isUseCanvas()) {
				window = new Window(w, h);
				if (title != null)
					window.setTitle(title);
				window.addPopup(popup);
				window.bind();
				Display.setParent(window.canvas);
			}
			
			Display.sync(DEFAULT_FRAME_RATE);
			DisplayUtils.setDisplayMode(w, h, fullscreen);
			Display.create();
			Display.setVSyncEnabled(true);
		
			SetupRC();
			ChangeSize(w,h);// Em LWJGL, n‹o pode mudar o tamanho da janela.
							// Esse mŽtodo ent‹o serve apenas para configurar o viewport,
							// clipping volume, etc.
			boolean closeRequested = false;
			while (!Display.isCloseRequested() 
					&& !closeRequested 
					&& !KeyboardUtil.isESC()) 
			{
				SpecialKeys();
				if (renderScene) {
					RenderScene();
					Display.update();
					if (isUseCanvas()) {
						closeRequested = window.closeRequested;
						window.requestFocus();
					}
				}
				if (getSleepTime()>0) {
					Thread.sleep(getSleepTime());
				}
			}
			StopTimers();
			ShutdownRC();
			Display.destroy();
			
			if (isUseCanvas()) {
				window.dispose();
				window = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	final public void setTitle(String title) {
		if (window != null)
			window.setTitle(title);
	}

	/*****
	 * PUBLIC METHODS WITH DEFAULT IMPLEMENTATIONS. CAN OVERRIDE
	 */
	public void ShutdownRC() {}


	/*****
	 * PRIVATE FINAL METHODS
	 */
	final private void StopTimers() {
		for (Timer timer : timers) {
			timer.cancel();
			timer.purge();
		}
	}
	
	/*****
	 * DEFAULT IMPLEMENTATIONS, CAN OVERRIDE
	 */
	protected void SpecialKeys() {
		float[] positions = KeyboardUtil.rotateUsingDefaultKeys(xRot, yRot);
		if (positions != null) {
			xRot = positions[0];
			yRot = positions[1];
		}
	}
	
	public void glutSetWindowTitle(String title) {
		if (window != null)
			window.setTitle(title);
	}
	
}
