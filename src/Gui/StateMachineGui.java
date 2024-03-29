package Gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.*;

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
	private Button btHelp;

	@FXML
	private ScrollPane scrollPane1;

	@FXML
	private ScrollPane minimizeScroll;

	private GridPane gridPanel1;

	private GridPane gridP1;

	private GridPane gridP2;

	private TextField[][] textfield;

	private int filas;

	private int columnas;

	// estados finales

	private ArrayList<Character> finalStates;

	private String[] estimulos;

	private MealyMachine<Character,Character,Character> mealyMachine;

	private MooreMachine<Character,Character,Character> mooreMachine;

	public void initialize() {
		minimizeScroll.setVisible(false);
		finalStates = new ArrayList<Character>();
		tgMealy.setSelected(true);
		
	}

	// llenar los estados

	private void fillStates(GridPane grid) {
		int position = 0;
		TextField ti = new TextField("State");
		ti.setEditable(false);
		grid.add(ti, 1, 1);
		for (int i = 1; i < columnas + 1; i++) {
			TextField tfStates = new TextField(estimulos[i - 1]);
			tfStates.setPrefWidth(30);
			tfStates.setEditable(false);
			grid.add(tfStates, i + 1, 1);
			position = i;
		}

		if (machine.getSelectedToggle() == tgMoore) {
			TextField to = new TextField("Salida");
			to.setEditable(false);
			grid.add(to, position + 2, 1);
		}
	}

	// leer los textfields
	private String[][] readTextFields(String type) {
		String[][] matrix = new String[type.equals("MOORE") ? columnas + 1 : columnas][filas];
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
		if(!validateTexfield()) {
			return;
		}
		finalStates = new ArrayList<Character>();

		gridP2 = new GridPane();
		gridP2.setHgap(3);
		gridP2.setVgap(3);
		minimizeScroll.setContent(gridP2);

		String split = "";
		String txtAlph = txtAlfabeto.getText();
		String txtEst = txtEstados.getText();
		validateTexfield();

		if (txtAlph.contains(";")) {
			split = ";";
		} else if (txtAlph.contains(",")) {
			split = ",";
		} else {
			split = " ";
		}

		filas = Integer.parseInt(txtEst);
		estimulos = txtAlph.split(split);
		columnas = estimulos.length;
		if (filas > 0 && filas <= 50) {
			btMin.setVisible(true);
			// t1.setVisible(true);
			gridP1 = new GridPane();

			gridP1.setHgap(5);
			gridP1.setVgap(5);

			scrollPane1.setContent(gridP1);

			try {
				textfield = new TextField[columnas + 1][filas];

				fillStates(gridP1);

				for (int i = 1; i < filas + 1; i++) {
					TextField ta = new TextField((char) (i + 64) + " ");
					ta.setEditable(false);
					ta.setPrefWidth(30);
					gridP1.add(ta, 1, i + 1);
					for (int j = 1; j < columnas + 1; j++) {
						ta = new TextField("");
						ta.setPrefWidth(30);
						ta.setPromptText("A" + (machine.getSelectedToggle() == tgMealy ? ",sucesor" : ""));
						textfield[j - 1][i - 1] = ta;
						gridP1.add(ta, j + 1, i + 1);
					}
					if (machine.getSelectedToggle() == tgMoore) {
						ta = new TextField("");
						ta.setPrefWidth(30);
						ta.setPromptText("a");
						textfield[columnas][i - 1] = ta;
						gridP1.add(ta, columnas + 2, i + 1);
					}
				}
			} catch (NegativeArraySizeException | IllegalArgumentException e) {
				Alert exceptionError = new Alert(Alert.AlertType.ERROR);
				exceptionError.setContentText(e.getMessage());
				exceptionError.show();
			}
		} else {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText(
					"Ingresa otro numero de estados valido");
			a.show();
		}

	}

	private char fillStatesColumns(ArrayList<Character> minStatesList, int i) {
		char current = minStatesList.get(i - 1);
		finalStates.add(current);

		TextField textfieldStatesQ = new TextField("Q" + finalStates.size());
		textfieldStatesQ.setEditable(false);
		textfieldStatesQ.setPrefWidth(45);
		gridPanel1.add(textfieldStatesQ, 15, i + 5);
		return current;
	}

	// metodo para minizar el automata

	@FXML
	public void minimizeMachine(ActionEvent event) {
		finalStates = new ArrayList<Character>();
		gridPanel1 = new GridPane();
		gridPanel1.setHgap(3);
		gridPanel1.setVgap(3);
		minimizeScroll.setVisible(true);

		fillStates(gridPanel1);

		if (machine.getSelectedToggle() == tgMoore) {
			mooreMachine();
		} else {
			mealyMachine();
		}
		minimizeScroll.setContent(gridPanel1);
	}

	private void mealyMachine() {
		String[][] matrix = readTextFields("MEALY");
		MealyMachine<Character, Character, Character> mealyMachine = new MealyMachine<>('A');

		for (int j = 1; j < matrix[0].length; j++) {
			char temp = (char) (j + 65);
			mealyMachine.insertState(temp);
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				char temp = (char) (j + 65);
				String[] cell = matrix[i][j].split(",");
				mealyMachine.addConnection(temp, cell[0].charAt(0), estimulos[i].charAt(0), cell[1].charAt(0));
			}
		}
		mealyMachine = mealyMachine.minimize();
		int minStates = mealyMachine.getMap().size();

		ArrayList<Character> minStatesList = mealyMachine.mapToArrayList();
		for (int i = 1; i < minStates + 1; i++) {
			char current = fillStatesColumns(minStatesList, i);
			TextField tanswer;
			for (int j = 1; j < columnas + 1; j++) {
				char stimulus = estimulos[j - 1].charAt(0);

				boolean b = false;
				String answer = "verificate";

				String cell = "" + mealyMachine.nextStates(current, stimulus);

				for(int s = 0; s < minStatesList.size() && !b; s++) {
					System.out.println(s + "s1");
					if(cell.charAt(0) == minStatesList.get(s)) {

						System.out.println(s + "s2");
						b = true;
						answer = "Q" + (s+1);
					}
				}

				answer += "," + mealyMachine.nextStates(current, stimulus);
				tanswer = new TextField(answer);
				tanswer.setEditable(false);
				tanswer.setPrefWidth(45);
				gridP2.add(tanswer, j+5, i+5);
			}
		}
	}

	private void mooreMachine() {
		String[][] matrix = readTextFields("MOORE");

		MooreMachine<Character, Character, Character> mooreMachine = new MooreMachine<>('A', matrix[matrix.length - 1][0].charAt(0));
		for (int j = 0; j < matrix[0].length; j++) {
			char temp = (char) (j + 65);
	
			mooreMachine.insertState(temp, matrix[matrix.length - 1][j].charAt(0));
		}
		for (int i = 0; i < matrix.length - 1; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				char temp = (char) (j + 65);
				mooreMachine.addConnection(temp, matrix[i][j].charAt(0), estimulos[i].charAt(0));
			}
		}
		mooreMachine = mooreMachine.minimize();
		int minStates = mooreMachine.getMap().size();

		ArrayList<Character> minStatesList = mooreMachine.mapToArrayList();

		for (int i = 1; i < minStates + 1; i++) {
			System.out.println(minStatesList);
			char current = fillStatesColumns(minStatesList, i);
			TextField tanswer = new TextField("" + mooreMachine.getResponses(current));

			tanswer.setEditable(false);
			tanswer.setPrefWidth(45);
			gridP2.add(tanswer, columnas + 2, i+1);
			for (int j = 1; j < columnas + 1; j++) {

				boolean validate = false;
				String answer = "verificate ";
				char f = mooreMachine.nextStates(current, estimulos[j - 1].charAt(0));
				System.out.println(f);

				for(int s = 0; s < minStatesList.size() && !validate; s++) {
					System.out.println(s + "s1");
					if(f == minStatesList.get(s)) {

						System.out.println(s + "s2");
						validate = true;
						answer = "Q" + (s+1);
					}
				}

				tanswer = new TextField(answer);
				System.out.println(mooreMachine.nextStates(current, estimulos[j - 1].charAt(0)) + "AAAaa");
				tanswer.setEditable(false);
				tanswer.setPrefWidth(45);
				gridP2.add(tanswer, j+2, i+1);
			}
		}

	}

	
	
	// metodo que valida que la informacion este completa
	public boolean validateTexfield() {
		String alfabeto = txtAlfabeto.getText();
		String states = txtEstados.getText();

		if (alfabeto.isEmpty() || states.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Informacion");
			alert.setContentText("DEBES LLENAR LOS CAMPOS REQUERIDOS");
		alert.show();

			return false;
		}
		return true;

	}
	

    @FXML
    void help(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setTitle("Informacion");
		alert.setContentText("Alfabeto:  Estados:");
	alert.show();
    }
}
