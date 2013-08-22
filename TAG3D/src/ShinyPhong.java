/**
 * A Phong material with a mirror reflection.
 *
 */
public class ShinyPhong extends Phong {
	/**
	 * Reflected color.
	 */
	public Color				reflection;

	/**
	 * Evaluate material mirror reflection.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public Color computeReflection(Vec3 N, Vec3 I) {
        throw new NotImplementedException();
	}

	/**
	 * True if this material has mirror reflections.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public boolean hasReflection(Vec3 N, Vec3 I) {
        throw new NotImplementedException();
	}
}
