package newgame;

import java.util.ArrayList;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Game;
import newgame.Move;
import newgame.MoveLeft;

public class Hero extends JGObject {
	public Game engine;
	public Game stdgame;
	public int health;
	public boolean cheatsToggle;
	
	//animations
	protected String moveAnimationLeft;
	protected String moveAnimationRight;
	protected String moveAnimationUp;
	protected String moveAnimationDown;
	
	protected String standStillLeft;
	protected String standStillRight;
	protected String standStillUp;
	protected String standStillDown;
	
	private int i=0;
	public int orientation = ORIENTATION_UP; //12-Forward, 3-Right, 6-Down, 9-Left
	
	//Speeds
	protected final static int WALK_SPEED = 1;
	protected final static int RUN_SPEED = 2;
	
	//Orientation
	protected final static int ORIENTATION_LEFT = 9;
	protected final static int ORIENTATION_RIGHT = 3;
	protected final static int ORIENTATION_UP = 12;
	protected final static int ORIENTATION_DOWN = 6;
	
	ArrayList<Move> myMoves;
	
	public Hero(double x,double y,double speed, Game engine, Game stdgame, int health, boolean cheatsToggle,
			String moveAnimationLeft, String moveAnimationRight, String moveAnimationUp, String moveAnimationDown,
			String standStillLeft, String standStillRight, String standStillUp, String standStillDown) {
		super("hero",true,x,y,1,"mstandu", 0,0,speed,speed,-1);
		
		this.engine = engine;
		this.stdgame = stdgame;
		this.health = health;
		this.cheatsToggle = cheatsToggle;
		
		this.moveAnimationLeft = moveAnimationLeft;
		this.moveAnimationRight = moveAnimationRight;
		this.moveAnimationUp = moveAnimationUp;
		this.moveAnimationDown = moveAnimationDown;
		
		this.standStillLeft = standStillLeft;
		this.standStillRight = standStillRight;
		this.standStillUp = standStillUp;
		this.standStillDown = standStillDown;

		this.myMoves = new ArrayList<Move>();
		myMoves.add(new MoveLeft(engine, this, "walkl", "walkr",
				"walku", "walkd", "mstandl", "mstandr", "mstandu", "mstandd"));
		myMoves.add(new MoveRight(engine, this, "walkl", "walkr",
				"walku", "walkd", "mstandl", "mstandr", "mstandu", "mstandd"));
		myMoves.add(new MoveUp(engine, this, "walkl", "walkr",
				"walku", "walkd", "mstandl", "mstandr", "mstandu", "mstandd"));
		myMoves.add(new MoveDown(engine, this, "walkl", "walkr",
				"walku", "walkd", "mstandl", "mstandr", "mstandu", "mstandd"));
		

	}
	
	//These are global as I do not want them in the move loop
	private String lastGraphic = "mstandu";
	
	public String getLastGraphic() {
		return lastGraphic;
	}

	public void setLastGraphic(String lastGraphic) {
		this.lastGraphic = lastGraphic;
	}
	
	/*
	 * Orientation is like that on a clock
	 * 3=facing right, 9=facing right, 12=facing forward, 6=facing down
	 * 
	 */
	public void move() {
		boolean flag=false;
		int speed = WALK_SPEED;
		if (engine.getKey(JGEngine.KeyShift)){
			speed=RUN_SPEED;
		}
		setDir(0,0);
		yspeed=2.5;
		xspeed=2.5;
		
		walk(flag, speed);
				
		//switch weapons
		weaponSwitch(i);
		weapon(i, "barx", "bary");

	}
	
	/*
	 * Hold shift to run
	 * 
	 */
	public void walk(boolean flag, int speed) {
		for(Move move : myMoves){
			flag = move.processKey(speed, flag);		
		}
		if (!flag){
			setGraphic(lastGraphic);
		}
	}
	
