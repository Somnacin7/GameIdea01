package entity.player;

import java.awt.image.BufferedImage;

import com.sun.glass.events.KeyEvent;

import utility.Animation;
import utility.Keys;

public class PlayerStateJumping implements PlayerState
{

	private Animation rightAnimation;
	private Animation leftAnimation;
	private final double MAX_VELOCITY = 3.0; // 3 is good
	private final double ACCEL = 0.2; // 0.3 is good. So is 0.15
	private final double DECCEL = 0.3;

	public PlayerStateJumping()
	{
		init();
	}

	public void init()
	{
		rightAnimation = new Animation("/player/playerRunningProto.png", 1, 32);
		rightAnimation.setDelay(-1);

		leftAnimation = new Animation("/player/playerRunningLeftProto.png", 1, 32);
		leftAnimation.setDelay(-1);
	}

	public void enter(Player player, Keys keys)
	{
		player.setdy(-2.8);

	}

	public void processInput(Player player, Keys keys)
	{
		if (keys.isKeyPressed(KeyEvent.VK_LEFT) && keys.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			if (player.getdx() > 0)
				player.incrementdx(ACCEL);
			else if (player.getdx() < 0)
				player.incrementdx(-ACCEL);
		}
		else if (keys.isKeyPressed(KeyEvent.VK_LEFT))
		{
			if (player.getdx() < 0)
				player.incrementdx(-ACCEL);
			else
				player.incrementdx(-DECCEL);

		}
		else if (keys.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			if (player.getdx() > 0)
				player.incrementdx(ACCEL);
			else
				player.incrementdx(DECCEL);

		}

		// If no keys are pressed, deccelerate
		if (!keys.isKeyPressed(KeyEvent.VK_RIGHT) && !keys.isKeyPressed(KeyEvent.VK_LEFT))
		{
			// Make sure we deccelerate in the right direction
			if (player.getdx() > 0)
			{
				player.incrementdx(-DECCEL);
				if (player.getdx() < 0)
					player.setdx(0);
			}
			else if (player.getdx() < 0)
			{
				player.incrementdx(DECCEL);
				if (player.getdx() > 0)
					player.setdx(0);
			}
		}

		// Make sure velocity is within limits
		if (player.getdx() < -MAX_VELOCITY)
			player.setdx(-MAX_VELOCITY);
		if (player.getdx() > MAX_VELOCITY)
			player.setdx(MAX_VELOCITY);

		if (keys.wasKeyJustPressed(KeyEvent.VK_LEFT))
			player.setFacingRight(false);
		if (keys.wasKeyJustPressed(KeyEvent.VK_RIGHT))
			player.setFacingRight(true);

		// Fix the animation
		if (player.getdx() > 0)
			player.setFacingRight(true);
		else if (player.getdx() < 0)
			player.setFacingRight(false);

		if (keys.isKeyPressed(KeyEvent.VK_Z))
			player.incrementdy(-.11); // Must be less than GRAVITY_ACCEL in
										// Player
		
		if (keys.wasKeyJustPressed(KeyEvent.VK_X)  && !player.getHasTeleported())
			player.changeState(Player.TELEPORTING);

		if (player.getdy() >= 0)
			player.changeState(Player.FALLING);
	}

	public void update(Player player)
	{
		player.applyGravity();

		player.incrementx();

		player.checkXCollision();

		player.incrementy();

		player.checkYCollision();
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
	}

}
