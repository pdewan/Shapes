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
public interface RemoteCurve extends RemoteShape{
	public int getControlX() throws RemoteException;
	public int getControlY() throws RemoteException;
	public void setControlX(int newVal) throws RemoteException;
	public void setControlY(int newVal) throws RemoteException;
	public Integer getControlX2() throws RemoteException ;

	public void setControlX2(Integer controlX2) throws RemoteException ;

	public Integer getControlY2() throws RemoteException ;

	public void setControlY2(Integer controlY2) throws RemoteException;

}
