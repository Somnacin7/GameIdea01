package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import utility.Keys;

public class GameStateManager
{
	
	public static int MAIN_MENU = 0,
			LEVEL_1 = 1;
	
	private HashMap<Integer, GameState> stateHash;
	
	private int currentState;
	
	private Keys keys;
	
	
	
	public GameStateManager()
	{
		keys = new Keys();
		
		stateHash = new HashMap<Integer, GameState>();
		stateHash.put(MAIN_MENU, new MainMenuState(this, keys));
		stateHash.put(LEVEL_1, new Level1State(this, keys));
		
		setState(MAIN_MENU);
		
		
		
	}
	
	public void setState(int state)
	{
		this.currentState = state;
		stateHash.get(currentState).init();
	}
	
	public void update()
	{
		stateHash.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		stateHash.get(currentState).draw(g);
	}
	
	public void keyPressed(KeyEvent e)
	{
		keys.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e)
	{
		keys.keyReleased(e);
	}
	
	public Keys getKeys()
	{
		return keys;
	}
	
	public void processInput()
	{
		keys.process();
		stateHash.get(currentState).processInput();
	}
	
	
	
}
