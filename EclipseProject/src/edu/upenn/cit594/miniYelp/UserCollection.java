package edu.upenn.cit594.miniYelp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class UserCollection implements Iterable<Entry<String, User>> {
	private static UserCollection userCollection;
	
	private HashMap<String,User> users;
	
	private UserCollection() {
		this.users = new HashMap<String,User>();
	}
	
	public static UserCollection getInstance() {
		if (userCollection == null) {
			userCollection = new UserCollection();
		}
		return userCollection;
	}

	public User getUser(String userId) {
		if (userId == null) return null;
		
		return users.get(userId);
	}
	
	public void addUser(User user){
		if (user == null) return;
		
		String id = user.getId();
		if (users.containsKey(id)) throw new IllegalArgumentException(id + " is already in list");
		
		this.users.put(id, user);
	}

	@Override
	public Iterator<Entry<String, User>> iterator() {
		return users.entrySet().iterator();
	}

}
