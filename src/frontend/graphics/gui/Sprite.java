package frontend.graphics.gui;

import java.awt.Image;

/**
 * Objeto que puede ser ubicado dentro de un   {@code   GamePanel}  . Los sprites tienen una imagen asociada (que puede ser modificada) y una posición relativa al panel.
 */
public class Sprite {

	private Image image;
	private Position position;

	/**
	 * Crea un nuevo sprite ubicado en las coordenadas indicadas con alguna imagen asociada.
	 * 
	 * @param image Imagen asociada al sprite.
	 * @param position Ubicación del sprite.
	 */
	public Sprite(Image image, Position position) {
		this.image = image;
		this.position = position;
	}

	/**
	 * Retorna la imagen asociada al sprite.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Retorna la ubicación del sprite.
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * @param  position
	 */
	void setPosition(Position position) {
		this.position = position;
	}

	void changeImage(Image image) {
		this.image = image;
	}
}