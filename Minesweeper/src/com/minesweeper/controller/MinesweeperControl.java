package com.minesweeper.controller;

import com.minesweeper.model.Match;
import com.minesweeper.view.MinesweeperView;
/**Control class of the minesweeper game
 * 
 * @author rubendcm9708
 *
 */
public class MinesweeperControl {
	//---------------------------------------------------
	//Constants
	//---------------------------------------------------
	/**
	 * Type of input: New game configuration
	 */
	public static final int NEW_GAME_SETUP = 0;
	/**
	 * Type of input: New move 
	 */
	public static final int NEW_MOVE = 1;
	//---------------------------------------------------
	//Attributes
	//---------------------------------------------------
	/**
	 * View class of the minesweeper game
	 */
	public static MinesweeperView view;
	/**
	 * Match class of the minesweeper game
	 */
	public static Match match;
	//---------------------------------------------------
	//Methods
	//---------------------------------------------------
	/**Main method that runs the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//Initialize view
		view = new MinesweeperView();
		//Run game
		runGame();
	}
	/**
	 * Run the minesweeper game
	 */
	public static void runGame() {
		try	{
			while (true) {
				//Welcome the user
				view.welcomeMessage();
				//Receives new game setuo
				String setUp = view.newGame();
				//Check the sentence
				while (!checkSentence(setUp, NEW_GAME_SETUP)) {
					//If wrong, ask new setup
					setUp = view.newGame();
				}
				//Initialize a new board
				initializeMatch(setUp);
				//Print game instructions
				view.startGame();
				//Print covered game board
				view.printBoard(match.getGameBoard());
				//If the player has not finished the game or the game is not over, keep asking for moves
				while ((!match.gameFinished()) && (!match.gameOver())) {
					//Ask new move
					String move = view.nextMove();
					//Check the sentence
					while (!checkSentence(move, NEW_MOVE)) {
						//If wrong, ask new move
						move = view.nextMove();
					}
					//Play the move
					playCell(move);
					//Print current game board
					view.printBoard(match.getGameBoard());
					}
				
				if (match.gameFinished()) {
					//If game finished, print message
					view.gameFinish();
				} else if (match.gameOver()) {
					//If game over, print message
					view.gameOver();
				}
			}
		}
		catch (Exception e) {
			//If app stops, print message
			view.showGameClosed();
		}
	}

	/**Check the input sentence entered by user
	 * 
	 * @param sentence entered by user
	 * @param sentenceType	type of sentence
	 * @return	sentence correct or not
	 */
	public static boolean checkSentence(String sentence, int sentenceType) {
		boolean validSentence = false;
		//Check the type of sentece
		if (sentenceType == NEW_GAME_SETUP) {
			try {
				//Try to split and parse values
				String[] values = sentence.split(" ");
				int newHeigth = Integer.parseInt(values[0]);
				int newWidth = Integer.parseInt(values[1]);
				int newMines = Integer.parseInt(values[2]);
				//Check the range of values
				if ((newWidth > 0) && (newHeigth > 0) && (newMines > 0) && 
						(newMines < newHeigth * newWidth) && (values.length == 3)) {
					//Sentence is correct
					validSentence = true;
				}

				if (validSentence) {
					//Send error message
					view.wrongSetUp();
				}
			}
			catch (Exception e) {
				//Send error message
				view.wrongSetUp();
			}
		}
		
		//Check the type of sentece
		else if (sentenceType == NEW_MOVE) {
			try {
				//Try to split and parse values
				String[] values = sentence.split(" ");
				int xMove = Integer.parseInt(values[0]);
				int yMove = Integer.parseInt(values[1]);
				//Check the range of values
				if ((xMove >= 0) && (xMove < match.getWidth()) && 
						(yMove >= 0) && (yMove < match.getHeight()) && 
						((values[2].equals("U")) || (values[2].equals("M"))) && 
						(values.length == 3)) {
					//Sentence is correct
					validSentence = true;
				}
				if (!validSentence) {
					//Send error message
					view.wrongMove();
				}
			}
			catch (Exception e) {
				//Send error message
				view.wrongMove();
			}
		} 
		return validSentence;
	}
	/**
	 * Initialize a new game 
	 * @param setUp entered by user
	 */
	public static void initializeMatch(String setUp) {
		//Get values from input
		String[] values = setUp.split(" ");
		int height = Integer.parseInt(values[0]);
		int width = Integer.parseInt(values[1]);
		int mines = Integer.parseInt(values[2]);
		//Initialize match of the minesweeper game
		match = new Match(width, height, mines);
	}
	/**Play a new move
	 * 
	 * @param move entered by user
	 */
	private static void playCell(String move) {
		//Get values from input
		String[] values = move.split(" ");
		int posX = Integer.parseInt(values[0]);
		int posY = Integer.parseInt(values[1]);
		String typeMove = values[2];
		//Play the input
		view.moveMessage(match.makePlay(posX, posY, typeMove));
	}
}