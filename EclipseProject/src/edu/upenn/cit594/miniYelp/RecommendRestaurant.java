package edu.upenn.cit594.miniYelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RecommendRestaurant implements Recommend {
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

	public List<Restaurant> getReclist(User user){
		List<Restaurant> reclist = new ArrayList<Restaurant>();
		List<Restaurant> reslist = restaurantParser.getRestaurantlist();
		if (user.getRatings().isEmpty()){
			for (int i = 0; i < reslist.size(); i++){
				if(reslist.get(i).getRating() >= 4.5){
					reclist.add(reslist.get(i));
				}
			}
			Collections.shuffle(reclist);
		} else {
			ArrayList<Restaurant> highRatings = getHighRatings(user);
			HashMap<String,Integer> categoryCt = getCategory(highRatings);
			ArrayList<String> selectCat = selectCategory(categoryCt);
			for (int i = 0; i < selectCat.size(); i++){
				String cg = selectCat.get(i);
				for (int j = 0; j < reslist.size(); i++){
					if (reslist.get(j).getCategories().contains(cg) && reslist.get(j).getRating() >= 4.5
							&& !user.getRatings().containsKey(reslist.get(j))){
						reclist.add(reslist.get(j));
					}
				}
				if (reclist.size() < 10){
					for (int k = 0; k < reslist.size(); i++){
						while(reclist.size()<10){
							if (reslist.get(k).getCategories().contains(cg) && reslist.get(k).getRating() == 4
									&& !user.getRatings().containsKey(reslist.get(k))){
								reclist.add(reslist.get(k));
							}
						}
					}
				}
			}
			Collections.shuffle(reclist);
		}
		return reclist.subList(0,10);
	}
	@Override
	public List<Restaurant> recommRestaurant() {
		// TODO Auto-generated method stub
		return null;
	}

}
