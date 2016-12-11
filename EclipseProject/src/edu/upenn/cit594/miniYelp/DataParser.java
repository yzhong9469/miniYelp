package edu.upenn.cit594.miniYelp;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * DataParser
 * isValidPath(): determine if a filename is valid 
 * loadData(): abstract method
 * writeData(): abstract method
 * 
 * @author MHu
 *
 */
public abstract class DataParser {
	
	/**
	 * determine if a filename is valid
	 * @param filename - name of input file
	 * @return true if the filename is valid; otherwise false
	 */
	public boolean isValidPath(String filename) {
		Path path;
		
		try {
			path = Paths.get(filename);
		} catch (InvalidPathException ex) {
			System.out.println(filename + " is invalide: " + ex.getMessage());
			return false;
		}
		
		if (Files.notExists(path)) {
			System.out.println(filename + " does not exist.");
			return false;
		}
		
		if (!Files.isReadable(path)) {
			System.out.println(filename + " is not accessable.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * parse and store data from input file
	 * @param inputfile
	 */
	public void loadData(String inputfile) {}
	
	/**
	 * write data put to local file
	 */
	public void writeData() {}

}
