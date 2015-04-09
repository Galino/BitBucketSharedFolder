/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;

import java.util.Random;
/**
 * Class represents game board with fields.
 * @author Michal Klco
 */
public class MazeBoard {

	private int size;
	private MazeField[][] fields;
	private MazeCard freeCard;
	
	/**
	 * Static method for creating game board.
	 * @param 	n	size of board (it will contain n*n fields).
	 * @return	board.
	 */
	public static MazeBoard createMazeBoard(int n){
		MazeBoard board = new MazeBoard();
		board.size = n;
		board.fields = new MazeField[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				board.fields[i][j] = new MazeField(i+1, j+1);
			}
		}
		return board;
	}
	
	/**
	 * Method return field at [r, c] position.
	 * @param 	r	number of row (starts with 1).
	 * @param 	c	number of collumn (starts with 1).
	 * @return	field at [r, c] position.
	 */
	public MazeField get(int r, int c){
		if(r > this.size || c > this.size){
			System.out.println("Trying to get MazeField out of board!!!");
			return null;
		}
		else{
			return fields[r-1][c-1];
		}
	}
	
	/**
	 * Method returns free card in game.
	 * @return free card.
	 */
	public MazeCard getFreeCard(){
		return this.freeCard;
	}
	
	/**
	 * Method place free card on position of chosen field.
	 * If free card can not be placed on selected position, nothing will happen.
	 * @param mf chosen position
	 */
	public void shift(MazeField mf){
		if(mf.row() == 1){
			if(mf.col()%2 == 0){
				MazeCard helpCard = this.freeCard;
				for(int i = this.size; i>=0; i--){
					if(i == this.size){
						this.freeCard = this.fields[i-1][mf.col()-1].getCard();
					}
					else if(i == 0){
						this.fields[i][mf.col()-1].putCard(helpCard);
					}
					else{
						this.fields[i][mf.col()-1].putCard(this.fields[i-1][mf.col()-1].getCard());
					}
				}
			}

		}
		else if(mf.row() == this.size){
			if(mf.col()%2 == 0){
				MazeCard helpCard = this.freeCard;
				for(int i = 0; i<=this.size; i++){
					if(i == 0){
						this.freeCard = this.fields[i][mf.col()-1].getCard();
					}
					else if(i == this.size){
						this.fields[i-1][mf.col()-1].putCard(helpCard);
					}
					else{
						this.fields[i-1][mf.col()-1].putCard(this.fields[i][mf.col()-1].getCard());
					}
				}
			}
		}
		else if(mf.col() == 1){
			if(mf.row()%2 == 0){
				MazeCard helpCard = this.freeCard;
				for(int i = this.size; i>=0; i--){
					if(i == this.size){
						this.freeCard = this.fields[mf.row()-1][i-1].getCard();
					}
					else if(i == 0){
						this.fields[mf.row()-1][i].putCard(helpCard);
					}
					else{
						this.fields[mf.row()-1][i].putCard(this.fields[mf.row()-1][i-1].getCard());
					}
				}
			}
		}
		else if(mf.col() == this.size){
			if(mf.row()%2 == 0){
				MazeCard helpCard = this.freeCard;
				for(int i = 0; i<=this.size; i++){
					if(i == 0){
						this.freeCard = this.fields[mf.row()-1][i].getCard();
					}
					else if(i == this.size){
						this.fields[mf.row()-1][i-1].putCard(helpCard);
					}
					else{
						this.fields[mf.row()-1][i-1].putCard(this.fields[mf.row()-1][i].getCard());
					}
				}
			}
		}
	}
	
	/**
	 * Method creates new game.
	 * It generates MazeCards on fields and create free card.
	 */
	public void newGame(){
		int rand;
		Random rdGen = new Random();
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				rand = rdGen.nextInt(3);
				switch(rand){
				case 0:
					this.fields[i][j].putCard(MazeCard.create("C"));
					break;
				case 1:
					this.fields[i][j].putCard(MazeCard.create("L"));
					break;
				case 2:
					this.fields[i][j].putCard(MazeCard.create("F"));
					break;
				}
			}
		}
		rand = rdGen.nextInt(3);
		if(rand == 0){
			this.freeCard = MazeCard.create("L");
		}
		else if(rand == 1){
			this.freeCard = MazeCard.create("C");
		}
		else if(rand == 2){
			this.freeCard = MazeCard.create("F");
		}
	}
}
