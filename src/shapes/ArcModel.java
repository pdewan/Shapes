package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class ArcModel extends ShapeModel implements RemoteArc
{
	int startAngle, endAngle;
    public ArcModel (Rectangle theBounds, int theStartAngle, int theEndAngle)throws RemoteException 
    {
        super(theBounds);
        startAngle = theStartAngle;
        endAngle = theEndAngle;
    }
    public ArcModel (int theStartAngle, int theEndAngle)throws RemoteException 
    {
        
        startAngle = theStartAngle;
        endAngle = theEndAngle;
    }
	public ArcModel (int x, int y, int width, int height, int theStartAngle, int theEndAngle )throws RemoteException 
    {
        super(x, y, width, height);
        startAngle = theStartAngle;
        endAngle = theEndAngle;
    }
	public ArcModel ()throws RemoteException 
    {
        super();
    }
	@Override
	public int getEndAngle() throws RemoteException {
		// TODO Auto-generated method stub
		return endAngle;
	}
	@Override
	public int getStartAngle() throws RemoteException {
		// TODO Auto-generated method stub
		return startAngle;
	}
	@Override
	public void setEndAngle(int newVal) throws RemoteException {
		endAngle = newVal;
		this.notifyListeners();
		
	}
	@Override
	public void setStartAngle(int newVal) throws RemoteException {
		startAngle = newVal;
		this.notifyListeners();
		
	}
	

}
