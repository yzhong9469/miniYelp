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
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(800, 500);
		miniYelp.setVisible(true);
		
//		
//		JLabel background=new JLabel(new ImageIcon("U-Penn.jpg"));
//        miniYelp.add(background);
//        miniYelp.setLayout(new FlowLayout());
//        miniYelp.setVisible(true);
		
		JPanel search = new SearchView();
		search.setVisible(true);
        
		JPanel login = new LoginView();
		login.setVisible(true);
		((LoginView) login).setLoginAction(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				miniYelp.remove(login);
				miniYelp.add(search);
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
		
		miniYelp.add(login);
		
		miniYelp.setVisible(true);
		
		
		
//		miniYelp.remove(search);
		
		
		//new SearchView().setVisible(true);

	}

}
