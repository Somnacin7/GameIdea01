package utility;

import java.awt.event.KeyEvent;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Keys
{

	public HashMap<Integer, Boolean> hash, processedHash, lastProcessedHash;

	public Keys()
	{
		hash = new HashMap<Integer, Boolean>()
		{
			{
				// Movement keys
				put(KeyEvent.VK_UP, false);
				put(KeyEvent.VK_DOWN, false);
				put(KeyEvent.VK_LEFT, false);
				put(KeyEvent.VK_RIGHT, false);

				// Action keys
				put(KeyEvent.VK_Z, false);
				put(KeyEvent.VK_X, false);
				put(KeyEvent.VK_C, false);
				
				// Menu nav keys
				put(KeyEvent.VK_ENTER, false);
			}
		};

		processedHash = new HashMap<Integer, Boolean>()
		{
			{
				// Movement keys
				put(KeyEvent.VK_UP, false);
				put(KeyEvent.VK_DOWN, false);
				put(KeyEvent.VK_LEFT, false);
				put(KeyEvent.VK_RIGHT, false);

				// Action keys
				put(KeyEvent.VK_Z, false);
				put(KeyEvent.VK_X, false);
				put(KeyEvent.VK_C, false);
				
				// Menu nav keys
				put(KeyEvent.VK_ENTER, false);
			}
		};

		lastProcessedHash = new HashMap<Integer, Boolean>()
		{
			{
				// Movement keys
				put(KeyEvent.VK_UP, false);
				put(KeyEvent.VK_DOWN, false);
				put(KeyEvent.VK_LEFT, false);
				put(KeyEvent.VK_RIGHT, false);

				// Action keys
				put(KeyEvent.VK_Z, false);
				put(KeyEvent.VK_X, false);
				put(KeyEvent.VK_C, false);
				
				// Menu nav keys
				put(KeyEvent.VK_ENTER, false);
			}
		};

	}

	// Evaluates whether the given key is pressed, and returns true if it is
	public boolean isKeyPressed(int keyEvent)
	{

		if (hash.containsKey(keyEvent))
			return (hash.get(keyEvent)) ? true : false;
		else
			return false;

	}

	public boolean wasKeyJustPressed(int keyEvent)
	{
		if (hash.containsKey(keyEvent))
		{
			if (processedHash.get(keyEvent) && !lastProcessedHash.get(keyEvent))
			{
				return true;
			}
		}
		return false;
	}

	public void keyPressed(KeyEvent e)
	{

		int k = e.getKeyCode();
		if (hash.containsKey(k))
		{
			hash.put(k, true);
		}
		
	}

	public void keyReleased(KeyEvent e)
	{

		int k = e.getKeyCode();
		if (hash.containsKey(k))
		{
			hash.put(k, false);
		}
	}

	public void process()
	{
		lastProcessedHash.putAll(processedHash);
		processedHash.putAll(hash);
	}

}