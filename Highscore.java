package zombified.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * 
 * This class creates the highscore screen by drawing saved file data directly
 * to the screen.
 * 
 * @author Stuart Cook
 *
 */

@SuppressWarnings("serial")
public class Highscore extends JPanel {

	/** An instance of the ContentManager */
	private ContentManager cm;

	/** Component to be added to this panel */
	private JButton mainMenu = new JButton("MainMenu");

	/** Arrays used for storing highscore and names */
	private long[] highScores;
	private String[] names;

	/** Used to display the names and scores */
	private String sNames = " ";
	private String sHighscore = " ";

	/**
	 * Class contructor
	 * 
	 * Creates a new panel in which to add components and draw text on to. The
	 * layout is set to an absolute layout and the {@link #highscoreLayout()} is
	 * called in order to load files.
	 * 
	 * 
	 * @param cm
	 *            An instance of the ContentManager
	 * @see #highscoreLayout()
	 */
	public Highscore(ContentManager cm) {

		super();
		this.cm = cm;
		this.setLayout(null);
		highscoreLayout();

	}

	/**
	 * This method is used to load a save file and stores the data retrieved
	 * into variables. The background color is set and the {@link #buttons()}
	 * method is called.
	 */
	public void highscoreLayout()
	{
		SaveFile.load();
		highScores = SaveFile.hsd.getHighScores();
		names = SaveFile.hsd.getNames();

		setBackground(new Color(0, 0, 0));
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
	 * The highscores and names are drawn to the screen using this method all
	 * text position, fonts and sizes and also there color is defined
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;

		for (int i = 0; i < highScores.length; i++) {
			sNames = String.format("%2d. %8s", i + 1, names[i]);
			sHighscore = String.format("%8s", highScores[i]);

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Chiller", Font.BOLD, 36));
			g2.drawString(sNames, 150, 130 + 40 * i);
			g2.drawString(sHighscore, 340, 130 + 40 * i);

		}

		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		g2.drawString("ZOMBIFIED HIGHSCORES", 70, 50);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Chiller", Font.BOLD, 36));
		g2.drawString("Pos", 150, 90);
		g2.drawString("Name", 260, 90);
		g2.drawString("Score", 390, 90);
	}
}
