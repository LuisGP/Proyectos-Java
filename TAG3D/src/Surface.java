/**
 * Base class for object surfaces.
 */
public abstract class Surface {
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	abstract boolean intersect(Ray ray, Intersection intersection);
}
