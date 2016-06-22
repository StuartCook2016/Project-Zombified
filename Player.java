package zombified.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * 
 * This class is used to create a player object and defines the movement and
 * collision attributes, also defines the players starting position. The player images are also loaded and stored in Image
 * objects. Audio for the player is also loaded.
 * 
 * 
 * @author Stuart Cook
 *
 */
public class Player implements CollideAble {

	/** Used for the screen position and movement */
	private int x;
	private int y;
	private int movementSpeed;

	/** Used to determine if the game has been played */
	private boolean hasPlayed = false;

	/** Used to determine if the player is dead */
	private boolean dead = false;

	/** Used to store player images */
	private Image image;
	private Image imageL;
	private Image imageR;

	/** Location of audio files */
	private String gun = "resources/M1.wav";
	private String playerDead = "resources/Death.wav";

	/**
	 * This is used to determine if the player is facing left or right.
	 */
	private int direction = -1;

	/**
	 * Class constructor loads the images required using
	 * {@link #loadPlayerImage()} then sets up the player using
	 * {@link #playerSetup()}.
	 * 
	 */
	public Player() {
		loadPlayerImage();
		playerSetup();

	}

	/**
	 * This method loads the images if they can be retrieved from there set
	 * location and stores them in Image objects ready to be used.
	 * 
	 * @exception IOException
	 *                images can not be retrieved.
	 */
	private void loadPlayerImage()
	{
		try {
			this.imageL = ImageIO.read(ResourceLoader.load("PlayerLeftScaled.gif"));
			this.image = imageL;
			this.imageR = ImageIO.read(ResourceLoader.load("PlayerRightScaled.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		;

	}

	/**
	 * This method is used to set the players starting position using this
	 * {@code #x} and this {@code #y} the {@code #movementSpeed} is also set
	 * along with a boolean value {@code #dead} which indicates the player is
	 * not dead.
	 */
	public void playerSetup()
	{
		this.x = (MainMenu.WIDTH / 2) - image.getWidth(null);
		this.movementSpeed = 2;
		dead = false;
		this.y = 482 - image.getHeight(null);
	
	}

	/**
	 * This method is used to check if the player can move left. only if both
	 * the conditions are met in the conditional statement can the player move
	 * left.
	 * 
	 */
	public void moveLeft()
	{
		int temp = this.x - movementSpeed;

		if (!dead && !(temp < 0)) {
			this.image = imageL;
			this.x -= movementSpeed;
			direction = -1;
		}

	}

	/**
	 * This method is used to check if the player can move right. only if both
	 * the conditions are met in the conditional statement can the player move
	 * right.
	 * 
	 */
	public void moveRight()
	{
		int temp = this.x + movementSpeed;

		if (!dead && !(temp > MainMenu.WIDTH - image.getWidth(null))) {
			this.image = imageR;
			this.x += movementSpeed;
			direction = 1;
		}

	}

	/**
	 * This method checks if this object has collided with a collidable object
	 * 
	 * @param collide
	 *            an instance of a collidable object.
	 * @return True if this methods conditional statements are met otherwise
	 *         return false.
	 */
	public boolean hasCollided(CollideAble collide)
	{
		int collidePosition = collide.getX() + collide.getImage().getWidth(null) - 30;
		int playerCollideRight = this.x + this.image.getWidth(null) - 30;

		if (collidePosition > this.x + 5 && this.x + 5 + this.image.getWidth(null) > collidePosition - 3) {

			return true;
		}
		if (collide.getX() <= playerCollideRight && collide.getX() > this.x - 5) {

			return true;
		}
		return false;

	}

	/**
	 * This method is used to get the players {@code #direction}
	 * 
	 * @return current {@code #direction} value
	 */
	public int getDirection()
	{
		return direction;
	}

	/**
	 * This method is used to get the players {@code #x} position
	 * 
	 * @return current {@code #x} value
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * This method is used to get the players {@code #y} position
	 * 
	 * @return current {@code #y} value
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * This method is used to get the players {@code #movementSpeed}
	 * 
	 * @return current {@code #movementSpeed} value
	 */
	public int getMovementSpeed()
	{
		return movementSpeed;
	}

	/**
	 * This method is used to get the players {@code #image}
	 * 
	 * @return current Image object
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * 
	 * This method is used to determine if this object is dead, the default
	 * setting is false. However {@link #collide(CollideAble)} can change the
	 * value to true.
	 * 
	 * @return this object as a boolean value.
	 */
	public boolean isDead()
	{
		return dead;
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
	public void playerDead()
	{

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(playerDead));
			Clip deadPlayer = AudioSystem.getClip();
			deadPlayer.open(audioIn);
			FloatControl floatControl = (FloatControl) deadPlayer.getControl(FloatControl.Type.MASTER_GAIN);
			floatControl.setValue(Options.sfxVolume);
			deadPlayer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method checks if this object has collided with an instance of a
	 * colliable zombie if the condition in the statement is met then this
	 * {@code #dead} is true.
	 * 
	 * @param collide
	 *            an instance of a collidable object.
	 */
	@Override
	public void collide(CollideAble collide)
	{

		if (collide instanceof Zombie) {
			if (!hasPlayed) {
				playerDead();

				hasPlayed = true;
			}

			this.dead = true;

		}

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
	public void gunShot()
	{

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(gun));
			Clip gunShot = AudioSystem.getClip();
			gunShot.open(audioIn);
			FloatControl floatControl = (FloatControl) gunShot.getControl(FloatControl.Type.MASTER_GAIN);
			floatControl.setValue(Options.sfxVolume);
			gunShot.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
