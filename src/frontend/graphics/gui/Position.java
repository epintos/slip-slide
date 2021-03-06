package frontend.graphics.gui;

/**
 * Ubicación de un objeto relativa a otro.
 */
public class Position {

	private int x;
	private int y;

	/**
	 * Crea una nueva posición.
	 * 
	 * @param x Coordenada X de la nueva posición.
	 * @param y Coordenada Y de la nueva posición.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retorna la coordenada X de la posición.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retorna la coordenada Y de la posición.
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
