/**
 * Color image represented as coor matrix.
 * 
 */
public class ColorImage {
	/**
	 * Colors
	 */
	private Color[] 			colors;
	
	/**
	 * Image width.
	 */
	public int					xResolution;
	/**
	 * Image height.
	 */
	public int					yResolution;
	
	/**
	 * Create an empty image of the given resolution.
	 */
	public ColorImage(int nxResolution, int nyResolution) {
		xResolution = nxResolution;
		yResolution = nyResolution;
		colors = new Color[xResolution*yResolution];
		for(int x = 0; x < xResolution; x ++) {
			for (int y = 0; y < yResolution; y++) {
				colors[idx(x,y)] = new Color(0,0,0);
			}
		}
	}
	
	/**
	 * Get coor at coordinate (x,y).
	 */
    public Color getColor(int x, int y) {
		return colors[idx(x,y)];
	}
	
	/**
	 * Set coor at coordinate (x,y).
	 */
    public void setColor(int x, int y, Color c) {
		colors[idx(x,y)].set(c);
	}
    
    /**
     * Finds the image index
     */
    private int idx(int x, int y) {
        return y*xResolution+x;
    }
}
