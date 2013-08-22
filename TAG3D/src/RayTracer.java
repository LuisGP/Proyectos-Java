/**
 * Basci raytracer.
 */
public class RayTracer {
	/**
	 * Scene.
	 */
	public Scene 			scene;
	
	/**
	 * Max recursion for reflection and refraction.
	 */
	public int				maxRecursion;
	
	/**
	 * Construct a raytracer for a given scene.
	 */	
	public RayTracer(Scene nScene) {
		scene = nScene;
		maxRecursion = 1;
	}
	
	/**
	 * Reaytrace the scene with one sample per pixel.
	 */
	public ColorImage render() {
        //throw new NotImplementedException();
		ColorImage colorimage;// = new ColorImage(320,240);
		int x, y;
		double u, v;
		Ray ray;
		Intersection intersection = new Intersection();
		x = scene.camera.xResolution;
		y = scene.camera.yResolution;
		colorimage = new ColorImage(x,y);
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++){
				// Para cada punto, generar rayo desde la camara a traves del punto
				// y comprobar la colision en la escena
				u = (i*scene.camera.xfov)/x;
				v = (j*scene.camera.yfov)/y;
				ray = scene.camera.generateRay(u, v);
				if(scene.intersect(ray, intersection))
					colorimage.setColor(i, j, new Color(1,1,1));
				else
					colorimage.setColor(i, j, new Color(0,0,0));
			}
		
		return colorimage;
	}
	
	/**
	 * Raytrace the scene with nsamples^2 samples oper pixel.
	 */
	public ColorImage renderSuperSampled(int nsamples) {
        //throw new NotImplementedException();
		return this.render();
	}
	
	/**
	 * Compute the visible color along the given ray.
	 * Should check to make sure rayDepth is less or equal to the given maximum.
	 */
	public Color computeColor(Ray ray) {
        throw new NotImplementedException();
	}
	
	/**
	 * Compute the color of a gien intersection point.
	 * Recurse if necessary for reflection.
	 */
	public Color computeIllumination(Ray ray, Intersection intersection) {
        throw new NotImplementedException();
	}
	
	/**
	 * Check if a point P is in shadow given a light.
	 * Returns 0 or 1.
	 */
	public double computeShadow(Vec3 P, Light light) {
        throw new NotImplementedException();
	}	
}
