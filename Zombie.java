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
 * This class is used to create a zombie object and defines the movement and
 * collision attributes. The zombie images are also loaded and stored within
 * this class.
 * 
 * 
 * @author Stuart Cook
 *
 */

public class Zombie implements CollideAble {

	/** Used for the screen position and movement */
	private int x;
	private int y;
	private int movementSpeed;

	/** Used to determine which direction the zombie is facing */
	private boolean left;

	/** Used to store zombie images */
	private Image imageL;
	private Image imageR;
	private Image image;

	/** boolean value to determine if this object has been collided with */
	private boolean dead;

	/**
	 * The location of the audio file to be used is stored.
	 */
	private String zombie = "resources/Zombie.wav";

	/**
	 * Class constructor
	 * 
	 * loads the images required {@link #loadZombieImage()} and then sets the
	 * movement speed. Each time this object is created it is assigned a random
	 * {@code #x} position which is used to define the spawn location and also
	 * the image to be used. This objects {@code #y} position is also defined.
	 * 
	 * @see #loadZombieImage()
	 * 
	 */
	public Zombie() {

		loadZombieImage();

		this.movementSpeed = 1 + (int) (Math.random() * 2);

		if ((Math.random() * 10) < 5) {
			this.x = -60;
			this.image = imageR;
			this.left = false;
		} else {
			this.x = MainMenu.WIDTH + 10;
			this.movementSpeed *= -1;
			this.left = true;
		}

		this.y = 480 - image.getHeight(null);

	}

	/**
	 * This method loads the images if they can be retrieved from there set
	 * location and stores them in Image objects ready to be used.
	 * 
	 * @exception IOException
	 *                images can not be retrieved.
	 */
	private void loadZombieImage()
	{
		try {
			this.imageL = ImageIO.read(ResourceLoader.load("ZombieLeftScaled.gif"));
			this.image = imageL;
			this.imageR = ImageIO.read(ResourceLoader.load("ZombieRightScaled.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		;
	}

	/**
	 * This method adds the {@code #movementSpeed} to this objects {@code #x}
	 * position.
	 */
	public void updatePosition()
	{
		this.x += movementSpeed;

	}

	/**
	 * This method is used to return this objects {@code #x} position.
	 * 
	 * @return an integer value which is this objects {@code #x} position.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * This method is used to return this objects {@code #y} position.
	 * 
	 * @return an integer value which is this objects {@code #y} position.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * This method is used to return this objects {@code #movementSpeed}.
	 * 
	 * @return an integer value which is this objects {@code #movementSpeed}.
	 */
	public int getMovementSpeed()
	{
		return movementSpeed;
	}

	/**
	 * This method is used to return an Image object {@code #imageL} which can
	 * be painted to the screen.
	 * 
	 * @return The image stored in this Image object.
	 */
	public Image getImageL()
	{
		return imageL;
	}

	/**
	 * This method is used to return an Image object {@code #imageR} which can
	 * be painted to the screen.
	 * 
	 * @return The image stored in this Image object.
	 */
	public Image getImageR()
	{
		return imageR;
	}

	/**
	 * This method is used to return an Image object {@code #image} which can be
	 * painted to the screen.
	 * 
	 * @return The image stored in this Image object.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * This method checks if this object has collided with an instance of a
	 * colliable bullet if the condition in the statement is met then this
	 * {@code #dead} is true.
	 * 
	 * @param collide
	 *            an instance of a collidable object.
	 */
	@Override
	public void collide(CollideAble collide)
	{

		if (collide instanceof Bullet) {

			this.dead = true;
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

		if (collidePosition > this.x && this.x + this.image.getWidth(null) > collidePosition) {
			return true;
		}
		if (collide.getX() <= playerCollideRight && collide.getX() > this.x) {
			return true;
		}
		return false;

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
	 * This method sets this zombie object {@code #movementSpeed} to zero and
	 * uses this {@code #image} to start switching between this {@code #imageL}
	 * and this {@code #imageR}
	 * 
	 */
	public void updateImage()
	{

		this.movementSpeed = 0;

		if (left) {
			this.image = imageR;
			this.left = false;
		} else {
			this.image = imageL;
			this.left = true;
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
	public void zombieDead()
	{

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(zombie));
			Clip deadZombie = AudioSystem.getClip();
			deadZombie.open(audioIn);
			FloatControl floatControl = (FloatControl) deadZombie.getControl(FloatControl.Type.MASTER_GAIN);
			floatControl.setValue(Options.sfxVolume);
			deadZombie.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

}
