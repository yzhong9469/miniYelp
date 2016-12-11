package edu.upenn.cit594.miniYelp;

import java.util.HashMap;

/**
 * This class stores information of individual users, as well as the ratings
 * the users have made. 
 * @author Ziyi
 *
 */
public class User {
	private String userId;
	private String password;
	private HashMap<String,Double> ratings;
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
		this.ratings = new HashMap<String, Double>();
	}
	
	/**
	 * Updates user's rating for individual restaurant
	 * @param restaurantId
	 * @param rating
	 */
	public void updateRatings(String restaurantId, double rating) {
		this.ratings.put(restaurantId, rating);
	}
	
	/**
	 * Gets user's ratings
	 * @return
	 */
	public HashMap<String, Double> getRatings() {
		return this.ratings;
	}
	
	/**
	 * Gets user's id
	 * @return
	 */
	public String getId() {
		return this.userId;
	}

	/**
	 * determine if a restaurant has been rated by this user before
	 * @param id restaurant id
	 * @return true if this user rated the given restaurant before; otherwise false
	 */
	public boolean ratedRestaurantBefore(String id) {
		return ratings.containsKey(id);
	}
	
	/**
	 * get password
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Printable version to display user's information and ratings
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId + "\t" + password + "\t");
		
		for (String rid : ratings.keySet()) {
			sb.append(rid + ":" + ratings.get(rid) + ";");
		}
		
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
