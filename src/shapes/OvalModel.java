package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class OvalModel extends BoundedShapeModel implements RemoteOval
{
	
    public OvalModel (Rectangle theBounds)
    {    
        super(theBounds);
    }
	public OvalModel (int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }
	public OvalModel ()throws RemoteException 
    {
        super();
    }
	
}
