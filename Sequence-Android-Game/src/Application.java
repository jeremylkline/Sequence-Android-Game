//To handle inputs
import java.util.Scanner;

//To-do
/*
 *
 *Create loop to match card selected and create function to update board
 *accordingly
 *Create joker and jack play mechanics
 *Add Computer Player & Basic AI - 2 Player
 *Add 3+ Players (Humans side-by-side / or Multiple Computers
 *Advanced AI - Easy / Medium / Hard
 *Graphics
 *Android Version
 */



public class Application {
	public static void main(String[] args) {
		//Welcome & How to Play
		System.out.println("Welcome to Mini-Sequence, a card and board game!");
		System.out.println("The object of the game is to play cards on the board");
		System.out.println("in order to place 4 in a row either horizontal, diagonal");
		System.out.println(" or vertical.");
		System.out.println();
		
		//Int to store borrowers card choices
		int selection;
		//Keep track of Rounds
		int round = 1;
		
		//Exit trigger
		boolean exit = false;
		
		//Create a Gameboard, 1 for Static, 2 for Random
		Board gameBoard = new Board(1);
		
		//Create Players
		Player playerOne = new Player(true, 2, gameBoard);
		
		Player playerTwo = new Player(false, 2, gameBoard);		
	
		//Beginning Loop for player's hand
		while(!exit)
		{
			//Display Board
			gameBoard.display();
			
			//Display player's hand
			System.out.println("-----------------------------------------");
			System.out.println("Player #1: ");
			playerOne.displayHand();
			System.out.println("-----------------------------------------");
			System.out.println("Player #2: ");
			playerTwo.displayHand();
			
			//Take a card away and replace it.
			System.out.println("Player #1, Which card will you play? (Enter the integer)");
			Scanner input = new Scanner(System.in);
			//Subtract 1 to reference the array starts at 0 not 1, force a selection between 1 and hand size
			//default selection is 1st card
			selection = input.nextInt() - 1;
			if(selection < 0 || selection > (playerOne.getHandsize()-1))
			{
				selection = 0;
			}
			playerOne.playCard(selection, gameBoard, "Blue");
		
			//Get Player's Choice to play card
			
			//Force player to select an Int between 1 and 7, or default to 1
			//doesn't stop player from putting in a double / char / string etc.
			
		
			//Find card and place onto game board, changing entry to an "X" for player and an "O" for computer
		
		
			//Replace the card with a new card, no need to reorganize hand
			//If deck runs out, game is over - exit
			
		
			//Increment Round
			round++;
		
			//Loop Back to updated board and new hand of cards
		}
		System.out.println("Thanks for playing!");
	}
}
