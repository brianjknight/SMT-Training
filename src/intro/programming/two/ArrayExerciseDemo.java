package intro.programming.two;

/****************************************************************************
 * <b>Title:</b> ArrayExerciseDemo.java
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

public class ArrayExerciseDemo {
	public static void main(String[] args) {
		ArrayExercise e1 = new ArrayExercise();
		e1.display();
		
		System.out.println("*".repeat(50));
		
		ArrayExercise e2 = new ArrayExercise(20);
		e2.display();
		
		System.out.println("*".repeat(50));
		
		ArrayExercise e3 = new ArrayExercise(15, 100_000);
		e3.display();
	}
}
