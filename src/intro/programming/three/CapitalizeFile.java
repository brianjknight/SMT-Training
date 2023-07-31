package intro.programming.three;

import java.io.FileReader;
import java.io.FileWriter;

/****************************************************************************
 * <b>Title:</b> CapitalizeFile.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> SMT Intro to Programming Part III. This class takes in a FileReaderWriter object to read, capitalize characters, then write to file.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class CapitalizeFile {
	
	private FileReaderWriter readerWriter;
	
	/**
	 * Constructor to instantiate a CapitalizeFile object taking in a FileReaderWriter.
	 * @param readerWriter
	 */
	public CapitalizeFile(FileReaderWriter readerWriter) {
		this.readerWriter = readerWriter;
	}
	
	/**
	 * Method to read each character in a file, capitalize the character, then write to a file.
	 * @param readFile - file to be read from.
	 * @param writeFile - file to output to.
	 */
	public void capitalizeText(String readFile, String writeFile) {
		
		try {
			FileReader reader = readerWriter.readFile(readFile);
			FileWriter writer = readerWriter.writeFile(writeFile);
			
			int c;
			
			while ((c=reader.read()) != -1) {
				writer.write(Character.toUpperCase((char) c));
			}
			
			reader.close();
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
}
