package entity.player;

import java.awt.image.BufferedImage;

import com.sun.glass.events.KeyEvent;

import utility.Animation;
import utility.Keys;

public class PlayerStateStanding implements PlayerState
{
	private Animation rightAnimation;
	private Animation leftAnimation;
	private Animation rightAnimationStanding;
	private Animation leftAnimationStanding;
	private final double MAX_VELOCITY = 3.0; // 3 is good
	private final double ACCEL = 0.2; // 0.3 is good. So is 0.15
	private final double DECCEL = 0.3;
	private boolean running;

	public PlayerStateStanding()
	{
		init();
	}

	public void init()
	{
		rightAnimation = new Animation("/player/playerRunningProto.png", 1, 32);
		rightAnimation.setDelay(-1);

		leftAnimation = new Animation("/player/playerRunningLeftProto.png", 1, 32);
		leftAnimation.setDelay(-1);

		rightAnimationStanding = new Animation("/player/playerRunningProto.png", 1, 32);
		rightAnimationStanding.setDelay(-1);

		leftAnimationStanding = new Animation("/player/playerRunningLeftProto.png", 1, 32);
		leftAnimationStanding.setDelay(-1);
	}

	public void enter(Player player, Keys keys)
	{
		player.setHasTeleported(false);
	}

	public void processInput(Player player, Keys keys)
	{
		if (keys.isKeyPressed(KeyEvent.VK_LEFT) && keys.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			if (player.getdx() > 0)
			{
				player.incrementdx(-ACCEL);
				if (player.getdx() < 0)
					player.setdx(0);
			}
			else if (player.getdx() < 0)
			{
				player.incrementdx(ACCEL);
				if (player.getdx() > 0)
					player.setdx(0);
			}
		}
		else if (keys.isKeyPressed(KeyEvent.VK_LEFT))
		{
			if (player.getdx() < 0)
				player.incrementdx(-ACCEL);
			else
				player.incrementdx(-DECCEL);

			if (player.getdx() < -MAX_VELOCITY) // May take out; makes it so player will not exceed max velocity
				player.incrementdx(ACCEL);

		}
		else if (keys.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			if (player.getdx() > 0)
				player.incrementdx(ACCEL);
			else
				player.incrementdx(DECCEL);

			if (player.getdx() > MAX_VELOCITY) // May take out; makes it so player will not exceed max velocity
				player.incrementdx(-ACCEL);

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

		// Qualified above stuff so that he will deccelerate normally after exiting teleport
		// Make sure velocity is within limits
		/*
		 * if (player.getdx() < -MAX_VELOCITY) player.setdx(-MAX_VELOCITY); if (player.getdx() > MAX_VELOCITY) player.setdx(MAX_VELOCITY);
		 */

		if (keys.wasKeyJustPressed(KeyEvent.VK_LEFT))
			player.setFacingRight(false);
		if (keys.wasKeyJustPressed(KeyEvent.VK_RIGHT))
			player.setFacingRight(true);

		// Fix the animation
		if (player.getdx() > 0)
			player.setFacingRight(true);
		else if (player.getdx() < 0)
			player.setFacingRight(false);

		if (player.getdy() > 0.4) // 0.4 because .5 and greater constitutes an actual move
			player.changeState(Player.FALLING);

		if (keys.isKeyPressed(KeyEvent.VK_Z))
			player.changeState(Player.JUMPING);

		if (keys.wasKeyJustPressed(KeyEvent.VK_X) && !player.getHasTeleported())
			player.changeState(Player.TELEPORTING);

		if (player.getdx() != 0)
			running = true;
		else
			running = false;

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
		if (running)
		{
			if (player.getFacingRight())
				return rightAnimation.getImage();
			else
				return leftAnimation.getImage();
		}
		else
		{
			if (player.getFacingRight())
				return rightAnimationStanding.getImage();
			else
				return leftAnimationStanding.getImage();
		}
	}

	public void exit(Player player)
	{
	}
}
