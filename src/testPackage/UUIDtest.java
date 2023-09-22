package testPackage;

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
	}
}
