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
		assert false : "Your forgot to override update()";
	}
	public void processKeys()
	{
		assert false : "You forgot to override processKeys()";
	}
	public void draw(Graphics2D g)
	{
		assert false : "You forgot to override draw()";
	}
	
	
}
