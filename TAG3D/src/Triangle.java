/**
 * Triangle.
 */
public class Triangle extends Surface {
	/**
	 * Material.
	 */
	public Material				material;
	/**
	 * Vertex 0.
	 */
	public Vec3					v0;
	/**
	 * Vertex 1.
	 */
	public Vec3					v1;
	/**
	 * Vertex 2.
	 */
	public Vec3					v2;
	/**
	 * Normal.
	 */
	public Vec3					normal;
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	public boolean intersect(Ray ray, Intersection intersection) {
        throw new NotImplementedException();		
	}

	/**
	 * Set the normal as orthogonal to the triangle plane.
	 * Use by the parser.
	 */
	public void initFromParser() {
		normal = (v1.sub(v0)).cross(v2.sub(v0)).normalize();
	}
	
}
