package edu.upenn.cit594.miniYelp;

import java.util.HashMap;


public class User {
	private String userId;
	private String username;
	private String password;
	private HashMap<String,Double> ratings;
	
	public User(String userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.ratings = new HashMap<String, Double>();
	}
	
	public void updateRatings(String restaurantId, double rating) {
		this.ratings.put(restaurantId, rating);
	}
	
	public HashMap<String, Double> getRatings() {
		return this.ratings;
	}
	
	public String getId() {
		return this.userId;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId + "\t" + username + "\t" + password + "\t");
		
		for (String rid : ratings.keySet()) {
			sb.append(rid + ":" + ratings.get(rid) + ";");
		}
		
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
