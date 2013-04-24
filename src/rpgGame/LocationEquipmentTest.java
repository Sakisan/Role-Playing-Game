/**
 * 
 */
package rpgGame;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Pieter
 *
 */
public class LocationEquipmentTest {

	/**
	 * Test method for {@link rpgGame.LocationEquipment#canLinkItem(rpgGame.Item)}.
	 */
	@Test
	public void testCanLinkItem() {
		assertFalse(LocationEquipment.Belt.canLinkItem(new Weapon()));
		// assertTrue(LocationEquipment.Belt.canLinkItem(((Purse)new Object())));
	}

}
