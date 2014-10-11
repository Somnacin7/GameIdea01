package entity.player;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utility.Animation;
import utility.Keys;

public class PlayerStateIdle implements PlayerState
{
	private Animation rightAnimation;
	private Animation leftAnimation;
	
	public PlayerStateIdle()
	{
		init();
	}
	
	public void init()
	{
		rightAnimation = new Animation("/player/playerIdle01.png", 1, 32);
		rightAnimation.setDelay(-1);
		
		leftAnimation = new Animation("/player/playerIdleLeft01.png", 1, 32);
		leftAnimation.setDelay(-1);
	}
	
	public void enter(Player player, Keys keys)
	{}
	
	public void processInput(Player player, Keys keys)
	{
		if (keys.isKeyPressed(KeyEvent.VK_LEFT) || keys.isKeyPressed(KeyEvent.VK_RIGHT))
			player.changeState(Player.STANDING);
		
	}
	
	public void update(Player player)
	{
		
	}
	
	public BufferedImage getImage(Player player)
	{
		if (player.getFacingRight())
			return rightAnimation.getImage();
		else
			return leftAnimation.getImage();
	}
	
	public void exit(Player player)
	{}
}
