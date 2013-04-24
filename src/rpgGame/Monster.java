package rpgGame;

import java.util.Arrays;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of monsters extending the class Actor and involving
 *  a name, hit points, strength, anchors and items.
 * 
 * @author Pieter Willemsen and Antoine Snyers
 * @version 3.0
 *
 */
public class Monster extends Actor{
	
	/**
	 * Initializes a monster with a given name and a given 
	 *  strength and a given amount of hit points and a given
	 *  array of items.
	 * 
	 * @param 	name
	 * 			The name for the new monster.	
	 * @param	hitPoints
	 * 			The value for the new monster's hit points.
	 * @param 	strength
	 * 			The value for the strength for this new monster.
	 * @param	item
	 * 			An array containing the item you want to give to this monster.
	 * @pre		The given array containing items must be valid for the given strength
	 * 			| isValidItemsArray(item,strength)
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| hitPoints > 0
	 * @post 	The number of anchors will be equal to the length of item
	 * 			| new.getNumberOfAnchors() == item.length;
	 * @post	Every item that is in the array list will be hold by the monster
	 * 			on the same location were it was in the given array.
	 * 			| for each I in 0..item.length
	 * 			|	this.getItem(i-1) == item[i-1]
	 * @effect 	The new monster is initialized as a new actor with the 
	 * 			given name and hitPoints and the given strength.
	 * 			| super(name, hitPoints, strength);
	 */
	public Monster(String name, int hitPoints, double strength, Item[] item) throws IllegalArgumentException {
		super(name, hitPoints, strength);
		
		assert(isValidItemsArray(item, strength));
		equipment = new Item[item.length];
		addItem(item);
	}
	
	/**
	 * Initializes a monster with a given name and a given 
	 *  strength and a given amount of hit points and a given
	 *  number of anchors.
	 * 
	 * @param 	name
	 * 			The name for the new monster.	
	 * @param	hitPoints
	 * 			The value for the new monster's hit points.
	 * @param 	strength
	 * 			The value for the strength for this new monster.
	 * @param	numberOfAnchors
	 * 			The number of anchors the monster will have
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| hitPoints > 0
	 * @effect 	The new monster is initialized as a new monster with the 
	 * 			given name and hitPoints and the given strength and 
	 * 			the array resulting from the static method fillEquipment.
	 * 			| this(name, hitPoints, strength, 
	 * 					Monster.fillEquipment(numberOfAnchors, strength);
	 */
	public Monster(String name, int hitPoints, double strength, int numberOfAnchors) throws IllegalArgumentException {
		this(name, hitPoints, strength,
				Monster.fillEquipment(numberOfAnchors, strength));
	}
	
	/**
	 *  Initializes a monster with a given name and a given strength
	 *   and a given amount of hit points.
	 * 
	 * @param 	name
	 * 			The name for the new monster.	
	 * @param	hitPoints
	 * 			The value for the new monster's hit points.
	 * @param 	strength
	 * 			The value for the strength for this new monster.
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| hitPoints > 0
	 * @effect 	The new monster is initialized as a new monster with 
	 * 			the given name, the given hit points, the given strength
	 * 			and random number between 0 and 20 as it's number of
	 * 			anchors.
	 * 			| this(name, hitPoints, strength, MathHelp.randomInt(20))
	 */
	public Monster(String name, int hitPoints, double strength) throws IllegalArgumentException {
		this(name, hitPoints, strength, MathHelp.randomInt(20));
	}
	
	/**
	 *  Initializes a monster with a given name and a given amount 
	 *   of hit points.
	 * 
	 * @param 	name
	 * 			The name for the new monster.	
	 * @param	hitPoints
	 * 			The value for the new monster's hit points.
	 * @pre 	The amount of hitPoints must be a natural number larger than zero.
	 * 			| hitPoints > 0
	 * @effect 	The new monster is initialized as a new monster with the 
	 * 			given name, the given hit points and a strength from 
	 * 			0 to 100.
	 * 			| this(name, hitPoints, (Math.random()*100));
	 */
	public Monster(String name, int hitPoints) throws IllegalArgumentException {
		this(name, hitPoints, Math.random()*100);
	}
		
