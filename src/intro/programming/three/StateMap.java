package intro.programming.three;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/****************************************************************************
 * <b>Title:</b> State.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Intro to Programming: Collections exercise
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class StateMap {
	
	private final String[] ABBREVIATIONS = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
		    "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
		    "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
	
	private final String[] NAMES = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
		    "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
		    "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
		    "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
		    "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
		    "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
		    "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
	
	Map<String, String> mapOfStates = new HashMap<>();
	
	/**
	 * Constructor instantiates a new StateMap object. 
	 * The hash map is populated with state abbreviations as the key and state name as the value.
	 */
	public StateMap() {
		for (int i=0; i<ABBREVIATIONS.length; i++) {
			mapOfStates.put(ABBREVIATIONS[i], NAMES[i]);
		}
	}

	/**
	 * Getter method for map of states.
	 * @param stateMap
	 */
	public void setStateMap(Map<String, String> stateMap) {
		this.mapOfStates = stateMap;
	}
	
	/**
	 * Void method to print stateMap entry sets.
	 */
	public void displayStates() {
		this.display(mapOfStates);
	}
	
	/**
	 * Converts the stateMap HashMap to a TreeMap and displays the results.
	 */
	public void displayStatesOrdered() {
		Map<String, String> statesOrdered = new TreeMap<>(mapOfStates);
		this.display(statesOrdered);
	}
	
	/**
	 * Converts the stateMap HashMap to a reverse ordered TreeMap.
	 */
	public void displayStatesReverseOrdered() {
		Map<String, String> statesReversed = new TreeMap<>(Collections.reverseOrder());
		statesReversed.putAll(mapOfStates);
		this.display(statesReversed);
	}
	
	/**
	 * Helper method to print the contents of a Map in new lines. 
	 * @param map
	 */
	private void display(Map<String, String> map) {	
		for (Map.Entry<String, String> e : map.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}
}
