package com.minesweeper.model;

import java.util.Random;

public class Board
{
	//---------------------------------------------------
	//Constants
	//---------------------------------------------------
	/**
	 * A mine has been marked
	 */
	public static final int MINE_MARKED = 1;
	/**
	 * An uncovered cell has been marked
	 */
	public static final int UNCOVECERED_MARKED = 2;
	/**
	 * An covered empty cell has been marked
	 */
	public static final int COVERED_MARKED = 3;
	/**
	 * A covered empty cell has ben uncovered
	 */
	public static final int COVERED_PLAYED = 4;
	/**
	 * An uncovered cell has been uncovered
	 */
	public static final int UNCOVERED_PLAYED = 5;
	/**
	 * A mine has been uncovered
	 */
	public static final int MINE_PLAYED = 6;
	
	//---------------------------------------------------
	//Attributes
	//---------------------------------------------------
	/**
	 * Array of cells that represents the board
	 */
	private Cell[][] cells;
	/**
	 * Width of the board
	 */
	private int width;
	/**
	 * Height of the board
	 */
	private int height;
	/**
	 * Number of mines on the board
	 */
	private int numMines;
	/**
	 * Number of cells uncovered
	 */
	private int numUncovered;
	/**
	 * Number of empty cells on the board
	 */
	private int numEmpties;
	
