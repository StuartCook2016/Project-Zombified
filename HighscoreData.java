package zombified.game;

import java.io.Serializable;

/**
 * 
 * This class is used to sort the highscore data which will be saved to a file
 * and then displayed to the user in the order it has been sorted into.
 * 
 * This class also uses Serializable in order to prevent the data stored in the
 * savefile from being manipulated.
 * 
 * @author Stuart Cook
 *
 */
public class HighscoreData implements Serializable {

	/** This is the serialID */
	private static final long serialVersionUID = 1;

	private final int MAX_SCORES = 10;
	private long[] highScores;
	private String[] names;
	private long checkScores;
	private int j;

	/**
	 * Class constructor
	 * 
	 * This contructs two arrays one to store scores and the other to store the
	 * names. The {@link #init()} method is used in order to fill these arrays
	 * with default data.
	 * 
	 */
	public HighscoreData() {
		highScores = new long[MAX_SCORES];
		names = new String[MAX_SCORES];
		init();
	}

	/**
	 * This method is used to store default data
	 */
	public void init()
	{
		for (int i = 0; i < MAX_SCORES; i++) {
			highScores[i] = 0;
			names[i] = "---";

		}

	}

	/**
	 * 
	 * This method is used to get the highScores array
	 * 
	 * @return an array of highScores
	 */
	public long[] getHighScores()
	{
		return highScores;
	}

	/**
	 * 
	 * This method is used to get the names array
	 * 
	 * @return an array of names
	 */
	public String[] getNames()
	{
		return names;
	}

	/**
	 * This method is used to get the checkScores value
	 * 
	 * @return the long variable checkScores
	 */
	public long getCheckScores()
	{
		return checkScores;
	}

	/**
	 * 
	 * This method sets the players last score to the checkScores variable.
	 * 
	 * @param totalscore
	 *            the total score the player achieved
	 */
	public void setCheckScores(long totalscore)
	{

		checkScores = totalscore;

	}

	/**
	 * 
	 * This method checks if the checkScores variable is greater than the
	 * highScore in the last position in the highScores array.
	 * 
	 * @param score
	 *            the total score the player achieved
	 * @return if checkScores is greater than highScores last position return
	 *         true, else return false
	 */
	public boolean newHighscore(long totalscore)
	{
		if (checkScores > highScores[MAX_SCORES - 1]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * This method determines if the new high score is a new score if it is then
	 * the new score and name are added to there arrays then the
	 * {@link #sortHighScores()}} is used in order to sort the scores into the
	 * correct position in there arrays.
	 * 
	 * @param newScore
	 *            new score to be added to the highScores array
	 * @param name
	 *            new name to be added to the names array
	 */
	public void addScore(long newScore, String name)
	{
		if (newHighscore(newScore)) {
			highScores[MAX_SCORES - 1] = newScore;
			names[MAX_SCORES - 1] = name;
			sortHighScores();
		}

	}

	/**
	 * 
	 * This method uses a bubblesort algorithm in order to sort the scores and
	 * names into there correct position in there arrays
	 * 
	 */
	private void sortHighScores()
	{
		for (int i = 0; i < MAX_SCORES; i++) {
			long score = highScores[i];
			String name = names[i];
			
			for (j = i - 1; j >= 0 && highScores[j] < score; j--) {
				highScores[j + 1] = highScores[j];
				names[j + 1] = names[j];
			}

			highScores[j + 1] = score;
			names[j + 1] = name;
		}

	}

}
