package com.minesweeper.model;
	/**This class represents a Cell
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
	public static final int CELL_COVERED = 0;
	/**
	 * Cell is uncovered
	 */
	public static final int CELL_UNCOVERED = 1;
	/**
	 * Cell is marked
	 */
	public static final int CELL_MARKED = 2;
	/**
	 * Cell is empty
	 */
	public static final int CELL_EMPTY = 3;
	/**
	 * Cell has a mine
	 */
	public static final int CELL_WITH_MINE = 4;
	/**
	 * Cell has exploded
	 */
	public static final int MINE_EXPLODED = 5;
	//---------------------------------------------------
	//Constants
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
		setPosX(posX);
		setPosY(posY);
		
		setCellState(CELL_COVERED);
		setHasMine(CELL_EMPTY);
	}
	//---------------------------------------------------
	//Constants	
	//---------------------------------------------------
	/**
	 * 
	 * @return
	 */
	public int getPosX()
	{	
		return this.posX;
	}
	/**
	 * 
	 * @param posX
	 */
	public void setPosX(int posX)
	{
		this.posX = posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
	public void setPosY(int posY)
	{
		this.posY = posY;
	}
	
	public int getCellState()
	{
		return this.cellState;
	}
	
	public void setCellState(int cellState)
	{
		this.cellState = cellState;
	}
	
	public int getMinesAround()
  	{
		return this.minesAround;
  	}
	
	public void setMinesAround(int minesAround)
	{
		this.minesAround = minesAround;
	}
	
	public int getHasMine()
  	{
		return this.hasMine;
  	}
	
	public void setHasMine(int hasMine)
	{
		this.hasMine = hasMine;
	}
	
	public String toString()
	{
		if (this.cellState == CELL_COVERED) {
			return ".";
		}
		if (this.cellState == CELL_MARKED) {
			return "P";
		}
			if ((this.cellState == CELL_UNCOVERED) && (getMinesAround() != 0))
					return this.minesAround+"";
			if (this.cellState == MINE_EXPLODED) {
					return "*";
			}
			return "-";
	}
	}