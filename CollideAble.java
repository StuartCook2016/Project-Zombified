package zombified.game;

import java.awt.Image;

/**
 * 
 * This class creates a collidable interface. Any other classes that inherit
 * this class must implement collidable and must cotain the methods declared. As
 * functions can not be programmed within each method of this class.
 * 
 * 
 * @author Stuart Cook
 *
 */
public interface CollideAble {

	/**
	 * This method checks if an object has collided with an instance of a
	 * colliable object
	 * 
	 * @param collide
	 *            an instance of a collidable object.
	 */
	public void collide(CollideAble collide);

	/**
	 * This method is used to get an objects position
	 * 
	 * @return x position
	 */
	public int getX();

	/**
	 * This method is used to get an image object
	 * 
	 * @return Image object
	 */
	public Image getImage();
}
