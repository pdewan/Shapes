/*
 * Created on Jun 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package shapes;

import java.rmi.RemoteException;

/**
 * @author dewan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RemoteArc extends RemoteShape{
	public int getStartAngle() throws RemoteException;
	public int getEndAngle() throws RemoteException;
	public void setStartAngle(int newVal) throws RemoteException;
	public void setEndAngle(int newVal) throws RemoteException;

}
