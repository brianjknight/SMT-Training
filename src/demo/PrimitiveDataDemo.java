package demo;

import exercises.PrimitiveData;

/****************************************************************************
 * <b>Title:</b> PrimitiveDataDemo.java
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

public class PrimitiveDataDemo {
	public static void main(String[] args) {
		// Testing PrimitiveData class:
		PrimitiveData pd1 = new PrimitiveData(1, 2.123456789D, 3.123456789F, 'a', false);
		System.out.println("Calling pd1.printValues():");
		pd1.printValues();

		System.out.println("-".repeat(20));
		
		System.out.println("Calling pd1.toString()");
		System.out.println(pd1.toString());

		System.out.println("-".repeat(20));
		
		PrimitiveData pd2 = new PrimitiveData();
		pd2.setI(1);
		pd2.setD(2.987654321);
		pd2.setF(3.987654321F);
		pd2.setC('A');
		pd2.setB(true);
		System.out.println("Calling pd2.printValues():");
		pd2.printValues();
		
		System.out.println("-".repeat(20));
		
		System.out.println("Calling pd2.toString()");
		System.out.println(pd2.toString());

	}
}
