package myTestPackage;

import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * <b>Title:</b> MyTestClass.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Oct 24, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class MyTestClass {

	public static void main(String[] args) {
		
		Map<String, Double> one = new HashMap<>();
		one.put("A", 1.0);
		one.put("B", 1.0);
		one.put("C", 1.0);
		
		Map<String, Double> two = new HashMap<>();
		two.put("A", 1.0);
		two.put("B", 1.0);
		two.put("C", 1.0);
		two.put("D", 1.0);
		two.put("E", 1.0);
		
		Map<String, Double> combined = new HashMap<>(one);
		two.forEach((key, value) -> combined.merge(key, value, Double::sum));
		
		System.out.println(combined);
		
	}
}
