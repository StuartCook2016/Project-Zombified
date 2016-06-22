package zombified.game;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * 
 * 
 * A frame which manages the content being displayed to the user and removes any
 * content which has been created that the user or the system no longer
 * requires.
 * 
 * @author Stuart Cook
 */
@SuppressWarnings("serial")
public class ContentManager extends JFrame {

	private int currentState;
	private Object[] contentStates;
	private MainMenu levelManager;
	private Level level;
	private Options option;
	private Highscore highscore;
	private HelpScreen helpScreen;
	private SaveScreen saveScreen;
	private Image image;

	/**
	 * {@code #NUMCONTENTSTATES} is used to define the final number of game
	 * states that can be stored in the {@code #contentStates}.
	 */
	public static final int NUMCONTENTSTATES = 6;

	/**
	 * {@code #MAINMENUSTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int MAINMENUSTATE = 0;

	/**
	 * {@code #LEVELSTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int LEVELSTATE = 1;

	/**
	 * {@code #OPTIONSTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int OPTIONSTATE = 2;

	/**
	 * {@code #HIGHSCORESTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int HIGHSCORESTATE = 3;

	/**
	 * {@code #HELPSCREENSTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int HELPSCREENSTATE = 4;

	/**
	 * {@code #SAVESCREENSTATE} is used to define the final position in the
	 * {@code #contentStates} where this state can be stored.
	 */
	public static final int SAVESCREENSTATE = 5;

	/**
	 * Class constructor
	 * 
	 * first loads an Icon {@link #loadIconImage()} and assigns it to this
	 * frame. The constructor then calls to check if the main menu already
	 * exists {@link #getMainMenu()}. A list of objects is created and set to
	 * values which are already predefined. A variable is set to the first value
	 * in the object list. A call to create a state {@link #loadState(int)}
	 * which sends the value given to the main menu state.
	 * 
	 * @see #loadIconImage()
	 * @see #getMainMenu()
	 * @see #loadState(int)
	 *
	 */
	public ContentManager() {

		loadIconImage();
		this.setIconImage(image);
		getMainMenu();
		contentStates = new Object[NUMCONTENTSTATES];
		currentState = MAINMENUSTATE;
		loadState(currentState);

	}

	/**
	 * 
	 * Stores an image which is located using the ResourceLoader
	 *
	 * @throws IOException
	 *             If Image can not be found.
	 * @see ResourceLoader
	 */
	private void loadIconImage()
	{
		try {
			image = ImageIO.read(ResourceLoader.load("ZombifiedIcon.gif"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * This method uses the currentState argument which must have a value and
	 * calls the corrosponding method in order to find out if that state already
	 * exists. If the state does not exist then a new state is created and
	 * stored, then the contents of this frame is set to the new state. All
	 * other states are set to {@code null} in order for the memory management
	 * system to remove the states no longer in use.
	 * 
	 * @param currentState
	 *            the number of the state required for use.
	 */
	private void loadState(int currentState)
	{

		if (currentState == MAINMENUSTATE) {
			levelManager = getMainMenu();
			this.setContentPane(levelManager);
			level = null;
			option = null;
			highscore = null;
			helpScreen = null;
			saveScreen = null;
		}
		if (currentState == LEVELSTATE) {
			level = getLevel();
			this.setContentPane(level);
			levelManager = null;
			option = null;
			highscore = null;
			helpScreen = null;
			saveScreen = null;
		}
		if (currentState == OPTIONSTATE) {
			option = getOptions();
			this.setContentPane(option);
			level = null;
			highscore = null;
			levelManager = null;
			helpScreen = null;
			saveScreen = null;
		}
		if (currentState == HIGHSCORESTATE) {
			highscore = getHighscore();
			this.setContentPane(highscore);
			level = null;
			option = null;
			levelManager = null;
			helpScreen = null;
			saveScreen = null;
		}
		if (currentState == HELPSCREENSTATE) {
			helpScreen = getHelp();
			this.setContentPane(helpScreen);
			level = null;
			option = null;
			levelManager = null;
			highscore = null;
			saveScreen = null;
		}
		if (currentState == SAVESCREENSTATE) {
			saveScreen = getSaveScreen();
			this.setContentPane(saveScreen);
			helpScreen = null;
			level = null;
			option = null;
			levelManager = null;
			highscore = null;
		}

	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private SaveScreen getSaveScreen()
	{

		if (saveScreen == null) {
			saveScreen = new SaveScreen(this);
		}

		return saveScreen;
	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private HelpScreen getHelp()
	{

		if (helpScreen == null) {
			helpScreen = new HelpScreen(this);
		}

		return helpScreen;
	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private Highscore getHighscore()
	{

		if (highscore == null) {
			highscore = new Highscore(this);
		}
		return highscore;
	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private Options getOptions()
	{

		if (option == null) {
			option = new Options(this);
		}

		return option;
	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private MainMenu getMainMenu()
	{

		if (levelManager == null) {
			levelManager = new MainMenu(this);
		}
		return levelManager;
	}

	/**
	 *
	 * When this method is called if the current state does not exist then a new
	 * state is created and returned. If the state already exists then a new
	 * state is not required and the existing one will be returned.
	 * 
	 * @return the new or existing state
	 */
	private Level getLevel()
	{

		if (level == null) {
			level = new Level(this);
		}
		return level;
	}

	/**
	 * Clears the currentState {@link #clearState(int)} in order to set a new
	 * state using the state argument which has been passed through from the
	 * call to this method. Calls {@link #loadState(int)} in order to change the
	 * contents of this frame.
	 * 
	 * @param state
	 *            the value of the new state to be used
	 */
	public void setCurrentState(int state)
	{
		clearState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/**
	 * This method sets the value in a list to {@code null} using the
	 * currentState argument which is then later removed by the memory
	 * management system.
	 * 
	 * @param currentState
	 *            the number of the state which is to be cleared
	 */
	private void clearState(int currentState)
	{
		contentStates[currentState] = null;
	}

}
