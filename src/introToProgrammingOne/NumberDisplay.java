package introToProgrammingOne;

/****************************************************************************
 * <b>Title:</b> NumberDisplay.java
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

public class NumberDisplay {
	
	// Private constructor to prevent instantiating new objects. Static methods must be used.
	private NumberDisplay() {}
		
	/**
	 * Method using a while loop to print integers 1 to 10.
	 */
	 public static void whileLoop() {
		 int n = 1; 
		 
		 while (n <= 10) {
			 System.out.print(n + " ");
			 n++;
		 }
	 }
	 
	 /**
	  * Method using a for loop to count down from 10 to 1.
	  */
	 public static void forLoop() {
		 for (int i = 10; i > 0; i--) {
			 System.out.print(i + " ");
		 }
	 }
	
	 /**
	  * Method to print even numbers 1 to 20 ascending. 
	  */
	 public static void evenNumbers() {
		 for (int i = 1; i <= 20; i++) {
			 if (i % 2 == 0) {
				 System.out.println(i);
			 }
		 }
	 }
}