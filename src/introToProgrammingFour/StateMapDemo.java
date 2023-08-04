package introToProgrammingFour;

/****************************************************************************
 * <b>Title:</b> StateDemo.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Demo class with main method to call StateMap methods.
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
		
	public void run() {
		StateMap mapOne = new StateMap();
		mapOne.displayStates();
		
		System.out.println("#".repeat(50));
		
		mapOne.displayStatesOrdered();
		
		System.out.println("#".repeat(50));
		
		mapOne.displayStatesReverseOrdered();		
	}
	
	public static void main(String[] args) {
		StateMapDemo demo = new StateMapDemo();
		demo.run();
	}
}