	/**
	 *  Initializes a monster with a given name.
	 * 
	 * @param 	name
	 * 			The name for the new monster.	
	 * @effect 	The new monster is initialized as a new monster with the 
	 * 			given name and hitPoints between 1 and 100.
	 * 			| this(name, MathHelp.randomInt(99)+1)
	 */
	public Monster(String name) throws IllegalArgumentException {
		this(name, MathHelp.randomInt(99)+1);
	}

	/**
	 * Checks whether a given name complies with the name rules.
	 * 
	 * @param 	name
	 * 			The name to be checked
	 * @return	True if the name complies with the rules and is effective.
	 * 			The rules are : 
	 * 			The name must start with a capital letter.
	 * 			The name can only contain letters, spaces, colon and apostrophes.
	 * 			| result == (name != null) && (name.matches("[A-Z][A-Za-z' ]*)")
	 * 			Otherwise the result will be false.
	 * 			| else result == false
	 */
	@Override
	@Model
	protected boolean isValidName(String name) {
		String regex = "[A-Z][A-Za-z' ]*";
		if(name != null && name.matches(regex)) 
			return true;
		else 
			return false;
	}
		
	/**
	 * This method construct an array of items whit as length the
	 * 	given number of slots. The array will be filled with
	 * 	random items.
	 * 
	 * @param 	numberofslots
	 * 			The number of slots the resulting array will have.
	 * @param 	strength
	 * 			The value that determines the maximum total weight
	 * 			of all the items in the array.
	 * @return	The length of the result is equal to number of slots.
	 * 			| result.length == numberofslots
	 * @return 	The total weight of the result will be less than or 
	 * 			equal to the capacity that is determined by the strength.
	 * 			| let totalWeightResult = new Weight(0)
	 * 			| for I is 1..result.length
	 * 			|	when result[I] != null
	 * 			|	  then totalWeightResult = result[I].getWeight()
	 * 			| 	then totalWeightResult.compare(Monster.getCapacityMonster(strength)) <=0
	 */
	private static Item[] fillEquipment(int numberofslots, double strength){
		Item[] gear = new Item[numberofslots];
		Weight capacity = Monster.getCapacityMonster(strength);
		Weight currentWeight = new Weight(0);

		for (int i = 0; i < gear.length; i++) {
			int random = MathHelp.randomInt(100);
			if(random > 50) {
				Item itemToAddToGear = null;
				if(random < 60) {
					itemToAddToGear = new Backpack();
				}
				else if(random < 70 ) {
					itemToAddToGear = new Armor(MathHelp.randomInt(100));
				}
				else if(random < 80) {
					itemToAddToGear = new Weapon();
				}
				else if(random < 90) {
					itemToAddToGear = new Purse(MathHelp.randomInt(100));
				}
				if(itemToAddToGear != null){
					if(capacity.compareTo(currentWeight.add(itemToAddToGear.getWeight())) >= 0){
						gear[i] = itemToAddToGear;
						currentWeight.add(itemToAddToGear.getWeight());
					}
				}	
			}
		}

		return gear;
	}
	
	/**
	 * A method that can be used to determine if the given items array 
	 * 	in a constructor is a valid array for the new monster.
	 * 
	 * @param 	item
	 * 			An array containing items.
	 * @param 	strength
	 * 			The strength that the new monster will have.
	 * @return	The result is false when the array isn't effective
	 * 			| when item == null
	 * 			|	then result == false
	 * @return	The result is true when the weight of the given array item 
	 * 			is smaller then the capacity of the given strength.
	 * 			| let weightItems be
	 * 			|	for each i in 1..item.length
	 * 			|		when item[i] != null
	 * 			|			then weightItems.add(item[i].getWeight())
	 * 			|  then result ==  getCapacity(strength).compareTo(weightItems) >= 0
	 * 
	 */
	@Model
	private boolean isValidItemsArray(Item[] item, double strength) {
		if(item == null)
			return false;
		Weight weightItem = new Weight(0);
		for (int i = 0; i < item.length; i++) {
			if(item[i] != null)
				weightItem.add(item[i].getWeight());
		}
		return getCapacity(strength).compareTo(weightItem) >= 0;
	}
	
	
	/**
	 * Returns the damage that this monster can deal in combat.
	 * 	
	 * @return	The result is the sum of a number that is unique for the monster
	 * 		    and the strength of the monster.
	 * 			| result == (int)(uniqueNumber + getStrength())
	 *			The uniqueNumber will be a number between or equal to minus one and thirty-one.
	 *			| -1 <= uniqueNumber <= 31
	 */
	@Override
	protected int getDamage() {
		double damage = super.getStrength();
		damage += (claw.getValue()-5)/(double) 3;
		return (int)damage;
	}
	
