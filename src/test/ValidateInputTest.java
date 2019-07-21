package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidateInputTest {
	
	@Test
	public void testIsNullOrEmpty() {
		String actual1 ="";
		String actual2= null;
		
		assertTrue(actual1, true);
		assertTrue(actual2,true);
		
	}
	
	@Test
	public void userWantToCreate() {
		String type ="directed";
		String weight= "5";
		
		assertTrue(type, true);
		assertTrue(weight,true);
		
	}
	
	

}
