/**
 * 
 */
package rpgGame;


import java.util.Iterator;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Antoine
 *
 */
public class BackpackTest {

	private Backpack backpack;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		backpack = new Backpack();
	}
	
	@Test
	public void testIteratorHasNext() throws Exception {
		Iterator<Item> it = backpack.iterator();
		assertFalse(it.hasNext());		
		
		Weapon weapon = new Weapon();
		weapon.moveTo(backpack);
		it = backpack.iterator();
		assertTrue(it.hasNext());
	}
	
	@Test
	public void testIteratorNext() throws Exception {
		Weapon weapon = new Weapon();
		weapon.moveTo(backpack);
		Armor armor = new Armor(55);
		armor.moveTo(backpack);
		
		Iterator<Item> it = backpack.iterator();
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testIteratorRemove() throws Exception {
		Weapon weapon = new Weapon();
		weapon.moveTo(backpack);
		assertTrue(backpack.holdsItem(weapon));
		
		Iterator<Item> it = backpack.iterator();
		assertTrue(it.hasNext());
		it.next();
		it.remove();
		assertFalse(backpack.holdsItem(weapon));
	}

}
