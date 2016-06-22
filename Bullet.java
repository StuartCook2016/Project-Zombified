package zombified.game;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * 
 * This class is used to create a bullet object and defines the movement and
 * collision attributes. The bullet image is loaded and stored in an Image
 * object.
 * 
 * @author Stuart Cook
 *
 */
public class Bullet implements CollideAble {

	/** Used for the screen position and movement */
	private int x;
	private int y;
	private int movementSpeed;

	/** Used to store the bullet image */
	private Image image;

	/** Used to determine if the bullet is dead */
	private boolean deadBullet;

	/**
	 * 
	 * Class constructor
	 * 
	 * sets the {@code #movementSpeed} and location in which to draw a bullet.
	 * The bullet image is loaded {@link #loadBulletImage()} and the direction
	 * in which the bullet will travel is also defined.
	 * 
	 * @param player
	 *            an instance of the player
	 */
	public Bullet(Player player) {

		this.movementSpeed = 10 * player.getDirection();
		loadBulletImage();

		this.y = 482 - (player.getImage().getHeight(null) - 37);

		if (player.getDirection() > 0) {
			this.x = player.getX() + player.getImage().getWidth(null);
		} else {
			this.x = player.getX();
		}
	}

	/**
	 * This method loads the image if it can be retrieved from the set location
	 * and stores the {@code #image} in an Image objects ready to be used.
	 * 
	 * @exception IOException
	 *                images can not be retrieved.
	 */
	private void loadBulletImage()
	{

		try {
			this.image = ImageIO.read(ResourceLoader.load("BulletScaled.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * This method checks if this object has collided with an instance of a
	 * colliable zombie if the condition in the statement is met then this
	 * {@code #deadBullet} is true.
	 * 
	 * @param collide
	 *            an instance of a collidable object.
	 */
	@Override
	public void collide(CollideAble collide)
	{

		if (collide instanceof Zombie) {
			deadBullet = true;
		}

	}

	/**
	 * This method checks if this object has left the screen, if the condition
	 * in the statement is met then{@code #deadBullet} is true.
	 * 
	 */
	public boolean deadBullet()
	{

		if (this.x < 0 || this.x > MainMenu.WIDTH) {
			deadBullet = true;
		}

		return deadBullet;
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
	 * This method is used to return an Image object {@code #image} which can be
	 * painted to the screen.
	 * 
	 * @return The image stored in this Image object.
	 */
	public Image getImage()
	{

		return image;
	}

}
