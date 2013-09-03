package newgame;

import jgame.*;
import java.awt.Cursor;
import jgame.JGColor;
import jgame.platform.*;
import newgame.Hero;
import newgame.Enemy;

public class Game extends StdGame {
	// Establish a virtual play field that is 100 pixels by 100 pixels. All
	// output in this play field is scaled to 800 by 600 pixels when the game
	// is run as an application. When the game is run as an applet, the output
	// is scaled to whatever window size is specified by the <applet> tag's
	// width and height parameters.

	final static int WIDTH = 800;
	final static int HEIGHT = 593;
	
	Hero hero = null;
	Enemy enemy = null;
	Block block = null;


	public Game() {
		initEngineApplet();
	}

	public Game(int width, int height) {
		initEngine(WIDTH, HEIGHT);
		setPFSize(50, 37);
		setViewOffset(1, 1, true);
		//setPFWrap(true, true, 10, 10);
	}

	@Override
	public void initCanvas() {
		JGColor red = new JGColor(255, 0, 0);
		JGColor black = new JGColor(0, 0, 0);
		setCanvasSettings(50,37,16,16,red,black,null);
	}

	public void initGame() {
		setMedia();
		initMap();
		setMouseCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		if (isMidlet()) {
			setFrameRate(20, 1);
			setGameSpeed(2.0);
		} else {
			setFrameRate(45, 1);
		}
		setHighscores(10, new Highscore(0, "nobody"), 15);
	}
	
	public void setMedia() {
		defineMedia("mygame.tbl");
		defineMedia("outdoors.tbl");
		defineMedia("character.tbl");
		defineMedia("game.tbl");
		setBGImage("grass1");
		defineMedia("sword.tbl");
		defineMedia("boulder.tbl");
	}
	
	public void initMap() {
		//Setting up background
		for (int i=0; i<36; i++){
			setTile(15, i, "p2");
			setTile(16, i, "p1");
			setTile(17, i, "p1");
			setTile(18, i, "p3");
		}
		for (int i=18; i<pfWidth(); i++){
			setTile(i+1, 24, "p4");
			setTile(i, 25, "p1");
			setTile(i, 26, "p1");
			setTile(i+1, 27, "p5");
		}
		setTile(18, 24, "p1");	
		setTile(18, 27, "p1");
		
		for (int i=18; i<pfWidth(); i++){
			setTile(i+1, 32, "p4");
			setTile(i, 33, "p1");
			setTile(i, 34, "p1");
			setTile(i, 35, "p5");
		}


		setTile(18, 32, "p1");
		setTile(15, 34, "p2");
		setTile(16, 34, "p1");
		setTile(17, 34, "p1");
		setTile(15, 35, "p7");
		setTile(16, 35, "p5");
		setTile(17, 35, "p5");
		
		for (int i=0; i<15; i++){
			setTile(i, 24, "p4");
			setTile(i, 25, "p1");
			setTile(i, 26, "p1");
			setTile(i, 27, "p5");
		}
		setTile(15, 24, "p1");
		setTile(15, 25, "p1");
		setTile(15, 26, "p1");
		setTile(15, 27, "p1");
		
		//Building
		setTile(20, 10, "b1");
		setTile(21, 10, "b2");
		setTile(22, 10, "b3");
		setTile(23, 10, "b4");
		
		setTile(20, 11, "b5");
		setTile(21, 11, "b6");
		setTile(22, 11, "b7");
		setTile(23, 11, "b8");
		
		setTile(20, 12, "b9");
		setTile(21, 12, "b10");
		setTile(22, 12, "b11");
		setTile(23, 12, "b12");
	}

	public void initNewLife() {
		removeObjects(null,0);
		enemy = new Enemy(4, 380, 2, 'x', "ewalkb", 15, this);
		hero = new Hero(pfWidth()/2,pfHeight()-50,5, this, this);
		block = new Block(200, 100, "boulder1", this, hero, "boulder1");
		
	}
	
	public void startGameOver() {
		removeObjects(null,0);
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCollision(2,1); // enemies hit player
		checkCollision(4,2); // bullets hit enemies
		//if (gametime>=500 && countObjects("enemy",0)==0) levelDone();
		checkPosition();
	}
	
	public void checkPosition() {
		if (!hero.isOnPF(-10, -10) && hero.orientation==9) {
			hero.setPos(pfWidth()-50, hero.getLastY());
			fillBG("pa");
//			for (int i=0; i<pfWidth(); i++){
//				setTile(i, 24, "p4");
//				setTile(i, 25, "p1");
//				setTile(i, 26, "p1");
//				setTile(i, 27, "p5");
//			}
			initMap();
			enemy = new Enemy(35, 380, .4, 'y', "ewalkr", 15, this);
		}
		else if (!hero.isOnPF(-10, -10) && hero.orientation==3) {
			hero.setPos(0, hero.getLastY());
			fillBG("pa");
			for (int i=0; i<pfWidth(); i++){
				setTile(i, 24, "p4");
				setTile(i, 25, "p1");
				setTile(i, 26, "p1");
				setTile(i, 27, "p5");
			}
		}
		else if (!hero.isOnPF(-10, -10) && hero.orientation==12) {
			hero.setPos(hero.getLastX(), pfHeight()-50);
			fillBG("pa");
			for (int i=0; i<pfWidth(); i++){
				setTile(i, 24, "p4");
				setTile(i, 25, "p1");
				setTile(i, 26, "p1");
				setTile(i, 27, "p5");
			}
		}
		else if (!hero.isOnPF(-10, -10) && hero.orientation==6){
			hero.setPos(hero.getLastX(), 0);
			fillBG("pa");
			for (int i=0; i<pfWidth(); i++){
				setTile(i, 24, "p4");
				setTile(i, 25, "p1");
				setTile(i, 26, "p1");
				setTile(i, 27, "p5");
			}
		}
	}
	
	public void incrementLevel() {
		score += 50;
		if (level<7) level++;
		stage++;
	}
	
	JGFont scoring_font = new JGFont("Arial",0,8);
	
	
	public static void main(String[] args) {
		// Run the game at a window size of 800 by 600 pixels.

		new Game(800, 593);
	}

}