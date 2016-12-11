package edu.upenn.cit594.miniYelp;

import java.util.List;
import java.util.Set;

public class Restaurant {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public boolean containCategories(Set<String> c) {
		for (int i = 0; i < categories.size(); i++) {
			if (c.contains(categories.get(i))) return true;
		}
		
		return false;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
