package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing an armor.
 * 		Armors are worn by heroes to make their protection level rise.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Armor extends Enhancer {

	/**
	 * Initializes a new armor with given ID.
	 * 
	 * @param 	ID
	 * 			The number that will be use to calculate the ID of the new armor.
	 * @effect	This new armor is initialized in the same way a new armor 
	 *			would be initialized with the more extended constructor 
	 *			involving the given ID as its ID, a value in between 0 and 100 as its value, one as its 
	 *			mininum protection level and 100 as its maximum protection level, a weight of 5 kg as 
	 *			its weight, 100 as its maximum price and the standard price as its value in amount 
	 *			of dukats.
	 */
	Armor(long ID){
		this(ID, new Weight(5), 100);
	}
	
	/**
	 * Initializes a new armor with given ID and protection factor.
	 * 
	 * @param 	ID
	 * 			The number that will be use to calculate the ID of the new armor.
	 * @param	protectionFactor
	 * 			The protection factor of the armor.
	 * @pre		ID is a positive non zero integer
	 * @effect	This new armor is initialized in the same way a new armor 
	 *			would be initialized with the more extended constructor 
	 *			involving the given ID as its ID, the protection factor as its value, one as its 
	 *			mininum protection level and 100 as its maximum protection level, a weight of 5 kg 
	 *			as its weight, 100 as its maximum price and the standard price as its value in 
	 *			amount of dukats.
	 */
	Armor(long ID, int protectionFactor){
		this(ID, protectionFactor, new Weight(5), 100);
	}
	
	/**
	 * Initializes a new armor with given ID, weight and maximum price.
	 * 
	 * @param 	ID
	 * 			The number that will be use to calculate the ID of the new armor.
	 * @param 	weight
	 * 			The weight of the new armor.
	 * @param	maxPrice
	 * 			The maximum price for this new armor.
	 * @pre		ID is a positive non zero integer
	 * @effect	This new armor is initialized in the same way a new armor 
	 *			would be initialized with the more extended constructor 
	 *			involving the given ID as its ID, a value in between 0 and 100 as its value, one as its 
	 *			mininum protection level and 100 as its maximum protection level, weight as its weight,
	 *			maxPrice as its maximum price and the standard price as its value in amount of dukats.
	 */
	public Armor(long ID, Weight weight, int maxPrice){
		this(ID, MathHelp.randomInt(100), weight, maxPrice);
	}
	
	/**
	 * Initializes a new armor with given ID, protection factor, weight and maximum price.
	 * 
	 * @param 	ID
	 * 			The number that will be use to calculate the ID of the new armor.
	 * @param	protectionFactor
	 * 			The protection factor of the armor.
	 * @param 	weight
	 * 			The weight of the new armor.
	 * @param	maxPrice
	 * 			The maximum price for this new armor.
	 * @pre		ID is a positive non zero integer
	 * @effect	This new armor is initialized in the same way a new armor  would be initialized 
	 * 			with the more extended constructor involving the given ID as its ID, the given 
	 * 			protection factor as its value, one as its mininum protection level and 100 as 
	 * 			its maximum protection level, the given weight as its weight, the given maximum 
	 * 			price as its maximum price and the standard price as its value in amount of dukats.
	 */
	public Armor(long ID, int protectionFactor, Weight weight, int maxPrice){
		this(ID,protectionFactor, 0,100, weight, maxPrice, 
				getStandardPrice(protectionFactor, 100, new DukatAmount(maxPrice)));
	}
	
	/**
	 * Initializes a new armor with given ID, protection factor, minimum protection factor,
	 * 		maximum protection factor, weight, maximum price and actual price.
	 * 
	 * @param 	ID
	 * 			The number that will be use to calculate the ID of the new armor.
	 * @param	protectionFactor
	 * 			The protection factor of the armor.
	 * @param	min
	 * 			The minimum protection factor.
	 * @param 	max
	 * 			The maximum protection factor.
	 * @param 	weight
	 * 			The weight of the new armor.
	 * @param	maxPrice
	 * 			The maximum price for this new armor.
	 * @param	price
	 * 			The price in amount of dukats for the new armor.
	 * @pre		ID is a positive non zero integer
	 * @pre		This new armor can have this maximum protection factor.
	 * @post	This armor's ID is the greatest prime number under the given ID.
	 * 			| getID() == MathHelp.greatestPrimeNumberUnder(ID);
	 * @post	The maximum price of this armor is equal to the given number.
	 * @post	The maximum protection factor of this armor is equal to the given number.
	 * @effect	A new Enhancer is initialized with ID as its ID, the given protection 
	 * 			factor as its protection factor, the given minimum protection factor as 
	 * 			its minimum value, the given weight as its weight and its standard price 
	 * 			as its value in amount of dukats.
	 */
	@Model
	private Armor(long ID, int protectionFactor, int min, int max, 
						Weight weight, int maxPrice, DukatAmount price){
		super(ID, min, protectionFactor, weight, price);
		maximumPrice =  new DukatAmount(maxPrice);
		maximumValue = max;
	}

	/* (non-Javadoc)
	 * @see Item#getNextID(long)
	 */
	@Override
	protected long getNextID(long ID) {
		return MathHelp.greatestPrimeNumberUnder(ID);
	}

	/**
	 * Checks whether a given ID is valid for this item.
	 * 
	 * @param 	ID
	 * 			The ID to be checked.
	 * @return	True if the ID is a prime number.
	 */
	@Override
	@Raw
	public boolean isValidID(long ID) {
		return MathHelp.isPrime(ID);
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Enhancer#getMaximumValue()
	 */
	@Override
	@Immutable
	public int getMaximumValue() {
		return maximumValue;
	}

	/**
	 * Checks whether a given value can be a potential maximum value for this armor.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * @return 	True if the value is larger than or equal to 1
	 * 			and smaller than or equal to 100.
	 * 			| result == value >= 1 && value <= 100
	 */
	@Override 
	public  boolean canHaveAsMaximumValue(int value){
		return value >= 1 && value <= 100;
	}
	
	/**
	 * Variable registering the maximum protection factor for this armor.
	 */
	private final int maximumValue;

	/* (non-Javadoc)
	 * @see rpgGame.Enhancer#canHaveAsValue(int)
	 */
	@Override
	public boolean canHaveAsValue(int value) {
		return value >= getMinimumValue() && value <= getMaximumValue();
	}
	
	/**
	 *  Returns the standard price for an armor with a given value, a given
	 *  maximum protection and a maximum dukat amount.
	 * 
	 * @param 	value
	 * 			The current value for the armor.
	 * @param 	maxProtection
	 * 			A maximum protection value.
	 * @param 	maxDukatAmount
	 * 			The maximum dukat amount
	 * @return	The result will be a dukat amount with a positive value.
	 * 			The value is calculated as the product of the amount of
	 * 			maximum dukats and the value devided by the maximum 
	 * 			protection.
	 */
	public static DukatAmount getStandardPrice(int value, int maxProtection, DukatAmount maxDukatAmount){
		int amount = maxDukatAmount.getAmount();
		return new DukatAmount((int)(amount * value / maxProtection));
	}
	
	/**
	 * Returns the maximum value of this armor in amount of dukats.
	 */
	private DukatAmount getMaximumPrice(){
		return maximumPrice;
	}
	
	/**
	 * Variable registering the maximum value of this armor in amount of dukats.
	 */
	private final DukatAmount maximumPrice;

	/* (non-Javadoc)
	 * @see rpgGame.Enhancer#updatePrice()
	 */
	@Override
	protected void updatePrice() {
		setPrice(getStandardPrice(getValue(), getMaximumValue(), getMaximumPrice()));
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Item#canHaveAsPrice(rpgGame.DukatAmount)
	 */
	protected boolean canHaveAsPrice(DukatAmount price){
		return price.getAmount() <= 1000;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maximumPrice.hashCode();
		result = prime * result + maximumValue;
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
		Armor other = (Armor) obj;
		if (maximumPrice != other.maximumPrice) {
			return false;
		}
		if (maximumValue != other.maximumValue) {
			return false;
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Armor [" 
				+ "Price= "+ getPrice().toString() +"/"+getMaximumPrice().toString()
				+", " + super.toString()
				+"]";
	}
	
}
