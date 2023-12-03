package myTestPackage;

import java.util.Arrays;

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
		System.out.println("hello world");
		
		String color = "2 red";
		
		String[] colorsStrings = color.split(",");
		
		System.out.println(Arrays.toString(colorsStrings));
		
	}
}
