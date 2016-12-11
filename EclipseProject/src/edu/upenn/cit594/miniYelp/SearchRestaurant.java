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
		String category = filters[0];
		String pricerange = filters[1];
		float rating = Float.parseFloat(filters[2]);
		int reviewCount = Integer.parseInt(filters[3]);
		HashMap<String,Restaurant> collection = rescollection.getCollection();
		for (String rid:collection.keySet()){
			Restaurant res = collection.get(rid);
			if (!res.getCategories().contains(category)){
				continue;
			}
			if (res.getPrice() != pricerange){
				continue;
			}
			if (res.getRating() != rating){
				continue;
			}
			if (res.getReviewCount() >= reviewCount){
				searchResults.add(res);
			}
		}
		return searchResults;
	}

}
