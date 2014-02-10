package shapes;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.rmi.RemoteException;
import java.text.AttributedString;
import java.util.Map;
public class TextModel extends ComponentModel implements ActionListener, RemoteText, AttributedTextShape, FlexibleTextShape
{
	javax.swing.JTextField textField;
	 Map<TextAttribute, Object> textAttributes;
	String text;


	
    public TextModel (Rectangle bounds)throws RemoteException 
    {		
        super(new javax.swing.JTextField(""), bounds);
		//super (createjavax.swing.JTextField(""), bounds);
		initializeTextField();
    }
	public TextModel ()throws RemoteException 
    {		
        super(new javax.swing.JTextField(""));
		//super (createjavax.swing.JTextField(""));
		initializeTextField();
    }
	public TextModel (String s)throws RemoteException 
    {
        super(new javax.swing.JTextField(s), new Rectangle(0, 0, 7*s.length(), 20));
		//super(createjavax.swing.JTextField(s), new Rectangle(0, 0, 7*s.length(), 20));
		initializeTextField();
    }
	void init (javax.swing.JTextField theComponent, Rectangle bounds) { 			
			super.init(theComponent, bounds);
	    }
	public TextModel (String s, int width, int height)throws RemoteException 
    {
		if (width <  0)
			width = 7*s.length();
		if (height <  0)
			height = 20;
       // super(new javax.swing.JTextField(s), new Rectangle(0, 0, width, height));
		init (new javax.swing.JTextField(s), new Rectangle(0, 0, width, height));
		//super(createjavax.swing.JTextField(s), new Rectangle(0, 0, 7*s.length(), 20));
		initializeTextField();
    }
	public TextModel (String s, int x, int y, int width, int height)throws RemoteException 
    {
		if (width == 0)
			width = 7*s.length();
		if (height == 0)
			height = 20;
       // super(new javax.swing.JTextField(s), new Rectangle(0, 0, width, height));
		init (new javax.swing.JTextField(s), new Rectangle(x, y, width, height));
		//super(createjavax.swing.JTextField(s), new Rectangle(0, 0, 7*s.length(), 20));
		initializeTextField();
    }
	
	void initializeTextField() {
		textField = (javax.swing.JTextField) component;
		setOriginalColors(textField);
		textField.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		this.notifyListeners();
	}
	public String getText() {
		if (component == null) return null;
		return ((javax.swing.JTextField) component).getText();
	}
	public void setText(String newValue) {
		if (newValue.equals(text)) return;
		text = newValue;
		((javax.swing.JTextField) component).setText(newValue);
		this.notifyListeners();
	}	
	
	public javax.swing.JTextField getTextField() {
		return textField;
	}
	public String toString() {
		return super.toString() + " text:" + text;
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
		Font font = textField.getFont().deriveFont(newVal);
		textField.setFont(font);
		
	}
	public boolean copy(BoundedShape aReference)
    {
		if (aReference.getClass() != this.getClass()) {
    		return false;
    	}
//    	TextModel aTextReference = (TextModel) aReference;
		if (aReference instanceof TextShape)
	    	setText(((TextShape) aReference).getText());
//    	setText(aReference.getText());
		return super.copy(aReference);
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
}
