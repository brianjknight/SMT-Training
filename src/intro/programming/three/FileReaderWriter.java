package intro.programming.three;

import java.io.*;

/****************************************************************************
 * <b>Title:</b> FileReaderWriter.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> SMT Intro to Programming Part III. Class provides reader and writer objects.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 31, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class FileReaderWriter {

    private String rootPath;

    /**
     * Constructor that accepts a root folder path.
     * @param rootFolder
     */
    public FileReaderWriter(String rootFolder) {
    	rootPath = rootFolder;
    }
 
    /**
     * Method to returns a FileReader object given a specific file name to read in.
     * @return
     * @throws IOException
     */
    public FileReader readFile(String fileName) throws IOException {
    	String filePath = rootPath + fileName;
    	
    	return new FileReader(filePath);
    }
    
    /**
     * Method returns a FileWriter which writes to the file name provided in the input parameter.
     * @param fileName
     * @return
     * @throws IOException
     */
    public FileWriter writeFile(String fileName) throws IOException {
    	String newPath = rootPath + fileName;
    	
    	return new FileWriter(newPath);
    }
}
