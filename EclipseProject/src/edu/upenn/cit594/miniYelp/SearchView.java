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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class SearchView extends JFrame implements JMapViewerEventListener {

	private static final long serialVersionUID = 1L;
	
	private final JMapViewerTree treeMap;
	private JLabel category = new JLabel("Category:");
	private JLabel price = new JLabel("Price:");
	private JLabel rating = new JLabel("Rating:");
	private JLabel review = new JLabel("Review Count:");
	/**
	* Constructs the {@code Demo}.
	*/
	public SearchView() {
		super("Search");
		setSize(800, 500);
	
		treeMap = new JMapViewerTree("Zones");
	
		// Listen to the map viewer for user operations so components will
		// receive events and update
		map().addJMVListener(this);
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
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
	//   
	//   JButton button = new JButton("setDisplayToFitMapMarkers");
	//   button.addActionListener(new ActionListener() {
	//       @Override
	//       public void actionPerformed(ActionEvent e) {
	//           map().setDisplayToFitMapMarkers();
	//       }
	//   });
	   
		map().setTileSource((TileSource) new OsmTileSource.Mapnik());
		
		/*
		 * the first filter choice
		 */
		panelTop.add(category);
		JComboBox<String> categorySelector = new JComboBox<>(new String[] {"","Chinese","American","Food Truck"});
		categorySelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
	           //map().setTileSource((TileSource) e.getItem());
	      	 //do something, send back
				System.out.println(e.getItem());
	       }	
		});
		panelTop.add(categorySelector);
		
		/*
		 * the second filter choice
		 */
		panelTop.add(price);
		JComboBox<String> priceSelector = new JComboBox<>(new String[] {"","$","$$","$$$"});
		priceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
	           //map().setTileSource((TileSource) e.getItem());
	      	 //do something, send back
				System.out.println(e.getItem());
			}
		});
		panelTop.add(priceSelector);
		
		/*
		 * the third filter choice
		 */
		panelTop.add(rating);
	   	JComboBox<String> ratingSelector = new JComboBox<>(new String[] {"","5",">4",">3",
	  		 							">2",">1"});
	   	ratingSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	           //map().setTileSource((TileSource) e.getItem());
	      	 //do something, send back
	   			System.out.println(e.getItem());
	   		}
	   	});
	   	panelTop.add(ratingSelector);
	   
	   	/*
		 * the fourth filter choice
		 */
	   	panelTop.add(review);
	   	JComboBox<String> reviewSelector = new JComboBox<>(new String[] {"",">200",">100",">50"});
	   	reviewSelector.addItemListener(new ItemListener() {
	   		@Override
	   		public void itemStateChanged(ItemEvent e) {
	           //map().setTileSource((TileSource) e.getItem());
	      	 //do something, send back
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
	   	
	   	/*
	   	 * map settings
	   	 */
	   	map().setZoomContolsVisible(true);
	   	treeMap.setVisible(true);
	   	add(treeMap, BorderLayout.CENTER);
	   	
	   	/*
	   	 * how to add marker example
	   	 */
	   	MapMarkerDot testMarker = new MapMarkerDot("Van Pelt",new Coordinate(39.952676, -75.194000));
	   	map().addMapMarker(testMarker);
	   	map().setDisplayPosition(new Coordinate(39.952676, -75.194000), 16);
	
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
	}
	
	private JMapViewer map() {
		return treeMap.getViewer();
	}
	
	@Override
	public void processCommand(JMVCommandEvent command) {
		if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
	           command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
	       //updateZoomParameters();
		}
	}
}

