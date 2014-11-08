package entity;

import java.awt.Graphics2D;

public class Entity
{
	// Position and velocity
	protected double x, y;
	protected double dx, dy;
	
	protected double xmap;
	protected double ymap;
	public void update()
	{
		System.out.println("Your forgot to override update");
	}
	public void processKeys()
	{
		System.out.println("You forgot to override processKeys");
	}
	public void draw(Graphics2D g)
	{
		System.out.println("You forgot to override draw");
	}
	
	
}
