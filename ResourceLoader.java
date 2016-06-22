package zombified.game;

import java.io.InputStream;

/**
 * 
 * @purpose The purpose of this class is to find and load images
 *
 * @author Stuart Cook
 */
public class ResourceLoader {
	public static InputStream load(String path)
	{
		/**
		 * 
		 * finds the location of the requested file and then returns the file
		 * path
		 * 
		 * @param path
		 * @return input
		 */
		InputStream input = ResourceLoader.class.getResourceAsStream("/" + path);

		if (input == null) {

			input = ResourceLoader.class.getResourceAsStream("/" + path);

		} // end if

		return input;
	}// end load
}
