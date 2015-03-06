//To handle inputs
import java.util.Scanner;

//To-do
/*Create loop to match card selected 
 *Create main game loop - Solo Play
 *Add Computer Player & Basic AI - 2 Player
 *Create Double Deck Board
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
		
		//Initialize a Deck of Cards /w Two Jokers
		Deck deck = new Deck(false);
		
		//Shuffle Deck
		deck.shuffle();
		
		/*
		int i = 0;
		do{
		Card yourCard = deck.dealCard();
		System.out.println(yourCard);
		i++;
		}
		while(i <= 53);
		*/
		
		//Initialize a 2D Board to play on using String's
		String[][] gameBoard = new String[7][9];
		
		//Initialize a Card Object
		Card nextCard;
		
		//Add Wild Spots to Game Board
		gameBoard[0][0] = "W";
		gameBoard[6][0] = "W";
		gameBoard[6][7] = "W";
		gameBoard[0][7] = "W";
		
		//Add Row 1
		for(int x = 1; x < 6; x++)
		{
			nextCard = deck.dealCard();
			gameBoard[x][0] = nextCard.toString();
		}
		//Add Row 2, 3, 4, 5 and 6
		for (int x = 0; x < 7; x++)
		{
			for (int y = 1; y < 7; y++)
			{
				nextCard = deck.dealCard();
				gameBoard[x][y] = nextCard.toString();
			}
		}
		//Add Row 7
		for(int x = 1; x < 6; x++)
		{
			nextCard = deck.dealCard();
			gameBoard[x][7] = nextCard.toString();
		}
		
		//Print Board
		System.out.println("Game Board");
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 7; x++)
			{
				System.out.printf("|" + gameBoard[x][y] + "|" + " ");
			}
			System.out.println();
		}
		System.out.println();
		/*if (deck.cardsLeft() == 0)
		{
			System.out.println("The deck is now empty!");
		}
		*/
		
		Scanner input = new Scanner(System.in);
		Deck playerDeck = new Deck(true);
		
		//Shuffle
		playerDeck.shuffle();
		
		//Create a player hand of cards
		Card[] playerHand = new Card[handSize];
		
		Card playerCard;
		
		//Initialize & display player's hand
		System.out.println("You are dealt the following cards: ");
		for(int i = 0; i < handSize; i++)
		{
			playerCard = playerDeck.dealCard();
			playerHand[i] = playerCard;
			System.out.println("#" + (i+1) + "| " + playerHand[i].toString());
		}
		System.out.println();
		
		//Get Player's Choice to play card
		System.out.println("Which card would you like to play? (Enter an Integer from 1 to 7)");
		selection = input.nextInt();
		//Force player to select an Int between 1 and 7, or default to 1
		//doesn't stop player from putting in a double / char / string etc.
		if(selection < 1 || selection > 7)
		{
			selection = 1;
		}
		System.out.println("You selected " + playerHand[(selection-1)]);
		System.out.println();
		
		//Find card and place onto game board, changing entry to an "X" for player and an "O" for computer
		
		
		//Replace the card with a new card, no need to reorganize hand
		//If deck runs out, game is over - exit
		if(playerDeck.cardsLeft() == 0)
		{
			//Exit Game
		}
		playerCard = playerDeck.dealCard();
		playerHand[(selection-1)] = playerCard;
		//Increment Round
		round++;
		
		//Show new Board and new Hand
		System.out.println("Game Board");
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 7; x++)
			{
				System.out.printf("|" + gameBoard[x][y] + "|" + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Your Hand - Round " + round);
		System.out.println();
		for(int i = 0; i < handSize; i++)
		{
			System.out.println("#" + (i+1) + "| " + playerHand[i].toString());
		}
		
	}
}
