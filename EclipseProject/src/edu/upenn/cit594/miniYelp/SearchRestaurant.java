package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class searches restaurant that meets certain criteria.
 * @author Ziyi
 *
 */
public class SearchRestaurant implements Search {
	private ArrayList<Restaurant> searchResults;;
	private RestaurantCollection rescollection;

	public SearchRestaurant(){
		searchResults = new ArrayList<Restaurant>();
		rescollection = RestaurantCollection.getInstance();
	}


	@Override
	public List<Restaurant> searchRestaurant(String[] filters) {
		float rating = 0;
		int reviewCount = 0;
		String category = filters[0];
		String pricerange = filters[1];
		if (filters[2].length() != 0){
			rating = Float.parseFloat(filters[2]);
		}
		if (filters[3].length() != 0){
			reviewCount = Integer.parseInt(filters[3]);
		}
		HashMap<String,Restaurant> collection = rescollection.getCollection();
		for (String rid:collection.keySet()){
			Restaurant res = collection.get(rid);
			if (!res.getCategories().contains(category) && category !=""){
				continue;
			}
			if (!res.getPrice().equals(pricerange) && pricerange != ""){
				continue;
			}
			if (res.getRating() < rating && filters[2] != "" ){
				continue;
			}
			if (res.getReviewCount() >= reviewCount || filters[3] == ""){
				searchResults.add(res);
			}
		}
		return searchResults;
	}

}
