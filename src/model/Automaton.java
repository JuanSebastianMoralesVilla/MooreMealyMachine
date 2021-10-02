package model;

public class Automaton {

    private int numberOfStates;
    private String alphabet;
    private MealyMachine mealyMachine;
    private MooreMachine mooreMachine;

    public Automaton (int numberOfStates, String alphabet, boolean machine) {
        this.numberOfStates = numberOfStates;
        this.alphabet = alphabet;
        if (machine) {  //Create a Mealy machine if true or a Moore otherwise
            mealyMachine = new MealyMachine();
        }else {
            mooreMachine = new MooreMachine();
        }
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

    public MealyMachine getMealyMachine() {
        return mealyMachine;
    }

    public void setMealyMachine(MealyMachine mealyMachine) {
        this.mealyMachine = mealyMachine;
    }

    public MooreMachine getMooreMachine() {
        return mooreMachine;
    }

    public void setMooreMachine(MooreMachine mooreMachine) {
        this.mooreMachine = mooreMachine;
    }
}
