/*
 * @author Michal Klco
 * Email: xklcom00@stud.fit.vutbr.cz
 * Login: xklcom00
 *
 * Homework1 for IJA
 */
package ija.homework1.treasure;

/*
 * Represents card with unique treasure
 */
public class TreasureCard {

	Treasure tr;
	
	/*
	 * Constructor
	 * param	tr	- Treasure, that card represent
	 */
	public TreasureCard(Treasure tr){
		this.tr = tr;
	}

	/*
	 * Overrided hashCode() method
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tr == null) ? 0 : tr.hashCode());
		return result;
	}

	/*
	 * Overrided equals() method
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreasureCard other = (TreasureCard) obj;
		if (tr == null) {
			if (other.tr != null)
				return false;
		} else if (!tr.equals(other.tr))
			return false;
		return true;
	}
	
	
	
}
