package game;

import javax.swing.JFrame;

public class Main
{
	
	public static void main(String[] args)
	{
		
		JFrame window = new JFrame();
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
		
	}
	
}
