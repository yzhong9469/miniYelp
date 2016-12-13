package edu.upenn.cit594.miniYelp;

//License: GPL. For details, see Readme.txt file.

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
* A simple implementation of the {@link MapMarker} interface. Each map marker
* is painted as a circle with a black border line and filled with a specified
* color.
*
* @author Jan Peter Stotz
*
*/
public class MyMapMarker implements MapMarker, ImageObserver {

	private Coordinate coord;
	private double radius;
	private STYLE markerStyle;
	private Layer layer;
    private String name;
    private Style style;
    private Boolean visible;

	public MyMapMarker(String name, Coordinate coord){
		this(null, name, coord, 5, STYLE.FIXED, getDefaultStyle());
	}
    
	public MyMapMarker(Layer layer, String name, Coordinate coord, double radius, STYLE markerStyle, Style style) {
	     this.layer = layer;
	     this.name = name;
	     this.style = style;
	     this.markerStyle = markerStyle;
	     this.coord = coord;
	     this.radius = radius;
	 }

	 @Override
	 public Coordinate getCoordinate() {
	     return coord;
	 }
	
	 @Override
	 public double getLat() {
	     return coord.getLat();
	 }

	 @Override
	 public double getLon() {
	     return coord.getLon();
	 }
	
	 @Override
	 public double getRadius() {
	     return radius;
	 }
	
	 @Override
	 public STYLE getMarkerStyle() {
	     return markerStyle;
	 }

	 @Override
	 public void paint(Graphics g, Point position, int radius) {
	     int ss = 50;
	     int s2 = 25;
	
	     BufferedImage marker;
	     try {
	    	 marker = ImageIO.read(new File("map.png"));
	         g.drawImage(marker, position.x - s2, position.y - ss, ss, ss, this);
	     } catch (IOException e) {
	     }
	     
	     if (getLayer() == null || getLayer().isVisibleTexts()) {
	    	 paintText(g, new Point(position.x - 20, position.y - 30));
	     }
	 }

	 public static Style getDefaultStyle() {
	     return new Style(Color.ORANGE, new Color(200, 200, 200, 200), null, getDefaultFont());
	 }
	
	 @Override
	 public String toString() {
	     return "MapMarker at " + getLat() + ' ' + getLon();
	 }
	
	 @Override
	 public void setLat(double lat) {
	     if (coord == null) coord = new Coordinate(lat, 0);
	     else coord.setLat(lat);
	 }
	
	 @Override
	 public void setLon(double lon) {
	     if (coord == null) coord = new Coordinate(0, lon);
	     else coord.setLon(lon);
	 }

	 @Override
	 public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
	     // TODO Auto-generated method stub
	     return false;
	 }
	 public static Font getDefaultFont() {
	     Font f = UIManager.getDefaults().getFont("TextField.font");
	     if (f == null) {
	         f = Font.decode(null);
	     }
	     return new Font(f.getName(), Font.BOLD, f.getSize());
	 }
	
	 public void paintText(Graphics g, Point position) {
	     if (name != null && g != null && position != null) {
	         g.setColor(Color.DARK_GRAY);
	         Font f = getDefaultFont();
	         g.setFont(new Font(f.getName(), Font.BOLD, f.getSize()-1));
	         g.drawString(name, position.x+MapMarkerDot.DOT_RADIUS+2, position.y+MapMarkerDot.DOT_RADIUS);
	     }
	 }

	 public Layer getLayer() {
	        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Style getStyle() {
        return style;
    }

    public Style getStyleAssigned() {
        return style == null ? (layer == null ? null : layer.getStyle()) : style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Color getColor() {
        Style styleAssigned = getStyleAssigned();
        return styleAssigned == null ? null : getStyleAssigned().getColor();
    }

    public void setColor(Color color) {
        if (style == null && color != null) style = new Style();
        if (style != null) style.setColor(color);
    }

    public Color getBackColor() {
        Style styleAssigned = getStyleAssigned();
        return styleAssigned == null ? null : getStyleAssigned().getBackColor();
    }

    public void setBackColor(Color backColor) {
        if (style == null && backColor != null) style = new Style();
        if (style != null) style.setBackColor(backColor);
    }

    public Stroke getStroke() {
        Style styleAssigned = getStyleAssigned();
        return styleAssigned == null ? null : getStyleAssigned().getStroke();
    }

    public void setStroke(Stroke stroke) {
        if (style == null && stroke != null) style = new Style();
        if (style != null) style.setStroke(stroke);
    }

    public Font getFont() {
        Style styleAssigned = getStyleAssigned();
        return styleAssigned == null ? null : getStyleAssigned().getFont();
    }

    public void setFont(Font font) {
        if (style == null && font != null) style = new Style();
        if (style != null) style.setFont(font);
    }

    private boolean isVisibleLayer() {
        return layer == null || layer.isVisible() == null ? true : layer.isVisible();
    }

    public boolean isVisible() {
        return visible == null ? isVisibleLayer() : visible.booleanValue();
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String txt) {
        this.name = txt;
    }
}
