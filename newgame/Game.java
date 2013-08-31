package newgame;

import jgame.*;

import java.awt.Cursor;
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
	
	Hero hero = null;
	Enemy enemy = null;


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
		JGColor green = new JGColor(0, 180, 85);
		//setCanvasSettings(1, 1, 800, 600, red, black, null);
		setCanvasSettings(50,38,16,16,red,green,null);
	}

	public void initGame() {
		defineMedia("mygame.tbl");
		defineMedia("outdoors.tbl");
		defineMedia("character.tbl");
		defineMedia("game.tbl");
		setBGImage("grass1");
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
			setTile(i+1, 34, "p4");
			setTile(i, 35, "p1");
			setTile(i, 36, "p1");
			setTile(i, 37, "p5");
		}


		setTile(18, 34, "p1");
		setTile(15, 36, "p2");
		setTile(16, 36, "p1");
		setTile(17, 36, "p1");
		setTile(15, 37, "p8");
		setTile(16, 37, "p5");
		setTile(17, 37, "p5");
		
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
	}

	public void initNewLife() {
		removeObjects(null,0);
		enemy = new Enemy(4, 380, 2, 'x', "ewalkb", 15);
		hero = new Hero(pfWidth()/2,pfHeight()-50,5);
		
	}
	
	public void startGameOver() {
		removeObjects(null,0);
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCollision(2,1); // enemies hit player
		checkCollision(4,2); // bullets hit enemies
		//if (gametime>=500 && countObjects("enemy",0)==0) levelDone();
//		if (countObjects("enemy",0)==0) levelDone();
		if (hero.x<=3) {
			hero.setPos(pfWidth()-50, hero.getLastY());
			fillBG("pa");
			for (int i=18; i<pfWidth(); i++){
				setTile(i, 24, "p4");
				setTile(i, 25, "p1");
				setTile(i, 26, "p1");
				setTile(i, 27, "p5");
			}
			enemy = new Enemy(35, 380, .4, 'y', "ewalkr", 15);
		}
		else if (hero.x>=770) {
			hero.setPos(0, hero.getLastY());
			fillBG("pa");
			initMap();
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

		new Game(800, 600);
	}
	
	//These are the enemies
	public class Enemy extends JGObject {
		int health = 70;
		String graphic;
		char direction;
		double timer=0;
		public Enemy(double x, double y, double speed, char direction, String graphic, int health) {
			super("enemy",true,x,y,
					2, graphic,
					speed, speed, -2 );
			this.direction = direction;
		}
		
		public void move() {
			double startingX=this.x;
			double startingY=this.y;
			
			if (this.direction=='y') {
				y += 0.5;
				
				y = 0;
				if (y> pfWidth()) {
					y = -8;	
				}
			}
			if (this.direction=='x') {
				x += 0.5;
				
				x = 0;
				if (x > pfWidth()) {
					x = -8;	
				}
			}
			lurk(startingX, startingY);
		}
		
		public void lurk(double startingX, double startingY) {
			if (this.direction=='w') {
				y += -(this.xspeed*2);
				x = 0;
				if (y> pfWidth()) {
					y = -8;	
				}
			}
			if (this.direction=='s') {
				x = 0;
				if (y> pfWidth()) {
					y = -8;	
				}
			}
			if (this.y>=pfHeight()-50) {
				this.direction = 'w';
				y += -(this.yspeed);
				setGraphic("ewalkf");
			}
			
			if (this.y <= 10) {
				this.direction = 's';
				y += (this.xspeed);
				setGraphic("ewalkb");
				
			}
			
		}
		
		public void hit(JGObject o) {
//			System.out.println(this.health);
			this.health += -1;
			if (this.health==0){
		        new JGObject ("explo", true, x, y, 0, "explo", 0, 0, 32);
				remove();
				o.remove();
				score += 5;
			}
		}
	}
	
	
	public class Hero extends JGObject {
		public Hero(double x,double y,double speed) {
			super("hero",true,x,y,1,"mstand4", 0,0,speed,speed,-1);
		}
		String lastGraphic = "mstand4";
		int i=0;
		int orientation = 12; //12-Forward, 3-Right, 6-Down, 9-Left
		public void move() {
			boolean flag=false;
			setDir(0,0);
			yspeed=2.5;
			xspeed=2.5;
			
			//Basic Movement
			if (getKey(KeyLeft)  && x > xspeed){
				setGraphic("walkl");
				lastGraphic="mstand2";
				orientation = 9;
				xdir=-1;
				flag=true;
			}
			if (getKey(KeyRight) && x < pfWidth()-32-yspeed){
				setGraphic("walkr");
				lastGraphic="mstand3";
				orientation = 3;
				xdir=1;
				flag=true;
			}
			if (getKey(KeyUp) && y > yspeed){
				setGraphic("walkf");
				lastGraphic="mstand4";
				orientation = 12;
				ydir=-1;
				flag=true;
			}
			if (getKey(KeyDown) && y < pfHeight()-32-xspeed){
				setGraphic("walkb");
				lastGraphic="mstand1";
				orientation = 6;
				ydir=1;
				flag=true;
			}
			
			//Running
			if (getKey(KeyLeft) && getKey(KeyShift) && x > xspeed) {
				xdir=-2;
				flag=true;
			}
			if (getKey(KeyRight) && getKey(KeyShift) && x < pfWidth()-32-yspeed) {
				xdir=2;
				flag=true;
			}
			if (getKey(KeyUp) && getKey(KeyShift) && y > yspeed) {
				ydir=-2;
				flag=true;
			}
			if (getKey(KeyDown) && getKey(KeyShift) && y < pfHeight()-32-xspeed) {
				ydir=2;
				flag=true;
			}
			
			if (getKey(KeyEnter) && i==0) {
				i=1;
				clearKey(KeyEnter);
			}
			if (getKey(KeyEnter)) {
				i=0;
				clearKey(KeyEnter);
			}
			gun(i);
			machineGun(i);
			
			if (!flag){
				setGraphic(lastGraphic);
			}
		}
		
		public void machineGun(int i) {
			//Machine Gun is fully automatic, you can hold down the fire key.
			if (getKey(key_fire) && countObjects("bullet",0) < 50 && i==1) {
				if (orientation == 9) {
					new JGObject("bullet", true, x, y, 4, "barx", -20, 0, -2);
					new JGObject("bullet", true, x-10, y-3, 4, "barx", -20, 0, -2);
					//clearKey(key_fire);
				}
				if (orientation == 3) {
					new JGObject("bullet", true, x, y, 4, "barx", 20, 0, -2);
					new JGObject("bullet", true, x-8, y-3, 4, "barx", 20, 0, -2);
					//clearKey(key_fire);
				}
				if (orientation == 12) {
					new JGObject("bullet", true, x, y, 4, "bary", 0, -20, -2);
					new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, -20, -2);
					//clearKey(key_fire);
				}
				if (orientation == 6){
					new JGObject("bullet", true, x, y, 4, "bary", 0, 20, -2);
					new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, 20, -2);
					//clearKey(key_fire);
				}

			}
			if (getKey(key_firedown) && countObjects("bullet",0) < 2) {
				new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
				clearKey(key_fire);
			}
		}
		
		public void gun(int i) {
			//The gun is a semi-automatic weapon, you must press the fire key each time to fire
			if (getKey(key_fire) && countObjects("bullet",0) < 50 && i==0) {
				if (orientation == 9) {
					new JGObject("bullet", true, x, y, 4, "barx", -5, 0, -2);
					clearKey(key_fire);
				}
				if (orientation == 3) {
					new JGObject("bullet", true, x, y, 4, "barx", 5, 0, -2);
					clearKey(key_fire);
				}
				if (orientation == 12) {
					new JGObject("bullet", true, x, y, 4, "bary", 0, -5, -2);
					clearKey(key_fire);
				}
				if (orientation == 6){
					new JGObject("bullet", true, x, y, 4, "bary", 0, 5, -2);
					clearKey(key_fire);
				}

			}
			if (getKey(key_firedown) && countObjects("bullet",0) < 2) {
				new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
				clearKey(key_fire);
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