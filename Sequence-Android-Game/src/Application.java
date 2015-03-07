//To handle inputs
import java.util.Scanner;

//To-do
/*Double Player's Deck
 *Create Player class to manipulate player's hands
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
		
		//Set Player's Hand Size
		int handSize = 7;
		//Int to store borrowers card choices
		int selection;
		//Keep track of Rounds
		int round = 1;
		
		//Exit trigger
		boolean exit = false;
		
		//Create a Gameboard, 1 for Static, 2 for Random
		Board gameBoard = new Board(1);
		
		Scanner input = new Scanner(System.in);
		
		//Create Player's Deck
		Deck playerDeck = new Deck(true);
		
		//Shuffle
		playerDeck.shuffle();
		
		//Create a player hand of cards
		Card[] playerHand = new Card[handSize];
		
		Card playerCard;
		
		//Beginning Loop for player's hand
		while(!exit)
		{
			//Display Board
			gameBoard.display();
			
			//Initialize & display player's hand
			System.out.println("You hold the following cards: ");
			for(int i = 0; i < handSize; i++)
			{
				//If deck is empty, end game
				if(playerDeck.cardsLeft() == 0)
				{
					exit = true;
					System.out.println("Sorry you are out of cards! You lose!");
					break;
				}
				playerCard = playerDeck.dealCard();
				playerHand[i] = playerCard;
				System.out.println("#" + (i+1) + "| " + playerHand[i].toString());
			}
			System.out.println();
		
			//Get Player's Choice to play card
			System.out.println("Which card would you like to play? (Enter an Integer from 1 to 7, or Enter 0 to Exit Game)");
			selection = input.nextInt();
			//Force player to select an Int between 1 and 7, or default to 1
			//doesn't stop player from putting in a double / char / string etc.
			if(selection < 0 || selection > 7)
			{
				selection = 1;
			}
			if(selection == 0)
			{
				exit = true;
				break;
			}
			System.out.println("You selected " + playerHand[(selection-1)]);
			System.out.println();
		
			//Find card and place onto game board, changing entry to an "X" for player and an "O" for computer
		
		
			//Replace the card with a new card, no need to reorganize hand
			//If deck runs out, game is over - exit
			if(playerDeck.cardsLeft() == 0)
			{
				exit = true;
				System.out.println("Sorry you are out of cards! You lose!");
				break;
			}
			playerCard = playerDeck.dealCard();
			playerHand[(selection-1)] = playerCard;
		
			//Increment Round
			round++;
		
			//Loop Back to updated board and new hand of cards
		}
		System.out.println("Thanks for playing!");
	}
}
