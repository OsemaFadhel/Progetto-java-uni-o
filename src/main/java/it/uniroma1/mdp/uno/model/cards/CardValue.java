package it.uniroma1.mdp.uno.model.cards;

public enum CardValue {
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),
	SKIP("SKIP"), REVERSE("REVERSE"), DRAW_TWO("DRAW_2"), WILD("WILD"), WILD_DRAW_FOUR("WILD_DRAW_4");

	private final String fileName;

	CardValue(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
