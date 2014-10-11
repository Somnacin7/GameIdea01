package gameState;

import java.awt.Graphics2D;

import utility.Keys;

public abstract class GameState
{
	protected GameStateManager gsm;
	

	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void processInput();
}
