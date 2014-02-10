package shapes;


public interface ArcShape extends FlexibleShape {

	int getStartAngle();

	int getEndAngle();

	void setStartAngle(int newVal);

	void setEndAngle(int newVal);

}
