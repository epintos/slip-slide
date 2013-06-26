package frontend;

import java.awt.Color;

/**
 * This class represents the colors available for painting the different images
 * used during the game
 */
public enum Colors {
	GREEN(Color.GREEN), RED(Color.RED), BLUE(Color.BLUE), YELLOW(Color.YELLOW), WHITE(
			Color.WHITE), DARK_GRAY(Color.DARK_GRAY), CYAN(Color.CYAN), MAGENTA(
			Color.MAGENTA), PINK(Color.PINK), GRAY(Color.GRAY), LIGHT_GRAY(
			Color.LIGHT_GRAY), ORANGE(Color.ORANGE);

	private Color color;

	private Colors(Color color) {
		this.color = color;
	}

	/**
	 * Returns a color
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the colors available
	 * 
	 * @return int Representing the colors available
	 */
	public static int colorsQty() {
		return values().length;
	}

}
