package shapes;

import java.awt.Font;

public class GlobalShapeData {
	 static Integer fontSize = null;
	 static Font font = null;

	public static Font getFont() {
		return font;
	}

	public static void setFont(Font font) {
		GlobalShapeData.font = font;
	}

	public static Integer getFontSize() {
		return fontSize;
	}

	public static void setFontSize(Integer fontSize) {
		GlobalShapeData.fontSize = fontSize;
	}

}