	/**
	 * A method that creates a weapon that will be used for the monster's claw.
	 */
	private Weapon createClaw() {
		Weapon weapon = new Weapon();
		weapon.terminate();
		return weapon;
	}
	
	/**
	 * Variable registering this monster's claw.
	 */
	private Weapon claw = createClaw();
	
	
	/**
	 * Return the protection factor for this monster. 
	 *  The protection factor is an indicator for the monster's ability block an attack.
	 *
	 * @return	The result will be positive.
	 * 			| result >= 0
	 */
	@Immutable
	@Basic
	public double getProtection() {
		return skin.getValue();
	}
	
	/**
	 * A method that creates an armor that will be used for the monster's skin.
	 */
	private Armor createSkin() {
		Armor armor = new Armor(1, MathHelp.randomInt(20));
		armor.terminate();
		return armor;
	}
	
	/**
	 * Variable registering this monster's skin.
	 */
	private Armor skin = createSkin();
	
	/**
	 * The result of this method is a random number that is used in the method hit.
	 * 
	 * @return	The result will be positive.
	 * 			| result >= 0
	 * @return	The result will be smaller than or 
	 * 			equal to the damage the monster can deal.
	 * 			| result <= getDamge();
	 * @return	The result always will be smaller than 
	 * 			or equal to 100 .
	 * 			| result <= 100;
	 */
	@Override
	protected int luckFactorForHit() {
		int factor = MathHelp.randomInt(100);
		if(factor >= getDamage())
			factor = getDamage();
		return factor;
	}
	
	/**
	 * A method that determines if this monster
	 *  wants to hold the given item.
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
	 * @return 	For all the other types of items the change
	 * 			on true or false as result is 50%.
	 */
	@Override
	protected boolean wants(Item item) {
		if(item == null)
			return false;
		else if(item instanceof Bag)
			return true;
		else if(MathHelp.randomInt(100) < 50)
			return true;
		return false;
	}
	
	/**
	 * A method that is triggered by the method hit and it
	 *  will return an number that will be healed.
	 * 
	 * @return	Monster can't heal what means the result is null.
	 * 			| result == 0
	 */
	@Override
	@Immutable
	protected int heal() {
		return 0;
	}
	
	/**
	 * Returns a capacity for the given strength.
	 * 
	 * @param 	strength
	 * 			The strength for which the capacity must be definite.
	 * @effect	the result will the same as when you utilise the static
	 * 			method getCapcity;
	 * 			| result == Monster.getCapacityMonster(strength)
	 */
	@Override
	public Weight getCapacity(double strength) throws IllegalArgumentException {
		return Monster.getCapacityMonster(strength);
	}
	
	/**
	 * Returns a capacity for the given strength.
	 * 	This is a static method.
	 * 
	 * @param 	strength
	 * 			The strength for which the capacity must be definite.
	 * @return	The result will be a weight with a numeral between or 
	 * 			equal to zero and the maximum value for double.
	 * 			|0 <= result.getNumeral() <= Double.MAX_VALUE
	 * @return	The unit of weight of the result will be kilogram.
	 * 			| result.getUnitofWeight() == UnitOfWeight.KILOGRAM
	 * @return	The numeral of the result will be the strength multiplied
	 * 			by 9.
	 * 			| result.getNumeral == strength*9
	 * @throws 	IllegalArgumentException
	 * 			If the given strength is negative.
	 * 			| strength < 0
	 */
	public static Weight getCapacityMonster(double strength) throws IllegalArgumentException {
		if(strength < 0)
			throw new IllegalArgumentException("negative values aren't supported");
		else if(strength == 0)
			return new Weight(0, UnitOfWeight.KILOGRAM);
		else {
			try{
				return new Weight(strength*9);
			}
			catch (IllegalArgumentException e) {
				return new Weight(Double.MAX_VALUE);
			}
		}
	}
		
