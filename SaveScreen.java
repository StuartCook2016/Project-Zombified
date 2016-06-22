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
 * 
 * This class creates the save screen which is used to enter names and save
 * scores. Names and score will be drawn to the screen.
 * 
 * @author Stuart Cook
 *
 */
@SuppressWarnings("serial")
public class SaveScreen extends JPanel implements ActionListener {

	/** An instance of the ContentManager */
	private ContentManager cm;

	private boolean newHighScore;
	private char[] newName;
	private JButton save = new JButton("Save");
	private JButton up = new JButton("Character Up");
	private JButton down = new JButton("Character Down");
	private JButton next = new JButton("Next Character");
	private JButton last = new JButton("Last Character");
	private int currentChar;

	/**
	 * Class contructor
	 * 
	 * Creates a new panel in which to add components and draw text on to. The
	 * background color is set, The layout of this panel is set to an absolute
	 * layout then the {@link #init()} method is called.
	 * 
	 * 
	 * @param cm
	 *            An instance of the ContentManager
	 * @see #init()
	 */
	public SaveScreen(ContentManager cm) {
		super();
		this.cm = cm;
		this.setLayout(null);
		init();

	}

	/**
	 * This method sets this panels background colour and then gets a score and
	 * checks if it is a new high score. Default characters are set then the
	 * {@link #buttons()} is called.
	 */
	public void init()
	{

		setBackground(new Color(0, 0, 0));

		newHighScore = SaveFile.hsd.newHighscore(SaveFile.hsd.getCheckScores());
		if (newHighScore) {
			newName = new char[] { 'A', 'A', 'A' };
			currentChar = 0;

		}
		buttons();

	}

	/**
	 * This method is used to set all button components a size and screen
	 * position. Help text is also assigned to each button component along with
	 * an actionlistener, then each button component is added to this panel.
	 */
	private void buttons()
	{
		save.setBounds(240, 530, 100, 24);
		save.setToolTipText("Click here to save your name and score");
		save.addActionListener(this);
		this.add(save);
		up.setBounds(220, 435, 130, 24);
		up.setToolTipText("Click here to change the character");
		up.addActionListener(this);
		this.add(up);
		down.setBounds(220, 470, 130, 24);
		down.setToolTipText("Click here to change the character");
		down.addActionListener(this);
		this.add(down);
		next.setBounds(365, 470, 120, 24);
		next.setToolTipText("Click here to move to the next character");
		next.addActionListener(this);
		this.add(next);
		last.setBounds(80, 470, 120, 24);
		last.setToolTipText("Click here to move to the previous character");
		last.addActionListener(this);
		this.add(last);
	}

	/**
	 * This method is used to set the color and font and text which is then
	 * drawn to the screen when this panel is created.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (!newHighScore) {
			cm.setCurrentState(ContentManager.MAINMENUSTATE);
			return;
		}
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		g2.drawString("SAVE SCREEN", 165, 50);

		String string;

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		string = "New High Score: " + SaveFile.hsd.getCheckScores();
		g2.drawString(string, 125, 250);

		for (int i = 0; i < newName.length; i++) {

			g2.drawString(Character.toString(newName[i]), 210 + 60 * i, 350);

		}
		g2.setColor(Color.RED);
		g2.drawLine(210 + 60 * currentChar, 360, 240 + 60 * currentChar, 360);
	}

	/**
	 * This method is used to control the changing of text and is also used to
	 * return the user to the main menu
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == save) {
			if (newHighScore) {
				SaveFile.hsd.addScore(SaveFile.hsd.getCheckScores(), new String(newName));
				SaveFile.save();
			}
			cm.setCurrentState(ContentManager.MAINMENUSTATE);

		}
		if (e.getSource() == down) {
			if (newName[currentChar] == ' ') {
				newName[currentChar] = 'Z';
			} else {
				newName[currentChar]--;
				if (newName[currentChar] < 'A') {
					newName[currentChar] = ' ';
				}
			}
			this.repaint();
		}
		if (e.getSource() == up) {
			if (newName[currentChar] == ' ') {
				newName[currentChar] = 'A';
			} else {
				newName[currentChar]++;
				if (newName[currentChar] > 'Z') {
					newName[currentChar] = ' ';
				}
			}
			this.repaint();
		}
		if (e.getSource() == next) {
			if (currentChar < newName.length - 1) {
				currentChar++;
			}

			this.repaint();
		}
		if (e.getSource() == last) {
			if (currentChar > 0) {
				currentChar--;
			}

			this.repaint();
		}

	}

}
