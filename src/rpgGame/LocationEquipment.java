package rpgGame;

/**
 * An enumeration that represents all the locations were the equipment can be stored.
 * 
 * @version 3.0
 * @author Antoine Snyers & Pieter Willemsen
 *
 */
public enum LocationEquipment {
	Back, LeftHand, RightHand, Body, Belt;
	
	/**
	 * Checks if the given item can be linked with the locationEquipment.
	 * 
	 * @param 	item
	 * 			The item that will be test.
	 * @return	The result will always be true except when this object
	 * 			is LocationEquipment.Belt and the item isn't an object
	 * 			of the class purse. (the belt only can carry a purse)
	 * 			| when (this == LocationEquipment.Belt && item.getClass() != Purse.class)
	 * 			|	then result == false
	 * 			| else result == true
	 */
	public boolean canLinkItem(Item item){
		if(this == LocationEquipment.Belt && item.getClass() != Purse.class){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the follow number of this location equipment.
	 * 
	 * @return	The result will be a positive number.
	 * 			| result >= 0
	 * @return	The result will be smaller then the amount 
	 * 			of location equipments.
	 * 			| result <= LocationEquipment.values().length
	 */
	public int getFollowNumber() {
		LocationEquipment[] locations = LocationEquipment.values();
		int i = 0;
		while(i < locations.length){
			if(locations[i] == this)
				return i;
			else 
				i++;
		}
		return -1;
	}
}