	/**
	 * Returns the item on the given place.
	 * 
	 * @param 	place
	 * 			The place to get the item from.
	 * @return	The item connected with the given place. If there is no item 
	 * 			connected with the place the result is null.
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
		return equipment[place];
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Holder#setItem(rpgGame.Item)
	 */
	public void addItem(@Raw Item item) throws IllegaleToestandsUitzondering,IllegalArgumentException {
		if(!this.canHoldItem(item))
			throw new IllegalArgumentException("This hero can't hold the given item.");
		if(item.getDirectHolder() != this) 
			throw new IllegalArgumentException("The holder of the item isn't this hero. To move an item use the method of the class Item.");
		if(this.holdsItem(item))
			throw new IllegaleToestandsUitzondering("A hero can't wear the same item 2 times.");
		
		boolean placed = false;
		for (int i = 0; i < getNumberOfAnchors(); i++){
			if(canHoldItem(item, i)){
				this.addItem(item, i);
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
	 * 			When this monster can't hold the given item on the given
	 * 			location.
	 * 			| !canHoldItem(item, location)
	 * @throws 	IllegalArgumentException
	 * 			When this monster isn't the direct holder of the given
	 * 			item.
	 * 			| item.getDirectHolder() != this
	 */
	@Override
	public void addItem(@Raw Item item,int location) 
				throws IllegalArgumentException {
		if(!canHoldItem(item, location) || item.getDirectHolder() != this)
			throw new IllegalArgumentException();
		equipment[location] = item;
	}
	
	/**
	 * This method tries to add the given array to this monsters 
	 *  equipment.
	 *  
	 * @param	gear
	 * 			An array containing the items to add.
	 * @post	The method will try on every nonempty item in 
	 * 			the given array the method moveTo of the class
	 * 			item whit the parameters this and the location
	 * 			of the item in the given array.
	 * 			If the method moveTo throws an error the item
	 * 			will not be added to the current monster's equipment
	 * 			and this method will continue whit the next item
	 * 			in the given array.
	 * 			| for each I in 1..gear.length
	 * 			| 	 when gear[i-1] != null
	 * 			|		then try gear[i-1].moveTo(this,i-1) 
	 * 			|			 catch(exception e)
	 * 			|				continue with the next I
	 */
	@Raw
	public void addItem(Item[] gear){
		for (int i = 0; i < gear.length; i++) {
			if(gear[i] != null){
				try{
					gear[i].moveTo(this, i);
				}
				catch (Exception e) {
					//something went wrong the items isn't added to this monster.
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see rpgGame.Actor#removeItem(rpgGame.Item)
	 */
	@Override
	public void removeItem(@Raw Item item) throws IllegalArgumentException {
		if(item == null || item.getDirectHolder() == this || !holdsItem(item))
			throw new IllegalArgumentException();
		for (int i = 0; i < getNumberOfAnchors(); i++) {
			if(equipment[i] == item) {
				equipment[i] = null;
				return;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see rpgGame.Actor#getNumberOfAnchors()
	 */
	@Immutable
	@Override
	@Basic
	protected int getNumberOfAnchors(){
		return equipment.length;
	}
	
	/**
	 * Variable registering the monster's items.
	 */
	private Item[] equipment;
		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((claw == null) ? 0 : claw.hashCode());
		result = prime * result + Arrays.hashCode(equipment);
		result = prime * result + ((skin == null) ? 0 : skin.hashCode());
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
		Monster other = (Monster) obj;
		if (claw == null) {
			if (other.claw != null) {
				return false;
			}
		} else if (!claw.equals(other.claw)) {
			return false;
		}
		if (!Arrays.equals(equipment, other.equipment)) {
			return false;
		}
		if (skin == null) {
			if (other.skin != null) {
				return false;
			}
		} else if (!skin.equals(other.skin)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Monster [Equipment=" + Arrays.toString(equipment) 
				+",\n" + super.toString() + "]";
	}

}
