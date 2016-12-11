package edu.upenn.cit594.miniYelp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

public class Main {
	
	private JFrame miniYelp;
	private JPanel search;
	private JPanel recommend;
	private JPanel login;
	private JPanel profile;
	private RestaurantFileParser restParser;
	private UserProfileParser userParser;
	private User user;
	
	
	public Main() throws BadLocationException{
		miniYelp = new JFrame();
		search = new SearchView();
		recommend = new RecommendView();
		login = new LoginView();
		
		restParser = new RestaurantFileParser();
		restParser.loadData("restaurant.csv");
		userParser = new UserProfileParser();
		userParser.loadData("user_profile.txt");
		
	}

	public static void main(String[] args) {
		
		try {
			Main main = new Main();
			main.runMiniYelp();
		} catch (BadLocationException e) {
		}

	}
	
	public void runMiniYelp(){
		setup();
		setActions();
		miniYelp.setVisible(true);
	}
	
	private void setup(){
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(1210, 740);
		miniYelp.setVisible(true);
		miniYelp.setResizable(false);
		
		search.setVisible(false);
		recommend.setVisible(false);
		login.setVisible(true);
		
		
		miniYelp.add(search);
		miniYelp.add(recommend);
        miniYelp.add(login);
		
	}
	
	
	public void setActions() {
		
		((RecommendView) recommend).addReturnListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				recommend.setVisible(false);
				search.setVisible(true);
				miniYelp.setVisible(true);	
			}
		});
		
		((SearchView) search).addRecommendListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				search.setVisible(false);
				recommend.setVisible(true);
				try {
					performRecommend();
				} catch (BadLocationException e1) {
				}
				miniYelp.setVisible(true);
			}
		});
		
		((SearchView) search).addSearchListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					performSearch();
				} catch (BadLocationException e1) {
				}
			}
		});
		
		
		((LoginView) login).setLoginAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = ((LoginView) login).getEmail();
				String password = new String(((LoginView) login).getPassword());
				if (email.length() == 0){
					((LoginView) login).displayErrorMessage("Email cannot be empty");
				}else if (password.length() == 0){
					((LoginView) login).displayErrorMessage("Password cannot be empty");
				}else{
					User user = UserCollection.getInstance().login(email, password);
					if (user == null){
						((LoginView) login).displayErrorMessage("Wrong username or password!");
					}else{
						Main.this.user = user;
						try {
							profile = new ProfileView(user);
							profile.setVisible(false);
							((ProfileView) profile).addReturnAction(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent e) {
									profile.setVisible(false);
									search.setVisible(true);
									miniYelp.setVisible(true);	
								}
							});
							miniYelp.add(profile);
						} catch (BadLocationException e1) {
						}
						login.setVisible(false);
						search.setVisible(true);
						miniYelp.setVisible(true);
					}
				}
				
			}
		});
		
		
		((LoginView) login).setRegisterAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = ((LoginView) login).getEmail();
				String password = new String(((LoginView) login).getPassword());
				if (email.length() == 0){
					((LoginView) login).displayErrorMessage("Email cannot be empty");
				}else if (password.length() == 0){
					((LoginView) login).displayErrorMessage("Password cannot be empty");
				}else{
					User user = UserCollection.getInstance().register(email, password);
					if (user == null){
						((LoginView) login).displayErrorMessage("This email address has already been userd!");
					}else{
						Main.this.user = user;
						try {
							profile = new ProfileView(user);
							profile.setVisible(false);
							((ProfileView) profile).addReturnAction(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent e) {
									profile.setVisible(false);
									search.setVisible(true);
									miniYelp.setVisible(true);	
								}
							});
							miniYelp.add(profile);
						} catch (BadLocationException e1) {
						}
						login.setVisible(false);
						search.setVisible(true);
						miniYelp.setVisible(true);
					}
				}
			}
		});
		
		((SearchView) search).addProfileListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				profile.setVisible(true);
				search.setVisible(false);
				miniYelp.setVisible(true);
			}
			
		});
		
		((SearchView) search).addExitListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				userParser.writeData();
				System.exit(0);
			}
			
		});
	}
	
	private void performSearch() throws BadLocationException{
		String category = ((SearchView) search).getCategory();
		String price = ((SearchView) search).getPrice();
		String rating = ((SearchView) search).getRating();
		String review = ((SearchView) search).getReview();
		SearchRestaurant sr = new SearchRestaurant();
		String[] filter = {category, price, rating, review};
		ArrayList<Restaurant> result = (ArrayList<Restaurant>) sr.searchRestaurant(filter);
		((SearchView) search).addMarkers(result);
		((SearchView) search).addDescription(result);
		
	}
	
	private void performRecommend() throws BadLocationException{
		User user = UserCollection.getInstance().getUser("manh@seas.upenn.edu");
		RecommendRestaurant rr = new RecommendRestaurant();
		List<Restaurant> result = rr.recommRestaurant(user);
		((RecommendView) recommend).addMarkers(result);
		((RecommendView) recommend).addDescription(result);
	}

}
