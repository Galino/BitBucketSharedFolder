package ija.player;

import javax.swing.ImageIcon;

import ija.homework1.treasure.TreasureCard;

public class Player {
	
	private TreasureCard treasure;
	private int posX;
	private int posY;
	private int score=0;
	
	private ImageIcon img;
	
	private int id;
	/*
	 * Constructor
	 */
	public Player(int id, int positx, int posity){
		this.id = id;
		
		try{
			if(id == 1){
				this.img = new ImageIcon(getClass().getResource("/images/player1.png"));
				this.posX = positx;
				this.posY = posity;
			}else if(id == 2){
				this.img = new ImageIcon(getClass().getResource("/images/player2.png"));
				this.posX = positx;
				this.posY = posity;
			}else if(id == 3){
				img = new ImageIcon(getClass().getResource("/images/player3.png"));
				this.posX = positx;
				this.posY = posity;
			}else if(id == 4){
				img = new ImageIcon(getClass().getResource("/images/player4.png"));
				this.posX = positx;
				this.posY = posity;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public int getPositionX(){
		return posX;
	}
	
	public int getPositionY(){
		return posY;
	}
	
	public int getId(){
		return id;
	}
	
	public TreasureCard getTreasure(){
		return treasure;
	}
	
	public void setPosition(int positx, int posity){
		posX = positx;
		posY = posity;
	}
	
	public void setTreasure(TreasureCard treas){
		treasure = treas;
	}
	
	public ImageIcon getIcon(){
		return img;
	}
	
	public void scored(){
		this.score++;
	}
	
	public int getScore(){
		return this.score;
	}
}
