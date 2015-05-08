package ija.gui.window;

import ija.homework1.treasure.CardPack;
import ija.homework1.treasure.Treasure;
import ija.homework2.board.MazeBoard;
import ija.homework2.board.MazeField;
import ija.player.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Position;

public class BoardPanel extends JPanel implements ActionListener {
	
	private boolean alreadyUsed = false;
	private int onTurn=1;			// ID of player on turn
	private MazeBoard board;		// Board of game
	
	private JButton[] butArray;		// array of buttons on board
	private JLabel[] boardLabels;	// board fields
	private JLabel[] tresLabels;
	private JButton butFreeCard;	
	private JLabel labFreeCard;
	
	private int butSize = 500/Game.boardSize;		// size of button on board
	
	private Player[] playerArray;
	private JLabel[] playerLabels;
	
	// Treasure cards
	private CardPack cardDeck;
	
	private JButton butPlayerCard;
	private JLabel labPlayerCard;
	private boolean cardShown = false;
	
	//Memory for undo button
	private int lastClickedX;
	private int lastClickedY;
	private int lastPlayerPosX;
	private int lastPlayerPosY;
	private boolean ifScored = false;
	
	// Initial position coordinates
	int origX = 50;
	int origY = 50;
	
	
	/*
	 * Constructor
	 */
	public BoardPanel(){
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		// Done button
		JButton butDone = new JButton("Done!");
		butDone.setActionCommand("done");
		butDone.setBounds(600, 390, 130, 35);
		
		butDone.addActionListener(this);
		add(butDone);
		
		//Undone button
		JButton butUndone = new JButton("Undo");
		butUndone.setActionCommand("undo");
		butUndone.setBounds(600, 340, 130, 35);
		
		butUndone.addActionListener(this);
		add(butUndone);
		
		// Create card deck
		cardDeck = new CardPack(24, Game.cardSize);
		cardDeck.shuffle();
		
		// Create new board
		board = MazeBoard.createMazeBoard(Game.boardSize, Game.cardSize);
		board.newGame();
		
		//Draw Board
		butArray = new JButton[Game.boardSize*Game.boardSize];
		boardLabels = new JLabel[Game.boardSize*Game.boardSize];
		tresLabels = new JLabel[Game.boardSize*Game.boardSize];
		
		MazeField currentField;
		JButton currentButton;
		ImageIcon currentImage;
		ImageIcon currentTresImage;
		JLabel currentLabel;
		JLabel currentTresLabel;

		for(int x = 0; x < Game.boardSize; x++){
			for(int y = 0; y < Game.boardSize; y++){
				currentField = board.get(x+1, y+1);
						
				currentButton = new JButton("");
				currentImage = currentField.getCard().getImage();
				
				if(currentField.getCard().getTreasure() == null){
					currentTresLabel = new JLabel("");
				}else{
					currentTresImage = currentField.getCard().getTreasure().getImg();
					currentTresLabel = new JLabel(currentTresImage);
				}
				
				currentButton.setOpaque(false);
				currentButton.setBounds(origX+(x*butSize), origY+(y*butSize), butSize, butSize);
				currentButton.setActionCommand("onBoard");
				currentButton.addActionListener(this);
				
				currentLabel = new JLabel(new ImageIcon(currentImage.getImage().getScaledInstance( butSize, butSize,
						java.awt.Image.SCALE_SMOOTH)));
				currentLabel.setBounds(origX+(x*butSize), origY+(y*butSize), butSize, butSize);
				
				currentTresLabel.setBounds(origX+(x*butSize), origY+(y*butSize), butSize, butSize);
				
				add(currentLabel);
				add(currentTresLabel);
				add(currentButton);
				
				this.setComponentZOrder(currentTresLabel, 1);
				
				butArray[x*Game.boardSize + y] = currentButton;
				boardLabels[x*Game.boardSize + y] = currentLabel;
				this.tresLabels[x*Game.boardSize + y] = currentTresLabel;
			}
		}
			
		//Draw Free Card
		butFreeCard = new JButton();
		currentImage = board.getFreeCard().getImage();
		labFreeCard = new JLabel(new ImageIcon(currentImage.getImage().getScaledInstance( butSize, butSize,
				java.awt.Image.SCALE_SMOOTH)));
		
		butFreeCard.setOpaque(false);
		butFreeCard.setBounds(570, 450, butSize, butSize);
		butFreeCard.setActionCommand("free");
		butFreeCard.addActionListener(this);
		
		
		labFreeCard.setBounds(570, 450, butSize, butSize);
		
		add(labFreeCard);
		add(butFreeCard);
		
		
		
		// Create Players
		Player currentPlayer;
		
		playerArray = new Player[Game.playersCount];
		playerLabels = new JLabel[Game.playersCount];
		int[][] startPositions = {{1, Game.boardSize}, {Game.boardSize, 1}, {1, 1}, {Game.boardSize, Game.boardSize}};
		
		for(int i = 1; i <=Game.playersCount; i++){
			currentPlayer = new Player(i, startPositions[i-1][0], startPositions[i-1][1]);
			playerArray[i-1] = currentPlayer;
			currentLabel = new JLabel(new ImageIcon(currentPlayer.getIcon().getImage().getScaledInstance( butSize, butSize,
					java.awt.Image.SCALE_SMOOTH)));
			
			//get Board button for location of player
			currentButton = butArray[(currentPlayer.getPositionX()-1) * Game.boardSize + (currentPlayer.getPositionY()-1)];
			currentLabel.setBounds(currentButton.getBounds());

			add(currentLabel);
			this.setComponentZOrder(currentLabel, 0);
			
			if(i == 1){
				currentLabel.setLocation(currentLabel.getLocation().x +10, currentLabel.getLocation().y);
			} else if(i == 2){
				currentLabel.setLocation(currentLabel.getLocation().x -10, currentLabel.getLocation().y);
			}else if(i == 3){
				currentLabel.setLocation(currentLabel.getLocation().x , currentLabel.getLocation().y-10);
			}else if(i == 3){
				currentLabel.setLocation(currentLabel.getLocation().x , currentLabel.getLocation().y+10);
			}
			playerLabels[i-1] = currentLabel;
		}
		
		
		// Give treasure card to each player
		for(Player pl : this.playerArray){
			pl.setTreasure(cardDeck.popCard());
		}
		
		/*int x,y;
		tresLabels = new JLabel[Game.cardSize];
	
		for(int i = 0; i < Game.cardSize; i++){
			currentLabel = new JLabel(Treasure.getTreasure(i).getImg());
			
			Random rd = new Random();
			// generate numbers from 1 to Game.boardSize-1
			// because we dont wanna any treasure on start fields
			x = rd.nextInt(Game.boardSize-2)+1;
			y = rd.nextInt(Game.boardSize-2)+1;
			
			currentButton = butArray[x*Game.boardSize + y];
			currentLabel.setBounds(currentButton.getBounds());
			
			for(JLabel lab : tresLabels){
				if(lab != null){
					if(! lab.equals(currentLabel)){
						while((int) lab.getLocation().getX() == (int) currentLabel.getLocation().getX() &&
								(int) lab.getLocation().getY() == (int) currentLabel.getLocation().getY()){
							x = rd.nextInt(Game.boardSize-2)+1;
							y = rd.nextInt(Game.boardSize-2)+1;
							currentButton = butArray[x*Game.boardSize + y];
							currentLabel.setBounds(currentButton.getBounds());
							
						}
					}
				}
			}
			add(currentLabel);
			this.setComponentZOrder(currentLabel, 0);
			tresLabels[i] = currentLabel;
		}*/
		
		// Create player's card appearance
		currentPlayer = this.playerArray[onTurn-1];
		butPlayerCard = new JButton("");
		butPlayerCard.setOpaque(false);
		butPlayerCard.setActionCommand("cardShow");
		butPlayerCard.setBounds(600, 120, 130, 200);
		
		labPlayerCard = new JLabel("", JLabel.CENTER);
		labPlayerCard.setBounds(600, 120, 130, 200);
		
		butPlayerCard.addActionListener(this);
		add(butPlayerCard);
		add(labPlayerCard);
		this.setComponentZOrder(labPlayerCard, 0);
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.BOLD, 20));
		g.drawString("Player "+onTurn+" is on turn!", 580, 50);
		g.setFont(new Font("Century Gothic", Font.BOLD, 16));
		g.drawString("Click to show/hide card.", 580, 110);
		g.drawString("Player "+onTurn+" score: "+this.playerArray[onTurn-1].getScore()+" / "+Game.cardSize/Game.playersCount, 605, 75);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("free".equals(e.getActionCommand())) {
			// Rotate Free card
			board.getFreeCard().turnRight();
			redrawBoard();
			
		}else  if ("done".equals(e.getActionCommand())) {
			// Rotate Free card
			if(onTurn < Game.playersCount){
				onTurn++;
			}else{
				onTurn = 1;
			}
			ifScored = false;
			alreadyUsed = false;
			this.repaint();
		}else if ("onBoard".equals(e.getActionCommand())) {
			Object button = e.getSource();
			JButton but = (JButton) button;
			int index = Arrays.asList(butArray).indexOf(button);
			if(!alreadyUsed){	
				//Place free card on chosen fied if can
				if(index != -1){
					int x = index / Game.boardSize;
					int y = index % Game.boardSize;
					if(x == 0 || x == Game.boardSize-1){
						if(y % 2 == 1){
							board.shift(board.get(x+1, y+1));
							alreadyUsed = true;
							lastClickedX = x+1;
							lastClickedY = y+1;
							this.lastPlayerPosX = this.playerArray[onTurn-1].getPositionX();
							this.lastPlayerPosY = this.playerArray[onTurn-1].getPositionY();
							for(Player pl : this.playerArray){
								if(y+1 == pl.getPositionY()){
									if(x+1 == 1){
										if(pl.getPositionX()+1 <= Game.boardSize){
											pl.setPosition(pl.getPositionX()+1, pl.getPositionY());
										}
										else{
											pl.setPosition(1, Game.boardSize);
										}
									} else if(x+1 == Game.boardSize){
										if(pl.getPositionX()-1 >= 1){
											pl.setPosition(pl.getPositionX()-1, pl.getPositionY());
										}
										else{
											pl.setPosition(Game.boardSize,pl.getPositionY());
										}
									}
								}
							}
						}
					} else if(y == 0 || y == Game.boardSize-1){
						if(x % 2 == 1){
							board.shift(board.get(x+1, y+1));
							alreadyUsed = true;
							lastClickedX = x+1;
							lastClickedY = y+1;
							this.lastPlayerPosX = this.playerArray[onTurn-1].getPositionX();
							this.lastPlayerPosY = this.playerArray[onTurn-1].getPositionY();
							for(Player pl : this.playerArray){
								if(x+1 == pl.getPositionX()){
									if(y+1 == 1){
										if(pl.getPositionY()+1 <= Game.boardSize){
											pl.setPosition(pl.getPositionX(), pl.getPositionY()+1);
										}
										else{
											pl.setPosition(pl.getPositionX(), 1);
										}
									} else if(y+1 == Game.boardSize){
										if(pl.getPositionY()-1 >= 1){
											pl.setPosition(pl.getPositionX(), pl.getPositionY()-1);
										}
										else{
											pl.setPosition(pl.getPositionX(),Game.boardSize);
										}
									}
								}
							}
						}
					}
					redrawBoard();
				}
				
			}else{
				// Try to move with player
				for(Player pl : playerArray){
					if(pl.getId() == onTurn){
						MazeField src = board.get(pl.getPositionX(), pl.getPositionY());
						int x = index / Game.boardSize;
						int y = index % Game.boardSize;
						MazeField dest = board.get(x+1, y+1);
						if(board.path(src, dest)){
							JLabel lab = playerLabels[pl.getId()-1];
							lab.setLocation(but.getLocation());
							pl.setPosition(x+1, y+1);
							
							if(dest.getCard().getTreasure() != null){
								if(dest.getCard().getTreasure().equals(pl.getTreasure().getTreasure())){
									pl.scored();
									ifScored = true;
									dest.getCard().setTreasure(null);
									if(pl.getScore() == Game.cardSize/Game.playersCount){
										// PLAYER WIN
									}
								}
							}
						}
					}
					redrawBoard();
				}
			}
					
		}else if ("cardShow".equals(e.getActionCommand())) {
			if(cardShown){
				labPlayerCard.setIcon(null);
				cardShown = false;
			}else {
				labPlayerCard.setIcon(this.playerArray[onTurn-1].getTreasure().getTreasure().getImg());
				cardShown = true;
			}
			this.repaint();
		}else if ("undo".equals(e.getActionCommand())) {
			if(!ifScored && alreadyUsed){
				alreadyUsed = false;
				if(this.lastClickedX == 1){
					board.shift(board.get(Game.boardSize, this.lastClickedY));
				} else if(this.lastClickedX == Game.boardSize){
					board.shift(board.get(1, this.lastClickedY));
				} else if(this.lastClickedY == 1){
					board.shift(board.get(this.lastClickedX, Game.boardSize));
				} else if(this.lastClickedY == Game.boardSize){
					board.shift(board.get(this.lastClickedX, 1));
				}
				this.playerArray[onTurn-1].setPosition(this.lastPlayerPosX, this.lastPlayerPosY);
			}
			redrawBoard();
		}
	}
	
	private void redrawBoard(){

		MazeField currentField;
		JLabel currentLabel;
		JLabel currentTresLabel;
		ImageIcon currentImage;
		ImageIcon currentTresImage;
		JButton currentButton;
		
		for(int x = 0; x < Game.boardSize; x++){
			for(int y = 0; y < Game.boardSize; y++){
				currentLabel = boardLabels[x*Game.boardSize + y];
				currentTresLabel = this.tresLabels[x*Game.boardSize + y];
				
				currentField = board.get(x+1, y+1);
						
				currentImage = currentField.getCard().getImage();
				
				if(currentField.getCard().getTreasure() == null){
					currentTresLabel.setIcon(null);
				}else{
					currentTresImage = currentField.getCard().getTreasure().getImg();
					currentTresLabel.setIcon(currentTresImage);
				}
				
				currentLabel.setIcon(new ImageIcon(currentImage.getImage().getScaledInstance( butSize, butSize,
																				java.awt.Image.SCALE_SMOOTH)));
				
				for(Player pl : this.playerArray){
					if(pl.getPositionX() == x+1 && pl.getPositionY() == y+1){
						currentButton = butArray[x*Game.boardSize + y];
						this.playerLabels[pl.getId()-1].setLocation(currentButton.getLocation());
					}
				}
			}
		}
				
		currentImage = board.getFreeCard().getImage();
		labFreeCard.setIcon(new ImageIcon(currentImage.getImage().getScaledInstance( butSize, butSize,
																		java.awt.Image.SCALE_SMOOTH)));
		this.repaint();
	}
}
