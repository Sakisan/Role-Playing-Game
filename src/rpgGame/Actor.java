package rpgGame;

import be.kuleuven.cs.som.annotate.*;

/**
 * A abstract class of actors involving a name, 
 * 	a strength, items and hit points.
 * 
 * @invar	The Actor's name must be valid.
 * 			| isValidName(getName())
 * @invar	The actor must have a legal amount of hit points.
 * 			| isValidHitPoints(getHitPoints());
 * @invar	The actor's strength must be larger than or equal to zero.
 * 			| getStrength() >= 0
 * @invar 	The actor's protection must be larger than or equal to zero.
 * 			| getProtection() >= 0
 * @invar	The actor's items always will be valid.
 * 			| hasValidItems()	
 * @invar	The actor price always will be positive.
 * 			| getPrice() >=0
 * @invar	The actor weight always will be positive.
 * 			| getWeight().compare(new Weight(0)) >=0
 * @invar	The actor weight always will be smaller than or equal to his capacity.
 * 			| getWeight().compare(getCapacity) <=0
 * 
 * @author Pieter Willemsen and Antoine Snyers
 * @version 3.0
 */
public abstract class Actor implements Holder {
	
	/**
	 * Initializes an actor with a given name, a strength and hit points.
	 * 
	 * @param 	name
	 * 			The name for the new actor.
	 * @param	hitPoints
	 * 			The value for the new actor's hit points.
 	 * @Param 	strength
	 * 			The value for the strength for this new actor.
	 *
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| hitPoints > 0
	 * @post	This actor's new hit points are smaller than or equal to the given hitPoints.
	 * 			| new.getHitpoints() <= hitPoints
	 * @post	The new value for the fighting status of the actor is false.
	 * 			| new.isFighting() == false
	 * @effect 	The actor's maximum hit points are set with the method 
	 * 			setMaxHitPoints
	 * 			| setMaxHitPoitns(hitPoints)
	 * @effect	The actor's hit points are set with the method setHitPoints.
	 * 			| setHitPoints(hitPoints)
	 * @effect	The new name of this actor is equal to the given name.
	 * 			| setName(name)
	 * @effect 	The actor's strength is set with the method setStrength.
	 * 			| setStrength(strength)
	 */
	@Raw 
	protected Actor(String name,int hitPoints, double strength) 
			throws IllegalArgumentException, IllegaleToestandsUitzondering{
		setName(name);
		setMaxHitpoints(hitPoints);
		setHitPoints(hitPoints);
		setFighting(false);
		setStrength(strength);
	}
	
	/**
	 * Return the actor's name.
	 */
	@Basic 
	@Raw
	public String getName() {
		return Name;
	}

	/**
	 * Set the name of this actor once to a given String. 
	 *
	 * @param 	name 
	 * 			The new name for this actor.
	 * @post	The actor's new name is equal to the given name.
	 * 			| this.getName().equals(name)
	 * @throws	IllegalArgumentException
	 * 			When name is not a valid name
	 * 			| !isValidName(name)
	 * @throws	IllegaleToestandsUitzondering
	 * 			When this actor already has a name you can't 
	 * 			change it anymore.
	 * 			| getName() != null
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException, 
						IllegaleToestandsUitzondering {
		if(getName() != null)
			throw new IllegaleToestandsUitzondering("The actor already has a name.");
		if(!isValidName(name)) {
			throw new IllegalArgumentException("Name isn't valid");
		}
		Name = name;
	}
	
	
	/**
	 * Checks whether a given name complies with the name rules.
	 * 
	 * @param 	name
	 * 			The name to be checked
	 * @return	Returns false if the name isn't effective.
	 *			| if(name == null)
	 *			|	then result == false
	 * @return	false if the name doesn't comply with the rules.
	 * 			The rules are : 
	 * 			The name must start with a capital letter.
	 *			The name can only contain letters, spaces, colon 
	 *					and apostrophes.
	 * 			| if (!name.matches([A-Z][A-Za-z': ]*))
	 * 			|	then result == false
	 */
	@Model
	protected abstract boolean isValidName(String name);
	
