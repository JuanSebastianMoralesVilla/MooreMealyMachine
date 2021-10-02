package model;

/**
 * @author Sebastian Morales (JuanSebastianMoralesVilla)
 * @author Alex Sanchez (ALEXJR2002)
 * @author Miguel Sarasti (MSarasti)
 *
 */
public abstract class Automaton {

    private int numberOfStates;
    private String alphabet;

    public Automaton (int numberOfStates, String alphabet) {
        this.numberOfStates = numberOfStates;
        this.alphabet = alphabet;
        /*if (machine) {  //Create a Mealy machine if true or a Moore otherwise
            mealyMachine = new MealyMachine();
        }else {
            mooreMachine = new MooreMachine();
        }*/
        //TODO assign this conditional to the GUI
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public void setNumberOfStates(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
