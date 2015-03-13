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

public class Board {
	
	private String[][] gameBoard = new String[10][10];
	private Deck deck = new Deck(false);
	private Deck deck2 = new Deck(false);
	private Card nextCard;
	private Deck playerDeck = new Deck(true);
	private Deck playerDeck2 = new Deck(true);
	private Card newCard;
	private String[][] gameBoardBackup = new String[10][10];
	
	//Method of dealing one card to player, uses the first deck until empty, and then the 2nd one.
	public Card dealCard()
	{
		if(playerDeck.cardsLeft() != 0)
		{
			newCard = playerDeck.dealCard();
		}
		else
		{
			newCard = playerDeck2.dealCard();
		}
		return newCard;
	}
	
	public Board(int option)
	{
		//Static GameBoard - not Random
		if(option == 1)
		{
			//Static no shuffling
		}
		//Random Game Board
		if(option == 2)
		{
			deck.shuffle();
			deck2.shuffle();
		}
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				//Add Wild Spots to Game Board if we are at the corners
				if(x == 0 && y == 0 || x == 9 && y == 0 ||
				   x == 9 && y == 9 || x == 0 && y == 9)
				{
					gameBoard[x][y] = "W";
					gameBoardBackup[x][y] = "W";
				}
				else
				{
					//Add Cards from Deck 1 until empty
					if(deck.cardsLeft() > 0)
					{
						nextCard = deck.dealCard();
						//do not add Jacks
						if(nextCard.getValue() == 11)
						{
							//It's a Jack, so get a new card,  & since the deck
							//isn't shuffled, the next card cannot be a Jack
							nextCard = deck.dealCard();
						}
						gameBoard[x][y] = nextCard.toString();
						gameBoardBackup[x][y] = nextCard.toString();
					}
					//Add Cards from Deck 2 now that Deck 1 is out
					else
					{
						nextCard = deck2.dealCard();
						//do not add Jacks
						if(nextCard.getValue() == 11)
						{
							//It's a Jack, so get a new card,  & since the deck
							//isn't shuffled, the next card cannot be a Jack
							nextCard = deck2.dealCard();
						}
						gameBoard[x][y] = nextCard.toString();
						gameBoardBackup[x][y] = nextCard.toString();
					}
				}
			}
		}
		//Shuffle Player's Decks
		playerDeck.shuffle();
		playerDeck2.shuffle();
	}
	public String gameBoardBackup(int x, int y)
	{
		return gameBoardBackup[x][y];
	}
	
	public String gameBoard(int x, int y)
	{
		return gameBoard[x][y];
	}
	
	public void changeBoard(int x, int y, String color)
	{
		gameBoard[x][y] = color;
	}
	
	public void display()
	{
		System.out.println("Game Board");
		for(int y = 0; y < 10; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				//Print Current Name
				System.out.printf("|" + gameBoard[x][y]);
				//If a token is played, print card name behind current name)
				if(gameBoard[x][y] == "Blue" || gameBoard[x][y] == "Green")
				{
					System.out.printf(" On " + gameBoardBackup[x][y] + "|" + " ");
				}
				else
				{
					System.out.printf("|" + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
