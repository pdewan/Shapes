package shapes;
import java.awt.Rectangle;
import java.rmi.RemoteException;
public class PointModel extends OvalModel implements RemotePoint
{
	static int DIAMETER = 8;
	static int RADIUS = DIAMETER/2;
    
	public PointModel (int initX, int initY) 
    {
        super(initX, initY, DIAMETER, DIAMETER);
//		super (initX - RADIUS, initY- RADIUS, DIAMETER, DIAMETER);
		this.setFilled(true);	
    }	
}