	/**
	 * String containing the actor's name.
	 */
	private String Name;
	
	/**
	 * Return this actor's amount of hit points
	 */
	@Basic
	@Raw
	public int getHitPoints() {
		return hitPoints;
	}
	
	/**
	 * A test that checks if the given amount of hit points is a valid amount for this actor.
	 * 
	 * @param 	hitPoints
	 * 			An amount of hit points that we want to test.
	 * @return	When the amount of hit points is larger than or equal 
	 * 			to zero but smaller than or equal to the maximum amount of
	 * 			hit points and the actor isn't fighting and the given amount of 
	 * 			hit points is prime then return true. 
	 * 			| result == (hitPoints > 0) && (hitPoints <= getMaxHitpoints())
	 * 			 			 && !isFighting() && MathHelp.isPrime(hitPoints)
	 * @return 	When the amount of hit points is larger than or equal 
	 * 			to zero but smaller than or equal to the maximum amount of 
	 * 			hit points and the actor is fighting then return true.
	 * 			| result ==  (hitPoints > 0) && (hitPoints <= getMaxHitpoints())
	 * 						 && isFighting()
	 */
	public boolean isValidHitPoints(int hitPoints) {
		if(hitPoints >= 0 && hitPoints <= getMaxHitpoints()){
			if(!isFighting()) {
				if(MathHelp.isPrime(hitPoints)) {
					return true;
				}
				return false;
			}	
			return true;
			
		}
		return false;
	}
	
	/**
	 * Set this actor's hit points to a given number.
	 * 
	 * @param 	hitPoints
	 *			The new amount of hit points for this actor.
	 * @pre		The amount of hit points has to be a integer number smaller than or equal to
	 * 			the actor's maximum hit points.
	 * 			|(hitPoints <= getMaxHitpoints()).
	 * @pre		The amount of hit points has to be larger than or
	 * 			or equal to zero
	 * 			| hitPoints >=0
	 * @post	If the actor is fighting and the given hitpoints is greater than zero his amount 
	 * 			of hit points is the given number.
	 * 			| when (isFighting() && hitPoints > 0)
	 * 			|		then new.getHitPoints() == hitPoints
	 * @post	If the amount of hit points is less than or equal to zero. Then this actor died and his
	 * 			new value for hit points will be zero.
	 * 			| when( hitPoints <= 0)
	 * 			|	 then new.getHitpoints() == 0
	 * @post 	If the actor isn't fighting then his amount of hit points is the greatest prime 
	 * 			number under the given number.
	 * 			| when !isFighting()
	 * 			|   then new.getHitpoints() == MathHelp.greatestPrimeNumberUnder(hitPoints)
	 * 
	 */
	@Model
	protected void setHitPoints(int hitPoints) {
		assert(hitPoints <= getMaxHitpoints());
		assert(hitPoints >=0);
		if(hitPoints < 0){
			this.setHitPoints(0); 
		}
		else if(!isFighting()){
			hitPoints = MathHelp.greatestPrimeNumberUnder(hitPoints);
			this.hitPoints = hitPoints;
		}
		else {
			this.hitPoints = hitPoints;
		}
	}

	/**
	 * Variable registering this actor's current hit points;
	 */
	private int hitPoints;
	
	/**
	 * Return this actor's maximum hit points.
	 */
	@Raw
	@Basic
	public int getMaxHitpoints() {
		return maxHitPoints;
	}

	/**
	 * Set this actor's maximum hit points to a given number.
	 *
	 * @param	maxHitPoints
	 * 			The new maximum amount of hit points for this actor.
	 * @pre		The maximum amount of hit points must be greater than or equal to his current 
	 * 			amount of hit points.
	 * 			| maxHitPoint >= getHitPoints()
	 * @pre		The maximum amount of hit points must be a natural number larger than zero.
	 * 			| maxHitPoints > 0 
	 * @post	This actor's new maximum hit points are equal to the given number.
	 * 			| new.getMaxHitPoints() = maxHitPoints
	 */
	@Raw
	public void setMaxHitpoints(int maxHitPoints) {
		assert(maxHitPoints >= getHitPoints());
		assert(maxHitPoints > 0);
		this.maxHitPoints = maxHitPoints;
	}

	
	/**
	 * Variable registering this Actor's maximum hit points.
	 */
	private int maxHitPoints;
	
