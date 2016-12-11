package edu.upenn.cit594.miniYelp;

import java.util.List;

/**
 * Recommend Interface
 * @author MHu
 *
 */
public interface Recommend {
	
	/**
	 * get a list of recommended restaurant for a given user
	 * @param user - given user
	 * @return a list of recommended restaurant
	 */
	public List<Restaurant> recommRestaurant(User user);

}
