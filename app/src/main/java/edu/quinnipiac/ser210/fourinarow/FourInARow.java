package edu.quinnipiac.ser210.fourinarow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 */
public class FourInARow implements IGame {
		 
	   // The game board and the game status
	   private static final int ROWS = 6, COLS = 6; // number of rows and columns
	   private int[][] board = new int[ROWS][COLS]; // game board in 2D array
	   public int currentPlayer;
	  
	/**
	 * clear board and set current player   
	 */
	public FourInARow(){
		clearBoard();		
	}
	@Override
	public void clearBoard() {
		// TODO Auto-generated method stub
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				board[i][j] = EMPTY;
			}
		}
		
	}
	public void setBoard(int[][] board){
		this.board = board;
	}

	//location is a number between 0 and 35
	//player is RED or BLUE
	@Override
	public void setMove(int player, int location) {
		// TODO Auto-generated method stub
		//Take location and calculate row number and a column number 
		int row = location / 6;
		int col = location % 6;
		if(board[row][col] == EMPTY) {
			board[row][col] = player;
		}
	}
	
	public void setCurrentPlayer(int player) {
		currentPlayer = player;
	}

	public int getCurrentPlayer(){
		return currentPlayer;
	}

	@Override
	public int getComputerMove() {
		// TODO Auto-generated method stub
		int move = -1;
		int typeOfMove = (int) (Math.random() * 2);
		switch(typeOfMove) {
			case 0:{
				move = (int) (Math.random() * 36);
				break;
			}
			case 1: {
				/*Find a place on the board where there is a player disc. Place the disk within 1-3 places of the disk.
				 *This isn't designed make the computer win. It's design to make the players fearful. 
				 */
				int human;
				if(currentPlayer == BLUE) {
					human = RED;
				}else {
					human = BLUE;
				}
				
				boolean humanFound = false;
				int humanRow = -1;
				int humanCol = -1;

				
				while(!humanFound) {
					//I chose to start at random positions on the board because I wanted to make sure that users couldn't trick the computer by placing one disc towards the top and then making a row of 4 at the bottom of the board.
					for(int i = (int) (Math.random() * ROWS); i < ROWS; i++) {
						for(int j = (int) (Math.random() * COLS); j < COLS; j++) {
							if(board[i][j] == human) {
								humanFound = true;
								humanRow = i;
								humanCol = j;
							}
						}
					}
				}
				
				
				
				ArrayList<Integer> possibleComputerXPosition = new ArrayList<Integer>();
				ArrayList<Integer> possibleComputerYPosition = new ArrayList<Integer>();
				
				if((humanRow + 3) <  ROWS) {
					possibleComputerXPosition.add((int) (Math.random() * 3));
					possibleComputerYPosition.add(humanCol);
					if((humanCol + 3) < COLS) {
						possibleComputerXPosition.add((int) (Math.random() * 3));
						possibleComputerYPosition.add((int) (Math.random() * 3));
					}
				}else if((humanCol + 3) < COLS){
					possibleComputerXPosition.add(humanRow);
					possibleComputerYPosition.add((int) (Math.random() * 3));
				}else {
					move = (int) (Math.random() * 36);
				}
				
				if(move == -1) {
					int decision = (int) (Math.random() * possibleComputerXPosition.size());
					
					int choiceX = possibleComputerXPosition.get(decision);
					int choiceY = possibleComputerYPosition.get(decision);
					
					move = 6 * choiceX + choiceY;
				}
				break;
			}
		}
		
		return move;
	}

	public int[][] getBoard(){
		return board;
	}

	//This is different from the method used in my first submission. I ran into errors that I magically did not run into while testing the initial version.
	@Override
	public int checkForWinner() {
		boolean redWinner = false;
		boolean blueWinner = false;
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if((j + 3) < COLS && (board[i][j] == RED && board[i][j + 1] == RED && board[i][j + 2] == RED && board[i][j + 3] == RED)){
					redWinner = true;
				}
				if((j+3) < COLS && (board[i][j] == BLUE && board[i][j + 1] == BLUE && board[i][j + 2] == BLUE && board[i][j + 3] == BLUE)){
					blueWinner = true;
				}
				if((i + 3) < ROWS) {
					if((board[i][j] == RED && board[i + 1][j] == RED && board[i + 2][j] == RED && board[i + 3][j] == RED)) {
						redWinner = true;
					}
					if((board[i][j] == BLUE && board[i + 1][j] == BLUE && board[i + 2][j] == BLUE && board[i + 3][j] == BLUE)){
						blueWinner = true;
					}
					if((j + 3) < COLS && ((board[i][j] == RED && board[i + 1][j + 1] == RED && board[i + 2][j + 2] == RED && board[i + 3][j + 3] == RED))){
						redWinner = true;
					}
					if((j + 3) < COLS && ((board[i][j] == BLUE && board[i + 1][j + 1] == BLUE && board[i + 2][j + 2] == BLUE && board[i + 3][j + 3] == BLUE))){
						blueWinner = true;
					}
					if((j - 3) >= 0 && ((board[i][j] == RED && board[i + 1][j - 1] == RED && board[i + 2][j - 2] == RED && board[i + 3][j - 3] == RED))) {
						redWinner = true;
					}
					if((j - 3) >= 0 && ((board[i][j] == BLUE && board[i + 1][j - 1] == BLUE && board[i + 2][j - 2] == BLUE && board[i + 3][j - 3] == BLUE))){
						blueWinner = true;
					}
				}
			}
		}
		if(redWinner && blueWinner) {
			return TIE;
		}else if (redWinner){
			return RED_WON;
		}else if (blueWinner) {
			return BLUE_WON;
		}else {
			return PLAYING;
		}
	}
	
	  /**
	   *  Print the game board 
	   */
	   public  void printBoard() {
		  
	      for (int row = 0; row < ROWS; ++row) {
	         for (int col = 0; col < COLS; ++col) {
	            printCell(board[row][col]); // print each of the cells
	            if (col != COLS - 1) {
	               System.out.print("|");   // print vertical partition
	            }
	         }
	         System.out.println();
	         if (row != ROWS - 1) {
	            System.out.println("--------------------"); // print horizontal partition
	         }
	      }
	      System.out.println(); 
	   }
	 
	   /**
	    * Print a cell with the specified "content" 
	    * @param content either BLUE, RED or EMPTY
	    */
	   public  void printCell(int content) {
	      switch (content) {
	         case EMPTY:  System.out.print("   "); break;
	         case BLUE: System.out.print(" B "); break;
	         case RED:  System.out.print(" R "); break;
	      }
	   }

}
