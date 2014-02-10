package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class CurveModel extends BoundedShapeModel implements RemoteCurve
{
	int controlX, controlY;
	Integer controlX2, controlY2;
    public CurveModel (Rectangle theBounds, int theControlX, int theControlY)throws RemoteException 
    {
        super(theBounds);
        controlX = theControlX;
        controlY = theControlY;
    }
    
    
    public CurveModel (int theControlX, int theControlY)throws RemoteException 
    {
        
        controlX = theControlX;
        controlY = theControlY;
    }
	public CurveModel (int x, int y, int width, int height, int theControlX, int theControlY )throws RemoteException 
    {
        super(x, y, width, height);
        controlX = theControlX;
        controlY = theControlY;
    }
	public CurveModel (int x, int y, int width, int height, 
			int theControlX, int theControlY, Integer theControlX2, Integer theControlY2 )throws RemoteException 
    {
        super(x, y, width, height);
        controlX = theControlX;
        controlY = theControlY;
        controlX2 = theControlX2;
        controlY2 = theControlY2;
    }
	public CurveModel ()throws RemoteException 
    {
        super();
    }
	@Override
	public int getControlY() throws RemoteException {
		// TODO Auto-generated method stub
		return controlY;
	}
	@Override
	public int getControlX() throws RemoteException {
		// TODO Auto-generated method stub
		return controlX;
	}
	@Override
	public void setControlY(int newVal) throws RemoteException {
		controlY = newVal;
		this.notifyListeners();
		
	}
	@Override
	public void setControlX(int newVal) throws RemoteException {
		controlX = newVal;
		this.notifyListeners();
		
	}
	
	public Integer getControlX2() throws RemoteException {
		return controlX2;
	}

	public void setControlX2(Integer controlX2) throws RemoteException {
		this.controlX2 = controlX2;
		this.notifyListeners();
	}

	public Integer getControlY2() throws RemoteException {
		return controlY2;
	}

	public void setControlY2(Integer controlY2) throws RemoteException {
		this.controlY2 = controlY2;
		this.notifyListeners();
	}
	

}
