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
	private String cardChoice;
	private boolean optionA;
	private boolean foundCardA;
	private boolean foundCardB;
	private int threeChances;
	
	//Constructor to create the player from the main game loop
	public Player(boolean isHuman, int numOfPlayers, Board gameBoard)
	{
		//Initialize player's score to track progress from game to game
		playerScore = 0;
		optionA = false;
		foundCardA = false;
		foundCardB = false;
		threeChances = 3;
		cardChoice = "Default";
		xCoordA = 0;
		xCoordB = 0;
		yCoordA = 0;
		yCoordB = 0;
		
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
		cardChoice = "Default";
		while(cardChoice != "Cancel")
		{
		//Test for Jack or Joker
		if(playerHand[selection].getValue() == 11)
		{
			System.out.println("#Found a Jack or Joker!#");
			//Test for 1 or 2 eyes
			//One Eyed Jack
			if(playerHand[selection].getSuit() == 0 || playerHand[selection].getSuit() == 1)
			{
				System.out.println("#One Eyed Jack#");
				//Input card to be removed.  Verify token can be removed - is not color of player, but is another color
				//remove it, replace card
				System.out.println("Which card would you like to replace? (Enter Card Name or Cancel)");
				Scanner input = new Scanner (System.in);
				cardChoice = input.nextLine();
				//Continue if we haven't cancelled
					//find cards that were chosen.  eliminate cards that match the color of the player (referenced in)
					for(int y = 0; y < 10; y++)
					{
						for(int x = 0; x < 10; x++)
						{
							//Check to see if a match, and if we haven't already found it the first time
							//We use the backup as that shows us the original card string
							if((gameBoard.gameBoardBackup(x, y) == cardChoice) && !foundCardA)
							{
								//Not valid if we are trying to remove our own token
								if(gameBoard.gameBoard(x, y) != color)
								{
									//Not valid if a gamepiece does not exist on this square
									if((color == "Blue" && gameBoard.gameBoard(x, y) == "Green") || (color == "Green" && gameBoard.gameBoard(x, y) == "Blue"))
									{
										System.out.println("#WE GOT A MATCH!#");
										//Success!  Now we use xCoordA and yCoordA to store this 1st (maybe only) option.
										xCoordA = x;
										yCoordA = y;
										//Now we've found our first card, so lets not overwrite it with the 2nd match (if any)
										foundCardA = true;
									}
								}
							}
							//Only test this if the first card has been found once already.
							if(gameBoard.gameBoardBackup(x, y) == cardChoice && foundCardA)
							{
								System.out.println("#WE GOT TWO MATCHES!#");
								xCoordB = x;
								xCoordB = y;
								foundCardB = true;
							}
							if(foundCardA && foundCardB)
							{
								//no way to find 3 of the same card on the board, might save a few cycles here
								//needs testing to make sure it doesn't break out completely
								break;
							}
						}
					}
					//Looks like we found two locations that would work
					if(foundCardA && foundCardB)
					{
						System.out.println("#TWO CHOICES#");
						//Reset these for next time
						foundCardA = false;
						foundCardB = false;
						
						//Give the player two options
						System.out.println("--------------------------------------------------------------------");
						System.out.println("Option #1: " + cardChoice + "at location: " + xCoordA + "," + yCoordA);
						System.out.println("Option #2: " + cardChoice + "at location: " + xCoordB + "," + yCoordB);
						System.out.println("--------------------------------------------------------------------");
						input = new Scanner (System.in);
						selection = input.nextInt();
						if(selection == 1)
						{
							//Remove token by putting the original card back onto the board using our backup
							System.out.println("You removed the " + gameBoard.gameBoard(xCoordA, yCoordA) + " token!");
							
							cardChoice = gameBoard.gameBoardBackup(xCoordA, xCoordB);
							gameBoard.changeBoard(xCoordA, xCoordB, cardChoice);

							//Let's get out of here!
							cardChoice = "Cancel";
						}
						//If 1 isn't selected, that means 2 is our choice
						else
						{
							System.out.println("You removed the " + gameBoard.gameBoard(xCoordB, yCoordB) + " token!");
							
							cardChoice = gameBoard.gameBoardBackup(xCoordB, yCoordB);
							gameBoard.changeBoard(xCoordA, xCoordB, cardChoice);
							
							//Let's get out of here!
							cardChoice = "Cancel";
						}
					}
					else if(foundCardA)
					{
						//Only 1 option was given, we could confirm this is correct, but we'll just assume
						//that never made an ass out of anyone i know!
						System.out.println("You removed the " + gameBoard.gameBoard(xCoordA, yCoordB) + " token!");
						
						cardChoice = gameBoard.gameBoardBackup(xCoordA, yCoordB);
						gameBoard.changeBoard(xCoordA, xCoordB, cardChoice);
						
						//Let's Leave this Popsicle Stand
						cardChoice = "Cancel";
						
					}
					else
					{
						System.out.println("Error, Try Again!  Chances left: " + threeChances);
						//Infinite loop through recursion if player doesn't select a valid option, or enters "Cancel", so let's
						//only give them three chances before we give up and they "lose a turn"
						if(threeChances > 0)
						{
							threeChances--;
							playCard(selection,gameBoard,color);
						}
						else
						{
							threeChances = 3;
							cardChoice = "Cancel";
						}
					}
			}
			//Two Eyed Jack
			else
			{
				System.out.println("#Two Eyed Jack!#");
				//Input coordinates x and y (color to place was referred in), test that position doesn't
				//already have a marker, place it, replace card
				playerHand[selection] = gameBoard.dealCard();
				cardChoice = "Cancel";
			}
		}
		else if(playerHand[selection].getSuit() == 4)
		{
			//See Two Eyed Jack
			System.out.println("#Found a Joker!#");
			playerHand[selection] = gameBoard.dealCard();
			cardChoice = "Cancel";
		}
		else
		{
			System.out.println("#Not a Jack, nor a Joker!#");
			//Find the card being played - playerHand[i] - and assign first match as option 1
		/*
			//Get option (even if there is only one)
			Scanner input = new Scanner(System.in);
			int option = input.nextInt();
			if(option == 1)
			{
				//Take a new card, to replace the one just played
				playerHand[selection] = gameBoard.dealCard();
				//Update the game board with colored token
				gameBoard.changeBoard(xCoordA, yCoordA, color);
			}
			else
			{
				playerHand[selection] = gameBoard.dealCard();
				gameBoard.changeBoard(xCoordB, yCoordB, color);
			}
		*/
			//Take a new card, to replace the one just played
			playerHand[selection] = gameBoard.dealCard();

			cardChoice = "Cancel";
			
		}
		}
	}
	
	public int getHandsize()
	{
		return handSize;
	}

}
