/**
 * Sphere.
 */
public class Sphere extends Surface {
	/**
	 * Material.
	 */
	public Material					material;
	/**
	 * Center.
	 */
	public Vec3						position;
	/**
	 * Radius.
	 */
	public double					radius;
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	boolean intersect(Ray ray, Intersection intersection) {
        //throw new NotImplementedException();
		
		// De forma bruta (sin profundidad) y sin interseccion
		
		if(Math.abs(ray.direction.x - position.x) == radius &&
			Math.abs(ray.direction.y - position.y) == radius)
			return true;
		return false;
	}

}
