
/**
 * Main command-line entry point.
 * Parses commands, load scenes, renders them and saves results to image files.
 */
public class Main {	
	/**
	 * Main entry point.
	 */
	public static void main(String args[]) {
		if(args.length < 1) {
			System.out.println("usage: Main sceneFilenames");
			return;
		}

        for (int i = 0; i < args.length; i++) {
            String filename = args[i];
            System.out.println("Reading scene: " + filename);
            Scene scene = loadScene(filename);

            RayTracer rayTracer = new RayTracer(scene);
            System.out.println("Rendering...");
            ColorImage image = rayTracer.render();
            ColorImage imageSuperSampled = rayTracer.renderSuperSampled(3);
            
            String imageName = filename + ".png";
            System.out.println("Saving image: " + imageName);
            saveImage(imageName,image);
            String imageSupersampledName = filename + "supersampled.png";
            System.out.println("Saving image: " + imageSupersampledName);
            saveImage(imageSupersampledName,imageSuperSampled);            
        }
	}

	/**
	 * Save an image to disk.
	 */
	protected static void saveImage(String filename, ColorImage image) {
		try {
			FileFormat.saveImage(filename, image);
		} catch(Exception e) {
			System.out.println("Problem saving image: " + filename);
			System.out.println(e);
            System.exit(1);
		}
	}
	
	/**
	 * Load a scene from file.
	 */
	protected static Scene loadScene(String filename) {
		Scene scene = null;
		try {
            FileFormat p = new FileFormat();
            scene = p.parseXMLScene(filename);
		} catch(Exception e) {
			System.out.println("Problem parsing file: " + filename);
            System.out.println(e);
			System.exit(1);
		}
		return scene;
	}
	
}
