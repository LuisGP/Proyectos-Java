/**
 * Represents a pin-hole camera.
 * 
 * All coordinates are given in world space.
 * Note that for simplicity, this is a left-handed coordinate frame.
 * 
 */
public class Camera {
	/**
	 * Origin.
	 */
	public Vec3				origin;
	/**
	 * Right vector.
	 */
	public Vec3				x;
	/**
	 * Up vector.
	 */
	public Vec3				y;
	/**
	 * View vector.
	 */
	public Vec3				z;
	
	/**
	 * Field of view along the right(x) axis.
	 */
	public double				xfov;
	
    /**
     * Field of view along the right(y) axis.
     */
    public double               yfov;
    
	/**
	 * Width of the image in pixels.
	 */
	public int					xResolution;
	/**
	 * Height of the image in pixels.
	 */
	public int					yResolution;
	
	/**
	 * Generate a ray through the image location.
	 * @param u Image x location in [0,1]
	 * @param v Image y location in [0,1]
	 * @return Normalized view ray
	 */
	public Ray generateRay(double u, double v) {
        //throw new NotImplementedException();
		Vec3 vec = imagePlanePoint(u, v);
		Ray r = new Ray(this.origin,vec);
		return r;
	}
	
	/**
	 * Compute the image plane point in world coordinates.
	 * @param u Image x location in [0,1]
	 * @param v Image y location in [0,1]
	 * @return Image plane point.
	 */
	public Vec3 imagePlanePoint(double u, double v) {
        double dis = z.z - origin.z;
		double hsize = 2 * dis * Math.tan(Math.toDegrees(xfov) / 2);
		double vsize = 2 * dis * Math.tan(Math.toDegrees(yfov) / 2);
		double hpixel = hsize / xResolution;
		double vpixel = vsize / yResolution;
		
		Vec3 center = origin.add(z.scale(dis));
		
		double vx = center.x + (u*hsize) - hsize/2;
		double vy = center.y + (v*vsize) - vsize/2;
				
		Vec3 vec = new Vec3(vx, vy, center.z);
		
		//System.out.println("CENTER: "+center+" VEC: "+vec);
		
		return vec;
	}
    
    /**
     * Initialize frame from ZY. Used by parser.
     */
    public void initFromParser() {
        // normalize forward
        z.setToNormalize();
        
        // orthonormalize the up vector
        y = y.sub(z.scale(y.dot(z))).normalize();
        
        // right is just the cross product
        x = new Vec3();
        x = z.cross(y);
        
        yfov = Math.toRadians(yfov);
        xfov = yfov * (double)xResolution / (double)yResolution;
    }
}
