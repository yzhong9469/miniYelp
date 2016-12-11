package edu.upenn.cit594.miniYelp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * UserProfileParser
 * read in file which contains the information of registered users
 * store each user's email, password and rated restaurants as an 
 * User object
 * 
 * @author MHu
 *
 */
public class UserProfileParser extends DataParser {
	private static final int EMAIL_INDEX = 0; //index of email in user_profile.txt
	private static final int PASSWORD_INDEX = 1; //index of password in user_profile.txt
	private static final int RATING_INDEX = 2; //index of rated restaurants in user_profile.txt
	private static final int FIELDS = 3; //total number of fields
	
	/**
	 * read in user_profile.txt and store all registered users' info
	 */
	public void loadData(String inputfile) {
		if (!isValidPath(inputfile)) {
			throw new IllegalArgumentException("Invalid input file");
		}
		
		String line;
		UserCollection users = UserCollection.getInstance();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputfile));

			while ((line = reader.readLine()) != null) {
				String[] user = line.split("\t");
				User u = new User(user[EMAIL_INDEX], user[PASSWORD_INDEX]);
				
				if (user.length < FIELDS) {
					users.addUser(u);
					continue;
				}
				
				//get all restaurants the user had rated before
				String[] ratedRestaurants = user[RATING_INDEX].split(";");
				for (String restaurantRating : ratedRestaurants) {
					String[] record = restaurantRating.split(":");
					if (record.length != 2) continue;					
					u.updateRatings(record[0], Double.parseDouble(record[1]));
				}
				
				users.addUser(u);
			}
			
			reader.close();
		} catch (IOException e) {
			System.out.println("Open " + inputfile + " failed: " + e.getMessage());
		} 
	}
	
	
	/**
	 * record registered users' information to local file
	 */
	public void writeData() {
		String outputfile = "user_profile.txt";
		UserCollection users = UserCollection.getInstance();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile));
			Iterator<Entry<String, User>> it = users.iterator();
			while (it.hasNext()) {
				Map.Entry<String, User> pair = it.next();				
				writer.write(pair.getValue().toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Write user_profile.txt failed");
		} 	
	}
	
}
