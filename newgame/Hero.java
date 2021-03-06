package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Game;

public class Hero extends JGObject {
	public Game engine;
	public Game stdgame;
	public int health;
	public int cheatOn;
	public Hero(double x,double y,double speed, Game engine, Game stdgame, int health, int cheatOn) {
		super("hero",true,x,y,1,"mstand4", 0,0,speed,speed,-1);
		this.engine = engine;
		this.stdgame = stdgame;
		this.health = health;
		this.cheatOn = cheatOn;
	}
	
	//These are global as I do not want them in the move loop
	private String lastGraphic = "mstand4";
	private int i=0;
	public int orientation = 12; //12-Forward, 3-Right, 6-Down, 9-Left
	
	/*
	 * Orientation is like that on a clock
	 * 3=facing right, 9=facing right, 12=facing forward, 6=facing down
	 * 
	 */
	public void move() {
		boolean flag=false;
		setDir(0,0);
		yspeed=2.5;
		xspeed=2.5;
		
		//Basic Movement
		if (engine.getKey(JGEngine.KeyLeft)){
			setGraphic("walkl");
			lastGraphic="mstand2";
			orientation = 9;
			xdir=-1;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyRight)){
			setGraphic("walkr");
			lastGraphic="mstand3";
			orientation = 3;
			xdir=1;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyUp)){
			setGraphic("walkf");
			lastGraphic="mstand4";
			orientation = 12;
			ydir=-1;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyDown)){
			setGraphic("walkb");
			lastGraphic="mstand1";
			orientation = 6;
			ydir=1;
			flag=true;
		}
		
		run(flag);
		
		//switch weapons
		weaponSwitch(i);
		weapon(i, "barx", "bary");
		
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
	 * Hold shift to run
	 * This needs to be combined with walk for code space efficiency
	 */
	public void run(boolean flag) {
		//Running
		if (engine.getKey(JGEngine.KeyLeft) && engine.getKey(JGEngine.KeyShift)) {
			xdir=-2;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyRight) && engine.getKey(JGEngine.KeyShift)) {
			xdir=2;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyUp) && engine.getKey(JGEngine.KeyShift)) {
			ydir=-2;
			flag=true;
		}
		if (engine.getKey(JGEngine.KeyDown) && engine.getKey(JGEngine.KeyShift)) {
			ydir=2;
			flag=true;
		}
	}
	
	/*
	 * 0=gun, 1=machine gun, 2=throwing sword
	 * These numbers are used to select between weapons
	 */
	public void weapon(int select, String graphicH, String graphicV) {
		//The gun is a semi-automatic weapon, you must press the fire key each time to fire
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 50) {
			if (orientation == 9) {
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
			if (orientation == 3) {
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
			if (orientation == 12) {
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
			if (orientation == 6){
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
	
	public void hit(JGObject obj) {
		//Collision with enemies
		if (and(obj.colid,2) && obj instanceof Enemy) {
			if (health >= 0){
				health-= 1;
			}
			if (health <=0 && this.cheatOn ==0) {
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