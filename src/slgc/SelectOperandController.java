package slgc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Container;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;

import shapes.RemoteShape;
import shapes.ShapeModel;
import shapes.LineModel;
import slm.SLModel;
import slm.ShapesList;
import slgc.MoveController;
import slgc.ResizeController;
import slgc.SelectionController;
import slgv.SLGView;
import java.awt.Component;
import slm.SLPutCommand;
//import bus.agent.AutoAllConnect;

public  class SelectOperandController extends slgc.SelectionController //implements AutoAllConnect
{
   
    boolean resizing = false;
	boolean moving = false;
	Point location = null;
	protected String oldSelectedKey;
	int whichHandle;
    
    public void mousePressed(MouseEvent e)
    {
    	if (e.getButton() != e.BUTTON1)
    		return;
    	try {
		//System.out.println("mouse pressed in Select Operand");
		resizing = false;
		moving = false;
		location= e.getPoint();
		if (e.getSource() instanceof Component) {
			Component component = (Component) e.getSource();
			location = translate (component, location);
			//Point componentLocation = component.getLocation();
			//location.x += componentLocation.x;
			//location.y += componentLocation.y;
		}
		if (selectedShapeModel != null) {
	 		whichHandle = ResizeController.whichHandle(selectedShapeModel, e.getX(), e.getY());
	 	}
		if (selectedKey != null &&
			!(selectedShapeModel instanceof shapes.PointModel) &&
			//getHandle(selectedShapeModel.getBounds()).contains(location))  {
			whichHandle != -1) {
			resizing = true;			
			//draggedKey = selectedKey;
			//draggableShapeModel = (ShapeModel) selectedShapeModel.clone();
			super.mousePressed(e);
			
		} else {
			selectedKey = getSelection(location);
			//System.out.println("mouse pressed");
			if (selectedKey != null) {
				//System.out.println("selected object");
				shapeModelWasSelected = true;
				selectedKeyIsEditable = slgController.isEditable(selectedKey);
				selectedShapeModel = slModel.get(selectedKey);
				draggableShapeModel = (RemoteShape) selectedShapeModel.remoteClone();
				draggedKey = selectedKey;
				//Rectangle handle = getHandle(selectedShapeModel.getBounds());
				if (getHandle(selectedShapeModel.getBounds()).contains(location) && !(selectedShapeModel instanceof shapes.PointModel))
					//if (handle.contains(location))
					resizing = true;
				else
					moving = true;
				//System.out.println("moving" + moving + "resizing" + resizing);
				//System.out.println("handle" + handle + "location" + location);
				super.mousePressed(e);
		} else {
				   shapeModelWasSelected = false;			
			   }
			if (selectedKey != oldSelectedKey) {
				slgController.shapeSelected(selectedKey);			
				oldSelectedKey = selectedKey;
			} else {
				//unselect(selectedKey, null);
				// unselect selected item as in OE window
				slgController.shapeSelected(selectedKey);		

				//selectedKey = null;
			}
		}
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	//public void select(String newKey, ShapeModel newShape) {
	public void select(String newKey, RemoteShape newShape) {
		selectedKey = newKey;
		selectedShapeModel = newShape;
		/*
		if (selectedKey == null)
			shapeModelWasSelected = false;
		*/
		slgView.getContainer().repaint();
		
		
	}
	//public void unselect(String newKey, ShapeModel newShape) {
	public void unselect(String newKey, RemoteShape newShape) {
		if (selectedKey == newKey) {
			shapeModelWasSelected = false;		
		    slgView.getContainer().repaint();
		}
		
		
	}
	public  Point translate(Component component, Point p) {
		// kliudge, should keep track of View
		if (component == slgView.getContainer())
			return p;
		Point location = new Point();
		Point componentLocation = component.getLocation();
		location.x = componentLocation.x + p.x;
		location.y = componentLocation.y + p.y;
		return location;		
	}
	
	public String getSelection(Point p)
    {
         for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
         {
			 String shapeKey = (String) shapeKeys.nextElement();
             RemoteShape shapeModel = slModel.get( shapeKey);
			 //if (shapeModel.getBounds().contains(p))
			 if (contains (shapeModel, p))
				 return shapeKey;
         }
		 return null;
    }
	
	public String getSelection(RemoteShape s)
    {
         for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
         {
			 String shapeKey = (String) shapeKeys.nextElement();
             RemoteShape shapeModel = slModel.get( shapeKey);
			 //if (shapeModel.getBounds().contains(p))
			 if (shapeModel == s)
				 return shapeKey;
         }
		 return null;
    }
	static public Rectangle enlarge(Rectangle r, int by) {
		return new Rectangle (r.x - by, r.y - by, r.width + (2*by), r.height + (2*by));
	}
	static final int DELTA = SelectionController.HANDLE_SIZE;
	static public boolean contains (RemoteShape shapeModel, Point p) {
		try {
		Rectangle bounds = new Rectangle (shapeModel.getBounds());
//		System.out.println("bounds of " + shapeModel + " =" + bounds);
		if (shapeModel instanceof LineModel) {			
			if (bounds.width < 0) {
				bounds.x = bounds.x + bounds.width;
				bounds.width = - bounds.width;
			}
			if (bounds.height < 0) {				
				bounds.y = bounds.y + bounds.height;
				bounds.height = - bounds.height;
			}
			return contains(bounds, p);
		} 
		//return bounds.contains(p);
		return (enlarge(bounds, DELTA)).contains(p);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return false;
		}
		
	}
	public static boolean contains (Rectangle bounds, Point p) {
		Polygon linePolygon = new Polygon();
		int upperLeftX = bounds.x - DELTA;
		int upperLeftY = bounds.y - DELTA;
		int upperRightX = bounds.x + bounds.width + DELTA;
		int upperRightY = bounds.y - DELTA;
		int lowerLeftX = bounds.x - DELTA;
		int lowerLeftY = bounds.y + bounds.height + DELTA;
		int lowerRightX = bounds.x + bounds.width + DELTA;
		int lowerRightY = bounds.y + bounds.height + DELTA;
		linePolygon.addPoint(upperLeftX, upperLeftY);
		linePolygon.addPoint(upperRightX, upperRightY);
		linePolygon.addPoint(lowerRightX, lowerRightY);
		linePolygon.addPoint(lowerLeftX, lowerLeftY);
		Rectangle newBounds = linePolygon.getBounds();
		return linePolygon.contains(p);
		
		
	}
	public static boolean containsOld (Rectangle lineBounds, Point p) {
		Polygon linePolygon = new Polygon();
		int upperLeftX = lineBounds.x - DELTA;
		int upperLeftY = lineBounds.y - DELTA;
		int upperRightX = lineBounds.x + DELTA;
		int upperRightY = lineBounds.y - DELTA;
		int lowerLeftX = lineBounds.x + lineBounds.width - DELTA;
		int lowerLeftY = lineBounds.y + lineBounds.height + DELTA;
		int lowerRightX = lineBounds.x + lineBounds.width + DELTA;
		int lowerRightY = lineBounds.y + lineBounds.height + DELTA;
		linePolygon.addPoint(upperLeftX, upperLeftY);
		linePolygon.addPoint(upperRightX, upperRightY);
		linePolygon.addPoint(lowerRightX, lowerRightY);
		linePolygon.addPoint(lowerLeftX, lowerLeftY);
		Rectangle newBounds = linePolygon.getBounds();
		return linePolygon.contains(p);
		
		
	}
	public void paintHandles(Graphics g) {
		if (shapeModelWasSelected)
			paintHandle(g, selectedShapeModel);
	}
   
	public void paintHandle(Graphics g, RemoteShape shapeModel)
    {
		paintSelectionHandle(g, shapeModel);
    }
	public  void paintSelectionHandle(Graphics g, RemoteShape shapeModel)
    {
		/*if (moving)
			MoveController.paintMoveHandle(g, shapeModel, location);
		else*/ if (moving || resizing)
			ResizeController.paintResizeHandle(g, draggableShapeModel);
		else if (shapeModelWasSelected)
			ResizeController.paintResizeHandle(g, shapeModel);
    }
	public void mouseDragged(MouseEvent e)
    {
		Point p = e.getPoint();	
		//System.out.println("Mouse dragged: moving" + moving);
		
        if (shapeModelWasSelected && selectedKeyIsEditable) {
			//System.out.println("shape model was selected");
			if (e.getSource() instanceof Component)
				p = translate((Component) e.getSource(), p);
			if (moving) {
            moveULCorner(draggableShapeModel, p.x - location.x, p.y - location.y);
			} else
				ResizeController.resizeSelectedShape(draggableShapeModel, whichHandle, e);
				/*
				ResizeController.setULCorner(draggableShapeModel, e.getX(), e.getY());
				*/
			
		    //location.x = e.getX();
			//location.y = e.getY();
			
			location.x = p.x;
			location.y = p.y;
			
				
           super.mouseDragged(e);
        }
    }
	static public void moveULCorner (RemoteShape shape, int deltaX, int deltaY)
    {
		try {
		//System.out.println("moving" + shape);
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x + deltaX, curBounds.y + deltaY,
            curBounds.width, curBounds.height);
		} catch (Exception e2) {
			e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	
	
	public void mouseReleased (MouseEvent e)
    {
		//System.out.println("Selection mouse released");
		if (e.getButton() != e.BUTTON1)
			return;
		moving = false;
		resizing = false;
		if (shapeModelWasSelected && selectedKeyIsEditable) {
            super.mouseReleased(e);
		}
		//draggableShapeModel = null;
		//shapeModelWasDragged = false;
		slgView.getContainer().repaint();
    }
	
	
										   
}

