package zombified.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * This class creates the options screen. This class controls the audio volumes
 * and the change control features.
 * 
 * @author Stuart Cook
 *
 */
@SuppressWarnings("serial")
public class Options extends JPanel implements ActionListener {

	/** An instance of the ContentManager */
	private ContentManager cm;

	/** All components being added to this panel */
	private JTextArea displayBgVolume = new JTextArea();
	private JTextArea displaySfxVolume = new JTextArea();
	private JTextArea currentControl1 = new JTextArea();
	private JTextArea currentControl2 = new JTextArea();
	private JButton decreaseBGM = new JButton("<");
	private JButton increaseBGM = new JButton(">");
	private JButton decreaseSFX = new JButton("<");
	private JButton increaseSFX = new JButton(">");
	private JButton control1 = new JButton("Left & Right =  A & D");
	private JButton control2 = new JButton("Left & Right =  <= & =>");
	private JButton mainMenu = new JButton("Main Menu");

	/** Used to change the default controls */
	public static int leftButton = KeyEvent.VK_LEFT;
	public static int rightButton = KeyEvent.VK_RIGHT;

	/**
	 * Set to default values and used to control the volume of the games music
	 * and sound effects
	 */
	public static float bgVolume = -30.0f;
	public static float sfxVolume = -10.0f;

	/**
	 * Class contructor
	 * 
	 * Creates a new panel in which to add components and draw text on to. The
	 * background color is set, The layout of the components are all set
	 * {@link #optionLayout()} and actions are set using {@link #buttons()}.
	 * 
	 * 
	 * @param cm
	 *            An instance of the ContentManager
	 * @see #optionLayout()
	 * @see #buttons()
	 */
	public Options(ContentManager cm) {
		super();
		this.cm = cm;
		this.setBackground(new Color(0, 0, 0));
		optionLayout();
		buttons();
	}

	/**
	 * This method sets this panels layout to an absolute layout and assigns all
	 * components a size and position and adds them to this panel.
	 */

	private void optionLayout()
	{

		this.setLayout(null);

		decreaseBGM.setBounds(288, 140, 50, 24);
		decreaseBGM.addActionListener(this);
		decreaseBGM.setToolTipText("Click here to decrease the background musics volume");
		increaseBGM.setBounds(370, 140, 50, 24);
		increaseBGM.addActionListener(this);
		increaseBGM.setToolTipText("Click here to increase the background musics volume");
		decreaseSFX.setBounds(288, 240, 50, 24);
		decreaseSFX.addActionListener(this);
		decreaseSFX.setToolTipText("Click here to decrease the sound effects volume");
		increaseSFX.setBounds(370, 240, 50, 24);
		increaseSFX.addActionListener(this);
		increaseSFX.setToolTipText("Click here to increase the sound effects volume");
		control1.setBounds(180, 370, 236, 24);
		control1.addActionListener(this);
		control1.setToolTipText("Click here to change the controls");
		control2.setBounds(180, 430, 236, 24);
		control2.addActionListener(this);
		control2.setToolTipText("Click here to change the controls");

		displayBgVolume.setBounds(339, 144, 30, 20);
		displayBgVolume.setEditable(false);

		displaySfxVolume.setBounds(339, 244, 30, 20);
		displaySfxVolume.setEditable(false);

		currentControl1.setBounds(250, 400, 100, 20);
		currentControl1.setEditable(false);

		currentControl2.setBounds(250, 462, 100, 20);
		currentControl2.setEditable(false);

		this.add(displayBgVolume);
		this.add(displaySfxVolume);
		this.add(currentControl1);
		this.add(currentControl2);
		this.add(control1);
		this.add(control2);
		this.add(decreaseBGM);
		this.add(increaseBGM);
		this.add(decreaseSFX);
		this.add(increaseSFX);

		displayBgVolume.setText(" " + (int) bgVolume);

		currentControl2.setText("Current");

		displaySfxVolume.setText(" " + (int) sfxVolume);

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
	 * This method provides the panel components with actions that are required
	 * for the features of the option screen to work.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == decreaseBGM) {
			if (bgVolume > -80) {
				bgVolume -= 1.0f;

			}
			displayBgVolume.setText(" " + (int) bgVolume);

		}
		if (e.getSource() == increaseBGM) {
			if (bgVolume < 6) {
				bgVolume += 1.0f;
			}
			displayBgVolume.setText(" " + (int) bgVolume);
		}
		if (e.getSource() == decreaseSFX) {
			if (sfxVolume > -80) {
				sfxVolume -= 1.0f;
			}

			displaySfxVolume.setText(" " + (int) sfxVolume);
		}
		if (e.getSource() == increaseSFX) {
			if (sfxVolume < 6) {
				sfxVolume += 1.0f;
			}

			displaySfxVolume.setText(" " + (int) sfxVolume);

		}
		if (e.getSource() == control1) {
			leftButton = KeyEvent.VK_A;
			rightButton = KeyEvent.VK_D;

			currentControl2.setText("");
			currentControl1.setText("Current");

		}
		if (e.getSource() == control2) {
			leftButton = KeyEvent.VK_LEFT;
			rightButton = KeyEvent.VK_RIGHT;

			currentControl1.setText("");
			currentControl2.setText("Current");
		}

	}

	/**
	 * This method is used to set the color and font and text which is then
	 * drawn to the screen when this panel is created.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics g2 = (Graphics2D) g;

		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 48));
		g2.drawString("ZOMBIFIED OPTIONS", 105, 50);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Chiller", Font.PLAIN, 30));
		g2.drawString("Audio Settings", 235, 90);

		g2.drawString("BGM...........", 165, 165);
		g2.drawRect(155, 100, 280, 80);

		g2.drawString("SFX............", 165, 263);
		g2.drawRect(155, 200, 280, 80);

		g2.setFont(new Font("Arial", Font.PLAIN, 12));
		g2.drawString("Increase", 370, 133);
		g2.drawString("Decrease", 288, 133);

		g2.drawString("Increase", 370, 233);
		g2.drawString("Decrease", 288, 233);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Chiller", Font.PLAIN, 30));
		g2.drawString("Control Settings", 235, 330);
		g2.drawRect(145, 340, 320, 160);

	}

}
