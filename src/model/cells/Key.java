package model.cells;

import java.awt.Point;

import model.interfaces.KeyListener;
import model.interfaces.Paintable;

/**
 * A Key is a Cell which has an associated Door, so when a Player is over it, the door opens.
 */
public class Key extends Collectable implements Paintable {

	private Door associatedDoor;
	private int id;

	/**
	 * Initializes the Key ID, location, the associated Door and it's ID
	 * 
	 * @param associatedDoor
	 * @param p
	 *            Point representing the location of the key
	 */
	public Key(Point p, Door associatedDoor) {
		super(p);
		this.associatedDoor = associatedDoor;
	}

	@Override
	public int getID() {
		return id;
	}

	/**
	 * Sets the state to NOTVISIBLE and opens the door associated. Removes
	 * itself and the door associated
	 * 
	 */
	@Override
	public void imOver() {
		if (getState() == states.VISIBLE) {
			KeyListener kl = getKeyListener();
			kl.removeElement(getLocation());
			kl.removeElement(associatedDoor.getLocation());
			associatedDoor.open();
		}
	}

	/**
	 * Returns the associated Door
	 * 
	 * @return Door
	 */
	public Door getDoor() {
		return associatedDoor;
	}

	@Override
	public void setID(int id) {
		this.id = id;
		associatedDoor.setID(id);
	}

}