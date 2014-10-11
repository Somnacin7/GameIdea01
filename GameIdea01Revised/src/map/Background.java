package map;

import game.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Background
{
	private BufferedImage image;
	
	// position
	private double x, y;
	// velocity
	private double dx, dy;
	
	private double xscale, yscale;
	

	
	public Background(String imageSource, double dx, double dy)
	{
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream(imageSource));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setVelocity(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update()
	{
		x = (x + dx) % GamePanel.WIDTH;
		y = (y + dy) % GamePanel.HEIGHT;
		
	}
	
	public void setMoveScale(double x, double y)
	{
		xscale = x;
		yscale = y;
	}
	
	public void setPosition(double x, double y)
	{
		this.x = (x * xscale) % GamePanel.WIDTH;
		this.y = (y * yscale) % GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int)x, (int)y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		if (x > 0)
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		if (x < 0)
			g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		if (y > 0)
			g.drawImage(image, (int)x, (int)y - GamePanel.HEIGHT, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		if (y < 0)
			g.drawImage(image, (int)x, (int)y + GamePanel.HEIGHT, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}
	
}