	/**
	 * An inspector that returns the actor's fighting status.
	 */
	@Basic
	@Raw
	public boolean isFighting() {
		return this.isFighting;
	}

	/**
	 * Change the actor's fighting status to a given status.
	 * 
	 * @param 	fight 
	 * 			The status to set the new fighting status to.
	 * @post	The new value for the fighting status of the actor is equal to the given flag.
	 * 			| new.isFighting() == fight
	 * @effect	If the actor new fighting status is false then his hit points must be prime.
	 * 			| when !fight
	 * 			|	then setHitpoints(getHitpoints())
	 */
	@Raw
	private void setFighting(boolean fight) {	
		this.isFighting = fight;
		if (!fight){
			setHitPoints(getHitPoints());
		}
	}
	/**
	 * Variable that keeps track if the actor is fighting or not.
	 */
	private boolean isFighting;
	

	/**
	 * Return the average strength for all the actors.
	 * 
	 * @return	This method always will return 10.00.
	 * 			| result == 10.00
	 */
	@Immutable
	public static double getAverageStrength() {
		return 10.00;
	}
	
	/**
	 * Return this actor's strength.
	 * 	
	 * @effect	The resulting double will be the intern double
	 * 			rounded to a scale of two.
	 *  		| result == MathHelp.convertPrecisionDouble(InternValueForStrength,2);
	 */
	@Basic
	public double getStrength() {
		return MathHelp.convertPrecisionDouble(strength,2);
	}
	
	/**
	 * Multiply this actor's strength with a given integer.
	 * 
	 * @param 	factor
	 * 			The factor with which this actor's strength will be multiplied.
	 * @post	This actor's new strength is equal to his strength times the 
	 * 			absolute value of the given factor.
	 * 			| new.getStrength() == getStrength() * Math.abs(factor)
	 * @post	If this would result in a infinity in double precision, then nothing happens.
	 * 			|  when(!(Math.abs(getStrength()*factor) > Double.MAX_VALUE))
	 * 			|	 then return;
	 */
	public void multiplyStrength(int factor) {
		factor = Math.abs(factor);
		if(!(getRealStrength()*factor > Double.MAX_VALUE))
			setStrength(getRealStrength()*factor);
	}

	/**
	 * Divide this actor's strength with a given integer.
	 * 
	 * @param 	factor
	 * 			The factor with which this actor's strength will be divided.
	 * @post	This actor's new strength is equal to his strength divided 
	 * 			by the absolute value of the given factor.
	 * 			This result is rounded down.
	 * 			| new.getStrength() == getStrength() / Math.abs(factor)
	 * @post	If this would result in a underflow in double precision, then nothing happens.
	 * 			| when (!(Math.abs(getStrength()*factor) < Double.MAX_VALUE))
	 * 			|	then return;
	 * @post	If the factor is zero, then nothing happens.
	 * 			| when (factor == 0)
	 * 			|	then return;
	 */
	public void divideStrength(int factor) {
		factor = Math.abs(factor);
		if(!(getRealStrength()*factor < Double.MIN_VALUE)
				&& factor != 0)
			setStrength(getRealStrength()/(double)factor);
	}

	/**
	 * Set this actor's strength to a given number.
	 * 
	 * @param 	strength
	 * 			The new strength for this actor.
	 * @post	This actor's strength is equal to the absolute value of the given number.
	 * 			| new.getStrength() == Math.abs(strength)
	 */
	@Raw
	@Model
	private void setStrength(double strength) {
		this.strength = Math.abs(strength);
	}
	
	/**
	 * Return the real actor's strength.
	 */
	private double getRealStrength() {
		return this.strength;
	}
	
	/**
	 * Variable registering this actor's strength.
	 */
	private double strength;
	
