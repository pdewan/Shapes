package shapes;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.rmi.RemoteException;
import java.text.AttributedString;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import util.trace.ImageFileMissing;
public class LabelModel extends ComponentModel implements ActionListener, RemoteLabel, LabelShape
{
	//javax.swing.JLabel label;
	String imageFile;
	String textString;
	Image image;
	 Map<TextAttribute, Object> textAttributes;
	
	
    public LabelModel (Rectangle bounds)throws RemoteException 
    {		
        super(new javax.swing.JLabel(""), bounds);
		//super (createTextField(""), bounds);
		initializeLabel();
    }
    public LabelModel (Rectangle bounds, Component c)throws RemoteException 
    {		
        super(c, bounds);
		//super (createTextField(""), bounds);
		initializeLabel();
    }
	public LabelModel ()throws RemoteException 
    {		
        super(new javax.swing.JLabel(""));
		//super (createLabel(""));
		initializeLabel();
    }
	public LabelModel (Component c)throws RemoteException 
    {		
        super(c);
		//super (createLabel(""));
		initializeLabel();
    }
	public LabelModel (String s)throws RemoteException 
    {
        super(new javax.swing.JLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		//super(createLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		initializeLabel();
    }
	public LabelModel (String s, Component c)throws RemoteException 
    {
        super(c, new Rectangle(0, 0, 7*s.length(), 20));
		//super(createLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		initializeLabel();
    }
	void init (javax.swing.JLabel theComponent, Rectangle bounds) { 			
			super.init(theComponent, bounds);
			/*
			((JLabel) component).setPreferredSize(new Dimension(bounds.width, bounds.height));
			((JLabel) component).setSize(new Dimension(bounds.width, bounds.height));
			*/
			//getLabel().setVerticalAlignment(SwingConstants.TOP);
			//getLabel().setVerticalAlignment(SwingConstants.TOP);
			getLabel().setVerticalAlignment(SwingConstants.CENTER);
	    }
	public LabelModel (String text, String theImageFile, int width, int height)throws RemoteException 
    {
		if (width == 0)
			width = 7*text.length();
		if (height == 0)
			height = 20;
       // super(new Label(s), new Rectangle(0, 0, width, height));
		init (new javax.swing.JLabel(text), new Rectangle(0, 0, width, height));
		//super(createLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		//((JLabel) component).setText(text);
		imageFile = theImageFile;
		textString = text;
		initializeLabel();
    }
	public LabelModel (String text, String theImageFile, int width, int height, Component c)throws RemoteException 
    {
		if (width == 0)
			width = 7*text.length();
		if (height == 0)
			height = 20;
       // super(new Label(s), new Rectangle(0, 0, width, height));
		init (c, new Rectangle(0, 0, width, height));
		//super(createLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		//((JLabel) component).setText(text);
		imageFile = theImageFile;
		textString = text;
		initializeLabel();
    }
	public LabelModel (String text, String theImageFile, int x, int y, int width, int height, Component c)throws RemoteException 
    {
		if (width == 0)
			width = 7*text.length();
		if (height == 0)
			height = 20;
       // super(new Label(s), new Rectangle(0, 0, width, height));
		init (component, new Rectangle(x, y, width, height));
		//super(createLabel(s), new Rectangle(0, 0, 7*s.length(), 20));
		//((JLabel) component).setText(text);
		imageFile = theImageFile;
		textString = text;
		initializeLabel();
    }
	void initializeLabel() {
		//label = (javax.swing.JLabel) component;
		setImageFileName(imageFile);
		setOriginalColors(component);
		//label.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		this.notifyListeners();
	}
	public String getText() {
		return textString;
		/*
		if (component == null) return null;
		return ((javax.swing.JLabel) component).getText();
		*/
	}
	public void setText(String newValue) {
		textString = newValue;
		if (component != null)
		((javax.swing.JLabel) component).setText(newValue);
		this.notifyListeners();
	}
	public String getImageFileName() {
		return imageFile;
		/*
		if (component == null) return null;
		return ((javax.swing.JLabel) component).getIcon();
		*/
	}
	public void setImageFileName(String iconFile) {
		
		if (iconFile != null) {
			imageFile = iconFile;
			if (component != null) {
//			java.net.URL imgURL = Object.class.getResource(iconFile);
//			image = Toolkit.getDefaultToolkit().getImage(imageFile);
//			if (image == null) {
//				ImageFileMissing.newCase(imageFile, this);
//			} else {
			Icon icon = new ImageIcon(iconFile);
			setImageData(iconFile, icon, null);
//			if (icon.getIconHeight() < 0 || icon.getIconWidth() < 0) {
//				ImageFileMissing.newCase(imageFile, this);
//				return;
//			}
//			getLabel().setIcon(icon);
//			this.notifyListeners();
////			}
			}
			//((javax.swing.JLabel) component).setIcon(icon);		
		}		
	}	
	
	
	public Image getImage() {
		return image;
	}
	
	public javax.swing.JLabel getLabel() {
		return (JLabel) component;
	}
	
	public String toString() {
		return super.toString() +  " Image:" + imageFile + " Text:" + textString;

//		if (textString != null && textString != "") {
//			return super.toString() + " Text:" + textString;
//		}
//		else {
//			return super.toString();
//		}
			
		
	}
	/*
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
	*/
	@Override
	public void setImageData(String anImageFile,  Icon icon, Image image) {
		imageFile = anImageFile;
		if (icon == null || icon.getIconHeight() < 0 || icon.getIconWidth() < 0) {
			ImageFileMissing.newCase(imageFile, this);
			return;
		}
		getLabel().setIcon(icon);
		this.notifyListeners();
//		}
		
		
	}
//	@Override
//	public TextAttribute[] getTextAttributes() throws RemoteException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void setTextAttributes(TextAttribute[] newVal)
//			throws RemoteException {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public AttributedString getAttributedString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAttributedString(AttributedString newVal) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<TextAttribute, Object> getTextAttributes()
			throws RemoteException {
		return textAttributes;
	}
	@Override
	public void setTextAttributes(Map<TextAttribute, Object> newVal)
			throws RemoteException {
		textAttributes = newVal;
		Font font = getLabel().getFont().deriveFont(newVal);
		getLabel().setFont(font);
		
	}
	
		public boolean copy(BoundedShape aReference)
	    {
			if (aReference.getClass() != this.getClass()) {
	    		return false;
	    	}
//	    	TextModel aTextReference = (TextModel) aReference;
			if (aReference instanceof TextShape)
		    	setText(((TextShape) aReference).getText());
//	    	setText(aReference.getText());
			if (aReference instanceof ImageShape)
	    	setImageFileName(((ImageShape) aReference).getImageFileName());
			return super.copy(aReference);
	    }
    
	
}
