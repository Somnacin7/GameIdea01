package gameState;

import java.awt.Graphics2D;

import map.Background;
import map.TileMap;
import utility.Keys;
import entity.player.Player;
import game.GamePanel;

public class Level1State extends GameState
{
	private GameStateManager gsm;
	private Keys keys;
	
	private Background clouds, mountains;
	
	private Player player;

	private TileMap tileMap;

	public Level1State(GameStateManager gsm, Keys keys)
	{
		this.gsm = gsm;
		this.keys = keys;
		init();
	}

	public void init()
	{
		// Background init
		clouds = new Background("/backgrounds/level1/clouds.png", 0.3, 0.3);
		clouds.setMoveScale(.7, 0);
		mountains = new Background("/backgrounds/level1/mountains.png", 0.7, 0.7);
		mountains.setMoveScale(.9, 0);
		
		// TileMap Stuff
		tileMap = new TileMap(16);
		tileMap.loadTiles("/tiles/tileSheet01.png");
		tileMap.loadCollisionMap("/maps/Level1.m");
		tileMap.loadTileMap("/maps/level1.tilemap");
		tileMap.setPosition(-100, -100);
		
		// Player init
		player = new Player(tileMap, keys);
		player.setTilePosition(15, 12);
	}
	
	public void processInput()
	{
		player.processInput(keys);
	}

	public void update()
	{
		player.update();
		tileMap.setPosition((GamePanel.WIDTH / 2) - player.getx(), (GamePanel.HEIGHT / 2) - player.gety());
		mountains.setPosition((int)tileMap.getx(), (int)tileMap.gety());
		clouds.setPosition(tileMap.getx(), tileMap.gety());
	}

	public void draw(Graphics2D g)
	{
		
		clouds.draw(g);
		mountains.draw(g);
		
		tileMap.draw(g);
		
		player.draw(g);
	}

	

}
