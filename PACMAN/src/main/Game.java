package main;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pacman.Pacman;
import tablero.Casilla;
import tablero.Tablero;
import fantasmas.*;

public class Game implements GameWindowCallback{
	
	public static GameWindow window;
	private Blinky blinky;
	private Pinky pinky;
	private Inky inky;
	private Clide clide;
	private long lastLoopTime;
	private long lastFpsTime;
	private long fps;
	
	private Tablero tablero;
	private Pacman pacman;
	private int level = 1;
	private String msg = new String();
	
	boolean leftPressed = false;
	boolean rightPressed = false;
	boolean upPressed = false;
	boolean downPressed = false;
	
	private boolean ignorar = false; 
	
	private int ultima_pulsada = -1;
	private boolean wait = false;
	private long twait = 0;
	private boolean gameover = false;
	
	private String title = "PacMan - LG: ";
	
	private Fantasma[] fantasmas = new Fantasma[4]; 
	
	public Game() {
		window = new GameWindow();
		
		Texturas.window = window;
		Texturas.init();
		
		window.setResolution(800,600);
		window.setGameWindowCallback(this);
		window.setTitle(title);
		
		window.startRendering();
	}

	public void frameRendering() {
		SystemTimer.sleep(lastLoopTime+10-SystemTimer.getTime());
		
		// work out how long its been since the last update, this
		// will be used to calculate how far the entities should
		// move this loop
		long delta = SystemTimer.getTime() - lastLoopTime;
		lastLoopTime = SystemTimer.getTime();
		lastFpsTime += delta;
		twait += delta;
		fps++;
		
		if(!wait){
			// update our FPS counter if a second has passed
			if (lastFpsTime >= 1000) {
				window.setTitle(title+"Level: "+level+" (FPS: "+fps+")");
				lastFpsTime = 0;
				fps = 0;
			}

			this.moverPacman();
			boolean spacePressed = window.isKeyPressed(KeyEvent.VK_SPACE);
			
			if(spacePressed){
				System.out.println("DEBUGGIN!!!");
			}
		}
		
		/* Pintar, movimientos, y juego */
		tablero.pintar();
		long miedo = 0;
		for(int i = 0; i < 4; i++){
			if(fantasmas[i].getTiempo() < System.currentTimeMillis()){
				fantasmas[i].salir();
			}
			if(fantasmas[i] != null){
				if(!wait){
					fantasmas[i].siguienteMovimiento(tablero);
					//System.out.println(fantasmas[i].getIa().toString());
				}
				fantasmas[i].pintar();
				long m = fantasmas[i].comprobarMiedo();
				miedo = Math.max(miedo,m);
				if(pacman.colisionPersonaje(fantasmas[i], tablero)){
					if(fantasmas[i].estado == Fantasma.MIEDO){
						fantasmas[i].comido();
						pacman.puntos += 500;
					}else if(fantasmas[i].estado == Fantasma.NORMAL){
						twait = 0;
						wait = true;
						muerte();
					}
				}
			}
		}
		pacman.pintar();
		
		String puntos = "Puntos "+pacman.puntos;
		window.getDrawGraphics().setColor(Color.WHITE);
		window.getDrawGraphics().drawString(puntos, 10, 10);
		window.getDrawGraphics().drawString(msg, 50, 30);
		if(miedo > 0){
			String miedo_str = "Restante: "+miedo/1000;
			window.getDrawGraphics().drawString(miedo_str, 20, 50);
		}
		msg = "";
		for(int i = 0; i < pacman.vidas; i++){
			Texturas.abreIz.pintar((40*(i%2))+680, 10+40*(i/2));
		}
		
		if(tablero.nbolas == 0){
			twait = 0;
			wait = true;
			this.level++;
			this.initialise();
		}
		
		if(wait){
			msg = new String((new Integer(3 - (int)(twait/1000))).toString());
			if(gameover)
				msg += " GAMEOVER";
			if(twait > 3000){
				wait = false;
				if(gameover){
					msg = "";
					this.level = 1;
					this.gameover();
				}
			}
		}
		
	}

	private void gameover() {
		this.pacman = null;
		initialise();
		
	}

	public void initialise() {		
		blinky = new Blinky(level);
		inky = new Inky(level);
		pinky = new Pinky(level);
		clide = new Clide(level);
		if(pacman == null)
			pacman = new Pacman(0,0);
		
		if(gameover){
			pacman.puntos = 0;
			gameover = false;
			msg = "";
		}
		blinky.situar(13, 10);
		inky.situar(11, 13);
		pinky.situar(13, 14);
		clide.situar(15, 12);
		pacman.situar(13, 22);
		
		/*blinky.setIa(new Aleatorio());
		inky.setIa(new Aleatorio());
		pinky.setIa(new Aleatorio());
		clide.setIa(new Aleatorio());*/
		
		tablero = new Tablero();
		
		fantasmas[0] = blinky;
		fantasmas[0].setTiempo(Long.MAX_VALUE);
		fantasmas[1] = inky;
		fantasmas[1].setTiempo(System.currentTimeMillis()+3000);
		fantasmas[2] = pinky;
		fantasmas[2].setTiempo(System.currentTimeMillis()+6000);
		fantasmas[3] = clide;
		fantasmas[3].setTiempo(System.currentTimeMillis()+9000);
		
		fantasmas[1].setEstado(Fantasma.DENTRO);
		fantasmas[2].setEstado(Fantasma.DENTRO);
		fantasmas[3].setEstado(Fantasma.DENTRO);
		
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
		
		tablero.fantasmas = fantasmas;
		tablero.pacman = pacman;
	}

