package slgv;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.font.TextAttribute;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import shapes.ComponentModel;
import shapes.ImageModel;
import shapes.LineModel;
import shapes.RemoteAWTShape;
import shapes.RemoteArc;
import shapes.RemoteCurve;
import shapes.RemoteImage;
import shapes.RemoteLine;
import shapes.RemoteOval;
import shapes.RemoteRectangle;
import shapes.RemoteShape;
import shapes.RemoteText;
import slgc.MouseController;
import slgc.SLGController;
import slgc.ShapeEventNotifier;
import slm.SLPutCommand;
import slm.SLRemoveCommand;
import slm.ShapesList;
import util.misc.Common;
import util.models.Listenable;
import util.trace.Tracer;
import util.undo.Listener;
import bus.uigen.widgets.Painter;
import bus.uigen.widgets.swing.DelegateJPanel;
//import java.awt.List;
//import bus.agent.AutoAllConnect;
// removing UnicastRemoteObject as it gives error saying util.Listener not found, perhaps there is a skeleton class
public class SLGView /*extends UnicastRemoteObject*/ implements Listener, Remote, Painter
{
	
    transient private boolean paintKeys = false;
	transient private boolean paintLabels = false;
    transient private ShapesList slModel;
    transient private MouseController mouseController;
	//transient TextField tryField = new TextField("hello world");
	//transient Container container = new SLGViewPanel (this);
	transient TextField tryField;
	transient DelegateJPanel container;
	transient ShapeEventNotifier shapeEventNotifier;
	public SLGView (ShapesList theSLModel, DelegateJPanel theContainer) throws RemoteException
    {
        super();
		setContainer(theContainer);
		setSLModel(theSLModel);
    }
    public SLGView (ShapesList theSLModel) throws RemoteException
    {
        super();
		setContainer(new DelegateJPanel());
		setSLModel(theSLModel);

		/*
        slModel = theSLModel;
        //slModel.addObserver(this);
        for (Enumeration keys = slModel.keys(); keys.hasMoreElements();)
        {
            ShapeModel shapeModel = slModel.get((String) keys.nextElement());
            //(slModel.get(keys.nextElement())).addObserver(this);
            //shapeModel.addObserver(this);
            shapeModel.addListener(this);
        }
		//this.add(tryField);
		*/

    }
    /*
	void init (SLGViewPanel container) {
		//tryField = new TextField("hello world");
		//container = new SLGViewPanel (this);
		//container = new SLGViewPanel();
		
		
		//container.setLayout(null);
		
	}
	*/
	public void setContainer(DelegateJPanel newValue) {
		container = newValue;
		container.setLayout(null);
		container.addPainter(this);
	}
	public DelegateJPanel getContainer() {
		return container;
	}
	public SLGView () throws RemoteException
    {
        super();
        setContainer(new DelegateJPanel());
		//init();
		//this.add(tryField);
		

    }
	public void setSLModel (ShapesList theSLModel)
    {
		try {
		//System.out.println("slmodel " + theSLModel);
        slModel = theSLModel;
        //slModel.addObserver(this);
        for (Enumeration keys = slModel.keys(); keys.hasMoreElements();)
        {
            RemoteShape shapeModel = slModel.get((String) keys.nextElement());
            //(slModel.get(keys.nextElement())).addObserver(this);
            //shapeModel.addObserver(this);
            shapeModel.addListener(this);
        }
        
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		shapeEventNotifier = new ShapeEventNotifier(theSLModel, this);
		container.addMouseListener(shapeEventNotifier);
		container.addMouseMotionListener(shapeEventNotifier);

    }
	public ShapeEventNotifier getShapeEventNotifier() {
		return shapeEventNotifier;
	}
    public void setMouseController (MouseController theMouseController)
    {
        container.removeMouseListener(mouseController);
        container.removeMouseMotionListener(mouseController);
        mouseController = theMouseController;
        container.addMouseListener(mouseController);
        container.addMouseMotionListener(mouseController);
        container.repaint();
    }
	transient SLGController slgController = null;
	public void setController (SLGController theSLGController)
    {
        slgController = theSLGController;
    }
	
	public void repaint() {
		container.repaint();
	}

    public void toggleKeys()
    {
       paintKeys = !paintKeys;
       container.repaint();
    }
	public void toggleLabels()
    {
       paintLabels = !paintLabels;
       container.repaint();
    }
//    public void updateOld(Listenable model, Object arg)
//    {
//    	try {
//    		//System.out.println ("Listenable " + model + " arg " + arg);
//		//System.out.println("update called");
//    		RemoteShape shapeModel = null;
//    		if (arg != null && arg instanceof Boolean && (Boolean) arg)
//    			slModel.sort();
//    		else if ((arg != null) && (arg instanceof SLPutCommand)) {
//			
//		    //System.out.println("Update called on view " + this);
//           //RemoteShape shapeModel = ((SLPutCommand) arg).getShapeModel();
//           shapeModel = ((SLPutCommand) arg).getShapeModel();
//           // System.out.println("Shape model:" + shapeModel);
//            //shapeModel.addObserver(this);
//            shapeModel.addListener(this);
//			if (shapeModel instanceof ComponentModel) {
//				ComponentModel componentModel = (ComponentModel) shapeModel;
//			    Rectangle b = componentModel.getBounds();
//				Component component = componentModel.getComponent();
//				//System.out.println("comp model" + componentModel + "comp" + component);
//				//System.out.println ("model bounds" + b + "comp bounds" + component.getBounds());
//				component.setBounds(b.x, b.y, b.width, b.height);
//			}
//        }
//    		// null means delete
//		if (model instanceof SLModel &&  ((shapeModel == null ) || shapeModel instanceof ComponentModel))
//		     updateComponents(shapeModel);
//		//System.out.println("calling repaint");
//        container.repaint();
//    	} catch (Exception e) {
//    		System.out.println(e);
//    		e.printStackTrace();
//    	}
//    }
    public void update(Listenable model, Object arg) {
		try {
			// System.out.println ("Listenable " + model + " arg " + arg);
			// System.out.println("update called");
			RemoteShape shapeModel = null;
			if (arg != null && arg instanceof Boolean && (Boolean) arg)
				slModel.sort();
			else if ((arg != null) && (arg instanceof SLPutCommand)) {

				// System.out.println("Update called on view " + this);
				// RemoteShape shapeModel = ((SLPutCommand)
				// arg).getShapeModel();
				shapeModel = ((SLPutCommand) arg).getShapeModel();
				// System.out.println("Shape model:" + shapeModel);
				// shapeModel.addObserver(this);
				shapeModel.addListener(this);
				if (shapeModel instanceof ComponentModel) {
					ComponentModel componentModel = (ComponentModel) shapeModel;
					Rectangle b = componentModel.getBounds();
					Component component = componentModel.getComponent();
					// System.out.println("comp model" + componentModel + "comp"
					// + component);
					// System.out.println ("model bounds" + b + "comp bounds" +
					// component.getBounds());
					component.setBounds(b.x, b.y, b.width, b.height);
					List sortedComponents = slModel.getSortedComponentList();
					int insertPos = sortedComponents.indexOf(shapeModel);
					if (insertPos >= 0 && insertPos <= container.getComponentCount())
						container.add(component, insertPos );
					else {
						Tracer.error("Illegal position:" + insertPos + " Same object:" + shapeModel  + " at coordinates(" + shapeModel.getX() + ", " + shapeModel.getY() + ") probably displayed twice in graphics window and its stacking order will be ignored");
						container.add(component);
					}
					component.addMouseListener(shapeEventNotifier);
					
				}
			} else if ((arg instanceof SLRemoveCommand)) {
				shapeModel = ((SLRemoveCommand) arg).getOriginalShapeModel();
				if (shapeModel instanceof ComponentModel) {
				   ComponentModel componentModel = (ComponentModel) shapeModel;
				   container.remove(componentModel.getComponent());
				}
				
			}
			// null means delete
//			if (model instanceof SLModel
//					&& shapeModel instanceof ComponentModel)
//				updateComponents(shapeModel);
			// System.out.println("calling repaint");
			if (!slModel.locked())
			container.repaint();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
    
//	public void updateComponentsOld(RemoteShape shape) {
//		Component[] currentComponents = container.getComponents();
//		Vector currentComponentVector = new Vector();
//		for (int i = 0; i < currentComponents.length; i++)
//			currentComponentVector.addElement(currentComponents[i]);	
//		int componentModelIndex = -1;
//		//for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
//		List sortedElements = slModel.getSortedList();
//		for (int index = sortedElements.size() - 1; index >= 0; index --)
//        {
//		     //System.out.println("paintShapes " + this);
//			 //RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
//			 RemoteShape shapeModel = (RemoteShape) sortedElements.get(index);
//			 if (shapeModel instanceof ComponentModel) {
//				 componentModelIndex++;
//				 ComponentModel componentModel = (ComponentModel) shapeModel;
//				 Component component = componentModel.getComponent();
//				 if (currentComponentVector.contains(component)) {
//					 currentComponentVector.removeElement(component);
//					
//				 } else {
////					 if (component.getParent() != null)
////						 System.out.println("Non null parent");
//					 //container.add(component, componentModelIndex);
//					 container.add(component, 0);
////					 if (component instanceof JTextField) {
////						 System.out.println ("Adding JtextField:" + ((JTextField) component).getText());
////					 }
////					 System.out.println("component x, y(" + component.getX() + "," + component.getY() + ")");
////					 System.out.println("component width, height(" + component.getBounds().getWidth() + "," + component.getBounds().getHeight() + ")");
//				 }
//			 }
//		}
//		for (Enumeration elements = currentComponentVector.elements();
//			 elements.hasMoreElements();) {
//			Component component = (Component) elements.nextElement();
////			System.out.println("Remving component:" + component.getX() + "," + component.getY());	
//			container.remove(component);
//			//container.remove((Component) elements.nextElement());
//		}
//		//container.validate();
//		
//	}
	void resort(List sortedElements) {
		container.removeAll();
		//int componentModelIndex = -1;
		for (int index = sortedElements.size() - 1; index >= 0; index --)
        {
		     
			 RemoteShape shapeModel = (RemoteShape) sortedElements.get(index);
			 if (shapeModel instanceof ComponentModel) {
				 //componentModelIndex++;				 
				 ComponentModel componentModel = (ComponentModel) shapeModel;
				 Component component = componentModel.getComponent();				 
				 container.add(component); 					
			 }
		}
	}
		
		
	
	public void updateComponents(RemoteShape shape) {		
		Component[] currentComponents = container.getComponents();
		//Vector currentComponentVector = new Vector();
		List sortedElements = slModel.getSortedList();		
		List sortedComponents = slModel.getSortedComponentList();
//		for (int i = 0; i < currentComponents.length; i++)
//			currentComponentVector.addElement(currentComponents[i]);
		//if (shape instanceof ComponentModel) {
			ComponentModel componentModel = (ComponentModel) shape;
			Component component = componentModel.getComponent();
			if (currentComponents.length < sortedComponents.size()) { // insert
			//if (!currentComponentVector.contains(component)) {
				int insertPos = sortedComponents.indexOf(shape);
				container.add(component, insertPos );
				//currentComponents = container.getComponents();
				return; // trying to make it more efficient
			}
			else  { // remove -- this should be done in a better way
				container.remove(component);
			}
			
			//}
		
				
//		Vector unvisitedComponentVector = new Vector(currentComponentVector);
//		int componentModelIndex = -1;
//		int componentIndex;
//		//for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
//		boolean resort;
//		
//		for (int index = sortedElements.size() - 1; index >= 0; index --)
//        {
//		     //System.out.println("paintShapes " + this);
//			 //RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
//			 RemoteShape shapeModel = (RemoteShape) sortedElements.get(index);
//			 if (shapeModel instanceof ComponentModel) {
//				 componentModelIndex++;				 
//				 ComponentModel componentModel = (ComponentModel) shapeModel;
//				 Component component = componentModel.getComponent();
//				 if (currentComponentVector.contains(component)) {
////					 componentIndex = currentComponentVector.indexOf(component);
////					 if (componentIndex != componentModelIndex) {
////						 resort (sortedElements);
////					 }
//					 unvisitedComponentVector.removeElement(component);
//					 
//					
//				 } else {
////					 if (component.getParent() != null)
////						 System.out.println("Non null parent");
//					 container.add(component, componentModelIndex); // this should not be executed
//					 //container.add(component, 0);
////					 if (component instanceof JTextField) {
////						 System.out.println ("Adding JtextField:" + ((JTextField) component).getText());
////					 }
////					 System.out.println("component x, y(" + component.getX() + "," + component.getY() + ")");
////					 System.out.println("component width, height(" + component.getBounds().getWidth() + "," + component.getBounds().getHeight() + ")");
//				 }
//			 }
//		}
//		for (Enumeration elements = unvisitedComponentVector.elements();
//			 elements.hasMoreElements();) {
//			Component component = (Component) elements.nextElement();
////			System.out.println("Remving component:" + component.getX() + "," + component.getY());	
//			container.remove(component);
//			//container.remove((Component) elements.nextElement());
//		}
//		//container.validate();
		
	}
	
	/*
	String selection = null;	ShapeModel selectedShape = null;
		public void setSelection(String newVal) {			selection =  newVal;			if (newVal != null)
			  selectedShape = slModel.get(selection);
			else
				selectedShape = null;			repaint();
		}
		public String getSelection() {			return selection;
		}
	*/
	
	
	
    public void paint(Object obj)
    {
		//System.out.println(Thread.currentThread());
		//System.out.println("graphics" + g);
		//System.out.println(slgController);
		//System.out.println("paint called");
//    	System.out.println("Painted shapes");
    	
    	if (slModel.locked())
    		return;
    	Graphics g = (Graphics) obj;
        this.paintShapes(g);
        if (paintKeys)
            this.paintKeys(g);
		if (paintLabels)
			this.paintLabels(g);
		if (mouseController != null)
			mouseController.paint(g);
		//paintSelection(g);
    }
    public synchronized void paintShapes(Graphics g)
    {
    	try {
		//System.out.println("slModel " + slModel);
        List sortedShapes = new ArrayList( slModel.getSortedList());
//        System.out.println("Painting:" + sortedShapes);
        //for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
        for (int index = sortedShapes.size() - 1; index >= 0; index--)
        {
		     //System.out.println("paintShapes " + this);
			 //RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
        	 if (index >= sortedShapes.size()) {
        		 Tracer.error("Index, " + index + ">=  size:" + sortedShapes.size() + " of sorted shapes");
        	 }
			 RemoteShape shapeModel = (RemoteShape) sortedShapes.get(index);
			 if ((mouseController == null ) || mouseController.notBuffered(shapeModel)) {
				 //System.out.println("paintShape " + this);
				 /*
				 if (shapeModel == selectedShape)
					 paintSelection(g);
				 else
				 */
                    paintShape(g, shapeModel, container);
			 }
        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    /*
    public Container getContainer () {
    	return container;
    }
    */
    public void paintKeys(Graphics g)
    {
        for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
        {
            String key = (String) shapeKeys.nextElement();	
            RemoteShape shapeModel = slModel.get(key);
            if ((mouseController == null ) || mouseController.notBuffered(shapeModel))
                paintKey(g, key, shapeModel);
        }
    }
	public void paintLabels(Graphics g)
    {
        for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
        {
            String key = (String) shapeKeys.nextElement();	
            RemoteShape shapeModel = slModel.get(key);
			if ((mouseController == null ) || mouseController.notBuffered(shapeModel)) {
                paintKey(g, slModel.getLabel(key), shapeModel);
			}
        }
    }
    public void paintShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        //paintKey(g, key, shapeModel);
		/*
		if (shapeModel == selectedShape)
			paintSelection(g);
		else
		*/
           paintShape(g, shapeModel, container);
        //MouseController.paintHandle(g, shapeModel);

    }
    //private static void paintKey(Graphics g, String key, Shape shapeModel)
    private static void paintKey(Graphics g, String key, RemoteShape shapeModel)
    {
    	try {
		if (key == null)
			return;
        Rectangle bounds = shapeModel.getBounds();		
		int xOffset =  KEYOFFSET;
		int yOffset =  KEYOFFSET;
		if (shapeModel instanceof LineModel) {
			if (bounds.height > 0)
				yOffset = - KEYOFFSET*2;
			if (bounds.width >= 0)
				xOffset = - KEYOFFSET*2;
			else
				xOffset = + KEYOFFSET*12;
		}
        g.drawString(key, bounds.x - xOffset, bounds.y - yOffset);
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e);
    	}
    }
	

    private static final int KEYOFFSET = 5;

    public void eraseShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        Color origColor = g.getColor();
        g.setColor(container.getBackground());
		/*
		if (key == getSelection())
	       paintShape(g, shapeModel, true);
		*/
        paintShape(g, shapeModel, container);
        paintKey(g, key, shapeModel);
        g.setColor(origColor);
    }
	/*
	Color selectionColor = Color.cyan;
	public void setSelectionColor(Color newVal) {
		selectionColor = newVal;
	}
	public Color getSelectionColor() {
		return selectionColor;
	}
	
	public void paintSelection(Graphics g)
    {
		//System.out.println("apint Selection");
		String key = getSelection();
		if (key == null) return;
		ShapeModel shapeModel = slModel.get(key);
		if (shapeModel == null) return;	
		
        Color origColor = g.getColor();
        g.setColor(getSelectionColor());
        paintShape(g, shapeModel, false);
        //paintKey(g, key, shapeModel);
		//ResizeController.paintResizeHandle(g, shapeModel);
        g.setColor(origColor);
		
    }
	*/
	 public static void paintShape(Graphics g, RemoteShape shapeModel, Component aComponent)
    {
		try {
		paintShape(g, shapeModel, shapeModel.isFilled(), aComponent); // why pass this as an argument?
		} catch (Exception e) {
			System.out.println();
		}
	}
	
	static Integer fontSize = null;
	public static void setFontSize(Integer newFontSize) {
		fontSize = newFontSize;
	}
	
	public static Integer getFontSize() {
		return fontSize;
	}

    public static void paintShape(Graphics g, RemoteShape shapeModel, boolean fill, Component aComponent)
    {
    	try {
    		if (shapeModel.getWidth() == 0 && shapeModel.getHeight() == 0)
    			return;
    		Font graphicsFont = g.getFont();
    		
		Color shapeColor = shapeModel.getColor();
		Color graphicsColor = g.getColor();
		Graphics2D g2d = (Graphics2D) g;
		Stroke graphicsStroke = g2d.getStroke();
		Paint graphicsPaint = g2d.getPaint();
		Font font =  shapeModel.getFont();
		Integer myFontSize = shapeModel.getFontSize();
		if (myFontSize == null || myFontSize <= 0) {
			myFontSize = fontSize;
		}
		if (font != null) {
			g.setFont(font);
		} else if (myFontSize != null) {
			g.setFont(Common.toFontSize(g.getFont(), myFontSize));
			if (shapeModel instanceof ComponentModel) {
				ComponentModel componentModel = (ComponentModel) shapeModel;
				componentModel.setFont(Common.toFontSize(componentModel.getFont(), myFontSize));
			}
		}
		Stroke basicStroke = shapeModel.getStroke();
    	if (basicStroke != null)
    		g2d.setStroke(basicStroke);
    	Paint gradientPaint = shapeModel.getPaint();
    	if (gradientPaint != null)
    		g2d.setPaint(gradientPaint);   
		if (shapeColor != null)
			g.setColor(shapeColor);
		
         try
            {
                //Class shapeClass = shapeModel.getClass();
                //if (shapeClass == Class.forName("shapes.LineModel"))
				//if (shapeModel instanceof LineModel)
        	 	if (shapeModel instanceof RemoteAWTShape) {
        	 		paintAWTShape(g2d, (RemoteAWTShape) shapeModel);
        	 	}
        	 	else if (shapeModel instanceof RemoteLine)
                {
					//System.out.println(shapeModel.getBounds().x + " " + shapeModel.getBounds().y);
                    //paintLine(g, (RemoteLine) shapeModel, fill);
					paintLine2D(g2d, (RemoteLine) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.OvalModel"))
				else if (shapeModel instanceof RemoteOval) 
                {
                    paintOval(g, (RemoteOval) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.RectangleModel"))
				else if (shapeModel instanceof RemoteRectangle)
                {
                    //paintRectangle(g, (RemoteRectangle) shapeModel, fill);
                    paintRectangle2D(g, (RemoteRectangle) shapeModel, fill);
                } else if (shapeModel instanceof RemoteArc) {
                	//paintArc(g, (RemoteArc) shapeModel, fill);
                	paintArc2D(g2d, (RemoteArc) shapeModel, fill);
                } else if (shapeModel instanceof RemoteCurve) {
                	paintCurve2D(g2d, (RemoteCurve) shapeModel, fill);
                } else if (shapeModel instanceof shapes.StringModel) {
                	paintString(g, (RemoteText) shapeModel, fill);
                } else if (shapeModel instanceof ImageModel) {
                	paintImage((Graphics2D) g, (RemoteImage) shapeModel, fill, aComponent);
                }
		    	g.setColor(graphicsColor);
		    	g.setFont(graphicsFont);
		    	g2d.setPaint(graphicsPaint);
		    	g2d.setStroke(graphicsStroke);
            }
            catch (Exception e)
            {
                System.err.println("Should have stored shape id: " + e.toString());
                e.printStackTrace();
            }
		 if (shapeColor != null)
			g.setColor(graphicsColor);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    	
    }
    static private void paintString(Graphics g, RemoteText text, boolean fill)
    {
    
    	Graphics2D g2 = (Graphics2D) g;
    	Shape oldClip = g.getClip();
    	int clipWidth = oldClip.getBounds().width;
    	int clipHeight = oldClip.getBounds().height;
//    	int clipX = oldClip.getBounds().x;
//    	int clipY = oldClip.getBounds().y;
		boolean changedClip = false;
		

//    	g2.setClip(oldClip);

    	
    	try {
//    		Rectangle bounds = text.getBounds();
//    		if (text.getWidth() >= 0 && text.getHeight() >= 0) {
////    			g2.setClip(text.getX(), text.getY(), text.getWidth(), text.getHeight());
//    			Rectangle newClip = new Rectangle(text.getX(), 0, text.getWidth(), text.getHeight());
//    			g2.setClip(newClip);
//
//    			
//    		} 
    		int newX = text.getX();
//    		int newY = text.getY() + Common.getFontHeight(g2.getFont(), null);
    		// does not seem necessary to do add to Y, it seems to be upper left corner, wonder why I did it
    		int newY = text.getY();

    		if (text.getWidth() >= 0 ) {
    			clipWidth = text.getWidth();
//    			clipX = newX;
    			changedClip = true;
    		}
    		if (text.getHeight() >= 0) {
    			clipHeight = text.getHeight();
//    			clipY = newY;
    			changedClip = true;
    			
    		}
    		if (changedClip) {
    			Rectangle newClipBounds = new  Rectangle(newX, text.getY(), clipWidth, clipHeight);
//    		   g2.setClip(new Rectangle(clipX, clipY, clipWidth, clipHeight));
    		   g2.setClip(newClipBounds);

    		}
    		
    		
    		AttributedString attributedString = text.getAttributedString();
    		
    		if (attributedString == null ) {
    			Map<TextAttribute, Object> textAttributes = text.getTextAttributes();    			
    			if (textAttributes != null)   
    				attributedString = new AttributedString(text.getText(), textAttributes);
    		}
    			if (attributedString == null)    		
    				g.drawString(text.getText(), newX, newY);
    			else {
    				
    			g.drawString(attributedString.getIterator(), newX, newY);
    
//    		g.drawString(text.getText(), text.getX(), text.getY());
    		}
    	
		
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	} finally {
    		if (changedClip)
    		g.setClip(oldClip);
    	}
    }
    
    public static void paintImage(Graphics2D g, RemoteImage image, boolean fill, Component aComponent)
    {
    	try {
//    		Rectangle bounds = text.getBounds();
//    		g.drawImage(image.getImage(), image.getX(), image.getY(), container);
    		g.drawImage(image.getImage(), image.getX(), image.getY(), aComponent);

		
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintLine(Graphics g, RemoteLine line, boolean fill)
    {
    	try {
        Rectangle bounds = line.getBounds();
		if (fill)
			g.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
		else			
          g.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintLine2D(Graphics g, RemoteLine line, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	

    	try {
    		Line2D.Double line2D = new Line2D.Double(line.getNWCorner(), line.getSECorner());
    		
    	
    		
        //Rectangle bounds = line.getBounds();
		if (fill)
			//g2d.fill(line2D);
			g2d.draw(line2D);
		else			
			g2d.draw(line2D);
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintAWTShape(Graphics g, RemoteAWTShape anAWTShape)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	

    	try {
    		
    	g2d.draw(anAWTShape.getShape());
    		
        //Rectangle bounds = line.getBounds();
//		if (fill)
//			//g2d.fill(line2D);
//			g2d.draw(line2D);
//		else			
//			g2d.draw(line2D);
//    	
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintArc2D(Graphics g, RemoteArc arc, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		Arc2D.Double arc2D = new Arc2D.Double(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc.getStartAngle(), arc.getEndAngle(), Arc2D.OPEN);
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static final int ARC_WIDTH = 10;
    static final int ARC_HEIGHT = 10;
    static private void paintRectangle2D(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	try {
    		if (rectangle.isRounded()) {
        		paintRoundedRectangle2D(g, rectangle, fill);
        		return;
        	}
        //Rectangle bounds = rectangle.getBounds();
    		Rectangle2D.Double rectangle2D = new Rectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    		
		if (fill) {
			g2d.fill(rectangle2D);
			Color origColor = g.getColor();
			g.setColor(Color.BLACK);
			if (rectangle.is3D())
				g2d.fill3DRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.isRaised());
			else
			g2d.draw(rectangle2D);
			g.setColor(origColor);
			//g2d.draw(rectangle2D);
		}
		else {
			if (rectangle.is3D())
				g2d.draw3DRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.isRaised());
			else
           g2d.draw(rectangle2D);
		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintRoundedRectangle2D(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = rectangle.getBounds();
    		RoundRectangle2D.Double rectangle2D= new RoundRectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), ARC_WIDTH, ARC_HEIGHT);
    		
		if (fill) {
			g2d.fill(rectangle2D);
			Color origColor = g.getColor();
			g.setColor(Color.BLACK);
			g2d.draw(rectangle2D);
			g.setColor(origColor);
			//g2d.draw(rectangle2D);
		}
		else
		
           g2d.draw(rectangle2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintCurve2D(Graphics2D g, RemoteCurve curve, boolean fill)
    {
    	try {
    	if (curve.getControlX2() != null && curve.getControlY2() != null)
    		paintCubeCurve2D(g, curve, fill);
    	else
    		paintQuadCurve2D(g, curve, fill);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    static private void paintQuadCurve2D(Graphics g, RemoteCurve curve, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		QuadCurve2D.Double arc2D = new QuadCurve2D.Double(curve.getX(), curve.getY(), curve.getWidth(), curve.getHeight(), curve.getControlX(), curve.getControlY());
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    
    static private void paintCubeCurve2D(Graphics g, RemoteCurve curve, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		CubicCurve2D.Double arc2D = new CubicCurve2D.Double(curve.getX(), curve.getY(), curve.getWidth(), curve.getHeight(), curve.getControlX(), curve.getControlY(), curve.getControlX2(), curve.getControlY2());
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    
    static private void paintRectangle(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	
    	try {
        Rectangle bounds = rectangle.getBounds();
		if (fill)
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		else			
           //g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		   g.draw3DRect(bounds.x, bounds.y, bounds.width, bounds.height, false);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintArc(Graphics g, RemoteArc arc, boolean fill)
    {
    	try {
        Rectangle bounds = arc.getBounds();
		if (fill)
			g.fillArc(bounds.x, bounds.y, bounds.width, bounds.height, arc.getStartAngle(), arc.getEndAngle());
		else			
           g.drawArc(bounds.x, bounds.y, bounds.width, bounds.height, arc.getStartAngle(), arc.getEndAngle());
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }

    static private void paintOval(Graphics g, RemoteOval oval, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        Rectangle bounds = oval.getBounds();
		//System.out.println("painting oval" + bounds.x + " " + bounds.y + " " + bounds.width + " " + bounds.height);
       
		if (fill) {
			g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
			Color origColor = g.getColor();
			g.setColor(Color.BLACK);
			g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
			g.setColor(origColor);

		} else
            g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }


}
