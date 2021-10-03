package Gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.MealyMachine;



public class StateMachineGui {
	


	    @FXML
	    private TextField txtEstados;

	    @FXML
	    private TextField txtAlfabeto;

	    @FXML
	    private RadioButton tgMealy;

	    @FXML
	    private ToggleGroup machine;

	    @FXML
	    private RadioButton tgMoore;

	    @FXML
	    private Button btCrearTabla;

	    @FXML
	    private Button btMin;
	    
	    @FXML
	    private ScrollPane scrollPane1;
	    
	    @FXML
	    private ScrollPane minimizeScroll;
	    
	    
	    private GridPane gridPanel1;
	    
	    private TextField[][] textfield;
	    
	    private int filas;
	    
	    private int columnas;
	    
	    //estados finales
	    
	    private ArrayList<Character> finalStates;
	    
	    private String[] estimulos;
	    
	    
	    public void initialize() {
	    	minimizeScroll.setVisible(false);
	     

	        finalStates = new ArrayList<Character>();
	        
	    }
	    
	    
	    private void fillStates(GridPane grid) {
	    	int position = 0;
	    	TextField ti = new TextField("State");
	    	ti.setEditable(false);
	    	grid.add(ti, 15, 3);
	        for (int i = 1; i < columnas + 1; i++) {
	            TextField ta = new TextField(estimulos[i - 1]);
	            ta.setPrefWidth(45);
	            ta.setEditable(false);
	            grid.add(ta, i+15, 3);
	            position = i;
	        }
	        
	        if (machine.getSelectedToggle()== tgMoore) {
		        TextField to = new TextField("Response");
		        to.setEditable(false);
		    	grid.add(to, position+16, 3);
	        }
	    }
	    
	    
	    private String[][] readTextFields(String type) {
	        String[][] matrix = new String[type.equals("MOORE") ? columnas+ 1 : columnas][filas];
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[0].length; j++) {
	                matrix[i][j] = textfield[i][j].getText();
	            }
	        }
	        return matrix;
	    }
	    
	    
	    
	    // metodo para ingresar los estados
	    
	    @FXML
	    public void generateTable() {
	    	
	    	finalStates = new ArrayList<Character>();
	    	
	    	GridPane gridP2 = new GridPane();
	    	 gridP2.setHgap(3);
	         gridP2.setVgap(3);
	         minimizeScroll.setContent(gridP2);
	         
	        filas = Integer.parseInt(txtEstados.getText());
	       estimulos = txtAlfabeto.getText().split(",");
	        columnas = estimulos.length;
	        if (filas > 0 && filas <= 26) {
	        	btMin.setVisible(true);
	            //t1.setVisible(true);
	            GridPane gridP1 = new GridPane();

	            gridP1.setHgap(5);
	            gridP1.setVgap(5);

	            scrollPane1.setContent(gridP1);

	            try {
	                textfield = new TextField[columnas + 1][filas];

	                fillStates(gridP1);

	                for (int i = 1; i < filas + 1; i++) {
	                    TextField ta = new TextField((char) (i + 64) + "");
	                    ta.setEditable(false);
	                    ta.setPrefWidth(45);
	                    gridP1.add(ta, 15, i+5);
	                    for (int j = 1; j < columnas + 1; j++) {
	                        ta = new TextField("");
	                        ta.setPrefWidth(45);
	                        ta.setPromptText("A" + (machine.getSelectedToggle () == tgMealy ? ",a" : ""));
	                        textfield[j - 1][i - 1] = ta;
	                        gridP1.add(ta, j+15, i+5);
	                    }
	                    if (machine.getSelectedToggle () == tgMoore ) {
	                        ta = new TextField("");
	                        ta.setPrefWidth(45);
	                        ta.setPromptText("a");
	                        textfield[columnas][i - 1] = ta;
	                        gridP1.add(ta, columnas + 16, i+5);
	                    }
	                }
	            } catch (NegativeArraySizeException | IllegalArgumentException e) {
	                Alert exceptionError = new Alert(Alert.AlertType.ERROR);
	                exceptionError .setContentText(e.getMessage());
	                exceptionError .show();
	            }
	        } else {
	            Alert a = new Alert(Alert.AlertType.ERROR);
	            a.setContentText("el maximo numero de estados para llenar es 26 que corresponden a las 26 letras del alfabeto.");
	            a.show();
	        }
	        
	    }

	    
	    private char fillStatesColumns(ArrayList<Character> minStatesList, int i) {
	        char current = minStatesList.get(i - 1);
	        finalStates.add(current);
	  
	        TextField textfieldStatesQ = new TextField("Q"+ finalStates.size());
	        textfieldStatesQ .setEditable(false);
	       textfieldStatesQ .setPrefWidth(45);
	       gridPanel1.add( textfieldStatesQ , 15, i+5);
	        return current;
	    }
	

	    
	    // metodo para minizar el automata
	    
	    @FXML
	    public void minimizeMachine() {
	    	finalStates = new ArrayList<Character>();
	    	gridPanel1= new GridPane();
	    	gridPanel1.setHgap(3);
	    	gridPanel1.setVgap(3);

	       fillStates(gridPanel1);
	        if(machine.getSelectedToggle()== tgMoore) {
	          //  mooreAutomaton();
	        } else {
	            //mealyAutomaton();
	        }
	        minimizeScroll.setContent(gridPanel1);
	    }
	    
	    
	    
	    
	   // automata de mealy
	    /// metodo por mejorar
	    
	    public void mealyAutomaton() {
	        String[][] matrix = readTextFields("MEALY");
	        MealyMachine<Character, Character, Character> mealyMachine = new MealyMachine<>('A');

	        for (int j = 1; j < matrix[0].length; j++) {
	            char temp = (char) (j + 65);
	            //mealyMachine.insertarEstado(temp);
	        }
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[0].length; j++) {
	                char temp = (char) (j + 65);
	                String[] cell = matrix[i][j].split(",");
	              //  mealyMachine.relacionarEstados(temp, cell[0].charAt(0), estimulos[i].charAt(0), cell[1].charAt(0));
	            }
	        }

	        mealyMachine = mealyMachine.minimize();
	        //int minStates = mealyMachine.getOrder();

	        //ArrayList<Character> minStatesList = mealyMachine.getVertices();
	        //for (int i = 1; i < minStates + 1; i++) {
	           char current = fillStatesColumns(minStatesList, i);
	            TextField ta;
	            for (int j = 1; j < columnas + 1; j++) {
	                char stimulus = estimulos[j - 1].charAt(0);
	                
	                boolean cent = false;
	            	String answer = "mmmmmm ";
	            	
	                String cell = "" + mealyMachine.estadoDeTransicionDeUnaFuncion(current, stimulus);
	                
	                for(int s = 0; s < minStatesList.size() && !cent; s++) {
	            		System.out.println(s + "s1");
	            		if(cell.charAt(0) == minStatesList.get(s)) {

	                		System.out.println(s + "s2");
	            			cent = true;
	            			answer = "Q" + (s+1);
	            		}
	            	}
	                
	                answer += "," + mealyMachine.getRespuestas(current, stimulus);
	                ta = new TextField(answer);
	                ta.setEditable(false);
	                ta.setPrefWidth(45);
	                gridPanel1.add(ta, j+15, i+5);
	            }
	        }
	    }
}
