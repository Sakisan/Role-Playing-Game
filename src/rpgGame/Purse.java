package rpgGame;

/**
 * A class of purses extending the class bag and this class van only
 *  carry dukats.
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Purse extends Bag {
	
	/**
	 * Initializes a new purse with a given amount of dukats, a given capacity
	 * and a given weight.
	 * 
	 * @param 	dukatAmount
	 * 			The amount of dukats to be contained by the newly created purse.
	 * @param 	capacity
	 * 			The initial capacity for this new purse.
	 * @param	bagsWeight
	 * 			The weight for this new purse.
	 * @post	The initial capacity of this new purse is equal to the given one.
	 * @effect	A new bag is initialized with bagsWeight as its weight.
	 * @effect	The given amount of dukats is added to this new purse.
	 */
	public Purse(int dukatAmount, DukatAmount capacity, Weight bagsWeight){
		super(bagsWeight);
		setCapacity(capacity);
		addDukats(dukatAmount);
	}
	
	/**
	 * Initializes a new purse with a given amount of dukats and a given capacity.
	 * 
	 * @param 	dukatAmount
	 * 			The amount of dukats to be contained by the newly created purse.
	 * @param 	capacity
	 * 			The initial capacity for this new purse.
	 * @effect	Initializes this new purse the same it would be initialized using
	 * 			the most extended constructor involving the given amount of dukats 
	 * 			as the amount of dukats,the given initial capacity as the capacity,
	 * 			and a weight of 1 kg as its weight.
	 */
	public Purse(int dukatAmount, DukatAmount capacity){
		this(dukatAmount, capacity, new Weight(1));
	}
	
	/**
	 * Initializes a new purse with a given amount of dukats.
	 * 
	 * @param 	dukatAmount
	 * 			The amount of dukats to be contained by the newly created purse.
	 * @effect	Initializes this new purse the same it would be initialized using
	 * 			the most extended constructor involving the given amount of dukats 
	 * 			as the amount of dukats, a capacity of 100 dukats as the capacity,
	 * 			and a weight of 1 kg as its weight.
	 */
	public Purse(int dukatAmount) {
		this(dukatAmount, new DukatAmount(100), new Weight(1));
	}

	/**
	 * Initializes a new purse with a given amount of dukats.
	 * 
	 * @effect	Initializes this new purse the same it would be initialized using
	 * 			the most extended constructor involving zero as the amount of dukats 
	 * 			and a capacity of 100 dukats as the capacity,
	 * 			and a weight of 1 kg as its weight.
	 */
	public Purse() {
		this(0,  new DukatAmount(100), new Weight(1));
	}

	/* (non-Javadoc)
	 * @see rpgGame.Item#getNextID(long)
	 */
	@Override
	protected long getNextID(long ID) {
		long next = nextID;
		long sum = previousID + nextID;
		previousID = nextID;
		nextID = sum;
		if(nextID < 0){ //overflow
			previousID = 0;
			nextID = 1;
		}
		return next;
	}

	/* (non-Javadoc)
	 * @see rpgGame.Item#isValidID(long)
	 */
	@Override
	public boolean isValidID(long ID) {
		return MathHelp.isFibonacciNumber(ID);
	}
	
	/**
	 * Variable registering the last ID number that was used to create a new weapon.
	 */
	private static long previousID = 0;
	
	/**
	 * Variable registering the next ID number to be used to create a new weapon.
	 */
	private static long nextID = 1;
	
	/* (non-Javadoc)
	 * @see rpgGame.Bag#dukatsToFill()
	 */
	public DukatAmount dukatsToFill(){
		DukatAmount temp = new DukatAmount(getCapacity().getAmount());
		temp.retract(getDukats().getAmount());
		return temp;
	}
	
	/**
	 * Returns the capacity of this purse.
	 */
	public DukatAmount getCapacity(){
		return capacity;
	}
	
	/**
	 * Sets the capacity of this bag to a given amount of dukats.
	 * 
	 * @param 	capacity
	 * 			The capacity to set.
	 * @post	The new capacity for this bag is the given capacity
	 * 			if it's a valid capacity.
	 */
	public void setCapacity(DukatAmount capacity){
		if(canHaveAsCapacity(capacity))
			this.capacity = capacity;
	}
	
	/**
	 * Checks whether this purse can have a given amount of dukats as its capacity.
	 * 
	 * @param	amount
	 * 			The amount to check
	 * @return	True if the amount is bigger than zero and less than or equal to
	 * 			the maximal DukatAmount.
	 */
	private boolean canHaveAsCapacity(DukatAmount amount){
		return amount.getAmount() > 0 && amount.getAmount() <= amount.getMaximum() ;
	}
	
	/**
	 * Variable referencing the value for the capacity of this backpack.
	 */
	private DukatAmount capacity = new DukatAmount();
	
	/**
	 * Add a given amount of dukats to this purse.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to add.
	 * @pre		amount is a positive number.
	 * @post	If the resulting amount of dukats isn't over its maximum,
	 * 			and if the resulting content doesn't exceed the capacity of this bag
	 * 			then the new amount of dukats contained by this bag
	 * 			is the sum of the previous amount and the given amount. 
	 * @post	If the resulting content exceeds the capacity of this purse,
	 * 			then the purse will be irreparably torn and it won't be able to
	 * 			contain dukats anymore.
	 */
	public void addDukats(int amount){
		if(!canAddDukats(amount))
			tear();
		else getDukats().add(amount);
	}
	
	/**
	 * Checks whether a given amount of dukats can be added to this purse.
	 * 
	 * @param 	amount
	 * 			The amount of dukats to be checked.
	 * @pre		amount is a positive number.
	 * @return	True if the sum of the dukats to be added and the dukats that already
	 * 			are in this purse doesn't exceed this purses capacity.
	 * @return	False if this purse is torned.
	 */
	public boolean canAddDukats(int amount){
		return !isTorned() &&
					getDukats().getAmount() + amount <= getCapacity().getAmount();
	}
	
	/**
	 * Returns the torned state of this purse.
	 */
	public boolean isTorned(){
		return torned;
	}
	
	/**
	 * Tears this purse. This makes this purse irreparably unable to contain any dukats anymore.
	 */
	private void tear(){
		torned = true;
		empty();
	}
	
	/**
	 * Variable registering the torned state of this purse.
	 */
	private boolean torned;
	
	/**
	 * Sets the amount of dukats in this purse to zero. All the dukats that used to be
	 * in this purse are dropped on the ground.
	 */
	private void empty(){
		int amount = getDukats().getAmount();
		retractDukats(amount);
		World.world.addDukats(new DukatAmount(amount));
	}
	
	/**
	 * Retracts all dukats in this purse and adds them to a given purse.
	 * Hereafter the purse will be empty and will be dropped on the ground.
	 * 
	 * @param 	purse
	 * 			The purse where the dukats are added.
	 */
	public void emptyIn(Purse purse){
		DukatAmount dukats = getDukats();
		retractDukats(dukats.getAmount());
		purse.addDukats(dukats.getAmount());
		this.drop();
	}

	/* (non-Javadoc)
	 * @see rpgGame.Item#canHaveAsPrice(rpgGame.DukatAmount)
	 */
	@Override
	protected boolean canHaveAsPrice(DukatAmount price){
		return price.getAmount() == 0;
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
		result = prime * result + (torned ? 1231 : 1237);
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
		Purse other = (Purse) obj;
		if (capacity == null) {
			if (other.capacity != null) {
				return false;
			}
		} else if (!capacity.equals(other.capacity)) {
			return false;
		}
		if (torned != other.torned) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Purse [Is torn="+ isTorned() 
				+ ", Amount of dukats()=" + getDukats()+"/"+dukatsToFill() 
				+ ", "+ super.toString() 
				+ "]";
	}
}
