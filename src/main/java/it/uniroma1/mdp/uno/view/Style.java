package it.uniroma1.mdp.uno.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Classe di utilità per la gestione dello stile dell'interfaccia grafica.
 * 
 * @author Osema Fadhel
 */
public class Style {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int PADDING = 30;
    public static final int SPACING = 6;
    public static final int LARGE_SPACING = 10;
    public static final int CARD_HEIGHT = 120;

    public static void applyTo(Scene scene) {
        scene.getStylesheets().add(Style.class.getResource("/style.css").toExternalForm());
    }

    public static Image loadCardImage(String fileName) {
        return new Image(Style.class.getResourceAsStream("/assets/cards/" + fileName));
    }
}
