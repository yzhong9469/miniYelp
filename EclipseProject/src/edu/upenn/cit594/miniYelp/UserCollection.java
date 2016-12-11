package edu.upenn.cit594.miniYelp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Stores information of registered users
 * @author Ziyi
 *
 */
public class UserCollection implements Iterable<Entry<String, User>> {
	//instance variables
	private static UserCollection userCollection;
	private HashMap<String, User> users;
	
	//constructor
	private UserCollection() {
		this.users = new HashMap<String,User>();
	}
	
	//singleton
	public static UserCollection getInstance() {
		if (userCollection == null) {
			userCollection = new UserCollection();
		}
		return userCollection;
	}
	
	/**
	 * Get user
	 * @param userId
	 * @return user
	 */
	public User getUser(String userId) {
		if (userId == null) return null;
		
		return users.get(userId);
	}
	
	/**
	 * Add user into the collection
	 * @param user
	 */
	public void addUser(User user){
		if (user == null) return;
		
		String id = user.getId();
		if (users.containsKey(id)) throw new IllegalArgumentException(id + " is already in list");
		
		this.users.put(id, user);
	}

	/**
	 * Get iterator
	 */
	@Override
	public Iterator<Entry<String, User>> iterator() {
		return users.entrySet().iterator();
	}
	
	/**
	 * Proceed user in login process
	 * @param id
	 * @param password
	 * @return user if successfully login, else return null
	 */
	public User login(String id, String password) {
		if (id == null || password == null) return null;
		
		User u = users.get(id);
		if (u == null) return null;
		
		if (password.equals(u.getPassword())) return u;
		else return null; 
	}
	
	/**
	 * Register new users
	 * @param id
	 * @param password
	 * @return user if new, else return null
	 */
	public User register(String id, String password) {
		if (users.containsKey(id)) return null;

		User u = new User(id, password);
		users.put(id, u);
		return u;
	}

}
