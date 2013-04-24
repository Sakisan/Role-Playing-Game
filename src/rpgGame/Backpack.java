package rpgGame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class representing a backpack that can contain items and that can be 
 * 		held itself by legitimate holders.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Backpack extends Bag implements Holder, Iterable<Item>{
	
	/**
	 * Initializes a new backpack with a given capacity.
	 * 
	 * @param 	capacity
	 * 			The capacity for this new backpack.
	 * @param	bagsWeight
	 * 			The weight for this new backpack.
	 * @post	The capacity for this new backpack is equal to the given number.
	 * @effect	A new bag is initialized with bagsWeight as its weight.
	 */
	Backpack(Weight capacity, Weight bagsWeight){
		super(bagsWeight);
		this.capacity = capacity;
	}
	
	/**
	 * Initializes a new backpack with a given capacity.
	 * 
	 * @param 	capacity
	 * 			The capacity for this new backpack.
	 * @effect	Initializes a new backpack involving its most extended constructor
	 * 			involving capacity as its capacity and a weight of 5 kg
	 * 			as its own weight.
	 */
	Backpack(Weight capacity){
		this(capacity, new Weight(5));
	}
	
	/**
	 * Initializes a new backpack.
	 * 
	 * @effect	Initializes a new backpack involving its most extended constructor
	 * 			involving a weight of 100 kg as its capacity and a weight of 5 kg
	 * 			as its own weight.
	 */
	Backpack(){
		this(new Weight(100), new Weight(5));
	}

	/** 
	 * The result of this method always is false.
	 */
	@Override
	@Immutable
	public boolean isSupremeHolder() {
		return false;
	}

	/* (non-Javadoc)
	 * @see rpgGame.Item#getNextID(long)
	 */
	@Override
	@Basic
	protected long getNextID(long ID) {
		previousID++;
		return MathHelp.getSumOfBinomials(previousID);
	}

	/**
	 * Checks whether a given ID is valid for this item.
	 * 
	 * @param 	ID
	 * 			The ID to be checked.
	 * @return	True if the ID is a sum of Binomials.
	 * 			| result == ID == MathHelp.getSumOfBinomials(i)
	 * 			and the ID is positive.
	 * 			| && ID > 0
	 */
	@Override
	public boolean isValidID(long ID) {
		if(ID <= 0)
			return false;

		for(int i = 1; i <= previousID; i++){
			if(ID == MathHelp.getSumOfBinomials(i))
				return true;
		}
		return false;
	}
	
	/**
	 * A variable registering the last ID that was used to create a new backpack.
	 */
	private static long previousID = 0;
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#hasValidItems()
	 */
	public boolean hasValidItems() {
		Collection<ArrayList<Item>> c = content.values();
		for(ArrayList<Item> items : c){
			if(items.size() > 0)
				for(Item item : items) {
					if(!holdsItem(item) || item.getDirectHolder() != this)
						return false;
					for(int y =0; y < item.getClass().getInterfaces().length; y++ ) {
						if(item.getClass().getInterfaces()[y] == Holder.class) {
							if(!((Holder)item).hasValidItems()){
								return false;
							}
						}
					}
				}
		}
		return true; 
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#holdsItem(rpgGame.Item)
	 */
	public boolean holdsItem(Item item){
		if(item == null)
			return false;
		Iterator<Item> lijst = iterator();
		while(lijst.hasNext()) {
			Item itemAlInBag = lijst.next();
			if(itemAlInBag == item)
				return true;
			for(int y =0; y < itemAlInBag.getClass().getInterfaces().length; y++ ) {
				if(itemAlInBag.getClass().getInterfaces()[y] == Holder.class) {
					if(((Holder)itemAlInBag).holdsItem(item)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether is backpack is a direct holder of any item with a given ID.
	 * 
	 * @param 	ID
	 * 			The ID to look for.
	 * @return	True if there is an item with the given ID which direct holder is this backpack.
	 * 			| result == getItemsByID(ID).size() != 0
	 */
	public boolean hasAnItemWithThisID(long ID){
		return content.containsKey(ID);
	}
	
	/**
	 * Returns an ArrayList of Item with all the items with the same ID that have this backpack
	 * 		as their direct holder.
	 * 
	 * @param 	ID
	 * 			The ID that the items have to match.
	 */
	public ArrayList<Item> getItemsByID(long ID) throws IllegalArgumentException{
		if(ID < 0 )
			throw new IllegalArgumentException("Id must be positive.");
		return content.get(ID);
	}
	

	/* (non-Javadoc)
	 * @see rpgGame.Holder#addItem(rpgGame.Item)
	 */
	@Override
	public void addItem(@Raw Item item) throws IllegalArgumentException, IllegaleToestandsUitzondering{
		if(item.getDirectHolder() != this) 
			throw new IllegalArgumentException("The holder of the item isn't this bag. To move an item use the method of the class Item.");
		if(this.holdsItem(item))
			throw new IllegaleToestandsUitzondering("A bag can't hold the same item 2 times.");
		if(!this.canHoldItem(item))
			throw new IllegalArgumentException("This bag can't hold the given item.");

		long ID = item.getID();
		ArrayList<Item> list;
		if(content.containsKey(ID)){
			list = content.get(ID);
			list.add(item);
		}
		else{
			list = new ArrayList<Item>();
			list.add(item);
			content.put(ID,list);
		}
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#removeItem(rpgGame.Item)
	 */
	@Raw
	@Override
	public void removeItem(@Raw Item item) throws IllegalArgumentException {
		if(item == null)
			throw new IllegalArgumentException();
		if(item.isDropped() || isTerminated() )
			content.get(item.getID()).remove(item);
		else {
			if(item.getDirectHolder() == this)
				throw new IllegalArgumentException();
			if(holdsItem(item))
				content.get(item.getID()).remove(item);
		}
	}

	/**
	 * Checks if an item can be added to the content of this backpack.
	 * 
	 * @param 	item
	 * 			The item to be checked.
	 * @return 	The result is false when the item isn't effective
	 * 			or when the item is terminated.
	 * 			| when item == null || item.isTerminated()
	 * 			|	then result == false
	 * @return	Returns false if the weight of the item plus the 
	 * 			backpack's content weight exceeds the capacity.
	 * 			| when((getCapacity().compareTo(getContentWeight()
	 *			|				.add(item.getWeight()))) < 0
	 *			|	then result == false
	 * @return 	The result is false when the backpack already holds
	 * 			the given item.
	 * 			| when holdsItem(item)
	 * 			|	then result == false
	 * @return 	Otherwise the result is true.
	 * 			| else result == true
	 */
	@Model
	@Override
	public boolean canHoldItem(Item item) {
		if(item == null || item.isTerminated())
			return false;
		if(holdsItem(item))
			return false;
		if(getCapacity().compareTo(item.getWeight().add(getContentWeight())) < 0)
			return false;
		return true;
	}

	/**
	 * Variable registering the references to items that are in this backpack.
	 */
	private HashMap<Long, ArrayList<Item>> content = new HashMap<Long, ArrayList<Item>>();
	
	/* (non-Javadoc)
	 * @see rpgGame.Bag#getWeight()
	 */
	@Override
	public Weight getWeight(){
		Weight weight = new Weight(0);
		weight = weight.add(getBagsWeight());
		weight = weight.add(getDukats().getWeight());
		return weight.add(getContentWeight());
	}
	
	/**
	 * Return the value of the total weight of all the items held by this backpack.
	 */
	private Weight getContentWeight(){
		Weight contentWeight = new Weight(0);
		Iterator<Item> it = iterator();
		while(it.hasNext()){
			contentWeight = contentWeight.add(it.next().getWeight());
		}
		return contentWeight;
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Bag#getPrice()
	 */
	@Override
	public DukatAmount getPrice(){
		DukatAmount price = new DukatAmount(0);
		price.add(super.getPrice().getAmount());
		
		Iterator<Item> it = iterator();
		while(it.hasNext()){
			price.add(it.next().getPrice().getAmount());
		}
		return price;
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Item#setPrice(rpgGame.DukatAmount)
	 */
	@Override
	public void setPrice(DukatAmount price){
		super.setPrice(price);
	}
	
	
	/**
	 * Checks whether a given price can be a potential price of this item.
	 * 
	 * @param 	price
	 * 			The price to be checked.
	 * @return	The result is false when the given price isn't effective.
	 * 			| when price == null
	 * 			|	then result == false
	 * @return	The result is true when the amount of the
	 * 			price is smaller than or equal to 500
	 * 			and larger than zero.
	 * 			| when ( price.getAmount() <= 500 &&  price.getAmount() >= 0.
	 *			|	then result == true
	 */
	@Override
	protected boolean canHaveAsPrice(DukatAmount price){
		if(price == null)
			return false;
		return price.getAmount() <= 500;
	}
	
	public DukatAmount dukatsToFill(){
		int amount = DukatAmount.getEquivalentAmount(getCapacity().difference(getWeight()));
		return new DukatAmount(amount);
	}
	
	/**
	 * Returns the capacity of this backpack.
	 */
	@Immutable
	public Weight getCapacity(){
		return capacity;
	}
	
	/**
	 * Variable referencing the value for the capacity of this backpack.
	 */
	private final Weight capacity;
	
	/**
	 * Add a given amount of dukats to this Bag.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to add.
	 * @pre		amount is a positive number.
	 * @post	If the resulting amount of dukats isn't over its maximum,
	 * 			and if the resulting content doesn't exceed the capacity of this bag
	 * 			then the new amount of dukats contained by this bag
	 * 			is the sum of the previous amount and the given amount. 
	 * 			| if(canAddDukats(amount))
	 * 			| 	getDukats().add(amount)
	 * @throws	IllegaleToestandsUitzondering
	 * 			If the resulting amount of dukats is over its maximum,
	 * 			or if the resulting content exceeds the capacity of this backpack
	 * 			then only what can be added will be added and 
	 * 			an IllegaleToestandsUitzondering will be thrown.
	 * 			| !canAddDukats(amount) 
	 * 			| 	||	getDukats().add(amount) > 0
	 */
	public void addDukats(int amount) throws IllegaleToestandsUitzondering{
		if(!canAddDukats(amount)){
			Weight difference = getContentWeight().difference(getCapacity());
			getDukats().add(DukatAmount.getEquivalentAmount(difference));
			throw new IllegaleToestandsUitzondering();
		}
		else if(getDukats().add(amount) > 0)
			throw new IllegaleToestandsUitzondering();
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Bag#canAddDukats(int)
	 */
	@Override
	public boolean canAddDukats(int amount){
		DukatAmount dukats = new DukatAmount(amount);
		return (getContentWeight().add(dukats.getWeight())).compareTo(capacity) <= 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Item> iterator(){
		return new Iterator<Item>(){
			
			{
				ArrayList<Item> items = new ArrayList<Item>();
				Iterator<Map.Entry<Long, ArrayList<Item>>> contentIterator = content.entrySet().iterator();
				while(contentIterator.hasNext()){
					items.addAll((contentIterator.next()).getValue());
				}
				itemsIterator = items.iterator();
			}
			
			@Override
			public boolean hasNext() {
				return itemsIterator.hasNext();
			}

			@Override
			public Item next() {
				lastItem = itemsIterator.next();
				return lastItem;
			}

			@Override
			public void remove() {
				itemsIterator.remove();
				lastItem.drop();
			}
			
			private Iterator<Item> itemsIterator;
			private Item lastItem;
		};
	}
	
	/**
	 * This backpack disconnects itself from every object
	 * and is ready to be recycled by the java garbage collector.
	 * This backpack also terminates all the items that it holds. 
	 */
	@Override
	public void terminate() {
		setTerminate(true);
		Iterator<Item> iterator = iterator();
		while(iterator().hasNext())
			(iterator.next()).terminate();
		super.terminate();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Backpack other = (Backpack) obj;
		if (capacity == null) {
			if (other.capacity != null) {
				return false;
			}
		} else if (!capacity.equals(other.capacity)) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Backpack [" 
				+ "Number of dukats="+ getDukats() 
				+ super.toString()
				+ "]" 
				+ "[ Content=" + content
				+ "]";
	}
}

