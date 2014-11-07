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
	private Platform platform0;
	
	// TODO add entity array or other entity system
	// For now I am using a platform array since its the only entity
	private ArrayList<Platform> platforms;

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
		platforms = new ArrayList<Platform>();
		
		platform0 = new Platform(tileMap, keys, 13 * tileMap.getTileSize(), 4 * tileMap.getTileSize());
		platform0.addPoint(18 * tileMap.getTileSize(), 5 * tileMap.getTileSize());
		platform0.addPoint(14 * tileMap.getTileSize(), 6 * tileMap.getTileSize());
		platform0.setSpeed(2);
		platforms.add(platform0);
		
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
		
		for (Platform p: platforms)
		{
			p.update();
		}
	}

	public void draw(Graphics2D g)
	{
		
		clouds.draw(g);
		mountains.draw(g);
		
		tileMap.draw(g);
		
		player.draw(g);
		
		for (Platform p: platforms)
		{
			p.draw(g);
		}
	}

	

}