	/*
	 * Selects between 0, 1, 2 to choose 
	 * between gun, machinegun, and throwing sword
	 * respectively
	 */
	public void weaponSwitch(int x) {
		if (engine.getKey(JGEngine.KeyEnter) && x==0) {
			i=1;
			engine.clearKey(JGEngine.KeyEnter);
		}
		if (engine.getKey(JGEngine.KeyEnter) && x==1) {
			i=2;
			engine.clearKey(JGEngine.KeyEnter);
		}
		if (engine.getKey(JGEngine.KeyEnter)) {
			i=0;
			engine.clearKey(JGEngine.KeyEnter);
		}
	}

	/*
	 * 0=gun, 1=machine gun, 2=throwing sword
	 * These numbers are used to select between weapons
	 * This should be better implemented in the future
	 */
	public void weapon(int select, String graphicH, String graphicV) {
		//The gun is a semi-automatic weapon, you must press the fire key each time to fire
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 50) {
			if (orientation == ORIENTATION_LEFT) {
				if(i==0){
					new JGObject("bullet", true, x, y, 4, graphicH, -5, 0, -2);
					engine.clearKey(stdgame.key_fire);
				}
				if(i==1){
					new JGObject("bullet", true, x, y, 4, graphicH, -30, 0, -2);
					new JGObject("bullet", true, x-10, y-3, 4, graphicH, -30, 0, -2);
				}
				if(i==2){
					new JGObject("sword4", true, x, y, 4, "swing1", -5, 0, -2);
					engine.clearKey(stdgame.key_fire);
				}
			}
			if (orientation == ORIENTATION_RIGHT) {
				if(i==0){
					new JGObject("bullet", true, x, y, 4, graphicH, 5, 0, -2);
					engine.clearKey(stdgame.key_fire);
				}
				if(i==1){
					new JGObject("bullet", true, x, y, 4, graphicH, 30, 0, -2);
					new JGObject("bullet", true, x-8, y-3, 4, graphicH, 30, 0, -2);
				}
				if(i==2){
					new JGObject("sword4", true, x, y, 4, "swing1", 5, 0, -2);
					engine.clearKey(stdgame.key_fire);
				}
			}
			if (orientation == ORIENTATION_UP) {
				if(i==0){
					new JGObject("bullet", true, x, y, 4, graphicV, 0, -5, -2);
					engine.clearKey(stdgame.key_fire);
				}
				if(i==1){
					new JGObject("bullet", true, x, y, 4, graphicV, 0, -30, -2);
					new JGObject("bullet", true, x-3, y-8, 4, graphicV, 0, -50, -2);
				}
				if(i==2){
					new JGObject("sword4", true, x, y, 4, "swing1", 0, -5, -2);
					engine.clearKey(stdgame.key_fire);
				}
			}
			if (orientation == ORIENTATION_DOWN){
				if(i==0){
					new JGObject("bullet", true, x, y, 4, graphicV, 0, 5, -2);
					engine.clearKey(stdgame.key_fire);
				}
				if(i==1){
					new JGObject("bullet", true, x, y, 4, graphicV, 0, 30, -2);
					new JGObject("bullet", true, x-3, y-8, 4, graphicV, 0, 50, -2);
				}
				if(i==2){
					new JGObject("sword4", true, x, y, 4, "swing1", 0, 5, -2);
					engine.clearKey(stdgame.key_fire);
				}
			}

		}
	}
	
	//Defines what happens when you hit objects with Hero
	public void hit(JGObject obj) {
		//Collision with enemies
		if (and(obj.colid,2) && obj instanceof Enemy) {
			if (health >= 0){
				health-= 1;
			}
			if (health <=0 && this.cheatsToggle == false) {
				engine.lifeLost();
			}
		}
		//Collision with wall objects
		else if (and(obj.colid, 6)  && obj instanceof Wall) {
			obj.xspeed=0;
			obj.yspeed=0;
		}

		//For picking stuff up, in this case treasure
		else if (and(obj.colid, 7) && obj instanceof Item){
			obj.remove();
			engine.setGameState("GameOver");
		}
	}
	
}	