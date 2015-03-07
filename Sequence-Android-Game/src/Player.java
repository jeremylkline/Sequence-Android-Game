import java.util.Scanner;

public class Player {
	private boolean humanPlayer;
	private boolean computerPlayer;
	private float playerScore;
	private Card[] playerHand;
	private int handSize;
	private int xCoordA;
	private int yCoordA;
	private int xCoordB;
	private int yCoordB;
	
	//Constructor to create the player from the main game loop
	public Player(boolean isHuman, int numOfPlayers, Board gameBoard)
	{
		//Initialize player's score to track progress from game to game
		playerScore = 0;
		
		//Check to see if this player is human, or computer
		if(isHuman)
		{
			humanPlayer = true;
			computerPlayer = false;
		}
		else
		{
			humanPlayer = false;
			computerPlayer = true;
		}
		
		//Number of players determines the hand size per player
		if(numOfPlayers == 2)
		{
			handSize = 7;
		}
		if(numOfPlayers == 3 || numOfPlayers == 4)
		{
			handSize = 6;
		}
		
		//Initialize the player's hand - we'll fill it later, as the deck does not belong
		//to the player, but rather the game board
		//Don't ever do this - Card[] playerHand = new Card[handSize];!!!!!!!!!!
		playerHand = new Card[handSize];
		//Fill player's hand to start
		for(int i = 0; i < handSize; i++)
		{
			playerHand[i] = gameBoard.dealCard();
		}
	}
	
	//We need a way to display the player's hand
	public void displayHand()
	{
		//Separate from the last output
		System.out.println("-----------------------------------------");
		System.out.println("You hold the following cards: ");
		System.out.println("-----------------------------------------");
		//Hand should always be full, as we replace cards immediately once they are played
		//therefore we can always display an entire "handSize" of cards.  If no cards can
		//be drawn from the deck, we either re-create the deck, or more likely end the game,
		//unlikely there are any options to win left at this point due to the method of playing
		for(int i = 0; i < handSize; i++)
		{
			System.out.println("#" + (i+1) + "| " + playerHand[i].toString() + " |");
		}
	}
	
	//We need a way to play a card onto the board, removing it from the player's hand.
	//At the same time, we replace that card with a new one.
	public void playCard(int selection, Board gameBoard, String color)
	{
		//Test for Jack or Joker
		if(playerHand[selection].getValue() == 11)
		{
			//Test for 1 or 2 eyes
			//One Eyed Jack
			if(playerHand[selection].getSuit() == 0 || playerHand[selection].getSuit() == 1)
			{
				//Input coordinates x, y, and color to be removed.  Verify token can be removed
				//remove it, replace card
			}
			//Two Eyed Jack
			else
			{
				//Input coordinates x and y (color to place was referred in), test that position doesn't
				//already have a marker, place it, replace card
			}
		}
		else if(playerHand[selection].getSuit() == 4)
		{
			//See Two Eyed Jack
		}
		else
		{
			//Find the card being played - playerHand[i] - and assign first match as option 1
			xCoordA = 5;
			yCoordA = 5;
			System.out.println("Option 1: " + xCoordA + " " + yCoordA);
			//If found a 2nd time, add option 2
			xCoordB = 7;
			yCoordB = 7;
			System.out.println("Option 2: " + xCoordB + " " + yCoordB);
		
			//Get option (even if there is only one)
			Scanner input = new Scanner(System.in);
			int option = input.nextInt();
			if(option == 1)
			{
				//Take a new card, to replace the one just played
				playerHand[selection] = gameBoard.dealCard();
				//Update the game board with colored token
				gameBoard.changeBoard(xCoordA, yCoordA, color);
				//Display the game board with changes
				gameBoard.display();
			}
			else
			{
				playerHand[selection] = gameBoard.dealCard();
				gameBoard.changeBoard(xCoordB, yCoordB, color);
			}
		}
		
	}
	
	public int getHandsize()
	{
		return handSize;
	}

}
