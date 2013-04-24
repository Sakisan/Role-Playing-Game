package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * DukatAmount is a container class for dukats. It represents a certain amount
 * 	of dukats as well in value as in weight.
 * 
 * @invar The amount of dukats represented is always bigger than or equal to zero.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class DukatAmount implements Comparable<DukatAmount> {
	
	/**
	 * Initializes a new DukatAmount with a given initial value for the amount
	 * 	of dukats represented.
	 * 
	 * @param 	amountOfDukats
	 * 			The amount of dukats to be initially represented.
	 * @param	maximum
	 * 			The maximum value that this DukatAmount may have.
	 * @post	This new DukatAmount represents the given amount of dukats 
	 * 			and won't ever have a value bigger than maximum.
	 */
	@Model
	private DukatAmount(int amountOfDukats, int maximum){
		setAmount(amountOfDukats);
		this.maximum = maximum;
	}
	
	/**
	 * Initializes a new DukatAmount with a given initial value for the amount
	 * 	of dukats represented.
	 * 
	 * @param 	amountOfDukats
	 * 			The amount of dukats to be initially represented.
	 * @effect	Initializes a new DukatAmount the same way it would initialize 
	 * 			a new DukatAmount using its most extended constructor involving 
	 * 			amountOfDukats as its amount of dukats to be initially represented
	 * 			and Integer.MAX_VALUE as its maximum value.
	 */
	DukatAmount(int amountOfDukats){
		this(amountOfDukats, Integer.MAX_VALUE);
	}
	
	/**
	 * Initializes a new DukatAmount with a given initial weight for the amount
	 * 	of dukats represented.
	 * 
	 * @param 	weightOfDukats
	 * 			The weight of the dukats to be initially represented.
	 * @effect	Initializes a new DukatAmount the same way it would initialize 
	 * 			a new DukatAmount using its most extended constructor involving 
	 * 			the amount of dukats which weight will be as close to the given
	 * 			weight, but less than or equal to the given weight,
	 * 			as its amount of dukats to be initially represented
	 * 			and Integer.MAX_VALUE as its maximum value.
	 */
	DukatAmount(Weight weightOfDukats){
		this(getEquivalentAmount(weightOfDukats));
	}
	
	/**
	 * Initializes a new DukatAmount.
	 * 
	 * @effect	Initializes a new DukatAmount the same way it would initialize 
	 * 			a new DukatAmount using its most extended constructor involving 0 
	 * 			as its amount of dukats to be initially represented
	 * 			and Integer.MAX_VALUE as its maximum value.
	 */
	DukatAmount(){
		this(0,Integer.MAX_VALUE);
	}

	/**
	 * Returns the amount of dukats that this DukatAmount represents.
	 */
	@Basic
	public int getAmount() {
		return amountOfDukats;
	}
	
	/**
	 * Add a given amount of dukats to this DukatAmount.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to add.
	 * @pre		amount is a positive number.
	 * @post	If the resulting amount of dukats isn't over its maximum,
	 * 			then the new amount of dukats represented by this DukatAmount
	 * 			is the sum of the previous amount and the given amount. 
	 * @return	If resulting amount of dukats is over its maximum, then
	 * 			the new amount of dukats represented by this DukatAmount
	 * 			is at it maximum and the amount of dukats that couldn't be added
	 * 			is returned.
	 */
	public int add(int amount){
		if(getAmount() > getMaximum() - amount){
			int returned = getMaximum() - getAmount();
			returned  = amount - returned;
			setAmount(getMaximum());
			return returned;
		}
			
		setAmount(getAmount() + amount);
		return 0;
	}
	
	/**
	 * Retract a given amount of dukat from this DukatAmount.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to retract.
	 * @pre		amount is a positive number.
	 * @post	If the amount to retract isn't more than the available amount, then
	 * 			the new amount represented by this DukatAmount is the difference of
	 * 			the two.
	 * @post	If the amount to retract is more than the available amount, then
	 * 			the new amount represented by this DukatAmount is zero.
	 */
	public void retract(int amount){
		if(amount <= getAmount())
			setAmount(getAmount()-amount);
		else setAmount(0);
	}

	/**
	 * Sets the amount of dukats represented to a given number. 
	 * 
	 * @param 	amountOfDukats
	 * 			The amount of dukats to be represented
	 * @post	This new DukatAmount represents the given number of dukats.
	 */
	private void setAmount(int amount) {
		this.amountOfDukats = amount;
	}

	/**
	 * Variable registering how many dukats this DukatAmount represents.
	 */
	private int amountOfDukats;
	
	/**
	 * Returns the weight of as many dukats as this DukatAmount represents.
	 */
	public Weight getWeight(){
		return new Weight(50*getAmount(), UnitOfWeight.GRAM);
	}
	
	/**
	 * Return the maximum amount of dukats that this DukatAmount can represent.
	 */
	@Immutable
	@Basic
	public int getMaximum(){
		return maximum;
	}

	/**
	 * Variable registering the maximum amount of dukats that this DukatAmount can represent.
	 */
	private final int maximum;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DukatAmount other) throws IllegalArgumentException {		
		if(this.getAmount() == other.getAmount())
			return 0;
		else if(this.getAmount() > other.getAmount())
			return 1;
		else
			return -1;
	}
		
	/**
	 * Returns the amount of dukats which weight will be as close to the given
	 * 			weight, but less than or equal to the given weight.
	 * 
	 * @param 	weight
	 * 			The weight of the dukats to be represented. 
	 */
	public static int getEquivalentAmount(Weight weight){
		boolean found = false;
		int i = 0;
		DukatAmount dukats;
		while(!found){
			dukats = new DukatAmount(i);
			if((dukats.getWeight()).compareTo(weight) > 0)
				if(i == 0)
					return 0;
				else
					return i-1;
			i++;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amountOfDukats;
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
		DukatAmount other = (DukatAmount) obj;
		if (amountOfDukats != other.amountOfDukats) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "" +getAmount();
		if(getAmount() <=1 && getAmount()>= 0)
			return result+" Dukat";
		else 
			return  getAmount() + " Dukats";
	}
	
	
}
