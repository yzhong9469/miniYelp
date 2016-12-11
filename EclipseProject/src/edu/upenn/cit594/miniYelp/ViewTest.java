package edu.upenn.cit594.miniYelp;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

public class ViewTest {

	public static void main(String[] args) throws BadLocationException {
		JFrame miniYelp = new JFrame();
		miniYelp.setLayout(new BorderLayout());
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(800, 525);
		miniYelp.setResizable(false);
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
		
		
		miniYelp.add(search, BorderLayout.CENTER);
		miniYelp.add(recommend, BorderLayout.CENTER);
        miniYelp.add(login, BorderLayout.CENTER);
		((RecommendView) recommend).addReturnListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				recommend.setVisible(false);
				search.setVisible(true);
				search.repaint();
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
		
		
		
//		miniYelp.remove(search);
		
		
		//new SearchView().setVisible(true);

	}

}
