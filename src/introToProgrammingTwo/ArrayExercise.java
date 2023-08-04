package introToProgrammingTwo;

/****************************************************************************
 * <b>Title:</b> ArraysExercise.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Exercise for Intro to Programming Part II
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 12, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class ArrayExercise {
	private int[] nums;
	
	/**
	 * No args constructor.
	 */
	public ArrayExercise() {
		nums = new int[10];
		fillArray();
	}
	
	/**
	 * Constructor takes an integer value. 
	 * @param n - length of the array to initialize.
	 */
	public ArrayExercise(int n) {
		nums = new int[n];
		fillArray();
	}
	
	/**
	 * Constructor taking 2 integers values
	 * @param n - length of the array to initialize.
	 * @param multiplier - multiplies the random number if fillArray();
	 */
	public ArrayExercise(int n, int multiplier) {
		nums = new int[n];
		fillArray(multiplier);
	}
	
	/**
	 * Method to print the random numbers in the array.
	 */
	public void display() {
		System.out.printf("%d random numbers%n", nums.length);
		for (int i=0; i<nums.length; i++) {
			System.out.println(String.valueOf(i+1) + ". " + nums[i]);
		}
	}
	
	/**
	 * Helper method to fill the array with integer values between 1-100;
	 */
	private void fillArray() {
		for (int i=0; i<nums.length; i++ ) {
			nums[i] = (int) (Math.random() * 100); 
		}
	}
	
	/**
	 * Helper method to fill the array with integer values between between 0-1 multiplied by the input parameter.
	 * @param multiplier - multiplies each random 0-1.
	 */
	private void fillArray(int multiplier) {
		for (int i=0; i<nums.length; i++ ) {
			nums[i] = (int) (Math.random() * multiplier); 
		}
	}
		
}
