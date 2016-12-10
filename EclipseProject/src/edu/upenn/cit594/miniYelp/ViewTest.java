package edu.upenn.cit594.miniYelp;

import java.awt.GridBagLayout;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewTest {

	public static void main(String[] args) {
		JFrame miniYelp = new JFrame();
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(800, 500);
		miniYelp.setVisible(true);
		
//		
//		JLabel background=new JLabel(new ImageIcon("U-Penn.jpg"));
//        miniYelp.add(background);
//        miniYelp.setLayout(new FlowLayout());
//        miniYelp.setVisible(true);
		
		
        
		JPanel login = new LoginView();
		login.setVisible(true);
		miniYelp.add(login);
		//miniYelp.pack();
		
		miniYelp.setVisible(true);
//		miniYelp.setVisible(true);
//		
//		JPanel search = new SearchView();
//		search.setVisible(true);
//		miniYelp.add(search);
//		miniYelp.setVisible(true);
//		miniYelp.remove(search);
		
		
		//new SearchView().setVisible(true);

	}

}
