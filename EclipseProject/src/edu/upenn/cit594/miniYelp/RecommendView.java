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
public class RecommendView extends JPanel implements JMapViewerEventListener {

	private static final long serialVersionUID = 1L;
	
	private final JMapViewerTree treeMap;
	
	JButton returnButton = new JButton("Return");
	
	private JTextPane textPane = new JTextPane();
	/*
	* Constructs the {@code Demo}.
	*/
	public RecommendView() throws BadLocationException {
		setSize(1210, 740);
	
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
		
        addButtons(panelTop, panelBottom);
	   	
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
	
	private void addButtons(JPanel panelTop, JPanel panelBottom){
	   	
	   	/*
		 * trigger search operation
		 */
	   	
	   	JButton showAll = new JButton("Show All Markers");
	   	showAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });
        panelBottom.add(showAll);
        
        panelBottom.add(returnButton);
	}
	
	public void addText(String s) throws BadLocationException{
		StyledDocument doc = textPane.getStyledDocument();
		doc.insertString(0, s, null);
	}

	public void addMarkers(MapMarkerDot marker){
		map().addMapMarker(marker);
	}
	
	public void addMarkers(List<Restaurant> restaurants){
		removeMarkers();
		int i = 1;
		for (Restaurant r : restaurants){
			MapMarkerDot m = new MapMarkerDot(i + "", 
					new Coordinate(r.getLatitude(), r.getLongitude()));
			map().addMapMarker(m);
			i++;
		}
	}
	
	public void addDescription(List<Restaurant> restaurants) throws BadLocationException{
		StyledDocument doc = textPane.getStyledDocument();
		doc.remove(0, doc.getLength());
		int i = 1;
		for (Restaurant r : restaurants){
			doc.insertString(doc.getLength(), r.toString(i) + "\n", null);
			i++;
		}
	}
	
	public void addReturnListener(ActionListener search){
	   	returnButton.addActionListener(search);
	}
	
	public void removeMarkers(){
		map().removeAllMapMarkers();
	}
	
	@Override
	public void processCommand(JMVCommandEvent arg0) {
		
	}

}

