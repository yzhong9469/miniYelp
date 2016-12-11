package edu.upenn.cit594.miniYelp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantCollection {
	private static RestaurantCollection restCollection;
	
	private HashMap<String, Restaurant> restaurants;
	private HashSet<String> categorylist;
	
	private RestaurantCollection() {
		this.restaurants = new HashMap<String, Restaurant>();
	}
	
	public static RestaurantCollection getInstance() {
		if (restCollection == null) {
			restCollection = new RestaurantCollection();
		}
		return restCollection;
	}
	
	public HashMap<String,Restaurant> getCollection(){
		return this.restaurants;
	}
	/**
	 * 
	 * @param restaurantId
	 * @return
	 */
	public Restaurant getRestaurant(String restaurantId) {
		if (restaurantId == null) return null;
		
		return restaurants.get(restaurantId);
	}
	
	/**
	 * 
	 * @param restaurant
	 */
	public void addRestaurant(Restaurant restaurant) {
		if (restaurant == null) return;

		restaurants.put(restaurant.getId(), restaurant);
	}
	
	public Set<String> getAllRestaurants() {
		return restaurants.keySet();
	}
	
	/**
	 * 
	 */
	public HashSet<String> getCategortList(){
		categorylist = new HashSet<String>();
		for (String id:this.restaurants.keySet()){
			List<String> categories = this.restaurants.get(id).getCategories();
			for (int i = 0; i < categories.size(); i++){
				categorylist.add(categories.get(i));
			}			
		}
		return categorylist;
	}
	
	
}
