/*<Sequence-Like Game for Java & Android>
    Copyright (C) 2015  Jeremy L. Kline

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
 	any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

	Jeremy Kline can be reached at jkline@gameneticevo.com & by mail - 
	33 N Lingle Ave. Hershey, PA 17033
 * 
 */

import java.util.Scanner;

public class Player {
	private boolean humanPlayer;
	private float playerScore;
	private Card[] playerHand;
	private int handSize;
	private int xCoordA;
	private int yCoordA;
	private int xCoordB;
	private int yCoordB;
	private String cardChoice;
	private boolean foundCardA;
	private boolean foundCardB;
	private int threeChances;
	private Scanner input;
	private int choice;
	private int totalTokens;
	private int sequences;
	private int badAssCounter;
	private boolean badAssWin;
	
	public boolean isHuman()
	{
		return humanPlayer;
	}
	//Constructor to create the player from the main game loop
	public Player(boolean isHuman, int numOfPlayers, Board gameBoard)
	{
		//Initialize player's score to track progress from game to game
		playerScore = 0;
		foundCardA = false;
		foundCardB = false;
		threeChances = 3;
		cardChoice = "Default";
		xCoordA = 0;
		xCoordB = 0;
		yCoordA = 0;
		yCoordB = 0;
		totalTokens = 0;
		sequences = 0;
		
		//Check to see if this player is human, or computer
		if(isHuman)
		{
			humanPlayer = true;
		}
		else
		{
			humanPlayer = false;
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
			System.out.println("#Found a Jack!#");
			//Test for 1 or 2 eyes
			//One Eyed Jack
			if(playerHand[selection].getSuit() == 0 || playerHand[selection].getSuit() == 1)
			{
				System.out.println("#One Eyed Jack#");
				//Input card to be removed.  Verify token can be removed - is not color of player, but is another color
				//remove it, replace card
				if(humanPlayer)
				{
					System.out.println("Which card would you like to replace? (Enter Card Name or Cancel)");
					input = new Scanner (System.in);
					cardChoice = input.nextLine();
				}
				else
				{
					for(int y = 0; y < 10; y++)
					{
						for(int x = 0; x < 10; x++)
						{
							if(color.equals("Blue"))
							{
								if(gameBoard.gameBoard(x,y).equals("Green"))
								{
									cardChoice = gameBoard.gameBoard(x,y);
									break;
								}
							}
							else if(color.equals("Green"))
							{
								if(gameBoard.gameBoard(x,y).equals("Blue"))
								{
									cardChoice = gameBoard.gameBoard(x,y);
									break;
								}
							}
						}
					}
				}
					//Continue if we haven't cancelled
					//find cards that were chosen.  eliminate cards that match the color of the player (referenced in)
					for(int y = 0; y < 10; y++)
					{
						for(int x = 0; x < 10; x++)
						{
							//Check to see if a match, and if we haven't already found it the first time
							//We use the backup as that shows us the original card string
							if((gameBoard.gameBoardBackup(x, y).equals(cardChoice)) && !foundCardA)
							{
								//Not valid if we are trying to remove our own token
								if(!gameBoard.gameBoard(x, y).equals(color))
								{
									//Not valid if a gamepiece does not exist on this square
									if((color == "Blue" && gameBoard.gameBoard(x, y).equals("Green")) || (color == "Green" && gameBoard.gameBoard(x, y).equals("Blue")))
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
							else if(gameBoard.gameBoardBackup(x, y).equals(cardChoice) && foundCardA)
							{
								System.out.println("#WE GOT TWO MATCHES!#");
								//Not valid if a gamepiece does not exist on this square
								if((color == "Blue" && gameBoard.gameBoard(x, y).equals("Green")) || (color == "Green" && gameBoard.gameBoard(x, y).equals("Blue")))
								{
									xCoordB = x;
									yCoordB = y;
									foundCardB = true;
								}
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
						
						//Give the human player two options
						if(humanPlayer)
						{
								System.out.println("--------------------------------------------------------------------");
								System.out.println("Option #1: " + cardChoice + " at location: " + xCoordA + "," + yCoordA);
								System.out.println("Option #2: " + cardChoice + " at location: " + xCoordB + "," + yCoordB);
								System.out.println("--------------------------------------------------------------------");
								input = new Scanner (System.in);
								choice = input.nextInt();
								if(choice == 1)
								{
									//Remove token by putting the original card back onto the board using our backup
									System.out.println("You removed the " + gameBoard.gameBoard(xCoordA, yCoordA) + " token!");
							
									cardChoice = gameBoard.gameBoardBackup(xCoordA, yCoordA);
									gameBoard.changeBoard(xCoordA, yCoordA, cardChoice);
									playerHand[selection] = gameBoard.dealCard();

									//Let's get out of here!
									playerScore += 10;
									cardChoice = "Cancel";
								}
								else
								{
									System.out.println("You removed the " + gameBoard.gameBoard(xCoordB, yCoordB) + " token!");
									
									cardChoice = gameBoard.gameBoardBackup(xCoordB, yCoordB);
									gameBoard.changeBoard(xCoordB, yCoordB, cardChoice);
									playerHand[selection] = gameBoard.dealCard();
									
									playerScore += 10;
									//Let's get out of here!
									cardChoice = "Cancel";
								}
						}
						else if(!humanPlayer)
						{
							System.out.println("Computer removed the " + gameBoard.gameBoard(xCoordA, yCoordA) + " token!");
							cardChoice = gameBoard.gameBoardBackup(xCoordA, yCoordA);
							gameBoard.changeBoard(xCoordA, yCoordA, cardChoice);
							playerHand[selection] = gameBoard.dealCard();
							playerScore += 10;
							cardChoice = "Cancel";
						}
					}
					else if(foundCardA)
					{
						foundCardA = false;
						//Only 1 option was given, we could confirm this is correct, but we'll just assume
						//that never made an ass out of anyone i know!
						if(humanPlayer)
						{
							System.out.println("You removed the " + gameBoard.gameBoard(xCoordA, yCoordA) + " token!");
						}
						else if(!humanPlayer)
						{
							System.out.println("Computer removed the " + gameBoard.gameBoard(xCoordA, yCoordA) + " token!");
						}
						
						cardChoice = gameBoard.gameBoardBackup(xCoordA, yCoordA);
						gameBoard.changeBoard(xCoordA, yCoordA, cardChoice);
						playerHand[selection] = gameBoard.dealCard();
						
						//Let's Leave this Popsicle Stand
						playerScore += 10;
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
							playerScore -= 2;
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
				if(humanPlayer)
				{	
					System.out.println("Where would you like to play the token? (Enter Name of Card or Cancel)");
					input = new Scanner(System.in);
					cardChoice = input.nextLine();
				}
				else if(!humanPlayer)
				{
					for(int y = 0; y < 10; y++)
					{
						for(int x = 0; x < 10; x++)
						{
							if(gameBoard.gameBoard(x, y).equals("Blue") || gameBoard.gameBoard(x,y).equals("Green"))
							{
								//invalid, so don't select
							}
							//Take the first valid option
							else
							{
								cardChoice = gameBoard.gameBoard(x,y);
							}
						}
					}
				}
				for(int y = 0; y < 10; y++)
				{
					for(int x = 0; x < 10; x++)
					{
						//Check to see if a match, and if we haven't already found it the first time
						//We use the backup as that shows us the original card string
						if((gameBoard.gameBoardBackup(x, y).equals(cardChoice)) && !foundCardA)
						{
							if(gameBoard.gameBoard(x, y).equals("Blue") || gameBoard.gameBoard(x,y).equals("Green"))
							{
								//Do Nothing, this square isn't valid.
							}
							else
							{
								System.out.println("#WE GOT A MATCH!#");
								//Success!  Now we use xCoordA and yCoordA to store this 1st (maybe only) option.
								xCoordA = x;
								yCoordA = y;
								//Now we've found our first card, so lets not overwrite it with the 2nd match (if any)
								foundCardA = true;
							}

						}
						//Only test this if the first card has been found once already.
						else if(gameBoard.gameBoardBackup(x, y).equals(cardChoice) && foundCardA)
						{
							if(gameBoard.gameBoard(x, y).equals("Blue") || gameBoard.gameBoard(x,y).equals("Green"))
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
							else
							{
								System.out.println("#WE GOT TWO MATCHES!#");
								xCoordB = x;
								yCoordB = y;
								foundCardB = true;
							}
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
					
					//Give the human player two options
					if(humanPlayer)
					{
						System.out.println("--------------------------------------------------------------------");
						System.out.println("Option #1: " + cardChoice + "at location: " + xCoordA + "," + yCoordA);
						System.out.println("Option #2: " + cardChoice + "at location: " + xCoordB + "," + yCoordB);
						System.out.println("--------------------------------------------------------------------");
						input = new Scanner (System.in);
						choice = input.nextInt();
						if(choice == 1)
						{
						//Remove token by putting the original card back onto the board using our backup
						System.out.println("You place your token!");
						
						gameBoard.changeBoard(xCoordA, yCoordA, color);
						playerHand[selection] = gameBoard.dealCard();

						//Let's get out of here!
						playerScore += 20;
						cardChoice = "Cancel";
						}
						//If 1 isn't selected, that means 2 is our choice
						else
						{
						System.out.println("You place your token!");
						
						gameBoard.changeBoard(xCoordB, yCoordB, color);
						playerHand[selection] = gameBoard.dealCard();
						
						//Let's get out of here!
						playerScore += 20;
						cardChoice = "Cancel";
						}
					}
					else if(!humanPlayer)
					{
						System.out.println("Computer places their token!");
						gameBoard.changeBoard(xCoordA, yCoordA, color);
						playerHand[selection] = gameBoard.dealCard();
						playerScore += 20;
						cardChoice = "Cancel";
					}
				}
				else if(foundCardA)
				{
					foundCardA = false;
					//Only 1 option was given, we could confirm this is correct, but we'll just assume
					//that never made an ass out of anyone i know!
					if(humanPlayer)
					{
						System.out.println("You place your token!");
					}
					else if(!humanPlayer)
					{
						System.out.println("Computer places their token!");
					}
					
					gameBoard.changeBoard(xCoordA, yCoordA, color);
					playerHand[selection] = gameBoard.dealCard();
					
					//Let's Leave this Popsicle Stand
					playerScore += 20;
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
						playerScore -= 2;
						playCard(selection,gameBoard,color);
					}
					else
					{
						threeChances = 3;
						cardChoice = "Cancel";
					}
				}
			}
		}
		else if(playerHand[selection].getSuit() == 4)
		{
			//See Two Eyed Jack
			System.out.println("#Found a Joker!#");
			
			if(humanPlayer)
			{
			System.out.println("Where would you like to play the token? (Enter Name of Card or Cancel)");
			input = new Scanner(System.in);
			cardChoice = input.nextLine();
			}
			else if(!humanPlayer)
			{
				for(int y = 0; y < 10; y++)
				{
					for(int x = 0; x < 10; x++)
					{
						if(gameBoard.gameBoard(x, y).equals("Blue") || gameBoard.gameBoard(x,y).equals("Green"))
						{
							//invalid, so don't select
						}
						//Take the first valid option
						else
						{
							cardChoice = gameBoard.gameBoard(x,y);
						}
					}
				}
			}
			for(int y = 0; y < 10; y++)
			{
				for(int x = 0; x < 10; x++)
				{
					//Check to see if a match, and if we haven't already found it the first time
					//We use the backup as that shows us the original card string
					if((gameBoard.gameBoardBackup(x, y).equals(cardChoice)) && !foundCardA)
					{
						if(gameBoard.gameBoard(x, y).equals("Blue") || gameBoard.gameBoard(x, y).equals("Green"))
						{
							//Do Nothing, this square isn't valid.
						}
						else
						{
							System.out.println("#WE GOT A MATCH!#");
							//Success!  Now we use xCoordA and yCoordA to store this 1st (maybe only) option.
							xCoordA = x;
							yCoordA = y;
							//Now we've found our first card, so lets not overwrite it with the 2nd match (if any)
							foundCardA = true;
						}

					}
					//Only test this if the first card has been found once already.
					else if((gameBoard.gameBoardBackup(x, y).equals(cardChoice)) && foundCardA)
					{
						if(gameBoard.gameBoard(x,y).equals("Blue") || gameBoard.gameBoard(x, y).equals("Green"))
						{
							//Do nothing, this square isn't valid
						}
						else
						{
							System.out.println("#WE GOT TWO MATCHES!#");
							xCoordB = x;
							yCoordB = y;
							foundCardB = true;
						}
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
				
				if(humanPlayer)
				{
					//Give the player two options
					System.out.println("--------------------------------------------------------------------");
					System.out.println("Option #1: " + cardChoice + "at location: " + xCoordA + "," + yCoordA);
					System.out.println("Option #2: " + cardChoice + "at location: " + xCoordB + "," + yCoordB);
					System.out.println("--------------------------------------------------------------------");
					input = new Scanner (System.in);
					choice = input.nextInt();
					if(choice == 1)
					{
						//Remove token by putting the original card back onto the board using our backup
						System.out.println("You place your token!");
					
						gameBoard.changeBoard(xCoordA, yCoordA, color);
						playerHand[selection] = gameBoard.dealCard();

						//Let's get out of here!
						playerScore += 50;
						cardChoice = "Cancel";
					}
					//If 1 isn't selected, that means 2 is our choice
					else
					{
						System.out.println("You place your token!");
					
						gameBoard.changeBoard(xCoordB, yCoordB, color);
						playerHand[selection] = gameBoard.dealCard();
					
						//Let's get out of here!
						playerScore += 50;
						cardChoice = "Cancel";
					}
				}
				else if(!humanPlayer)
				{
					System.out.println("Computer places their token!");
					gameBoard.changeBoard(xCoordA, yCoordA, color);
					playerHand[selection] = gameBoard.dealCard();
					playerScore += 50;
					cardChoice = "Cancel";
				}
			}
			else if(foundCardA)
			{
				foundCardA = false;
				//Only 1 option was given, we could confirm this is correct, but we'll just assume
				//that never made an ass out of anyone i know!
				if(humanPlayer)
				{
					System.out.println("You place your token!");
				}
				else if(!humanPlayer)
				{
					System.out.println("Computer places their token!");
				}
				
				gameBoard.changeBoard(xCoordA, yCoordA, color);
				playerHand[selection] = gameBoard.dealCard();
				
				//Let's Leave this Popsicle Stand
				playerScore += 50;
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
					playerScore -= 2;
					playCard(selection,gameBoard,color);
				}
				else
				{
					cardChoice = "Cancel";
				}
			}
		}
		else
		{
			System.out.println("#Not a Jack, nor a Joker!#");
			//Find the card being played - playerHand[i] - and assign first match as option 1

			cardChoice = playerHand[selection].toString();
			for(int y = 0; y < 10; y++)
			{
				for(int x = 0; x < 10; x++)
				{
					if((gameBoard.gameBoard(x, y).equals(cardChoice)) && !foundCardA)
					{
						xCoordA = x;
						yCoordA = y;
						foundCardA = true;
					}
					else if((gameBoard.gameBoard(x, y).equals(cardChoice)) && foundCardA)
					{
						xCoordB = x;
						yCoordB = y;
						foundCardB = true;
					}
					if(foundCardA && foundCardB)
					{
						//No third option, lets break out of this loop
						break;
					}
				}
			}
			if(foundCardA && foundCardB)
			{
				//Two Options to present to player
				System.out.println("#TWO CHOICES#");
				//Reset these for next time
				foundCardA = false;
				foundCardB = false;
				
				if(humanPlayer)
				{
					//Give the player two options
					System.out.println("--------------------------------------------------------------------");
					System.out.println("Option #1: " + cardChoice + " at location: " + xCoordA + "," + yCoordA);
					System.out.println("Option #2: " + cardChoice + " at location: " + xCoordB + "," + yCoordB);
					System.out.println("--------------------------------------------------------------------");
					input = new Scanner (System.in);
					choice = input.nextInt();
					if(choice == 1)
					{
						System.out.println("You place your token!");
						gameBoard.changeBoard(xCoordA, yCoordA, color);
						playerHand[selection] = gameBoard.dealCard();
						playerScore += 5;
						cardChoice = "Cancel";
					}
					else
					{
						System.out.println("You place your token!");
						gameBoard.changeBoard(xCoordB, yCoordB, color);
						playerHand[selection] = gameBoard.dealCard();
						playerScore += 5;
						cardChoice = "Cancel";
					}
				}
				else if(!humanPlayer)
				{
					System.out.println("Computer places their token!");
					gameBoard.changeBoard(xCoordA, yCoordA, color);
					playerHand[selection] = gameBoard.dealCard();
					playerScore += 5;
					cardChoice = "Cancel";
				}
			}
			else if(foundCardA)
			{
				if(humanPlayer)
				{
					System.out.println("You place your token!");
				}
				else if(!humanPlayer)
				{
					System.out.println("Computer places their token!");
				}
				gameBoard.changeBoard(xCoordA, yCoordA, color);
				playerHand[selection] = gameBoard.dealCard();
				playerScore += 5;
				cardChoice = "Cancel";
			}
			else
			{
				//If no options for a normal card, it is dead (that is jokers or two eyed jacks
				//used up both spaces, so we replace the card, and the player should get an extra turn
				//not implemented yet
				if(humanPlayer)
					{
						System.out.println("This Card is Dead, you replace it with a new one!");
					}
				else if(!humanPlayer)
				{
					System.out.println("This Card is Dead, the computer replaced it with a new one!");
				}
				playerHand[selection] = gameBoard.dealCard();
				playerScore -= 15;
				cardChoice = "Cancel";
			}
			
		}
		
		}
		//Test for "Sequence" - Win condition - and call endGame function
		//To win the game you must have "2 Sequences", a Sequence is a row of 5 tokens that are consecutive and go either horizontal, diagonal, or vertical.
		/*Examples of Valid Sequences
		 * 		0	1	2	3	4	5	6	7	8	9
		 * 	0	W	0	0	0	0	0	0	0	0	W
		 *  1	X	X	X	X	X	0	0	0	X	0	
		 *  2	0	0	0	0	0	0	0	X	X	0	
		 *  3	0	0	0	0	0	0	X	0	X	0	
		 *  4	0	0	0	0	0	X	0	0	X	0	
		 * 	5	0	0	0	0	0	0	0	0	X	0	
		 * 	6	0	0	0	0	0	0	0	0	0	0	
		 *  7	0	0	0	0	0	0	0	0	0	0	
		 *  8	0	0	0	0	0	0	0	0	0	0
		 *  9	W 	0	0	0	0	0	0	0	0	W
		 */
		//A sequence can use at most 1 token from a prior sequence, and a sequence can include 1 Wild square.  So the first sequence at minimum needs 4 tokens.  The second
		//one would need 4 more, for a minimum of 8
		//Find tokens
		//reset values to default, so we dont' give credit for the last player's tokens
		totalTokens = 0;
		sequences = 0;
		
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
				{
					//Find all of the current player's tokens
					if(gameBoard.gameBoard(x, y).equals(color))
					{
						//increment total tokens
						totalTokens++;
					}
				}
		}
		//If they don't have 8, they can't "win", give up now
		if(totalTokens >= 8)
		{
				//Check for Badass Wins - IE all of 1 row, all of 1 column, or all of 1 diagonal
				/*
				 * X X X X X X X X X X
				 * X X             X
				 * X   X         X
				 * X     X     X
				 * X       X X
				 * X       X X
				 * X     X     X
				 * X   X         X
				 * X X             X
				 * X                 X
				 */
				//Reset badAss vars
				badAssCounter = 0;
				badAssWin = false;
				
				//Check along the rows
				while(badAssCounter < 10)
				{
					//check for 10 in a row along the X axis
					for(int x = 0; x < 10; x++)
					{
						badAssCounter = 0;
						for(int y = 0; y < 10; y++)
						{
							if(gameBoard.gameBoard(x,y).equals(color))
							{
								badAssCounter++;
							}
						}
					}
					badAssCounter = 0;
					//check for 10 in a row along the Y axis
					for(int y = 0; y < 10; y++)
					{
						badAssCounter = 0;
						for(int x = 0; x < 10; x++)
						{
							if(gameBoard.gameBoard(x, y).equals(color))
							{
								badAssCounter++;
							}
						}
					}
					badAssCounter = 0;
					//check for 10 in a row along the NW to SE
					for(int x = 0; x < 10; x++)
					{
						for(int y = 0; y < 10; y++)
						{
							if(gameBoard.gameBoard(x,y).equals(color))
							{
								//NW to SE route
								if(x == 1 && y == 1 ||
								   x == 2 && y == 2 ||
								   x == 3 && y == 3 ||
								   x == 4 && y == 4 ||
								   x == 5 && y == 5 ||
								   x == 6 && y == 6 ||
								   x == 7 && y == 7 ||
								   x == 8 && y == 8	  )
								{
									badAssCounter++;
								}
								//NE to SW route
								if(x == 8 && y == 1 ||
								   x == 7 && y == 2 ||
								   x == 6 && y == 3 ||
								   x == 5 && y == 4 ||
								   x == 4 && y == 5 ||
								   x == 3 && y == 6 ||
								   x == 2 && y == 7 ||
								   x == 1 && y == 8   )
								{
									badAssCounter++;
								}
							}
						}
					}
				}
				if(badAssCounter == 10)
				{
					badAssWin = true;
					playerScore += 200;
					sequences = 2;
				}
				//Then check for normal sequences
				//Horizontal Checks
				for(int y = 0; y <10; y++)
				{
					if(checkForHorizontalSequence(gameBoard, color, y))
					{
						if(humanPlayer)
							{
								playerScore += 50;
								System.out.println("You got a Horizontal Sequence!");
							}
						if(!humanPlayer)
							{
								playerScore += 50;
								System.out.println("Computer got a Horizontal Sequence!");
							}
						sequences++;
					}
				}
				//Vertical Checks
				for(int x = 0; x < 10; x++)
				{
					if(checkForVerticalSequence(gameBoard, color, x))
					{
						if(humanPlayer)
						{
							playerScore += 50;
							System.out.println("You got a Vertical Sequence!");		
						}
						if(!humanPlayer)
						{
							playerScore += 50;
							System.out.println("Computer got a Vertical Sequence!");
						}
						sequences++;
					}
				}
				
				//Diagonal Checks
				
		}
		if(sequences >= 2)
		{
			//End Game
			playerScore += (100 * sequences);
			System.out.println("Congrats you won!  Your score was: " + playerScore);
			System.out.println("Enter any value to exit!");
			input = new Scanner(System.in);
			choice = input.nextInt();
			input.close();
			System.exit(0);
		}
		
	}
	
	public boolean checkForDiagonalSequence(Board gameBoard, String color)
	{
		boolean found = false;
		boolean exit = false;
		//Check for Wild Sequences - (1,1) - (4,4) || (8,1) - (5,4) || (1,8) - (4,5) || (8,8) - (5,5)
		if(	gameBoard.gameBoard(1, 1).equals(color) &&
			gameBoard.gameBoard(2, 2).equals(color) &&
			gameBoard.gameBoard(3, 3).equals(color) &&
			gameBoard.gameBoard(4, 4).equals(color)
			)
		{
			found = true;
		}
		else if( gameBoard.gameBoard(8, 1).equals(color) &&
			gameBoard.gameBoard(7, 2).equals(color) &&
			gameBoard.gameBoard(6, 3).equals(color) &&
			gameBoard.gameBoard(5, 4).equals(color)
			)
		{
			found = true;
		}
		else if( gameBoard.gameBoard(1, 8).equals(color) &&
			gameBoard.gameBoard(2, 7).equals(color) &&
			gameBoard.gameBoard(3, 6).equals(color) &&
			gameBoard.gameBoard(4, 5).equals(color)
			)
		{
				found = true;
		}
		else if( gameBoard.gameBoard(8, 8).equals(color) &&
			gameBoard.gameBoard(7, 7).equals(color) &&
			gameBoard.gameBoard(6, 6).equals(color) &&
			gameBoard.gameBoard(5, 5).equals(color)
			)
		{
					found = true;
		}
		//Check for normal sequences
		int counter = 0;
		while(!found || !exit)
		{
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				
				//test NE for 5 matches
				for(int z = 0; z < 6; z++)
				{
					//if we have a token, search NE for the next one, but be aware of boundaries
					if(gameBoard.gameBoard(x,y).equals(color))
					{
						if(x < 9 && y < 9)
						{
							x++;
							y++;
							counter++;
						}
					}
				}
				if(counter >= 5)
				{
					found = true;
					exit = true;
				}
				else
				{
					counter = 0;
				}
				//test SW for 5 matches
				for(int z = 0; z < 6; z++)
				{
					//if we have a token, search SW for the next one, but be aware of boundaries
					if(gameBoard.gameBoard(x,y).equals(color))
					{
						if(x > 0 && y > 0)
						{
							x--;
							y--;
							counter++;
						}
					}
				}
				if(counter >= 5)
				{
					found = true;
					exit = true;
				}
				else
				{
					counter = 0;
				}
				//test SE for 5 matches
				for(int z = 0; z < 6; z++)
				{
					if(gameBoard.gameBoard(x,y).equals(color))
					{
						if(x < 9 && y > 0)
						{
							x++;
							y--;
							counter++;
						}
					}
				}
				if(counter >= 5)
				{
					found = true;
					exit = true;
				}
				else
				{
					counter = 0;
				}
				//Test NW for 5 matches
				for(int z = 0; z < 6; z++)
				{
					if(gameBoard.gameBoard(x,y).equals(color))
					{
						if(x > 0 && y < 9)
						{
							x--;
							y++;
							counter++;
						}
					}
				}
				if(counter >= 5)
				{
					found = true;
					exit = true;
				}
			}
		}
		//If we have checked every value and haven't gotten a diagonal sequence, there isn't one
		exit = true;
		}
		
		
		return found;
	}
	
	public boolean checkForVerticalSequence(Board gameBoard, String color, int column)
	{
		boolean found = false;
		//Check for Wild Sequences
		if(column == 0 || column == 9)
		{
			//Sequence in Primary Position
			if(		gameBoard.gameBoard(column,1).equals(color) &&
					gameBoard.gameBoard(column,2).equals(color) &&
					gameBoard.gameBoard(column,3).equals(color) &&
					gameBoard.gameBoard(column,4).equals(color)
			  )
			{
				found = true;
			}
			if(		gameBoard.gameBoard(column,5).equals(color) &&
					gameBoard.gameBoard(column,6).equals(color) &&
					gameBoard.gameBoard(column,7).equals(color) &&
					gameBoard.gameBoard(column,8).equals(color)
			  )
			{
				found = true;
			}
		}
		else
		{
			//Sequence in Primary Position
			if(		gameBoard.gameBoard(column,0).equals(color) &&
					gameBoard.gameBoard(column,1).equals(color) &&
					gameBoard.gameBoard(column,2).equals(color) &&
					gameBoard.gameBoard(column,3).equals(color) &&
					gameBoard.gameBoard(column,4).equals(color)
			  )
			{
				found = true;
			}
			else if(gameBoard.gameBoard(column,1).equals(color) &&
					gameBoard.gameBoard(column,2).equals(color) &&
					gameBoard.gameBoard(column,3).equals(color) &&
					gameBoard.gameBoard(column,4).equals(color) &&
					gameBoard.gameBoard(column,5).equals(color)
			       )
			{
				found = true;
			}
			else if(gameBoard.gameBoard(column,2).equals(color) &&
					gameBoard.gameBoard(column,3).equals(color) &&
					gameBoard.gameBoard(column,4).equals(color) &&
					gameBoard.gameBoard(column,5).equals(color) &&
					gameBoard.gameBoard(column,6).equals(color)
			       )
			{
				found = true;
			}
			else if(gameBoard.gameBoard(column,3).equals(color) &&
					gameBoard.gameBoard(column,4).equals(color) &&
					gameBoard.gameBoard(column,5).equals(color) &&
					gameBoard.gameBoard(column,6).equals(color) &&
					gameBoard.gameBoard(column,7).equals(color)
			       )
			{
				found = true;
			}
			else if(gameBoard.gameBoard(column,4).equals(color) &&
					gameBoard.gameBoard(column,5).equals(color) &&
					gameBoard.gameBoard(column,6).equals(color) &&
					gameBoard.gameBoard(column,7).equals(color) &&
					gameBoard.gameBoard(column,8).equals(color)
			       )
			{
				found = true;
			}
			else if(gameBoard.gameBoard(column,5).equals(color) &&
					gameBoard.gameBoard(column,6).equals(color) &&
					gameBoard.gameBoard(column,7).equals(color) &&
					gameBoard.gameBoard(column,8).equals(color) &&
					gameBoard.gameBoard(column,9).equals(color)
			       )
			{
				found = true;
			}
		}
		return found;
	}
	
	public boolean checkForHorizontalSequence(Board gameBoard, String color, int row)
	{
		boolean found = false;
		//If Row is 0 or 9, Check for Wild Sequences
		if(row == 0 || row == 9)
		{
			//Sequence in Primary Position
			if(		gameBoard.gameBoard(1,row).equals(color) &&
					gameBoard.gameBoard(2,row).equals(color) &&
					gameBoard.gameBoard(3,row).equals(color) &&
					gameBoard.gameBoard(4,row).equals(color)
			  )
			{
				found = true;
			}
			//Sequence in Primary Position
			else if(	gameBoard.gameBoard(5,row).equals(color) &&
						gameBoard.gameBoard(6,row).equals(color) &&
						gameBoard.gameBoard(7,row).equals(color) &&
						gameBoard.gameBoard(8,row).equals(color)
				   )
			{
				found = true;
			}
		}
		//Else check for normal sequence
		else
		{
			//Sequence in Primary Position
			if(		gameBoard.gameBoard(0,row).equals(color) &&
					gameBoard.gameBoard(1,row).equals(color) &&
					gameBoard.gameBoard(2,row).equals(color) &&
					gameBoard.gameBoard(3,row).equals(color) &&
					gameBoard.gameBoard(4,row).equals(color)
			  )
			{
				found = true;
			}
			//Sequence in +1 Offset
			else if(		gameBoard.gameBoard(1,row).equals(color) &&
					gameBoard.gameBoard(2,row).equals(color) &&
					gameBoard.gameBoard(3,row).equals(color) &&
					gameBoard.gameBoard(4,row).equals(color) &&
					gameBoard.gameBoard(5,row).equals(color)
			  )
			{
				found = true;
			}
			else if(		gameBoard.gameBoard(2,row).equals(color) &&
					gameBoard.gameBoard(3,row).equals(color) &&
					gameBoard.gameBoard(4,row).equals(color) &&
					gameBoard.gameBoard(5,row).equals(color) &&
					gameBoard.gameBoard(6,row).equals(color)
			  )
			{
				found = true;
			}
			else if(		gameBoard.gameBoard(3,row).equals(color) &&
					gameBoard.gameBoard(4,row).equals(color) &&
					gameBoard.gameBoard(5,row).equals(color) &&
					gameBoard.gameBoard(6,row).equals(color) &&
					gameBoard.gameBoard(7,row).equals(color)
			  )
			{
				found = true;
			}
			else if(		gameBoard.gameBoard(4,row).equals(color) &&
					gameBoard.gameBoard(5,row).equals(color) &&
					gameBoard.gameBoard(6,row).equals(color) &&
					gameBoard.gameBoard(7,row).equals(color) &&
					gameBoard.gameBoard(8,row).equals(color)
			  )
			{
				found = true;
			}
			else if(		gameBoard.gameBoard(5,row).equals(color) &&
					gameBoard.gameBoard(6,row).equals(color) &&
					gameBoard.gameBoard(7,row).equals(color) &&
					gameBoard.gameBoard(8,row).equals(color) &&
					gameBoard.gameBoard(9,row).equals(color)
			  )
			{
				found = true;
			}
		}
		return found;
	}
	
	public int getHandsize()
	{
		return handSize;
	}

}
