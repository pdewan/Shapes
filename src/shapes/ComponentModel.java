package shapes;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Component;
import java.rmi.RemoteException;
public class ComponentModel extends BoundedShapeModel implements RemoteShape
{
	Component component;
	
    public ComponentModel (Component theComponent, Rectangle bounds)throws RemoteException 
    {		
        super(bounds);
        if (component != null)
        component.setBounds(bounds);
		component = theComponent;
    }
    public void setFont(Font f) {
    	component.setFont(f);
    }
    public Font getFont() {
    	if (component != null)
    	return component.getFont();
    	else return null;
    }
    void init (Component theComponent, Rectangle bounds) {    	
	   component = theComponent;
	   if (component != null)
		   component.setBounds(bounds);
	   super.init(bounds);
    }
	public ComponentModel (Component theComponent)throws RemoteException 
    {		
        super();
		component = theComponent;
    }
	public ComponentModel ()throws RemoteException 
    {		
        super();
    }
    public void setBounds(int x, int y, int width, int height)
    {
        //System.out.println("Set Bounds called " + x + " " + y + " " + width + " " + height);
    	 if (component != null)
    	component.setBounds(x,y, width, height);
		super.setBounds(x,y, width, height);

    }
    public void setBounds()
    {
        //System.out.println("Set Bounds called " + x + " " + y + " " + width + " " + height);
    	 if (component != null)
    	component.setBounds(getX(),getY(), getWidth(), getHeight());
		super.setBounds(getX(),getY(), getWidth(), getHeight());

    }
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component newComponent) {
		component = newComponent;
		this.notifyListeners();
	}
	public void setX (int newX) {
		super.setX(newX);
		setBounds();
		//super.setBounds(newX, getY(), getWidth(), getHeight());
	}
	public void setY (int newY) {
		super.setY(newY);
		setBounds();
		//super.setBounds(getX(),newY, getWidth(), getHeight());
	}
	public void setWidth (int newWidth) {
		super.setWidth(newWidth);
		setBounds();
		//super.setBounds(getX(),getY(),newWidth, getHeight());
	}
	public void setHeight (int newHeight) {
		super.setHeight(newHeight);
		setBounds();
		//super.setBounds(getX(),getY(),newWidth, getHeight());
	}
	//Color color;
	//boolean filled;
	Color originalFG;
	Color originalBG;
	public void setColor(Color newValue) {
		super.setColor(newValue);
		processFilledAndColor(component);
		
	}
	public void setFilled(boolean newValue) {
		super.setFilled(newValue);
		processFilledAndColor(component);
		
	}
	void processFilledAndColor(Component c) {
		if (c == null)
			return;
		if (getColor() == null)
			return;
		if (isFilled()) {
			c.setBackground(color);
			c.setForeground(originalFG);
		} else {
			c.setBackground(originalBG);
			c.setForeground(color);
		}
	}
	void setOriginalColors (Component c) {
		if (c == null) return;
		originalBG = component.getBackground();
		originalFG = component.getForeground();
	}
	
}
