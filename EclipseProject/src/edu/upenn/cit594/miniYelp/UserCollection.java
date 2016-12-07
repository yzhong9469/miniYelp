package edu.upenn.cit594.miniYelp;

import java.util.HashMap;

public class UserCollection {
	private HashMap<String,User> userCollection;
	
	public UserCollection(){
		this.userCollection = new HashMap<String,User>();
	}
	
	public void addUser(User user){
		String id = user.getId();
		this.userCollection.put(id, user);
	}

}