	/**Constructor of the class Board with values
	 * 
	 * @param width of the new board
	 * @param height of the new board
	 * @param numMines number of mines on the board
	 */
	public Board(int width, int height, int numMines)
	{
		setWidth(width);
		setHeight(height);
		setNumMines(numMines);
		
		this.cells = new Cell[height][width];
		initializeGameBoard();
		initializeMines();
		
		countMinesAroundCells();
		
		setNumEmpties(width * height - numMines);
	}
	
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	/**
	 * Initialize all the cells on the array
	 */
	public void initializeGameBoard()
	{
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.cells[i][j] = new Cell(j, i);
			}
		}
	}
	/**
	 * Randomly generates the position of the mines
	 */
	public void initializeMines()
	{
		Random rand = new Random();
		int XminePos = 0;
		int YminePos = 0;
		int currentMines = 0;
		while (currentMines < this.numMines)
		{
			XminePos = rand.nextInt(this.width);
			YminePos = rand.nextInt(this.height);
			Cell temp = this.cells[YminePos][XminePos];
			
			//Only if the cell is empty 
			if (temp.getHasMine() == Cell.CELL_EMPTY)
			{
				temp.setHasMine(Cell.CELL_WITH_MINE);
				currentMines++;
			}
		}
	}
	/**
	 * Counts the mines around all the cells
	 */
	public void countMinesAroundCells()
	{
		int minesAround = 0;

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				Cell temp = this.cells[i][j];
				minesAround = 0;
				//Only if the cell is empty
				if (temp.getHasMine() == Cell.CELL_EMPTY)
					
				{
					//Chek up-left 
					if ((i - 1 >= 0) && (j - 1 >= 0) && 
						  (this.cells[(i - 1)][(j - 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
							minesAround++;
					}
					//Check up 
					if ((i - 1 >= 0) && 
						  (this.cells[(i - 1)][j].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check up-right
					if ((i - 1 >= 0) && (j + 1 < this.width) && 
						  (this.cells[(i - 1)][(j + 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check right
					if ((j + 1 < this.width) && 
						  (this.cells[i][(j + 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check down-right
					if ((i + 1 < this.height) && (j + 1 < this.width) && 
						  (this.cells[(i + 1)][(j + 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check down
					if ((i + 1 < this.height) && 
						  (this.cells[(i + 1)][j].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check down-left
					if ((i + 1 < this.height) && (j - 1 >= 0) && 
						  (this.cells[(i + 1)][(j - 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
						minesAround++;
					}
					//Check left
					if ((j - 1 >= 0) && 
						  (this.cells[i][(j - 1)].getHasMine() == Cell.CELL_WITH_MINE)) {
					  minesAround++;
					}

			  }

			  temp.setMinesAround(minesAround);
		  }
	  }
  }
	/**Mark the cell
	 * 
	 * @param x position of the cell on the board
	 * @param y position of the cell on the board
	 * @return status of the play
	 */
	public int markCell(int x, int y){
		Cell marked = this.cells[y][x];
		
		if (marked.getCellState() == Cell.CELL_COVERED){
			marked.setCellState(Cell.CELL_MARKED);
      
			if (marked.getHasMine() == Cell.CELL_WITH_MINE) {
				return MINE_MARKED;
			}
			return COVERED_MARKED;
		}
		return UNCOVECERED_MARKED;
	}
	/**Uncover de cell
	 * 
	 * @param x position of the cell
	 * @param y position of the cell
	 * @return status of the play
	 */
  	public int uncoverCell(int x, int y){
	  
  		Cell played = this.cells[y][x];
  		
  		if ((played.getCellState() == Cell.CELL_COVERED) || (played.getCellState() == Cell.CELL_MARKED)){
  			if (played.getHasMine() == Cell.CELL_WITH_MINE){
  				//If play a mine, show all mines
  				showMines();
  				return MINE_PLAYED;
  			}
  			//Uncover and add the cell
  			played.setCellState(Cell.CELL_UNCOVERED);
  			this.numUncovered += 1;
  			//If the cell does not have mines around, find all the empty neightbors
  			if (played.getMinesAround() == 0) {
  				findEmpties(played);
  			}
  			return COVERED_PLAYED;
  		}

  		return UNCOVERED_PLAYED;
  	}
  	/**Find all the empty neighboors around the cell
  	 * 
  	 * @param played empty cell 
  	 */
  	private void findEmpties(Cell played)
  	{
  		int i = played.getPosY();
  		int j = played.getPosX();
  		//Check all the cells around the played
  		//Check up-left
  		if ((i - 1 >= 0) && (j - 1 >= 0)) {
  			Cell temp = this.cells[(i - 1)][(j - 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}

  		}

  		if (i - 1 >= 0) {
  			Cell temp = this.cells[(i - 1)][j];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if ((i - 1 >= 0) && (j + 1 < this.width)) {
  			Cell temp = this.cells[(i - 1)][(j + 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if (j + 1 < this.width) {
  			Cell temp = this.cells[i][(j + 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if ((i + 1 < this.height) && (j + 1 < this.width)) {
  			Cell temp = this.cells[(i + 1)][(j + 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if (i + 1 < this.height) {
  			Cell temp = this.cells[(i + 1)][j];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if ((i + 1 < this.height) && (j - 1 >= 0)) {
  			Cell temp = this.cells[(i + 1)][(j - 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}

  		if (j - 1 >= 0) {
  			Cell temp = this.cells[i][(j - 1)];
  			//Check if the cell is empty and covered
  			if ((temp.getHasMine() == Cell.CELL_EMPTY) && (temp.getCellState() == Cell.CELL_COVERED))  
  			{
  				//Uncover the cell
  				temp.setCellState(Cell.CELL_UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  	}
  	/**
  	 * Set all the mines on the board as exploded
  	 */
  	public void showMines()
  	{
  		for (int i = 0; i < this.height; i++)
  			for (int j = 0; j < this.width; j++)
  				if (this.cells[i][j].getHasMine() == 4)
  					this.cells[i][j].setCellState(5);
  	}

  	public int getNumMines()
  	{
  		return this.numMines;
  	}

  	public void setNumMines(int numMines)
  	{
  		this.numMines = numMines;
  	}
	public int getWidth()
	{
		return this.width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
  		return this.height;
  	}

  	public void setHeight(int height)
  	{
  		this.height = height;
  	}

  	public int getUncovered()
 	{
  		return this.numUncovered;
  	}
  	public void setUncovered(int uncovered)
  	{
  		this.numUncovered = uncovered;
  	}

  	public int getNumEmpties()
  	{
  		return this.numEmpties;
  	}

  	public void setNumEmpties(int numEmpties)
  	{
  		this.numEmpties = numEmpties;
  	}
  	/**Generates a string that representes the current board
  	 *
  	 * @return Representation of the current board 
  	 */
  	public String toString()
  	{
  		String output = "";
  		for (int i = 0; i < this.height; i++) {
  			for (int j = 0; j < this.width; j++) {
  				output = output + this.cells[i][j].toString() + " ";
  			}
  			output = output + "\n";
  			}
  		return output;
  		}
	}