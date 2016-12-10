package edu.upenn.cit594.miniYelp;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public abstract class DataParser {
	protected final Logger Log = Logger.getLogger(DataParser.class.getName());
	
	public boolean isValidPath(String filename) {
		Path path;
		
		try {
			path = Paths.get(filename);
		} catch (InvalidPathException ex) {
			Log.severe("Invalid Path: input path to " + filename + " is invalid.");
			return false;
		}
		
		if (Files.notExists(path)) {
			Log.severe("Invalid File: input file " + filename + " does not exist.");
			return false;
		}
		
		if (!Files.isReadable(path)) {
			Log.severe("Invalid File: no read accessbility to input file " + filename + ".");
			return false;
		}
		
		return true;
	}
	
	
	public void loadData(String inputfile) {}
	
	public void writeData() {}
	

}
