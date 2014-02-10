package shapes;


public interface CurveShape extends FlexibleShape {

	int getControlX();

	int getControlY();

	void setControlX(int newVal);

	void setControlY(int newVal);

	int getControlY2();

	void setControlX2(int newVal);

	void setControlY2(int newVal);

}
