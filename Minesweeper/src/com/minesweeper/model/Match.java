package com.minesweeper.model;

public class Match
{
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
	public Match(int width, int height, int mines)
	{
		this.gameOver = false;

		setNumMines(mines);

		this.gameBoard = new Board(width, height, mines);
	}
	/**Recieves values for make a play 
	 * 
	 * @param x position of the cell
	 * @param y position of the cell
	 * @param action mark or uncover
	 * @return	Status of the game
	 */
	public String makePlay(int x, int y, String action){
		String moveResult = "";
		int status = -1;
		
		//Check the type of play
		switch(action){
			case "M":
				status = gameBoard.markCell(x, y);
				break;
			case "U":
				status = gameBoard.uncoverCell(x, y);
				break;
		}
		//Check the status and returns status of the game
		if (status == Board.MINE_PLAYED) {
			moveResult = GAME_OVER;
			this.gameOver = true;
		} else if (status == Board.COVERED_PLAYED) {
			moveResult = NEXT_ROUND;
		} else if (status == Board.UNCOVERED_PLAYED) {
			moveResult = INCORRECT_PLAY;
		} else if (status == Board.MINE_MARKED) {
			this.minesFound += 1;
			moveResult = NEXT_ROUND;
		} else if (status == Board.COVERED_MARKED) {
			moveResult = NEXT_ROUND;
		} else if (status == Board.UNCOVECERED_MARKED) {
			moveResult = INCORRECT_PLAY;
		}

		if (gameFinished()) {
			moveResult = WINNER;
		}

		return moveResult;
	}
	/**Checks if the game is finished or not
	 * 
	 * @return game finished or not
	 */
	public boolean gameFinished()
	{
		if ((this.minesFound == this.numMines) && (this.gameBoard.getNumEmpties() == this.gameBoard.getUncovered())) {
			return true;
		}
		return false;
	}

	public int getWidth(){
		return this.gameBoard.getWidth();
	}

	public int getHeight()
	{
		return this.gameBoard.getHeight();
	}

	public boolean gameOver()
	{
		return this.gameOver;
	}

	public String getGameBoard()
	{
    return this.gameBoard.toString();
	}

	public void setGameBoard(Board gameBoard)
	{
		this.gameBoard = gameBoard;
	}

	public int getMinesFound()
	{
		return this.minesFound;
	}

	public void setMinesFound(int minesFound)
	{
		this.minesFound = minesFound;
	}

	public int getNumMines()
	{
		return this.numMines;
	}

	public void setNumMines(int numMines)
	{
		this.numMines = numMines;
	}
}