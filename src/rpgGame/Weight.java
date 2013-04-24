package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weight involving a numeral and a unit.
 * 
 * @invar	The unit of each weight is a valid unit.
 * 			| isValidUnitOfWeight(getUnitOfWeight)
 * @invar	The numeral is always a positive number
 * 			| getNumeral() >= 0
 * 
 * @author  Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Weight implements Comparable<Weight>{
	
	/**
	 * Initialize this new weight with given numeral and given unit.
	 * 
	 * @param 	number
	 * 			The numeral for the new weight.
	 * @param 	unit
	 * 			The unit of the new weight.
	 * @post	The numeral of this new weight is equal to the given numeral.
	 * 			|new.getNumeral() == number
	 * @post	The unit of weight of the new weight is equal to the given unit.
	 * 			|new.getUnitOfWeight() == unit
	 * @throws 	IllegalArgumentException
	 * 			The given unit is not a valid unit for any weight
	 * 			| !isValidUnitOfWeight(unit)
	 */
	public Weight(double number, UnitOfWeight unit) throws IllegalArgumentException {
		this.numeral = number;
		if(!isValidUnitOfWeight(unit))
			throw new IllegalArgumentException("Invalid unit of weight");
		this.unit = unit;
	}
	
	/**
	 * Initialize this new weight with given numeral and KILOGRAM as unit.
	 * 
	 * @param 	number
	 * 			The numeral for this new weight.
	 * @effect	This new weight is initialized in the same way as a new weight
	 * 			would be initialized with the most extended constructor involving
	 * 			the given numeral and the unit KILOGRAM. 
	 * 			| this(numeral,UnitOfWeight.KILOGRAM)
	 */
	public Weight(double number)throws IllegalArgumentException {
		this(number, UnitOfWeight.KILOGRAM);
	}
	
	/**
	 * Return the numeral of this weight. 
	 */
	@Basic @Raw @Immutable
	public double getNumeral(){
		return numeral;
	}
	
	/**
	 * Variable referencing the numeral of this weight.
	 */
	private final double numeral;
	
	
	
	/**
	 * Return the unit of this weight.
	 */
	@Basic @Raw @Immutable
	public UnitOfWeight getUnitOfWeight() {
		return unit;
	}
	/**
	 * Checks whether the given unit is a valid unit for any weight.
	 * 
	 * @param 	unit
	 * 			The unit to check
	 * @return	True if and only if the given currency is effective
	 * 			| result == (unit != null) 
	 */
	public static boolean isValidUnitOfWeight(UnitOfWeight unit) {
		return (unit != null);
	}
	
	/**
	 * Rerun a weight that has the same value as this weight expressed
	 * in the given unit
	 * 
	 * @param 	unit
	 * 			The unit of weight to convert to.
	 * @return	The resulting weight has the given unit as its unit of weight.
	 * 			| result.getUnitOfWeight() == unit
	 * @return	The numeral of the resulting weight is equal to the numeral of
	 * 			this weight multiplied with the conversion rate form the unit of
	 * 			weight of this weight to the given unit.
	 * 			| let 
	 * 			|	exchangeRate = this.getUnitOfWeight().toConversionRate(unit)
	 * 			|	numeralInUnit = this.getNumeral * exchangeRate
	 * 			| in
	 * 			|	result.getNumeral() == numeralInUnit
	 * @throws 	IllegalArgumentException
	 * 			The given unit of weight is not valid.
	 * 			|!isValidUnitOfWeight(unit)
	 */
	public Weight toUnitOfWeight(UnitOfWeight unit) throws IllegalArgumentException {
		if(!isValidUnitOfWeight(unit))
			throw new IllegalArgumentException("Non-effective unit");
		if(this.getUnitOfWeight() == unit)
			return this;
		double convertionRate = this.getUnitOfWeight().toConversionRate(unit);
		double numeralInUnit = getNumeral() * convertionRate;
		return new Weight(numeralInUnit,unit);
	}
	
	/**
	 *Variable referencing the unit of this weight.
	 */
	private final UnitOfWeight unit;
	
	/**
	 * Compute the sum of this weight and the other weight.
	 * 
	 * @param 	other
	 * 			The other weight to add.
	 * @return	The resulting weight is effective.
	 * 			| result != null;
	 * @return	The resulting weight has the same unit as this weight.
	 * 			| result.getUnitOfWegith() == this.getUnitOfWeight()
	 * @return	If both weights have the same unit of weight. The numeral
	 * 			of the resulting weight will be the sum of the two numerals.
	 * 			| if(this.getUnitOfWeight() == other.getUnitOfWeight)
	 * 			|	then result.getNumeral == this.getNumeral + other.getNumeral
	 * @return	If both weights use different units of weight. The resulting weight
	 * 			will be equal to the sum of this weight and the other weight 
	 * 			expressed in the unit of this weight.
	 * 			if(this.getUnitOfWeight() != other.getUnitOfWeight)
	 * 			|	then result == this.add(other.toUnitOfWeight(getUnitOfWeight))
	 * @throws 	IllegalArgumentException 
	 * 			The other weight is not effective
	 * 			| other == null
	 */
	public Weight add(Weight other) throws IllegalArgumentException {
		if(other == null)
			throw new IllegalArgumentException("Non-effective unit");
		if(getUnitOfWeight() == other.getUnitOfWeight()){
			return new Weight(getNumeral() + other.getNumeral(), getUnitOfWeight());
		}
		return add(other.toUnitOfWeight(getUnitOfWeight()));
	}
	
	
	/**
	 * Compute the difference of this weight and the other weight.
	 * 
	 * @param 	weight
	 * 			The other weight to retract.
	 * @return	The resulting weight is effective.
	 * 			| result != null;
	 * @return	The resulting weight has the same unit as this weight.
	 * 			| result.getUnitOfWegith() == this.getUnitOfWeight()
	 * @return	If both weights have the same unit of weight. The numeral
	 * 			of the resulting weight will be the difference of the two numerals.
	 * 			| if(this.getUnitOfWeight() == other.getUnitOfWeight)
	 * 			|	then result.getNumeral == Math.abs(this.getNumeral - other.getNumeral)
	 * @return	If both weights use different units of weight. The resulting weight
	 * 			will be equal to the sum of this weight and the other weight 
	 * 			expressed in the unit of this weight.
	 * 			if(this.getUnitOfWeight() != other.getUnitOfWeight)
	 * 			|	then result == this.retract(other.toUnitOfWeight(getUnitOfWeight))
	 * @throws 	IllegalArgumentException 
	 * 			The other weight is not effective
	 * 			| other == null
	 */
	public Weight difference(Weight weight) throws IllegalArgumentException {
		if(weight == null)
			throw new IllegalArgumentException("Non-effective unit");
		if(getUnitOfWeight() == weight.getUnitOfWeight()){
			return new Weight(Math.abs(getNumeral() - weight.getNumeral()), getUnitOfWeight());
		}
		return difference(weight.toUnitOfWeight(getUnitOfWeight()));
	}
	
	/**
	 * Compute the product of this weight with the given factor.
	 * 
	 * @param 	factor
	 * 			The factor to multiply with.
	 * @return	The resulting weight is effective.
	 * 			| result != null
	 * @return	The resulting weight has the same unit of weight as the current weight
	 * 			| restul.getUnitOfWeight() == this.getUnitOfWeight()
	 * @return	The numeral of the resulting weight is equal to the product of the numeral
	 * 			of this weight and the given factor.
	 * 			|result.getNumeral() == this.getNumeral * factor
	 * @throws 	IllegalArgumentException
	 * 			The product is larger then the maximum value of the double that is used
	 * 			to store the numeral of the new weight.
	 * 			| this.getNumeral()*factor > Double.MAX_VALUE
	 */
	public Weight multiply(int factor) throws IllegalArgumentException {
		double newNumeral = this.getNumeral()*factor;
		if(newNumeral > Double.MAX_VALUE)
			throw new IllegalArgumentException("double overflow");
		return new Weight(this.getNumeral()*factor, this.getUnitOfWeight());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Weight other) throws IllegalArgumentException{		
		Weight otherInGram = other.toUnitOfWeight(UnitOfWeight.GRAM);
		Weight thisInGram = this.toUnitOfWeight(UnitOfWeight.GRAM);

		double numeralThis = thisInGram.getNumeral();
		double numeralOther = otherInGram.getNumeral();
		
		if(Math.abs(numeralThis - numeralOther) < 0.005)
			return 0;
		else if(numeralThis > numeralOther)
			return 1;
		else return -1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(numeral);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weight other = (Weight)obj;
		if (Double.doubleToLongBits(numeral) != Double
				.doubleToLongBits(other.numeral))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return  "" + getNumeral() + getUnitOfWeight().getSymbol();
	}
}
