package org.aesgard.caninoengine;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.aesgard.caninoengine.entity2d.AnimatedSprite;
import org.aesgard.caninoengine.entity2d.Point2D;
import org.aesgard.caninoengine.entity2d.Sprite;
import org.aesgard.caninoengine.music.MidiSequence;
import org.aesgard.caninoengine.sound.SoundClip;
import org.aesgard.caninoengine.sprite.SpriteManagement;
import org.aesgard.caninoengine.swing.listener.DefaultKeyboardListener;
import org.aesgard.caninoengine.swing.listener.DefaultMouseListener;

public class CaninoGameEngine2D {

	/**************************************************************************************
	 * PRIVATE SECTION
	 */
	// Titulo default que aparece na barra de título
	private String  title            = "CaninoGameEngine2D";
	
	// Altura padrão da janela, quando não está em modo fullscreen
	private int     windowHeight     = 600;
	
	// Largura padrão da janela, quando não está em modo fullscreen
	private int     windowWidth      = 800;
	
	// Altura do modo fullscreen. -1 significa que o engine vai obter o valor corrente do sistema.
	private int     fullScreenHeight = -1;
	
	// Largura do modo fullscreen. -1 significa que o engine vai obter o valor corrente do sistema.
	private int     fullScreenWidth  = -1;
	
	// Liga/desliga o modo fullscreen
	private boolean fullscreen = false;
	
	// Frame principal do engine. Contém a instância do engine
	private JFrame  frame;
	
	// Ambiente gráfico.
	private GraphicsEnvironment ge;
	
	// Dispositivo gráfico
	private GraphicsDevice      gd;
	
	// Thread principal do jogo
    private Thread gameloop;
    
    // Back Buffer, para desenho suave
    private BufferedImage backbuffer;

    // Controla se o init do jogo já foi feito ou não
    private Boolean started = false;
    
    // Guarda a última tecla usada, o caracter da tecla e o evento
	private int keyCode;
    private char keyChar;
    private KeyEvent keyEvent;
    
    // Guarda o último botão usado do mouse e as coordenadas
    private int mouseButton;
    private Point2D mousePosition = new Point2D(0,0);
    
    // Lista de sprites
    private List<Sprite> sprites;
    
    // Taxa de quadros do jogo
    private int frameCount = 0;
    private int frameRate = 0;
    private int desiredRate = 60;
    private long startTime = System.currentTimeMillis();
    
    // Controla se o jogo está ou não pausado
    private boolean gamePaused = false;

	private void startMainThread() {
		// Iniciando loop principal do jogo
    	gameloop = new Thread() {
    	    /** 
    	     * LOOP Principal do Game
    	     */
    	    public void run() {
    	        Thread t = Thread.currentThread();
    	        while (t == gameloop) {
    	            try {
						// Define um frame rate consistente
						Thread.sleep(1000 / desiredRate);
    	            }
    	            catch (InterruptedException e) {
    	                e.printStackTrace();
    	            }
    	            
    	             // Atualiza a lista interna de sprites
    	             if (!gamePaused()) {
    	                 SpriteManagement.updateSprites(sprites);
    	                 SpriteManagement.testCollisions(sprites);
    	             }

    	             // Atualiza a logica do jogo, se necessário
    	             gameUpdate();

    	             getComponent().repaint();
    	        }
    	    }
    	};
        gameloop.start();
	}
	
    // Cria o back buffer para gráficos suaves.
	private void createBackbuffer() {
		backbuffer = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
	}
	
	// Garante que a inicializacao do jogo foi feita somente uma vez
	private synchronized void verifyStartup() {
    	if(!started) {
    		this.gameInit();
        	started = true;
    	}
	}
	
	// Verifica se o backbuffer foi criado
	private synchronized boolean isBackbufferCreated() {
		if ((backbuffer == null || g2d == null)) return false;
		return true;
	}
	
	/**************************************************************************************
	 * PROTECTED SECTION
	 */
    // Objeto 2D principal de Desenho para o back buffer
    protected Graphics2D g2d;
    
    // Matriz identidade usada nas tranformações
    protected AffineTransform identity = new AffineTransform();
    
    // Gerador de numeros aleatórios
    protected Random rand = new Random();
    
    // Toca arquivos MIDI
    protected MidiSequence midi = new MidiSequence();
    
    // Toca arquivos de som
    protected SoundClip sound = new SoundClip();
    
	// Largura da janela do jogo
	protected int getGameWidth() {return fullscreen?fullScreenWidth:windowWidth;}
	
	// Altura da janela do jogo
	protected int getGameHeight() {return fullscreen?fullScreenHeight:windowHeight;}
	
	// Para o jogo
	public void stop() {
        // Mata a thread
        gameloop = null;
        // Limpeza da classe filha
        gameStop();
        frame.setVisible(false);
        System.exit(0);
    }
    
    // Inicializa o jogo, carrega sprites, sons, musica, etc.
	protected void gameInit() {}
	// Faz o desenho na tela. Usado para fundos, menus, etc.
    protected void gameDraw() {}
    // Atualiza a lógica interna do jogo
    protected void gameUpdate() {}
    // Finaliza o jogo, liberando recursos
    protected void gameStop() {}
    
    // Atualiza a lógica do sprite
    public void spriteUpdate(AnimatedSprite sprite) {}
    // Desenha o sprite
    public void spriteDraw(AnimatedSprite sprite) {}
    // É chamado quando o sprite está para ser removido
    public void spriteDying(AnimatedSprite sprite) {}
    // Detecta colisão entre dois sprites
    public void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {}

    
	/**************************************************************************************
	 * PUBLIC SECTION
	 */
    // Métodos de teclado
    public void doKeyPressed() {};
    public void doKeyReleased() {};
    public void doKeyLeft() {};
    public void doKeyRight() {};
    public void doKeyUp() {};
    public void doKeySpace() {};
    public void doKeyControl() {};
    public void doKeyShift() {};
    public void doKeyEnter() {};
    public void doKeyEsc() {};
    
