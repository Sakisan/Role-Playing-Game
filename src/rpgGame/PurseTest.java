package rpgGame;


import static org.junit.Assert.assertTrue;

import org.junit.*;

public class PurseTest {
	
	Purse purse;
	
	@Before
	public void setUp() throws Exception {
		purse = new Purse();
	}

	@Test
	public void testSetCapacity() throws Exception {
		DukatAmount dukats = new DukatAmount(100);
		purse.setCapacity(dukats);
		assertTrue(purse.getCapacity().getAmount() == 100);
	}
}
