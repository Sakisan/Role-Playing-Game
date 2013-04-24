package rpgGame;


import org.junit.*;
import static org.junit.Assert.*;

public class DukatAmountTest {

	private DukatAmount dukats;
	
	@Before
	public void setUp() throws Exception {
		dukats = new DukatAmount();
	}
	
	@Test
	public void testGetMaximum() throws Exception {
		assertTrue(dukats.getMaximum() == Integer.MAX_VALUE);
	}
	
	@Test
	public void testAddAndGetAmount() throws Exception {
		assertTrue(dukats.add(50) == 0);
		assertTrue(dukats.getAmount() == 50);
	}
	
	@Test
	public void testAddTooMuch() throws Exception {
		dukats = new DukatAmount(Integer.MAX_VALUE - 20);
		assertTrue(dukats.add(50) == 30);
		assertTrue(dukats.getAmount() == Integer.MAX_VALUE);
	}
	
	@Test
	public void testRetract() throws Exception {
		dukats.add(50);
		dukats.retract(20);
		assertTrue(dukats.getAmount() == 30);
	}
	
	@Test
	public void testRetractTooMuch() throws Exception {
		dukats.add(50);
		dukats.retract(200);
		assertTrue(dukats.getAmount() == 0);
	}
	
	@Test
	public void testGetEquivalentAmount() throws Exception {
		Weight weight = new Weight(51,UnitOfWeight.GRAM);
		assertTrue(DukatAmount.getEquivalentAmount(weight) == 1);
		
		weight = new Weight(50,UnitOfWeight.GRAM);
		assertTrue(DukatAmount.getEquivalentAmount(weight) == 1);
		
		weight = new Weight(49,UnitOfWeight.GRAM);
		assertTrue(DukatAmount.getEquivalentAmount(weight) == 0);
	}
	
	@Test
	public void testCompareTo() throws Exception {
		dukats.add(20);
		
		DukatAmount dukats2 = new DukatAmount(30);
		assertTrue(dukats2.compareTo(dukats) > 0);
		assertTrue(dukats.compareTo(dukats2) < 0);
		assertTrue(dukats.compareTo(dukats) == 0);
	}
	
	@Test
	public void testGetWeight() throws Exception {
		Weight weight = new Weight(50, UnitOfWeight.GRAM);
		dukats.add(1);
		assertTrue(dukats.getWeight().compareTo(weight) == 0);
	}

}
