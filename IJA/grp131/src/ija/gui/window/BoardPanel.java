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
	private JButton butFreeCard;	
	private JLabel labFreeCard;
	
	private int butSize = 500/Game.boardSize;		// size of button on board
	
	private Player[] playerArray;
	private JLabel[] playerLabels;
	
	// Treasure cards
	private CardPack cardDeck;
	private JLabel[] tresLabels;
	
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
		butDone.setBounds(600, 380, 130, 35);
		
		butDone.addActionListener(this);
		add(butDone);
		
		// Create new board
		board = MazeBoard.createMazeBoard(Game.boardSize);
		board.newGame();
		
		//Draw Board
		butArray = new JButton[Game.boardSize*Game.boardSize];
		boardLabels = new JLabel[Game.boardSize*Game.boardSize];
		
		MazeField currentField;
		JButton currentButton;
		ImageIcon currentImage;
		JLabel currentLabel;

		for(int x = 0; x < Game.boardSize; x++){
			for(int y = 0; y < Game.boardSize; y++){
				currentField = board.get(x+1, y+1);
						
				currentButton = new JButton("");
				currentImage = currentField.getCard().getImage();
				currentButton.setOpaque(false);
				currentButton.setBounds(origX+(x*butSize), origY+(y*butSize), butSize, butSize);
				currentButton.setActionCommand("onBoard");
				currentButton.addActionListener(this);
				
				currentLabel = new JLabel(new ImageIcon(currentImage.getImage().getScaledInstance( butSize, butSize,
						java.awt.Image.SCALE_SMOOTH)));
				currentLabel.setBounds(origX+(x*butSize), origY+(y*butSize), butSize, butSize);
				
				add(currentLabel);
				add(currentButton);
				
				butArray[x*Game.boardSize + y] = currentButton;
				boardLabels[x*Game.boardSize + y] = currentLabel;
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
		
		// Create card deck and Treasures
		cardDeck = new CardPack(24, Game.cardSize);
		cardDeck.shuffle();
		
		int x,y;
		tresLabels = new JLabel[Game.cardSize];
	
		for(int i = 0; i < Game.cardSize; i++){
			currentLabel = new JLabel(new ImageIcon(Treasure.getTreasure(i).getImg().getImage().getScaledInstance( butSize, butSize,
					java.awt.Image.SCALE_SMOOTH)));
			
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
						while(lab.getBounds().equals(currentLabel.getBounds())){
							x = rd.nextInt(Game.boardSize-2)+1;
							y = rd.nextInt(Game.boardSize-2)+1;
							currentButton = butArray[x*Game.boardSize + y];
							currentLabel.setBounds(currentButton.getBounds());
						}
					}
				}
			}
			// ESTE VYBAV POSUN SPOLU S KAMENMI
			add(currentLabel);
			this.setComponentZOrder(currentLabel, 0);
			tresLabels[i] = currentLabel;
		}
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.BOLD, 20));
		g.drawString("Player "+onTurn+" is on turn!", 580, 50);
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
							redrawBoard();
						}
					} else if(y == 0 || y == Game.boardSize-1){
						if(x % 2 == 1){
							board.shift(board.get(x+1, y+1));
							alreadyUsed = true;
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
							/*for(JLabel lab : this.tresLabels){
								if(y==0){
									if(lab.getLocation().getX() == but.getLocation().getX()){
										if(lab.getLocation().getY() + butSize > origY+(butSize*(Game.boardSize-1))){
											
										}
									}
								}
							}*/
							redrawBoard();
						}
					}
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
						}
					}
				}
			}
					
		}
	}
	
	private void redrawBoard(){

		MazeField currentField;
		JLabel currentLabel;
		ImageIcon currentImage;
		JButton currentButton;
		
		for(int x = 0; x < Game.boardSize; x++){
			for(int y = 0; y < Game.boardSize; y++){
				currentLabel = boardLabels[x*Game.boardSize + y];
				
				currentField = board.get(x+1, y+1);
						
				currentImage = currentField.getCard().getImage();
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
