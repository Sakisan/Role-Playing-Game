package rpgGame;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class HeroTest {
	private Hero heroStandard;
	private Hero heroWithEquipment;
	
	@Before
	public void setUp() throws Exception {
		heroStandard = new Hero("Pieter", 100, 10);
		
		//equipment for hero
		Weapon weaponLeftHand = new Weapon(5, new Weight(5));
		Weapon weaponRightHand = new Weapon(10, new Weight(5));
		Armor armorBody = new Armor(10, 10, new Weight(5), 200);
		Backpack bag = new Backpack();
			Armor armorBag = new Armor(15);
			Weapon weaponBag = new Weapon();
			Backpack backpackBag = new Backpack();
				
				Purse purseBackpackBag = new Purse();
		Purse purseBelt = new Purse();
		
		armorBag.moveTo(bag);
		weaponBag.moveTo(bag);
		backpackBag.moveTo(bag);
			purseBackpackBag.moveTo(backpackBag);
		HashMap<LocationEquipment, Item> gear = new HashMap<LocationEquipment, Item>();
		gear.put(LocationEquipment.LeftHand, weaponLeftHand);
		gear.put(LocationEquipment.RightHand, weaponRightHand);
		gear.put(LocationEquipment.Back,bag);
		gear.put(LocationEquipment.Body, armorBody);
		gear.put(LocationEquipment.Belt, purseBelt);
		
		heroWithEquipment = new Hero("Antoine", 10, 100, gear);
	
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
			// armorBackpackBag.moveTo(backpackBag); // a hero can only wear 2 armors
			purseBackpackBag.moveTo(backpackBag);
		
		HashMap<LocationEquipment, Item> gear = new HashMap<LocationEquipment, Item>();
		gear.put(LocationEquipment.LeftHand, weaponLeftHand);
		gear.put(LocationEquipment.RightHand, weaponRightHand);
		gear.put(LocationEquipment.Back,bag);
		gear.put(LocationEquipment.Body, armorBody);
		gear.put(LocationEquipment.Belt, purseBelt);
		
		DukatAmount totalPrice = new DukatAmount(0);
		Weight totalWeight = new Weight(0);
		Collection<Item> equipment = gear.values();
		for (Iterator<Item> iterator = equipment.iterator(); iterator.hasNext();) {
			Item item = (Item)iterator.next();
			totalWeight = totalWeight.add(item.getWeight());
			totalPrice.add(item.getPrice().getAmount());
		}
		
		Hero hero5 = new Hero("Geer't", 100, 20, gear);
		assertTrue(hero5.getName() == "Geer't");

		
		// assertEquals(expected, actual)
		
		assertTrue(hero5.getCapacity().getNumeral() == 400);
		assertEquals(totalWeight, hero5.getWeight());
		assertEquals(totalPrice, hero5.getPrice());
		assertEquals(weaponLeftHand, hero5.getItem(LocationEquipment.LeftHand));
		assertEquals(weaponRightHand, hero5.getItem(LocationEquipment.RightHand));
		assertEquals(armorBody ,hero5.getItem(LocationEquipment.Body));
		assertEquals(purseBelt, hero5.getItem(LocationEquipment.Belt));
		assertEquals(bag, hero5.getItem(LocationEquipment.Back));
		
		assertEquals(weaponLeftHand, hero5.getItem(LocationEquipment.LeftHand.getFollowNumber()));
		assertEquals(weaponRightHand, hero5.getItem(LocationEquipment.RightHand.getFollowNumber()));
		assertEquals(armorBody ,hero5.getItem(LocationEquipment.Body.getFollowNumber()));
		assertEquals(purseBelt, hero5.getItem(LocationEquipment.Belt.getFollowNumber()));
		assertEquals(bag, hero5.getItem(LocationEquipment.Back.getFollowNumber()));
		
		assertTrue(hero5.holdsItem(purseBelt));
		assertTrue(hero5.holdsItem(purseBackpackBag));
		assertTrue(hero5.holdsItem(backpackBag));
		assertTrue(hero5.holdsItem(weaponBag));
		assertTrue(hero5.holdsItem(armorBag));
		assertTrue(hero5.holdsItem(bag));
		assertTrue(hero5.holdsItem(armorBody));
		assertTrue(hero5.holdsItem(weaponRightHand));
		assertTrue(hero5.holdsItem(weaponLeftHand));
		assertFalse(hero5.holdsItem(armorBackpackBag));
		
		assertTrue(hero5.getHitPoints() == 97);
		assertTrue(hero5.getMaxHitpoints() == 100);
		assertTrue(hero5.getStrength() == 20);
		assertTrue(hero5.isFighting() == false);
		assertTrue(hero5.getNumberOfAnchors() == 5);
	}
	
	@Test
	public void testExtendedConstructoreEmptyItems() throws Exception {
		Hero hero5 = new Hero("Geer't", 695, 20, null);

		for (LocationEquipment location:LocationEquipment.values()) {
			if(location == LocationEquipment.Body) {
				assertTrue(hero5.getItem(location).getClass() == Armor.class);
				assertTrue(hero5.holdsItem(hero5.getItem(location)));
			}
			else if(location == LocationEquipment.Belt){
				assertTrue(hero5.getItem(location).getClass() == Purse.class);
				assertTrue(hero5.holdsItem(hero5.getItem(location)));
			}
			else {
				assertTrue(hero5.getItem(location)== null);
			}
		}
		assertTrue(hero5.getHitPoints() == 691);
		assertTrue(hero5.getMaxHitpoints() == 695);
		assertTrue(hero5.getStrength() == 20);
	}
	
	@Test
	public void testConstructorNormal() {
			Hero hero5 = new Hero("Geer't", 100, 20);
			
			for (LocationEquipment location:LocationEquipment.values()) {
				if(location == LocationEquipment.Body) {
					assertTrue(hero5.getItem(location).getClass() == Armor.class);
					assertTrue(hero5.holdsItem(hero5.getItem(location)));
				}
				else if(location == LocationEquipment.Belt){
					assertTrue(hero5.getItem(location).getClass() == Purse.class);
					assertTrue(hero5.holdsItem(hero5.getItem(location)));
				}
				else {
					assertTrue(hero5.getItem(location)== null);
				}
			}
			
			assertTrue(hero5.getHitPoints() == 97);
			assertTrue(hero5.getMaxHitpoints() == 100);
			assertTrue(hero5.getStrength() == 20);
	}
	
	@Test 
	public void testConstructorName() throws Exception
	{
		new Hero("James o'Hara", 100, 10);
	}
	
	@Test
	public void testConstructorNameSpace() throws Exception
	{
		new Hero("P iet   er", 100, 10);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameCapital() throws Exception
	{
		new Hero("pieter", 100, 10);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameThreeApostrophes() throws Exception
	{
		new Hero("Pi'e'te'r", 100, 10);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameStrangeSymbols() throws Exception
	{
		new Hero("Piùeter", 100, 10);
	}
	
	@Test 
	public void testConstructorNameColon() throws Exception
	{
		new Hero("Pi: e''ter", 100, 10);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameNull() throws Exception
	{
		new Hero(null, 100, 10);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testConstructorNameEmpty() throws Exception
	{
		new Hero("", 100, 10);
	}
	
	@Test(expected=IllegaleToestandsUitzondering.class) 
	public void testChangeNameEmpty() throws Exception
	{
		heroStandard.setName("");
	}
	
	@Test
	public void testGetCapacityDouble() {
		assertTrue(heroStandard.getCapacity(0).getNumeral() == 0);
		assertTrue(heroStandard.getCapacity(2).getNumeral() == 20);
		assertTrue(heroStandard.getCapacity(14).getNumeral() == 175);
		assertTrue(heroStandard.getCapacity(52.9).getNumeral() == 38400);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCapacityNegatifDouble() {
		heroStandard.getCapacity(-9);
	}
	@Test
	public void testGetCapacityStrengthLessThanOne(){
		heroStandard.divideStrength(11);
		assertTrue(heroStandard.getCapacity().getNumeral() == 0);
	}
	
	@Test
	public void testGetCapacityStrengthBetweenTenAndZero(){
		heroStandard.divideStrength(5);
		assertTrue(heroStandard.getCapacity().getNumeral() == 20);
	}
	
	@Test
	public void testGetCapacityBetweenElevenAndTwenty(){
		heroStandard.divideStrength(5);
		heroStandard.multiplyStrength(7);
		assertTrue(heroStandard.getCapacity().getNumeral() == 175);
	}
	
	@Test
	public void testGetCapacityBetweenTwentyAndInfinity(){
		heroStandard.multiplyStrength(100);
		heroStandard.divideStrength(19); //52.9
		assertTrue(heroStandard.getCapacity().getNumeral() == 38400);
	}
	
	@Test
	public void testGetProtection() {
		assertTrue(heroStandard.getProtection() == 
			(10+((Armor)heroStandard.getItem(LocationEquipment.Body)).getValue()));
		assertTrue(heroWithEquipment.getProtection() == 
			(10+((Armor)heroWithEquipment.getItem(LocationEquipment.Body)).getValue()));
	}

	@Test
	public void testCanFightOpponent() {
		assertTrue(heroStandard.canFightOpponent(new Monster("Hoi")));
		assertFalse(heroStandard.canFightOpponent(heroWithEquipment));
	}

	@Test
	public void testWants() {
		assertTrue(heroWithEquipment.wants(new Armor(10, 1000, new Weight(5), 100)));
		assertFalse(heroWithEquipment.wants(new Armor(10, 1, new Weight(5), 100)));
		assertTrue(heroWithEquipment.wants(new Weapon(1000, new Weight(5))));
		assertFalse(heroWithEquipment.wants(new Weapon(1, new Weight(5))));
		assertTrue(heroWithEquipment.wants(new Purse()));
		assertTrue(heroWithEquipment.wants(new Backpack()));		
	}

	@Test
	public void testGetWeight() {
		//already tested in the test for most extended constructor
	}

	@Test
	public void testCanHoldItemItemDifferentInt() {
		heroStandard.getItem(LocationEquipment.Body).drop();
		assertFalse(heroStandard.canHoldItem(new Weapon(), -1));
		assertTrue(heroStandard.canHoldItem(new Weapon(), 0));
		assertTrue(heroStandard.canHoldItem(new Weapon(), 1));
		assertTrue(heroStandard.canHoldItem(new Weapon(), 2));
		assertTrue(heroStandard.canHoldItem(new Weapon(), 3));
		assertFalse(heroStandard.canHoldItem(new Weapon(), 4)); //belt
		assertFalse(heroStandard.canHoldItem(new Weapon(), 5)); // don't exist
		assertFalse(heroStandard.canHoldItem(new Weapon(), 6)); // don't exist
	}
	
	@Test
	public void testCanHoldItemItem() {
		heroStandard.getItem(LocationEquipment.Body).drop();
		assertTrue(heroStandard.canHoldItem(new Weapon()));
		assertTrue(heroStandard.canHoldItem(new Armor(10)));
		assertTrue(heroStandard.canHoldItem(new Backpack()));
		assertTrue(heroStandard.canHoldItem(new Purse()));
		
		assertFalse(heroStandard.canHoldItem(null));
		
		heroWithEquipment.getItem(LocationEquipment.Body).drop();
		assertTrue(heroWithEquipment.canHoldItem(new Weapon()));
		assertTrue(heroWithEquipment.canHoldItem(new Armor(10)));
		assertTrue(heroWithEquipment.canHoldItem(new Backpack()));
		assertTrue(heroWithEquipment.canHoldItem(new Purse()));
	}
	
	@Test
	public void testCanHoldItemItemFullLocations() {
		assertFalse(heroWithEquipment.canHoldItem(new Weapon()));
		assertFalse(heroWithEquipment.canHoldItem(new Armor(10)));
		assertFalse(heroWithEquipment.canHoldItem(new Backpack()));
		assertFalse(heroWithEquipment.canHoldItem(new Purse()));
	}
	
	@Test
	public void testCanHoldItemTerminatedItem() {
		Armor armor = (new Armor(10));
		armor.terminate();
		assertFalse(heroStandard.canHoldItem(armor));
	}
	
	@Test
	public void testCanHoldItemToHeavy() {
		Armor armor = (new Armor(10, new Weight(1000000), 100));
		assertFalse(heroStandard.canHoldItem(armor));
	}
	
	@Test
	public void testCanHoldItemItemLocationEquipment() {
		Item itemToAdd = new Weapon();
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Back));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Belt));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.LeftHand));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Body));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.RightHand));
		
		itemToAdd = new Armor(15);
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Back));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Belt));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.LeftHand));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Body));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.RightHand));
		
		itemToAdd = new Purse();
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Back));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Belt));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.LeftHand));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Body));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.RightHand));
		
		itemToAdd = new Backpack();
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Back));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Belt));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.LeftHand));
		assertFalse(heroStandard.canHoldItem(itemToAdd, LocationEquipment.Body));
		assertTrue(heroStandard.canHoldItem(itemToAdd, LocationEquipment.RightHand));

	}
	
	@Test
	public void testGetItemInt() {
		//tested in the most extended constructor
	}

	@Test
	public void testAddItemItemInt() {
		Item item = new Weapon();
		item.moveTo(heroStandard, LocationEquipment.LeftHand);
		assertEquals(item, heroStandard.getItem(LocationEquipment.LeftHand));
		assertEquals(item, heroStandard.getItem(LocationEquipment.LeftHand.getFollowNumber()));
		
		Hero hero1 = new Hero("Test",100,100);
		item.moveTo(hero1);
		item.moveTo(heroStandard, (LocationEquipment.LeftHand.getFollowNumber()));
		assertEquals(item, heroStandard.getItem(LocationEquipment.LeftHand));
		assertEquals(item, heroStandard.getItem(LocationEquipment.LeftHand.getFollowNumber()));
		
	}

	@Test
	public void testGetNumberOfAnchors() {
		assertTrue(heroStandard.getNumberOfAnchors() == LocationEquipment.values().length);
	}

	@Test
	public void testHasValidItems() {
		assertTrue(heroStandard.hasValidItems());
		assertTrue(heroWithEquipment.hasValidItems());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveItem() {
		Item item = heroWithEquipment.getItem(1);
		heroWithEquipment.removeItem(item);
	}

	@Test
	public void testSwitchArmor() {
		Item oldArmor = heroStandard.getItem(LocationEquipment.Body);
		Armor armor = new Armor(10, new Weight(1), 10);
		heroStandard.switchArmor(armor);
		assertEquals(armor, heroStandard.getItem(LocationEquipment.Body));
		assertTrue(heroStandard.holdsItem(oldArmor));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSwitchArmorNull() {
		Armor armor = null;
		heroStandard.switchArmor(armor);
	}

	@Test 
	public void testIsValidHitPointsToLarge()
	{
		assertFalse(heroStandard.isValidHitPoints(101));
	}
	
	@Test 
	public void testIsValidHitPointsNormalNotPrime()
	{
		assertFalse(heroStandard.isFighting());
		assertFalse(heroStandard.isValidHitPoints(50));
	}
	
	@Test 
	public void testIsValidHitPointsNormalPrime()
	{
		assertFalse(heroStandard.isFighting());
		assertTrue(heroStandard.isValidHitPoints(13));
	}
	
	@Test 
	public void testIsValidHitPointsMinus()
	{
		assertFalse(heroStandard.isValidHitPoints(-5));
	}
	
	@Test 
	public void testIsValidHitPointsFightingValid()
	{
		Monster creep = new Monster("Testje");
		heroWithEquipment.hit(creep);
		assertTrue(heroWithEquipment.isFighting());
		assertTrue(heroWithEquipment.isValidHitPoints(9));
	}
	
	@Test 
	public void testIsValidHitPointsZero()
	{
		Monster creep = new Monster("Testje");
		heroWithEquipment.hit(creep);
		assertTrue(heroWithEquipment.isValidHitPoints(0));
	}

	@Test
	public void testIsFighting() {
		Monster creep = new Monster("Testje");
		heroWithEquipment.hit(creep);
		assertTrue(heroWithEquipment.isFighting());
	}


	
	@Test
	public void testMultiplyStrengthNegInt(){
		heroStandard.multiplyStrength(-5);
		assertTrue(heroStandard.getStrength() == 50);
	}
	
	@Test
	public void testMultiplyStrengthPosInt(){
		heroStandard.multiplyStrength(5);
		assertTrue(heroStandard.getStrength() == 50);
	}
	
	@Test
	public void testMultiplyStrengthZero(){
		heroStandard.multiplyStrength(0);
		assertTrue(heroStandard.getStrength() == 0);
	}
	
	@Test
	public void testDivideStrengthNegInt(){
		heroStandard.divideStrength(-5);
		assertTrue(heroStandard.getStrength() == 2);
	}
	
	@Test
	public void testDivideStrengthPosInt(){
		heroStandard.divideStrength(5);
		assertTrue(heroStandard.getStrength() == 2);
	}
	
	@Test
	public void testDivideStrengthPosIntRoundDown(){
		heroStandard.divideStrength(3);
		assertTrue(Math.abs(heroStandard.getStrength()- 3.33) < 0.001);
	}
	

	@Test
	public void testDivideStrengthPosIntRoundUp(){
		heroStandard.divideStrength(6);
		assertTrue(Math.abs(heroStandard.getStrength()- 1.67) < 0.001);
	}
	
	@Test
	public void testDivideStrengthZero(){
		heroStandard.divideStrength(0);
		assertTrue(heroStandard.getStrength() == 10 );
	}
	
	@Test
	public void testGetAverageStrength() {
		assertTrue(Hero.getAverageStrength()==10);
	}

	@Test
	public void testHitDead(){
		Monster defender = new Monster("Creep", 50, 49);
		Hero attacker = new Hero("Jero: en", 100, 112);
		Weight oldWeightAttacker = attacker.getWeight();
		assertTrue(attacker.getDamage() > defender.getHitPoints());
		
		
		attacker.hit(defender);
		assertTrue(attacker.isFighting());
		assertTrue(defender.isFighting());
		
		
		if(defender.getHitPoints() != 0) {
			//in this case the hit failed.
			//System.out.println("survive");
			assertTrue(attacker.getHitPoints() == 97);
			assertTrue(defender.getHitPoints() == 47);
			assertFalse(defender.isTerminated());
		}
		else {
			// System.out.println("dead");
			assertTrue(attacker.getHitPoints() <= 100);
			assertTrue(attacker.getHitPoints() >= 97);
			assertTrue(defender.getHitPoints() == 0);
			assertTrue(defender.isTerminated());
			assertTrue(oldWeightAttacker.compareTo(attacker.getWeight())<= 0);
		}
	}
	
	@Test
	public void testHitSurvive(){
		Monster defender = new Monster("Creep", 50, 49);
		Hero attacker = new Hero("Jero: en", 100, 20);
		
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
			assertTrue(defender.getHitPoints() == 47);
		}
		else {
			// System.out.println("hit succeded");
			assertTrue(attacker.getHitPoints() <= 100);
			assertTrue(attacker.getHitPoints() >= 97);
			assertTrue(defender.getHitPoints() < 47);
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
		assertTrue(heroStandard.isSupremeHolder());
		assertTrue(heroWithEquipment.isSupremeHolder());
	}

	@Test
	public void testIsValidLocation() {
		for(int i = 0; i < heroStandard.getNumberOfAnchors(); i++) {
			assertTrue(heroStandard.isValidLocation(i));
		}
		assertFalse(heroStandard.isValidLocation(-1));
		assertFalse(heroStandard.isValidLocation(6));
	}
}
