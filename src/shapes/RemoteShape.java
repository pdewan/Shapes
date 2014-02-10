package shapes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.awt.Point;
import util.models.AListenable;
import util.models.Listenable;

import java.awt.*;
import java.awt.geom.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RemoteShape extends Listenable
{
   
    public Rectangle getBounds() throws RemoteException;
    public void setBounds(int x, int y, int width, int height) throws RemoteException;
	
	public void setBounds(Rectangle r) throws RemoteException;
	public void setBounds(Point northWest, Point southEast)throws RemoteException;
	public void setSize(Dimension d)throws RemoteException;
	public void setSize(int width, int height)throws RemoteException;
	public void setWidth(int width)throws RemoteException;
	public void setHeight(int height)throws RemoteException;
	public Dimension getSize()throws RemoteException;
	public int getX() throws RemoteException;
	public int getY() throws RemoteException;
	public void setX(int newVal) throws RemoteException;
	public void setY(int newVal) throws RemoteException;
	public void setPosition(Point p) throws RemoteException;
	public Point getPosition() throws RemoteException;
	public int getHeight() throws RemoteException;
	public int getWidth() throws RemoteException;
	public Point getNWCorner()throws RemoteException;
	public void setNWCorner(int x, int y)throws RemoteException;
	public void setNWCorner(Point p) throws RemoteException;
	public Point getCenter()throws RemoteException;
	public void setCenter(Point center)throws RemoteException;
	public void setCenter(int x, int y ) throws RemoteException;	
	public Point getNECorner() throws RemoteException;
	public void setNECorner(Point p) throws RemoteException;
	public void setNECorner(int x, int y )throws RemoteException;
	
	public Point getSWCorner() throws RemoteException;
	public void setSWCorner(Point p)throws RemoteException;
	public void setSWCorner(int x, int y ) throws RemoteException;
	
	public Point getSECorner()throws RemoteException;
	public void setSECorner(Point p)throws RemoteException;
	public void setSECorner(int x, int y ) throws RemoteException;
	
	public Point getNorthEnd()throws RemoteException;
	public void setNorthEnd(Point p)throws RemoteException;
	public void setNorthEnd(int x, int y ) throws RemoteException;
	
	public Point getSouthEnd()throws RemoteException;
	public void setSouthEnd(Point p)throws RemoteException;
	public void setSouthEnd(int x, int y ) throws RemoteException;
	
	public Point getWestEnd()throws RemoteException;
	public void setWestEnd(Point p)throws RemoteException;
	public void setWestEnd(int x, int y ) throws RemoteException;
	
	public Point getEastEnd()throws RemoteException;
	public void setEastEnd(Point p)throws RemoteException;
	public void setEastEnd(int x, int y ) throws RemoteException;
	
	
	public Color getColor() throws RemoteException;
	public void  setColor(Color newVal) throws RemoteException;
	public boolean isFilled() throws RemoteException;
	public void  setFilled(boolean newVal) throws RemoteException;
	
	
	public Font getFont() throws RemoteException;
	public void  setFont(Font newVal) throws RemoteException;
	
	public int getFontSize() throws RemoteException;
	public void  setFontSize(int newVal) throws RemoteException;
	
    public Object remoteClone() throws RemoteException;
     
    public boolean contains(double x, double y) throws RemoteException;
    public boolean contains(double x, double y, double w, double h) throws RemoteException;
public boolean contains(Point2D p) throws RemoteException;
public boolean contains(Rectangle2D r) throws RemoteException;

public Rectangle2D getBounds2D() throws RemoteException;
public PathIterator getPathIterator(AffineTransform at) throws RemoteException;
 public PathIterator getPathIterator(AffineTransform at, double flatness) throws RemoteException;
public boolean intersects(double x, double y, double w, double h) throws RemoteException;
public boolean intersects(Rectangle2D r) throws RemoteException;
public int getZIndex() throws RemoteException;
public void setZIndex(int newValue) throws RemoteException ;
boolean is3D() throws RemoteException;
void setPaint(Paint gradientpaint) throws RemoteException;
Paint getPaint() throws RemoteException;
void setStroke(Stroke basicStroke) throws RemoteException;
Stroke getStroke() throws RemoteException;
void setRounded(boolean rounded) throws RemoteException;
boolean isRounded() throws RemoteException;
void setRaised(boolean raised) throws RemoteException;
boolean isRaised() throws RemoteException;
void set3D(boolean threeD) throws RemoteException;
int getArcWidth() throws RemoteException;
void setArcWidth(int arcWidth) throws RemoteException;
int getArcHeight()throws RemoteException;
void setArcHeight(int arcHeight)throws RemoteException;
public String getDebugToolTipText() throws RemoteException;
public double getDiagonalLength() throws RemoteException; 
public double getDiagonalAngle() throws RemoteException;
public void moveX(int increment) throws RemoteException;
public void moveY(int increment) throws RemoteException;

public void setDashedStroke() throws RemoteException;

public void setDottedStroke() throws RemoteException;
public void setSolidStroke() throws RemoteException;
public boolean copy (BoundedShape aReference) throws RemoteException;


}
