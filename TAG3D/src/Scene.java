/**
 * Raytracing scene.
 */
public class Scene {
	/**
	 * Camera.
	 */
	public Camera				camera;
	/**
	 * Object surfaces.
	 */
    public Surface[]			surfaces;
	/**
	 * Lights.
	 */
    public Light[]				lights;
	
	/**
	 * Default constructor.
	 * Scene is initialized in the Loader.
	 */
	public Scene() {
		camera = new Camera();
		surfaces = new Surface[0];
		lights = new Light[0];
	}
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	boolean intersect(Ray ray, Intersection intersection) {
        //throw new NotImplementedException();
		for(int i = 0; i < surfaces.length; i++){
			if(surfaces[i].intersect(ray, intersection))
				return true;
		}
		return false;
	}
}
