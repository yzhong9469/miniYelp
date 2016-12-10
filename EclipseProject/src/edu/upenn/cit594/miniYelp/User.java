package edu.upenn.cit594.miniYelp;

import java.util.HashMap;


public class User {
	private String userId;
	private String username;
	private String password;
	private HashMap<Restaurant,Integer> ratings;
	
	public User(String userId, String username, String password){
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.ratings = new HashMap<Restaurant,Integer>();
	}
	
	public void updateRatings(Restaurant restaurant, int rating){
		this.ratings.put(restaurant, rating);
	}
	
	public HashMap<Restaurant,Integer> getRatings(){
		return this.ratings;
	}
	
	public String getId(){
		return this.userId;
	}

}
