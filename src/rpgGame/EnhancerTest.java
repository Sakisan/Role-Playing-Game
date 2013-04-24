/**
 * 
 */
package rpgGame;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class EnhancerTest {

	private Weapon weapon;
	private Armor armor;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		weapon = new Weapon(new Weight(3));
		armor = new Armor(600, new Weight(40), 700);
	}
	
	@Test
	public void testDamage() throws Exception {
		int value = weapon.getValue();
		assertTrue(value >= 1);
		assertTrue(value <= 100);
		assertTrue(value % 7 == 0);
	}
	
	@Test
	public void testProtection() throws Exception {
		int max = armor.getMaximumValue();
		assertTrue(max >= 1);
		assertTrue(max <= 100);
		int value = armor.getValue();
		assertTrue(value >= 0);
		assertTrue(value <= max);
		
	}

}
