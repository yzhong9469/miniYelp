package edu.upenn.cit594.miniYelp;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import java.awt.*;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
* The Search View for mini Yelp, using JMapViewer to display the map
* Please include the modified JMapViewer Library 'myMap' as an external library
*
* @author Yan Zhong
*
*/
public class SearchView extends JPanel implements JMapViewerEventListener {

	private static final long serialVersionUID = 1L;
	
	private final JMapViewerTree treeMap;
	
	private String category = "All";
	private String price = "All";
	private String rating = "All";
	private String review = "All";
	
	JButton searchButton = new JButton("Search");
	JButton recommendButton = new JButton("Get Recommendation");
	JButton gotoProfile = new JButton("My Profile");
	JButton exit = new JButton("Exit");
	
	JComboBox<String> categorySelector;
	
	private JLabel categoryLabel = new JLabel("Category:");
	private JLabel priceLabel = new JLabel("Price Range:");
	private JLabel ratingLabel = new JLabel("Rating: ≥");
	private JLabel reviewLabel = new JLabel("Review Count: ≥");
	
	private String[] categoryChoice = new String[] {""};
	private String[] priceChoice = new String[] {""};
	private String[] ratingChoice = new String[] {""};
	private String[] reviewChoice = new String[] {""};

	private JTextPane textPane = new JTextPane();
	
	/**
	 * constructor, setup the view
	 * @throws BadLocationException
	 */
	public SearchView() throws BadLocationException {
		setSize(1210, 740);
	
		treeMap = new JMapViewerTree("Zones");

		setLayout(new BorderLayout());
	   
		JPanel panel = new JPanel(new BorderLayout());
	   
		JPanel panelTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JPanel helpPanel = new JPanel();
	
		add(panel, BorderLayout.NORTH);
		add(helpPanel, BorderLayout.SOUTH);
		panel.add(panelTop, BorderLayout.NORTH);
		panel.add(panelBottom, BorderLayout.SOUTH);
	   
		JLabel helpLabel = new JLabel("Drag to move,\n "
	           + "double click to zoom.");
		helpPanel.add(helpLabel);
		
		// set text panel to display information
		JScrollPane paneScrollPane = new JScrollPane(textPane);
		textPane.setEditable(false);
		paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(250, 155));
		paneScrollPane.setMinimumSize(new Dimension(10, 10));
		
		add(paneScrollPane, BorderLayout.WEST);
		
		setPriceChoice();
		setRatingChoice();
		setReviewChoice();
		
        addButtons(panelTop, panelBottom);

