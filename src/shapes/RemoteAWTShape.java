/*
 * Created on Jun 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package shapes;

import java.awt.Shape;
import java.rmi.RemoteException;

/**
 * @author dewan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RemoteAWTShape extends RemoteShape{
	 public Shape getShape() throws RemoteException;;

		public void setShape(Shape shape) throws RemoteException;;

}
