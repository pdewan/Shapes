package slgc;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import bus.uigen.widgets.events.VirtualMouseEvent;

import shapes.RemoteShape;

public interface ShapeListAWTMouseListener {
	public void mouseClicked(List <RemoteShape> theShapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) ;
	public void mousePressed(List <RemoteShape> theShapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) ;
	public void mouseReleased(List <RemoteShape> theShapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) ;

	public void mouseEntered(List <RemoteShape> theShapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) ;
	
	public void mouseExited(List <RemoteShape> theShapes, RemoteShape theSmallestShape, MouseEvent mouseEvent, Point aClickPoint) ;



}
