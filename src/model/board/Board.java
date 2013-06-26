package model.board;

import exceptions.PointAllreadyContainsCellException;
import frontend.Colors;

import java.awt.Point;
import java.util.ArrayList;

import model.cells.Cell;
import model.cells.Key;
import model.cells.Transporter;
import model.cells.Wall;

/**
 * A board has a Cell matrix with an extra surrounding wall. This class will represent the board in which the player will moved through the game. The extra surrounding wall means that, if the constructor is called with rows = 5 and cols = 5, the board will have a dimension of 7x7. When calling the add method the user must take into consideration that if he needs to add an element at point (0,0), he must call the method with the point (1,1). This class also contains a list of the transporters added.
 */

public class Board {

	private Cell[][] board;
	private int TCColorID = Colors.colorsQty() - 1;
	private int KDColorID = 0;
	
	private ArrayList<Transporter> transporters;

	/**
	 * Initializes the Board with an extra surrounding wall of size one.
	 * 
	 * @param rows
	 * @param cols
	 */
	public Board(int rows, int cols) {
		if (rows <= 0 || cols <= 0)
			throw new IllegalArgumentException();
		board = new Cell[rows + 2][cols + 2];
		initializeExternalWalls(rows, cols);
		transporters = new ArrayList<Transporter>();
	}

	private void initializeExternalWalls(int rows, int cols) {
		for (int i = 0; i < cols + 1; i++)
			board[0][i] = new Wall(new Point(0, i));
		for (int i = 0; i < rows + 2; i++)
			board[i][cols + 1] = new Wall(new Point(i, cols + 1));
		for (int i = cols + 1; i >= 0; i--)
			board[rows + 1][i] = new Wall(new Point(rows + 1, i));
		for (int i = rows + 1; i >= 0; i--)
			board[i][0] = new Wall(new Point(i, 0));

	}

	/**
	 * Adds a Cell to an specific Row and Column. If the Cell is a key, adds
	 * it's associated Door. If the Cell is a Transporter adds it's destiny and
	 * itself to the list.
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             If the parameter row or col exceeds the board size
	 * @param row
	 * @param col
	 * @param cell
	 * @throws PointAllreadyContainsCellException
	 *             If wants to add a cell where there was one already
	 */
	public void add(int row, int col, Cell cell)
			throws PointAllreadyContainsCellException {
		if (getElemIn(row, col) != null)
			throw new PointAllreadyContainsCellException();
		if (cell instanceof Key) {
			add(row, col, (Key) cell);
			((Key) cell).setID(KDColorID++);
			return;
		}
		if (board.length <= row || board[0].length <= col)
			throw new ArrayIndexOutOfBoundsException();
		board[row][col] = cell;
		if (cell instanceof Transporter) {
			((Transporter) cell).setID(TCColorID--);
			int x = ((Transporter) cell).getDestinyLocation().x;
			int y = ((Transporter) cell).getDestinyLocation().y;
			if (board.length <= x || board[0].length <= y)
				throw new ArrayIndexOutOfBoundsException();
			board[x][y] = ((Transporter) cell).getDestiny();
			transporters.add((Transporter) cell);
		}

	}

	/**
	 * Returns the transporters list
	 * @return    ArrayList<Transporter> With the transporters
	 */
	public ArrayList<Transporter> getTransporters() {
		return transporters;
	}

	private void add(int row, int col, Key key)
			throws PointAllreadyContainsCellException {
		if (board.length <= row || board[0].length <= col)
			throw new ArrayIndexOutOfBoundsException();
		board[row][col] = key;
		int doorRow = key.getDoor().getLocation().x;
		int doorCol = key.getDoor().getLocation().y;
		add(doorRow, doorCol, key.getDoor());
	}

	/**
	 * Returns an element in an specific Row and Column
	 * 
	 * @throws ArrayIndexOutOfBoundsException If the parameter row or col
	 *                exceeds the board size
	 * @param row
	 * @param col
	 * @return Cell
	 */
	public Cell getElemIn(int row, int col) {
		if (board.length <= row || board[0].length <= col)
			throw new ArrayIndexOutOfBoundsException();
		return board[row][col];
	}

	/**
	 * Returns the rows dimention
	 * 
	 * @return Rows Dimention of the board rows
	 */
	public int getRows() {
		return this.board.length;
	}

	/**
	 * Returns the columns dimention
	 * 
	 * @return Cols Dimention of the board columns
	 */
	public int getCols() {
		return this.board[0].length;
	}
}
