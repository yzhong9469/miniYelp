package edu.upenn.cit594.miniYelp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

public class Main {
	
	private JFrame miniYelp;
	private JPanel search;
	private JPanel recommend;
	private JPanel login;
	
	public Main() throws BadLocationException{
		miniYelp = new JFrame();
		search = new SearchView();
		recommend = new RecommendView();
		login = new LoginView();
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
		miniYelp.setSize(800, 500);
		miniYelp.setVisible(true);
		
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
				miniYelp.setVisible(true);
			}
		});
		
		
		((LoginView) login).setLoginAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				login.setVisible(false);
				search.setVisible(true);
				miniYelp.setVisible(true);
			}
		});
		
		((LoginView) login).setRegisterAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((LoginView) login).getEmail().length() == 0){
					((LoginView) login).displayErrorMessage("Email cannot be empty");
				}else if (((LoginView) login).getPassword().length == 0){
					((LoginView) login).displayErrorMessage("Password cannot be empty");
				}else{
					((LoginView) login).displayErrorMessage("Wrong password!");
				}
			}
		});
		
	}

}
