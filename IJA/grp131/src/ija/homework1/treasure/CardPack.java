/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework1 for IJA
 */
package ija.homework1.treasure;
import java.util.Random;
import java.util.LinkedList;


/*
 * Represents pack of TreasureCards
 */
public class CardPack {
	
	private int maxSize;
	private int size;
	// LinkedList that contain Treasure cards in order (later can be shufled)
	private LinkedList<TreasureCard> cardDeck = new LinkedList<TreasureCard>();
	
	/*
	 * Constructor
	 * param	maxSize		- define max size of deck
	 * param	initSize	- define starting size of deck (maxSize >= initSize)
	 */
	public CardPack(int maxSize, int initSize){
		this.maxSize = maxSize;
		this.size = initSize;
		
		Treasure.createSet();
		for(int i = 0; i < initSize; i++){
			cardDeck.add(new TreasureCard(Treasure.getTreasure(i)));
		}
	}
	
	/*
	 * Method for "drawing" card
	 * decrease the size of CardPack by one
	 * return	TreasureCard from the top of CardPack
	 */
	public TreasureCard popCard(){
		this.size -= 1;
		return cardDeck.pollFirst();
	}
	
	/*
	 * Return current size of CardPack
	 */
	public int size(){
		return this.size;
	}
	
	/*
	 * Shufle cards in CardPack
	 */
	public void shuffle(){
		Random rd = new Random();
		int newPosition;
		for(int i = 0; i < size; i++){
			newPosition = rd.nextInt(size);
			TreasureCard card = cardDeck.get(newPosition);
			cardDeck.set(newPosition, cardDeck.get(i));
			cardDeck.set(i, card);
		}
	}
}
