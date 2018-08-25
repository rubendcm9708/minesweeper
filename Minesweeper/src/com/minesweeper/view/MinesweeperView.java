package com.minesweeper.view;

import java.util.Scanner;
/**View class of the minesweeper game
 * 
 * @author rubendcm
 *
 */
public class MinesweeperView {
	//---------------------------------------------------
	//Attributes
	//---------------------------------------------------
	/**
	 * Scanner for the inputs
	 */
	private Scanner scanner;
	/**Constructor of the view class 
	 * 
	 */
	public MinesweeperView() {
		//Initialize the scanner
		this.scanner = new Scanner(System.in);
	}
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	/**
	 * Print welcome message with set up instructions
	 */
	public void welcomeMessage() {
		System.out.println("----------------------------------------------------------\n| "
				+ "Welcome to Minesweeper!!                                 |\n"
				+ "| This is a game made by rubendcm9708 hope you enjoy it !! "
				+ "|\n----------------------------------------------------------\n"
				+ "To start the game, please enter a new game configuration like the following:\n"
				+ "'X Y Z', where X is the height, Y the width and Z the number of mines of the board\n"
				+ "Note: Z should be less than the maximum number of mines that the board can have (X*Y)\n");
	}	
	/**Read input: new game configuration 
	 * 
	 * @return input with new game configuration entered by user
	 */
	public String newGame() {
		String input = this.scanner.nextLine();
		return input;
	}
	/**Read input: new move
	 * 
	 * @return input with the new move entered by user
	 */
	public String nextMove() {
		System.out.println("Your turn!: ");
		String input = this.scanner.nextLine();
		return input;
	}
	/**
	 * Message error: wrong input for game configuration
	 */
	public void wrongSetUp() {
		System.out.println("Please, check the format of the game configuration\n");
	}
	/**
	 * Print game instructions
	 */
	public void startGame() {
		System.out.println("\nTo play a cell, just enter a move like the following:\n"
				+ "'X Y Z' where X is the column (left to right), Y is the file (up to down)\n"
				+ "and Z the type of move (U to uncover or M to put a flag)\n");
	}	
	/**Print current game board
	 * 
	 * @param board String that represents current game board
	 */
	public void printBoard(String board) {
		System.out.println("Board:\n" + board);
	}
	/**
	 * Message error: wrong input format for a move
	 */
	public void wrongMove() {
		System.out.println("Please, check the format of the move\n");
	}
	/**
	 * Print game finished
	 */
	public void gameFinish() {
		System.out.println("Congratulations!! You have found all the mines. Let's try again!\n");
	}
	/**Print incorrect move 
	 * 
	 * @param makePlay type of move made
	 */
	public void moveMessage(String makePlay) {
		if (makePlay.equals("Incorrect"))
			System.out.println("You can't uncover an uncovered cell, or mark a marked cell\n");
	}
	/**
	 * Print game over
	 */
	public void gameOver() {
		System.out.println("Oh oh.... You just exploded. Let's try again!");
	}
	/**
	 * Print application stopped running
	 */
	public void showGameClosed() {
		System.out.println("Finishing the game... See you next time!");
	}
}