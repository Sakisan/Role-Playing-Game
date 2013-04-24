package rpgGame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import be.kuleuven.cs.som.annotate.*;
/**
 * A class of heroes extending the class Actor and involving an amount of hit points, a maximum value for
 * the amount of hit points, a certain amount of strength, a capacity, a fighting status and
 * the hero's equipment. 
 * 
 * @invar	A Hero can't hold more than two armors.
 * 			| getNumberOfArmors() <= 2

 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class Hero extends Actor{
	
	/**
	 * Initializes a hero with given maximum hit points, name, strength and set of equipment.
	 * 
	 * @param	maxHitPoints
	 * 			The maximum value for this new hero's hit points.
	 * @param	name
	 * 			The name for this new hero.
	 * @param 	strength
	 * 			The strength for this new hero.
	 * @param	gear
	 * 			A HashMap with LocationEquipment as key variables and Item as values.
	 * 			The LocationEquipment indicates where the piece of equipment is being held and the 
	 * 			Item represents that piece of equipment that is being held by the hero.	
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| maxHitPoints > 0
	 * @effect	Puts every piece of equipment from gear in the hero's equipment.
	 * 			| setEquipment(gear)
	 * @effect 	The new hero is initialized as a new actor with the given name and with the 
	 * 			the given maxHitpoins and the given strength.
	 * 			| super(name, maxHitPoints, strength);
	 * @effect	The new hero will gain some standard items with the method setStandardEquipment.
	 * 			| this.setStandardEquipment();
	 */
	public Hero(String name, int maxHitPoints, double strength, HashMap<LocationEquipment,Item> gear)
			throws IllegalArgumentException, IllegaleToestandsUitzondering
	{
		super(name, maxHitPoints, strength); 
		addItem(gear);
		setStandardEquipment();
	}
	
	
	/**
	 * Initiate a hero with given maximum hit points, name and strength.
	 * 
	 * @param	maxHitPoints
	 * 			The maximum value for this new hero's hit points.
	 * @param	name
	 * 			The name for this new hero.
	 * @param 	strength
	 * 			The strength for this new hero.
	 * @pre		The maximum amount of hit points must be a natural number larger than zero.
	 * 			| maxHitPoints > 0
	 * @effect	Initiates a hero like it would initiate a hero with given maximum hit points, 
	 * 			name and an empty set of equipment.
	 * 			| this(name, maxHitPoints, strength, null)
	 */
	public Hero(String name,int maxHitPoints, double strength) 
		throws IllegalArgumentException, IllegaleToestandsUitzondering
	{
		this(name,maxHitPoints, strength, null);
	}

	/**
	 * This method equips the hero with an armor and a purse.
	 * 
	 * @effect	When there isn't an item in the location body.
	 * 			This method will try to move a new armor
	 * 			to the location body with a protection factor of 10.
	 * 			| when (this.getItem(LocationEquipment.Body) == null) 
	 *		 	|	then (new Armor(115, 10)).moveTo(this,LocationEquipment.Body);
	 * @effect	When there isn't an item in the location belt.
	 * 			This method will try to move a new pruse
	 * 			to the location purse.
	 * 			| when (this.getItem(LocationEquipment.Purse) == null) 
	 *		 	|	then (new Purse(MathHelp.randomInt(100))
	 *			|					.moveTo(this,LocationEquipment.Body);
	 */
	@Model
	private void setStandardEquipment() throws IllegalArgumentException, IllegaleToestandsUitzondering {
		if(this.getItem(LocationEquipment.Body) == null) {
			Item item = new Armor(115, 10);
			item.moveTo(this,LocationEquipment.Body);
		}
		if(this.getItem(LocationEquipment.Belt) == null){
			Item item = new Purse(MathHelp.randomInt(100), new DukatAmount(100));
			item.moveTo(this, LocationEquipment.Belt);
		}
	}
	
	/**
	 * Checks whether a given name complies with the name rules.
	 * 
	 * @param 	name
	 * 			The name to be checked
	 * @return 	True if the name complies with the rules and is effective.
	 * 			The rules are : 
	 * 			The name must start with a capital letter.
	 * 			The name can only contain letters, spaces, colon and apostrophes.
	 * 			The name can contain at most two apostrophes.
	 * 			The colon must be followed by a space.
	 * 			| result == (name != null) && (name.matches(
	 * 			|					[A-Z]([A-Za-z ]*[']{0,1}[A-Za-z ]*){0,2}))
	 * 			|					&& name.matches("[A-Z]([A-Za-z' ]*(: )*[A-Za-z' ]*)*")
	 * 			Otherwise the result is false
	 * 			| else result == false
	 */
	@Override
	@Model
	@Raw
	protected boolean isValidName(String name){
		String regex = "[A-Z]([A-Za-z: ]*[']{0,1}[A-Za-z: ]*){0,2}";
		String regex2 = "[A-Z]([A-Za-z' ]*(: )*[A-Za-z' ]*)*";
		if(name != null && name.matches(regex) && name.matches(regex2)) 
			return true;
		else return false;
	}
	
	/**
	 * Returns the damage that this hero can deal.
	 * 	
	 * @return 	The result is the sum of the hero's strength and the damage
	 * 			of the weapons that the hero wears on his
	 * 			left and right hand minus ten divided by two.
	 * 			|let damage = getStrenth()
	 * 			| for I = getEquipment(LocationEquipment.LeftHand) and getEquipment(LocationEquipment.RightHand)
	 * 			| 	When (I != null && I.getClass() == Weapon.class)
	 * 			|		then damage += ((weapon)I).getValue();
	 * 			| result == (int)((damage-10)/2)
	 */
	@Override
	protected int getDamage(){
		double damage = super.getStrength();
		Item[] lijst = new Item[2];
		lijst[0] = getItem(LocationEquipment.LeftHand);
		lijst[1] = getItem(LocationEquipment.RightHand);
		for(Item item : lijst) {
			if(item !=null && (item.getClass() == Weapon.class)) {
				damage += ((Weapon)item).getValue();
			}
		}
		return (int)((damage-10)/2);
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Actor#getCapacity(double)
	 */
	@Override
	public Weight getCapacity(double strength) throws IllegalArgumentException{
		if(strength < 0)
			throw new IllegalArgumentException("negative values aren't supported");
		try{
			Weight[] capacity = {new Weight(0,UnitOfWeight.KILOGRAM),
					new Weight(115, UnitOfWeight.KILOGRAM),
					new Weight(130, UnitOfWeight.KILOGRAM),
					new Weight(150, UnitOfWeight.KILOGRAM),
					new Weight(175, UnitOfWeight.KILOGRAM), 
					new Weight(200, UnitOfWeight.KILOGRAM), 
					new Weight(230, UnitOfWeight.KILOGRAM),
					new Weight(260, UnitOfWeight.KILOGRAM), 
					new Weight(300, UnitOfWeight.KILOGRAM), 
					new Weight(350, UnitOfWeight.KILOGRAM), 
					new Weight(400, UnitOfWeight.KILOGRAM)};

			if(strength < 1){
				return new Weight(0,UnitOfWeight.KILOGRAM);
			}
			else if(strength <= 10){
				return new Weight(10*strength, UnitOfWeight.KILOGRAM);
			}
			else if(strength <= 20){
				return capacity[(int)Math.ceil(strength) - 10];
			}
			else{
				return getCapacity(strength-10).multiply(4);
			}
		}
		catch (IllegalArgumentException e) {
			if(strength < 0)
				throw new IllegalArgumentException("negative values aren't supported");
			return new Weight(Double.MAX_VALUE, UnitOfWeight.KILOGRAM);
		}
	}
	
	/**
	 * Return the protection factor for this hero. 
	 * 		The protection factor is an indicator for the hero's ability to avoid attacks in a fight.
	 * 	
	 * @return 	The result will be a positive number.
	 * 			| result <= 0
	 * @return 	The result will be the sum of the hero's base protection and 
	 * 			the protection factor of the armor that the hero wears on his
	 * 			body. The base protection factor is ten for all the heros.
	 * 			| when getEquipment(LocationEquipment.Body).class == Armor.class
	 * 			|	then factor = ((Armor)getEquipment(LocationEquipment.Body)).getValue()
	 * 			| result == factor + 10;
	 */
	@Override
	@Basic
	public double getProtection(){
		Item item = getItem(LocationEquipment.Body);
		double protectionWithArmor = protection;
		if(item != null && (item.getClass() == Armor.class)){
			protectionWithArmor += ((Armor)item).getValue();
		}
		return protectionWithArmor;
	}
	
	/**
	 * Variable registering the protection factor for all the heroes.
	 */
	static private double protection = 10;
	
	
	/**
	 * A test to determine if this hero can fight against the given actor.
	 * 
	 * @param 	opponent
	 * 			The opponent were this hero wants to fight against.
	 * @return 	The result will be true if the method cenFightOpponent of
	 * 			the superclass is true and if the opponent isn't a hero. 
	 * 			Otherwise the result will be false.
	 * 			| result == (super.canFightOpponent(opponent) && (opponent.getClass() != Hero.class));
	 */
	@Override
	@Model
	protected  boolean canFightOpponent(Actor opponent){
		return (super.canFightOpponent(opponent) && (opponent.getClass() != Hero.class));
	}
	
	/**
	 * The result of this method is a random number that is used in the method hit.
	 * 
	 * @return	The result will be positive.
	 * 			| result >= 0
	 * @return 	The result will be smaller than twenty
	 * 			| result <= 20;
	 */
	@Override
	protected int luckFactorForHit() {
		return MathHelp.randomInt(20);
	}
	
	/**
	 * A method that is triggered by the method hit and 
	 * it will return an number that will be healed.
	 *  
	 * @return	The resulting value will be a value between
	 * 			zero and the max amount of hit points minus his
	 * 			current amounts of hit points.
	 * 			| 0 =< result =< getMaxHitpoints()-getHitPoints();
	 */
	@Override
	protected int heal() {
		return MathHelp.randomInt(getMaxHitpoints()-getHitPoints());
	}
	
	/**
	 * A method that determines if this hero wants to
	 * 	hold the given item.
	 * 
	 * @param 	item
	 * 			An item that you want to test.
	 * @return	The result is false when the given item
	 * 			isn't effective.
	 * 			| when (item == null)
	 * 			|	then result == false
	 * @return	If the items is an instance of the abstract
	 * 			class bag then the result is always true.
	 * 			| when item instanceof Bag
	 * 			|	then result == true
	 * @return	If the items is an instance of the class weapon
	 * 			then the result is true when the hero holds a 
	 * 			weapon on his lefthand or rigthhand 
	 * 			with a value smaller then the value of the 
	 * 			given item.
	 * 			| when item.getClass() == Weapon.class
	 * 			|	for I = getItem(LocationEquipment.RightHand) and getItem(LocationEquipment.LeftHand)
	 * 			|		when I.getClass() == Weapon.class and I.getValue() < item.getValue()
	 * 			|			then result == true
	 * @return	If the items is an instance of the class armor
	 * 			then the result is true when the hero holds a 
	 * 			armor on the location body with a value smaller 
	 * 			then the value of the given item.
	 * 			| when item.getClass() == Armor.class
	 * 			|	let I = getItem(LocationEquipment.Body)
	 * 			|	when I.getClass() == Armor.class and I.getValue() < item.getValue()
	 * 			|		then result == true 
	 */
	@Override
	protected boolean wants(Item item) {
		if(item == null)
			return false;

		Class<? extends Item> classOfItems = item.getClass();

		if(classOfItems == Weapon.class) {
			int valueItem = ((Weapon)item).getValue();
			Item[] currentItems = new Item[2];
			currentItems[0] = getItem(LocationEquipment.RightHand);
			currentItems[1] = getItem(LocationEquipment.LeftHand);
			for (int i = 0; i < currentItems.length; i++) {
				if(currentItems[i] != null && currentItems[i].getClass() == Weapon.class)
					if(((Weapon)currentItems[i]).getValue() < valueItem)
						return true;
			}
		}

		else if(classOfItems == Armor.class) {
			Item armor = getItem(LocationEquipment.Body);
			if(armor.getClass() == Armor.class)
				if(((Armor)item).getValue() > ((Armor)armor).getValue())
					return true;
		}

		else if(item instanceof Bag)
			return true;

		return false;
	}
	
	

	/**
	 * Checks if the hero can wear the given item on the 
	 * 	given location equipment.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the hero.
	 * @param 	location
	 * 			The location equipment were you maybe want to put 
	 * 			the item.
	 * @return 	The result is false when the location isn't effective.
	 * 			| when (location == null)
	 * 			|	then result == false
	 * @effect	The result is false when the method 
	 * 			canHoldItem(item,location.getFollowNumber()) 
	 * 			of the superclass actor returns false.
	 * 			| when !super.canHoldItem(item, location.getFollowNumber())
	 * 			|	then result == false
	 * @effect	The result is false if the method CanLinkItem of
	 * 			location equipment returns false.
	 * 			| when !location.canLinkItem(item)
	 * 			|	then result == false
	 * @return	Returns false if the class of the given item is
	 * 			armor and the hero is already holding 2 armors.
	 * 			|when (item.getClass() == Armor.class && getAmountOfArmor() >= 2 )
	 * 			|  	then result == false
	 * @return	Otherwise the result is true
	 * 			| else result == true;
	 */
	public boolean canHoldItem(Item item, LocationEquipment location){
		if(location == null)
			return false;
		if(!super.canHoldItem(item, location.getFollowNumber()))
			return false;
		if(!location.canLinkItem(item))
			return false; 
		if(item.getClass() == Armor.class && getNumberOfArmors() >= 2 )
			return false;
		return true;
	}
	
	/**
	 * Checks if this hero can wear the given item on the given location.
	 * 
	 * @param 	item
	 * 			An item that you maybe want to give to the hero.
	 * @param 	location
	 * 			The location were you want to put the item.
	 * @return	The result is false when the given number
	 * 			isn't a valid location
	 * 			| when !isValidLocation(location)
	 * 			|	then result == false
	 * @effect	The result is equal to the result of the method 
	 * 			canHoldItem(item, location equipment).
	 * 			| let locationEquipment = LocationEquipment.values();
	 * 			|	then result ==  canHoldItem(item, 
	 * 			|				locationEquipment[location]);
	 */
	@Override
	public boolean canHoldItem(Item item, int location){
		if(!isValidLocation(location))
			return false;
		LocationEquipment[] locationEquipment = LocationEquipment.values();
		return canHoldItem(item, locationEquipment[location]);
	}
	
	
	/**
	 * Returns the item on the given place.
	 * 
	 * @param 	place
	 * 			The place to get the item from.
	 * @effect	The effect will be the same as the method
	 * 			getItem(place).
	 * 			| let locationEquipment = LocationEquipment.values()
	 * 			|	then result == getItem(locationEquipment[place])
	 * @throws	IllegalArgumentException
	 * 			When the given place isn't a valid place for this monster.
	 * 			| !isValidAnchors(place) 
	 */
	@Basic
	@Raw
	@Override
	public Item getItem(int place) throws IllegalArgumentException{
		if(!isValidLocation(place))
			throw new IllegalArgumentException("The given place wasn't in the valid range.");
		LocationEquipment[] locationEquipment = LocationEquipment.values();
		return getItem(locationEquipment[place]);
	}
	
	/**
	 * Returns the item on the given location equipment.
	 * 
	 * @param 	place
	 * 			The location equipment to get the item from.
	 * @return	The item connected with the given place. If there is no item 
	 * 			connected with the given place the result is null.
	 * @throws	IllegalArgumentException
	 * 			When the place isn't effective.
	 * 			| place == null
	 */
	@Basic
	@Raw
	public Item getItem(LocationEquipment place) throws IllegalArgumentException{
		if(place == null)
			throw new IllegalArgumentException("The given place wasn't in the valid range.");
		return equipment.get(place);
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#setItem(rpgGame.Item)
	 */
	@Override
	public void addItem(@Raw Item item) throws IllegaleToestandsUitzondering,IllegalArgumentException {
		if(!this.canHoldItem(item))
			throw new IllegalArgumentException("This hero can't hold the given item.");
		if(item.getDirectHolder() != this) 
			throw new IllegalArgumentException("The holder of the item isn't this hero. To move an item use the method of the class Item.");
		if(this.holdsItem(item))
			throw new IllegaleToestandsUitzondering("A hero can't wear the same item 2 times.");
		//small AL
		if(item.getClass() == Purse.class && canHoldItem(item, LocationEquipment.Belt)) {
			addItem(item, LocationEquipment.Belt);
			return;
		}
		boolean placed = false;
		for(LocationEquipment otherLocation : LocationEquipment.values()){
			if(canHoldItem(item, otherLocation) && !placed){
				this.addItem(item, otherLocation);
				return; //exit method when placed.
			}
		}
		if(!placed)
			throw new IllegaleToestandsUitzondering("The item isn't placed");
	}
	
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
	 * 			When this hero can't hold the given item on the given
	 * 			location.
	 * 			| !canHoldItem(item, location)
	 * @throws 	IllegalArgumentException
	 * 			When this hero isn't the direct holder of the given
	 * 			item.
	 * 			| item.getDirectHolder() != this
	 */
	public void addItem(@Raw Item item, LocationEquipment location) 
				throws IllegalArgumentException {
		if(!canHoldItem(item, location) || item.getDirectHolder() != this)
			throw new IllegalArgumentException();
		equipment.put(location, item);
	}
	
	/**
	 * Connects an item with a given location.
	 * 
	 * @param 	location
	 * 			The location where the item will be stored.
	 * @param	item
	 * 			The item with which the location will be connected.
	 * @effect	The result will be the same as when you use
	 * 			the method addItem(location equipment, item)
	 * 			| let locationEquipment = LocationEquipment.values();
	 * 			| addItem(locationEquipment[location], item);
	 * @throws 	IllegalArgumentException
	 * 			When the given location isn't a valid location.
	 * 			| !isValidLocation(location)
	 */
	@Override
	public void addItem(@Raw Item item, int location) 
				throws IllegalArgumentException {
		if(!isValidLocation(location))
			throw new IllegalArgumentException();
		LocationEquipment[] locationEquipment = LocationEquipment.values();
		addItem(item, locationEquipment[location]);
	}
	
	/**
	 * Add a given set of equipment to the hero's equipment.
	 * 
	 * @param 	gear
	 * 			A HashMap with LocationEquipment as key variables and Item as values.
	 * 			The LocationEquipment indicates where the piece of equipment is going to be held and the 
	 * 			Item represents that piece of equipment that is going to be held by the hero.	
	 * @effect	Puts every piece of equipment from the given HashMap in the hero's equipment.
	 * 			| for each I in LocationEquipment.values()
	 * 			|	(gear.get(I)).move(this,I)
	 * @post	If a exception occurrence during moveto the current item won't be add to the hero's 
	 * 			equipment and this method will continue with the next item.
	 * 			| when (gear.get(I)).move(this,I) throws exception
	 * 			|	then catch and continue;
	 */
	public void addItem(HashMap<LocationEquipment, Item>  gear) 
	throws IllegalArgumentException, IllegaleToestandsUitzondering
	{
		if(gear != null) 
			for (LocationEquipment location : LocationEquipment.values()) {
				if(gear.containsKey(location)){
					Item item = gear.get(location);
					if(item != null) {
						try {
							item.moveTo(this, location);
						}
						catch (Exception e) {
							// An error occurred. This method will continue with an attempt to add the next item. 
						}
					}
				}
			}
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Actor#removeItem(rpgGame.Item)
	 */
	@Raw
	@Override
	 public void removeItem(@Raw Item item) throws IllegalArgumentException{
		if(item == null || item.getDirectHolder() == this)
			throw new IllegalArgumentException();
		Collection<Item> c = equipment.values();
		c.remove(item);
	}
	
	/**
	 * A method that you can use to switch the armor that is in the location
	 * 	body with the given armor.
	 * 
	 * @param 	armor
	 * 			The new armor for the location body.
	 * @post	The hero holds the given item on the location body.
	 * 			| (new this).getItem(LocationEquipment.Body) == armor
	 * @post	The hero still hold the old armor.
	 * 			| new.holdsItem(getItem(LocationEquipment.Body))
	 * @throws	IllegalArgumentException
	 * 			When the given armor isn't effective.
	 * 			| armor == null
	 * @throws	IllegaleToestandsUitzondering
	 * 			When the hero can't hold the given item or when the 
	 * 			on the location body isn't an armor.
	 * 			| getItem(LocationEquipment.Body).getClass() != Armor.class ||
				|	!canHoldItem(armor)
	 */
	public void switchArmor(Armor armor) throws IllegalArgumentException, IllegaleToestandsUitzondering{
		if(armor == null)
			throw new IllegalArgumentException("the armor must be valid");
		if(getItem(LocationEquipment.Body).getClass() == Armor.class &&
				canHoldItem(armor)) {
			for (LocationEquipment location : LocationEquipment.values()){ 
				if(location != LocationEquipment.Body && getItem(location) == null){
					Armor oldArmor = (Armor)getItem(LocationEquipment.Body);
					try {
						equipment.remove(LocationEquipment.Body);
						armor.moveTo(this, LocationEquipment.Body);
						equipment.put(location, oldArmor);
						return;
					}
					catch (Exception e) {
							equipment.remove(location);
							equipment.put(LocationEquipment.Body, oldArmor);
							//something went wrong try again with the next location
					}
				}
			}
		}
		throw new IllegaleToestandsUitzondering("There went something wrong the armor isn't switched.");
	}
	
	/**
	 * Returns how many anchors this actor has.
	 * 
	 * @return	The result will be equal to the number
	 * 			of different location equipments that
	 * 			exist.
	 * 			| result == LocationEquipment.values().length
	 */
	@Immutable
	@Override
	@Basic
	protected int getNumberOfAnchors(){
		return LocationEquipment.values().length;
	}
	
	/**
	 * Calculates how many items of the type armor the hero is holding.
	 *
	 * @return	The result will be larger than or equal to zero.
	 * 			| result >=0;
	 */
	@Model
	private int getNumberOfArmors() {
		int number = 0;
		for(LocationEquipment location: LocationEquipment.values()) {
			Item item = getItem(location);
			if(item != null)
			{
				Class<? extends Item> classOfItem = item.getClass();
				if(classOfItem == Armor.class) {
					number++;
				}
				else if(classOfItem == Backpack.class) {
					number  = number + getNumberOfArmors((Backpack)item);
				}
			}
		}
		return number;
	}
	
	/**
	 * Calculates the amount of armors in the given backpack.
	 * 
	 * @param 	backpack
	 *			The backpack for what you want to calculate the
	 *			amount of armors for. 
	 * @return	The result will be larger than or equal to zero.
	 * 			| result >=0;
	 * 			
	 */
	private int getNumberOfArmors(Backpack backpack) {
		Iterator<Item> iterator = backpack.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Item item = iterator.next();
			Class<? extends Item> classOfItem = item.getClass();
			if(classOfItem == Armor.class) {
				i++;
			}
			else if(classOfItem == Backpack.class) {
				i = i + getNumberOfArmors((Backpack)item);
			}
		}
		return i;
	}
	
	/**
	 * Variable registering the hero's equipment.
	 */
	private HashMap<LocationEquipment, Item> equipment= new HashMap<LocationEquipment, Item>();

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((equipment == null) ? 0 : equipment.hashCode());
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
		Hero other = (Hero) obj;
		if (equipment == null) {
			if (other.equipment != null) {
				return false;
			}
		} else if (!equipment.equals(other.equipment)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hero [equipment= " + equipment
				+ ",\n"+ super.toString() + "]\n";
	}
}

