package edu.upenn.cit594.miniYelp;

import java.awt.*;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LoginView extends JPanel{
	
	
	BufferedImage background;
	//String message = "Wrong Password!";
	
	private JTextField email = new JTextField(10);
	private JPasswordField password = new JPasswordField(10);
	
	private JButton login = new JButton("Login");
	private JButton register = new JButton("register");
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public LoginView(){
		
		try {
			background = ImageIO.read(new File("U-Penn.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(800, 500);
		setLayout(new GridBagLayout());
				
		JPanel fields = setField();
		
		add(fields);
	}
	
	public String getEmail(){
		return email.getText();
	}
	
	public char[] getPassword(){
		return password.getPassword();
	}
	
	void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
	
//	public void setMessage(String s){
//		message = s;
//	}
	
	public void setLoginAction(ActionListener a){
		login.addActionListener(a);
	}
	
	public void setRegisterAction(ActionListener a){
		register.addActionListener(a);
	}
	
	public JPanel setField(){
		JPanel fields = new JPanel();
		fields.setLayout(new GridBagLayout());
		fields.setBorder(new EmptyBorder(20,20,20,20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		JLabel welcome = new JLabel("Welcome to Mini Yelp for Penn!");
		welcome.setBorder(new EmptyBorder(0,5,10,5));
		fields.add(welcome, gbc);
		
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        fields.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        fields.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        fields.add(email, gbc);
        
        gbc.gridy++;
        fields.add(password, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        fields.add(login, gbc);
        gbc.gridx++;
        fields.add(register, gbc);
        
//        JLabel error = new JLabel(message);
//        error.setBorder(new EmptyBorder(5,5,0,5));
//        gbc.gridx = 0;
//        gbc.gridy++;
//        gbc.gridwidth = 3;
//        fields.add(error, gbc);
        return fields;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        
        double desired = 8 * 1.0 /5;
        double ratio = w * 1.0/h;
        
//        if (h > 740 && w > 1210){
//        	try {
//				background = ImageIO.read(new File("U-Penn2.jpg"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//        }
        
        if (h < 500 && w < 800){
        	g.drawImage(background, 0, 0, 800, 500, this);
        }else 
        if (ratio < desired){
        	g.drawImage(background, 0, 0, (int) (h * desired), h, this);
        }else{
        	g.drawImage(background, 0, 0, w, (int) (w/desired), this);
        }
        
//        if (h < 500){
//        	g.drawImage(normal, 0, 0, getWidth(), 500, this);
//        }else if (w > 800 || h > 500){
//        	g.drawImage(normal, 0, 0, getWidth(), getHeight(), this);
//        }else{
//        	g.drawImage(normal, 0, 0, this);
//        }
    }

}
