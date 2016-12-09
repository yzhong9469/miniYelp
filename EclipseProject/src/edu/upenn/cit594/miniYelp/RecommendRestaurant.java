package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecommendRestaurant implements Recommend {
	private ArrayList<Restaurant> recList;
	
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
	
	public ArrayList<String[]> getCategory(ArrayList<Restaurant> resList){
		ArrayList<String[]> categoryList = new ArrayList<String[]>();
		for (int i = 0; i < resList.size(); i++){
			String[] categories = resList.get(i).getCategory();
			categoryList.add(categories);
		}
		return categoryList;
	}
	
	
	@Override
	public List<Restaurant> recommRestaurant() {
		// TODO Auto-generated method stub
		return null;
	}

}