	/**
	 * Returns the damage that this actor can deal in combat.
	 */
	protected abstract int getDamage();

	/**
	 * Returns this actor's capacity.
	 *  The actor's capacity determines how much the actor can carry.
	 * 	The capacity is directly in function of the actor's strength.
	 * 	| result ~ getStrength()
	 * 
	 * @effect	The result will be the same as executing the 
	 * 			method getCapacity with argument the actor's
	 * 			strength.
	 * 			| result == getCapacity(getStrenght())
	 */
	public Weight getCapacity() {
		return getCapacity(getStrength());
	}
	
	/**
	 * Returns a capacity for the given strength.
	 * 
	 * @param 	strength
	 * 			The strength for which the capacity must be definite.
	 * @return	The result will be a weight with a numeral between or 
	 * 			equal to zero and the maximum value for double.
	 * 			|0 <= result.getNumeral() <= Double.MAX_VALUE
	 * @return	The unit of weight of the result will be kilogram.
	 * 			| result.getUnitofWeight() == UnitOfWeight.KILOGRAM
	 * @throws 	IllegalArgumentException
	 * 			If the given strength is negative.
	 * 			| strength < 0
	 */
	public abstract Weight getCapacity(double strength)
		throws IllegalArgumentException;

	/**
	 * Return the protection factor for this actor. 
	 *  The protection factor is an indicator for the actor's ability block an attack.
	 *
	 * @return	The result will be positive.
	 * 			| result >= 0
	 */
	public abstract double getProtection();

	/**
	 * Hit the given opponent one time.
	 * 
	 * @param 	other
	 * 			The actor to be hit by this actor.
	 * @post 	This actor's hit points can increase and he will be fighting.
	 *			| new.getHitPoints() >= getHitPoints()
	 *			| new.isFighting() == true;
	 * @post	There are 3 cases. Which case applies can't be known beforehand.
	 *		
	 *			1.blocked hit
	 *			If the other actor blocks the attack then both actors will end in
	 *			fighting status and the hit points won't change.
	 *			| (new other).isFighting() == true
	 *			| (new other).getHitPoints() == other.getHitPoints()
	 *			| new.getHitPoints() == getHitpoints()
	 *		
	 *			2.non fatal hit
	 *			If the other actor gets hit but doesn't die then the other actor's 
	 *			hit points can change in any direction but still have to be valid.
	 *			| (new other).isFighting() == true
	 *			| (new other).isValidHitpoints == true
	 *			| new.getHitPoints() == getHitpoints()
	 *
	 *			3.Died
	 *			If the other actor dies. The other actor's hit points 
	 *			will change to zero and the method heal() and 
	 *			loot(other) are triggered. The other actor
	 *			is terminated.
	 * 			| (new other).isFighting() == true
	 *			| (new other).getHitpoints() == 0;
	 *			| (new other).isTerminate() == true
	 * @effect	In case 3 these methods will be triggered
	 *			|loot(other)
	 *			| heal()
	 * @throws	IllegalArgumentException
	 *			Throws the exception when the method canFightOpponent returns false
	 *			| !canFightOpponent(other)
	 */
	public void hit(Actor other) throws IllegalArgumentException {
		if(!canFightOpponent(other))
			throw new IllegalArgumentException("this actor can't fight against the given opponent");

		other.setFighting(true);
		this.setFighting(true);
		int random = luckFactorForHit();
		if(random >= other.getProtection()){
			int damage = getDamage();
			//in some cases the damage can be negative what means that the actor will get hit points in stead of lose hit points
			if(damage < 0 && (other.getHitPoints()>=other.getMaxHitpoints()+damage)) {//damage is negative the actor will gain healed  
				other.setHitPoints(other.getMaxHitpoints());
			}
			else { 
				other.setHitPoints(other.getHitPoints()-damage);
			}
			if(other.getHitPoints() == 0) { // the other opponent died
				setHitPoints(getHitPoints()+heal());
				loot(other);
			}
		}
	}
	
