package intro.programming.two;

import java.util.Arrays;

/****************************************************************************
 * <b>Title:</b> ArraysExercise.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
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
	
	public ArrayExercise() {
		nums = new int[10];
		fillArray();
	}
	
	public ArrayExercise(int n) {
		nums = new int[n];
		fillArray();
	}
	
	public ArrayExercise(int n, int multiplier) {
		nums = new int[n];
		fillArray(multiplier);
	}
	
	public void display() {
		System.out.printf("%d random numbers%n", nums.length);
		for (int i=0; i<nums.length; i++) {
			System.out.println(String.valueOf(i+1) + ". " + nums[i]);
		}
	}
	
	private void fillArray() {
		for (int i=0; i<nums.length; i++ ) {
			nums[i] = (int) (Math.random() * 100); 
		}
	}
	
	private void fillArray(int m) {
		for (int i=0; i<nums.length; i++ ) {
			nums[i] = (int) (Math.random() * m); 
		}
	}
		
}
