package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class RecommendRestaurant implements Recommend {
	RestaurantCollection rc;
	
	public RecommendRestaurant() {
		rc = RestaurantCollection.getInstance();
	}

	private ArrayList<String> getHighRatedRestaurantID(User user){
		ArrayList<String> list = new ArrayList<>();
		HashMap<String, Double> userRatings = user.getRatings();
		
		for (String id : userRatings.keySet()) {
			if (userRatings.get(id) >= rc.getRestaurant(id).getRating()){
				list.add(id);
			}
		}
		return list;
	}
	
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
	

	public HashSet<String> getUserFavoriteCategories(ArrayList<String> resList){
		HashMap<String,Integer> categoryCt = new HashMap<String,Integer>();
		
		//count the number of the appearance of each category
		for (int i = 0; i < resList.size(); i++){
			List<String> categories = rc.getRestaurant(resList.get(i)).getCategories();
			for (int j = 0; j < categories.size(); j++){
				Integer ct = categoryCt.get(categories.get(j));
				if (ct == null) ct = 0;
				categoryCt.put(categories.get(i), ++ct);
			}
		}
		
		//select the first five highest categories
		PriorityQueue<Point> pq = new PriorityQueue<>();
		for (String category : categoryCt.keySet()) {
			Point p = new Point(category, categoryCt.get(category));
			pq.add(p);
			if (pq.size() > 5) pq.poll();
		}
		
		HashSet<String> favoriteCategories = new HashSet<String>();
		while (!pq.isEmpty()) {
			favoriteCategories.add(pq.poll().getId());
		}
		
		return favoriteCategories;
	}
	

	@Override
	public List<Restaurant> recommRestaurant(User user) {
		ArrayList<Restaurant> result = new ArrayList<>();
		Iterator<String> it = rc.getAllRestaurants().iterator();
		
		if (user.getRatings().isEmpty()){
			while (it.hasNext()) {
				Restaurant r = rc.getRestaurant(it.next());
				if (r.getRating() >= 4.5) result.add(r);
			} 
			
			Collections.shuffle(result);
			return result.subList(0, Math.min(10, result.size()));
		}
		
		ArrayList<String> restIDs = getHighRatedRestaurantID(user);
		HashSet<String> favoriteCategories = getUserFavoriteCategories(restIDs);
		ArrayList<Restaurant> backup = new ArrayList<>();
		
		while (it.hasNext()) {
			Restaurant r = rc.getRestaurant(it.next());
			if (user.ratedRestaurantBefore(r.getId()) || !r.containCategories(favoriteCategories)) continue;
			
			if (r.getRating() >= 4.5) result.add(r);
			else if (r.getRating() == 4) backup.add(r);
			else continue;
		}
		
		int index = new Random().nextInt(backup.size());
		int s1 = result.size(), s2 = backup.size();
		
		if (s1 + s2 < 10) {
			result.addAll(backup);
		} else if (s1 < 10){
			for (int i = 0; i < 10 - s1; i++) {
				result.add(backup.get((i + index) % s2));
			}
		}
		
		Collections.shuffle(result);
		return result.subList(0, Math.min(10, result.size()));
	}
	
}
