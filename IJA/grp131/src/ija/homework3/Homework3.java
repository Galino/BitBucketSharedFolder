/**
 * Authors: Michal Klco, Lukas Galbicka
 * Emails: xklcom00@stud.fit.vutbr.cz  xgalbi01@stud.fit.vutbr.cz
 * Description: Class for Text User Interface 
 */
package ija.homework3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



import ija.homework2.board.*; 
import ija.homework2.board.MazeCard.CANGO;

/**
 * Class for Text User Interface
 * Create game and allow to control the game using commands:
 * 		p	- print game board
 *		n 	- create new game
 *		q 	- quit game
 *		sRC	- put free card on [R, C] field
 */
public class Homework3 {
	public static void main(String[] args) {
		System.out.println("Welcome at Labyrinth");
		System.out.println("Commands:");
		System.out.println("	p	- print game board");
		System.out.println("	n 	- create new game");
		System.out.println("	q 	- quit game");
		System.out.println("	sRC	- put free card on [R, C] field");
		boolean running = true;
		
		MazeBoard board = MazeBoard.createMazeBoard(7);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		while(running){
			try {
				input = br.readLine();
				if(input.matches("^s\\d{2}$")){
					if(board.getFreeCard() == null){
						System.out.println("New game was not created!");
					}
					else{
						System.out.println("Trying to shift!");
						MazeField mField = board.get(Character.getNumericValue(input.charAt(1)),
								Character.getNumericValue(input.charAt(2)));
                        if(mField != null){
                            board.shift(mField);
                        }
					}
				}
				else if(input.matches("^p$")){
					if(board.getFreeCard() == null){
						System.out.println("New game was not created!");
					}
					else{
						printBoard(board);
					}
				}
				else if(input.matches("^n$")){
					board.newGame();
					System.out.println("New game was created successfully!");
				}
				else if(input.matches("^q$")){
					board = null;
					br.close();
					running = false;
					System.out.println("Game was closed!");
				}
				else{
					System.out.println("Unknown command!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}

	}
	
	public static void printBoard(MazeBoard board){
		MazeCard mc;
		String card;
		for(int i = 1; i <= 7; i++){
			for(int j = 1; j <= 7; j++){
				mc = board.get(i, j).getCard();
				card = "|";
				if(mc.canGo(CANGO.LEFT)){
					card += "-";
				}
				else{
					card += " ";
				}
				if(mc.canGo(CANGO.UP)){
					card += "'";
				}
				else{
					card += " ";
				}
				if(mc.canGo(CANGO.DOWN)){
					card += ",";
				}
				else{
					card += " ";
				}
				if(mc.canGo(CANGO.RIGHT)){
					card += "-";
				}
				else{
					card += " ";
				}
				card += "|";
				System.out.print(card);
			}
			System.out.println();
		}
		System.out.print("FreeCard: ");
		mc = board.getFreeCard();
		card = "|";
		if(mc.canGo(CANGO.LEFT)){
			card += "-";
		}
		else{
			card += " ";
		}
		if(mc.canGo(CANGO.UP)){
			card += "'";
		}
		else{
			card += " ";
		}
		if(mc.canGo(CANGO.DOWN)){
			card += ",";
		}
		else{
			card += " ";
		}
		if(mc.canGo(CANGO.RIGHT)){
			card += "-";
		}
		else{
			card += " ";
		}
		card += "|";
		System.out.print(card);
		System.out.println();
	}
}
