package myTestPackage;

import java.util.Random;
import java.util.UUID;

/****************************************************************************
 * <b>Title:</b> UUIDtest.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Sep 15, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class UUIDtest {
	public static void main(String[] args) {
		String inputString = "ca98ce805f3c48069e3ca425568b9aaf";
		String uuidFormat = inputString.replaceAll(
		    "(.{8})(.{4})(.{4})(.{4})(.{12})", 
		    "$1-$2-$3-$4-$5"
		);
		System.out.println(uuidFormat);
		
		System.out.println(String.format("UUID format in a string: %s", UUID.randomUUID()));
		
		UUID anotherID = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
		System.out.println(anotherID);
		System.out.println("concat string with a uuid... " + anotherID);
		
		System.out.println("replace hypens in uuid... " + anotherID.toString().replaceAll("-", ""));
	}
}
