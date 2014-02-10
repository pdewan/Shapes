package shapes;
//import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Stroke;

import util.misc.Common;
import util.models.AListenable;
import util.models.Listenable;

import java.awt.*;
import java.awt.geom.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public  class ShapeModel extends AListenable
      implements RemoteShape, java.io.Serializable, Cloneable, Comparable<RemoteShape>, FlexibleShape
{
	final static float dash1[] = {10.0f};
	boolean disposed;
	Point end;

	public final static int DEFAULT_X = 0;
	public final static int DEFAULT_Y = 0;
	public final static int DEFAULT_WIDTH = 20;
	public final static int DEFAULT_HEIGHT = 30;
	int zIndex;
	String zIndexToString = "0";
    protected Rectangle bounds;
    boolean threeD;
    boolean raised = true;
    boolean rounded;
    transient Stroke basicStroke;
    Paint gradientPaint;
    transient BasicStroke dashed;
//  = new BasicStroke(1.0f, 
//          BasicStroke.CAP_BUTT, 
//          BasicStroke.JOIN_MITER, 
//          10.0f, dash1, 0.0f);
  transient BasicStroke dotted;
//  = new BasicStroke(
//  	      1f, 
//  	      BasicStroke.CAP_ROUND, 
//  	      BasicStroke.JOIN_ROUND, 
//  	      1f, 
//  	      new float[] {2f}, 
//  	      0f);
  transient BasicStroke solid;
  Rectangle initBounds;
    
    public ShapeModel (Rectangle theBounds)
    {
        super();
        //bounds = theBounds;
        init(theBounds);
//		bounds = new Rectangle(theBounds.x, theBounds.y, theBounds.width, theBounds.height);
    }
    protected void init (Rectangle theBounds) {
 	   bounds = theBounds;
 	   initBounds = new Rectangle(theBounds);
 			try {
 				 dashed = new BasicStroke(1.0f, 
 				            BasicStroke.CAP_BUTT, 
 				            BasicStroke.JOIN_MITER, 
 				            10.0f, dash1, 0.0f);
 				   dotted = new BasicStroke(
 				    	      1f, 
 				    	      BasicStroke.CAP_ROUND, 
 				    	      BasicStroke.JOIN_ROUND, 
 				    	      1f, 
 				    	      new float[] {2f}, 
 				    	      0f);
 				     solid = new BasicStroke(1f);
 			setStroke(solid);
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		
     }
	public ShapeModel (int x, int y, int width, int height)
    {
        super();
//        bounds = new Rectangle(x, y, width, height);
        init (new Rectangle(x, y, width, height));
    }
	public ShapeModel () 
    {
		this(new Rectangle(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }	
	
    public Rectangle getBounds()
    {
        return (bounds);
    }
    public void setBounds(int x, int y, int width, int height)
    {
        //System.out.println("Set Bounds called " + x + " " + y + " " + width + " " + height);
		bounds.setBounds(x, y, width, height);
        //this.setChanged();
     	//this.notifyObservers();
     	this.notifyListeners();
    }
	
	public void setBounds(Rectangle r)
    {
        //System.out.println("Set Bounds called " + x + " " + y + " " + width + " " + height);
		setBounds(r.x, r.y, r.width, r.height);
		if (initBounds == null)
			initBounds = new Rectangle(r);
        //this.setChanged();
     	//this.notifyObservers();
     	//this.notifyListeners();
    }
	public void setBounds(Point northWest, Point southEast)
    {
        
		setBounds(northWest.x, northWest.y, southEast.x - northWest.x, southEast.y - northWest.y);
        //this.setChanged();
     	//this.notifyObservers();
     	//this.notifyListeners();
    }
	public void setSize(Dimension d)
    {
		bounds.setSize(d);
        //this.setChanged();
     	//this.notifyObservers();
     	this.notifyListeners();
    }
	public void setSize(int width, int height)
    {		
		bounds.setSize(new Dimension(width, height));
        //this.setChanged();
     	//this.notifyObservers();
     	this.notifyListeners();
    }
	public void setWidth(int width)
    {
		if (bounds.getWidth() == width)
			return;
		bounds.setSize(new Dimension(width, getBounds().height));
        //this.setChanged();
     	//this.notifyObservers();
     	this.notifyListeners();
    }
	public void setHeight(int height)
    {
		if (bounds.getHeight() == height)
			return;
		bounds.setSize(new Dimension(getBounds().width, height));
        //this.setChanged();
     	//this.notifyObservers();
     	this.notifyListeners();
    }
	public Dimension getSize()
    {
		return bounds.getSize();
        //this.setChanged();
     	//this.notifyObservers();
     	//this.notifyListeners();
    }
	public int getX() {
		return bounds.x;
	}
	public int getY() {
		return bounds.y;
	}
	public void setX(int newVal) {
		if (bounds.x == newVal)
			return;
		bounds.x = newVal;
		this.notifyListeners();
	}
	public void setY(int newVal) {
		if (bounds.y == newVal)
			return;
		bounds.y = newVal;
		this.notifyListeners();
	}
	public void setPosition(Point p) {
		bounds.x = p.x;
		bounds.y = p.y;
		this.notifyListeners();
	}
	public Point getPosition() {
		return new Point(bounds.x, bounds.y);
	}
	public int getHeight() {
		return getBounds().height;
	}
	public int getWidth() {
		return getBounds().width;
	}
	public Point getNWCorner()
    {
		return new Point(bounds.x, bounds.y);
    }
	public void setNWCorner(int x, int y)
    {
		bounds.x = x;
		bounds.y = y;
		this.notifyListeners();
    }
	public void setNWCorner(Point p)
    {
		setNWCorner(p.x, p.y);
    }
	public Point getCenter()
    {
		return centerFromRectangle(bounds);
    }
	public int getCenterX() {
		return getX() + getWidth()/2;
	}
	public void setCenterX(int newVal) {
		setX(newVal - getWidth()/2);
	}
	public int getCenterY() {
		return getY() + getHeight()/2;
	}
	public void setCenterY(int newVal) {
		setY(newVal - getHeight()/2);
	}
	public void setCenter(Point center)
    {
		this.setBounds(rectangleFromCenter(center, bounds.width, bounds.height));
    }
	public void setCenter(int x, int y ) 
    {
		setCenter(new Point(x,y));
    }
	public static Point centerFromRectangle (Rectangle r) {
		return new Point(r.x + r.width/2, r.y + r.height/2);
	}
	public static Rectangle rectangleFromCenter(Point center, int width, int height) {
		return new Rectangle(center.x - width/2, center.y - height/2, width, height);
		
	}
	
	public Point getNECorner()
    {
		return nECornerFromRectangle(bounds);
    }
	public void setNECorner(Point p)
    {
		this.setBounds(rectangleFromNECorner(p, bounds.width, bounds.height));
    }
	public void setNECorner(int x, int y ) 
    {
		setNECorner(new Point(x,y));
    }
	public static Point nECornerFromRectangle (Rectangle r) {
		return new Point(r.x + r.width, r.y);
	}
	public static Rectangle rectangleFromNECorner(Point p, int width, int height) {
		return new Rectangle(p.x - width, p.y, width, height);
		
	}
	public Point getSWCorner()
    {
		return sWCornerFromRectangle(bounds);
    }
	public void setSWCorner(Point p)
    {
		this.setBounds(rectangleFromSWCorner(p, bounds.width, bounds.height));
    }
	public void setSWCorner(int x, int y ) 
    {
		setSWCorner(new Point(x,y));
    }
	public static Point sWCornerFromRectangle (Rectangle r) {
		return new Point(r.x, r.y + r.height);
	}
	public static Rectangle rectangleFromSWCorner(Point p, int width, int height) {
		return new Rectangle(p.x, p.y - height, width, height);
		
	}
	public Point getSECorner()
    {
		return sECornerFromRectangle(bounds);
    }
	public void setSECorner(Point p)
    {
		this.setBounds(rectangleFromSECorner(p, bounds.width, bounds.height));
    }
	public void setSECorner(int x, int y ) 
    {
		setSECorner(new Point(x,y));
    }
	public static Point sECornerFromRectangle (Rectangle r) {
		return new Point(r.x + r.width, r.y + r.height);
	}
	public static Rectangle rectangleFromSECorner(Point p, int width, int height) {
		return new Rectangle(p.x - width, p.y - height, width, height);
		
	}
	public Point getNorthEnd()
    {
		return northEndFromRectangle(bounds);
    }
	public void setNorthEnd(Point p)
    {
		this.setBounds(rectangleFromNorthEnd(p, bounds.width, bounds.height));
    }
	public void setNorthEnd(int x, int y ) 
    {
		setSECorner(new Point(x,y));
    }
	public static Point northEndFromRectangle (Rectangle r) {
		return new Point(r.x + r.width/2, r.y);
	}
	public static Rectangle rectangleFromNorthEnd(Point p, int width, int height) {
		return new Rectangle(p.x - width/2, p.y, width, height);
		
	}
	public Point getSouthEnd()
    {
		return southEndFromRectangle(bounds);
    }
	public void setSouthEnd(Point p)
    {
		this.setBounds(rectangleFromSouthEnd(p, bounds.width, bounds.height));
    }
	public void setSouthEnd(int x, int y ) 
    {
		setSouthEnd(new Point(x,y));
    }
	public static Point southEndFromRectangle (Rectangle r) {
		return new Point(r.x + r.width/2, r.y + r.height);
	}
	public static Rectangle rectangleFromSouthEnd(Point p, int width, int height) {
		return new Rectangle(p.x + width/2, p.y - height, width, height);
		
	}
	public Point getWestEnd()
    {
		return westEndFromRectangle(bounds);
    }
	public void setWestEnd(Point p)
    {
		this.setBounds(rectangleFromWestEnd(p, bounds.width, bounds.height));
    }
	public void setWestEnd(int x, int y ) 
    {
		setWestEnd(new Point(x,y));
    }
	public static Point westEndFromRectangle (Rectangle r) {
		return new Point(r.x, r.y + r.height/2);
	}
	public static Rectangle rectangleFromWestEnd(Point p, int width, int height) {
		return new Rectangle(p.x , p.y - height/2, width, height);
		
	}
	public Point getEastEnd()
    {
		return eastEndFromRectangle(bounds);
    }
	public void setEastEnd(Point p)
    {
		this.setBounds(rectangleFromEastEnd(p, bounds.width, bounds.height));
    }
	public void setEastEnd(int x, int y ) 
    {
		setEastEnd(new Point(x,y));
    }
	public static Point eastEndFromRectangle (Rectangle r) {
		return new Point(r.x + r.width, r.y + r.height/2);
	}
	public static Rectangle rectangleFromEastEnd(Point p, int width, int height) {
		return new Rectangle(p.x - width , p.y - height/2, width, height);
		
	}
	Color color;
	public Color getColor() {
		return color;
	}
	public void  setColor(Color newVal) {
		color = newVal;
		this.notifyListeners();
	}	
	boolean isFilled = false;
	public boolean isFilled() {
		return isFilled;
	}
	public void  setFilled(boolean newVal) {
		isFilled = newVal;
		this.notifyListeners();
	}
	
	
	boolean is3DRecangle = false;
	public boolean is3DRecangle() {
		return is3DRecangle;
	}
	public void  set3DRecangle(boolean newVal) {
		is3DRecangle = newVal;
	}
	
	int arcWidth, arcHeight;
	
	
	Font font;
	public Font getFont() {
		if (font == null)
			return ShapesAPI.getDefaultFont();
		return font;
	}
	public void  setFont(Font newVal) {
		font = newVal;
		this.notifyListeners();
	}	
	
	int fontSize;
	
	public void  setFontSize(int newVal) {
		fontSize = newVal;
		this.notifyListeners();
	}	
	
	public int getFontSize() {
		if (fontSize == 0)
			return ShapesAPI.getDefaultFontSize();
		return fontSize;
	}
	
    public Object clone()
    {
        ShapeModel clone = null;
        try
        {
            clone = (ShapeModel) super.clone();
            Rectangle bounds = this.getBounds();
            clone.bounds = new Rectangle(bounds);
			//clone.setBounds(new Rectangle(bounds));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return (clone);
        }
    }
    
    public boolean copy(BoundedShape aReference)
    {
//    	if (aReference.getClass() != this.getClass()) {
//    		return false;
//    	}
    	if (!copyable(aReference)) return false;
    	try {
    	bounds = new Rectangle (aReference.getBounds());
    	if (aReference instanceof AttributedShape) {
    		AttributedShape anAttributedReference = (AttributedShape) aReference;
    	isFilled = anAttributedReference.isFilled();
    	font = anAttributedReference.getFont();
    	basicStroke = anAttributedReference.getStroke();
//    	raised = anAttributedReference.isRaised();
//    	rounded = anAttributedReference.isRounded();
    	gradientPaint = anAttributedReference.getPaint();
//    	threeD = anAttributedReference.is3DRecangle();
    	color = anAttributedReference.getColor();
    	}
    	} catch (Exception e) {
    		return false;
    		
    	}
    	this.notifyListeners();
    	return true;
    }
    
    public boolean equals(ShapeModel aReference) {
    	try {
    	return bounds.equals(aReference.getBounds()) &&
    	isFilled == aReference.isFilled() &&
    	font == aReference.getFont() &&
    	Common.equal(basicStroke, aReference.getStroke()) &&
    	raised == aReference.isRaised() &&
    	rounded == aReference.isRounded() &&
    	Common.equal (gradientPaint, aReference.getPaint()) &&
    	threeD == aReference.is3DRecangle &&
    	Common.equal(color, aReference.getColor());
    	} catch (Exception e) {
    		return false;
    	}
    }
    public Object remoteClone()
    {
        return clone();
    }
     private void writeObject(java.io.ObjectOutputStream out)
        throws java.io.IOException
    {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, ClassNotFoundException
    {
        in.defaultReadObject();
    }
    public boolean contains(double x, double y) {
    	return getBounds().contains(x, y);
		}
    public boolean contains(double x, double y, double w, double h) { 
    //Tests if the interior of the Shape entirely contains the specified rectangular area.
	return getBounds().contains (x, y, w, h);
}
public boolean contains(Point2D p) {
    //Tests if a specified Point2D is inside the boundary of the Shape. 
	return getBounds().contains(p);
	//return false;
}
public boolean contains(Rectangle2D r) {
    //Tests if the interior of the Shape entirely contains the specified Rectangle2D. 
	return getBounds().contains(r);
}

public Rectangle2D getBounds2D() { 
    //Returns a high precision and more accurate bounding box of the Shape than the getBounds method. 
	return getBounds().getBounds2D();
}
public PathIterator getPathIterator(AffineTransform at) {
    //Returns an iterator object that iterates along the Shape boundary and provides access to the geometry of the Shape outline. 
	return getBounds().getPathIterator(at);
}
 public PathIterator getPathIterator(AffineTransform at, double flatness) {
    //Returns an iterator object that iterates along the Shape boundary and provides access to a flattened view of the Shape outline geometry. 
	 return getBounds().getPathIterator(at, flatness);
}
public boolean intersects(double x, double y, double w, double h) {
    //Tests if the interior of the Shape intersects the interior of a specified rectangular area. 
	return getBounds().intersects(x,y,w,h);
}
public boolean intersects(Rectangle2D r) {
	return getBounds().intersects(r);
}
public int getZIndex()  {
	return zIndex;
}
public void setZIndex(int newValue) {
	zIndex = newValue;
	zIndexToString = "" + newValue;
	this.notifyListeners(true);
}
public String toString() {
//	return zIndexToString;
	return 
			"ZIndex:" + zIndex +
			" X: " + getX() + 
			" Y:" + getY() 
//			+ 
//			" Width:" + getWidth() + 
//			" Height:" + getHeight()
			;
			
}

public String getDebugToolTipText() {
	return 	"X: " + getX() + 
			" Y:" + getY() + 
			" Width:" + getWidth() + 
			" Height:" + getHeight() +
			" ZIndex:" + zIndex;	
	
}
//public String toString() {
//	return 	"X: " + getX() + 
//			" Y:" + getY() + 
//			" Width:" + getWidth() + 
//			" Height:" + getHeight() +
//			" ZIndex:" + zIndex;	
//}
//public static int compare(RemoteShape s1, RemoteShape s2) {
//	try {
//	if (s1.getZIndex() < s2.getZIndex())
//		return - 1;
//	else if (s1.getZIndex() == s2.getZIndex())
//		return 0;
//	else
//		return 1;
//	} catch (Exception e) {
//		return 0;
//	}
//	
//}
// ideas is to make 3 > 21, that is make the 3rd element > the first child of second element
public static int compare(RemoteShape s1, RemoteShape s2) {
	try {
//		// takes care of the normal situation in which zindices are not specified
//		if (s1.getZIndex() == s2.getZIndex()) return 0;
//		String s1ZIndex = "" + s1.getZIndex();
//		String  s2ZIndex =  "" + s2.getZIndex();
		
		//return s1ZIndex.compareTo(s2ZIndex);
		return s1.toString().compareTo(s2.toString());
	
	} catch (Exception e) {
		return 0;
	}	
}

public int compareTo(RemoteShape s2) {
	return compare(this, s2);
}
@Override
public boolean is3D()  {
	return threeD;
}
@Override
public void set3D(boolean threeD)  {
	this.threeD = threeD;
	this.notifyListeners();
}
@Override
public boolean isRaised()  {
	return raised;
}
@Override
public void setRaised(boolean raised)  {
	this.raised = raised;
}

@Override
public boolean isRounded()  {
	return rounded;
}
@Override
public void setRounded(boolean rounded)  {
	this.rounded = rounded;
}

@Override
public Stroke getStroke()  {
	return basicStroke;
}
@Override
public void setStroke(Stroke basicStroke)  {
	this.basicStroke = basicStroke;
}
@Override
public Paint getPaint()  {
	return gradientPaint;
}
@Override
public void setPaint(Paint gradientpaint)  {
	this.gradientPaint = gradientpaint;
}
@Override
public int getArcWidth() {
	return arcWidth;
}
@Override
public void setArcWidth(int arcWidth) {
	this.arcWidth = arcWidth;
}
@Override
public int getArcHeight() {
	return arcHeight;
}
@Override
public void setArcHeight(int arcHeight) {
	this.arcHeight = arcHeight;
}

public double getOriginRadius() {
	return Math.sqrt(getX()*getX() + getY()*getY());
	
}
public double getOriginAngle() {
	return Math.atan2(getY(), getX());
}

public double getDiagonalLength() {
	return Math.sqrt(getWidth()*getWidth() + getHeight()*getHeight());
	
}
public double getDiagonalAngle() {
	return  Math.atan2(getHeight(), getWidth());
}
public void moveX(int increment) {
	int oldX = getX();
	setX(oldX + increment);
}
public void moveY(int increment) {
	int oldY = getY();
	setY(oldY + increment);
}

public void setDashedStroke() {
	setStroke(dashed);
}

public void setDottedStroke() {
	setStroke(dotted);
}
public void setSolidStroke() {
	setStroke(solid);
}
public double getAngle() {
	return Math.atan2((double)bounds.y, (double) bounds.x);
}

public void setAngle(double newAngle) {
	double currentRadius = getRadius();
//	System.out.println("The Radius at start of setAngle:" + currentRadius);
	set(currentRadius, newAngle);
	currentRadius = getRadius();
//	System.out.println("The radius ar end of set Angle:" + currentRadius);
	/*
	x = (int) (currentRadius*Math.cos(newAngle));
	y = (int) (currentRadius*Math.sin(newAngle));
	*/
}

public double getRadius() {
//	return  Math.sqrt(bounds.x*bounds.x + bounds.y*bounds.y);
	return  Math.sqrt(bounds.width*bounds.width + bounds.height*bounds.height);

}

public void setRadius(double newRadius) {
	double currentAngle = getAngle();
	set(newRadius, currentAngle);
	/*
	x = (int) (newRadius*Math.cos(currentAngle));
	y = (int) (newRadius*Math.sin(currentAngle));
	*/
}
void set (double theRadius, double theAngle) {
//	bounds.x = (int) (theRadius*Math.cos(theAngle));
//	bounds.y = (int) (theRadius*Math.sin(theAngle));
	bounds.width = (int) (theRadius*Math.cos(theAngle));
	bounds.height = (int) (theRadius*Math.sin(theAngle));
	this.notifyListeners();
	
}
double magnification = 1;


@Override
public double getMagnification() {
	return magnification;
}

@Override
public void setMagnification(double newVal) {
	magnification = newVal;
	Dimension newSize = new Dimension ( (int) (initBounds.width * magnification),  (int) (initBounds.height*magnification));
	setSize(newSize);
}
//public void copy (ShapeModel aReference) {
//	
//	copy(((ShapeModel) aReference));
//}
@Override
public void setDisposed(boolean newVal) {
		disposed = newVal;
	
}
@Override
public boolean getDisposed() {
	// TODO Auto-generated method stub
	return false;
}
public void joinStartToEndOf (BoundedShape shape) {
	setX(shape.getX() + shape.getWidth());
	setY(shape.getY() + shape.getHeight());
}

public void joinStartToStartOf (BoundedShape shape) {
	setX(shape.getX());
	setY(shape.getY());
}

public void joinEndToEndOf (BoundedShape shape) {
	setWidth(shape.getX() + shape.getWidth() - getX());
	setHeight(shape.getY() + shape.getHeight() - getY());
}

public void joinEndToStartOf (BoundedShape shape) {
	setWidth(shape.getX() - getX());
	setHeight(shape.getY() - getY());
}
@Override
public boolean copyable(BoundedShape aReference) {
	// TODO Auto-generated method stub
	return aReference.getClass() == this.getClass();
}




}
