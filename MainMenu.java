package zombified.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * A panel which contains buttons for user navigation and draws a background
 * image and draws rain over this image.
 * 
 * @author Stuart Cook
 */
@SuppressWarnings("serial")
public class MainMenu extends JPanel {

	/** An instance of the ContentManager */
	private ContentManager cm;

	/** All components to be added to this panel */
	private JButton newGame = new JButton("New Game");
	private JButton highScore = new JButton("Highscore");
	private JButton options = new JButton("Options");
	private JButton help = new JButton("Help");
	private JButton exit = new JButton("Exit");

	/** An Image object used to store the background image */
	private Image backgroundImage;

	/** Used to store rain objects which will be drawn to the screen */
	private ArrayList<RainDrop> rain = new ArrayList<RainDrop>();

	/**
	 * Used to set the speed of the drawing {@link #fpsSetter(long)}
	 * 
	 * @see #fpsSetter(long)
	 */
	private int fps = 120;
	private int skipTicks = 1000 / fps;

	/**
	 * This is used to define the final width of this panel.
	 */
	public static final int WIDTH = 600;
	/**
	 * This is used to define the final height of this panel.
	 */
	public static final int HEIGHT = 610;

	/**
	 * 
	 * Class constructor Assigns the main menu to the ContentManager frame and
	 * resizes this container. The UI is created {@link #levelMan()}. The
	 * Components are created and added to this container{@link #buttons()}.
	 * 
	 * @param cm
	 *            An instance of the ContentManager class
	 * 
	 * @see ContentManager
	 * @see #levelMan()
	 * @see #buttons()
	 */
	public MainMenu(ContentManager cm) {

		this.cm = cm;
		this.setBounds(0, 0, WIDTH, HEIGHT);
		levelMan();
		buttons();

	}

	/**
	 * 
	 * Checks if the user interface can be created {@link #CreateUI()}.
	 * 
	 * 
	 * @exception Exception
	 *                if the user interface cannot be created.
	 * @see #CreateUI()
	 */
	public void levelMan()
	{

		try {
			CreateUI();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Unable to load the User Interface");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Checks if the background image can be loaded {@link #loadImage()}. sets
	 * this containers color and uses an absolute layout to position components.
	 * 
	 * @exception IOException
	 *                signals that an I/O exception of some sort has occurred.
	 * @see #loadImage()
	 */
	private void CreateUI()
	{

		try {
			loadImage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Unable to load image file","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		this.setBackground(new Color(144, 108, 63));
		this.setLayout(null);

	}

	/**
	 * Sets the component size and position then adds the components to this
	 * container and adds an ActionListener to each component.
	 * 
	 */
	public void buttons()
	{

		newGame.setBounds(50, 500, 128, 24);
		newGame.setToolTipText("Click here to start a new game");
		options.setBounds(225, 500, 128, 24);
		options.setToolTipText("Click here to go to the options menu");
		highScore.setBounds(405, 500, 128, 24);
		highScore.setToolTipText("Click here to view highscores");
		help.setBounds(470, 535, 60, 24);
		help.setToolTipText("Click here to view the help screen");
		exit.setBounds(530, 535, 55, 24);
		exit.setToolTipText("Click here to exit Zombified");

		this.add(newGame);
		newGame.addActionListener(new ActionListener() {

			/**
			 * 
			 * Calls the setCurrentState method in ContentManager and sends the
			 * method an integer in order to change the screen.
			 * 
			 * @Override Overrides when a component-defined action occurres.
			 * @see ContentManager
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.LEVELSTATE);

			}
		});

		this.add(highScore);
		highScore.addActionListener(new ActionListener() {
			/**
			 * 
			 * 
			 * Calls the setCurrentState method in ContentManager and sends the
			 * method an integer in order to change the screen.
			 * 
			 * @Override Overrides when a component-defined action occurres.
			 * @see ContentManager
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.HIGHSCORESTATE);

			}
		});

		this.add(options);
		options.addActionListener(new ActionListener() {
			/**
			 * 
			 * Calls the setCurrentState method in ContentManager and sends the
			 * method an integer in order to change the screen.
			 * 
			 * @Override Overrides when a component-defined action occurres.
			 * @see ContentManager
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.OPTIONSTATE);

			}
		});

		this.add(help);
		help.addActionListener(new ActionListener() {

			/**
			 * 
			 * Calls the setCurrentState method in ContentManager and sends the
			 * method an integer in order to change the screen.
			 * 
			 * @Override Overrides when a component-defined action occurres.
			 * @see ContentManager
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.HELPSCREENSTATE);
			}
		});

		this.add(exit);
		exit.addActionListener(new ActionListener() {

			/**
			 * 
			 * Terminates the currently running Java Virtual Machine.
			 * 
			 * @Override Overrides when a component-defined action occurres.
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

	}

	/**
	 * 
	 * Stores an image which is located using the ResourceLoader
	 *
	 * @throws IOException
	 *             If Image can not be found.
	 * @see ResourceLoader
	 */
	private void loadImage() throws IOException
	{
		try {
			this.backgroundImage = ImageIO.read(ResourceLoader.load("backgroundScaled.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		;

	}

	/**
	 * Draws rain {@link RainDrop} and other user interface graphics to the
	 * screen. Stores the current time in milliseconds in order to interupt the
	 * drawing of graphics by using the {@link #fpsSetter(long)}.
	 * 
	 * @param g
	 *            Allows graphics to draw onto this container.
	 * @see #fpsSetter(long)
	 * @see RainDrop
	 */
	public void paintComponent(Graphics g)
	{
		long currentTick = System.currentTimeMillis();
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		Graphics2D g2 = (Graphics2D) g;

		g2d.drawImage(backgroundImage, 0, 0, null);

		if (rain.size() < 50) {
			rain.add(new RainDrop(MainMenu.WIDTH));
		}
		for (RainDrop rainDrop : rain) {

			rainDrop.updatePosition();
			g2.drawImage(rainDrop.getImage(), rainDrop.getX(), rainDrop.getY(), null);
			this.revalidate();
			this.repaint();

		}
		g2.setColor(Color.RED);
		g2.setFont(new Font("Chiller", Font.BOLD, 126));
		g2.drawString("ZOMBIFIED", 20, 260);
		fpsSetter(currentTick);
	}

	/**
	 * 
	 * Creates a timer to sleep the thread using the current system time.
	 * {@code skipTicks} is a number which is predefined at the opening of this
	 * class which is then added to the {@code currentTick}. The current system
	 * time is then deducted from the {@code currentTick} and added to
	 * {@code sleepTime} which defines how long the thread sleeps for.
	 * 
	 * 
	 * @param currentTick
	 *            A number given to this method when it is called from the
	 *            {@link #paintComponent(Graphics)}.
	 * @exception InterruptedException
	 *                Thrown when a thread is waiting, sleeping, or otherwise
	 *                occupied.
	 * @see #paintComponent(Graphics).
	 * @see Thread
	 * 
	 */
	private void fpsSetter(long currentTick)
	{

		int sleepTime = 0;

		currentTick += skipTicks;
		sleepTime = (int) (currentTick - (int) System.currentTimeMillis());
		if (sleepTime >= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
