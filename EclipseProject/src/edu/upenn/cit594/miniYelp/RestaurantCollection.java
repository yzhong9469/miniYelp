package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RestaurantCollection
 * 
 * @author MHu
 *
 */
public class RestaurantCollection {
	//instance variables
	private static RestaurantCollection restCollection;
	private HashMap<String, Restaurant> restaurants;
	private HashMap<String,Integer> categorycount;
	private HashSet<String> categorylist;

	//constructor
	private RestaurantCollection() {
		this.restaurants = new HashMap<String, Restaurant>();
	}

	//singleton
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
	 * get the restaurant for a given id
	 * @param restaurantId
	 * @return
	 */
	public Restaurant getRestaurant(String restaurantId) {
		if (restaurantId == null) return null;

		return restaurants.get(restaurantId);
	}

	/**
	 * add a restaurant to the collection
	 * @param restaurant
	 */
	public void addRestaurant(Restaurant restaurant) {
		if (restaurant == null) return;

		restaurants.put(restaurant.getId(), restaurant);
	}

	/**
	 * get all restaurants' id in the collection
	 * @return
	 */
	public Set<String> getAllRestaurants() {
		return restaurants.keySet();
	}
	
	/**
	 * get all restaurants which have a similar name with the entered
	 * keyword
	 * @param keyword
	 * @return
	 */
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
	 * Get category count 
	 * @return a HashMap that key is category and value is category count
	 */
	public HashMap<String,Integer> getCategorycount(){
		categorycount = new HashMap<String,Integer>();
		//update category count 
		for (String id:this.restaurants.keySet()){
			List<String> categories = this.restaurants.get(id).getCategories();
			for (int i = 0; i < categories.size(); i++){
				String cg = categories.get(i);
				//if the category is already in the HashMap, update counting
				if(categorycount.containsKey(cg)){
					int count = categorycount.get(cg);
					count++;
					categorycount.replace(cg, count);
					//if the category is not in the HashMap, add the category in it
				} else {
					categorycount.put(cg, 1);
				}
			}			
		}
		return categorycount;
	}
	
	/**
	 * Get a list of categories
	 * @return a list of categories
	 */
	public List<String> getCategoryilst(){
		List<Integer> count = new ArrayList<Integer>();
		HashMap<String,Integer> frequency = this.getCategorycount();
		//add category counts in the list
		for (String key:frequency.keySet()){
			count.add(frequency.get(key));
		}
		//sort the list and keep the highest 20 counts
		Collections.sort(count,Collections.reverseOrder());
		count = count.subList(0, 19);
		categorylist = new HashSet<String>();
		//get the list of categories with highest 20 counts
		for (String key:frequency.keySet())
			for (int i = 0; i<count.size(); i++){
				if (frequency.get(key) == count.get(i)){
					categorylist.add(key);
				}
			}
		//sort the category in alphabetical order
		List<String> sortlist = new ArrayList<String>(categorylist);
		Collections.sort(sortlist);
	    return sortlist;
	}

}
