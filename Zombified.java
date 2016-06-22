package zombified.game;

import javax.swing.JFrame;

/**
 * 
 * 
 * 
 * Creates and initialises a ContentManager object, assigns all necessary
 * settings in order to create the games frame.
 * 
 * @author Stuart Cook
 * 
 */
public class Zombified {

	/** An instance of the ContentManager class */
	private static ContentManager frame;

	public static void main(String[] args)
	{

		frame = new ContentManager();
		frame.setLocationRelativeTo(null);
		frame.setTitle("Zombified");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setCurrentState(ContentManager.MAINMENUSTATE);
		frame.revalidate();
		frame.repaint();

	}

}
