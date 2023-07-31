package intro.programming.three;

import java.io.FileReader;

/****************************************************************************
 * <b>Title:</b> FileDemo.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> SMT Intro to Programming Part III. 
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class FileDemo {
	
	public static void main(String[] args) {
		FileDemo demo = new FileDemo();
		demo.run();
	}
	
	/**
	 * Demo method to test FileReaderWriter and CapitalizeFile classes.
	 */
	public void run() {
		String rootPath = "/home/brian/eclipse-workspace/Training/brian.training/resources/";
		
		FileReaderWriter readerWriter = new FileReaderWriter(rootPath);
		
		/**
		 * Read output.txt and print to terminal prior to writing to it.
		 */
		try {
			FileReader reader = readerWriter.readFile("output.txt");	
			int c;
			while ((c=reader.read()) != -1) {
				System.out.print((char) c);
			}
			System.out.println("\n");
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		/**
		 * Instantiate object to capitalize the file.
		 */
		CapitalizeFile capFile = new CapitalizeFile(readerWriter);
		capFile.capitalizeText("quotes.txt", "output.txt");
		
		/**
		 * Print resulting contents of output.txt.
		 */
		try {
			FileReader reader = readerWriter.readFile("output.txt");	
			int c;
			while ((c=reader.read()) != -1) {
				System.out.print((char) c);
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
