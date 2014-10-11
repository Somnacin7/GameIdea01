package map;

import java.awt.image.BufferedImage;

public class Tile
{
	
	private BufferedImage image;
	private int type;
	
	public static final int EMPTY = 0,
			COLLIDES = 1;
	
	public Tile(BufferedImage image)
	{
		this.image = image;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
}
