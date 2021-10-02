/**
 * 
 */
package model;

/**
 * @author sebastianmorales
 *
 */
public class MealyMachine extends Automaton{

    private int numberOfStates;
    private String alphabet;

    public MealyMachine(int numberOfStates, String alphabet) {
        super(numberOfStates, alphabet);
    }


}
