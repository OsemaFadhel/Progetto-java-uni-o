package it.uniroma1.mdp.uno;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
    public void start(Stage primaryStage) throws Exception {
		GameManager model = new GameManager();
	    
	    GameView view = new GameView(model);
	    
	    GameController controller = new GameController(model, view);
        
        primaryStage.setTitle("UNO - Progetto MDP");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }
	
	public static void main(String[] args) {
	    launch(args);
	}
}
