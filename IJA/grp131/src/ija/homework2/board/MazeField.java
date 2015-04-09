/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;

/**
 * Class represent field on board.
 * @author Michal Klco
 *
 */
public class MazeField {
	private final int row;
	private final int col;
	
	MazeCard card;
	
	/**
	 * Constructor of field.
	 * @param row	number of row.
	 * @param col	number of column.
	 */
	public MazeField(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	/**
	 * 
	 * @return number of row.
	 */
	public int row(){
		return this.row;
	}
	
	/**
	 * 
	 * @return number of column.
	 */
	public int col(){
		return this.col;
	}

	/**
	 * 
	 * @return card placed on field.
	 */
	public MazeCard getCard(){
		return this.card;
	}
	
	/**
	 * Puts card on field.
	 * @param c	card that should be placed.
	 */
	public void putCard(MazeCard c){
		this.card = c;
	}
}
