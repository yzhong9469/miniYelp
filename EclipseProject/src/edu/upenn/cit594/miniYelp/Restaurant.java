package edu.upenn.cit594.miniYelp;

public class Restaurant {
	private int restaurant_id;
	private String name;
	private Double latitude;
	private Double longtitude;
	private String priecerange;
	private int review_count;
	private Double rating;
	private String[] category;

	public Restaurant(int id, String name, double lat, double lon, String pr, int rc, double rating, String[] category){
		this.restaurant_id = id;
		this.name = name;
		this.latitude = lat;
		this.longtitude = lon;
		this.review_count = rc;
		this.priecerange = pr;
		this.rating = rating;
		this.category = category;
	} 

	public double getRating(){
		return this.rating;
	}

	public String[] getCategory(){
		return this.category;
	}
}
