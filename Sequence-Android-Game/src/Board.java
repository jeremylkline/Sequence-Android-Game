
public class Board {
	
	private String[][] gameBoard = new String[10][10];
	private Deck deck = new Deck(false);
	private Deck deck2 = new Deck(false);
	private Card nextCard;
	private Deck playerDeck = new Deck(true);
	private Deck playerDeck2 = new Deck(true);
	private Card newCard;
	
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
			for(int x = 0; x < 10; x++)
			{
				for(int y = 0; y < 10; y++)
				{
					//Add Wild Spots to Game Board if we are at the corners
					if(x == 0 && y == 0 || x == 9 && y == 0 ||
					   x == 9 && y == 9 || x == 0 && y == 9)
					{
						gameBoard[x][y] = "W";
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
						}
					}
				}
			}
		}
		//Random Game Board
		if(option == 2)
		{
			//todo
		}
		//Shuffle Player's Decks
		playerDeck.shuffle();
		playerDeck2.shuffle();
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
				System.out.printf("|" + gameBoard[x][y] + "|" + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
