/**
 * Base class for materials
 *
 */

public abstract class Material {
	/**
	 * Evaluate material for direct lighting.
	 * @param N Surface normal.
	 * @param L Light direction (point towards the light).
	 * @param I View direction  (point towards the surface).
	 */
	public abstract Color computeDirectLighting(Vec3 N, Vec3 L, Vec3 I);
	
	/**
	 * Evaluate material mirror reflection.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public abstract boolean hasReflection(Vec3 N, Vec3 I);
	
	/**
	 * True if this material has mirror reflections.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public abstract Color computeReflection(Vec3 N, Vec3 I);
}