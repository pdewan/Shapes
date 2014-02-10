package shapes;

import java.text.AttributedString;


public interface TextShape {

	String getText();
	void setText(String newValue);
	AttributedString getAttributedString();
	void setAttributedString(AttributedString newVal);

}
