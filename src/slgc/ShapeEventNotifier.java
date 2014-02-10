package slgc;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shapes.RemoteShape;
import slgv.SLGView;
import slm.ShapesList;
import util.trace.Tracer;

public class ShapeEventNotifier implements MouseListener, KeyListener, MouseMotionListener {
	List<ShapeListAWTMouseListener> mouseListeners = new ArrayList();

	ShapesList shapesList;
	SLGView slgView;
	RemoteShape lastEnteredShape;

	public ShapeEventNotifier(ShapesList theShapesList, SLGView theView) {
		shapesList = theShapesList;
		slgView = theView;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	Point getClickPoint(MouseEvent arg0) {
		Point clickPoint = arg0.getPoint();
		Object source = arg0.getSource();
		if (source != slgView.getContainer()) {
			// there must be a way for the container to get component events untranslated
			Component component = (Component) source;
			int componentX = component.getX();
			int componentY = component.getY();
			clickPoint.x += componentX;
			clickPoint.y += componentY;
		}
		return clickPoint;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point clickPoint = getClickPoint(arg0);		
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape smallest = smallestShape(shapes);;
		notifyMouseClickListeners(shapes, smallest, arg0, null);
		
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
//		if (arg0.getSource() == slgView.getContainer())
//			return;
//		System.out.println("Mouse Entered:" + arg0);		
		Point clickPoint = getClickPoint(arg0);
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape shape = smallestShape(shapes);
		lastEnteredShape = shape;
		notifyMouseEnteredListeners(shapes, shape, arg0, clickPoint);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
//		if (arg0.getSource() == slgView.getContainer())
//			return;
//		System.out.println("Mouse exited:" + arg0);
		Point clickPoint = getClickPoint(arg0);
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape shape = smallestShape(shapes);
//		System.out.println("Mouse exited smallest shape:" + shape);
		// dunno why this happens, perhaps because of the enlarging of the rectangle
		if (shape != lastEnteredShape) {
//			System.out.println("last entered shape not same as exited one");
			shape = lastEnteredShape;
		}
		notifyMouseExitedListeners(shapes, shape, arg0, clickPoint);
		
	}
	
	public static int size(Rectangle bounds) {
		// measuring perimeter to account for vertical or horizontal lines
		return Math.abs(bounds.height + bounds.width);
	}
	
	public static RemoteShape smallestShape(List<RemoteShape> shapes) {
		int currentSize = Integer.MAX_VALUE;
		RemoteShape retVal = null;
		
		for (RemoteShape shape:shapes) {
			try {
			int size = size(shape.getBounds());
			if (size < currentSize) {
				retVal = shape;
				currentSize = size;
			}
			} catch (Exception e) {
				
			}
		}		
		return retVal;
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point clickPoint = getClickPoint(arg0);
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape shape = smallestShape(shapes);
		notifyMousePressedListeners(shapes, shape, arg0, null);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point clickPoint = getClickPoint(arg0);	
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape shape = smallestShape(shapes);
		notifyMouseReleasedListeners(shapes, shape, arg0, clickPoint);
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point clickPoint = getClickPoint(arg0);
		List<RemoteShape> shapes = getShapesContaining(clickPoint);
		RemoteShape shape = smallestShape(shapes);
		if (shape == lastEnteredShape)
			return;
//		if (shape != null ) {
//			if (lastEnteredShape != null) {
//				System.out.println("Mouse exits: " + lastEnteredShape);
				notifyMouseExitedListeners(shapes, lastEnteredShape, arg0, clickPoint);
//			}	
//			System.out.println("Mouse enteres: " + shape);
//			notifyMouseEnteredListeners(shapes, shape, arg0, clickPoint);
			 
//		}
		notifyMouseEnteredListeners(shapes, shape, arg0, clickPoint);
		lastEnteredShape = shape;


		
	}
	public List<RemoteShape> getShapesContaining(Point p)
    {
		 List retVal = new ArrayList();
         for (Enumeration shapeKeys = shapesList.keys();shapeKeys.hasMoreElements();)
         {
			 String shapeKey = (String) shapeKeys.nextElement();
             RemoteShape shapeModel = shapesList.get( shapeKey);
             if (SelectOperandController.contains (shapeModel, p))
				 retVal.add(shapeModel);
         
         }
		 return retVal;
    }
	public void addMouseListener(ShapeListAWTMouseListener theMouseClickListner) {
		if (!mouseListeners.contains(theMouseClickListner))
			mouseListeners.add(theMouseClickListner);		
	}
	public void removeMouseListener(ShapeListAWTMouseListener theMouseClickListner) {
		if (mouseListeners.contains(theMouseClickListner))
			mouseListeners.remove(theMouseClickListner);		
	}
	synchronized void  notifyMouseClickListeners(List<RemoteShape> shapes, RemoteShape smallest, MouseEvent mouseEvent, Point aClickPoint) {
		//System.out.println("notifyMouseClickListeners called");
		if (mouseListeners == null)
			return;
		for (int i = 0; i < mouseListeners.size(); i++ ) {
			mouseListeners.get(i).mouseClicked(shapes, smallest, mouseEvent, null);
		}
//		for (ShapeListAWTMouseClickListener mouseClickListener: mouseClickListeners ) {
//			try {
//			mouseClickListener.mouseClicked(shapes, mouseEvent);
//			} catch (Exception e) {
//				Message.error("Mouse click error: " + e.toString());
//			}
//		}
		
//		if (shapes.size() >= 0) {
//			slgView.repaint();
//		}
	}
	synchronized void  notifyMousePressedListeners(List<RemoteShape> shapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) {
		//System.out.println("notifyMouseClickListeners called");
		if (mouseListeners == null)
			return;		
		for (int i = 0; i < mouseListeners.size(); i++ ) {
//			System.out.println("Notifying:" + mouseListeners.get(i));
			mouseListeners.get(i).mousePressed(shapes, theSmallestShape, mouseEvent, aClickPoint);
		}
	}
	synchronized void  notifyMouseReleasedListeners(List<RemoteShape> shapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) {
		//System.out.println("notifyMouseClickListeners called");
		if (mouseListeners == null)
			return;
		for (int i = 0; i < mouseListeners.size(); i++ ) {
//			System.out.println("Notifying:" + mouseListeners.get(i));
			mouseListeners.get(i).mouseReleased(shapes, theSmallestShape, mouseEvent, aClickPoint);
		}
	}
	synchronized void  notifyMouseEnteredListeners(List<RemoteShape> shapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) {
		//System.out.println("notifyMouseClickListeners called");
		if (mouseListeners == null)
			return;
		for (int i = 0; i < mouseListeners.size(); i++ ) {
//			System.out.println("Notifying:" + mouseListeners.get(i));
			mouseListeners.get(i).mouseEntered(shapes, theSmallestShape, mouseEvent, aClickPoint);
		}
	}
	synchronized void  notifyMouseExitedListeners(List<RemoteShape> shapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) {
		//System.out.println("notifyMouseClickListeners called");
		if (mouseListeners == null)
			return;
		for (int i = 0; i < mouseListeners.size(); i++ ) {
//			System.out.println("Notifying:" + mouseListeners.get(i));
			mouseListeners.get(i).mouseExited(shapes, theSmallestShape, mouseEvent, aClickPoint);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
//		System.out.println("Mouse Dragged on Component: " + arg0.getSource());
		
	}

	

}
