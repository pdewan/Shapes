package shapes;

import java.awt.Rectangle;
import java.util.List;


public class ShapesUtility {
	public static RectangleDiagonalPoints toDiagonalPoints (Rectangle aRectangle) {
		if (aRectangle == null)
			return new ARectangleDiagonalPoints(0, 0, 0, 0);
		return new ARectangleDiagonalPoints(aRectangle.x, 
				aRectangle.y, 
				aRectangle.x + aRectangle.width, 
				aRectangle.y + aRectangle.height);
		
	}

	public static Rectangle toRectangle (RectangleDiagonalPoints aRectangleDiagonalPoints) {
		if (aRectangleDiagonalPoints == null)
			return new Rectangle(0, 0, 0, 0);
		return new Rectangle(aRectangleDiagonalPoints.getUpperLeftX(), 
				aRectangleDiagonalPoints.getUpperLeftY(), 
				aRectangleDiagonalPoints.getLowerRightX() - aRectangleDiagonalPoints.getUpperLeftX(), 
				aRectangleDiagonalPoints.getLowerRightY() - aRectangleDiagonalPoints.getUpperLeftY());
	
	}

	public static RectangleDiagonalPoints areaCovering (
			RectangleDiagonalPoints anArea1, 
			RectangleDiagonalPoints anArea2) {
		if (anArea1 == null) return new  ARectangleDiagonalPoints(anArea2);
		else if (anArea2 == null) return new ARectangleDiagonalPoints(anArea1);
		
		return new ARectangleDiagonalPoints(
				Math.min(anArea1.getUpperLeftX(), anArea2.getUpperLeftX()),
				Math.min(anArea1.getUpperLeftY(), anArea2.getUpperLeftY()),
				Math.max(anArea1.getLowerRightX(), anArea2.getLowerRightX()),
				Math.max(anArea1.getLowerRightY(), anArea2.getLowerRightY()));
	}
	public static RectangleDiagonalPoints areaCovering (
			BoundedShape aShape1, 
			BoundedShape aShape2) {
		if (aShape1 == null) return toDiagonalPoints(aShape2.getBounds());
		else if (aShape2 == null) return toDiagonalPoints(aShape1.getBounds());
		return  areaCovering(toDiagonalPoints(aShape1.getBounds()), toDiagonalPoints(aShape2.getBounds()));
			
				
		
	}
	
	public static RectangleDiagonalPoints areaCovering(List<? extends BoundedShape> shapes) {
		if (shapes.size() == 0) 
			return new ARectangleDiagonalPoints(null);
		else if (shapes.size() == 1) {
			return toDiagonalPoints(shapes.get(0).getBounds());
		} else {
			RectangleDiagonalPoints retVal = toDiagonalPoints(shapes.get(0).getBounds());
			for (int i = 1; i < shapes.size(); i++) {
				retVal = areaCovering(shapes.get(i), retVal);
			}
			return retVal;
		}
	}
	public static Rectangle rectangleCovering(List<? extends BoundedShape> shapes) {
		return toRectangle(areaCovering(shapes));
	}

	public static RectangleDiagonalPoints areaCovering (
			BoundedShape aShape1,
			RectangleDiagonalPoints anArea2) {
		
			if (aShape1 == null)
				return new ARectangleDiagonalPoints(anArea2);
			else
				return (areaCovering(toDiagonalPoints(aShape1.getBounds()), anArea2));
	
				
		
	}
	 

}
