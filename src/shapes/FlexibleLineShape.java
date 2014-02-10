package shapes;

public interface FlexibleLineShape extends FlexibleShape, Rotatable, RotatableBoundedShape{
	public static final double ROTATION_UNIT = Math.PI / 12;

	public double getLength ();
	public double getRelativeAngle ();
	public void setLength (double newVal);
	public void setRelativeAngle (double newVal );
	public void rotate(int units) ;
	public void rotate(double angle);



}
