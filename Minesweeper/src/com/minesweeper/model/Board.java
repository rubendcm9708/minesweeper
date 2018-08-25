package com.minesweeper.model;
import java.util.Random;

/**This class represents the Board of the minesweeper game
 * 
 * @author rubendcm
 *
 */
public class Board {
	//---------------------------------------------------
	//Constants
	//---------------------------------------------------
	/**
	 * Type of move: A mine has been marked
	 */
	public static final int MINE_MARKED = 1;
	/**
	 * Type of move: An uncovered cell has been marked
	 */
	public static final int UNCOVECERED_MARKED = 2;
	/**
	 * Type of move: An covered empty cell has been marked
	 */
	public static final int COVERED_MARKED = 3;
	/**
	 * Type of move: A covered empty cell has ben uncovered
	 */
	public static final int COVERED_PLAYED = 4;
	/**
	 * Type of move: An uncovered cell has been uncovered
	 */
	public static final int UNCOVERED_PLAYED = 5;
	/**
	 * Type of move: A mine has been uncovered
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
	public Board(int width, int height, int numMines) {
		//Set width, height and number of mines of the board
		setWidth(width);
		setHeight(height);
		setNumMines(numMines);
		
		//Initialize the cells array
		this.cells = new Cell[height][width];
		initializeGameBoard();
		
		//Initialize the mines 
		initializeMines();		
		
		//Calculates and set the number of empties
		setNumEmpties(width * height - numMines);
	}
	
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	/**
	 * Initialize all the cells on the array
	 */
	public void initializeGameBoard() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.cells[i][j] = new Cell(j, i);
			}
		}
	}
	/**
	 * Randomly generates the position of the mines
	 */
	public void initializeMines() {
		//Initialize random generator
		Random rand = new Random();
		
		int XminePos = 0;
		int YminePos = 0;		
		int currentMines = 0;
		
		//Generates all the mines that are neeeded
		while (currentMines < this.numMines){
			//Generate X and Y randomly
			XminePos = rand.nextInt(this.width);
			YminePos = rand.nextInt(this.height);
			//Get random cell
			Cell temp = this.cells[YminePos][XminePos];
			
			//Only if the cell is empty 
			if (temp.getHasMine() == Cell.EMPTY) {
				//Set mine and update number of mines of the cells around
				temp.setHasMine(Cell.MINE);
				updateNumberOfMinesAround(temp);
				//Add to current number of mines
				currentMines++;
			}
		}
	}
	/**
	 * Update number of mines around the new mine 
	 */
	public void updateNumberOfMinesAround(Cell mine) {
		//Get X and Y of the mine
		int i = mine.getPosY();
		int j = mine.getPosX();		
		//Chek up-left and add
		if ((i - 1 >= 0) && (j - 1 >= 0)) {
			cells[i-1][j-1].addMinesAround();
		}
		//Check up and add
		if ((i - 1 >= 0)) {
			cells[i-1][j].addMinesAround();
		}
		//Check up-right and add
		if ((i - 1 >= 0) && (j + 1 < this.width)) {
			cells[i-1][j+1].addMinesAround();
		}
		//Check right and add
		if ((j + 1 < this.width)) {
			cells[i][j+1].addMinesAround();
		}
		//Check down-right and add
		if ((i + 1 < this.height) && (j + 1 < this.width)) {
			cells[i+1][j+1].addMinesAround();
		}
		//Check down and add
		if ((i + 1 < this.height)) {
			cells[i+1][j].addMinesAround();
	    }
		//Check down-left and add
		if ((i + 1 < this.height) && (j - 1 >= 0)) {
			cells[i+1][j-1].addMinesAround();
		}
		//Check left and add
		if ((j - 1 >= 0)) {
			cells[i][j-1].addMinesAround();
		}					
  }
	/**Mark a cell played by the user
	 * 
	 * @param x position of the cell on the board
	 * @param y position of the cell on the board
	 * @return type of move
	 */
	public int markCell(int x, int y) {
		//Get the cell
		Cell marked = this.cells[y][x];
		
		//Check if the cell is covered
		if (marked.getCellState() == Cell.COVERED){
			//Set the cell as marked
			marked.setCellState(Cell.MARKED);  
			//Check if the cell is a mine
			if (marked.getHasMine() == Cell.MINE) {
				//Return mine marked
				return MINE_MARKED;
			}
				//Return empty covered cell marked
				return COVERED_MARKED;
		}
		//Return uncovered empty cell marked
		return UNCOVECERED_MARKED;
	}
	/**Uncover the cell played by the user
	 * 
	 * @param x position of the cell
	 * @param y position of the cell
	 * @return type of move
	 */
  	public int uncoverCell(int x, int y) {
  		//Get the cell
  		Cell played = this.cells[y][x];
  		
  		//Check if the cell is covered or marked
  		if ((played.getCellState() == Cell.COVERED) || (played.getCellState() == Cell.MARKED)){
  			//If uncover a mine, show all mines on the board
  			if (played.getHasMine() == Cell.MINE){
  				//Show all the mines on the board
  				showMines();
  				//Return mine played
  				return MINE_PLAYED;
  			}
  			//Uncover the cell
  			played.setCellState(Cell.UNCOVERED);
  			//Update number of mines uncovered
  			this.numUncovered += 1;
  			//If the cell has not mines around, find all the empty neightbors
  			if (played.getMinesAround() == 0) {
  				//Find all the empty neightbors
  				findEmpties(played);
  			}
  			//Return covered cell played
  			return COVERED_PLAYED;
  		}
  		//Return uncovered cell played
  		return UNCOVERED_PLAYED;
  	}  	
  	/**Find all the empty neighboors around the cell
  	 * 
  	 * @param played empty cell 
  	 */
  	private void findEmpties(Cell played) {
  		
  		//Get the X and Y position of the empty cell
  		int i = played.getPosY();
  		int j = played.getPosX();
  		
  		//Check all the cells around the played
  		
  		//Check up-left
  		if ((i - 1 >= 0) && (j - 1 >= 0)) {
  			Cell temp = this.cells[(i - 1)][(j - 1)];
  			//Check if the cell is not marked
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		
  		//Check up
  		if (i - 1 >= 0) {
  			Cell temp = this.cells[(i - 1)][j];
  			//Check if the cell is empty and covered
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}  			
  		}
  		//Check up-right
  		if ((i - 1 >= 0) && (j + 1 < this.width)) {
  			Cell temp = this.cells[(i - 1)][(j + 1)];
  			//Check if the cell is empty and covered
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		//Check right
  		if (j + 1 < this.width) {
  			Cell temp = this.cells[i][(j + 1)];
  			//Check if the cell is not marked	
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		//Check down-right
  		if ((i + 1 < this.height) && (j + 1 < this.width)) {
  			Cell temp = this.cells[(i + 1)][(j + 1)];
  			//Check if the cell is not marked
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		//Check down
  		if (i + 1 < this.height) {
  			Cell temp = this.cells[(i + 1)][j];
  			//Check if the cell is not marked
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		//Check down-left
  		if ((i + 1 < this.height) && (j - 1 >= 0)) {
  			Cell temp = this.cells[(i + 1)][(j - 1)];
  			//Check if the cell is not marked
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
  				this.numUncovered += 1;
  				//If the cell does not have mines around, uncover all the empty neighboors
  				if (temp.getMinesAround() == 0) {
  					findEmpties(temp);
  				}
  			}
  		}
  		//Check left
  		if (j - 1 >= 0) {
  			Cell temp = this.cells[i][(j - 1)];
  			//Check if the cell is not marked
  			if (temp.getCellState() == Cell.COVERED) {
  				//Uncover the cell
  				temp.setCellState(Cell.UNCOVERED);
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
  	public void showMines()	{
  		for (int i = 0; i < this.height; i++)
  			for (int j = 0; j < this.width; j++)
  				if (this.cells[i][j].getHasMine() == Cell.MINE)
  					this.cells[i][j].setCellState(Cell.MINE_EXPLODED);
  	}
  	/**Get the number of mines on the board
  	 * 
  	 * @return number of mines on the board
  	 */
  	public int getNumMines() {
  		return this.numMines;
  	}
  	/**Set a new number of mines on the board
  	 * 
  	 * @param numMines new number of mines on the board
  	 */
  	public void setNumMines(int numMines) {
  		this.numMines = numMines;
  	}
  	/**Get the width of the board
  	 * 
  	 * @return width of the board
  	 */
	public int getWidth() {
		return this.width;
	}
	/**Set a new width for the board
	 * 
	 * @param width new width for the board
	 */
	public void setWidth(int width)	{
		this.width = width;
	}
	/**Get the height of the board
	 * 
	 * @return height of the board
	 */
	public int getHeight()	{
  		return this.height;
  	}
	/**Set a new height for the board
	 * 
	 * @param height new height for the board
	 */
  	public void setHeight(int height) {
  		this.height = height;
  	}
  	/**Get number of uncovered cells on the board
  	 * 
  	 * @return number of uncovered cells on the board
  	 */
  	public int getUncovered() {
  		return this.numUncovered;
  	}
  	/**Set a new number of uncovered cells on the board
  	 * 
  	 * @param uncovered new number of uncovered cells on the board
  	 */
  	public void setUncovered(int uncovered)	{
  		this.numUncovered = uncovered;
  	}
  	/**Get number of empty cells on the board
  	 * 
  	 * @return number of empty cells on the board
  	 */
  	public int getNumEmpties() {
  		return this.numEmpties;
  	}
  	/**Set a new number of empty cells on the board
  	 * 
  	 * @param numEmpties new number of empty cells on the board
  	 */
  	public void setNumEmpties(int numEmpties) {
  		this.numEmpties = numEmpties;
  	}
  	/**Generates a string that representes the current board
  	 *
  	 * @return Representation of the current board 
  	 */
  	public String toString() {
  		String output = "";
  		for (int i = 0; i < this.height; i++) {
  			for (int j = 0; j < this.width; j++) {
  				//Get the string that represents every cell
  				output = output + this.cells[i][j].toString() + " ";
  			}
  			output = output + "\n";
  			}
  		return output;
  		}
	}