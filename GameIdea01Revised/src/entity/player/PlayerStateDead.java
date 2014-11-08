package entity.player;

import java.awt.image.BufferedImage;

import com.sun.glass.events.KeyEvent;

import utility.Animation;
import utility.Keys;

public class PlayerStateDead implements PlayerState
{
	private Animation animation;
	
	public PlayerStateDead()
	{
		init();
	}
	
	public void init()
	{
		animation = new Animation("/player/playerDead.png", 1, 32);
		animation.setDelay(-1);
	}
	
	public void enter(Player player, Keys keys)
	{
		player.kill();
	}
	
	public void processInput(Player player, Keys keys)
	{
		
	}
	
	public void update(Player player)
	{}
	
	public BufferedImage getImage(Player player)
	{
		return animation.getImage();
	}
	
	public void exit(Player player)
	{}
	
}
