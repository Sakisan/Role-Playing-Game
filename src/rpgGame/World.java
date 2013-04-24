package rpgGame;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing the world. 
 * 		Every item that doesn't belong to anyone, belongs to the world.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */

public class World implements Holder {
	
	/**
	 * Variable referencing the only possible instance of World 
	 */
	public final static World world = new World();
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#isSupremeHolder()
	 */
	@Override
	@Immutable
	public boolean isSupremeHolder() {
		return true;
	}

	/**
	 * Checks if the world can hold the given Item.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the world.
	 * @return	The result is false when the item isn't effective.
	 * 			Other wise the result is true.
	 */
	@Override
	public boolean canHoldItem(Item item) {
		if(item == null)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#holdsItem(rpgGame.Item)
	 */
	@Override
	public boolean holdsItem(Item item) {
		if(item == null)
			return false;
		ArrayList<Item> items = getItems();
		for (int i = 0; i < items.size(); i++) {
			Item itemOnI = items.get(i);
			if(item == itemOnI )
				return true;
			if(itemOnI != null) {
				for(int y =0; y < itemOnI.getClass().getInterfaces().length; y++ ) {
					if(itemOnI.getClass().getInterfaces()[y] == Holder.class) {
						if(((Holder)itemOnI).holdsItem(item)){
							return true;
						}
					}
				}
			}

		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#hasValidItems()
	 */
	@Override
	public boolean hasValidItems() {
		for(Item item : getItems()) {
			if(item.getDirectHolder() != World.world)
				return false;
			if(item.getClass()== Backpack.class)
				if(!((Backpack)item).hasValidItems())
					return false;
		}
		return true;
	}
	
	/**
	 * This method returns all the items that are on the ground
	 * 
	 * @return	The result is an array list containing items.
	 * @return 	All the items in the resulting array list will
	 * 			be effective.
	 */
	@Basic
	public ArrayList<Item> getItems() {
		return itemsOnTheGround;
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#setItem(rpgGame.Item)
	 */
	@Override
	public void addItem(@Raw Item item) throws IllegalArgumentException, IllegaleToestandsUitzondering{
		if(!this.canHoldItem(item))
			throw new IllegalArgumentException("This hero can't hold the given item.");
		if(item.getDirectHolder() != this) 
			throw new IllegalArgumentException("The holder of the item isn't this hero. To move an item use the method of the class Item.");
		if(this.holdsItem(item))
			throw new IllegaleToestandsUitzondering("A hero can't wear the same item 2 times.");
		itemsOnTheGround.add(item);
	} 

	/* (non-Javadoc)
	 * @see rpgGame.Holder#removeItem(rpgGame.Item)
	 */
	@Override
	public void removeItem(@Raw Item item) {
		if(item == null || item.getDirectHolder() == World.world)
			throw new IllegalArgumentException();
		itemsOnTheGround.remove(item);
	}
	
	/**
	 * Variable registering all the references to the items that are on the ground.
	 */
	private ArrayList<Item> itemsOnTheGround = new ArrayList<Item>();
	
	/**
	 * The result is true if if the world contains the given 
	 * 	dukat amount.
	 * 
	 * @param 	dukat
	 * 			The dukat amount that will be checked.
	 * @return	The result is true when the given dukat is 
	 * 			effective and when the array resulting from 
	 * 			the method getAllDukats() contains the given
	 * 			dukat amount.
	 */
	public boolean holdsDukats(DukatAmount dukat) {
		return dukat != null && getAllDukats().contains(dukat);
	}
	
	/**
	 * This method returns all the dukat amounts that are on the ground
	 * 
	 * @return	The result is an array list containing dukat amounts.
	 * @return 	All the dukat amounts in the resulting array list
	 * 			will be effective.
	 */
	@Basic
	public ArrayList<DukatAmount> getAllDukats() {
		return dukatsOnTheGround;
	}
	
	/**
	 * This method adds a new DukatAmount to the ground.
	 * 
	 * @param	amount
	 * 			The new DukatAmount that is dropped on the ground.
	 * @post	If the given amount is effective and isn't 
	 *			in the array that is the result of the method
	 *			getAllDukats() then the array
	 * 			list that is returned from the method getAllDukats()
	 * 			will contain the given amount.	
	 */
	public void addDukats(DukatAmount amount){
		if(amount != null && !getAllDukats().contains(amount))
			dukatsOnTheGround.add(amount);
	}
	
	/**
	 * Variable registering all the references to the DukatAmounts that are on the ground.
	 */
	private ArrayList<DukatAmount> dukatsOnTheGround = new ArrayList<DukatAmount>();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "World [Dukats in world=" + getAllDukats() 
				+ ", Items in wolrd="+ getItems() 
				+ "]";
	}
}
