package it.uniroma1.mdp.uno.view;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Style {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int PADDING = 30;

    public static void applyTo(Scene scene) {
        scene.getStylesheets().add(Style.class.getResource("/style.css").toExternalForm());
    }
}
