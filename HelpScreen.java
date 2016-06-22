package zombified.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HelpScreen extends JPanel {

	/** An instance of the ContentManager */
	private ContentManager cm;

	/** Component being added to this panel */
	private JButton mainMenu = new JButton("Main Menu");

	/**
	 * Class contructor
	 * 
	 * Creates a new panel in which to add components and draw text on to. The
	 * background color is set, The layout of the this panel is set to an
	 * absolute layout in order to manually position components and actions are
	 * set using {@link #buttons()}. Also the background color is set.
	 * 
	 * 
	 * @param cm
	 *            An instance of the ContentManager
	 * @see #buttons()
	 */
	public HelpScreen(ContentManager cm) {
		super();
		this.cm = cm;
		this.setLayout(null);
		this.setBackground(new Color(0, 0, 0));
		buttons();
	}

	/**
	 * This method adds a component to this panel which is used to return to the
	 * main menu screen by use of the ContentManager instance.
	 */
	private void buttons()
	{

		mainMenu.setBounds(240, 530, 100, 24);
		mainMenu.setToolTipText("Click here to return to the main menu");
		this.add(mainMenu);
		mainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.MAINMENUSTATE);
			}
		});

	}

	/**
	 * This method is used to draw all text to the screen. position/fonts and
	 * font colors are also set within this method.
	 * 
	 * @param A
	 *            graphics object
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics g2 = (Graphics2D) g;

		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		g2.drawString("ZOMBIFIED HELP", 125, 50);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Chiller", Font.BOLD, 36));
		g2.drawString("Game Objective", 11, 95);

		g2.setFont(new Font("Arial", Font.PLAIN, 18));
		g2.drawString("You have one life, You must survive as many days as possible by killing", 11, 125);
		g2.drawString("all remaining zombies that appear per day.", 11, 143);

		g2.setFont(new Font("Chiller", Font.BOLD, 36));
		g2.drawString("Controls (Default)", 11, 200);

		g2.setFont(new Font("Arial", Font.PLAIN, 18));
		g2.drawString("Use the left and right arrow keys in order to move the main character,", 11, 230);
		g2.drawString("press the spacebar to shoot. The movement keys can be changed in", 11, 250);
		g2.drawString("the options menu.", 11, 270);

		g2.setFont(new Font("Chiller", Font.BOLD, 36));
		g2.drawString("Audio", 11, 325);

		g2.setFont(new Font("Arial", Font.PLAIN, 18));
		g2.drawString("The BGM (Background Music) and SFX (Sound FX) can be adjusted in", 11, 355);
		g2.drawString("the options menu.", 11, 374);

		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		g2.drawString("Now go forth survivor and....", 70, 450);
		g2.drawString("Kill Them ALL!", 155, 500);

	}

}
