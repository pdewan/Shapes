package shapes;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.rmi.RemoteException;
public class AWTShapeModel extends BoundedShapeModel implements RemoteAWTShape
{
	Shape awtShape;
  

	public AWTShapeModel (Shape theShape)throws RemoteException 
    {
    	
        super(theShape.getBounds());
        awtShape = theShape;
    }
	
	public AWTShapeModel ()throws RemoteException 
    {
        super();
    }
	
	  public Shape getShape() {
			return awtShape;
		}

		public void setShape(Shape shape) {
			this.awtShape = shape;
			notifyListeners();
		}
//		public void setWidth(int width)
//	    {
//			if (bounds.getWidth() == width)
//				return;
//			super.setWidth(width);
//			
//	    }
//		public void setHeight(int height)
//	    {
//			if (bounds.getHeight() == height)
//				return;
//			super.setHeight(height);
////			bounds.setSize(new Dimension(getBounds().width, height));
//	        //this.setChanged();
//	     	//this.notifyObservers();
////	     	this.notifyListeners();
//	    }

}
