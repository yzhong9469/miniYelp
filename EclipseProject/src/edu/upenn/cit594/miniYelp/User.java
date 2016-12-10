package edu.upenn.cit594.miniYelp;

import java.util.HashMap;


public class User {
	private String userId;
	private String username;
	private String password;
	private HashMap<Restaurant,Double> ratings;
	
	public User(String userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.ratings = new HashMap<Restaurant, Double>();
	}
	
	public void updateRatings(Restaurant restaurant, double rating) {
		this.ratings.put(restaurant, rating);
	}
	
	public HashMap<Restaurant, Double> getRatings() {
		return this.ratings;
	}
	
	public String getId() {
		return this.userId;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId + "\t" + username + "\t" + password + "\t");
		
		for (Restaurant r : ratings.keySet()) {
			sb.append(r.getId() + ":" + ratings.get(r) + ";");
		}
		
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
