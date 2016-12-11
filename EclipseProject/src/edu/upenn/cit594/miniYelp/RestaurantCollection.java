package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author MHu
 *
 */
public class RestaurantCollection {
	private static RestaurantCollection restCollection;

	private HashMap<String, Restaurant> restaurants;
	private HashMap<String,Integer> categorycount;
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
	
	
	public List<Restaurant> getSimilarRestaurants(String keyword) {
		keyword = keyword.toLowerCase();
		ArrayList<Restaurant> result = new ArrayList<>();
		
		for (String key : restaurants.keySet()) {
			String title = restaurants.get(key).getName().toLowerCase();
			String pattern = "(.*)" + keyword + "(.*)";

			if (title.matches(pattern)) {
				result.add(restaurants.get(key));
			}	
		}

		return result;
	}
	

	/**
	 * 
	 */
	public HashMap<String,Integer> getCategorycount(){
		categorycount = new HashMap<String,Integer>();
		for (String id:this.restaurants.keySet()){
			List<String> categories = this.restaurants.get(id).getCategories();
			for (int i = 0; i < categories.size(); i++){
				String cg = categories.get(i);
				if(categorycount.containsKey(cg)){
					int count = categorycount.get(cg);
					count++;
					categorycount.replace(cg, count);
				} else {
					categorycount.put(cg, 1);
				}
			}			
		}
		return categorycount;
	}

	public List<String> getCategoryilst(){
		List<Integer> count = new ArrayList<Integer>();
		HashMap<String,Integer> frequency = this.getCategorycount();
		for (String key:frequency.keySet()){
			count.add(frequency.get(key));
		}
		Collections.sort(count,Collections.reverseOrder());
		count = count.subList(0, 19);
		categorylist = new HashSet<String>();
		for (String key:frequency.keySet())
			for (int i = 0; i<count.size(); i++){
				if (frequency.get(key) == count.get(i)){
					categorylist.add(key);
				}
			}
		List<String> sortlist = new ArrayList<String>(categorylist);
		Collections.sort(sortlist);
	    return sortlist;
	}

}
