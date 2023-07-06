package demo;

import exercises.NumberDisplay;

/****************************************************************************
 * <b>Title:</b> NumberDisplayDemo.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 6, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class NumberDisplayDemo {
	public static void main(String[] args) {
		// Testing NumberDisplay class:
		NumberDisplay.whileLoop();
		
		System.out.println("");
		
		System.out.println("-".repeat(20));
		
		NumberDisplay.forLoop();
		
		System.out.println("");
		
		System.out.println("-".repeat(20));
		
		NumberDisplay.evenNumbers();
	}
}
