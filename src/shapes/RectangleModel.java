package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class RectangleModel extends BoundedShapeModel implements RemoteRectangle
{
	
    public RectangleModel (Rectangle theBounds)throws RemoteException 
    {
        super(theBounds);
    }
	public RectangleModel (int x, int y, int width, int height)throws RemoteException 
    {
        super(x, y, width, height);
    }
	public RectangleModel ()throws RemoteException 
    {
        super();
    }
	
}
