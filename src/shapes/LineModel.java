package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class LineModel extends BoundedShapeModel implements RemoteLine, FlexibleLineShape
{
	FlexibleLocatable lineStart = new PointModel(0, 0);
	FlexibleLocatable lineEnd = new PointModel(0, 0);	
	double initRadius;
	double initAngle;

    public LineModel (Rectangle theBounds)throws RemoteException 
    {
        super(theBounds);
        boundsChanged();
//        lineStart.setX (bounds.x);
//        lineStart.setY (bounds.y);
//        lineEnd.setX(bounds.width);
//        lineEnd.setY(bounds.height);
       
    }
	public LineModel (int x, int y, int width, int height)throws RemoteException 
    {
        super(x, y, width, height);
        boundsChanged();
    }
	public LineModel ()throws RemoteException 
    {
        super();
        
        
    }
	
	public LineModel(int x, int y, double length, double angle) {
		super ();
		lineEnd.setRadius(length);
		lineEnd.setAngle(angle);
		init (new Rectangle (x, y, lineEnd.getX(), lineEnd.getY()) );
	}
	
	void boundsChanged() {
		 lineStart.setX (bounds.x);
	        lineStart.setY (bounds.y);
	        lineEnd.setX(bounds.width);
	        lineEnd.setY(bounds.height);
	}
	public void setX(int newVal) {
		lineStart.setX(newVal);
		super.setX(newVal);
	}
	
	public void setY(int newVal) {
		lineStart.setY(newVal);
		super.setY(newVal);

	}
	@Override
	public void rotate(int units) {
		rotate(units * ROTATION_UNIT);
	}

	@Override
	public void rotate(double angle) {
		lineEnd.setAngle(lineEnd.getAngle() + angle);
		lineEndChanged();
//		super.setWidth(lineEnd.getX());
//		super.setHeight(lineEnd.getY());
	}

	@Override
	public double getLength() {
		return lineEnd.getRadius();
	}

	@Override
	public double getRelativeAngle() {
		return lineEnd.getAngle();
	}

	@Override
	public void setLength(double theLength) {
		lineEnd.setRadius(theLength);
		lineEndChanged();
	}
	
	void lineEndChanged() {
		super.setWidth(lineEnd.getX());
		super.setHeight(lineEnd.getY());
	}

	@Override
	public void setRelativeAngle(double theAngle) {
		lineEnd.setAngle(theAngle);
		lineEndChanged();
//		super.setWidth(getWidth());
//		super.setHeight(getHeight());
	}
	
//	public int getWidth() {
//		return lineEnd.getX();
//	}
//	
//	public int getHeight() {
//		return lineEnd.getY();
//	}
	
	public void setWidth(int newVal) {
		super.setWidth(newVal);
		 lineEnd.setX(newVal);
	}
	
	public void setHeight(int newVal) {
		super.setHeight(newVal);
		 lineEnd.setY(newVal);
	}
	

}
