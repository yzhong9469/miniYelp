package edu.upenn.cit594.miniYelp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantFileParser
 * read in file which contains the information of restaurants
 * store as a Restaurant object
 * 
 * @author MHu
 *
 */
public class RestaurantFileParser extends DataParser {
	private static final int NAME_INDEX = 0; //index of name in restaurant.csv
	private static final int ID_INDEX = 1; //index of id in restaurant.csv
	private static final int PRICE_INDEX = 2; //index of name in restaurant.csv
	private static final int CATEGORIES_INDEX = 3; //index of categories in restaurant.csv
	private static final int TITLES = 3; //number of categories
	private static final int LONGITUDE_INDEX = 6; //index of longitude in restaurant.csv
	private static final int LATITUDE_INDEX = 7; //index of latitude in restaurant.csv
	private static final int ZIPCODE_INDEX = 8; //index of zipcode in restaurant.csv
	private static final int ADDRESS_INDEX = 9; //index of address in restaurant.csv
	private static final int RATING_INDEX = 10; //index of rating in restaurant.csv
	private static final int REVIEWCOUNT_INDEX = 11; //index of review count in restaurant.csv
	private static final int PHONE_INDEX = 12; //index of phone in restaurant.csv
	private static final int FIELDS = 13; //number of fields
	
	private RestaurantCollection restaurants;
	
	//constructor
	public RestaurantFileParser () {
		restaurants = RestaurantCollection.getInstance();
	}
	
	/**
	 * read in restaurant.csv and store all restaurants' info
	 */
	public void loadData(String inputfile) {
		if (!isValidPath(inputfile)) {
			throw new IllegalArgumentException("Invalid input file");
		}
		
		String line, name, id, price, phone;
		List<String> categories;
		double longitude, latitude;
		String zipcode, address;
		float rating;
		int reviewCount;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputfile));
			reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				String[] restaurant = line.split("\t");
				if (restaurant == null || restaurant.length > FIELDS) continue;
				
				name = restaurant[NAME_INDEX];
				id = restaurant[ID_INDEX];
				price = restaurant[PRICE_INDEX];
				categories = new ArrayList<>();
				
				for (int i = 0; i < TITLES; i++) {
					if (restaurant[CATEGORIES_INDEX + i].length() == 0) continue;
					categories.add(restaurant[CATEGORIES_INDEX + i]);
				}
				
				try {
					longitude = Double.parseDouble(restaurant[LONGITUDE_INDEX]);
				} catch(NumberFormatException e) {
					longitude = 0;
				}
				
				try {
					latitude = Double.parseDouble(restaurant[LATITUDE_INDEX]);
				} catch(NumberFormatException e) {
					latitude = 0;
				}
				
				zipcode = restaurant[ZIPCODE_INDEX];
				address = restaurant[ADDRESS_INDEX];
				rating = Float.parseFloat(restaurant[RATING_INDEX]);
				reviewCount = Integer.parseInt(restaurant[REVIEWCOUNT_INDEX]);
				
				if (restaurant.length == FIELDS) phone = restaurant[PHONE_INDEX];
				else phone = "";
				
				Restaurant r = new Restaurant(id, name, price, categories, longitude, latitude, zipcode, address, rating, reviewCount, phone);
				restaurants.addRestaurant(r);;
			}
			
			reader.close();
		} catch (IOException e) {
			System.out.println("Open " + inputfile + " failed: " + e.getMessage());
		} 
	}
}
