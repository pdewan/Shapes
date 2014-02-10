package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;

import util.models.PropertyListenerRegisterer;

public interface FlexibleShape extends AttributedShape, FlexibleLocatable, Magnifiable, Disposable {
	public Rectangle getBounds() ;
    public void setBounds(int x, int y, int width, int height) ;
	
	public void setBounds(Rectangle r) ;
	public void setBounds(Point northWest, Point southEast);
	public void setSize(Dimension d);
	public void setSize(int width, int height);
	public void setWidth(int width);
	public void setHeight(int height);
	public Dimension getSize();
	public int getX() ;
	public int getY() ;
	public void setX(int newVal) ;
	public void setY(int newVal) ;
	public void setPosition(Point p) ;
	public Point getPosition() ;
	public int getHeight() ;
	public int getWidth() ;
	public Point getNWCorner();
	public void setNWCorner(int x, int y);
	public void setNWCorner(Point p) ;
	public Point getCenter();
	public void setCenter(Point center);
	public void setCenter(int x, int y ) ;	
	public Point getNECorner() ;
	public void setNECorner(Point p) ;
	public void setNECorner(int x, int y );
	
	public Point getSWCorner() ;
	public void setSWCorner(Point p);
	public void setSWCorner(int x, int y ) ;
	
	public Point getSECorner();
	public void setSECorner(Point p);
	public void setSECorner(int x, int y ) ;
	
	public Point getNorthEnd();
	public void setNorthEnd(Point p);
	public void setNorthEnd(int x, int y ) ;
	
	public Point getSouthEnd();
	public void setSouthEnd(Point p);
	public void setSouthEnd(int x, int y ) ;
	
	public Point getWestEnd();
	public void setWestEnd(Point p);
	public void setWestEnd(int x, int y ) ;
	
	public Point getEastEnd();
	public void setEastEnd(Point p);
	public void setEastEnd(int x, int y ) ;
	
	
	public Color getColor() ;
	public void  setColor(Color newVal) ;
	public boolean isFilled() ;
	public void  setFilled(boolean newVal) ;
	
	public Font getFont() ;
	public void  setFont(Font newVal) ;
	public Stroke getStroke() ;
	public void  setStroke(Stroke newVal) ;
	public Paint getPaint() ;
	public void  setPaint(Paint newVal) ;
	public boolean isRounded() ;
	public void  setRounded(boolean newVal) ;
	public boolean is3D() ;
	public void  set3D(boolean newVal) ;
	
	
    public Object remoteClone() ;
     
    public boolean contains(double x, double y) ;
    public boolean contains(double x, double y, double w, double h) ;
public boolean contains(Point2D p) ;
public boolean contains(Rectangle2D r) ;

public Rectangle2D getBounds2D() ;
public PathIterator getPathIterator(AffineTransform at) ;
public PathIterator getPathIterator(AffineTransform at, double flatness) ;
public boolean intersects(double x, double y, double w, double h) ;
public boolean intersects(Rectangle2D r) ;

public int getZIndex() ;
public void setZIndex(int newValue) ;
void setDashedStroke();
void moveX(int increment);
void moveY(int increment);
void setDottedStroke();
void setSolidStroke();

void setFontSize(int newSize);
int getFontSize();
public int getCenterX();
public void setCenterX(int newVal);
public int getCenterY() ;
public void setCenterY(int newVal) ;
public void joinStartToEndOf (BoundedShape shape) ;

public void joinStartToStartOf (BoundedShape shape);

public void joinEndToEndOf (BoundedShape shape) ;

public void joinEndToStartOf (BoundedShape shape);
//void dispose();
//public double getAngle(); 
//public void setAngle(double newAngle); 
//public double getRadius(); 
//public void setRadius(double newRadius); 

//void copy (Object aReference);

//int getZIndex();
}
