/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework2 for IJA
 */

package ija.homework2.board;


//import ija.homework1.treasure.TreasureCard;
import java.util.*;
import ija.homework1.treasure.*;

/**
 * Class represents game board with fields.
 * @author Michal Klco
 */
public class MazeBoard {

	private int size;
	private MazeField[][] fields;
	private MazeCard freeCard;
	private int cardSize;
	
	
	/**
	 * Static method for creating game board.
	 * @param 	n	size of board (it will contain n*n fields).
	 * @return	board.
	 */
	public static MazeBoard createMazeBoard(int n, int cardSize){
		MazeBoard board = new MazeBoard();
		board.cardSize = cardSize;
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
	public void newGame()
	{
		int rand;
		Random rdGen = new Random();
		int pack = this.cardSize-1;
		LinkedList<MazeCard> allOthers = new LinkedList<MazeCard>();
		int Ccard =  (this.size*this.size)/3;
		int Fcard = (this.size*this.size)/3;
		
		for(int i = 0; i < this.size; i++)
		{
			for(int j = 0; j < this.size; j++)
			{
				Random pom = new Random();
				int putTreasure = pom.nextInt(2);
				// Putting "C" cards on corners of board
				if ( i == 0 && j == 0)
				{
					MazeCard card = MazeCard.create("C",null);
					card.turnRight();
					card.turnRight();
					this.fields[i][j].putCard(card);
					Ccard--;
					
				}
				else if (i == 0 && j == this.size-1)
				{
					MazeCard card = MazeCard.create("C",null);
					card.turnRight();
					card.turnRight();
					card.turnRight();
					this.fields[i][j].putCard(card);
					Ccard--;
				}
				else if (i == this.size-1 && j == 0)
				{
					MazeCard card = MazeCard.create("C",null);
					card.turnRight();
					this.fields[i][j].putCard(card);
					Ccard--;
				}
				else if (i == this.size-1 && j == this.size-1)
				{
					MazeCard card = MazeCard.create("C",null);
					// no need to rotate
					this.fields[i][j].putCard(card);
					Ccard--;
				}
				
				
				
				// Putting "F" cards on their positions
				else if (i == 0 && j%2 == 0)			//up edge
				{
					if (putTreasure == 1)
					{
						Treasure trs = Treasure.getTreasure(pack);
						MazeCard card = MazeCard.create("F",trs);
						pack--;
						card.turnRight();
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					else
					{
						MazeCard card = MazeCard.create("F",null);
						card.turnRight();
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					Fcard--;
				}				
				else if (i%2 == 0 && j == 0)			//left edge
				{
					if (putTreasure == 1)
					{
						Treasure trs = Treasure.getTreasure(pack);
						MazeCard card = MazeCard.create("F",trs);
						pack--;
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					else
					{
						MazeCard card = MazeCard.create("F",null);
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					Fcard--;
				}
				else if (i == this.size-1 && j%2 == 0)		//down edge
				{
					if (putTreasure == 1)
					{
						Treasure trs = Treasure.getTreasure(pack);
						MazeCard card = MazeCard.create("F",trs);
						pack--;
						// no need to rotate
						this.fields[i][j].putCard(card);
					}
					else
					{
						MazeCard card = MazeCard.create("F",null);
						//no need to rotate
						this.fields[i][j].putCard(card);
					}
					Fcard--;
				}
				else if (i%2 == 0 && j == this.size-1)		//right edge
				{
					if (putTreasure == 1)
					{
						Treasure trs = Treasure.getTreasure(pack);
						MazeCard card = MazeCard.create("F",trs);
						pack--;
						card.turnRight();
						card.turnRight();
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					else
					{
						MazeCard card = MazeCard.create("F",null);
						card.turnRight();
						card.turnRight();
						card.turnRight();
						this.fields[i][j].putCard(card);
					}
					Fcard--;
				}
				else if (i%2 == 0 && j%2 == 0)			// inwards (yeah, inward !)
				{
					if (putTreasure == 1)
					{
						Treasure trs = Treasure.getTreasure(pack);
						MazeCard card = MazeCard.create("F",trs);
						pack--;
						rand = rdGen.nextInt(4);
						for (int k = 0; k < rand; k++)
							card.turnRight();
						this.fields[i][j].putCard(card);
						Fcard--;
					}
					else
					{
						MazeCard card = MazeCard.create("F",null);
						rand = rdGen.nextInt(4);
						for (int k = 0; k < rand; k++)
							card.turnRight();
						this.fields[i][j].putCard(card);
						Fcard--;
					}
					
				}
				else // create linked list of all others cards
				{
					if (pack >= 0)
					{
						if (Ccard > 0)
						{
							Treasure trs = Treasure.getTreasure(pack);
							MazeCard card = MazeCard.create("C",trs);
							allOthers.add(card);
							Ccard--;
							pack--;
						}
						else if (Fcard > 0)
						{
							Treasure trs = Treasure.getTreasure(pack);
							MazeCard card = MazeCard.create("F",trs);
							allOthers.add(card);
							Fcard--;
							pack--;
						}
						else
						{
							Treasure trs = Treasure.getTreasure(pack);
							MazeCard card = MazeCard.create("L",trs);
							allOthers.add(card);
							pack--;
						}
					}
					else
					{
						if (Ccard > 0)
						{
							MazeCard card = MazeCard.create("C",null);
							allOthers.add(card);
							Ccard--;
						}
						else if (Fcard > 0)
						{
							MazeCard card = MazeCard.create("F",null);
							allOthers.add(card);
							Fcard--;
						}
						else
						{
							MazeCard card = MazeCard.create("L",null);
							allOthers.add(card);
						}
					}
				}				
			} // end of for(j)
		} // end of for(i)
		
		//---------------------------------------------------------------
		
		// shuffle list of all others cards (copied from CardPack)
		Random rd = new Random();
		int newPosition;
		for(int x = 0; x < this.size; x++)
		{
			newPosition = rd.nextInt(this.size);
			MazeCard card = allOthers.get(newPosition);
			allOthers.set(newPosition, allOthers.get(x));
			allOthers.set(x, card);
		}
		
		// putting all others cards from shuffled list
		for(int i = 0; i < this.size; i++)
		{
			for(int j = 0; j < this.size; j++)
			{
				if (j%2 == 1)
				{
					MazeCard card = allOthers.pollFirst();
					
					rand = rdGen.nextInt(4);	//rotate card random times
					for (int k = 0; k < rand; k++)
						card.turnRight();
					
					this.fields[i][j].putCard(card);
				}
				else if (i%2 == 1 && j%2 == 0)
				{
						MazeCard card = allOthers.pollFirst();
					
						rand = rdGen.nextInt(4);	//rotate card random times
						for (int k = 0; k < rand; k++)
							card.turnRight();
						
						this.fields[i][j].putCard(card);
				}
			} // end of for(j)
		} // end of for(i)
		
		
		//generate freeCard
		rand = rdGen.nextInt(3);
		if(rand == 0){
			this.freeCard = MazeCard.create("L",null);
		}
		else if(rand == 1){
			this.freeCard = MazeCard.create("C",null);
		}
		else if(rand == 2){
			this.freeCard = MazeCard.create("F",null);
		}		
	}

	public boolean path(MazeField src, MazeField dst)
	{
		int x = src.row();
		int y = src.col();
		
		int x_dst = dst.row();
		int y_dst = dst.col();
		
		Stack<MazeField> closed = new Stack<MazeField>();
		Stack<MazeField> done = new Stack<MazeField>();
		
		//System.out.println("Dest coord:\n\tx_dst = " + x_dst + "\n\ty_dst = " + y_dst + "\n");
		while (true)
		{
			//System.out.print("\n=======================================================\n");
			//System.out.print("My coord:\n\tx = " + x + "\n\ty = " + y + "\n");
			if (this.get(x,y) == this.get(x_dst,y_dst))
				return true;
			
			//System.out.print("Look of actual MazeCard:\n\t");
			//MazeCard mc = this.get(x,y).getCard();
			//String card;
		/*	card = "|";
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
			System.out.print(card+"\n");
			*/
			if (this.get(x,y).getCard().canGo(MazeCard.CANGO.LEFT))
			{
			//	System.out.print("I can go left\n");
				if (y-1 > 0)
				{
					if (this.get(x,y-1).getCard().canGo(MazeCard.CANGO.RIGHT))
					{
						//System.out.print("Then come to my right\n");
						
						if (closed.search(this.get(x,y-1)) < 0)
						{
							if(done.search(this.get(x,y-1)) < 0)
							{
							//	System.out.println("GO LEFT");
								closed.push(this.get(x,y));
								y--;
								continue;
							}
						}
						
					}
				}
			}
			if (this.get(x,y).getCard().canGo(MazeCard.CANGO.UP))
			{
			//	System.out.print("I can go up\n");
				if (x-1 > 0)
				{
					if (this.get(x-1,y).getCard().canGo(MazeCard.CANGO.DOWN))
					{
					//	System.out.print("Then come to my down\n");
						if (closed.search(this.get(x-1,y)) < 0)
						{
							if(done.search(this.get(x-1,y)) < 0)
							{
						//		System.out.println("GO UP");
								closed.push(this.get(x,y));
								x--;
								continue;
							}
						}
						
					}
				}
			}
			if (this.get(x,y).getCard().canGo(MazeCard.CANGO.RIGHT))
			{
			//	System.out.print("I can go right\n");
			//	System.out.println("1");
				if (y+1 <= this.size)
				{
					//System.out.println("2");
					if (this.get(x,y+1).getCard().canGo(MazeCard.CANGO.LEFT))
					{
					//	System.out.print("Then come to my left\n");
					//	System.out.println("3");
						if (closed.search(this.get(x,y+1)) < 0)
						{
						//	System.out.println("4");
							if(done.search(this.get(x,y+1)) < 0)
							{
							//	System.out.println("5");
								//System.out.println("GO RIGHT");
								closed.push(this.get(x,y));
								y++;
								continue;
							}
						}
						
					}
				}
			}
			if (this.get(x,y).getCard().canGo(MazeCard.CANGO.DOWN))
			{
			//	System.out.print("I can go down\n");
				if (x+1 <= this.size)
				{
					if (this.get(x+1,y).getCard().canGo(MazeCard.CANGO.UP))
					{
					//	System.out.print("Then come to my up\n");
					//	System.out.println("I can go up !");
						if (closed.search(this.get(x+1,y)) < 0)
						{
							if(done.search(this.get(x+1,y)) < 0)
								{
								//	System.out.println("We love each other !!!");
									//System.out.println("GO DOWN");
									closed.push(this.get(x,y));
									x++;
									continue;
								}
						}
						
					}
				}
			}
			
			
			done.push(this.get(x,y));	 // push it to done ("I was there and couldn't go any further..")
			if (!closed.empty()) 			//("...so if I can ,...")
			{	
				x = closed.peek().row();
				y = closed.peek().col();
			//	System.out.print("GO BACK on\n\tx = "+ x + "\n\ty = " + y + "\n");
				closed.pop();				// pop it from closed ("..I make  a step back ")
				continue;
			}
			
			
			
			if (x == src.row() && y == src.col())
				return false;
			
			//System.out.print("HAVE TO BREAK IT !!!\n");
			break;
		}
		return false;
	}
}
