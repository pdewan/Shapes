package shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;


public interface AttributedShape extends BoundedShape {
//	public int getX() ;
//	public int getY() ;
//	public void setX(int newVal) ;
//	public void setY(int newVal) ;
//	public void setWidth(int width);
//	public void setHeight(int height);
//	public int getHeight() ;
//	public int getWidth() ;
//	public Rectangle getBounds();
//	public void setBounds(Rectangle newVal) ;
	public Color getColor() ;
	public void  setColor(Color newVal) ;
	public boolean isFilled() ;
	public void  setFilled(boolean newVal) ;
	
	public Font getFont() ;
	public void  setFont(Font newVal) ;
	public Stroke getStroke() ;
	public void  setStroke(Stroke newVal) ;
	public Paint getPaint() ;
	public void setFontSize(int newSize) ;
	public int getFontSize() ;
	public int getZIndex();
	public void setZIndex(int newVal);
	//public void mouseClicked(VirtualMouseEvent mouseEvent) {
	//	
	//};

	public void  setPaint(Paint newVal) ;
	public boolean isRounded() ;
	public void  setRounded(boolean newVal) ;
	public boolean is3D() ;
	public void  set3D(boolean newVal) ;

}
