package it.uniroma1.mdp.uno;

import it.uniroma1.mdp.uno.model.GameEngine;
import it.uniroma1.mdp.uno.controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameEngine engine = new GameEngine();
		
		GameController controller = new GameController(engine, primaryStage);
		
		controller.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
