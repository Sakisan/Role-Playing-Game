package rpgGame;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * An abstract class representing all items that can contain dukats.
 * 
 * @invar	The amount of dukats contained by this bag is always larger than
 * 			or equal to zero.
 * 			| getDukats().getAmount() >= 0
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public abstract class Bag extends Item {

	/**
	 * Initializes a new bag with a given weight.
	 * 
	 * @param 	bagsWeight
	 * 			The weight for this new bag.
	 * @effect	A new item is initialized with the given weight as its weight.
	 */
	Bag(Weight bagsWeight){
		super(bagsWeight);
	}
	
	/**
	 * Initializes a new bag.
	 * 
	 * @effect	A new bag is initialized involving a weight of 5 kg as its weight.
	 */
	Bag(){
		this(new Weight(5));
	}
	
	/**
	 * Returns the amount of dukats that this bag contains.
	 */
	@Basic
	public DukatAmount getDukats(){
		return dukatAmount;
	}
	
	/**
	 * Add a given amount of dukats to this bag.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to add.
	 * @pre		amount is a positive number.
	 * 			| amount > 0
	 * @post	If the resulting amount of dukats isn't over its maximum,
	 * 			and if the resulting content doesn't exceed the capacity of this bag
	 * 			then the new amount of dukats contained by this bag
	 * 			is the sum of the previous amount and the given amount. 
	 * 			| if(canAddDukats(amount))
	 * 			| 	getDukats().add(amount)
	 * @throws	IllegaleToestandsUitzondering
	 * 			If the resulting amount of dukats is over its maximum,
	 * 			then only what can be added will be added and 
	 * 			an IllegaleToestandsUitzondering will be thrown.
	 * 			| getDukats().add(amount) > 0
	 */
	public abstract void addDukats(int amount) throws IllegaleToestandsUitzondering;
	
	/**
	 * Checks whether a given amount of dukats can be added to this bag.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to be checked.
	 * @pre		amount is a positive number.
	 * 			| amount > 0
	 * @return	True if the sum of the dukats to be added and the dukats that already
	 * 			are in this bag doesn't exceed this bag capacity.
	 * 			| result == amount <= dukatsTofull();
	 */
	public abstract boolean canAddDukats(int amount);

	/**
	 * Retract a given amount of dukats from this bag.
	 * 
	 * @param 	dukats
	 * 			The amount of dukats to retract.
	 * @pre		amount is a positive number.
	 * @post	If the amount to retract isn't more than the available amount, then
	 * 			the new amount of dukats in this bag is the difference of the two.
	 * @post	If the amount to retract is more than the available amount, then
	 * 			the new amount of dukats in this bag is zero.
	 */
	public void retractDukats(int dukats)
	{
		getDukats().retract(dukats);
	}
	
	/**
	 * Returns the biggest amount of dukats possible that you can add to this bag
	 * so it doesn't exceed this bag's capacity.
	 */
	public abstract DukatAmount dukatsToFill();
	
	/**
	 * Variable referencing the dukat container for this bag. 
	 */
	private DukatAmount dukatAmount = new DukatAmount();
	
	/**
	 * Returns the capacity of this purse.
	 */
	public abstract Comparable getCapacity();
	
	/**
	 * Returns the weight of this bag if it would be completely empty.
	 */
	public Weight getBagsWeight(){
		return super.getWeight();
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Item#getWeight()
	 */
	public Weight getWeight(){
		Weight weight = new Weight(0);
		weight = weight.add(getBagsWeight());
		return weight.add(dukatAmount.getWeight());
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Item#getPrice()
	 */
	public DukatAmount getPrice(){
		DukatAmount price = new DukatAmount(0);
		price.add(super.getPrice().getAmount());
		price.add(dukatAmount.getAmount());
		return price;
	}
	
	/**
	 * Drops this item on the ground.
	 * 
	 * @pre 	This bag mustn't be on the ground already.
	 * 			| this.getHolder() != World.world 
	 * @post	The world becomes the new holder of this bag.
	 * 			| (new this).getHolder() == World.world
	 */
	@Override
	public void drop() {
		Holder holder = this.getDirectHolder();
		setHolder(World.world);
		holder.removeItem(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((dukatAmount == null) ? 0 : dukatAmount.hashCode());
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
		Bag other = (Bag) obj;
		if (dukatAmount == null) {
			if (other.dukatAmount != null) {
				return false;
			}
		} else if (!dukatAmount.equals(other.dukatAmount)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  ", Weight=" + getWeight() + "/" + getCapacity()
				+ ", Price=" + getPrice().toString() 
				+ ", " + super.toString();
	}

}