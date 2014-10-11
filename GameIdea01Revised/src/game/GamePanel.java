package game;

import gameState.GameStateManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable
{
	
	// dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	// Game state
	private GameStateManager gsm;
	
	// Running
	private boolean running;
	private Thread thread;
	
	// Render layer
	private BufferedImage image;
	
	// Graphics instance
	private Graphics2D g;
	
	// Maintaining framerate
	private int FPS = 60;
	private long targetTime = 1000/ FPS;
	
	public GamePanel()
	{
		
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		setFocusable(true);
		gsm = new GameStateManager();
		addKeyListener(new KeyListener());
		requestFocus();
		
		
	}
	
	public void addNotify()
	{
		super.addNotify();
		if (thread == null)
		{
			thread = new Thread(this);
			
			thread.start();
		}
	}
	
	public void init()
	{
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
	}
	
	public void run()
	{
		
		init();
		
		
		// timer stuff to maintain constant framerate
		long start;
		long elapsed;
		long wait;
		
		
		
		// game loop
		while (running)
		{
			// start time
			start = System.nanoTime();
			
			
			
			processInput();
			update();
			draw();
			drawRenderLayer();
			
			// how long it took to do everything
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - (elapsed / 1000000);
			
			if (wait < 0)
				wait = 1;
			
			try
			{
				Thread.sleep(wait);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void update()
	{
		gsm.update();
	}
	
	public void draw()
	{
		gsm.draw(g);
	}
	
	public void drawRenderLayer()
	{
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	public void processInput()
	{
		gsm.processInput();
	}
	
	private class KeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			gsm.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e)
		{
			gsm.keyReleased(e);
		}
	}

	
}
