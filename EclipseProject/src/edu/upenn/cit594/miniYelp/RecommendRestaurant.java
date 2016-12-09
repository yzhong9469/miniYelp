package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RecommendRestaurant implements Recommend {
	private ArrayList<Restaurant> recList;
	private RestaurantFileParser restaurantParser;
	
	public RecommendRestaurant(){
	restaurantParser = new RestaurantFileParser();
	}
	
	
	public ArrayList<Restaurant> getHighRatings(User user){
		ArrayList<Restaurant> list = new ArrayList<Restaurant>();
		HashMap<Restaurant,Integer> userRatings = user.getRatings();
		for (Restaurant restaurant:userRatings.keySet()){
			if (userRatings.get(restaurant) >= restaurant.getRating()){
				list.add(restaurant);
			}
		}
		return list;
	}
	
	public HashMap<String,Integer> getCategory(ArrayList<Restaurant> resList){
		HashMap<String,Integer> categoryCt = new HashMap<String,Integer>();
		for (int i = 0; i < resList.size(); i++){
			List<String> categories = resList.get(i).getCategories();
			for (int j = 0; j < categories.size(); j++){
				int ct;
				if (categoryCt.containsKey(categories.get(j))){
					ct = categoryCt.get(categories.get(j));
					categoryCt.replace(categories.get(j), ct+1);
				} else {
					categoryCt.put(categories.get(j),1);
				}
			}
		}
		return categoryCt;
	}
	
	public ArrayList<String> selectCategory(HashMap<String,Integer> categoryCt){
		ArrayList<String> cg = new ArrayList<String>();
		if (categoryCt.size() <= 5){
			for (String category:categoryCt.keySet()){
				cg.add(category);
			}
		} else {
			ArrayList<Integer> count = new ArrayList<Integer>();
			for (String cat:categoryCt.keySet()){
				count.add(categoryCt.get(cat));
			}
			Collections.sort(count);
			Collections.reverse(count);
			for (int k = 0; k < 5; k++){
				for (String cat:categoryCt.keySet()){
					if (categoryCt.get(cat) == count.get(k)){
						cg.add(cat);
					}
				}
			}
		}
		return cg;
	}
	
	public ArrayList<Restaurant> getReclist(User user){
		ArrayList<Restaurant> reclist = new ArrayList<Restaurant>();
		List<Restaurant> resList = restaurantParser.getRestaurantlist();
		if (user.getRatings().isEmpty()){
			for (int i = 0; i < resList.size(); i++){
				
			}
		}
		return reclist;
	}
	@Override
	public List<Restaurant> recommRestaurant() {
		// TODO Auto-generated method stub
		return null;
	}

}
