package edu.upenn.cit594.miniYelp;

import java.util.List;
/**
 * Search interface
 * @author Ziyi
 *
 */
public interface Search {
	/**
	 * Search restaurants from collections that meet the 
	 * criteria in the filters
	 * @param filters
	 * @return a list of searched restaurants
	 */
	public List<Restaurant> searchRestaurant(String[] filters);
}
