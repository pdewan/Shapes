/*
 * Created on Jun 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package shapes;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.rmi.RemoteException;
import java.text.AttributedString;
import java.util.Map;

/**
 * @author dewan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RemoteText extends RemoteShape{
	public String getText() throws RemoteException ;
	public void setText(String newValue) throws RemoteException ;
	public void setFont(Font f) throws RemoteException;
    public Font getFont() throws RemoteException;
//    public TextAttribute[] getTextAttributes() throws RemoteException;
//    public void setTextAttributes(TextAttribute[] newVal) throws RemoteException;
    public AttributedString getAttributedString() throws RemoteException;
    public void setAttributedString(AttributedString newVal) throws RemoteException;
    public Map<TextAttribute, Object> getTextAttributes() throws RemoteException;
    public void setTextAttributes(Map<TextAttribute, Object> newVal) throws RemoteException;
  

}
