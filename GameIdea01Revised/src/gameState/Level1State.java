package gameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import map.Background;
import map.TileMap;
import utility.Keys;
import entity.Entity;
import entity.objects.Platform;
import entity.player.Player;
import game.GamePanel;

public class Level1State extends GameState
{
	private GameStateManager gsm;
	private Keys keys;
	
	private Background clouds, mountains;
	
	private Player player;

	private TileMap tileMap;	
	
	// TODO Hopefully this is a sufficent entity system
	private ArrayList<Entity> entities;

	public Level1State(GameStateManager gsm, Keys keys)
	{
		this.gsm = gsm;
		this.keys = keys;
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
		
		// Other Entities and Enemies? TODO Support text file loading for entities (like TileMaps)
		entities = new ArrayList<Entity>();
		
		Platform p = new Platform(tileMap, keys, 13 * tileMap.gts(), 4 * tileMap.gts());
		p.addPoint(18 * tileMap.gts(), 5 * tileMap.gts());
		p.addPoint(14 * tileMap.gts(), 6 * tileMap.gts());
		p.setSpeed(2);
		entities.add(p);
		
		p = new Platform(tileMap, keys, 20 * tileMap.gts(), 5 * tileMap.gts());
		p.addPoint(21 * tileMap.gts(), 8 * tileMap.gts());
		p.setSpeed(1);
		entities.add(p);
		
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
		
		for (Entity p: entities)
		{
			p.update();
		}
		
		if (player.getIsDead())
		{
			System.out.println("SDASDASD");
			player.setTilePosition(15, 12);
			player.init();
		}
		
	}

	public void draw(Graphics2D g)
	{
		
		clouds.draw(g);
		mountains.draw(g);
		
		tileMap.draw(g);
		
		player.draw(g);
		
		for (Entity e: entities)
		{
			e.draw(g);
		}
	}

	

}
