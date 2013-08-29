package newgame;

import jgame.*;

import java.awt.Cursor;
import java.lang.Object;

import mygame.MyGame.Player;
import jgame.JGColor;
import jgame.platform.*;

public class Game extends StdGame {
	// Establish a virtual play field that is 100 pixels by 100 pixels. All
	// output in this play field is scaled to 800 by 600 pixels when the game
	// is run as an application. When the game is run as an applet, the output
	// is scaled to whatever window size is specified by the <applet> tag's
	// width and height parameters.

	final static int WIDTH = 800;
	final static int HEIGHT = 600;

	public Game() {
		initEngineApplet();
	}

	public Game(int width, int height) {
		initEngine(WIDTH, HEIGHT);
	}

	@Override
	public void initCanvas() {
		JGColor red = new JGColor(255, 0, 0);
		JGColor black = new JGColor(0, 0, 0);
		//setCanvasSettings(1, 1, 800, 600, red, black, null);
		setCanvasSettings(50,38,16,16,red,black,null);
	}

	public void initGame() {
		defineMedia("mygame.tbl");
		defineMedia("outdoors.tbl");
		defineMedia("character.tbl");
		defineMedia("game.tbl");
		//fillBG("grass1");
		setBGImage("grass1");
		setMouseCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		if (isMidlet()) {
			setFrameRate(20, 1);
			setGameSpeed(2.0);
		} else {
			setFrameRate(45, 1);
		}
		setHighscores(10, new Highscore(0, "nobody"), 15);

	}

	public void initNewLife() {
		removeObjects(null,0);
		new Hero(pfWidth()/2,pfHeight()-32,5);
		new Enemy(4, 400, .4);
		
	}
	
	public void startGameOver() {
		removeObjects(null,0);
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCollision(2,1); // enemies hit player
		checkCollision(4,2); // bullets hit enemies
		if (gametime>=500 && countObjects("enemy",0)==0) levelDone();
	}
	public void incrementLevel() {
		score += 50;
		if (level<7) level++;
		stage++;
	}
	
	JGFont scoring_font = new JGFont("Arial",0,8);
	
	
	public static void main(String[] args) {
		// Run the game at a window size of 800 by 600 pixels.

		new Game(800, 600);
	}
	
	//These are the enemies
	public class Enemy extends JGObject {
		double timer=0;
		public Enemy(double x, double y, double speed) {
			super("enemy",true,x,y,
					2, "ctowawalk6",
					speed, speed, -2 );
		}
		public void move() {
			if(gametime < 800){
				x += 0.5;
			}
			y = 400;
			if (y>pfHeight()) y = -8;
		}
		public void hit(JGObject o) {
	        new JGObject ("explo", true, x, y, 0, "explo", 0, 0, 32);
			remove();
			o.remove();
			score += 5;
		}
	}
	
	
	public class Hero extends JGObject {
		public Hero(double x,double y,double speed) {
			super("hero",true,x,y,1,"mstand4", 0,0,speed,speed,-1);
		}
		public void move() {
			boolean flag=false;
			setDir(0,0);
			yspeed=2.5;
			xspeed=2.5;
			if (getKey(key_left)  && x > xspeed){
				setGraphic("mstand2");
				xdir=-1;
			}
			if (getKey(key_right) && x < pfWidth()-32-yspeed){
				setGraphic("mstand3");
				xdir=1;
			}
			if (getKey(key_up) && y > yspeed){
				setGraphic("walkf");
				//new JGObject ("walkf", true, x, y, 0, "walkf", 0, 0, 2);
				ydir=-1;
				flag=true;
			}
			if (getKey(key_down) && y < pfHeight()-32-xspeed){
				setGraphic("mstand1");
				//new JGObject ("walkb", true, x, y, 0, "walkb", 0, 0, 2);
				ydir=1;
			}
			if (getKey(key_fire) && countObjects("bullet",0) < 50) {
				//new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
				clearKey(key_fire);
			}
			if (getKey(key_firedown) && countObjects("bullet",0) < 2) {
				//new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
				clearKey(key_fire);
			}
			if (!flag){
				setGraphic("mstand4");
			}
		}
		public void hit(JGObject obj) {
			if (and(obj.colid,2)) lifeLost();
			else {
				score += 5;
				obj.remove();
			}
		}
	}
}