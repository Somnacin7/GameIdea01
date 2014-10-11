package utility;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Animation
{
	
	// Frames
	private BufferedImage[] frames;
	private int currentFrame;
	
	// For changing speed of animation
	private int delay;
	private int delayTime;
	
	
	public Animation(String path, int numberOfFrames, int tileSize)
	{
		currentFrame = 0;
		delayTime = 0;
		delay = 2;
		try
		{
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(path));
			frames = new BufferedImage[numberOfFrames];
			for (int i = 0; i < numberOfFrames; i++)
			{
				frames[i] = sheet.getSubimage(i * tileSize, 0, tileSize, tileSize);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Animation(String path, int numberOfFrames, int width, int height)
	{
		currentFrame = 0;
		delayTime = 0;
		delay = 2;
		try
		{
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(path));
			frames = new BufferedImage[numberOfFrames];
			for (int i = 0; i < numberOfFrames; i++)
			{
				frames[i] = sheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
	
	public BufferedImage getImage()
	{
		return frames[currentFrame];
	}
	
	public void update()
	{
		
		
		if (delay == -1) // For objects with no animation
			return;
		
		delayTime++;
		
		if (delayTime >= delay)
		{
			delayTime = 0;
			currentFrame++;
			if (currentFrame >= frames.length)
				currentFrame = 0;
		}
		
		
	}
	
	
	
}
