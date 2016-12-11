package edu.upenn.cit594.miniYelp;

import java.util.List;

public class RestaurantTest {

	public static void main(String[] args) {
		List<Restaurant> test;
		RestaurantFileParser testParser = new RestaurantFileParser();
		testParser.loadData("restaurant.csv");
		SearchRestaurant sr = new SearchRestaurant();
		
		String[] filter = {"","","","300"};
		test = sr.searchRestaurant(filter);
		for(int i = 0; i<test.size(); i++){
			System.out.println(test.get(i).toString(i));
		}
	}

}
