package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * An abstract class representing every item in the game.
 * An item is by definition something that can be held by a hero.
 * 
 * @invar	The holder must be valid.
 * 			| isValidHolder()
 * @invar	The ID must be valid
 * 			| isValid(getID())
 * @invar	The weight must be valid
 * 			| isValidWeight(getWeight())
 * @invar	The price must be valid
 * 			| canHaveAsPrice(getPrice())
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public abstract class Item {
	
	/**
	 * Initializes a new item with given ID, weight and price.
	 * 
	 * @param 	ID
	 * 			The number that will be used to calculate the ID of this new item.
	 * @param	weight
	 * 			The weight for this new item.
	 * @param	price
	 * 			The value in amount of dukats for this item.
	 * @post	This new item has the next available ID after the given ID.
	 * 			| isValidID(new getID())
	 * @post	This new item's direct holder is the world.
	 * 			| new getDirectHolder() ==  World.world
	 * @post	If the given weight is valid, this new item's weight is the given weight
	 * 			| if(isValidWeight(weight))
	 * 			|	new getWeight().equals(weight)
	 * 			Else this new item's weight is 5 kg.
	 * 			| else new getWeight().equals(new Weight(5))
	 * @post	This new item's price is equal to the given price.
	 * 			| this.getPrice().equals(price)
	 */
	Item(long ID, Weight weight,  DukatAmount price){
		this.ID = getNextID(ID);
		this.setHolder(World.world);
		World.world.addItem(this);
		if(isValidWeight(weight))
			this.weight = weight;
		else this.weight = new Weight(5);
		setPrice(price);
	}
	
	/**
	 * Initializes a new item with given ID and price.
	 * 
	 * @param 	ID
	 * 			The number that will be used to calculate the ID of this new item.
	 * @param	price
	 * 			The value in amount of dukats for this new item.
	 * @effect	Initializes a new item the same way it would initialize a new item using
	 * 			its most extended constructor involving ID as its ID , a weight of 5 kg
	 * 			as its weight and price as its price.
	 */
	Item(long ID, DukatAmount price){
		this(ID, new Weight(5), price);
	}
	
	/**
	 * Initializes a new item with given price.
	 * 
	 * @param	price
	 * 			The value in amount of dukats for this new item.
	 * @effect	Initializes a new item the same way it would initialize a new item using
	 * 			its most extended constructor involving 0 as its ID, a weight of 5 kg
	 * 			as its weight and price as its price.
	 */
	Item(DukatAmount price){
		this(0, new Weight(5), price);
	}
	
	/**
	 * Initializes a new item.
	 * 
	 * @param	weight
	 * 			The weight for this new item.
	 * @effect	Initializes a new item the same way it would initialize a new item using
	 * 			its most extended constructor involving 0 as its ID, a weight
	 * 			as its weight and 0 dukats as its price.
	 */
	Item(Weight weight){
		this(0, weight, new DukatAmount(0));
	}
	
	/**
	 * Initializes a new item.
	 * 
	 * @effect	Initializes a new item the same way it would initialize a new item using
	 * 			its most extended constructor involving 0 as its ID, a weight of 5 kg
	 * 			as its weight and 0 dukats as its price.
	 */
	Item(){
		this(0, new Weight(5), new DukatAmount(0));
	}

	/**
	 * Checks whether the current direct holders of this item 
	 * are valid holders for this item.
	 * 
	 * @return	If this items is terminated then the only valid
	 * 			direct holder isn't effective.
	 * 			| when( this.isTerminated() && getDirectHolder() == null)
	 * 			|	then result == true
	 * 			| else when (isTerminated() && getDirectHolder() =! null)
	 * 			|	then result == false
	 * @return	The result is false when the item isn't terminated
	 * 			and the direct holder is null or when item holds itself.
	 * 			| when( directHolder == null && !isTerminated() || directHolder == this)
	 *			|	then result == false
	 * @return	Returns false if the current holder is directly or indirectly
	 * 			held by this item.
	 * 			| when this.getDirectHolder().getDirectHolder() ... .getDirectHolder() == this)
	 * 			|	then result == false
	 * @return	In all the other cases the result will be true.
	 * 			| else result == true
	 */
	public boolean hasValidHolder(){
		Holder directHolder = getDirectHolder();
		if(isTerminated() && directHolder == null) 
			return true;
		else if(isTerminated() && directHolder != null){
			return false;
		}
		if(directHolder == null || directHolder == this)
			return false;		
		else{
			Holder parser = getDirectHolder();
			while(!parser.isSupremeHolder()){
				if(parser == this)
					return false;
				parser = ((Item)parser).getDirectHolder();
			}	
		}
		return true;
	}
	
	/**
	 * Returns the holder of this Item. If the holder (e.g. a backpack) of this item is being held itself 
	 *   	by another holder (e.g. a hero), then first holder not being held itself is returned. 
	 */
	public Holder getHolder() {
		if(!holder.isSupremeHolder())
			return ((Item)holder).getHolder();
		else return holder;
	}
	
	/**
	 * Returns the direct holder of this Item. If the holder (e.g. a backpack) of this item is being held itself 
	 *   	by another holder (e.g. a hero), then that backpack is returned.
	 */
	@Basic
	public Holder getDirectHolder(){
		return holder;
	}

	/**
	 * Set the direct holder of this item to a given holder.
	 * 
	 * @param 	holder
	 * 			The new direct holder for this item.
	 * @post	The new direct holder of this item is equal to the given holder.
	 * 			| 	(new this).getHolder() == holder
	 * @throws 	IllegalArgumentException
	 * 			When the holder isn't a valid holder.
	 * 			| !isValidHolder()
	 */
	protected void setHolder(Holder holder) throws IllegalArgumentException{
		if(!isValidHolder(holder))
			throw new IllegalArgumentException("the given holder isn't valid for this item");
		this.holder = holder;
	}
	
	/**
	 * Checks whether a holder can be a valid direct holder for this item.
	 * 
	 * @param 	holder
	 * 			The holder to be checked.
	 * @return	If this items is terminated then the only valid
	 * 			holder isn't effective
	 * 			| when( isTerminated() && holder == null)
	 * 			|	then result == true
	 * 			| else when (isTerminated() && holder =! null)
	 * 			|	then result == false
	 * @return	Returns false if the given holder is directly or indirectly
	 * 			held by this item.
	 * 			| when( holder.getDirectHolder().getDirectHolder() ... .getHolder() == this)
	 *			|	then result == false
	 * @return	In all the other cases the result will be true.
	 * 			| else result == true
	 */
	private boolean isValidHolder(Holder holder){
		if(isTerminated() && holder == null) 
			return true;
		else if(isTerminated() && holder != null){
			return false;
		} 
		else if(holder == null || holder == this)
			return false;
		else {
			Holder parser = holder;
			while(!parser.isSupremeHolder()){
				if(parser == this)
					return false;
				parser = ((Item)parser).getDirectHolder();
			}
		}
		return true;
	}
	
	/**
	 * Variable referencing the direct holder of this item.
	 */
	private Holder holder;

	/**
	 * Returns this item's identification number.
	 */
	@Immutable
	@Basic
	public long getID() {
		return ID;
	}
	
	/**
	 * Checks whether a given ID is valid for this item.
	 * 
	 * @param 	ID
	 * 			The ID to be checked.
	 * @return	The result if false when the given ID is negative.
	 * 			| when (ID < 0)
	 * 			|	then (result == false)
	 */
	public abstract boolean isValidID(long ID);

	/**
	 * Returns the next available ID that can be used for creation of a new item.
	 * 
	 * @param 	ID
	 * 			The ID to base the search for a valid ID on.
	 * @return	The result will be a valid ID.
	 * 			| isValidID(result)
	 */
	protected abstract long getNextID(long ID);

	/**
	 * A variable registering this item's identification number.
	 */
	private final long ID;
	
	/**
	 * Returns the weight of this item plus eventually the weight of everything it contains.
	 */
	@Basic
	public Weight getWeight(){
		return weight;
	}
	
	/**
	 * Checks whether a given weight is potentially ok for this item. 
	 * 
	 * @param	weight
	 * 			A weight that you want to test.
	 * @return	The result is true when the given weight compared 
	 * 			with an empty weight is larger than zero.
	 * 			| result == weight.compareTo(new Weight(0)) > 0
	 * @return	When the given weight isn't effective the result 
	 * 			is false.
	 * 			| when (weight == null)
	 * 			|	then result == false
	 */
	public boolean isValidWeight(Weight weight){
		if(weight == null)
			return false;
		Weight zero = new Weight(0);
		return weight.compareTo(zero) > 0;
	}
	
	/**
	 * Variable referencing the value of the weight of this item.
	 */
	private final Weight weight;
	
	/**
	 * Returns the value of this item in amount of dukats.
	 */
	@Basic
	public DukatAmount getPrice(){
		return price;
	}
	
	/**
	 * Set the price of this item to a given amount of dukats.
	 * 
	 * @param 	price
	 * 			The new value in dukats for this item.
	 * @post	The new value of this item is equal to the given amount.
	 * 			| this.getPrice().equals(price)
	 * @post	If this item can't have the given price as
	 * 			it's price. Then nothing happens.
	 * 			| when !canHaveAsPrice(price)
	 * 			|	then return;
	 */
	protected void setPrice(DukatAmount price){
		if(canHaveAsPrice(price))
			this.price = price;
	}
	
	/**
	 * Checks whether a given price can be a potential price of this item.
	 * 
	 * @param 	price
	 * 			The price to be checked.
	 * @return	The result is false when the given price isn't effective.
	 * 			| when price == null
	 * 			|	then result == false
	 * @return	The result is false when the amount of the given
	 * 			price is smaller than zero.
	 * 			| when  price.getAmount() <= 0
	 * 			|	then result == false;
	 */
	protected abstract boolean canHaveAsPrice(DukatAmount price);
	
	/**
	 * Variable referencing the value of this item. 
	 */
	private DukatAmount price;
	
	/**
	 * Drops this item on the ground. What this means depends on the inheriting class.
	 * 
	 * @pre 	This item mustn't be on the ground already.
	 */
	public abstract void drop();
	
	/**
	 * Returns the dropped state of this item.
	 * 
	 * @return	The result is true when the direct holder
	 * 			is the world.
	 * 			| when getDirectHolder() == World.world
	 * 			|	then result == true
	 */
	public boolean isDropped(){
		return getDirectHolder() == World.world;
	}
	
	/**
	 * Returns the terminated state of this item.
	 */
	@Basic
	@Raw
	public boolean isTerminated(){
		return isTerminated;
	}

	/**
	 * This item disconnects itself from every object
	 * and is ready to be recycled by the java garbage collector.
	 * 
	 * @post	The direct holder will be null for the item
	 * 			| new.getDirectHolder() == null
	 * @post	The old direct holder won't hold the item.
	 * 			| !(new getDirectHolder).holdItem(this)
	 */
	protected void terminate(){
		setTerminate(true);
		Holder holder = this.getDirectHolder();
		setHolder(null);
		holder.removeItem(this);
	}

	/**
	 * Set the terminated state of this item to a given flag
	 * 
	 * @param 	terminate
	 * 			The flag to set the terminated state to.
	 * @post	The new terminated state of this item is the given
	 * 			flag when the item already isn't terminated.
	 * 			|when (!isTerminated()) 
	 * 			| then new.isTerminated() == terminate
	 */
	protected void setTerminate(boolean terminate){
		if(isTerminated() == false)
			isTerminated = terminate;
	}
	
	/**
	 * Variable registering the terminated state of this item.
	 */
	private boolean isTerminated = false;
		
	/**
	 * Move this item to an available anchor point of a given holder.
	 * 
	 * @param 	newHolder
	 * 			The holder to were you want to move move the item to.
	 * @effect	oldHolder is a variable that represents the old
	 * 			holder of this item.
	 * 			| let oldHolder = this.getDirectHolder()
	 * 			First: The given holder is set as the new holder of
	 * 			 this item.
	 * 			| setHolder(newHolder);
	 * 			Second: Removes the item from the equipment of 
	 * 			the old holder.
	 * 			| oldHolder.removeItem(this);
	 * 			Third: Adds the item to the equipment of the 
	 * 			given holder.
	 * 			| newHolder.addItem(this);
	 * @post	The new direct holder of the item is the given holder
	 * 			and the new old holder doesn't hold the item.
	 * 			| new.getDirectHolder() == newHolder; 
	 * 			| (new.(this.getDirecthHolder)).holdsItem(this) == false;
	 * @post	When an exception occurrence during the execution of the method
	 * 			all the objects will return to their old state.
	 * 			| when throws exception
	 * 			|	then (new this).equal(this)
	 * 			|	  && (new newHolder).equal(newHolder)	
	 * @throws	IllegalArgumentException
	 * 			When the given holder isn't effective.
	 * 			| newHolder == null
	 * @throws	IllegaleToestandsUitzondering
	 * 			When the given holder can't hold this item
	 * 			or when the given holder isn't valid
	 * 			| !newHolder.canHoldItem(this) || !isValdiHolder(newHolder)
	 */
	public void moveTo(Holder newHolder) throws IllegalArgumentException, IllegaleToestandsUitzondering {
		Holder oldHolder = getHolder();
		if(newHolder == null)
			throw new IllegalArgumentException();
		if(!newHolder.canHoldItem(this) || !isValidHolder(newHolder)) { 
			throw new IllegaleToestandsUitzondering("the given holder isn't valid for this item");
		}
		try {
			setHolder(newHolder);
			oldHolder.removeItem(this);
			newHolder.addItem(this); 
		} catch (IllegaleToestandsUitzondering e) {
			reset(oldHolder);
			throw e;
		} catch (IllegalArgumentException e) {
			reset(oldHolder);
			throw e;
		} 
	}
		
	/**
	 * Move this item to some location equipment of a given hero.
	 * 
	 * @param 	newHero
	 * 			The hero that's going to hold this item next.
	 * @param 	location
	 * 			The location equipment where the hero's going 
	 * 			to hold this item.
	 * @effect	This method has the same effect as when
	 * 			the method moveTo whit a interger instead of a 
	 * 			location equipment.
	 * 			| moveTo( newHero,location.getFollowNumber())
	 * @throws	IllegalArgumentException
	 * 			When the location isn't effective.
	 * 			| location == null
	 */
	public void moveTo(Hero newHero, LocationEquipment location) throws IllegaleToestandsUitzondering, IllegalArgumentException {
		if(location == null)
			throw new IllegalArgumentException("ineffectif location");
		moveTo(newHero,location.getFollowNumber());
	}
	
	/**
	 * Move this item to some location of a given actor.
	 * 
	 * @param 	newActor
	 * 			The actor that's going to hold this item next.
	 * @param 	location
	 * 			The location where the actor is going to hold this item.
	 * @effect	oldActor is a variable that represents the old
	 * 			holder of this item.
	 * 			| let oldActor = this.getDirectHolder()
	 * 			First: The given actor is set as the new holder
	 * 			of the given item
	 * 			| setHolder(newActor)
	 * 			Second: The item is removed form the old holder
	 * 			| oldActor.removeItem(this);
	 * 			Third: The item is add to the collection of the
	 * 			given actor
	 * 			| newActor.addItem(this, location); 
	 * @post	The new direct holder of the item is the given actor
	 * 			and the new old holder doesn't hold the item.
	 * 			| new.getDirectHolder() == newHolder; 
	 * 			| (new.(this.getDirecthHolder)).holdsItem(this) == false;
	 * @post	The given actor holds the this item in the given 
	 * 			location
	 * 			| (new newActor).getItem(location) == this
	 * @post	When an exception occurrence during the execution of the method
	 * 			all the objects will return to their old state.
	 * 			| when throws exception
	 * 			|	then (new this).equal(this)
	 * 			|	  && (new newHolder).equal(newHolder)
	 * @throws	IllegalArgumentException
	 * 			When the new actor isn't effective.
	 * 			| newHolder == null
	 * @throws	IllegaleToestandsUitzondering
	 * 			When the new actor can't hold the given items on
	 * 			the given location or when the given actor isn't
	 * 			a valid holder for this item.
	 * 			|(!newActor.canHoldItem(this,location) || !isValidHolder(newActor))
	 */
	public void moveTo(Actor newActor, int location) throws IllegaleToestandsUitzondering, IllegalArgumentException {
		Holder oldActor = getHolder();
		if(newActor == null)
			throw new IllegalArgumentException();
		if(!newActor.canHoldItem(this,location) || !isValidHolder(newActor)) { 
			throw new IllegaleToestandsUitzondering("the given holder isn't valid for this item");
		}
		try {
			setHolder(newActor);
			oldActor.removeItem(this);
			newActor.addItem(this, location); 
		} catch (IllegaleToestandsUitzondering e) {
			reset(oldActor);
			throw e;
		} catch (IllegalArgumentException e) {
			reset(oldActor);
			throw e;
		} 
	}
	
	/**
	 * A small method that is used by the method 
	 *  moveTo(Holder holder). When an exception occurrence in that
	 *  method.
	 *  
	 * @param 	oldHolder
	 * 			The direct holder that the item had on 
	 * 			invocation of the method moveTo(Holder holder).
	 * @post 	The direct holder of this item is the given
	 * 			holder and the given holder holds this item.
	 * 			| new.getDirectHolder() == oldHolder
	 * 			| (new oldHolder).holdsItem(this)== true
	 */
	private void reset(Holder oldHolder) {
		if(getHolder() != oldHolder)
			setHolder(oldHolder);
		if(!oldHolder.holdsItem(this))
			oldHolder.addItem(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + ((holder == null) ? 0 : holder.hashCode());
		result = prime * result + (isTerminated ? 1231 : 1237);
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		if (ID != other.ID) {
			return false;
		}
		if (holder == null) {
			if (other.holder != null) {
				return false;
			}
		} else if (!holder.equals(other.holder)) {
			return false;
		}
		if (isTerminated != other.isTerminated) {
			return false;
		}
		if (price == null) {
			if (other.price != null) {
				return false;
			}
		} else if (!price.equals(other.price)) {
			return false;
		}
		if (weight == null) {
			if (other.weight != null) {
				return false;
			}
		} else if (!weight.equals(other.weight)) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Type of direct holder=" + getDirectHolder().getClass() 
				+ ", ID=" + getID() 
				+ ", Is dropped=" + isDropped()
				+ ", Is terminated=" + isTerminated();
	}
}
