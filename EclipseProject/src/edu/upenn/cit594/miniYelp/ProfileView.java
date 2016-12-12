package edu.upenn.cit594.miniYelp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.List;

public class ProfileView extends JPanel{
	
	
	private BufferedImage background;
	
	private User user;
	
	
	private JButton add = new JButton("Add Rating");
	private JButton returnToSearch = new JButton("Return");
	private JTextPane textPane = new JTextPane();
	
	private JTextField restaurant = new JTextField(10);
	JComboBox<String> ratingSelector = new JComboBox<>(new String[] {"5","4","3","2","1"});
	JComboBox<String> restaurantSelector = new JComboBox<>(new String [] {""});
	private String selectedRating = "";
	private String selectedRestaurantID = "";
	
	private JButton innerFind = new JButton("Find");
	private JButton innerAdd = new JButton("Add");
	
	JFrame popup = new JFrame();
	
	private List<Restaurant> choice;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public ProfileView(User user) throws BadLocationException{
		
		try {
			background = ImageIO.read(new File("U-Penn2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(1210, 740);
		setLayout(new GridBagLayout());
		this.user = user;
		
		JPanel fields = setField();
		
		add(fields);
		setAddAction();
	}
	
	
	void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
	
	public void setUser(User user){
		this.user = user;
	}
	
	public void setAddAction(){
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				popup = new JFrame();
				popup.setSize(900, 200);
				JPanel innerField = setInnerField();
				popup.add(innerField);
				popup.setVisible(true);
				
			}
		});
	}
	
	
	public JPanel setInnerField(){
		JPanel fields = new JPanel();
		fields.setLayout(new GridBagLayout());
		fields.setBorder(new EmptyBorder(20,20,20,20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        fields.add(new JLabel("Restaurant Name: "), gbc);
        gbc.gridy++;
        fields.add(new JLabel("Results: "), gbc);
        gbc.gridy++;
        fields.add(new JLabel("Rating: "), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        fields.add(restaurant, gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.NONE;
        fields.add(innerFind, gbc);
        
        ratingSelector.addItemListener(new ItemListener() {
    		@Override
    		public void itemStateChanged(ItemEvent e) {
    			selectedRating = e.getItem().toString();
           }	
    	});
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        fields.add(ratingSelector, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        fields.add(restaurantSelector, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        
        innerAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = restaurantSelector.getSelectedIndex();
				//user.updateRatings(rests.get(index).getId(), Double.parseDouble(selectedRating));
				if (index != 0){
					user.updateRatings(choice.get(index - 1).getId(), 
								Double.parseDouble(ratingSelector.getSelectedItem().toString()));
					try {
						displayRating();
						popup.setVisible(false);
					} catch (BadLocationException e1) {
					}
				}
			}
        });
        
        
        fields.add(innerAdd, gbc);
        
        return fields;
	}
	
	public void setSelectedRestaurantID(String ID){
		selectedRestaurantID = ID;
	}
	
	public void setRestaurantChoice(List<Restaurant> choice){
		this.choice = choice;
	}
	
	public int getSelectedIndex(){
		return restaurantSelector.getSelectedIndex();
	}
	
	public void addInnerAddAction(ActionListener a){
		innerAdd.addActionListener(a);
	}
	
	public void addReturnAction(ActionListener a){
		returnToSearch.addActionListener(a);
	}
	
	public JPanel setField() throws BadLocationException{
		JPanel fields = new JPanel();
		fields.setLayout(new GridBagLayout());
		fields.setBorder(new EmptyBorder(20,20,20,20));
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.ipady = 5;
		gbc.ipadx = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		JLabel title = new JLabel("User Profile");
		title.setBorder(new EmptyBorder(0,5,10,5));
		fields.add(title, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        fields.add(new JLabel("Email:"), gbc);
        gbc.weightx = 1;
        gbc.gridy++;
        fields.add(new JLabel("Ratings:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        fields.add(new JLabel(user.getId()), gbc);
        
        
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        JScrollPane paneScrollPane = new JScrollPane(textPane);
		textPane.setEditable(false);
		paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(250, 155));
		paneScrollPane.setMinimumSize(new Dimension(10, 10));
		
		// add in text, create a function
		displayRating();
		fields.add(paneScrollPane, gbc);
		
		
		gbc.ipady = 1;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        fields.add(add, gbc);
        gbc.gridx++;
        fields.add(returnToSearch, gbc);
        
        return fields;
	}
	
	private void displayRating() throws BadLocationException{
		RestaurantCollection rc = RestaurantCollection.getInstance();
		HashMap<String, Double> ratings = user.getRatings();
		StyledDocument doc = textPane.getStyledDocument();
		
		doc.remove(0, doc.getLength());
		for (String id : ratings.keySet()){
			Restaurant r = rc.getRestaurant(id);
			doc.insertString(doc.getLength(), r.getName() + ": " + ratings.get(id) + "\n", null);
			//doc.insertString(doc.getLength(), id + ": " + ratings.get(id) + "\n", null);
		}
	}
	
	public void addInnerFindAction(ActionListener a){
		innerFind.addActionListener(a);
	}
	
	public String getInput(){
		return restaurant.getText();
	}
	
	public void setSelector(DefaultComboBoxModel model){
		restaurantSelector.setModel(model);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        
        double desired = 8 * 1.0 /5;
        double ratio = w * 1.0/h;
        
        if (h < 500 && w < 800){
        	g.drawImage(background, 0, 0, 800, 500, this);
        }else 
        if (ratio < desired){
        	g.drawImage(background, 0, 0, (int) (h * desired), h, this);
        }else{
        	g.drawImage(background, 0, 0, w, (int) (w/desired), this);
        }
    }
	
	public static void main(String[] args) throws BadLocationException{
		User u = new User("abc@gmail.com","abc@gmail.com");
		System.out.println(u);
		u.updateRatings("123", 4.0);
		u.updateRatings("345", 2.0);
		JPanel test = new ProfileView(u);
		
		test.setVisible(true);
		JFrame miniYelp = new JFrame();
		miniYelp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniYelp.setSize(800, 500);
		
		
		miniYelp.add(test);
		miniYelp.setVisible(true);
	}

}
