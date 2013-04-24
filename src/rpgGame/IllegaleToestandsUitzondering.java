package rpgGame;

public class IllegaleToestandsUitzondering extends RuntimeException {

	/**
	 * Initializes a new IllegaleToestandsUitzondering.
	 *  
	 * @author Antoine Snyers & Pieter Willemsen
	 * @version 3.0 
	 */
	public IllegaleToestandsUitzondering() {
	}
	
	/**
	 * Initializes a new IllegaleToestandsUitzondering involving a reason
	 * 	why the exception occurred.
	 * 
	 * @param 	reason
	 * 			The reason why the exception is thrown.
	 */
	public IllegaleToestandsUitzondering(String reason) {
		super(reason);
	}

	/**
     * The Java API strongly recommends to explicitly define a version
     * number for classes that implement the interface Serializable.
     * At this stage, that aspect is of no concern to us. 
     */
    private static final long serialVersionUID = 2003001L;

}
