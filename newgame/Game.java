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
	final static int PFWIDTH = 50;
	final static int PFHEIGHT = 37;
	final static int PADDED_HEIGHT= HEIGHT-45;
	final static int PADDED_WIDTH = WIDTH-25;
	final static double HALF_SCREEN_HEIGHT = HEIGHT/2;
	final static double HALF_SCREEN_WIDTH = WIDTH/2;
	final static int PF_MARGIN = -10;
	final static int ITEM_X = 700;
	final static int ITEM_Y = 100;
	final static int WORLD0 = 0;
	final static int WORLD1 = 1;
	final static int WORLD2 = 2;
	final static int WORLD3 = 3;

	
	private boolean visitedWorld0 = false;
	private boolean visitedWorld1 = false;
	private boolean visitedWorld2 = false;
	private boolean visitedWorld3 = false;
	
	
	Hero hero = null;
	Enemy enemy = null;
	Block block = null;
	Wall wall = null;
	Item item = null;
	
	int currentWorld = 0; //Start in the lower right

	
	public Game() {
		initEngineApplet();
	}
		
	public Game(int width, int height) {
		initEngine(WIDTH, HEIGHT);
		setPFSize(PFWIDTH, PFHEIGHT); 
		setViewOffset(1, 1, true);
		//setPFWrap(true, true, 10, 10);
	}
	
	@Override
	public void initCanvas() {
		JGColor red = new JGColor(255, 0, 0);
		JGColor black = new JGColor(0, 0, 0);
		setCanvasSettings(PFWIDTH,PFHEIGHT,16,16,red,black,null);
	}

	public void initGame() {
		setMedia();
		setMouseCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		if (isMidlet()) {
			setFrameRate(20, 1);
			setGameSpeed(2.0);
		} else {
			setFrameRate(45, 1);
		}
		setHighscores(10, new Highscore(0, "nobody"), 15);

		//dbgShowBoundingBox(true);
	}
	
	public void paintFrameTitle() {
		JGColor black = new JGColor(0, 0, 0);
		JGFont courier = new JGFont("Courier", 0, 32);
		drawString("Welcome to SwordRun", HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT - 200, 0, courier, black);
	}
	
	public void setMedia() {
		String[] tableArray = {"tables/mygame.tbl", "tables/outdoors.tbl", "tables/character.tbl", "tables/game.tbl",
				"tables/sword.tbl", "tables/boulder.tbl", "tables/boss.tbl"};
		for(String table : tableArray){
			defineMedia(table);
		}
		setBGImage("grass1");
	}
	
	//makes a path