	   	setMap();
	}
	
	/**
	 * create a map
	 * @return
	 */
	private JMapViewer map() {
		return treeMap.getViewer();
	}
	
	/**
	 * set up the map
	 */
	private void setMap(){
		map().addJMVListener(this);

		map().setTileSource((TileSource) new OsmTileSource.Mapnik());
	   	map().setZoomContolsVisible(true);
	   	
		map().addMouseListener(new MouseAdapter() {
	   		@Override
	   		public void mouseClicked(MouseEvent e) {
	   			if (e.getButton() == MouseEvent.BUTTON1) {
	   				map().getAttribution().handleAttribution(e.getPoint(), true);
	   			}
	   		}
	   	});
	
	   	map().addMouseMotionListener(new MouseAdapter() {
	   		@Override
	   		public void mouseMoved(MouseEvent e) {
	   			Point p = e.getPoint();
	   			boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
	   			if (cursorHand) {
	   				map().setCursor(new Cursor(Cursor.HAND_CURSOR));
	   			} else {
	   				map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	   			}
	   		}
	   	});
	   	
	   	map().setDisplayPosition(new Coordinate(39.952676, -75.194000), 16);
	   	treeMap.setVisible(true);
	   	add(treeMap, BorderLayout.CENTER);
	}
	
	/**
	 * add buttons to the view
	 * @param panelTop the top row
	 * @param panelBottom the bottom row
	 */
	private void addButtons(JPanel panelTop, JPanel panelBottom){

		/*
		 * the first filter choice
		 */
		panelTop.add(categoryLabel);
		categorySelector = new JComboBox<>(categoryChoice);
		categorySelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				category = e.getItem().toString();
	       }	
		});
		panelTop.add(categorySelector);
		
		/*
		 * the second filter choice
		 */
		panelTop.add(priceLabel);
		JComboBox<String> priceSelector = new JComboBox<>(priceChoice);
		priceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				price = e.getItem().toString();
			}
		});
		panelTop.add(priceSelector);
		
		/*
		 * the third filter choice
		 */
		panelTop.add(ratingLabel);
	   	JComboBox<String> ratingSelector = new JComboBox<>(ratingChoice);
	   	ratingSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	   			rating = e.getItem().toString();
	   		}
	   	});
	   	panelTop.add(ratingSelector);
	   
	   	/*
		 * the fourth filter choice
		 */
	   	panelTop.add(reviewLabel);
	   	JComboBox<String> reviewSelector = new JComboBox<>(reviewChoice);
	   	reviewSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	   			review = e.getItem().toString();
	   		}
	   	});
	   	panelTop.add(reviewSelector);
	   	
	   	/*
		 * trigger search operation
		 */
	   	panelBottom.add(searchButton);
	   	
	   	JButton showAll = new JButton("Show All Markers");
	   	showAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });
        panelBottom.add(showAll);
        
        panelBottom.add(recommendButton);
        
        panelBottom.add(gotoProfile);
        panelBottom.add(exit);
	}
	
	/**
	 * add text to the panel
	 * @param s the text to be added
	 * @throws BadLocationException
	 */
	public void addText(String s) throws BadLocationException{
		StyledDocument doc = textPane.getStyledDocument();
		doc.insertString(0, s, null);
	}
	
	/**
	 * add a list of markers to the map
	 * @param restaurants
	 */
	public void addMarkers(ArrayList<Restaurant> restaurants){
		removeMarkers();
		int i = 1;
		for (Restaurant r : restaurants){
			String display = i + "";
			if (display.length() < 3){
				display = "  " + display;
			}
			MyMapMarker m = new MyMapMarker(display, 
					new Coordinate(r.getLatitude(), r.getLongitude()));
			map().addMapMarker(m);
			i++;
		}
	}
	
	/**
	 * add restaurant desc to the text panel
	 * @param restaurants a list of restaurants
	 * @throws BadLocationException
	 */
	public void addDescription(ArrayList<Restaurant> restaurants) throws BadLocationException{
		StyledDocument doc = textPane.getStyledDocument();
		doc.remove(0, doc.getLength());
		int i = 1;
		for (Restaurant r : restaurants){
			doc.insertString(doc.getLength(), r.toString(i) + "\n", null);
			i++;
		}
	}
	
	public void addSearchListener(ActionListener search){
	   	searchButton.addActionListener(search);
	}
	
	public void addRecommendListener(ActionListener recommend){
	   	recommendButton.addActionListener(recommend);
	}
	
	public void addProfileListener(ActionListener pAction){
		gotoProfile.addActionListener(pAction);
	}
	
	public void addExitListener(ActionListener eAction){
		exit.addActionListener(eAction);
	}
	
	public void removeMarkers(){
		map().removeAllMapMarkers();
	}
	
	/**
	 * set the category choices
	 * @param choice the list of choice
	 */
	public void setCategoryChoice(List<String> choice){
		
		String[] c = new String[choice.size()+1];
		int i = 1;
		for (String s : choice){
			c[i] = s;
			i++;
		}
		c[0] = "All";
		categoryChoice = c;
		DefaultComboBoxModel model = new DefaultComboBoxModel(categoryChoice);
		categorySelector.setModel(model);
	}
	
	public void setPriceChoice(){
		priceChoice = new String[]{"All", "$", "$$", "$$$"};
	}
	
	public void setRatingChoice(){
		ratingChoice = new String[]{"All", "5", "4", "3", "2", "1"};
	}
	
	public void setReviewChoice(){
		reviewChoice = new String[]{"All", "200", "100", "50", "25"};
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getRating(){
		return rating;
	}
	
	public String getPrice(){
		return price;
	}
	
	public String getReview(){
		return review;
	}
	
	/**
	 * required method, no use
	 */
	@Override
	public void processCommand(JMVCommandEvent arg0) {
	}

}

