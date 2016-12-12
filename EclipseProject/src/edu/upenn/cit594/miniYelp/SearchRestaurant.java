package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Searches restaurant that meets certain criteria.
 * @author Ziyi
 *
 */
public class SearchRestaurant implements Search {
	//instance variables
	private ArrayList<Restaurant> searchResults;;
	private RestaurantCollection rescollection;

	//constructor
	public SearchRestaurant(){
		searchResults = new ArrayList<Restaurant>();
		rescollection = RestaurantCollection.getInstance();
	}

	/**
	 * Searches for restaurants that meet certain criteria
	 */
	@Override
	public List<Restaurant> searchRestaurant(String[] filters) {
		float rating = 0;
		int reviewCount = 0;
		String category = filters[0];
		String pricerange = filters[1];
		//parse rating to float number
		if (!filters[2].equals("All")){
			rating = Float.parseFloat(filters[2]);
		}
		//parse review count to integer
		if (!filters[3].equals("All")){
			reviewCount = Integer.parseInt(filters[3]);
		}
		//get the restaurant collection
		HashMap<String,Restaurant> collection = rescollection.getCollection();
		//add the restaurants that meet the screening criteria into the list
		for (String rid:collection.keySet()){
			Restaurant res = collection.get(rid);
			if (!res.getCategories().contains(category) && category !="All"){
				continue;
			}
			if (!res.getPrice().equals(pricerange) && pricerange != "All"){
				continue;
			}
			if (res.getRating() < rating && !filters[2].equals("All")){
				continue;
			}
			if (res.getReviewCount() >= reviewCount || filters[3].equals("All")){
				searchResults.add(res);
			}
		}
		//sort the results to display restaurants from high to low ratings
		Collections.sort(searchResults);
		return searchResults;
	}
}