	public void muerte(){
		blinky.situar(13, 10);
		inky.situar(11, 13);
		pinky.situar(13, 14);
		clide.situar(15, 12);
		pacman.situar(13, 22);
		
		fantasmas[0].setTiempo(Long.MAX_VALUE);
		fantasmas[1].setTiempo(System.currentTimeMillis()+3000);
		fantasmas[2].setTiempo(System.currentTimeMillis()+6000);
		fantasmas[3].setTiempo(System.currentTimeMillis()+9000);
		fantasmas[1].setEstado(Fantasma.DENTRO);
		fantasmas[2].setEstado(Fantasma.DENTRO);
		fantasmas[3].setEstado(Fantasma.DENTRO);
		fantasmas[0].setEstado(Fantasma.NORMAL);
		
		pacman.vidas--;
		if(pacman.vidas < 0){
			msg = "GAME OVER";
			gameover = true;
		}
		
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
	}
	
	private void moverPacman(){
		/* Movimientos */
		boolean auxleftPressed = window.isKeyPressed(KeyEvent.VK_LEFT);
		boolean auxrightPressed = window.isKeyPressed(KeyEvent.VK_RIGHT);
		boolean auxupPressed = window.isKeyPressed(KeyEvent.VK_UP);
		boolean auxdownPressed = window.isKeyPressed(KeyEvent.VK_DOWN);

		// Esperamos medio segundo entre 2 pulsaciones
		//if(pulsacion < SystemTimer.getTime()){
		if(!ignorar){
			if(auxleftPressed && !auxrightPressed && !auxupPressed && !auxdownPressed){
				leftPressed = true;
				rightPressed = false;
				upPressed = false;
				downPressed = false;
			}else
				if(!auxleftPressed && auxrightPressed && !auxupPressed && !auxdownPressed){
					leftPressed = false;
					rightPressed = true;
					upPressed = false;
					downPressed = false;
				}
			if(auxupPressed && !auxdownPressed && !auxrightPressed && !auxleftPressed){
				upPressed = true;
				downPressed = false;
				leftPressed = false;
				rightPressed = false;
			}else
				if(auxdownPressed && !auxupPressed && !auxrightPressed && !auxleftPressed){
					downPressed = true;
					upPressed = false;
					leftPressed = false;
					rightPressed = false;
				}
		}

		Casilla actual = tablero.miCasilla(pacman);
		
		if(actual.getVecino(0) == null || !actual.getVecino(0).isAccesible()){
			leftPressed = false;
		}
		if(actual.getVecino(1) == null || !actual.getVecino(1).isAccesible()){
			rightPressed = false;
		}
		if(actual.getVecino(2) == null || !actual.getVecino(2).isAccesible()){
			upPressed = false;
		}
		if(actual.getVecino(3) == null || !actual.getVecino(3).isAccesible()){
			downPressed = false;
		}
		
		ignorar = false;
		// A no ser que hayamos movido ^^
		if ((leftPressed) && (!rightPressed)) {
			if(pacman.setHorizontalMovement(tablero,-1)){
				ultima_pulsada = 0;
			}else{
				leftPressed = false;
				if(ultima_pulsada != 0)
					ignorar = true;
			}
		} else if ((rightPressed) && (!leftPressed)) {
			if(pacman.setHorizontalMovement(tablero,+1)){
				ultima_pulsada = 1;
			}else{
				rightPressed = false;
				if(ultima_pulsada != 1)
					ignorar = true;
			}
		}

		if ((upPressed) && (!downPressed)) {
			if(pacman.setVerticalMovement(tablero,-1)){
				ultima_pulsada = 2;
			}else{
				upPressed = false;
				if(ultima_pulsada != 2)
					ignorar = true;
			}
		} else if ((downPressed) && (!upPressed)) {
			if(pacman.setVerticalMovement(tablero,+1)){
				ultima_pulsada = 3;
			}else{
				downPressed = false;
				if(ultima_pulsada != 3)
					ignorar = true;
			}
		}
		
		if(!(downPressed || upPressed || rightPressed || leftPressed)){
			switch(ultima_pulsada){
			case 0:
				pacman.setHorizontalMovement(tablero,-1);
				leftPressed = true;
				break;
			case 1:
				pacman.setHorizontalMovement(tablero,+1);
				rightPressed = true;
				break;
			case 2:
				pacman.setVerticalMovement(tablero,-1);
				upPressed = true;
				break;
			case 3:
				pacman.setVerticalMovement(tablero,+1);
				downPressed = true;
				break;
			default:
				break;
			}
		}
	}
	
	public void windowClosed() {
		System.exit(0);
	}
	
	public static void main(String args[]){
		new Game();
	}

}
