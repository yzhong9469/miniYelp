package edu.upenn.cit594.miniYelp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantFileParser extends DataParser {
	private static final int NAME_INDEX = 0;
	private static final int ID_INDEX = 1;
	private static final int PRICE_INDEX = 2;
	private static final int CATEGORIES_INDEX = 3;
	private static final int TITLES = 3;
	private static final int LONGITUDE_INDEX = 6;
	private static final int LATITUDE_INDEX = 7;
	private static final int ZIPCODE_INDEX = 8;
	private static final int ADDRESS_INDEX = 9;
	private static final int RATING_INDEX = 10;
	private static final int REVIEWCOUNT_INDEX = 11;
	private static final int PHONE_INDEX = 12;
	private static final int FIELDS = 13;
	
	private List<Restaurant> restaurants;
	
	public RestaurantFileParser () {
		restaurants = new ArrayList<>();
	}
	
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
				String[] restaurant = line.split(",");
				if (restaurant == null || restaurant.length != FIELDS) continue;
				
				name = restaurant[NAME_INDEX];
				id = restaurant[ID_INDEX];
				price = restaurant[PRICE_INDEX];
				categories = new ArrayList<>();
				
				for (int i = 0; i < TITLES; i++) {
					if (restaurant[CATEGORIES_INDEX + i].length() == 0) continue;
					categories.add(restaurant[CATEGORIES_INDEX + i]);
				}
				
				longitude = Double.parseDouble(restaurant[LONGITUDE_INDEX]);
				latitude = Double.parseDouble(restaurant[LATITUDE_INDEX]);
				zipcode = restaurant[ZIPCODE_INDEX];
				address = restaurant[ADDRESS_INDEX];
				rating = Float.parseFloat(restaurant[RATING_INDEX]);
				reviewCount = Integer.parseInt(restaurant[REVIEWCOUNT_INDEX]);
				phone = restaurant[PHONE_INDEX];
				
				Restaurant r = new Restaurant(id, name, price, categories, longitude, latitude, zipcode, address, rating, reviewCount, phone);
				restaurants.add(r);
			}
			
			reader.close();
		} catch (IOException e) {
			//TO DO: add exception
		} 

	}
	
	public List<Restaurant> getRestaurantList(){
		return this.restaurants;
	}

}
