package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * An emumeration introducing different units of weight used to express
 * the weight of something.
 * 	In its current form the class only supports pound,
 *  gram and kilogram.
 *  
 * @author  Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
@Value
public enum UnitOfWeight {
	POUND("lb"), GRAM("g"), KILOGRAM("kg");
	
	/**
	 * Initialize this unit of weight with the given symbol.
	 *
	 * @param 	symbol
	 * 			The symbol for this new unit of weight.
	 * @post	The symbol for this new currency is equal to the given symbol.
	 * 			| new.getSymbol() = symbol
	 */
	private UnitOfWeight(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Return the symbol for this unit of weight.
	 */
	@Basic @Raw @Immutable
	public String getSymbol(){
		return this.symbol;
	}
	
	/**
	 * Variable storing the symbol for this unit of weight.
	 */
	private final String symbol;
	
	/**
	 * Returning the value of 1 unit of this unit of weight in the other unit of weight
	 * 
	 * @param	other
	 * 			The unit of weight to convert to.
	 * @return	The resulting conversion rate is effective.
	 * 			|result != null
	 * @return	the resulting conversion rate will be positive.
	 * 			| result >=0
	 * @return	If this unit is the same as the other unit than the 
	 * 			conversion rate will be one.
	 * 			| when ( this == other)
	 * 			|	then result == 1
	 * @return	The resulting conversion rate is the inverse of the conversion rate
	 * 			from the other unit of weight to this unit of weight
	 * 			| result == 1 / other.toConversionRate(this)
	 * @throws	IllegalArgumentException
	 * 			The other unit is not effective.
	 * 			| other == null
	 */
	public double toConversionRate(UnitOfWeight other)throws IllegalArgumentException {
		if(other == null)
			throw new IllegalArgumentException("Non effective unit of weight");
		if(conversionRates[this.ordinal()][other.ordinal()] ==  0) {
			conversionRates[this.ordinal()][other.ordinal()] =
					1/conversionRates[other.ordinal()][this.ordinal()];
		}
		return conversionRates[this.ordinal()][other.ordinal()];
	}
	
	
	/**
	 * Variable referencing a two-dimensional array registering conversion rates
	 *  between units of weight. The first level is indexed by the ordinal number
	 *  of the unit of weight to convert from. The ordinal number to convert to is
	 *  is used to index the second level.
	 */
	private static double[][] conversionRates = new double[3][3];
	
	static {
		conversionRates[POUND.ordinal()][POUND.ordinal()] = 1;
		conversionRates[POUND.ordinal()][GRAM.ordinal()] = 453.6;
		conversionRates[POUND.ordinal()][KILOGRAM.ordinal()] = 0.4536;
		conversionRates[GRAM.ordinal()][GRAM.ordinal()] = 1;
		conversionRates[GRAM.ordinal()][KILOGRAM.ordinal()] = 0.001;
		conversionRates[KILOGRAM.ordinal()][KILOGRAM.ordinal()] = 1;
	}
}
