package fantasmas;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

import main.GameWindow;
import main.Java2DSprite;
import main.Sprite;

public class Texturas {
	/* Fantasmas */
	public static Sprite blinkyArriba;
	public static Sprite blinkyAbajo;
	public static Sprite blinkyIzquierda;
	public static Sprite blinkyDerecha;
	public static Sprite pinkyArriba;
	public static Sprite pinkyAbajo;
	public static Sprite pinkyIzquierda;
	public static Sprite pinkyDerecha;
	public static Sprite inkyArriba;
	public static Sprite inkyAbajo;
	public static Sprite inkyIzquierda;
	public static Sprite inkyDerecha;
	public static Sprite clideArriba;
	public static Sprite clideAbajo;
	public static Sprite clideIzquierda;
	public static Sprite clideDerecha;
	public static Sprite miedoArriba;
	public static Sprite miedoAbajo;
	public static Sprite miedoIzquierda;
	public static Sprite miedoDerecha;
	public static Sprite comido;
	
	/* Paredes */
	public static Sprite horizontal;
	public static Sprite vertical;
	public static Sprite esquina1;
	public static Sprite esquina2;
	public static Sprite esquina3;
	public static Sprite esquina4;
	
	/* PacMan */
	public static Sprite abreAr;
	public static Sprite abreAb;
	public static Sprite abreIz;
	public static Sprite abreDe;
	public static Sprite cierraAr;
	public static Sprite cierraAb;
	public static Sprite cierraIz;
	public static Sprite cierraDe;
	public static Sprite espaldas;
	
	public static Sprite[] animacion = new Sprite[14];
	
	/* Puntos */
	public static Sprite punto;
	public static Sprite puntoBonus;
	
	public static GameWindow window;
	
	public static void init(){
		String nombre = new String();
		DecimalFormat nf = new DecimalFormat("0000");
		for(int i = 0; i < 14; i++){
			nombre = "Pacman "+nf.format(new Integer(i))+".png";
			animacion[i] = load("media"+File.separator+"pacman"+File.separator+nombre);
		}
		blinkyAbajo = load("media"+File.separator+"rojo_abajo.png");
		blinkyArriba = load("media"+File.separator+"rojo_arriba.png");
		blinkyIzquierda = load("media"+File.separator+"rojo_izquierda.png");
		blinkyDerecha = load("media"+File.separator+"rojo_derecha.PNG");
		
		pinkyAbajo = load("media"+File.separator+"rosa_abajo.png");
		pinkyArriba = load("media"+File.separator+"rosa_arriba.png");
		pinkyIzquierda = load("media"+File.separator+"rosa_izquierda.png");
		pinkyDerecha = load("media"+File.separator+"rosa_derecha.png");
		
		inkyAbajo = load("media"+File.separator+"cian_abajo.png");
		inkyArriba = load("media"+File.separator+"cian_arriba.png");
		inkyIzquierda = load("media"+File.separator+"cian_izquierda.png");
		inkyDerecha = load("media"+File.separator+"cian_derecha.png");
		
		clideAbajo = load("media"+File.separator+"naranja_abajo.png");
		clideArriba = load("media"+File.separator+"naranja_arriba.png");
		clideIzquierda = load("media"+File.separator+"naranja_izquierda.png");
		clideDerecha = load("media"+File.separator+"naranja_derecha.png");
		
		miedoAbajo = load("media"+File.separator+"miedo_abajo.png");
		miedoArriba = load("media"+File.separator+"miedo_arriba.png");
		miedoIzquierda = load("media"+File.separator+"miedo_izquierda.png");
		miedoDerecha = load("media"+File.separator+"miedo_derecha.png");
		comido = load("media"+File.separator+"comido.png");
		
		abreAb = load("media"+File.separator+"pacman_abre_aba.png");
		abreAr = load("media"+File.separator+"pacman_abre_arri.png");
		abreIz = load("media"+File.separator+"pacman_abre_izq.png");
		abreDe = load("media"+File.separator+"pacman_abre_der.png");
		
		cierraAb = load("media"+File.separator+"pacman_cierra_aba.png");
		cierraAr = load("media"+File.separator+"pacman_cierra_arri.png");
		cierraIz = load("media"+File.separator+"pacman_cierra_izq.png");
		cierraDe = load("media"+File.separator+"pacman_cierra_der.png");
		
		espaldas = load("media"+File.separator+"pacman_atras.png");
		
		horizontal = load("media"+File.separator+"horizontal.bmp");
		vertical = load("media"+File.separator+"vertical.bmp");
		esquina1 = load("media"+File.separator+"esquina1.bmp");
		esquina2 = load("media"+File.separator+"esquina2.bmp");
		esquina3 = load("media"+File.separator+"esquina3.bmp");
		esquina4 = load("media"+File.separator+"esquina4.bmp");
		punto = load("media"+File.separator+"punto.bmp");
		puntoBonus = load("media"+File.separator+"punto_bonus.bmp");
	}
	
	private static Sprite load(String ref){
		BufferedImage sourceImage = null;

		try {
			File file = new File(ref);
			//URL url = new URL(ref);
			URL url = file.toURL();

			if (url == null) {
				System.err.println("Can't find ref: "+ref);
			}

			// use ImageIO to read the image in
			sourceImage = ImageIO.read(url);
			//sourceImage = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Failed to load: "+ref);
		}

		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);

		// create a sprite, add it the cache then return it
		Sprite sprite = new Java2DSprite(window,image);

		return sprite;
	}
}
