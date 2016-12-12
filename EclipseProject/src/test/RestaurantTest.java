package test;

import java.util.List;

import edu.upenn.cit594.miniYelp.Restaurant;
import edu.upenn.cit594.miniYelp.RestaurantCollection;
import edu.upenn.cit594.miniYelp.RestaurantFileParser;
import edu.upenn.cit594.miniYelp.SearchRestaurant;

public class RestaurantTest {

	public static void main(String[] args) {
		List<Restaurant> test;
		RestaurantFileParser testParser = new RestaurantFileParser();
		testParser.loadData("restaurant.csv");
		SearchRestaurant sr = new SearchRestaurant();
		
		String[] filter = {"","","","300"};
		test = sr.searchRestaurant(filter);
//		for(int i = 0; i<test.size(); i++){
//			System.out.println(test.get(i).toString(i));
//		}
		
		RestaurantCollection rc = RestaurantCollection.getInstance();
		System.out.println(rc.getCategoryilst());
	}
		

}
