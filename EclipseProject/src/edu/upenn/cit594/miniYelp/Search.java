package edu.upenn.cit594.miniYelp;

import java.util.List;

public interface Search {
	public List<Restaurant> searchRestaurant(String... filters);
}
