package intro.programming.three;

import java.io.*;

/****************************************************************************
 * <b>Title:</b> FileReaderWriter.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> SMT Intro to Programming Part III. Class provides file reader and writer objects.
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

    private String rootFolder;

    /**
     * Constructor that accepts a root folder path.
     * @param rootFolder - input parameter requires fully qualified path name ending with "/"
     */
    public FileReaderWriter(String rootFolder) {
    	this.rootFolder = rootFolder;
    }

    /**
     * Getter method to show current folder for reading/writing files.
     * @return
     */
    public String getRootFolder() {
		return rootFolder;
	}

    /**
     * Setter method to change root folder for reading/writing files.
     * @param rootFolder
     */
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

	/**
     * Method to returns a FileReader object given a specific file name to read in.
     * @param fileName - name of file to read provided in format file_name.file_type
     * @return new FileReader object.
     * @throws IOException
     */
    public FileReader readFile(String fileName) throws IOException {
    	String filePath = rootFolder + fileName;
    	
    	return new FileReader(filePath);
    }

    /**
     * Method returns a FileWriter which writes to the file name provided in the input parameter.
     * @param fileName - name of file to write to formated file_name.file_type
     * @return new FileWriter object.
     * @throws IOException
     */
    public FileWriter writeFile(String fileName) throws IOException {
    	String newPath = rootFolder + fileName;
    	
    	return new FileWriter(newPath);
    }
}
