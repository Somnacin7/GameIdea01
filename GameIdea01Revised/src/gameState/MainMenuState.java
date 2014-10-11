package gameState;

import game.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import map.Background;
import map.TileMap;
import utility.Keys;

public class MainMenuState extends GameState
{
	
	private GameStateManager gsm;
	private Keys keys;
	
	private Background bg;
	
	private Font font;
	
	private int currentOption = 0;
	private String[] options = {
			"Start",
			"Options",
			"Quit"
	};
	
	
	public MainMenuState(GameStateManager gsm, Keys keys)
	{
		this.gsm = gsm;
		this.keys = keys;
		
		bg = new Background("/backgrounds/mainMenuBG.png", 1, 0);

		
		font = new Font("Fixedsys", 10, 10);
		TileMap tm = new TileMap(16);
		tm.loadTiles("/tiles/tileSheet01.png");
		tm.loadCollisionMap("/maps/Level1.m");
	}
	
	public void init()
	{}
	public void update()
	{
		bg.update();
	}
	public void draw(Graphics2D g)
	{
		FontMetrics fm = g.getFontMetrics(font);
		// draw background
		bg.draw(g);
		
		// draw title
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Idea01", (GamePanel.WIDTH - fm.stringWidth("Idea01")) / 2, 40);
		
		for (int i = 0; i < options.length; i++)
		{
			if (i == currentOption)
			{
				g.setColor(Color.WHITE);
				g.drawString(options[i], (GamePanel.WIDTH - fm.stringWidth("Idea01")) / 2, 70 + (20 * i));
			}
			else
			{
				g.setColor(Color.BLACK);
				g.drawString(options[i], (GamePanel.WIDTH - fm.stringWidth("Idea01")) / 2, 70 + (20 * i));
			}
		}
	}
	
	public void selectOption()
	{
		System.out.println(currentOption);
		switch (currentOption)
		{
			case 0:
				gsm.setState(GameStateManager.LEVEL_1);
				break;
				
			case 1:
				break;
				
			case 2:
				System.exit(0);
				break;
		}
	}
	
	

	public void processInput()
	{
		if(keys.wasKeyJustPressed(KeyEvent.VK_ENTER))
		{			
			selectOption();
		}
		if(keys.wasKeyJustPressed(KeyEvent.VK_UP))
		{
			System.out.println("asd");
			if (--currentOption < 0) // Wrap menu selection
				currentOption = options.length - 1;
		}
		if(keys.wasKeyJustPressed(KeyEvent.VK_DOWN))
		{
			if (++currentOption >= options.length) // Wrap menu selection
				currentOption = 0;
		}
	}
	
}
