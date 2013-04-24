package rpgGame;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An abstract class representing all items that have an enhancing nature.
 * 		For example a weapon enhances a hero's damage, and an armor enhances
 * 		a hero's defense.
 * 
 * @invar	This item's value is a valid value.
 * 			| canHaveAsValue(getValue())
 * @invar	This item's minimum value is valid.
 * 			| canHaveAsMinimumValue(getMinimumValue())
 * @invar	This item's maximum value is valid.
 * 			| canHaveAsMaximumValue(getMaximumValue())
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public abstract class Enhancer extends Item {

	/**
	 * Initializes a new enhancer with given ID, minimum value, value, weight and price.
	 * 
	 * @param 	ID
	 * 			The number that will be used to calculate the ID of this new enhancer.
	 * @param	min
	 * 			The minimum value for the special effect.
	 * @param	value
	 * 			The protection factor of the enhancer.
	 * @param	weight
	 * 			The weight for this new enhancer.
	 * @param	price
	 * 			The price for this new enhancer.
	 * @effect	Initializes an item involving ID as its ID, weight as its weight and price as its price.
	 * @effect	Sets the minimum value of the special effect of this item to a given value.
	 * @effect	Sets the value of the special effect of this item to a given value.
	 */
	Enhancer(long ID, int min, int value, Weight weight, DukatAmount price){
		super(ID, weight, price);
		setValue(value);
		setMinimumValue(min);
	}
	
	/**
	 * Initializes a new enhancer given minimum value, value, weight and price.
	 * 
	 * @param	min
	 * 			The minimum value for the special effect.
	 * @param	value
	 * 			The protection factor of the enhancer.
	 * @param	weight
	 * 			The weight for this new enhancer.
	 * @param	price
	 * 			The price for this new enhancer.
	 * @effect 	Initializes a new Enhancer the same way it would be initialized
	 * 			using the most extended constructor involving 0 as its ID, min
	 * 			as its minimum value, value as its value, weight as its weight and 
	 * 			price as its price.
	 */
	Enhancer(int min, int value, Weight weight, DukatAmount price){
		this(0,min, value, weight, price);
	}
	
	/**
	 * Returns the value for the special effect of this item.
	 */
	@Basic
	public int getValue() {
		return value;
	}

	/**
	 * Set the special effect of this item to a given number.
	 * 
	 * @param 	value
	 * 			The number to set the special effect to.
	 * @pre		This enhancer can have this value as a new value.
	 * 			| canHaveAsValue(value)
	 * @post	The new value for the special effect of this item is equal to the given number.
	 */
	private void setValue(int value) {
		assert(canHaveAsValue(value));
		this.value = value;
		if(getMaximumValue() != 0)
			updatePrice();
	}
	
	/**
	 * Checks whether this item can have a given number as its special value.
	 * 
	 * @param 	value
	 * 			The number to be checked.
	 * @return	The number has to be larger than or equal to the minimum value 
	 * 			and less or equal to the maximum value.
	 * 			| result == value >= getMinimumValue()
	 * 			|			&& value <= getMaximumValue()
	 */
	@Model
	@Raw
	protected abstract boolean canHaveAsValue(int value);
	
	/**
	 * A variable registering the value of the special effect that this item has.
	 */
	private int value;

	/**
	 * Returns the minimum value of the special effect that this item has.
	 */
	@Basic
	public int getMinimumValue() {
		return minimumValue;
	}

	/**
	 * Sets the minimum value of the special effect of this item to a given value.
	 * 
	 * @param 	minimumValue
	 * 			The new minimum value for the special effect of this item.
	 * @pre		The minimumValue must be valid
	 * 			| canHaveAsMinimumValue(minimumValue)
	 * @post	The new minimum value for the special effect of this item is equal to the given number.
	 */
	protected void setMinimumValue(int minimumValue) {
		assert(canHaveAsMinimumValue(minimumValue));
		this.minimumValue = minimumValue;
	}
	
	/**
	 * Checks whether a given value can be a potential minimum value for this enhancer.
	 * 
	 * @param 	minimumValue
	 * 			The value to be checked.
	 * @return 	True if the value is larger then or equal to zero and smaller than the maximum value.
	 * 			| result == minimumValue >= 0 &&  minimumValue < getMaximumValue()
	 */
	@Model
	@Raw
	private boolean canHaveAsMinimumValue(int minimumValue){
		return minimumValue >= 0 &&  minimumValue < getMaximumValue();
	}
	
	/**
	 * A variable registering the minimum value of the special effect that this item has.
	 */
	private int minimumValue;
	
	/**
	 * Returns the maximum value of the special effect that this item has.
	 */
	public abstract int getMaximumValue();
	
	/**
	 * Checks whether a given value can be a potential maximum value for this enhancer.
	 * 
	 * @param 	max
	 * 			The maximum to be checked.
	 * @return 	True if the value is larger than the minimum value
	 * 			| result == max > getMinimumValue();
	 */
	@Model
	@Raw
	protected boolean canHaveAsMaximumValue(int max){
		return max > getMinimumValue();
	}

	/**
	 * Drops this item on the ground. 
	 * This means this enhancer disconnects itself from every object
	 * and is ready to be recycled by the java garbage collector.
	 * 
	 * @pre 	This enhancer mustn't be on the ground already.
	 */
	public void drop(){
		terminate();
	}

	/**
	 * Sets the value of this item to the right value in amount of dukats.
	 */
	protected abstract void updatePrice();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "value=" + getValue()  +"/"+getMaximumValue() 
				+ ", Weight=" + getWeight() 
				+ ", " + super.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + minimumValue;
		result = prime * result + value;
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
		Enhancer other = (Enhancer) obj;
		if (minimumValue != other.minimumValue) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}
}
