package edu.upenn.cit594.miniYelp;
/**
 * Creates restaurant object and stores information about the restaurant
 * @author Ziyi
 *
 */
public class Restaurant {
	private int restaurant_id;
	private String name;
	private Double latitude;
	private Double longtitude;
	private String priecerange;
	private int review_count;
	private Double rating;
	
	public Restaurant(int id, String name, 
			double lat, double lon, String pr, int rc, double rating){
		this.restaurant_id = id;
		this.name = name;
		this.latitude = lat;
		this.longtitude = lon;
		this.review_count = rc;
		this.priecerange = pr;
		this.rating = rating;
	} 
	
	public double getRating(){
		return this.rating;
	}
	
	
	
	
}
