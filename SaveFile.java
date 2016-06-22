package zombified.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

/**
 * 
 * This class is used to load/save or create a new savefile
 * 
 * @author Stuart Cook
 *
 */
public class SaveFile {

	/** A static instance of the HighscoreData Class */
	public static HighscoreData hsd;

	/**
	 * This method locates the file in which to write to and using the instance
	 * of the Highscoredata writes to the file, then closes the file.
	 */
	public static void save()
	{

		try {
			ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream("highscores.txt"));
			fileOut.writeObject(hsd);
			fileOut.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Save File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Unable to save data","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		
		}

	}

	/**
	 * This method checks if a file at the specified location exists, if it does
	 * not a method is called to create a new file {@link #init()}. If the file
	 * does exist the file is read and used by the instance of the HighscoreData
	 * Class then the file is closed.
	 */
	public static void load()
	{

		if (!fileExists()) {
			init();
		}
		ObjectInputStream fileIn;
		try {
			fileIn = new ObjectInputStream(new FileInputStream("highscores.txt"));
			hsd = (HighscoreData) fileIn.readObject();
			fileIn.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Save file not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Unable to load save file","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Unable to load save file","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * When this method is called if the saveFile does not exist a new file is
	 * created at the specified location.
	 * 
	 * @return returns true
	 */
	public static boolean fileExists()
	{
		File saveFile = new File("highscores.txt");
		return saveFile.exists();
	}

	/**
	 * Creates a new instance of the HighscoreData class and uses this instance
	 * in order to call the default layout of the savefile and then saves the
	 * file using {@link #save()}.
	 */
	public static void init()
	{
		hsd = new HighscoreData();
		hsd.init();
		save();
	}

}