	/**
	 * The result of this method is a random number that is used in the method hit.
	 * 
	 * @return	The result will be positive.
	 * 			| result => 0
	 */
	protected abstract int luckFactorForHit();
	
	/**
	 * A test to determine if this actor can fight against the given actor.
	 * 
	 * @param 	opponent
	 * 			The opponent were this actor wants to fight against.
	 * @return	If the opponent isn't effective or when the opponent is
	 * 			terminated or when the opponent is this actor the result 
	 * 			will be false.
	 * 			| when (opponent == null) || (opponent.isTerminted()) || (opponent = this)
	 * 			|  then result == true
	 */
	@Model
	protected boolean canFightOpponent(Actor opponent) {
		return (opponent != null && !opponent.isTerminated() && (opponent != this));
	}

	/**
	 * A method that is triggered by the method hit and will
	 * 	return a number representing the amount of hit points
	 * 	this actor will heal.
	 * 
	 * @return	The result is positive.
	 * 			| result >= 0
	 */
	@Model
	protected abstract int heal();
	
	/**
	 * A method that determines if the given actor wants to
	 * 	hold the given item.
	 * 
	 * @param 	item
	 * 			An item that you want to test.
	 * @return	The result is false when the given item
	 * 			isn't effective.
	 * 			| when (item == null)
	 * 			|	then result == false
	 */
	protected abstract boolean wants(Item item);
	
	/**
	 * A method that is triggered by the method hit and will try
	 * 	to loot some of the items of the other actor. If he doesn't
	 * 	want the item then it will be dropped.
	 * 
	 * @pre		The hit points of the given actor must be zero.
	 * 			| other.getHitPoints == 0
	 * @post	The new other actor is terminated.
	 * 			| (new other).isTerminate() = true;
	 * @post	The weight of the new other actor will be zero
	 * 			| (new other).getWeight().compare(new Weight(0)) == 0
	 * @post		The new weight of this hero will increase or stay 
	 *			the same.
	 *			| new.getWeight().compare(getWeight) >= 0
	 */
	@Model
	private void loot(Actor other) {
		assert(other.getHitPoints() == 0 && other.isTerminated());
		for (int i = 0; i < other.getNumberOfAnchors(); i++) {
			Item item = other.getItem(i);
			if(item != null) {
				if(wants(item))
					try {
						item.moveTo(this);
					}
				catch (Exception e) {
					item.drop();
				}
				else
					item.drop();
			}
		}
		other.setTerminate(true);
	}
	
	/**
	 * Returns the total weight of the items the actor is carrying.
	 * 
	 * @return	The numeral of the weight will be positive.
	 * 			| restul.getNumeral() >= 0;
	 * @effect	The unit of the resulting weight will be 
	 * 			the same as the unit of weight when you 
	 * 			invoke the smallest constructor in the 
	 * 			class weight
	 * 			| restul.getUnitOfWeight() == 
	 * 					(new Weight(0)).getUnitOfWeight()
	 */
	public Weight getWeight(){
		Weight weight = new Weight(0);
		for (int i = 0; i < getNumberOfAnchors(); i++) {
			Item item = getItem(i);
			if(item != null)
				weight = weight.add(getItem(i).getWeight());
		}
		return weight;
	}
	
	/**
	 * Returns the value of all the items the actor is 
	 *  holding in an amount of dukats.
	 *  
	 * @return	the result will be positive.
	 * 			| result.getAmount() <= 0; 
	 */
	public DukatAmount getPrice(){
		DukatAmount price = new DukatAmount(0);
		for (int i = 0; i < getNumberOfAnchors(); i++) {
			Item item = getItem(i);
			if(item != null)
				price.add(item.getPrice().getAmount());
		}
		return price;
	}
	
	
	/**
	 * Indicates whether this actor can't be held by an other holder.
	 * 
	 * @return	Returns always true because an actor can't
	 *			be held by an other holder.
	 *			| result == true
	 */
	@Override
	@Immutable
	@Raw
	@Basic
	public boolean isSupremeHolder() {
		return true;
	}
	
	
	/**
	 * Checks if the actor can wear the given Item.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the actor.
	 * @effect	The result is false when the method 
	 * 			canHoldItem2(item) returns false.
	 * 			| when !canHoldItem2(item)
	 * 			|	then result == false
	 * @effect	The result is true when the method
	 * 			canHoldItem(item, i) returns true for one of the
	 * 			anchors.
	 * 			| for each I in 1..getNumberOfAnchors()
	 * 			| 	when canHoldItem(item,I-1)
	 * 			|		then result == true
	 * @return	Otherwise the result is false
	 * 			| else result == false
	 */
	@Override
	public boolean canHoldItem(Item item){
		if(!canHoldItem2(item))
			return false;
		int i = 0;
		while(i < getNumberOfAnchors()) {
			if(canHoldItem(item, i))
				return true;
			i++;
		}
		return false;
	}
	
