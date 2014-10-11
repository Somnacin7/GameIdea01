package entity.player;

import java.awt.image.BufferedImage;

import utility.Keys;

public interface PlayerState
{
	public void init(); // Should initialize Animation
	public void enter(Player player, Keys keys);
	public void processInput(Player player, Keys keys);
	public void update(Player player);
	public BufferedImage getImage(Player player);
	public void exit(Player player);
}
