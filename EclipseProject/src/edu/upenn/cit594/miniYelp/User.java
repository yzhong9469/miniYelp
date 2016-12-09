package edu.upenn.cit594.miniYelp;

import java.util.HashMap;


public class User {
	private String login_id;
	private String user_name;
	private String password;
	private HashMap<Restaurant,Integer> ratings;
	
	public User(String loginId, String userName, String password){
		this.login_id = loginId;
		this.user_name = userName;
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
		return this.login_id;
	}
}
