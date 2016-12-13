package test;


import java.util.List;

import edu.upenn.cit594.miniYelp.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestaurantFileParser fp = new RestaurantFileParser();
		fp.loadData("restaurant.csv");
		
		UserProfileParser up = new UserProfileParser();
		up.loadData("user_profile.txt");
		System.out.println("load file complete");
		
		//System.out.println(RestaurantCollection.getInstance().getAllRestaurants().toString());
		
		
		User u = UserCollection.getInstance().getUser("manh@seas.upenn.edu");
		if (u == null) System.out.println("no user exist");
		RecommendRestaurant r = new RecommendRestaurant();
		List<Restaurant> result = r.recommRestaurant(u);
		
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getName() + " - " + result.get(i).getRating());
		}
		
		
	}

}
