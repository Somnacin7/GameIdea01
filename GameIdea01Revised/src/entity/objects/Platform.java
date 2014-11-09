package entity.objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import map.TileMap;
import utility.Animation;
import utility.Keys;
import entity.Entity;

public class Platform extends Entity implements Collidable
{
	private Keys keys; // Perhaps a later mechanic can be arrow key presses interacting with platforms

	// Position
	private double x, y;

	// Velocity
	private double dx, dy;
	private double moveSpeed;

	// Reference to tileMap
	private TileMap tileMap;
	
	// Image
	private Animation animation;


	// Collision checking - Updates when grabbed
	private Rectangle boundingBox;
	
	// Each point in the path
	private ArrayList<Point> points;
	private Point currentPoint; // This is the point we are seeking

	// x and y become the first point.
	public Platform(TileMap tileMap, Keys keys, double x, double y)
	{
		this.tileMap = tileMap;
		this.keys = keys;
		this.x = x;
		this.y = y;
		points = new ArrayList<Point>();
		dx = 1; // Default value
		moveSpeed = 1;

		
		init();
	}

	public void init()
	{
		animation = new Animation("/objects/platform.png", 1, 32, 16);
		animation.setDelay(-1); // No animation
		
		addPoint((int) x,(int) y);
		currentPoint = points.get(0);
	}
	
	public void addPoint(int x, int y)
	{
		points.add(new Point(x, y));
	}
	
	
	// Selects a new point and calculates slope to that point
	public void selectNextPoint()
	{
		x = currentPoint.getX();
		y = currentPoint.getY();
		
		double x1, y1, x2, y2, rise, run;
		
		x1 = currentPoint.getX();
		y1 = currentPoint.getY();
		
		// If this is the last point, go to the first
		if (points.indexOf(currentPoint) == points.size() - 1)
			currentPoint = points.get(0);
		else
			currentPoint = points.get(points.indexOf(currentPoint) + 1);
		
		x2 = currentPoint.getX();
		y2 = currentPoint.getY();
		
		// Calculating slope
		rise = y2 - y1;
		run = x2 - x1;
		
		double th = Math.atan2(rise, run);
		
		dy = moveSpeed * Math.sin(th);
		dx = moveSpeed * Math.cos(th);

		
		if (x2 > x1)
			dx = Math.abs(dx);
		else if (x2 < x1)
			dx = -1 * Math.abs(dx);
		else
			dx = 0;

		
		if (y2 > y1)
			dy = Math.abs(dy);
		else if (y2 < y1)
			dy = -1 * Math.abs(dy);
		else
			dy = 0;
	}

	public void processInput(Keys keys)
	{}

	public void update()
	{
		// Keep updating unless we have reached the next point
		double dist = currentPoint.getX() - x;
		double xTime = dist / dx;
		
		if ((int) x != (int)currentPoint.getX() && !(xTime < 1 && xTime > 0))
		{
			incrementx();
		}
		
		dist = currentPoint.getY() - y;
		double yTime = dist / dy;
		
		if ((int) y != (int)currentPoint.getY() && !(yTime < 1 && yTime > 0))
		{
			incrementy();
		}
		
		// If we reach the next point, set currentPoint to the next point
		if (((int) x == (int)currentPoint.getX()) && ((int) y == (int)currentPoint.getY()))
			selectNextPoint();
		else if (xTime < 1 && yTime < 1)
			selectNextPoint();
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
	{
		g.drawImage(animation.getImage(),  (int) (x + tileMap.getx()), (int) (y + tileMap.gety()), null);
	}

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
		boundingBox = new Rectangle((int) x,(int) y, 32, 16);
		return boundingBox;
	}

	public TileMap getTileMap()
	{
		return tileMap;
	}
	
	public void setSpeed(double ms)
	{
		moveSpeed = ms;
	}

}
