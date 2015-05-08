/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.lang.IllegalArgumentException;

import javax.swing.ImageIcon;

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
	
	private ImageIcon img;
	
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
				card.img = new ImageIcon(card.getClass().getResource("/images/tile3.png"));
				break;
			case "L":
				card.firstDir = CANGO.LEFT;
				card.secondDir = CANGO.RIGHT;
				card.thirdDir = null;
				card.img = new ImageIcon(card.getClass().getResource("/images/tile1.png"));
				//card.turnRightImage();
				
				break;
			case "F":
				card.firstDir = CANGO.LEFT;
				card.secondDir = CANGO.UP;
				card.thirdDir = CANGO.RIGHT;
				card.img = new ImageIcon(card.getClass().getResource("/images/tile2.png"));
				//card.turnRightImage();
				//card.turnRightImage();
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
		this.turnRightImage();
	}
	
	private void turnRightImage() {
        int w = this.img.getIconWidth();
        int h = this.img.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(-90), w/2.0, h/2.0);
        g2.drawImage(img.getImage(), at, null);
        g2.dispose();
        this.img = new ImageIcon(image);
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
	
	public ImageIcon getImage(){
		return img;
	}
}
