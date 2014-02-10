package shapes;


public class ARectangleDiagonalPoints implements RectangleDiagonalPoints {
	int upperLeftX, upperLeftY, lowerRightX, lowerRightY;

	public ARectangleDiagonalPoints(int upperLeft, int upperLeftY, int lowerRightX,
			int lowerRightY) {
		super();
		this.upperLeftX = upperLeft;
		this.upperLeftY = upperLeftY;
		this.lowerRightX = lowerRightX;
		this.lowerRightY = lowerRightY;
	}
	
	public ARectangleDiagonalPoints(RectangleDiagonalPoints other) {
		super();
		if (other == null)
			return;
		this.upperLeftX = other.getUpperLeftX();
		this.upperLeftY = other.getUpperLeftY();
		this.lowerRightX = other.getLowerRightX();
		this.lowerRightY = other.getLowerRightY();
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#getUpperLeft()
	 */
	public int getUpperLeftX() {
		return upperLeftX;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#setUpperLeft(int)
	 */
	public void setUpperLeftX(int upperLeft) {
		this.upperLeftX = upperLeft;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#getUpperLeftY()
	 */
	public int getUpperLeftY() {
		return upperLeftY;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#setUpperLeftY(int)
	 */
	public void setUpperLeftY(int upperLeftY) {
		this.upperLeftY = upperLeftY;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#getLowerRightX()
	 */
	public int getLowerRightX() {
		return lowerRightX;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#setLowerRightX(int)
	 */
	public void setLowerRightX(int lowerRightX) {
		this.lowerRightX = lowerRightX;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#getLowerRightY()
	 */
	public int getLowerRightY() {
		return lowerRightY;
	}

	/* (non-Javadoc)
	 * @see yaas.layout.nodes.RectangleDiagonalPoints#setLowerRightY(int)
	 */
	public void setLowerRightY(int lowerRightY) {
		this.lowerRightY = lowerRightY;
	}
	

}
