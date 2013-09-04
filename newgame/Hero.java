package newgame;

import java.awt.Rectangle;

import jgame.JGObject;
import jgame.impl.JGEngineInterface;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import jgame.*;
import newgame.Game;
import newgame.Block;

public class Hero extends JGObject {
	public Game engine;
	public Game stdgame;
	public int health;
	public Hero(double x,double y,double speed, Game engine, Game stdgame, int health) {
		super("hero",true,x,y,1,"mstand4", 0,0,speed,speed,-1);
		this.engine = engine;
		this.stdgame = stdgame;
		this.health = health;
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
		gun(i, "barx", "bary");
		machineGun(i);
		//machineGun(i);
		sword(i);
		
		if (!flag){
			setGraphic(lastGraphic);
		}
	}
	
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
	
	public void machineGun(int i) {
		//Machine Gun is fully automatic, you can hold down the fire key.
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 30 && i==1) {
			if (orientation == 9) {
				new JGObject("bullet", true, x, y, 4, "barx", -30, 0, -2);
				new JGObject("bullet", true, x-10, y-3, 4, "barx", -30, 0, -2);
				//clearKey(key_fire);
			}
			if (orientation == 3) {
				new JGObject("bullet", true, x, y, 4, "barx", 30, 0, -2);
				new JGObject("bullet", true, x-8, y-3, 4, "barx", 30, 0, -2);
				//clearKey(key_fire);
			}
			if (orientation == 12) {
				new JGObject("bullet", true, x, y, 4, "bary", 0, -30, -2);
				new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, -50, -2);
				//clearKey(key_fire);
			}
			if (orientation == 6){
				new JGObject("bullet", true, x, y, 4, "bary", 0, 30, -2);
				new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, 50, -2);
				//clearKey(key_fire);
			}

		}
	}
	
	public void gun(int automatic, String graphicH, String graphicV) {
		//The gun is a semi-automatic weapon, you must press the fire key each time to fire
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 50 && i==0) {
			if (orientation == 9) {
				new JGObject("bullet", true, x, y, 4, graphicH, -5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 3) {
				new JGObject("bullet", true, x, y, 4, graphicH, 5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 12) {
				new JGObject("bullet", true, x, y, 4, graphicV, 0, -5, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 6){
				new JGObject("bullet", true, x, y, 4, graphicV, 0, 5, -2);
				engine.clearKey(stdgame.key_fire);
			}

		}
	}
	
	// Throw sword
	public void sword(int i) {

		if (engine.getKey(stdgame.key_fire) && i==2) {
			if (orientation == 9) {
				new JGObject("sword4", true, x, y, 4, "swing1", -5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 3) {
				new JGObject("sword4", true, x, y, 4, "swing1", 5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 12) {
				new JGObject("sword4", true, x, y, 4, "swing1", 0, -5, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (orientation == 6){
				new JGObject("sword4", true, x, y, 4, "swing1", 0, 5, -2);
				engine.clearKey(stdgame.key_fire);
			}

		}
	}
	
//	public void healthBar(int healthX, int healthY, int heathWidth, int healthHeight) {
//		Rectangle healthBG = new Rectangle(healthX, healthY, healthWidth, healthHeight);
//
//        float healthGAWidth = ((float) health / (float) maxHealth) * (float) healthWidth;
//        Rectangle healthGA = new Rectangle(healthX, healthY, healthGAWidth, healthHeight);
//
//	}
	
	public void hit(JGObject obj) {
		if (and(obj.colid,2) && obj instanceof Enemy) {
			if (health != 0){
				health-= 1;
			}
			else {
				engine.lifeLost();
			}
		}
		else if (and(obj.colid, 6)  && obj instanceof Wall) {
			obj.xspeed=0;
			obj.yspeed=0;
		}
		

		//For picking stuff up
		else if (and(obj.colid, 7) && obj instanceof Item){
			health += 300;
			obj.remove();
		}
	}
	
}	