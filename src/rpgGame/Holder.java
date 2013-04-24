
package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * Interface representing any class capable of holding items.
 * 
 * @invar	The hero's equipment must be valid.
 * 			| hasValidItems()
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public interface Holder {

	/**
	 * Indicates whether this holder can be held by an other holder.
	 * 		For instance, heroes are supreme holders, because nothing can hold them.
	 * 		Backpacks are commonly held by heroes and thus are not supreme, unless
	 * 		they haven't been picked up yet.
	 */
	@Immutable
	@Basic
	@Raw
	public abstract boolean isSupremeHolder();
	
	/**
	 * Checks whether the given item is being held by this holder.
	 * 
	 * @param 	item
	 * 			The item to look for.
	 * @return	Returns false when the given item is null
	 * 			| when(item == null)
	 * 			|	then result == false
	 * @return 	Returns true if this holder is a direct or 
	 * 			indirect holder of the given item.
	 * 			| when(item.getDirectHolder().getDirectHolder() ... .getHolder() == this)
	 * 			|	then result == true;
	 */
	public abstract boolean holdsItem(Item item);
	
	/**
	 * Disconnects the bond between the Holder and a given item.
	 * 
	 * @param 	item
	 * 			The item that needs to be disconnected.
	 * @post	The holder doesn't hold this item anymore.
	 * 			| new.holdsItem(item) == false
	 * @throws	IllegalArgumentException
	 * 			The item must be effective
	 * 			| item == null
	 * @throws  IllegalArgumentException
	 * 			If the items still thinks this holder is the 
	 * 			direct holder. 
	 * 			| item.getDirectHolder() == this
	 */
	public abstract void removeItem(@Raw Item item) throws IllegalArgumentException;
	
	/**
	 * Makes this holder hold a given item.
	 * 
	 * @param 	item
	 * 			The item to be add to the collection
	 * 			 of items of the holder.
	 * @post	This holder holds the given item.
	 * 			| (new this).holdsItem(item)
	 * @throws	IllegalArgumentException
	 * 			When the direct holder of the item isn't this
	 * 			holder
	 * 			| item.getDirectHolder() != this
	 * @throws	IllegaleToestandsUitzondering
	 * 			When this holder already holds the given item.
	 * 			| this.holdsItem(item)
	 * @throws 	IllegalArgumentException
	 * 			When the holder can't hold the given item
	 * 			| !this.canHoldItem(item)
	 */	
	public abstract void addItem(@Raw Item item) throws IllegalArgumentException, IllegaleToestandsUitzondering ;
	
	/**
	 * Checks if all the items the holder holds are valid
	 * 
	 * @return	True if every items held by this holder also sees this holder as their holder.
	 * 			| when for all items current holding. 
	 * 			|	item.getDirectHolder() == this and 
	 * 			| 			holdsItem(item) == true
	 * 			|	then result == true
	 */
	public abstract boolean hasValidItems();
	
	/**
	 * Checks if this holder can hold the given Item.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the holder.
	 * @return	Returns true if the holder can hold the given item.
	 * 			Returns false if the holder can't hold the given item.
	 */
	public boolean canHoldItem(Item item);	
}
