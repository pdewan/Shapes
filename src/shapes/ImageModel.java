package shapes;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import util.misc.Common;
import util.trace.ImageFileMissing;
import util.trace.Tracer;
public class ImageModel extends BoundedShapeModel implements RemoteImage
{
	String imageFile;
	Image image, scaledImage;
	int imageWidth, imageHeight;
//	public static int CHARECTER_HEIGHT = 20;
//	public static int CHARACTER_WIDTH = 7;
	public ImageModel (String theImageFile)throws RemoteException 
    {
        super(new Rectangle(0, 0, -1, -1));
        imageFile = theImageFile;
    }
	public ImageModel (String theImageFile, int x, int y)throws RemoteException 
    {
        super(x, y, 0, 0);  
        imageFile = theImageFile;
//        setImageFileName (theImageFile);

    }
	public ImageModel (String theImageFile, int x, int y, int width, int height)throws RemoteException 
    {
        super(x, y, width, height);  
        imageFile = theImageFile;
//        setImageFileName (theImageFile);

    }
	
	@Override
	public String getImageFileName()  {
		return imageFile;
	}
	@Override
	public void setImageFileName(String newValue)  {
		if (newValue == null) {
			Tracer.error("Null image file:" + newValue);
			return;
		}
		imageFile = newValue;
		ImageIcon icon = new ImageIcon(imageFile);
//		Common.toImage(imageFile, this)
		setImageData(imageFile, icon, icon.getImage());
//		if (icon.getIconHeight() < 0 || icon.getIconWidth() < 0) {
//			ImageFileMissing.newCase(imageFile, this);
//			return;
//		}
//		image = Toolkit.getDefaultToolkit().getImage(imageFile);
//		bounds.width = icon.getIconWidth();
//		bounds.height = icon.getIconHeight();

		this.notifyListeners();		
	}	
	
	public Image getImage() {
		return scaledImage;
	}
	public String toString() {
		return super.toString() + " Image:" + imageFile;
	}
	
	@Override
	public void setImageData(String anImageFile, Icon icon, Image anImage) {
		if (anImage == null) {
				ImageFileMissing.newCase(imageFile, this);
				return;
			
		}
		imageFile = anImageFile;
		image = anImage;
//		imageWidth = anImage.getWidth(null);
//		imageHeight = anImage.getHeight(null);	
		imageWidth = icon.getIconWidth();
		imageHeight = icon.getIconWidth();
//		if (imageWidth < 0 && imageHeight < 0) {
//			ImageFileMissing.newCase(imageFile, this);
//			return;
//		}
		if (bounds.width <= 0)
//		bounds.width = anImage.getWidth(null);
			bounds.width = imageWidth;
		if (bounds.height <= 0)
			bounds.height = imageHeight;
//		bounds.height = anImage.getHeight(null);
		
		image = anImage;
		scaledImage = image;
		
		if (imageWidth != bounds.width || imageHeight != bounds.height) {
			// scale picture
			scaledImage = image.getScaledInstance(bounds.width, bounds.height, Image.SCALE_DEFAULT);
		} 
//		else
//			image = anImage;

			
//		image = Toolkit.getDefaultToolkit().getImage(imageFile);

//		bounds.width = icon.getIconWidth();
//		bounds.height = icon.getIconHeight();
		this.notifyListeners();		
	}
	
	public void setWidth(int newVal)
    {
		if (newVal == 0 || bounds.height == 0) {
			super.setWidth(newVal);
			return;
		}
		if (scaledImage == null) {
			super.setWidth(newVal); // this will notify
			return;
			
		}
		if (bounds.width == newVal)
			return;
		if (newVal == -1) {
			scaledImage = image;
			super.setWidth(imageWidth);
		} else {
		scaledImage = image.getScaledInstance(newVal,bounds.height, Image.SCALE_DEFAULT);
    
		super.setWidth(newVal); // this will notify
		}

		
    }
	public void setHeight(int newVal)
    {
		if (newVal == 0 || bounds.width == 0) {
			super.setHeight(newVal);
			return;
		}
		if (scaledImage == null) {
			super.setHeight(newVal); // this will notify
			return;
			
		}
		if (bounds.getHeight() == newVal)
			return;
		if (newVal == -1) {
			scaledImage = image;
			super.setHeight(imageHeight);

		} else {
		scaledImage = image.getScaledInstance(bounds.width, newVal, Image.SCALE_DEFAULT);
		
		super.setHeight(newVal); // this will notify
		}

		
    }
	public void copy(ImageModel aReference)
    {
//    	TextModel aTextReference = (TextModel) aReference;
    	setImageFileName(aReference.getImageFileName());
		super.copy(aReference);
    }

}
