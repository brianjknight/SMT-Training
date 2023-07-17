package intro.programming.three;

/****************************************************************************
 * <b>Title:</b> StateDemo.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class StateMapDemo {
	public static void main(String[] args) {
		StateMap mapOne = new StateMap();
		mapOne.displayStates();
		
		System.out.println("#".repeat(50));
		
		mapOne.displayStatesOrdered();
		
		System.out.println("#".repeat(50));
		
		mapOne.displayStatesReverseOrdered();
	}
}