	// Verifica quais teclas (numericas e alfanumericas) foram pressionadas
	public void doKey() {};
    
    // Métodos que verificam qual tecla foi pressionada
    public boolean isKeyB() {return this.keyEvent==null?false:this.keyEvent.getKeyCode() == KeyEvent.VK_B;}
    public boolean isKeyC() {return this.keyEvent==null?false:this.keyEvent.getKeyCode() == KeyEvent.VK_C;}
       
    // Métodos de mouse
    public void doMouseButton1Pressed() {};
    public void doMouseButton2Pressed() {};
    public void doMouseButton3Pressed() {};
    public void doMouseEntered() {};
    public void doMouseExited() {};
    public void doMouseReleased() {};
    public void doMouseClicked() {};
    public void doMousePressed() {};    
	public void doMouseDragged() {};
	public void doMouseMoved() {};

    // Componente onde é feito o desenho
	public Component component = new JPanel() {
		private static final long serialVersionUID = 6817720587786251882L;

		public void update(Graphics g) {
	    	paint(g);
	    }
	    
	    public void paint(Graphics g) {
	    	if (!isBackbufferCreated()) return;
	    	
	    	// Calcula o frame rate
            frameCount++;
            if (System.currentTimeMillis() > startTime + 1000) {
                startTime = System.currentTimeMillis();
                frameRate = frameCount;
                frameCount = 0;

                // A cada segundo, sprites mortos são removidos
                SpriteManagement.purgeSprites(sprites);
            }
            
            // Metodo usado pela classe filha para desenha fundos, menus, etc.
            gameDraw();

            // Desenha os sprites
            if (!gamePaused()) {
            	SpriteManagement.drawSprites(sprites);
            }

            //Atualiza o back buffer na tela
			g.drawImage(backbuffer, 0, 0, getGameWidth(), getGameHeight(), component);
	    }
	    
	};

	// Controla o modo fullscreen
	public boolean isFullscreen() {return fullscreen;}
	public CaninoGameEngine2D setFullscreen(boolean fullscreen) {this.fullscreen = fullscreen; return this;}
	
	// Componente de desenho
	public Component getComponent() {return component;}
	
	// Backbuffer
	public Graphics2D getScreen() {return g2d;}
	
	public int getKeyCode() {return keyCode;}
	public void setKeyCode(int keyCode) {this.keyCode = keyCode;}

	public char getKeyChar() {return keyChar;}
	public void setKeyChar(char keyChar) {this.keyChar = keyChar;}

	public KeyEvent getKeyEvent() {return keyEvent;}
	public void setKeyEvent(KeyEvent keyEvent) {this.keyEvent = keyEvent;}

	public int getMouseButton() {return mouseButton;}
	public void setMouseButton(int mouseButton) {this.mouseButton = mouseButton;}

	public int getMouseX() {return (int)mousePosition.X();}
	public void setMouseX(int mouseX) {mousePosition.setX(mouseX);}

	public int getMouseY() {return (int)mousePosition.Y();}
	public void setMouseY(int mouseY) {mousePosition.setY(mouseY);}
	
    public List<Sprite> sprites() { return sprites; }
   
	public int getDesiredRate() {return desiredRate;}
	public void setDesiredRate(int desiredRate) {this.desiredRate = desiredRate;}
	
    public boolean gamePaused() { return gamePaused; }
    public void pauseGame() { gamePaused = true; }
    public void resumeGame() { gamePaused = false; }

    //current frame rate
    public int frameRate() { return frameRate; }

    public void start() {
        // Lista de sprites
        sprites = new LinkedList<Sprite>();
        
        SpriteManagement.setEngine(this);

		// Obtem o Ambiente e o Dispositivo gráfico atual
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();

		// Frame principal onde o componente de desenho é inserido. 
		frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		frame.add(component);
		if (gd.isFullScreenSupported() && isFullscreen()) {
			// Remove bordas e barras da janela. Só funciona em frames não visíveis
			frame.setUndecorated(true);
			gd.setFullScreenWindow(frame);
			fullScreenWidth  = gd.getDisplayMode().getWidth();
			fullScreenHeight = gd.getDisplayMode().getHeight();
		} else {
			frame.setTitle(title);
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			// Centraliza
			frame.setLocation(
					(int) (d.getWidth()-getGameWidth()) / 2, 
					(int) (d.getHeight()-getGameHeight()) / 2);
			frame.setSize(getGameWidth(), getGameHeight());
		}
		
		// Precisa criar o buffer ANTES do init
		createBackbuffer();
		
		// INIT do jogo
		verifyStartup();

		// Definindo listeners de teclado e mouse
		frame.addKeyListener(DefaultKeyboardListener.createKeyListener(this));
		frame.addMouseListener(DefaultMouseListener.createMouseListener(this));
		frame.addMouseMotionListener(DefaultMouseListener.createMouseMotionListener(this));
			
    	startMainThread();
        
 		// Agora sim. Mostra!
		frame.setVisible(true);
    }
	
	public void start(String title){
    	this.title = title;
    	start();
    }

	public void start(int width, int height) {
    	this.windowHeight = height;
    	this.windowWidth  = width;
  		start();
    }
	
	public void start(int frameRate, int width, int height) {
		this.desiredRate = frameRate;
    	this.windowHeight = height;
    	this.windowWidth  = width;
  		start();
    }
    
	public void start(String title, int width, int height) {
    	this.title        = title;
    	this.windowHeight = height;
    	this.windowWidth  = width;
  		start();
    }
}
