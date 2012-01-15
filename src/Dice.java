package BeardyRoller;
import java.util.Random;

/**
 * Dice<br>
 * 
 * The Dice class is a generic dice rolling class for any number of d6, d10,<br>
 * or d20 dice.  For use in BeardyRoller.<br>
 * <br>
 * This is in fact a code.<br>
 * 
 * @author Joshua Krell a.k.a. "deject"
 * @version 1.1
 * @date 6/3/2009
 *
 */

public class Dice {
	
	/** Dice roll generator */
	Random roller = new Random();
	
	/**
	 * Method for rolling d6 values.  Returns an int array containing values
	 * for each die rolled.  Values are from 1 to 6.
	 * 
	 * @param numDice		Number of dice to be rolled.
	 * @return roll			roll values
	 */
	public int[] rollD6(int numDice){
		
		// List of rolls
		int[] roll = new int[numDice];
		
		// For each die, roll it
		for(int index = 0; index < numDice; index++){
			roll[index] = Integer.valueOf(roller.nextInt(6) + 1);
		}
		
		// Return the results
		return roll;
	}
	
	/**
	 * Method for rolling d10 values.  Returns an int array containing values
	 * for each die rolled.  Values are from 0 to 9.
	 * 
	 * @param numDice		Number of dice to be rolled.
	 * @return roll			roll values
	 */
	public int[] rollD10(int numDice){
		
		// List of rolls
		int[] roll = new int[numDice];
		
		// For each die, roll it
		for(int index = 0; index < numDice; index++){
			roll[index] = Integer.valueOf(roller.nextInt(10));
		}
		
		// Return the results
		return roll;
	}
	
	/**
	 * Method for rolling d20 values.  Returns an int array containing values
	 * for each die rolled.  Values are from 0 to 19.
	 * 
	 * @param numDice		Number of dice to be rolled.
	 * @return roll			roll values
	 */
	public int[] rollD20(int numDice){
		
		// List of rolls
		int[] roll = new int[numDice];
		
		// For each die, roll it
		for(int index = 0; index < numDice; index++){
			roll[index] = Integer.valueOf(roller.nextInt(20) + 1);
		}
		
		// Return the results
		return roll;
	}

}
