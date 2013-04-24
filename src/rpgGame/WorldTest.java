package rpgGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	private World world; 
	
	@Before
	public void setUp() throws Exception {
		world = World.world;
	}

	@Test
	public void testIsSupremeHolder() {
		assertTrue(world.isSupremeHolder());
	}

	@Test
	public void testCanHoldItem() {
		assertTrue(world.canHoldItem(new Weapon()));
		assertTrue(world.canHoldItem(new Armor(10)));
		assertTrue(world.canHoldItem(new Purse()));
		assertTrue(world.canHoldItem(new Backpack()));
		assertFalse(world.canHoldItem(null));
		
	}

	@Test
	public void testHoldsItem() {
		Weapon weapon = new Weapon();
		Armor armor = new Armor(15);
		Purse purse = new Purse(100);
		Backpack bag = new Backpack();
		assertTrue(world.holdsItem(weapon));
		assertTrue(world.holdsItem(armor));
		assertTrue(world.holdsItem(purse));
		assertTrue(world.holdsItem(bag));
		Hero hero = new Hero("Hoi", 100, 10);
		weapon.moveTo(hero);
		assertTrue(hero.holdsItem(weapon));
		assertFalse(world.holdsItem(weapon));
	}

	@Test(expected=IllegaleToestandsUitzondering.class)
	public void testAddItem() {
		world.addItem(new Weapon());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveItem() {
		world.removeItem(new Weapon());
	}

	@Test
	public void testGetItems() {
		Armor armor = new Armor(190);
		assertTrue(world.getItems().contains(armor));
	}

	@Test
	public void testAddDukats() {
		DukatAmount dukat = new DukatAmount(11);
		DukatAmount dukat2= new DukatAmount(11);
		
		world.addDukats(dukat);
		world.addDukats(dukat2);
		assertTrue(world.holdsDukats(dukat));
		assertTrue(world.holdsDukats(dukat2));
	}
	
	@Test
	public void testGetAllDukats() {
		DukatAmount dukat = new DukatAmount(11);
		DukatAmount dukat2= new DukatAmount(11);
		
		world.addDukats(dukat);
		world.addDukats(dukat2);
		assertTrue(world.getAllDukats().contains(dukat));
		assertTrue(world.getAllDukats().contains(dukat2));
	}

	@Test
	public void testHasValidItems() {
		assertTrue(world.hasValidItems());
	}

	@Test
	public void testToString() {
		new Weapon();
		new Armor(16);
		// System.out.println(world.toString());
	}
}
