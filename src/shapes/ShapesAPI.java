package shapes;

import java.awt.Font;
import java.awt.Rectangle;

import slm.SLModel;



public class ShapesAPI {
	static int fontSize = 0;
	 static Font font = null;

	public static Font getDefaultFont() {
		return font;
	}

	public static void setDefaultFont(Font newVal) {
		font = newVal;
	}

	public static int getDefaultFontSize() {
		return fontSize;
	}

	public static void setDefaultFontSize(int newVal) {
		fontSize = newVal;
	}
  public static void drawLine(SLModel model, 
			      String label,
			      int x1, int y1, 
			      int x2, int y2) {
  	try {
    LineModel line = new LineModel(new Rectangle(x1, y1, (x2-x1), (y2-y1)));
    model.put(label, line);
  	} catch (Exception e) {
  		e.printStackTrace();
  		//System.out.println(e);
  	}
  }

  public static void drawCircle(SLModel model, 
				String label, 
				int xc, int yc, 
				int r) {
  	try {
    OvalModel oval = new OvalModel(new Rectangle(xc-r, yc-r, 2*r, 2*r));
    model.put(label, oval);
  	} catch (Exception e) {
  		e.printStackTrace();
  		//System.out.println(e);
  	}
  }
  
		 

}
