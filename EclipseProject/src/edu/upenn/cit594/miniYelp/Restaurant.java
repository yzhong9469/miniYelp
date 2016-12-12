package edu.upenn.cit594.miniYelp;

import java.util.List;
import java.util.Set;

/**
 * Restaurant
 * 
 * @author MHu
 *
 */
public class Restaurant implements Comparable<Restaurant>{
	//instance variables
	private String id;
	private String name;
	private String price;
	private List<String> categories;
	private double longitude;
	private double latitude;
	private String zipcode;
	private String address;
	private float rating;
	private int reviewCount;
	private String phone;
	
	//constructor
	public Restaurant(String id, String name, String price, List<String> categories, double longitude, double latitude, String zipcode, String address, float rating, int reviewCount, String phone) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.categories = categories;
		this.longitude = longitude;
		this.latitude = latitude;
		this.zipcode = zipcode;
		this.address = address;
		this.rating = rating;
		this.reviewCount = reviewCount;
		this.phone = phone;
	}

	/**
	 * getter methods
	 */
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public List<String> getCategories() {
		return categories;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getAddress() {
		return address;
	}

	public float getRating() {
		return rating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public String getPhone() {
		return phone;
	}

	/**
	 * determine if the restaurant belongs to a set of categories
	 * @param c a set of categories
	 * @return true if the restaurants belongs to; otherwise false
	 */
	public boolean containCategories(Set<String> c) {
		for (int i = 0; i < categories.size(); i++) {
			if (c.contains(categories.get(i))) return true;
		}
		
		return false;
	}
	
	/**
	 * printable version to display restaurant's information
	 * @param index
	 * @return
	 */
	public String toString(int index) {
		StringBuilder sb = new StringBuilder();
		sb.append(index + ". ");
		sb.append(this.name + "   " + this.price + "\n");
		sb.append("Rating: " + this.rating + "\tReviews: " + this.reviewCount + "\n");
		sb.append(this.address + "\n");
		
		String category = getCategories().toString().substring(1);
		category = category.substring(0, category.length() - 1);
		
		sb.append(category + "\n");
		return sb.toString();
	}

	/**
	 * ordered by rating and review count descendingly
	 */
	@Override
	public int compareTo(Restaurant o) {
		if (this.rating > o.rating){
			return -1;
		} else if (this.rating < o.rating) {
			return 1;
		} else {
			if (this.reviewCount > o.reviewCount){
				return -1;
			} else if (this.reviewCount < o.reviewCount) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
