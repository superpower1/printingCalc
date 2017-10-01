package mypackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrintingCalcTest {
	
	private static PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);

	@Before
	public void setUp() throws Exception {		
		A4.readFile("src\\testdata\\printJobs.csv");
	}

	@Test
	public void testPrintingCalc() {
		
		assertEquals(6410, A4.getTotalCost());
	}

}
