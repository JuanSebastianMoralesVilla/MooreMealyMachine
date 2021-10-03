package Gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainGui extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Automata");

		stage.setScene(scene);
	
		stage.show();
	}
}
