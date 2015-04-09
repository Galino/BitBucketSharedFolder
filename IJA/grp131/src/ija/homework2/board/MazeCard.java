/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;

import java.lang.IllegalArgumentException;

/**
 * Class represents cards placed on fields on board.
 * @author Michal Klco
 *
 */
public class MazeCard {
	
	private String type;
	CANGO firstDir;
	CANGO secondDir;
	CANGO thirdDir;
	
	public static enum CANGO{
		LEFT, UP, RIGHT, DOWN;
	}
	
	/**
	 * Static method for creating cards.
	 * @param type card type (C, L or F).
	 * @return	created card.
	 * @throws IllegalArgumentException
	 */
	public static MazeCard create(String type) throws IllegalArgumentException{
		if(type != "C" && type != "L" && type != "F")
			throw new IllegalArgumentException("Argmument "+type+" is not valid");
		MazeCard card = new MazeCard();
		card.type = type;
		switch(type){
			case "C":
				card.firstDir = CANGO.UP;
				card.secondDir = CANGO.LEFT;
				card.thirdDir = null;
				break;
			case "L":
				card.firstDir = CANGO.LEFT;
				card.secondDir = CANGO.RIGHT;
				card.thirdDir = null;
				break;
			case "F":
				card.firstDir = CANGO.LEFT;
				card.secondDir = CANGO.UP;
				card.thirdDir = CANGO.RIGHT;
				break;
		}
		
		return card;
	}
	
	/**
	 * Method turns card to right by 90 degree.
	 */
	public void turnRight(){
		switch(this.type){
			case "C":
				if (this.firstDir == CANGO.UP){
					if(this.secondDir == CANGO.LEFT){
						this.secondDir = CANGO.RIGHT;
					}
					else if(this.secondDir == CANGO.RIGHT){
						this.firstDir = CANGO.DOWN;
					}
				}
				else if(this.firstDir == CANGO.DOWN){
					if(this.secondDir == CANGO.RIGHT){
						this.secondDir = CANGO.LEFT;
					}
					else if(this.secondDir == CANGO.LEFT){
						this.firstDir = CANGO.UP;
					}
				}
				break;
			case "L":
				if(this.firstDir == CANGO.LEFT){
					this.firstDir = CANGO.UP;
					this.secondDir = CANGO.DOWN;
				}
				else if(this.firstDir == CANGO.UP){
					this.firstDir = CANGO.LEFT;
					this.secondDir = CANGO.RIGHT;
				}
				break;
			case "F":
				if(this.firstDir == CANGO.LEFT){
					this.firstDir = CANGO.UP;
					this.secondDir = CANGO.RIGHT;
					this.thirdDir = CANGO.DOWN;
				}
				else if(this.firstDir == CANGO.UP){
					this.firstDir = CANGO.RIGHT;
					this.secondDir = CANGO.DOWN;
					this.thirdDir = CANGO.LEFT;
				}
				else if(this.firstDir == CANGO.RIGHT){
					this.firstDir = CANGO.DOWN;
					this.secondDir = CANGO.LEFT;
					this.thirdDir = CANGO.UP;
				}
				else if(this.firstDir == CANGO.DOWN){
					this.firstDir = CANGO.LEFT;
					this.secondDir = CANGO.UP;
					this.thirdDir = CANGO.RIGHT;
				}
				break;
		}
	}
	
	/**
	 * Method inspects if card can be leaved in chosen direction.
	 * @param dir	chosen direction.
	 * @return	true if card can be leaved in chosen direction, otherwise false.
	 */
	public boolean canGo(MazeCard.CANGO dir){
		if(dir == this.firstDir || dir == this.secondDir || dir == this.thirdDir)
			return true;
		else
			return false;
	}
}
