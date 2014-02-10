package shapes;

import java.awt.Rectangle;
import java.rmi.RemoteException;

public class BoundedShapeModel extends ShapeModel {

	public BoundedShapeModel() {
		super();
	}
	public BoundedShapeModel (Rectangle theBounds)
    {
        super(theBounds);
       
    }   
	public BoundedShapeModel (int x, int y, int width, int height) 
    {
        super(x, y, width, height);
    }
	
	public String toString() {
		return super.toString() + 
				" Width:" + getWidth() + 
				" Height:" + getHeight()
				;
				
	}

}
