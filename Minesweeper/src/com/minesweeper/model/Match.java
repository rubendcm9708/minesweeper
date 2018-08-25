package com.minesweeper.model;
/**This class represents the match of the minesweeper game
 * 
 * @author rubendcm
 *
 */
public class Match {
	//---------------------------------------------------
	//Constants
	//---------------------------------------------------
	/**
	 * Play is incorrect
	 */
	public static final String INCORRECT_PLAY = "Incorrect";
	/**
	 * Game is over, mine exploded
	 */
	public static final String GAME_OVER = "Game Over";
	/**
	 * Player found all the mines and uncover all the empties
	 */
	public static final String WINNER = "Winner";
	/**
	 * Player can play next round
	 */
	public static final String NEXT_ROUND = "Next Round";
	//---------------------------------------------------
	//Attributes
	//---------------------------------------------------
	/**
	 * Game board of minesweeper
	 */
	private Board gameBoard;
	/**
	 * Number of mines found
	 */
	private int minesFound;
	/**
	 * Number of mines on the board
	 */
	private int numMines;
	/**
	 * Games is over or not
	 */
	private boolean gameOver;
	
	/**Constructor of the class Match with values
	 * 
	 * @param width of the game board
	 * @param height of the game board
	 * @param mines number of mines on the game board
	 */
	public Match(int width, int height, int mines) {
		//Initialize game not over
		this.gameOver = false;
		//Set number of mines that are not marked
		setNumMines(mines);
		//Initialize the board
		this.gameBoard = new Board(width, height, mines);
	}
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	/**Recieves values for make a play 
	 * 
	 * @param x position of the cell
	 * @param y position of the cell
	 * @param action mark or uncover
	 * @return	Status of the game
	 */
	public String makePlay(int x, int y, String action) {
		String moveResult = "";
		int status = -1;
		
		//Check the type of play (U or M)
		switch(action){
			case "M":
				//Mark cell
				status = gameBoard.markCell(x, y);
				break;
			case "U":
				//Uncover cell
				status = gameBoard.uncoverCell(x, y);
				break;
		}
		//Check the status of the move and returns status of the game
		if (status == Board.MINE_PLAYED) {
			//Mine exploded
			moveResult = GAME_OVER;
			this.gameOver = true;
		} else if (status == Board.COVERED_PLAYED) {
			//Keep playing
			moveResult = NEXT_ROUND;
		} else if (status == Board.UNCOVERED_PLAYED) {
			//Incorrect move
			moveResult = INCORRECT_PLAY;
		} else if (status == Board.MINE_MARKED) {
			//Mine has been marked
			this.minesFound += 1;
			moveResult = NEXT_ROUND;
		} else if (status == Board.COVERED_MARKED) {
			//Keep playing
			moveResult = NEXT_ROUND;
		} else if (status == Board.UNCOVECERED_MARKED) {
			//Incorrect move
			moveResult = INCORRECT_PLAY;
		}
		
		//Check if the game is finished, all mines marked and all empty uncovered
		if (gameFinished()) {
			moveResult = WINNER;
		}
		//Return the status of the game
		return moveResult;
	}
	/**Checks if the all the mines are marked and all empties are uncovered
	 * 
	 * @return game finished or not
	 */
	public boolean gameFinished() {	
		//Check mines and uncovered cells
		if ((this.minesFound == this.numMines) && (this.gameBoard.getNumEmpties() == this.gameBoard.getUncovered())) {
			return true;
		}
		return false;
	}
	/**Get the width of the board
	 * 
	 * @return width of the board
	 */
	public int getWidth() {
		return this.gameBoard.getWidth();
	}
	/**Get the height of the board
	 * 
	 * @return height of the board
	 */
	public int getHeight() {
		return this.gameBoard.getHeight();
	}
	/**Get game over 
	 * 
	 * @return game is over or not
	 */
	public boolean gameOver() {
		return this.gameOver;
	}
	/**Get the game board represented by a String
	 * 
	 * @return String that represents the game board
	 */
	public String getGameBoard() {
		return this.gameBoard.toString();
	}
	/**Seta a new game board
	 * 
	 * @param gameBoard a new game Board
	 */
	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
	}
	/**Get number of mines found and marked
	 * 
	 * @return number of mines found and marked
	 */
	public int getMinesFound() {
		return this.minesFound;
	}
	/**Set a new number of mines found
	 * 
	 * @param minesFound new number of mines found
	 */
	public void setMinesFound(int minesFound) {
		this.minesFound = minesFound;
	}
	/**Get number of mines in the game
	 * 
	 * @return number of mines in the game
	 */
	public int getNumMines() {
		return this.numMines;
	}
	/**Set a new number of mines in the game
	 * 
	 * @param numMines new number of mines in the game
	 */
	public void setNumMines(int numMines) {
		this.numMines = numMines;
	}
}