/**
 * Base class for light source.
 */
public abstract class Light {
	/**
	 * Light intensity.
	 */
	public Color				intensity;
	
	/**
	 * Default connstructor.
	 */
	public Light() {
		intensity = new Color();
	}

	/**
	 * Compute light direction.
	 * @param surfacePoint Surface position.
	 */
	public abstract Vec3 computeLightDirection(Vec3 surfacePoint);
	/**
	 * Compute light internsity.
	 * @param surfacePoint Surface position.
	 */
	public abstract Color computeLightIntensity(Vec3 surfacePoint);
	/**
	 * Compute shadow distance, i.e. max distance (in world space) a ray should 
	 * travel when checking for shadows. 
	 * @param surfacePoint Surface position.
	 */
	public abstract double computeShadowDistance(Vec3 surfacePoint);
}