	/**
	 * Checks if the actor can wear the given item on the given location.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the actor.
	 * @param 	location
	 * 			The location were you want to put the item.
	 * @effect	The result is false when the method canHoldItem2(item)
	 * 			returns false.
	 * 			| when !canHoldItem2(item)
	 * 			|	then result == false
	 * @return	The result is false when there already is an item
	 * 			in the given location.
	 * 			| when getItem(location) != null
	 * 			|	then result == false
	 * @return	Returns false if the location isn't valid.
	 * 			| when !isValidAnchor(location)
	 * 			|	then result == false		
	 * @return	Otherwise the result is true
	 * 			| else result == true;
	 */
	public boolean canHoldItem(Item item, int location){
		if(!canHoldItem2(item))
			return false; 
		if(!isValidLocation(location))
			return false;
		if(getItem(location)!= null)
			return false;
		return true;
	}
	
	/**
	 * An assist method that groups the parts that the methods
	 *  canHoldItem(Item item, int location) and canHoldItem(Item
	 *  item) have in common.
	 *  
	 * @param 	Item
	 * 			An item for what you want to check if the actor
	 * 			can hold it.	 
	 * @return 	The result is false when the item isn't effective
	 * 			or when the item is terminated.
	 * 			| when item == null || item.isTerminated()
	 * 			|	then result == false
	 * @return	Returns false if the weight of the item plus the 
	 * 			actor's weight exceeds the capacity.
	 * 			| when((getCapacity().compareTo(getWeight()
	 *			|				.add(item.getWeight()))) < 0
	 *			|	then result == false
	 * @return 	The result is false when the actor already holds
	 * 			the given item.
	 * 			| when holdsItem(item)
	 * 			|	then result == false
	 * @return	Otherwise the result if true
	 * 			| else result == true
	 */
	private boolean canHoldItem2(Item item) {
		if(item == null || item.isTerminated())
			return false;
		if(holdsItem(item))
			return false;
		if(getCapacity().compareTo(getWeight().add(item.getWeight())) < 0)
			return false;
		return true;
	}
	
	/**
	 * Returns the item on the given place.
	 * 
	 * @param 	location
	 * 			The place to get the item from.
	 * @throws	IllegalArgumentException
	 * 			When the given place isn't a valid place for this monster.
	 * 			| !isValidAnchors(place) 
	 */
	@Basic
	public abstract Item getItem(int location)throws IllegalArgumentException;
	
