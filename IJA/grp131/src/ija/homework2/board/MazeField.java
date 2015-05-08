/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;

import ija.homework1.treasure.*;

/**
 * Class represent field on board.
 * @author Michal Klco
 *
 */
public class MazeField {
	private final int row;
	private final int col;
	private Treasure trs;
	
	MazeCard card;
	
	/**
	 * Constructor of field.
	 * @param row	number of row.
	 * @param col	number of column.
	 */
	public MazeField(int row, int col, Treasure trs){
		this.row = row;
		this.col = col;
		this.trs = trs;
	}
	
	
	/**
	 * 
	 * @return treasure of field.
	 */
	public Treasure getTreasure()
	{
		return this.trs;
	}
	
	public void setTreasure(Treasure trs){
		this.trs = trs;
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
