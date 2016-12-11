package edu.upenn.cit594.miniYelp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			runMiniYelp();
		} catch(BadLocationException e) {
			
		}

	}
	
	
	
	public static void runMiniYelp() throws BadLocationException {
		JFrame miniYelp = new JFrame();
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(800, 500);
		miniYelp.setVisible(true);
		
//		
//		JLabel background=new JLabel(new ImageIcon("U-Penn.jpg"));
//        miniYelp.add(background);
//        miniYelp.setLayout(new FlowLayout());
//        miniYelp.setVisible(true);
		
		JPanel search = new SearchView();
		search.setVisible(false);
		JPanel recommend = new RecommendView();
		recommend.setVisible(false);
		
		JPanel login = new LoginView();
		login.setVisible(true);
		
		
		miniYelp.add(search);
		miniYelp.add(recommend);
        miniYelp.add(login);
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
		
		miniYelp.setVisible(true);
		
	}

}
