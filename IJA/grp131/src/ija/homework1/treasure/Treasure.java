/*
 * @author Michal Klco, Lukas Galbicka
 * Email: xklcom00@stud.fit.vutbr.cz xgalbi01@stud.fit.vutbr.cz
 * Login: xklcom00,  xgalbi01
 *
 * Homework1 for IJA
 */

package ija.homework1.treasure;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

/*
 * Represents treasure on card (various items)
 */
public class Treasure {
	
	private static Treasure[] treasureSet;
	private static int treasureCount = 24;
	
	private ImageIcon img;
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
		
		Treasure currentTreasure;
		int i = 0;
		
		File imgDir = new File("./images/objects/");
		
		for(File fileImg : imgDir.listFiles()){
			if(i >= treasureCount){
				break;
			}
			currentTreasure = new Treasure(i);
			
			String name = fileImg.getPath();
			name = name.replace(".\\","\\");
			name = name.replace("\\", "/");
			
			currentTreasure.setImg(new ImageIcon(currentTreasure.getClass().getResource(name)));
			treasureSet[i] = currentTreasure;
			i++;
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
	
	public void setImg(ImageIcon img){
		this.img = img;
	}
	
	public ImageIcon getImg(){
		return this.img;
	}
}
