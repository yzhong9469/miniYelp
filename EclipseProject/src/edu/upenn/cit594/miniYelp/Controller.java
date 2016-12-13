package edu.upenn.cit594.miniYelp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

/**
 * the controller, merge the model and the view together
 * @author Yan Zhong
 *
 */
public class Controller {
	
	private JFrame miniYelp;
	private JPanel search;
	private JPanel recommend;
	private JPanel login;
	private JPanel profile;
	private RestaurantFileParser restParser;
	private UserProfileParser userParser;
	private User user;
	
	/**
	 * constructor
	 * @throws BadLocationException
	 */
	public Controller() throws BadLocationException{
		miniYelp = new JFrame();
		search = new SearchView();
		recommend = new RecommendView();
		login = new LoginView();
		
		// load in data
		restParser = new RestaurantFileParser();
		restParser.loadData("restaurant.txt");
		userParser = new UserProfileParser();
		userParser.loadData("user_profile.txt");
		
	}
	
	/**
	 * call this function to run our program
	 * @throws BadLocationException
	 */
	public void runMiniYelp() throws BadLocationException{
		setup();
		setActions();
		miniYelp.setVisible(true);
	}
	
	/**
	 * set up all panels
	 * @throws BadLocationException
	 */
	private void setup() throws BadLocationException{
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(1210, 740);
		miniYelp.setVisible(true);
		miniYelp.setResizable(false);
		
		// set up the choices in categories
		List<String> choice = RestaurantCollection.getInstance().getCategoryilst();
		((SearchView) search).setCategoryChoice(choice);
		
		search.setVisible(true);
		search.setVisible(false);
		recommend.setVisible(false);
		login.setVisible(true);
		
		miniYelp.add(search);
		miniYelp.add(recommend);
        miniYelp.add(login);
	}
	
	/**
	 * set all buttons' action
	 */
	public void setActions() {
		
		// return to main page
		((RecommendView) recommend).addReturnListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				recommend.setVisible(false);
				search.setVisible(true);
				miniYelp.setVisible(true);	
			}
		});
		
		// go to recommend page
		((SearchView) search).addRecommendListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				search.setVisible(false);
				try {
					performRecommend();
				} catch (BadLocationException e1) {
				}
				recommend.setVisible(true);
				miniYelp.setVisible(true);
			}
		});
		
		// perfrom search
		((SearchView) search).addSearchListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					performSearch();
				} catch (BadLocationException e1) {
				}
			}
		});
		
		// login
		((LoginView) login).setLoginAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = ((LoginView) login).getEmail();
				String password = new String(((LoginView) login).getPassword());
				
				// error message
				if (email.length() == 0){
					((LoginView) login).displayErrorMessage("Email cannot be empty");
				}else if (password.length() == 0){
					((LoginView) login).displayErrorMessage("Password cannot be empty");
				}else{
					// try to find the user
					User user = UserCollection.getInstance().login(email, password);
					if (user == null){
						((LoginView) login).displayErrorMessage("Wrong username or password!");
					}else{
						// set up user
						Controller.this.user = user;
						try {
							profile = new ProfileView(user);
							profile.setVisible(false);
							setProfile(profile);
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
		
		// register action
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
						Controller.this.user = user;
						try {
							profile = new ProfileView(user);
							profile.setVisible(false);
							setProfile(profile);
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
		
		// go to profile
		((SearchView) search).addProfileListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				profile.setVisible(true);
				search.setVisible(false);
				miniYelp.setVisible(true);
			}
		});
		
		// exit the program, write out data
		((SearchView) search).addExitListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				userParser.writeData();
				System.exit(0);
			}
		});
	}
	
	/**
	 * set up the profile panel
	 * @param profile the profile panel
	 */
	private void setProfile(JPanel profile){
		
		// return to main page
		((ProfileView) profile).addReturnAction(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				profile.setVisible(false);
				search.setVisible(true);
				miniYelp.setVisible(true);	
			}
		});
		
		// find the possible matching restaurant
		((ProfileView) profile).addInnerFindAction(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String input = ((ProfileView) profile).getInput();
				List<Restaurant> result = RestaurantCollection.getInstance().getSimilarRestaurants(input);
				String[] choice = new String[result.size()+1];
				choice[0] = "";
				int i = 1;
				for (Restaurant r : result){
					String single = r.getName() + "\t" + r.getAddress();
					choice[i] = single;
					i++;
				}
				DefaultComboBoxModel model = new DefaultComboBoxModel(choice);
				((ProfileView) profile).setSelector(model);
				((ProfileView) profile).setRestaurantChoice(result);
			}
		});
	}
	
	/**
	 * perform search
	 * @throws BadLocationException
	 */
	private void performSearch() throws BadLocationException{
		String category = ((SearchView) search).getCategory();
		String price = ((SearchView) search).getPrice();
		String rating = ((SearchView) search).getRating();
		String review = ((SearchView) search).getReview();
		// search
		SearchRestaurant sr = new SearchRestaurant();
		String[] filter = {category, price, rating, review};
		// display
		ArrayList<Restaurant> result = (ArrayList<Restaurant>) sr.searchRestaurant(filter);
		((SearchView) search).addMarkers(result);
		((SearchView) search).addDescription(result);
		
	}
	
	/**
	 * perform recommend
	 * @throws BadLocationException
	 */
	private void performRecommend() throws BadLocationException{
		RecommendRestaurant rr = new RecommendRestaurant();
		List<Restaurant> result = rr.recommRestaurant(user);
		// display
		((RecommendView) recommend).addMarkers(result);
		((RecommendView) recommend).addDescription(result);
	}

}
