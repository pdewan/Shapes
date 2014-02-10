package shapes;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.rmi.RemoteException;
import java.text.AttributedString;
import java.util.Map;

import util.misc.Common;
public class StringModel extends BoundedShapeModel implements RemoteText, AttributedTextShape, FlexibleTextShape
{
//	TextAttribute[] textAttributes;
	AttributedString attributedString;
	public static int CHARACTER_HEIGHT = 20;
	public static int CHARACTER_WIDTH = 7;
	int maxStringWidth;
	int maxStringHeight;
	Map<TextAttribute, Object> textAttributes;
	
	public StringModel (String s)throws RemoteException 
    {
//        super(new Rectangle(0, 0, CHARACTER_WIDTH*s.length(), CHARECTER_HEIGHT));
        super(new Rectangle(0, 0, -1, -1));

		//super(createjavax.swing.JTextField(s), new Rectangle(0, 0, 7*s.length(), 20));
		text = s;
		calculateMaxSize();
    }
	public StringModel (String theString, int x, int y)throws RemoteException 
    {
//        super(x, y, CHARACTER_WIDTH*theString.length(), CHARACTER_HEIGHT);
        super(x, y, -1, -1); // -1 width and height means draw the whole string

        text = theString;
        calculateMaxSize();
    }
	@Override
	public int getCenterX() {
		if (bounds.width == -1) {
			return bounds.x + maxStringWidth/2;
		}
		return super.getCenterX();
		
	}
	
	@Override
	public int getCenterY() {
		if (bounds.height == -1) {
			return bounds.x + maxStringHeight/2;
		}
		return super.getCenterY();
		
	}
	@Override
	public void setCenterX(int newVal) {
		if (bounds.width == -1)
			setX(newVal - maxStringWidth/2);
		else
			super.setCenterX(newVal);
	}
	
	@Override
	public void setCenterY(int newVal) {
		if (bounds.height == -1)
			setX(newVal - maxStringHeight/2);
		else
			super.setCenterY(newVal);
	}
	
	void calculateMaxSize() {
		Font aFont = getFont();
		maxStringWidth = Common.getFontStringWidth(aFont, text, getFontSize());
		maxStringHeight = Common.getFontHeight(aFont, getFontSize());
		
	}
	
    public StringModel (Rectangle theBounds)throws RemoteException 
    {
        super(theBounds);
    }
	public StringModel (int x, int y, int width, int height, String theString)throws RemoteException 
    {
        super(x, y, width, height);
        text = theString;
        calculateMaxSize();
    }
	public StringModel ()throws RemoteException 
    {
        super();
    }
	String text;
	@Override
	public String getText()  {
		return text;
	}
	@Override
	public void setFontSize(int newVal) {
		super.setFontSize(newVal);
		maxStringWidth = Common.getFontStringWidth(getFont(), text, getFontSize());
		maxStringHeight = Common.getFontHeight(getFont(), getFontSize());
	}
	@Override
	public void setText(String newValue) {
//		if (newValue.equals("goodye")) {
//			System.out.println ("goodye");
//		}
		text = newValue;
		calculateMaxSize();
		this.notifyListeners();
		
	}
	public String toString() {
		return super.toString() + " Text:" + text;

	}
	@Override
	public void setBounds (Rectangle newVal) {
		if (newVal.width != -1 && newVal.height != -1) {

		newVal.width = Math.min(maxStringWidth, newVal.width);
		newVal.height = Math.min(maxStringHeight, newVal.height);
		}
		super.setBounds(newVal);		
		
	}
	@Override
	public void setSize (Dimension newVal) {
		if (newVal.width != -1 && newVal.height != -1) {
		newVal.width = Math.min(maxStringWidth, newVal.width);
		newVal.height = Math.min(maxStringHeight, newVal.height);
		}
		super.setSize(newVal);		
		
	}
	@Override
	public void setWidth (int newVal) {
		if (newVal != -1)
		newVal = Math.min(maxStringWidth, newVal);
		super.setWidth(newVal);		
		
	}
	@Override
	public void setHeight (int newVal) {
		if (newVal != -1)

		newVal = Math.min(maxStringHeight, newVal);
		super.setHeight(newVal);		
		
	}
//	void deriveAttributedString() {
//		attributedString = new AttributedString(text);
//		for (TextAttribute textAttribute: textAttributes) {
//			attributedString.
//		}
//	}
//	@Override
//	public TextAttribute[] getTextAttributes() throws RemoteException {
//		return textAttributes;
//	}
//	@Override
//	public void setTextAttributes(TextAttribute[] newVal)
//			throws RemoteException {		
//		if (newVal == null) {
//			attributedString = null;		
//		} else if (newVal.equals(textAttributes)) {
//			return;
//		} else {
//			attributedString = new AttributedString(text);
//			
//		}
//		textAttributes = newVal;
//
//		this.notifyListeners();
//
//	}
	@Override
	public AttributedString getAttributedString() {
		return attributedString;
	}
	@Override
	public void setAttributedString(AttributedString newVal) {
		attributedString = newVal;
		
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
		
	}
	public boolean copy(BoundedShape aReference)
    {
		if (aReference.getClass() != this.getClass()) {
    		return false;
    	}
//    	TextModel aTextReference = (TextModel) aReference;
		if (aReference instanceof TextShape)
    	setText(((TextShape) aReference).getText());
		return super.copy(aReference);
    }
	

}
