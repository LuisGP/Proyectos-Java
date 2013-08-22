/**
 * Point light source
 *
 */
public class PointLight extends Light {
	/**
	 * Light position.
	 */
	public Vec3						position;
	
	/**
	 * Default constructor.
	 *
	 */
	public PointLight() {
		super();
		position = new Vec3();
	}

	/**
	 * Compute light direction.
	 * @param surfacePoint Surface position.
	 */
	public Vec3 computeLightDirection(Vec3 surfacePoint) {
        throw new NotImplementedException();
	}

	/**
	 * Compute light internsity.
	 * @param surfacePoint Surface position.
	 */
	public Color computeLightIntensity(Vec3 surfacePoint) {
        throw new NotImplementedException();
	}

	/**
	 * Compute shadow distance, i.e. max distance (in world space) a ray should 
	 * travel when checking for shadows. 
	 * @param surfacePoint Surface position.
	 */
	public double computeShadowDistance(Vec3 surfacePoint) {
        throw new NotImplementedException();
	}
}
