package rpgGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MonsterTest {
	private Monster monsterStandard;
	private Monster monsterWithEquipment;
	
	@Before
	public void setUp() throws Exception {
		monsterStandard = new Monster("Pieter", 100, 10);
		
		//equipment for hero
		Weapon weaponLeftHand = new Weapon(5, new Weight(5));
		Weapon weaponRightHand = new Weapon(10, new Weight(5));
		Armor armorBody = new Armor(10, 10, new Weight(5), 200);
		Backpack bag = new Backpack();
			Armor armorBag = new Armor(15);
			Weapon weaponBag = new Weapon();
			Backpack backpackBag = new Backpack();
				Armor armorBackpackBag = new Armor(90);
				Purse purseBackpackBag = new Purse();
		Purse purseBelt = new Purse();
		
		armorBag.moveTo(bag);
		weaponBag.moveTo(bag);
		backpackBag.moveTo(bag);
			purseBackpackBag.moveTo(backpackBag);
		Item[] gear = new Item[10];
		gear[1] = weaponLeftHand;
		gear[5] = bag;
		gear[9] = armorBody;
		gear[6] = purseBelt;
		gear[4] = armorBackpackBag;
		gear[0] = weaponRightHand;
		monsterWithEquipment = new Monster("Antoine", 10, 100, gear);
	
	}

	@Test
	public void testConstructorMostExtended() {
		Weapon weaponLeftHand = new Weapon(5, new Weight(5));
		Weapon weaponRightHand = new Weapon(10, new Weight(5));
		Armor armorBody = new Armor(10, 10, new Weight(5), 200);
		Backpack bag = new Backpack();
			Armor armorBag = new Armor(15);
			Weapon weaponBag = new Weapon();
			Backpack backpackBag = new Backpack();
				Armor armorBackpackBag = new Armor(90);
				Purse purseBackpackBag = new Purse();
		Purse purseBelt = new Purse();
		
		armorBag.moveTo(bag);
		weaponBag.moveTo(bag);
		backpackBag.moveTo(bag);
			purseBackpackBag.moveTo(backpackBag);
		Item[] gear = new Item[10];
		gear[1] = weaponLeftHand;
		gear[5] = bag;
		gear[9] = armorBody;
		gear[6] = purseBelt;
		gear[4] = armorBackpackBag;
		gear[0] = weaponRightHand;

		DukatAmount totalPrice = new DukatAmount(0);
		Weight totalWeight = new Weight(0);
		for (int i = 0; i < gear.length; i++) {
			if(gear[i] != null) {
				totalWeight = totalWeight.add(gear[i].getWeight());
				totalPrice.add(gear[i].getPrice().getAmount());
			}
		}

		Monster monster5 = new Monster("Geer't", 100, 20, gear);
		assertTrue(monster5.getName() == "Geer't");


		// assertEquals(expected, actual)

		assertTrue(monster5.getCapacity().getNumeral() == 180);
		assertEquals(totalWeight, monster5.getWeight());
		assertEquals(totalPrice, monster5.getPrice());
		assertEquals(weaponLeftHand, monster5.getItem(1));
		assertEquals(weaponRightHand, monster5.getItem(0));
		assertEquals(armorBody ,monster5.getItem(9));
		assertEquals(purseBelt, monster5.getItem(6));
		assertEquals(bag, monster5.getItem(5));
		assertEquals(armorBackpackBag, monster5.getItem(4));
		
		assertTrue(monster5.holdsItem(purseBelt));
		assertTrue(monster5.holdsItem(purseBackpackBag));
		assertTrue(monster5.holdsItem(backpackBag));
		assertTrue(monster5.holdsItem(weaponBag));
		assertTrue(monster5.holdsItem(armorBag));
		assertTrue(monster5.holdsItem(bag));
		assertTrue(monster5.holdsItem(armorBody));
		assertTrue(monster5.holdsItem(weaponRightHand));
		assertTrue(monster5.holdsItem(weaponLeftHand));
		assertTrue(monster5.holdsItem(armorBackpackBag));

		assertTrue(monster5.getHitPoints() == 97);
		assertTrue(monster5.getMaxHitpoints() == 100);
		assertTrue(monster5.getStrength() == 20);
		assertTrue(monster5.isFighting() == false);
		assertTrue(monster5.getNumberOfAnchors() == 10);
	}

	@Test
	public void testConstructorNormal() {
		Monster monster = new Monster("Geer't", 100, 20);


		assertTrue(monster.getHitPoints() == 97);
		assertTrue(monster.getMaxHitpoints() == 100);
		assertTrue(monster.getStrength() == 20);
	}

	@Test 
	public void testConstructorName() throws Exception
	{
		new Monster("James o'Hara", 100, 10);
	}

	@Test
	public void testConstructorNameSpace() throws Exception
	{
		new Monster("P iet   er", 100, 10);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameCapital() throws Exception
	{
		new Monster("pieter", 100, 10);
	}

	@Test
	public void testConstructorNameThreeApostrophes() throws Exception
	{
		new Monster("Pi'e'te'r", 100, 10);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameStrangeSymbols() throws Exception
	{
		new Monster("Piùeter", 100, 10);
	}

	@Test(expected=IllegalArgumentException.class)  
	public void testConstructorNameColon() throws Exception
	{
		new Monster("Pi: e''ter", 100, 10);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameNull() throws Exception
	{
		new Monster(null, 100, 10);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameEmpty() throws Exception
	{
		new Monster("", 100, 10);
	}

	@Test(expected=IllegaleToestandsUitzondering.class) 
	public void testChangeNameEmpty() throws Exception
	{
		monsterStandard.setName("");
	}

	@Test
	public void testGetCapacityDouble() {
		assertTrue(monsterStandard.getCapacity(0).getNumeral() == 0);
		assertTrue(monsterStandard.getCapacity(2).getNumeral() == 18);
		assertTrue(monsterStandard.getCapacity(14).getNumeral() == 126);
		assertTrue(monsterStandard.getCapacity(52.9).getNumeral()-476.1<0.001);	
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetCapacityNegatifDouble() {
		monsterStandard.getCapacity(-9);
	}
	

	@Test
	public void testGetCapacityBetweenElevenAndTwenty(){
		monsterStandard.divideStrength(5);
		monsterStandard.multiplyStrength(7);
		assertTrue(monsterStandard.getCapacity().getNumeral() == 126);
	}

	@Test
	public void testGetCapacityBetweenTwentyAndInfinity(){
		monsterStandard.multiplyStrength(100);
		monsterStandard.divideStrength(19); //52.9
		assertTrue(monsterStandard.getCapacity().getNumeral()-473.68 < 0.001);
	}

	@Test
	public void testCanFightOpponent() {
		assertTrue(monsterStandard.canFightOpponent(new Monster("Hoi")));
		assertTrue(monsterStandard.canFightOpponent(monsterWithEquipment));
		assertTrue(monsterStandard.canFightOpponent(new Hero("Hoi", 100, 10)));
	}

	@Test
	public void testWants() {
		assertTrue(monsterWithEquipment.wants(new Purse()));
		assertTrue(monsterWithEquipment.wants(new Backpack()));		
	}

	@Test
	public void testCanHoldItemItemNoAnchors() {
		Monster monster = new Monster("Pieter", 100, 100, new Item[0]);
		assertFalse(monster.canHoldItem(new Weapon(), -1));
		assertFalse(monster.canHoldItem(new Weapon(), 0));
		assertFalse(monster.canHoldItem(new Weapon(), 1));
		assertFalse(monster.canHoldItem(new Weapon(), 2));
		assertFalse(monster.canHoldItem(new Weapon(), 3));
		assertFalse(monster.canHoldItem(new Weapon(), 4)); //belt
		assertFalse(monster.canHoldItem(new Weapon(), 5)); // don't exist
		assertFalse(monster.canHoldItem(new Weapon(), 6)); // don't exist
	}
	
	@Test
	public void testCanHoldItemItemWithAnchors() {
		Monster monster = new Monster("Pieter", 100, 100, new Item[4]);
		assertFalse(monster.canHoldItem(new Backpack(), -1));
		assertTrue(monster.canHoldItem(new Backpack(), 0));
		assertTrue(monster.canHoldItem(new Backpack(), 1));
		assertTrue(monster.canHoldItem(new Backpack(), 2));
		assertTrue(monster.canHoldItem(new Backpack(), 3));
		assertFalse(monster.canHoldItem(new Backpack(), 4)); //belt
		assertFalse(monster.canHoldItem(new Backpack(), 5)); // don't exist
		assertFalse(monster.canHoldItem(new Backpack(), 6)); // don't exist
	}

	@Test
	public void testCanHoldItemItem() {
		Monster monster = new Monster("Pieter", 100, 100, new Item[4]);
		assertTrue(monster.canHoldItem(new Weapon()));
		assertTrue(monster.canHoldItem(new Armor(10)));
		assertTrue(monster.canHoldItem(new Backpack()));
		assertTrue(monster.canHoldItem(new Purse()));

		assertFalse(monster.canHoldItem(null));
	}

	@Test
	public void testCanHoldItemTerminatedItem() {
		Armor armor = (new Armor(10));
		armor.terminate();
		assertFalse(monsterStandard.canHoldItem(armor));
	}

	@Test
	public void testCanHoldItemToHeavy() {
		Armor armor = (new Armor(10, new Weight(1000000), 100));
		assertFalse(monsterStandard.canHoldItem(armor));
	}

	@Test
	public void testCanHoldItemItemLocationEquipment() {
		Monster monster = new Monster("Pieter", 100, 100, new Item[4]);
		Item itemToAdd = new Weapon();
		assertTrue(monster.canHoldItem(itemToAdd, 0));
		assertTrue(monster.canHoldItem(itemToAdd, 1));
		assertTrue(monster.canHoldItem(itemToAdd, 2));
		assertTrue(monster.canHoldItem(itemToAdd, 3));
		assertFalse(monster.canHoldItem(itemToAdd, 4));

		itemToAdd = new Armor(15);
		assertTrue(monster.canHoldItem(itemToAdd, 0));
		assertTrue(monster.canHoldItem(itemToAdd, 1));
		assertTrue(monster.canHoldItem(itemToAdd, 2));
		assertTrue(monster.canHoldItem(itemToAdd, 3));
		assertFalse(monster.canHoldItem(itemToAdd, 4));


		itemToAdd = new Purse();
		assertTrue(monster.canHoldItem(itemToAdd, 0));
		assertTrue(monster.canHoldItem(itemToAdd, 1));
		assertTrue(monster.canHoldItem(itemToAdd, 2));
		assertTrue(monster.canHoldItem(itemToAdd, 3));
		assertFalse(monster.canHoldItem(itemToAdd, 4));


		itemToAdd = new Backpack();
		assertTrue(monster.canHoldItem(itemToAdd, 0));
		assertTrue(monster.canHoldItem(itemToAdd, 1));
		assertTrue(monster.canHoldItem(itemToAdd, 2));
		assertTrue(monster.canHoldItem(itemToAdd, 3));
		assertFalse(monster.canHoldItem(itemToAdd, 4));
	}

	@Test
	public void testAddItemItemInt() {
		Monster monster = new Monster("Pieter", 100, 100, new Item[4]);
		Item item = new Weapon();
		item.moveTo(monster, 3);
		assertEquals(item, monster.getItem(3));

		Monster monster1 = new Monster("Antoine", 100, 100, new Item[10]);
		item.moveTo(monster1);
		assertTrue(monster1.holdsItem(item));
		item.moveTo(monster, 2);
		assertEquals(item, monster.getItem(2));
		assertTrue(monster.holdsItem(item));
		assertFalse(monster1.holdsItem(item));
	}

	@Test
	public void testGetNumberOfAnchors() {
		assertTrue(monsterWithEquipment.getNumberOfAnchors() == 10);
	}

	@Test
	public void testHasValidItems() {
		assertTrue(monsterStandard.hasValidItems());
		assertTrue(monsterWithEquipment.hasValidItems());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveItem() {
		Item item = monsterWithEquipment.getItem(1);
		monsterWithEquipment.removeItem(item);
	}

	@Test 
	public void testIsValidHitPointsToLarge()
	{
		assertFalse(monsterStandard.isValidHitPoints(101));
	}

	@Test 
	public void testIsValidHitPointsNormalNotPrime()
	{
		assertFalse(monsterStandard.isFighting());
		assertFalse(monsterStandard.isValidHitPoints(50));
	}

	@Test 
	public void testIsValidHitPointsNormalPrime()
	{
		assertFalse(monsterStandard.isFighting());
		assertTrue(monsterStandard.isValidHitPoints(13));
	}

	@Test 
	public void testIsValidHitPointsMinus()
	{
		assertFalse(monsterStandard.isValidHitPoints(-5));
	}

	@Test 
	public void testIsValidHitPointsFightingValid()
	{
		Monster creep = new Monster("Testje");
		monsterWithEquipment.hit(creep);
		assertTrue(monsterWithEquipment.isFighting());
		assertTrue(monsterWithEquipment.isValidHitPoints(9));
	}

	@Test 
	public void testIsValidHitPointsZero()
	{
		Monster creep = new Monster("Testje");
		monsterWithEquipment.hit(creep);
		assertTrue(monsterWithEquipment.isValidHitPoints(0));
	}

	@Test
	public void testIsFighting() {
		Monster creep = new Monster("Testje");
		monsterWithEquipment.hit(creep);
		assertTrue(monsterWithEquipment.isFighting());
	}



	@Test
	public void testMultiplyStrengthNegInt(){
		monsterStandard.multiplyStrength(-5);
		assertTrue(monsterStandard.getStrength() == 50);
	}

	@Test
	public void testMultiplyStrengthPosInt(){
		monsterStandard.multiplyStrength(5);
		assertTrue(monsterStandard.getStrength() == 50);
	}

	@Test
	public void testMultiplyStrengthZero(){
		monsterStandard.multiplyStrength(0);
		assertTrue(monsterStandard.getStrength() == 0);
	}

	@Test
	public void testDivideStrengthNegInt(){
		monsterStandard.divideStrength(-5);
		assertTrue(monsterStandard.getStrength() == 2);
	}

	@Test
	public void testDivideStrengthPosInt(){
		monsterStandard.divideStrength(5);
		assertTrue(monsterStandard.getStrength() == 2);
	}

	@Test
	public void testDivideStrengthPosIntRoundDown(){
		monsterStandard.divideStrength(3);
		assertTrue(Math.abs(monsterStandard.getStrength()- 3.33) < 0.001);
	}


	@Test
	public void testDivideStrengthPosIntRoundUp(){
		monsterStandard.divideStrength(6);
		assertTrue(Math.abs(monsterStandard.getStrength()- 1.67) < 0.001);
	}

	@Test
	public void testDivideStrengthZero(){
		monsterStandard.divideStrength(0);
		assertTrue(monsterStandard.getStrength() == 10 );
	}

	@Test
	public void testGetAverageStrength() {
		assertTrue(Hero.getAverageStrength()==10);
	}

	@Test
	public void testHitDeadHero(){
		Monster attacker = new Monster("Creep", 100, 120);
		Hero defender = new Hero("Jero: en", 50, 49);
		defender.switchArmor(new Armor(13, 0));
		assertTrue(defender.getProtection() - 10 < 0.0005);
		assertTrue(attacker.getDamage() > defender.getHitPoints());

		Weight oldWeightAttacker = attacker.getWeight();

		attacker.hit(defender);
		assertTrue(attacker.isFighting());
		assertTrue(defender.isFighting());
		if(defender.getHitPoints() == 0) {
			assertTrue(attacker.getHitPoints() <= 100);
			assertTrue(attacker.getHitPoints() >= 97);
			assertTrue(defender.getHitPoints() == 0);
			assertTrue(defender.isTerminated());
			assertTrue(defender.getWeight().compareTo(new Weight(0)) == 0);
			assertTrue(oldWeightAttacker.compareTo(attacker.getWeight())<= 0);
		}
	}

	@Test
	public void testHitSurviveHero(){
		Monster attacker = new Monster("Creep", 100, 20);
		Hero defender = new Hero("Jero: en", 150, 49);
		
		Weight oldWeightAttacker = attacker.getWeight();
		int oldHitPoints = defender.getHitPoints();
		attacker.hit(defender);
		assertTrue(attacker.isFighting());
		assertTrue(defender.isFighting());
		//if the hit is a success the defender will be dead.
		assertTrue(attacker.getDamage() < defender.getHitPoints());
		assertFalse(defender.isTerminated());

		if(defender.getHitPoints() == oldHitPoints) {
			//in this case the hit failed.
			// System.out.println("failed hit");
			assertTrue(attacker.getHitPoints() == 97);
			assertTrue(defender.getHitPoints() == 149);
		}
		else {
			// System.out.println("hit succeded");
			assertTrue(attacker.getHitPoints() <= 100);
			assertTrue(attacker.getHitPoints() >= 97);
			assertTrue(defender.getHitPoints() < 149);
			assertTrue(oldWeightAttacker.compareTo(attacker.getWeight())== 0);
		}
	}
	
	@Test
	public void testHitDeadMonster(){
		Monster attacker = new Monster("Creep", 100, 120);
		Monster defender = new Monster("Jero en", 50, 49);

		assertTrue(attacker.getDamage() > defender.getHitPoints());

		Weight oldWeightAttacker = attacker.getWeight();

		attacker.hit(defender);
		assertTrue(attacker.isFighting());
		assertTrue(defender.isFighting());

		if(defender.isTerminated()) {
			assertTrue(attacker.getHitPoints() == 97);
			assertTrue(defender.getHitPoints() == 0);
			assertTrue(defender.isTerminated());
			assertTrue(defender.getWeight().compareTo(new Weight(0)) == 0);
			assertTrue(oldWeightAttacker.compareTo(attacker.getWeight())<= 0);
		}
	}

	@Test
	public void testHitSurviveMonster(){
		Monster attacker = new Monster("Creep", 100, 20);
		Monster defender = new Monster("Jero en", 150, 49);
		
		Weight oldWeightAttacker = attacker.getWeight();
		int oldHitPoints = defender.getHitPoints();
		attacker.hit(defender);
		assertTrue(attacker.isFighting());
		assertTrue(defender.isFighting());
		//if the hit is a success the defender will be dead.
		assertTrue(attacker.getDamage() < defender.getHitPoints());
		assertFalse(defender.isTerminated());

		if(defender.getHitPoints() == oldHitPoints) {
			//in this case the hit failed.
			//System.out.println("failed hit");
			assertTrue(attacker.getHitPoints() == 97);
			assertTrue(defender.getHitPoints() == 149);
		}
		else {
			//System.out.println("hit succeded");
			assertTrue(attacker.getHitPoints() <= 100);
			assertTrue(attacker.getHitPoints() >= 97);
			assertTrue(defender.getHitPoints() < 149);
			assertTrue(oldWeightAttacker.compareTo(attacker.getWeight())== 0);
		}
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testHitNullPointer(){
		Monster defender = null;
		Hero attacker = new Hero("Jero: en", 100, 20);

		attacker.hit(defender);
	}

	@Test
	public void testIsSupremeHolder() {
		assertTrue(monsterStandard.isSupremeHolder());
		assertTrue(monsterWithEquipment.isSupremeHolder());
	}

	@Test
	public void testIsValidLocation() {
		for(int i = 0; i < monsterStandard.getNumberOfAnchors(); i++) {
			assertTrue(monsterStandard.isValidLocation(i));
		}
		assertFalse(monsterStandard.isValidLocation(-1));
		assertFalse(monsterStandard.isValidLocation(monsterStandard.getNumberOfAnchors()));
	}
}
