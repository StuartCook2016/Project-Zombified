package zombified.game;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * 
 * This class is used to create a rain object and defines the movement and
 * positioning attributes. The rain image is also loaded and stored within this
 * class.
 * 
 * 
 * @author Stuart Cook
 *
 */
public class RainDrop {
	/** Used for the screen position and movement */
	private int x;
	private int y;
	private int speed;
	private int screenWidth;

	/** Used to store a rain image */
	private Image rainImage;

	/**
	 * 
	 * Class constructor
	 * 
	 * This sets the screen width which is used to give a rain drop a random
	 * starting position and set the speed of the rain drop to a random speed
	 * the rain image is then loading using {@link #loadRainImage()}.
	 * 
	 * @param screenWidth
	 *            The width of the game panel
	 */
	public RainDrop(int screenWidth) {
		this.screenWidth = screenWidth;
		this.x = randomX();
		this.y = 0;
		this.speed = (int) (Math.random() * 10) + 4;
		loadRainImage();
	}

	/**
	 * This method adds the random {@code #speed} to this rain objects
	 * {@code #y} position. when this {@code #y} meets the condition in the
	 * conditional statement the {@code #y} is reset and the rain object is
	 * given a new random {@code #x} position.
	 */
	public void updatePosition()
	{
		this.y += this.speed;

		if (y >= 450) {
			this.x = randomX();
			y = 0;
		}
	}

	/**
	 * 
	 * This method is used to create a random {@code #x} position.
	 * 
	 * @return A random integer
	 */
	private int randomX()
	{
		return (int) (Math.random() * 50) * screenWidth / 50;

	}

	/**
	 * This method loads an image if the image can be retrieved from its set
	 * location and stores it in an Image objects ready to be used.
	 * 
	 * @exception IOException
	 *                images can not be retrieved.
	 */
	private void loadRainImage()
	{
		try {
			this.rainImage = ImageIO.read(ResourceLoader.load("Rain1Scaled.gif"));

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		;
	}

	/**
	 * This method is used to return an Image object {@code #rainImage} which
	 * can be painted to the screen.
	 * 
	 * @return The image stored in this Image object.
	 */
	public Image getImage()
	{
		return this.rainImage;
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

}