//	public void initPath() {
//		double random = Math.random() * PFHEIGHT + 1;
//		double random2 = Math.random() * PFWIDTH + 1;
//		int starting = (int)random;
//		int starting2 = (int)random2;
//		for(int i=0; i <= PFHEIGHT; i++) {
//			setTile(starting, i, "p2");
//			setTile(starting+1, i, "p1");
//			setTile(starting+2, i, "p1");
//			setTile(starting+3, i, "p3");
//		}
//		for(int i=0; i <= PFWIDTH; i++) {
//			setTile(i, starting2, "p4");
//			setTile(i, starting2+1, "p1");
//			setTile(i, starting2+2, "p1");
//			setTile(i, starting2+3, "p5");
//		}
//	}
	

	public void initNewLife() {
		removeObjects(null,0);
		//Enemies have 'facing' which specifies the direction they are headed in
		//enemy = new Enemy(4, 380, 2, 'y', "ewalkb", 15, this, 'd', true);
		hero = new Hero(HALF_SCREEN_WIDTH,pfHeight()-100,5, this, this, 3000);
		block = new Block(200, pfHeight()-100, "boulder1", this, hero);
		item = new Item(ITEM_X, ITEM_Y, "block1", this, hero);

		//wall = new Wall(300, 200, "boulder4", this, hero, "boulder4");

	}
	
	public void startGameOver() {
		removeObjects(null,0);
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCol();
		setWalls();
		checkPosition();
	}
	
	//Called in the doFrameInGame loop
	public void checkCol() {
		checkCollision(2,1); // enemies hit player
		checkCollision(4,2); // bullets hit enemies
		checkCollision(1,5); // player hit block
		checkCollision(1,6); // player hit wall
		checkCollision(2,5); // enemy hit block
		checkCollision(5,6); // block hit wall
		checkCollision(1,7); // player hit health
	}
	
	/*
	 * 0 is the world in the lower right
	 * 1 is the world in the lower left
	 * 2 is the world in the upper left
	 * 3 is the world in the upper right
	 *  ----------
	 * | 2  |  3  |
	 * |----------|
	 * | 1  |  0  |
	 *  -----------
	 *  Called in the doFrameInGame loop
	 */
	public void checkPosition() {

		if (!hero.isOnPF(PF_MARGIN, PF_MARGIN) && hero.orientation==9 && (currentWorld==WORLD0 || currentWorld==WORLD3)) {
			
			//Determine the next world that you will be in
			if(currentWorld==WORLD0){
				currentWorld = WORLD1;
			}
			else{
				currentWorld=WORLD2;
			}
			
			block.setPos(pfWidth() - 50 - (hero.x - block.x), block.getLastY());
			hero.setPos(pfWidth()-50, hero.getLastY());
			
			fillBG("pa");
		}
		else if (!hero.isOnPF(PF_MARGIN, PF_MARGIN) && hero.orientation==WORLD3 && (currentWorld==WORLD1 || currentWorld==WORLD2)) {
			
			//Determine the next world that you will be in
			if(currentWorld==WORLD2){
				currentWorld = WORLD3;
			}
			else{
				currentWorld=WORLD0;
			}
			
			block.setPos(0 + (block.x - hero.x), block.getLastY());
			hero.setPos(0, hero.getLastY());
			fillBG("pa");

		}
		else if (!hero.isOnPF(PF_MARGIN, PF_MARGIN) && hero.orientation==12 && (currentWorld==WORLD0 || currentWorld==WORLD1)) {
			
			//Determine the next world that you will be in
			if(currentWorld==WORLD0){
				currentWorld = WORLD3;
			}
			else{
				currentWorld=WORLD2;
			}
			
			//block.setPos(pfWidth() - 50 - (hero.x - block.x), block.getLastY());
			hero.setPos(hero.getLastX(), pfHeight()-50);
			fillBG("pa");

		}
		else if (!hero.isOnPF(PF_MARGIN, PF_MARGIN) && hero.orientation==6 && (currentWorld==WORLD2 || currentWorld==WORLD3)){
			
			//Determine the next world that you will be in
			if(currentWorld==WORLD3){
				currentWorld = WORLD0;
			}
			else{
				currentWorld=WORLD1;
			}
			
			
			hero.setPos(hero.getLastX(), 0);
			fillBG("pa");

		}
	}
	
	public void genEnemies(int num, String graphic) {
		for(int i=0; i < num; i++){
			double randomy = Math.random() * PADDED_HEIGHT + 1;
			double randomx = Math.random() * PADDED_WIDTH + 1;
			double randoms = Math.random() * 4 + 1;

			new Enemy(randomx, randomy, randoms, 'x', graphic, 15, this, 'r', true);
		}
	}
	public void setWalls() {
		
		//First World
		if(currentWorld==WORLD0) {
			if (!visitedWorld0) {
				genEnemies(1, "ewalkr");
			}
			if (hero.x >= PADDED_WIDTH){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if (hero.y >= PADDED_HEIGHT){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			//This is the only case where you may not go up
			//This would be getting to the end without any effort
			if (hero.y <= 0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if(hero.x <= 0 && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			visitedWorld0 = true; //We have indeed visited this world
		}
		
		//Second World
		else if (currentWorld==WORLD1) {
			if(!visitedWorld1) {
				genEnemies(2, "ewalkr");
			}
			
			//Permanent boundries, even after the game is over
			if (hero.x <= 0){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if (hero.y >= PADDED_HEIGHT){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			//Temporary boundries while enemies are still alive
			if(hero.y <= 0 && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if(hero.x >= PADDED_WIDTH && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			visitedWorld1 = true;

		}
		
		//Third World
		else if(currentWorld==WORLD2) {
			if(!visitedWorld2) {
				genEnemies(3, "ewalkr");
			}
			
			//Permanent
			if(!visitedWorld2) {
				new Enemy(4, 100, 2, 'x', "ewalkr", 15, this, 'r', true);
			}
			if (hero.x <= 0){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if (hero.y <= 0){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			//Temporary
			if (hero.y >= PADDED_HEIGHT  && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if(hero.x >= PADDED_WIDTH && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}

			visitedWorld2 = true;
		}
		
		//Fourth World
		else if(currentWorld==WORLD3) {
			if(!visitedWorld3) {
				genEnemies(1, "bwalkr");
			}
			//Permanent
			if (hero.x >= PADDED_WIDTH){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if (hero.y <= 0){
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			//Temporary
			if (hero.y >= PADDED_HEIGHT  && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			if(hero.x <= 0 && countObjects("enemy",0)!=0) {
				hero.setPos(hero.getLastX(), hero.getLastY());
			}
			
			visitedWorld3 = true;

		}
	}
	
	public void incrementLevel() {
		score += 50;
		if (level<3){
			level++;
		}
		stage++;
	}
	
	JGFont scoring_font = new JGFont("Arial",0,8);
	
	public static void main(String[] args) {
		// Run the game at a window size of 800 by 600 pixels.

		new Game(WIDTH, HEIGHT);
	}

}