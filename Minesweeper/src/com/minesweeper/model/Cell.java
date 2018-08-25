package com.minesweeper.model;
	/**This class represents a Cell on the board
	 * 
	 * @author rubendcm9708
	 *
	 */
public class Cell{
	
	//---------------------------------------------------
	//Constants
	//---------------------------------------------------
	/**
	 * Cell is covered
	 */
	public static final int COVERED = 0;
	/**
	 * Cell is uncovered
	 */
	public static final int UNCOVERED = 1;
	/**
	 * Cell is marked
	 */
	public static final int MARKED = 2;
	/**
	 * Cell is empty
	 */
	public static final int EMPTY = 3;
	/**
	 * Cell has a mine
	 */
	public static final int MINE = 4;
	/**
	 * Cell has exploded
	 */
	public static final int MINE_EXPLODED = 5;
	//---------------------------------------------------
	//Attributs
	//---------------------------------------------------
	/**
	 * X position of the Cell on the board
	 */
	private int posX;
	/**
	 * Y position of the Cell on the board
	 */
	private int posY;
	/**
	 * State of the cell, uncovered, covered, marked, exploded
	 */
	private int cellState;
	/**
	 * Number of mines around the cell
	 */
	private int minesAround;
	/**
	 * The cell has a mine or not
	 */
	private int hasMine;
	
	/**Constructor of the class Cell with values
	 * 
	 * @param posX X position of the cell on the board
	 * @param posY Y position of the cell on the board
	 */
	public Cell(int posX, int posY){
		//Set X and Y
		setPosX(posX);
		setPosY(posY);
		//Initalize the cell as covered and empty
		setCellState(COVERED);
		setHasMine(EMPTY);
	}
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	
	/**Get the current X position of the cell
	 * 
	 * @return X position of the cell on the board
	 */
	public int getPosX() {	
		return this.posX;
	}
	/**Set a new X position for the cell 
	 * 
	 * @param posX new X position of the cell on the board
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	/**Get the current Y position of the cell
	 * 
	 * @return Y position of the cell on the board
	 */
	public int getPosY()
	{
		return this.posY;
	}
	/**Set a new Y position for the cell 
	 * 
	 * @param posY new Y position of the cell on the board
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	/**Get the current state of the cell
	 * 
	 * @return state of the cell
	 */
	public int getCellState() {
		return this.cellState;
	}
	/**Set a new state for the cell
	 * 
	 * @param cellState new state of the cell
	 */
	public void setCellState(int cellState)	{
		this.cellState = cellState;
	}
	/**Get the number of mines around the cell
	 * 
	 * @return number of mines around the cell
	 */
	public int getMinesAround()	{
		return this.minesAround;
  	}
	/**Add one to the current number of mines around
	 * 
	 */
	public void addMinesAround() {
		this.minesAround++;
	}
	/**The cell has a mine or not
	 * 
	 * @return true or false if the cell has a mine or not
	 */
	public int getHasMine()	{
		return this.hasMine;
  	}
	/**Set the cell with a mine or is empty
	 * 
	 * @param hasMine the cell has a mine or is empty
	 */
	public void setHasMine(int hasMine)	{
		this.hasMine = hasMine;
	}
	/**
	 * Get the string representation of the cell
	 * 
	 * @return String that represents the cell on the board
	 */
	public String toString() {
		//Return * if the mine has exploded
		if (this.cellState == MINE_EXPLODED) {
			return "*";
		}
		//Return . if the cell is covered
		if (this.cellState == COVERED) {
			return ".";
		}
		//Return P if the cell is marked
		if (this.cellState == MARKED) {
			return "P";
		}
		//Return a number from 1 to 8 if the cell has mines around and is uncovered
		if ((this.cellState == UNCOVERED) && (getMinesAround() != 0)) {
			return minesAround+"";
		}
		//Return - if the cell is uncovered and has no mines around
		return "-";
	}
}