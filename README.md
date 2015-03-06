# Sequence-Android-Game
Sequence style game for android devices.  

Sequence is a board game played with two decks of cards, including jokers, and 3 different colored coin tokens.  

The board consists of a grid 10 x 10 filled with the cards from two standard decks. - But thats only 90! two decks in 104 or 108
with jokers.  Well wild spaces in the 4 corners (96) and Jacks and Jokers are not on the board.  (104 - 8 = 96, 108 - 12 = 96)
Jacks instead act as jokers albeit with a twist: One-eyed jacks remove a game piece.
Two-eyed jacks and jokers allow a piece to be played anywhere.

Each round you place a game piece and discard the corresponding card from your hand.  You must then draw a new card to keep
your original hand size.  The first player to score a sequence - that is 5 pieces in a row - either horizontal, vertical or
diagonally wins in a 3 player or 3 teams of 2 (6 player version).  In other versions - 2, or 4 players you need to score
two sequences to win.

Strategy is light, and at most you have two options for each non-wild card to be played.  Chance is a large part, and when
playing as a team you are not allowed to disclose your hand contents.

This version of the game will include an AI/Computer player with varying levels of "skill".  Also, since we are playing
on a computer, the game board can be generated randomly or statically as you would see in real life.  The real life board groups
cards in order by suit, sharing at least one N, S, E or W border with the next card in the suit.  IE 2 of Hearts must have the
3 of Hearts as a neighbor to either the N, S, E or W.  3 of Hearts must also have 4 of Hearts to N, S, E or W; and so on, with
the Ace being adjacent to the King, and not necessarily the 2.  The "wild" spaces on the 4 corners are free and can be used
as part of a sequence allowing you to score in as few as 4 moves, and are not locked in to any one players color/team.  In
other words you and your opponent in a game of 2 sequences to win; each use the same corner wild to create a sequence.  
As you are not able to play over that player's tokens, if they score horizontal, you'd need to score vertical or diagonal
instead.

