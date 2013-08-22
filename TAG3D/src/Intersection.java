/**
 * Stores data of a ray intersection.
 */

public class Intersection {
	/**
	 * Intersection point.
	 */
	public Vec3				position;
	/**
	 * Normal at intersection point.
	 */
	public Vec3				normal;
	/**
	 * Material at intersection point.
	 */
	Material				material;
	/**
	 * Ray parameter (distance along the ray if ray direction is normalized).
	 */
	public double			distance;
	
	/**
	 * Default contructor.
	 */
	public Intersection() {
		position = new Vec3();
		normal = new Vec3();
		material = null;
		distance = 0;
	}

	/**
	 * Set the intersection record to no intersection.
	 */
	public void clear() {
		normal = new Vec3();
		position = new Vec3();
		material = null;
		distance = Double.MAX_VALUE;
	}
	
	/**
	 * Assign the internal values to the one given in the parameters. 
	 */
	public void set(Intersection i) {
		normal = new Vec3(i.normal);
		position = new Vec3(i.position);
		distance = i.distance;
		material = i.material;
	}
}