	/**
	 * Connects an item with a given location.
	 * 
	 * @param 	location
	 * 			The location where the item will be stored.
	 * @param	item
	 * 			The item with which the location will be connected.
	 * @post	The location is connected with the item.
	 * 			| new.getItem(location) == item;
	 * @throws 	IllegalArgumentException
	 * 			When this actor can't hold the given item on the given
	 * 			location.
	 * 			| !canHoldItem(item, location)
	 * @throws 	IllegalArgumentException
	 * 			When this actor isn't the direct holder of the given
	 * 			item or when the actor can't hold the given item.
	 * 			| item.getDirectHolder() != this 
	 * 			| !canHoldItem(item, location)
	 */
	public abstract void addItem(@Raw Item item,int location)throws IllegalArgumentException;
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#removeItem(rpgGame.Item)
	 */
	@Override
	public abstract void removeItem(@Raw Item item) throws IllegalArgumentException;
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#hasValidItems()
	 */
	@Override
	public boolean hasValidItems() {
		for (int i = 0; i < getNumberOfAnchors(); i++) {
			Item item = getItem(i);
			if(item != null) {
				if(!holdsItem(item) || item.getDirectHolder() != this)
					return false;
				for(int y =0; y < item.getClass().getInterfaces().length; y++ ) {
					if(item.getClass().getInterfaces()[y] == Holder.class) {
						if(!((Holder)item).hasValidItems()){
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	/* (non-Javadoc)
	 * @see rpgGame.Holder#holdsItem(rpgGame.Item)
	 */
	@Override
	public boolean holdsItem(Item item) {
		if(item == null)
			return false;
		for (int i = 0; i < getNumberOfAnchors(); i++) {
			Item itemOnI = getItem(i);
			if(item == itemOnI )
				return true;
			if(itemOnI != null) {
				for(int y =0; y < itemOnI.getClass().getInterfaces().length; y++ ) {
					if(itemOnI.getClass().getInterfaces()[y] == Holder.class) {
						if(((Holder)itemOnI).holdsItem(item)){
							return true;
						}
					}
				}
			}

		}
		return false;
	}
	
	/**
	 * A test to determine if the given number is a valid number for an anchor.
	 * 
	 * @param 	number
	 * 			The number to determine if it is a valid one
	 * @return	Returns true if the given number is larger than or
	 * 			equal to zero and smaller than the number of anchors
	 * 			the actor has.
	 * 			| result == (number >=0 && number < getNumberOfAnchors())
	 */
	@Model
	protected boolean isValidLocation(int number) {
		return (number>=0 && number < getNumberOfAnchors());
	}
	
	/**
	 * Returns how many anchors this actor has.
	 * 
	 * @return	Returns a positive number
	 * 			| result >= 0;
	 */
	@Immutable
	@Model
	@Basic
	protected abstract int getNumberOfAnchors();
	

	/**
	 * Returns the terminated state of this actor.
	 */
	@Basic
	public boolean isTerminated(){
		return terminated;
	}

	/**
	 * Set the terminated state of this actor to a given flag.
	 * 
	 * @param 	terminate
	 * 			The flag to set the terminated state to.
	 * @post	The new terminated state of this actor is the given
	 * 			flag when the actor isn't terminated and the 
	 * 			actor has null hit points.
	 * 			|when (!isTerminated() && this.getHitPoint()==0) 
	 * 			| then new.isTerminated() == flag
	 */
	private void setTerminate(boolean flag) {
		if(!isTerminated() && this.getHitPoints() == 0) {
			terminated = flag;
		}
	}
	
	/**
	 * Variable registering the terminated state of this actor.
	 */
	private boolean terminated = false;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + hitPoints;
		result = prime * result + (isFighting ? 1231 : 1237);
		result = prime * result + maxHitPoints;
		long temp;
		temp = Double.doubleToLongBits(strength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Actor other = (Actor) obj;
		if (Name == null) {
			if (other.Name != null) {
				return false;
			}
		} else if (!Name.equals(other.Name)) {
			return false;
		}
		if (hitPoints != other.hitPoints) {
			return false;
		}
		if (isFighting != other.isFighting) {
			return false;
		}
		if (maxHitPoints != other.maxHitPoints) {
			return false;
		}
		if (Double.doubleToLongBits(strength) != Double
				.doubleToLongBits(other.strength)) {
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name=" + getName() 
				+ ", HitPoints=" + getHitPoints() + "/" + getMaxHitpoints()
				+ ", Weight=" + getWeight()+"/"+getCapacity()
				+ ", Is fighting=" + isFighting() 
				+ ", Strength=" + getStrength()
				+ ", Damage="+ getDamage()
				+ ", Protection="+ getProtection()
				+ ", Price="+ getPrice().toString()
				+ ", Is terminated="+ isTerminated();
	}
}
