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


//To handle inputs
import java.util.Scanner;
//Random numbers
import java.util.Random;
//To-do
/*Finish algorithm for win condition - all done except Diagonals & Badass
 *Add 3+ Players (Humans side-by-side / or Multiple Computers
 *Advanced AI - Easy / Medium / Hard - Currently random
 *Graphics
 *Android Version
 */


public class Application {
	public static void main(String[] args) {
		
		gameFrame gFrame = new gameFrame();
		
		gFrame.createAndShowGUI();
		gFrame.displayText("Sequence-Like Game  Copyright (C) 2015  Jeremy L. Kline\n\n"
				+ "Welcome to Mini-Sequence, a card and board game!\n\n"
				+ "The object of the game is to play cards on the board"
				+ " in order to place 5 in a row either horizontal, diagonal"
				+ " or vertical.", "BLACK");
		
		//Int to store borrowers card choices
		int selection;
		//Keep track of Rounds
		int round = 1;
		
		//Exit trigger
		boolean exit = false;
		
		//Create a Gameboard, 1 for Static, 2 for Random
		Board gameBoard = new Board(2);
		
		//Create Players
		Player playerOne = new Player(true, 2, gameBoard);
		
		Player playerTwo = new Player(false, 2, gameBoard);		
		
		Random randomNum = new Random();
		
		Scanner input = new Scanner(System.in);
	
		//Beginning Loop for player's hand
		while(!exit)
		{
			//Display Board
			gameBoard.display();
			
			if(playerOne.isHuman())
			{
				//Display player's hand
				System.out.println("-----------------------------------------");
				System.out.println("Player #1: ");
				playerOne.displayHand();
			
				//Take a card away and replace it.
				System.out.println("Player #1, Which card will you play? (Enter the integer)" + "Round: " + round);
				input = new Scanner(System.in);
				//Subtract 1 to reference the array starts at 0 not 1, force a selection between 1 and hand size
				//default selection is 1st card
				selection = input.nextInt() - 1;
				if(selection < 0 || selection > (playerOne.getHandsize()-1))
				{
					selection = 0;
				}
				playerOne.playCard(selection, gameBoard, "Blue");
			}
			else
			{
				selection = randomNum.nextInt(playerTwo.getHandsize());
				playerOne.playCard(selection, gameBoard, "Blue");
			}
			
			if(playerTwo.isHuman())
			{
				//Display player's hand
				System.out.println("-----------------------------------------");
				System.out.println("Player #2: ");
				playerOne.displayHand();
			
				//Take a card away and replace it.
				System.out.println("Player #2, Which card will you play? (Enter the integer)" + "Round: " + round);
				input = new Scanner(System.in);
				//Subtract 1 to reference the array starts at 0 not 1, force a selection between 1 and hand size
				//default selection is 1st card
				selection = input.nextInt() - 1;
				if(selection < 0 || selection > (playerOne.getHandsize()-1))
				{
					selection = 0;
				}
				playerTwo.playCard(selection, gameBoard, "Green");
			}
			else
			{
				selection = randomNum.nextInt(playerTwo.getHandsize());
				playerTwo.playCard(selection, gameBoard, "Green");
			}

		
			//Increment Round
			round++;
		
			//Loop Back to updated board and new hand of cards
		}
		input.close();
		System.out.println("Thanks for playing!");
	}
}
