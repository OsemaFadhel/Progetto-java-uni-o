package it.uniroma1.mdp.uno.view;

import it.uniroma1.mdp.uno.model.cards.CardColor;
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
	public static final int CARD_SPACING = 3;
    public static final int LARGE_SPACING = 10;
    public static final int CARD_HEIGHT = 120;

    public static void applyTo(Scene scene) {
        scene.getStylesheets().add(Style.class.getResource("/style.css").toExternalForm());
    }

    public static Image loadCardImage(String fileName) {
        return new Image(Style.class.getResourceAsStream("/assets/cards/" + fileName));
    }

    /**
	 * Restituisce il colore in formato stringa in base al {@link CardColor}
	 * fornito.
	 * 
	 * @param color
	 * @return {@link String}
	 */
	public static String getColor(CardColor color) {
		return switch (color) {
		case RED -> "red";
		case YELLOW -> "yellow";
		case GREEN -> "green";
		case BLUE -> "blue";
		default -> "transparent";
		};
	}
}
