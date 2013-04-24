/**
 * 
 */
package rpgGame;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Antoine
 *
 */
public class BagTest {

	private Backpack backpack;
	private Purse purse;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		backpack = new Backpack();
		purse = new Purse(0,new DukatAmount(200));
	}
	
	@Test
	public void testCapacityBackPack() throws Exception {
		Weight weight = new Weight(100);
		assertTrue(backpack.getCapacity().compareTo(weight) == 0);
	}
	
	@Test
	public void testGetCapacityPurse() throws Exception {
		DukatAmount dukats = new DukatAmount(100);
		purse.setCapacity(dukats);
		assertTrue(purse.getCapacity().getAmount() == 100);
	}
	
	@Test
	public void testCanAddDukatsBackpack() throws Exception {
		int amount;
		amount = DukatAmount.getEquivalentAmount(new Weight(99,UnitOfWeight.KILOGRAM));
		assertTrue(backpack.canAddDukats(amount));
		amount = DukatAmount.getEquivalentAmount(new Weight(100,UnitOfWeight.KILOGRAM));
		assertTrue(backpack.canAddDukats(amount));
		amount = DukatAmount.getEquivalentAmount(new Weight(101,UnitOfWeight.KILOGRAM));
		assertFalse(backpack.canAddDukats(amount));
	}
	
	@Test
	public void testCanAddDukatsPurse() throws Exception {
		assertTrue(purse.canAddDukats(50));
		purse.addDukats(190);
		assertFalse(purse.canAddDukats(50));
		assertTrue(purse.canAddDukats(5));
		purse.addDukats(50); //tears the purse
		assertFalse(purse.canAddDukats(5));
	}
	
	@Test
	public void testAddDukatsBackpack() throws Exception {
		backpack.addDukats(50);
		assertTrue(backpack.getDukats().getAmount() == 50);
	}
	
	@Test
	public void testAddDukatsPurse() throws Exception {
		purse.addDukats(50);
		assertTrue(purse.getDukats().getAmount() == 50);
	}
	
	@Test
	public void testAddTooManyDukatsBackpack() throws Exception {
		Weight capacity = new Weight(50);
		backpack = new Backpack(capacity);
		
		assertTrue(backpack.getDukats().getAmount() == 0);
		
		boolean caught = false;
		try {
			backpack.addDukats(1001);
		} catch (IllegaleToestandsUitzondering e) {
			caught = true;
		}
		assertTrue(caught);
		assertTrue(backpack.getDukats().getAmount() == 1000);
	}
	
	@Test
	public void testAddTooManyDukatsPurse() throws Exception {
		purse.setCapacity(new DukatAmount(100));
		purse.addDukats(500);
		assertTrue(purse.isTorned());
		assertTrue(purse.getDukats().getAmount() == 0);
	}
	
	@Test
	public void testRetract() throws Exception {
		purse.addDukats(15);
		purse.retractDukats(5);
		assertTrue(purse.getDukats().getAmount() == 10);
		
	}
}
