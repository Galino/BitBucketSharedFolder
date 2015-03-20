/*
 * @author Michal Klco
 * Email: xklcom00@stud.fit.vutbr.cz
 * Login: xklcom00
 *
 * Homework1 for IJA
 */

package ija.homework1.treasure;

/*
 * Represents treasure on card (various items)
 */
public class Treasure {
	
	private static Treasure[] treasureSet;
	private static int treasureCount = 24;
	
	private int treasureCode;
	
	/*
	 * Constructor
	 * param	code	- define code of treasure (what item)
	 */
	private Treasure(int code){
		// Constructor
		this.treasureCode = code;
	}
	
	/*
	 * Create set (array) of treasures
	 */
	public static void createSet(){
		treasureSet = new Treasure[treasureCount];
		
		for(int i = 0; i < treasureCount; i++){
			treasureSet[i] = new Treasure(i);
		}
	}
	
	/*
	 * Return treasure from set of treasures
	 * param	code 	- define treasure
	 * return	treasure with code value, otherwise null
	 */
	public static Treasure getTreasure(int code){
		if(treasureSet != null){
			if(code < treasureCount && code >= 0){
				return treasureSet[code];
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	/*
	 * Return code of treasure (int)
	 */
	public int getTreasureCode(){
		return this.treasureCode;
	}
}
