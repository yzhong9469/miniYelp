package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * RecommendRestaurant
 * 
 * @author MHu
 *
 */
public class RecommendRestaurant implements Recommend {
	RestaurantCollection rc;
	
	//constructor
	public RecommendRestaurant() {
		rc = RestaurantCollection.getInstance();
	}

	/**
	 * get a list of restaurant ID, which the given user rated higher
	 * than the restaurant's average rating
	 * @param user - login user
	 * @return a list of restaurant ID
	 */
	private ArrayList<String> getHighRatedRestaurantID(User user){		
		ArrayList<String> list = new ArrayList<>();
		HashMap<String, Double> userRatings = user.getRatings();
		
		for (String id : userRatings.keySet()) {
			System.out.println(rc.getRestaurant(id));
			if (userRatings.get(id) >= rc.getRestaurant(id).getRating()){
				list.add(id);
			}
		}
		return list;
	}
	
	/**
	 * helper class for priority queue
	 * @author MHu
	 *
	 */
	private class Point implements Comparable<Point> {
		String id;
		int count;
		
		public Point(String id, int count) {
			this.id = id;
			this.count = count;
		}

		@Override
		public int compareTo(Point o) {
			return this.count - o.count;
		}
		
		public String getId() {
			return id;
		}
	}
	
	/**
	 * get top five most frequent categories among a list of restaurant ID
	 * @param resList - a list of restaurant ID
	 * @return top five most frequent categories
	 */
	public HashSet<String> getUserFavoriteCategories(ArrayList<String> resList){
		HashMap<String,Integer> categoryCt = new HashMap<String,Integer>();
		
		//count the number of the appearance of each category
		for (int i = 0; i < resList.size(); i++){
			List<String> categories = rc.getRestaurant(resList.get(i)).getCategories();
			for (int j = 0; j < categories.size(); j++){
				Integer ct = categoryCt.get(categories.get(j));
				if (ct == null) ct = 0;
				categoryCt.put(categories.get(j), ++ct);
			}
		}
		
		//select the first five highest categories
		PriorityQueue<Point> pq = new PriorityQueue<>();
		for (String category : categoryCt.keySet()) {
			Point p = new Point(category, categoryCt.get(category));
			pq.add(p);
			if (pq.size() > 5) pq.poll();
		}
		
		//store the result in a hashset
		HashSet<String> favoriteCategories = new HashSet<String>();
		while (!pq.isEmpty()) {
			favoriteCategories.add(pq.poll().getId());
		}
		
		return favoriteCategories;
	}
	
	/**
	 * recommend at most ten restaurants for a given user
	 * if the user has not rated any restaurants before, randomly recommend
	 * any restaurants which has a rating greater or equal to 4.5
	 * 
	 * if the user has rated some restaurants before, get the user's favorite
	 * restaurant categories first, then randomly recommend restaurants belong 
	 * to those categories and has a rating greater or equal to 4.5 or 4.0
	 * 
	 * the return result is ordered by rating and review count descendingly
	 * 
	 */
	@Override
	public List<Restaurant> recommRestaurant(User user) {
		ArrayList<Restaurant> result = new ArrayList<>();
		List<Restaurant> topTenResult;
		Iterator<String> it = rc.getAllRestaurants().iterator();
		
		//if the user has not rated any restaurant before
		if (user.getRatings().isEmpty()){
			while (it.hasNext()) {
				Restaurant r = rc.getRestaurant(it.next());
				if (r.getRating() >= 4.5) result.add(r);
			} 
			
			Collections.shuffle(result);
			
			topTenResult = result.subList(0, Math.min(10, result.size()));
			Collections.sort(topTenResult);
			
			return topTenResult;
		}
		
		ArrayList<String> restIDs = getHighRatedRestaurantID(user); //get high rated restaurant from user's profile
		HashSet<String> favoriteCategories = getUserFavoriteCategories(restIDs); // get user's favourite categories
		ArrayList<Restaurant> backup = new ArrayList<>();
		
		while (it.hasNext()) {
			Restaurant r = rc.getRestaurant(it.next());
			if (user.ratedRestaurantBefore(r.getId()) || !r.containCategories(favoriteCategories)) continue;
			
			if (r.getRating() >= 4.5) result.add(r); //restaurants with rating >= 4.5
			else if (r.getRating() == 4) backup.add(r); //restaurants with rating == 4
			else continue;
		}
		
		int index = new Random().nextInt(backup.size());
		int s1 = result.size(), s2 = backup.size();
		
		//if less than 10 restaurants with rating >= 4.5, add restaurants with rating of 4.0 in the results
		if (s1 + s2 < 10) {
			result.addAll(backup);
		} else if (s1 < 10){
			for (int i = 0; i < 10 - s1; i++) {
				result.add(backup.get((i + index) % s2));
			}
		}
		
		topTenResult = result.subList(0, Math.min(10, result.size()));
		Collections.sort(topTenResult);
		
		return topTenResult;
	}
	
}
