package zombified.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * This class is used to create the playable section of the system. All Game
 * objects and test is drawn to the screen using
 * {@link #paintComponent(Graphics)}. Through this class the user is able to
 * control the game and also navigate to other user interface screens.
 * 
 * 
 * @author Stuart Cook
 *
 */
@SuppressWarnings("serial")
public class Level extends JPanel {

	/** All components to be added to this panel */
	private JButton mainMenu = new JButton("Main Menu");
	private JButton restart = new JButton("Restart");
	private JButton exit = new JButton("Exit");
	private JButton save = new JButton("Save");

	/** An Image object used to hold the background image */
	private Image backgroundImage;

	/** Used to store game objects */
	private ArrayList<RainDrop> rain = new ArrayList<RainDrop>();
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
	private ArrayList<Zombie> deadZombies = new ArrayList<Zombie>();

	/** Used for audio playback */
	private Clip backgroundMusic;

	/** Used to control the value of the games volume */
	private FloatControl fControl;

	/** An instance of the Player class */
	private Player player;

	/** An instance of the ContentManager class */
	private ContentManager cm;

	/** Variables to control the functions of the game */
	private float bgVolume = Options.bgVolume;
	private int currentDay = 1;
	private int numberOfZombies = 1;
	private int remainingZombies = 1;
	private int frameWait = 0;
	private int minWait = 20;
	private int randomWait = 1;
	private int currentFrame = 0;
	private long totalScore = 0;
	private int numberOfTries;
	private int zombiesKilled;
	private boolean left = false;
	private boolean right = false;

	/**
	 * The location of the audio file to be used is stored.
	 */
	private String ambientSound = "resources/AmbientSound.wav";

	/**
	 * Used to set the speed of the drawing {@link #fpsSetter(long)}
	 * 
	 * @see #fpsSetter(long)
	 */
	private int fps = 120;
	private int skipTicks = 1000 / fps;

	/**
	 * 
	 * Class constructor
	 * 
	 * Creates a new Panel for this screen and uses the ContentManager frame.
	 * The layout for this panel is set to an absolute layout in order for the
	 * text and components to be manually placed on the panel. A new instance of
	 * the Player class is created. The background image is loaded using
	 * {@link #levelLayout()}. The keyboard is setup using {@link #movePlayer()}
	 * also the background sound is loaded {@link #ambientSound()}, the buttons
	 * are also setup {@link #buttons()}.
	 * 
	 * @param cm
	 *            An instance of the ContentManager class
	 * 
	 * @see ContentManager
	 * @see #levelLayout()
	 * @see #buttons()
	 * @see #movePlayer()
	 * @see #ambientSound()
	 */
	public Level(ContentManager cm) {
		super();
		this.cm = cm;
		this.setLayout(null);
		this.player = new Player();
		levelLayout();
		movePlayer();
		ambientSound();
		buttons();
	}

	/**
	 * This method is used to set the size and position of the button
	 * components and assigns actions to each one.
	 */
	private void buttons()
	{

		mainMenu.setBounds(20, 535, 100, 20);
		mainMenu.setToolTipText("Click here to return to the main menu");
		mainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cm.setCurrentState(ContentManager.MAINMENUSTATE);
				backgroundMusic.stop();
			}
		});

		restart.setBounds(180, 535, 100, 20);
		restart.setToolTipText("Click here to restart the game");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (player.isDead()) {
					clearLevel();
					levelLayout();
					movePlayer();
					numberOfTries++;
					zombiesKilled = 0;
				}
			}
		});
		exit.setBounds(490, 535, 100, 20);
		exit.setToolTipText("Click here to exit Zombified");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		save.setBounds(330, 535, 100, 20);
		save.setToolTipText("Click here to save your score");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SaveFile.load();
				SaveFile.hsd.setCheckScores(totalScore);
				cm.setCurrentState(ContentManager.SAVESCREENSTATE);
				backgroundMusic.stop();
			}
		});

	}

	/**
	 * This method sets the background color and also loads and stores this
	 * background image in this {@code #backgroundImage}
	 * 
	 * @exception IOException
	 *                if the file can not be found
	 */
	public void levelLayout()
	{

		this.setBackground(new Color(144, 108, 63));

		try {
			this.backgroundImage = ImageIO.read(ResourceLoader.load("backgroundScaled.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Unable to load image file","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		;

	}

	/**
	 * This method is used to read in an audio file and creates an audio clip
	 * from this file when this method is called the audio clip is played. The
	 * volume for this audio clip is adjustable through the use of this method
	 * 
	 * @exception IOException
	 *                if the file can not be found
	 * @exception UnsupportedAudioFileException
	 *                if the file data is not valid
	 * @exception LineUnavailableException
	 *                if a line of audio data is unavaliable
	 * 
	 */
	private void ambientSound()
	{
		// System.out.println(System.getProperty("user.dir"));
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(ambientSound));
			this.backgroundMusic = AudioSystem.getClip();
			this.backgroundMusic.open(audioIn);
			this.backgroundMusic.start();
			this.backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
			this.fControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
			this.fControl.setValue(bgVolume);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Unable to load audio file","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null,"Audio file not vaild","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			JOptionPane.showMessageDialog(null,"Unable to load audio line","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to setup the keyboard focus manager and also the key
	 * events which will be used to control the playable section of the system.
	 */
	private void movePlayer()
	{
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();

		kfm.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getKeyCode() == Options.leftButton) {
						left = true;

					} else if (e.getKeyCode() == Options.rightButton) {
						right = true;

					}
				}
				return false;
			}
		});

		kfm.addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (e.getKeyCode() == Options.leftButton) {
						left = false;

					} else if (e.getKeyCode() == Options.rightButton) {
						right = false;

					}
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {

						if (bullets.size() < 1 && !player.isDead()) {

							player.gunShot();
							bullets.add(new Bullet(player));
							if (deadBullets.size() >= 10) {
								deadBullets.clear();
							}

						}

					}
				}
				return false;
			}
		});
	}

	/**
	 * This method is used to reset all this classes variables to there starting
	 * values
	 */
	private void clearLevel()
	{

		this.removeAll();
		bgVolume = Options.bgVolume;
		player = null;
		if (player == null) {
			player = new Player();
		}
		totalScore = 0;
		currentDay = 1;
		numberOfZombies = 1;
		remainingZombies = 1;
		rain.clear();
		zombies.clear();
		bullets.clear();
		deadBullets.clear();
		deadZombies.clear();
		backgroundMusic.stop();
		backgroundMusic.start();
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

	}

	/**
	 * This method is used to draw all objects and images to this panel using a
	 * graphics object and is also used to add the button components to the this
	 * panel. Button components are only added to the screen if
	 * {@link #player.isDead()} = true. This method also uses multiple method
	 * calls in order for the system to work. All the methods used are displayed
	 * below.
	 * 
	 * @see #zombieDraw(Graphics2D)
	 * @see #rainDraw(Graphics2D)
	 * @see #bulletDraw(Graphics2D)
	 * @see #cleanUp()
	 * @see #collisionCheck()
	 * @see #checkState()
	 * @see #playerMoving()
	 * @see #fpsSetter(long)
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		long currentTick = System.currentTimeMillis();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Graphics2D g2 = (Graphics2D) g;
		Graphics2D graphicFont = (Graphics2D) g;

		g2d.drawImage(backgroundImage, 0, 0, null);
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);

		drawHUD(g2);

		if (player.isDead()) {
			// add buttons to the mainMenu
			this.add(mainMenu);
			this.add(restart);
			this.add(exit);
			if (totalScore > 0) {
				this.add(save);
			}
			graphicFont.setColor(Color.RED);
			graphicFont.setFont(new Font("Chiller", Font.BOLD, 92));
			graphicFont.drawString("You're DEAD!!", 75, 260);
			backgroundMusic.stop();
		}

		zombieDraw(g2);
		rainDraw(g2);
		bulletDraw(g2);
		cleanUp();
		collisionCheck();
		checkState();
		playerMoving();
		this.revalidate();
		this.repaint();
		fpsSetter(currentTick);
	}

	/**
	 * This method is used to draw all the user interface text to this panel
	 * 
	 * @param g2
	 *            A graphics object which is used for rendering 2-dimensional
	 *            shapes/images and text
	 */
	private void drawHUD(Graphics2D g2)
	{
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 26));
		g2.drawString("Day: " + currentDay, 15, 505);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 18));
		g2.drawString("Tries: " + numberOfTries, 465, 530);

		g2.setColor(Color.WHITE);
		g2.drawString("Zombies Remaining: " + remainingZombies, 200, 505);

		g2.setColor(Color.WHITE);
		g2.drawString("Zombies Killed : " + zombiesKilled, 200, 530);

		g2.setColor(Color.WHITE);
		g2.drawString("Score: " + totalScore, 465, 505);
	}

	/**
	 * This method is used to check if the player is moving left or right.
	 * 
	 */
	private void playerMoving()
	{
		if (left) {
			player.moveLeft();
		} else if (right) {
			player.moveRight();
		}
	}

	/**
	 * Draws rain {@link RainDrop} and creates new rainDrops if the condition is
	 * not met for each rainDrop the positions are updated and drawn to the
	 * screen.
	 * 
	 * @param g2
	 *            A graphics object which is used for rendering 2-dimensional
	 *            shapes/images and text
	 * @see RainDrop
	 */
	private void rainDraw(Graphics2D g2)
	{
		if (rain.size() < 50) {
			rain.add(new RainDrop(MainMenu.WIDTH));
		}
		for (RainDrop rainDrop : rain) {

			rainDrop.updatePosition();
			g2.drawImage(rainDrop.getImage(), rainDrop.getX(), rainDrop.getY(), null);

		}
	}

	/**
	 * This method is used to draw bullets to this panel and update there
	 * position if the bullet leaves the screen or collides with an object it is
	 * removed and added to a list called {@code #deadBullets} in order to be
	 * removed from memory using {@link #cleanUp()}
	 * 
	 * @param g2
	 *            A graphics object which is used for rendering 2-dimensional
	 *            shapes/images and text
	 * 
	 * @see #cleanUp()
	 */
	private void bulletDraw(Graphics2D g2)
	{
		for (Bullet bullet : bullets) {

			bullet.updatePosition();
			g2.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
			if (bullet.deadBullet()) {
				deadBullets.add(bullet);
			}

		}
	}

	/**
	 * This method is used to draw zombies to this panel and update there
	 * positions a zombie is only added if the conditions are met if the zombie
	 * collides with an object it is removed and added to a list called
	 * {@code #deadZombies} in order to be removed from memory using
	 * {@link #cleanUp()}
	 * 
	 * @param g2
	 *            A graphics object which is used for rendering 2-dimensional
	 *            shapes/images and text
	 */

	private void zombieDraw(Graphics2D g2)
	{
		if (zombies.size() + deadZombies.size() < numberOfZombies) {

			if (frameWait == minWait + randomWait) {
				zombies.add(new Zombie());
				frameWait = 0;
				randomWait = (int) (Math.random() * 20);
			} else {
				frameWait++;
			}

		}
		for (Zombie zombie : zombies) {

			zombie.updatePosition();
			g2.drawImage(zombie.getImage(), zombie.getX(), zombie.getY(), null);
			if (zombie.isDead()) {
				remainingZombies--;
				zombiesKilled++;
				this.totalScore += 10;
				deadZombies.add(zombie);

			}
		}
	}

	/**
	 * This method is used to remove objects from the {@code #deadBullets} and
	 * {@code #deadZombies} array lists
	 */
	private void cleanUp()
	{
		for (Bullet bullet : deadBullets) {
			bullets.remove(bullet);

		}

		for (Zombie zombie : deadZombies) {
			zombies.remove(zombie);

		}
	}

	/**
	 * This method is used to check if the player is dead. If the
	 * player.isDead() = true then the zombie objects on the screen will play an
	 * animation. This method is also used to update variables in order for the
	 * game to progress.
	 * 
	 */
	private void checkState()
	{

		if (player.isDead()) {
			if (currentFrame % 20 == 0) {
				for (Zombie zombie : zombies) {
					zombie.updateImage();
				}
				this.currentFrame = 0;
			}
		}
		if (zombies.size() <= 0 && deadZombies.size() == numberOfZombies) {

			numberOfZombies += 1;
			remainingZombies = numberOfZombies;
			currentDay++;
			deadZombies.clear();

		}
		this.currentFrame++;
	}

	/**
	 * This method is used to check if a zombie has collided with the player,
	 * also this method checks if a bullet has collided with a zombie
	 */
	private void collisionCheck()
	{
		for (Zombie zombie : zombies) {

			if (player.hasCollided(zombie)) {

				player.collide(zombie);
				// System.out.println("Player Dead!");
			}
			for (Bullet bullet : bullets) {
				if (zombie.hasCollided(bullet)) {
					zombie.zombieDead();
					zombie.collide(bullet);
					bullet.collide(zombie);

				}
			}
		}
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
				//JOptionPane.showMessageDialog(null,"Thread is occupied","Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

	}

}
