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
	
	private String category;
	private String price;
	private String rating;
	private String review;
	
	
	
	private JLabel categoryLabel = new JLabel("Category:");
	private JLabel priceLabel = new JLabel("Price:");
	private JLabel ratingLabel = new JLabel("Rating:");
	private JLabel reviewLabel = new JLabel("Review Count:");

	private JTextPane textPane = new JTextPane();
	/*
	* Constructs the {@code Demo}.
	*/
	public SearchView() throws BadLocationException {
		setSize(800, 500);
	
		treeMap = new JMapViewerTree("Zones");
	   	
	   	// layout settings
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
		
		StyledDocument doc = textPane.getStyledDocument();
		for (int i = 0; i < 40; i++){
			doc.insertString(0, "Hello\n", null);
		}
		
        addButtons(panelTop);
	   	
	   	/*
	   	 * how to add marker example
	   	 */
	   	MapMarkerDot vanPelt = new MapMarkerDot("Van Pelt",new Coordinate(39.952676, -75.194000));
	   	addMarkers(vanPelt);
	   	
	   	setMap();
	}
	
	private JMapViewer map() {
		return treeMap.getViewer();
	}
	
	private void setMap(){
		map().addJMVListener(this);
		
		/*
	   	 * map settings
	   	 */
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
	
	private void addButtons(JPanel panelTop){

		/*
		 * the first filter choice
		 */
		panelTop.add(categoryLabel);
		JComboBox<String> categorySelector = new JComboBox<>(new String[] {"","Chinese","American","Food Truck"});
		categorySelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				category = e.getItem().toString();
				System.out.println(e.getItem());
	       }	
		});
		panelTop.add(categorySelector);
		
		/*
		 * the second filter choice
		 */
		panelTop.add(priceLabel);
		JComboBox<String> priceSelector = new JComboBox<>(new String[] {"","$","$$","$$$"});
		priceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				price = e.getItem().toString();
				System.out.println(e.getItem());
			}
		});
		panelTop.add(priceSelector);
		
		/*
		 * the third filter choice
		 */
		panelTop.add(ratingLabel);
	   	JComboBox<String> ratingSelector = new JComboBox<>(new String[] {"","5",">4",">3",
	  		 							">2",">1"});
	   	ratingSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	   			rating = e.getItem().toString();
	   			System.out.println(e.getItem());
	   		}
	   	});
	   	panelTop.add(ratingSelector);
	   
	   	/*
		 * the fourth filter choice
		 */
	   	panelTop.add(reviewLabel);
	   	JComboBox<String> reviewSelector = new JComboBox<>(new String[] {"",">200",">100",">50"});
	   	reviewSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	   			review = e.getItem().toString();
	   			System.out.println(e.getItem());
	   		}
	   	});
	   	panelTop.add(reviewSelector);
	   	
	   	/*
		 * trigger search operation
		 */
	   	JButton searchButton = new JButton("Search");
	   	panelTop.add(searchButton);
	   	searchButton.addActionListener(new ActionListener() {
	       	@Override
	       	public void actionPerformed(ActionEvent e) {
	       		// do something, trigger search operation
	       	}
	   	});
	   	
	   	
	   	JButton showAll = new JButton("Show All Markers");
	   	showAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });
        panelTop.add(showAll);
	}
	
	public void addText(String s) throws BadLocationException{
		StyledDocument doc = textPane.getStyledDocument();
		doc.insertString(0, s, null);
	}

	public void addMarkers(MapMarkerDot marker){
		map().addMapMarker(marker);
	}
	
	public void removeMarkers(){
		map().removeAllMapMarkers();
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
	@Override
	public void processCommand(JMVCommandEvent arg0) {
		
	}
	
	
	
}

