package rpgGame;


import org.junit.*;

import static org.junit.Assert.*;

public class ItemTest {
	
private Armor armor;
private Weapon weapon;
private Backpack backpack, backpack2;
private Hero hero;
private Purse purse;
	
	@Before
	public void setUp() throws Exception {
		armor = new Armor(100,99, new Weight(5), 200);
		weapon = new Weapon(99, new Weight(5));
		backpack = new Backpack();
		backpack2 = new Backpack();
		purse = new Purse();
		hero = new Hero("James o'Hara", 100, 10);
	}
	
	@Test
	public void testArmorID() throws Exception {
		assertTrue( MathHelp.isPrime(armor.getID()));
	}
	
	@Test
	public void testWeaponID() throws Exception {
		assertTrue( weapon.getID() % 3 == 0 && weapon.getID() % 2 == 0 && weapon.getID() > 0);
	}
	
	@Test
	public void testWeaponID1000() throws Exception {
		long[] ids = new long[1001]; 
		Weapon weapon = new Weapon();
		long first = weapon.getID();
		for(int i = 0; i < 1000; i++){
			weapon = new Weapon();
			ids[(int)((weapon.getID() - first )/ 6)]++;
		}
		for(int i = 0; i < 1001; i++)
			assertTrue(ids[i] < 2);
	}
	
	@Test
	public void testBackPackID() throws Exception {
		assertTrue( MathHelp.isSumOfBinomials(backpack.getID()));
	}
	
	@Test
	public void testPurseID() throws Exception {
		assertTrue(MathHelp.isFibonacciNumber(purse.getID()));
	}
	
	@Test
	public void testWeight() throws Exception {
		Armor armor = new Armor(20, new Weight(15), 100);
		assertTrue(armor.getWeight().compareTo(new Weight(15)) == 0);
		Backpack backpack = new Backpack(new Weight(100), new Weight(7));
		assertTrue(backpack.getBagsWeight().compareTo(new Weight(7)) == 0);
		Purse purse = new Purse();
		assertTrue(purse.getWeight().compareTo(new Weight(1))==0);
	}
	
	@Test
	public void testWeaponValue() throws Exception {
		Weapon weapon = new Weapon(60, new Weight(3));
		assertTrue(weapon.getValue() * 2 == weapon.getPrice().getAmount());
	}
	
	@Test
	public void testArmorValue() throws Exception {
		int protection = 70; // maximum 100
		int maxPrice = 1000; // maximum 1000
		armor = new Armor(100,protection, new Weight(5), maxPrice);
		assertTrue(armor.getPrice().getAmount() == maxPrice * protection / armor.getMaximumValue() );
		assertTrue(armor.getPrice().getAmount() <= 1000);
		assertTrue(armor.getPrice().getAmount() % 2 == 0);
	}
	
	@Test
	public void testBackpackValue() throws Exception {
		backpack.setPrice(new DukatAmount(20));
		assertTrue(backpack.getPrice().getAmount() == 20);
		weapon = new Weapon(50, new Weight(3), new DukatAmount(50));
		purse.addDukats(10);
		weapon.moveTo(backpack);
		purse.moveTo(backpack);
		assertTrue(backpack.getPrice().getAmount() == 20 + 50 + 10);
		backpack2.setPrice(new DukatAmount(30));
		backpack.moveTo(backpack2);
		assertTrue(backpack2.getPrice().getAmount() == 20 + 50 + 10 + 30);	
	}
	
	@Test
	public void testPurseValue() throws Exception {
		purse.addDukats(new DukatAmount(30).getAmount());
		assertTrue(purse.getPrice().getAmount() == 30);
	}

	@Test
	public void testDirectHolder() throws Exception {

		assertTrue(armor.getDirectHolder() == World.world);
		assertTrue(weapon.getDirectHolder() == World.world);
		assertTrue(backpack.getDirectHolder() == World.world);
		
		armor.moveTo(hero);
		weapon.moveTo(backpack);
		
		assertTrue(armor.getDirectHolder() == hero);
		assertTrue(weapon.getDirectHolder() == backpack);

	}
	
	@Test
	public void testHolder() throws Exception {
		armor.moveTo(backpack);
		backpack.moveTo(hero);
		assertTrue(armor.getHolder() == hero);
		
		//andere volgorde
		weapon.moveTo(backpack);
		assertTrue(weapon.getHolder() == hero);
		
		weapon.moveTo(backpack2);
		assertTrue(weapon.getHolder() == World.world);
		
		backpack2.moveTo(backpack);
		assertTrue(weapon.getHolder() == hero);
		
	}
	
	@Test (expected=IllegaleToestandsUitzondering.class) 
	public void testLoopHolder() throws Exception {
		backpack.moveTo(backpack2);
		backpack2.moveTo(backpack);
	}
	
	@Test
	public void testEnhancerDrop() throws Exception {
		armor.moveTo(backpack);
		backpack.moveTo(hero);
		
		armor.drop();
		
		assertTrue(armor.isTerminated());
		assertFalse(backpack.holdsItem(armor));
	}
	
	@Test
	public void testBackPackDrop() throws Exception {
		armor.moveTo(backpack);
		backpack.moveTo(hero);
		
		backpack.drop();

		assertFalse(hero.holdsItem(backpack));
		assertTrue(backpack.getHolder() == World.world);
	}

	@Test
	public void testMoveToBackPack() throws Exception {
		backpack.moveTo(backpack2);
		assertTrue(backpack2.holdsItem(backpack));
		assertTrue(backpack.getDirectHolder() == backpack2);
	}
	
	@Test
	public void testMoveToBackPackFromHero() throws Exception {
		weapon.moveTo(hero, LocationEquipment.LeftHand);
		weapon.moveTo(backpack);
		
		assertFalse(hero.holdsItem(weapon));
		assertTrue(weapon.getDirectHolder() == backpack);
		assertTrue(hero.getItem(LocationEquipment.LeftHand) == null);
	}
	
	@Test
	public void testMoveToHolderHero()throws Exception {
		armor.moveTo(hero);
		assertTrue(hero.holdsItem(armor));
	}
	
	@Test
	public void testMoveToHolderHeroIllegalArgumentexception()throws Exception {
		purse.moveTo(hero);
		Purse purse2 = new Purse();
		purse2.moveTo(hero);
		assertFalse(hero.holdsItem(armor));
		assertTrue(hero.holdsItem(purse));
		assertTrue(hero.holdsItem(purse2));
		Hero hero2 = new Hero("Hoi", 100, 1000);
		assertFalse(purse2.getHolder() == hero2);
		assertTrue(purse2.getHolder() == hero);
		purse2.moveTo(hero2);
		assertFalse(hero.holdsItem(purse2));
		assertTrue(hero2.holdsItem(purse2));
		assertTrue(purse2.getHolder() == hero2);
		assertFalse(purse2.getHolder() == hero);
	}
	
	
}
