/*
 * Created on Jun 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package shapes;

import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author dewan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RemoteImage extends RemoteShape{
	public String getImageFileName() throws RemoteException;
	public void setImageFileName(String newValue) throws RemoteException ;
	public void setImageData(String imageFile, Icon newIcon, Image newImage) throws RemoteException ;

	public Image getImage() throws RemoteException;

}
