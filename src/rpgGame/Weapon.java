package rpgGame;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;

/**
 * A class of weapons.
 * 		Weapons are worn by heroes to make their strength rise.
 * 
 * @invar 	A weapon's ID number is at all times a positive even number that is divisible by 3.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Weapon extends Enhancer {
	
	/**
	 * Initializes a new weapon.
	 * 
	 * @effect	This new weapon is initialized in the same way a new weapon 
	 *			would be initialized with the more extended constructor 
	 *			involving a value between 1 and 100 as its damage value, 1 as its 
	 *			minimum damage value, 100 as its maximum damage value,
	 *			a weight of 5 kg as its weight and the standard price as its price in amount of dukats.
	 */
	Weapon(){
		this(new Weight(5));
	}
	
	/**
	 * Initializes a new weapon with given weight.
	 * 
	 * @param 	weight
	 * 			The weight of the new weapon. 
	 * @effect	This new weapon is initialized in the same way a new weapon 
	 *			would be initialized with the more extended constructor 
	 *			involving a value between 1 and 100 as its damage value, 1 as its 
	 *			minimum damage value, 100 as its maximum damage value,
	 *			weight as its weight and the standard price as its price in amount of dukats.
	 */
	public Weapon(Weight weight){
		this(generateRandomValue(100), weight);
	}
	
	/**
	 * Initializes a new weapon with given damage value and a given weight.
	 * 
	 * @param 	damage
	 * 			The damage value of the new weapon. 
	 * @param 	weight
	 * 			The weight of the new weapon.
	 * @pre		This weapon can have this damage as a new value.
	 * 			| canHaveAsValue(value)
	 * @effect	This new weapon is initialized in the same way a new weapon 
	 *			would be initialized with the more extended constructor 
	 *			involving the given damage as its damage value, 1 as its minimum damage value,
	 *			100 as its maximum damage value, weight as its weight 
	 *			and the standard price as its price in amount of dukats.
	 */
	public Weapon(int damage, Weight weight){
		this(damage, 1,100, weight, getStandardPrice(damage));
		standardPrice = true;
	}
	
	/**
	 * Initializes a new weapon with given damage value, a given price and a given weight.
	 * 
	 * @param 	damage
	 * 			The damage value of the new weapon. 
	 * @param 	weight
	 * 			The weight of the new weapon.
	 * @param	price
	 * 			The price in amount of dukats for the new weapon.
	 * @pre		This weapon can have this damage as a new value.
	 * 			| canHaveAsValue(value)
	 * @pre		Price has a value between 1 and 200.
	 * @effect	This new weapon is initialized in the same way a new weapon 
	 *			would be initialized with the more extended constructor 
	 *			involving the given damage as its damage value, 1 as its minimum damage value,
	 *			100 as its maximum damage value, weight as its weight and price 
	 *			as its price in amount of dukats.
	 */
	public Weapon(int damage, Weight weight, DukatAmount price){
		this(damage, 1 , 100, weight, price);
	}

	/**
	 * Initializes a new weapon with given damage value, minimum damage value, maximum damage value,
	 * weight and price.
	 * 
	 * @param 	damage
	 * 			The damage value of the new weapon. 
	 * @param 	min
	 * 			The minimum damage value of the new weapon.
	 * @param 	max
	 * 			The maximum damage value of the new weapon.
	 * @param 	weight
	 * 			The weight of the new weapon.
	 * @param	price
	 * 			The price in amount of dukats for the new weapon.
	 * @pre		This weapon can have this damage as a new value.
	 * 			| canHaveAsValue(value)
	 * @pre 	This weapon can have this minimum damage value.
	 * 			| canHaveAsMinimumValue(min)
	 * @pre 	This weapon can have this maximum damage value.
	 * 			| canHaveAsMaximumValue(max)
	 * @pre		Price has a value between 1 and 200.
	 * @effect	A new enhancer is initialized with min as minimum value, value as its damage,
	 * 			weight as its weight and price as its value in amount of dukats.
	 * @effect	Set the maximum value of this weapon to the given maximum.
	 */
	@Model
	private Weapon(int damage, int min, int max, Weight weight, DukatAmount price){
		super(min, damage, weight, price);
		setMaximumValue(max);
	}
	
	/**
	 * Returns the next ID number that is going to be used for a new weapon.
	 * 		This ID number is updated every time this method is invoked.
	 */
	@Override
	@Basic
	protected long getNextID(long ID){
		if(previousID >= Long.MAX_VALUE -5 )
			previousID = 0;
		previousID += 6;
		return previousID;
	}

	/**
	 * Checks whether a given ID is valid for this item.
	 * 
	 * @param 	ID
	 * 			The ID to be checked.
	 * @return	True if the ID is a positive even number that is divisible by 3.
	 */
	public boolean isValidID(long ID){
		return ID % 3 == 0 && ID % 2 == 0 && ID > 0;
	}
	
	/**
	 * Variable registering the last ID number that was used to create a new weapon.
	 */
	private static long previousID = 0;
	
	/**
	 * Returns a random value that's a valid value for this weapon.
	 */
	private static int generateRandomValue(int maximum)
	{
		int deler = 7;
		int c =  maximum / deler - 1;
		return deler*(int)(c*Math.random() + 1);
	}
	
	/** 
	 * @return  The number has to be larger than or equal to the minimum value 
	 * 			and less or equal to the maximum value and the value must be divisible by 7.
	 * 			| result == value >= getMinimumValue()
	 * 			|  			&& value <= getMaximumValue()
	 * 			| 			&& value % 7 == 0
	 */
	@Override
	public boolean canHaveAsValue(int value){
		return value >= getMinimumValue() && 
				value <= getMaximumValue() && value % 7 == 0;
	}

	/* (non-Javadoc)
	 * @see rpgGame.Enhancer#getMaximumValue()
	 */
	@Override
	@Basic
	public int getMaximumValue() {
		return maximumValue;
	}

	/**
	 * Sets the maximum value of the special effect of all weapons to a given value.
	 * 
	 * @param 	value
	 * 			The new maximum value for the special effect of all weapons.
	 * @pre		The maximumValue must be valid
	 * 			| canHaveAsMaximum(maximumValue)
	 * @post	The new maximum value for the special effect of all weapons is equal to the given number.
	 */
	public static void setMaximumValue(int value) {
		maximumValue = value;	
	}
	
	/**
	 * Variable registering the general maximum damage value for weapons.
	 */
	private static int maximumValue;
	
	/**
	 * Return the standard price for a weapon with a given value. 
	 * 
	 * @param 	damage
	 * 			The damage that the weapon does.
	 */
	@Basic
	public static DukatAmount getStandardPrice(int damage){
		return new DukatAmount(2*damage);
	}

	/* (non-Javadoc)
	 * @see rpgGame.Enhancer#updatePrice()
	 */
	@Override
	protected void updatePrice() {
		if(standardPrice)
			setPrice(getStandardPrice(getValue()));
	}
	
	/**
	 * Variable registering whether or not this weapon has the standard price or not.
	 */
	private boolean standardPrice;
	
	/* (non-Javadoc)
	 * @see rpgGame.Item#canHaveAsPrice(rpgGame.DukatAmount)
	 */
	protected boolean canHaveAsPrice(DukatAmount price){
		return price.getAmount() <= 200;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (standardPrice ? 1231 : 1237);
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
		Weapon other = (Weapon) obj;
		if (standardPrice != other.standardPrice) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Weapon [Price="+ getPrice() 
				+", " + super.toString()
				+ "]";
	}
}
