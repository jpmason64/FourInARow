package edu.quinnipiac.ser210.fourinarow;

import java.util.Scanner;
/**
 * Four in a row: Two-player console, non-graphics
 * @author relkharboutly
 * @date 1/22/2020
 */
public class FIRConsole  {
                                                     
   public static Scanner in = new Scanner(System.in); // the input Scanner
 
   public static FourInARow FIRboard = new FourInARow();
   
   private static int HUMAN_PLAYER;
   private static int COMPUTER_PLAYER;
  
   
   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
      
	   int currentState = FourInARow.PLAYING;
	   String userInput ="";
	   System.out.println("Which color do you want to be? Red or Blue? (R/B)");
	   userInput = in.next();
	   if(userInput.toLowerCase().equals("r") || userInput.toLowerCase().equals("red")) {
		   HUMAN_PLAYER = FourInARow.RED;
		   COMPUTER_PLAYER = FourInARow.BLUE;
	   }else if (userInput.toLowerCase().equals("b") || userInput.toLowerCase().equals("blue")) {
		   HUMAN_PLAYER = FourInARow.BLUE;
		   COMPUTER_PLAYER = FourInARow.RED;
	   }else {
		   System.out.println("You did not select a color properly. You will now be red since you can't make decisions. The computer will be blue");
		   HUMAN_PLAYER = FourInARow.RED;
		   COMPUTER_PLAYER = FourInARow.BLUE;
	   }
	   //game loop
	   do {
		   FIRboard.printBoard();
		   
		   FIRboard.setCurrentPlayer(HUMAN_PLAYER);
		   userInput = in.next();
		   try {
			   if(Integer.parseInt(userInput) <= 35 ) {
				   FIRboard.setMove(HUMAN_PLAYER, Integer.parseInt(userInput)); 
			   }
		   }catch(NumberFormatException e) {
			   System.out.println("You did not input an integer");
			   continue;
		   }
		   
		   FIRboard.setCurrentPlayer(COMPUTER_PLAYER);
		   FIRboard.setMove(COMPUTER_PLAYER, FIRboard.getComputerMove());
		   
		   currentState = FIRboard.checkForWinner();
		   if(currentState == FourInARow.RED_WON) {
			   if(HUMAN_PLAYER == FourInARow.RED) {
				   System.out.println("Congratulations on beating the computer!");
			   }else {
				   System.out.println("Unfortunately, you've lost to the computer.");
			   }
		   }else if(currentState == FourInARow.BLUE_WON) {
			   if(HUMAN_PLAYER == FourInARow.BLUE) {
				   System.out.println("Congratulations on beating the computer!");
			   }else {
				   System.out.println("Unfortunately, you've lost to the computer.");
			   }
		   }else if(currentState == FourInARow.TIE){
			   System.out.println("Seems that you've tied with the computer.");
		   }
		   
         
      } while ((currentState == IGame.PLAYING) && (!userInput.equals("q"))); // repeat if not game-over
   }
 
     
}