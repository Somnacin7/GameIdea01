package entity.player;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utility.Animation;
import utility.Keys;

public class PlayerStateTeleporting implements PlayerState
{
	private Animation leftAnimation;
	private Animation rightAnimation;
	private int teleTime;
	private final int MAX_TELE_TIME = 15;

	public PlayerStateTeleporting()
	{
		init();
	}

	public void init() // Should initialize Animation
	{
		leftAnimation = new Animation("/player/playerTeleportLeft.png", 1, 32);
		leftAnimation.setDelay(-1);

		rightAnimation = new Animation("/player/playerTeleportRight.png", 1, 32);
		rightAnimation.setDelay(-1);

	}

	public void enter(Player player, Keys keys)
	{
		teleTime = 0;
	}

	public void processInput(Player player, Keys keys)
	{
		teleTime++;
		if (keys.isKeyPressed(KeyEvent.VK_X) && teleTime <= MAX_TELE_TIME) // 60 = 1 second
		{
			if (player.getFacingRight())
			{
				player.setdx(7);
				player.setdy(0);
			}
			else
			{
				player.setdx(-7);
				player.setdy(0);
			}
		}
		else
		{
			player.changeState(Player.FALLING);
		}
	}

	public void update(Player player)
	{
		// player.applyGravity();

		player.incrementx();

		// player.checkXCollision();

		player.incrementy();

		// player.checkYCollision();
	}

	public BufferedImage getImage(Player player)
	{
		if (player.getFacingRight())
			return rightAnimation.getImage();
		else
			return leftAnimation.getImage();
	}

	public void exit(Player player)
	{
		// Check if player exits teleport inside a block.  If so, kill that player
		int row = (int) (player.getBoundingBox().getCenterY()) / player.getTileMap().getTileSize();
		int col = (int) (player.getBoundingBox().getCenterX()) / player.getTileMap().getTileSize();
		
		if (player.getTileMap().getTileCollision(row, col))
		{
			player.setState(player.DEAD);
		}
		
		player.setHasTeleported(true);
	}
}
