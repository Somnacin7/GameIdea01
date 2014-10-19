package entity.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import map.TileMap;
import utility.Animation;
import utility.Keys;
import entity.Entity;

public class Platform extends Entity
{
	private Keys keys; // Perhaps a later mechanic can be arrow key presses interacting with platforms

	// Position
	private double x, y;

	// Velocity
	private double dx, dy;

	// Reference to tileMap
	private TileMap tileMap;
	
	// Image
	private Animation animation;


	// Collision checking - Will update every frame.  TODO Change to only update when it is grabbed?
	private Rectangle boundingBox;


	public Platform(TileMap tileMap, Keys keys)
	{
		this.tileMap = tileMap;
		this.keys = keys;
		init();
	}

	public void init()
	{
		animation = new Animation("/objects/platform.png", 1, 32, 16);
		animation.setDelay(-1);
	}

	public void processInput(Keys keys)
	{}

	public void update()
	{

		incrementx();

		incrementy();
		
		boundingBox = new Rectangle((int) x,(int) y, 32, 16);

	}

	
	public void incrementx()
	{
		x += dx;
	}
	
	public void incrementy()
	{
		y += dy;
	}


	// TODO add image and draw it
	public void draw(Graphics2D g)
	{}

	public void setTilePosition(int x, int y)
	{
		this.x = x * tileMap.getTileSize();
		this.y = y * tileMap.getTileSize();
	}

	public double getx()
	{
		return x;
	}

	public double gety()
	{
		return y;
	}

	public void setdx(double dx)
	{
		this.dx = dx;
	}

	public void setdy(double dy)
	{
		this.dy = dy;
	}

	public void incrementdx(double i)
	{
		dx += i;
	}

	public void incrementdy(double i)
	{
		dy += i;
	}

	public double getdx()
	{
		return dx;
	}

	public double getdy()
	{
		return dy;
	}

	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}

	public TileMap getTileMap()
	{
		return tileMap;
	}

}
