package newgame;

import jgame.JGObject;
import jgame.impl.JGEngineInterface;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import jgame.*;
import newgame.Game;

public class Hero extends JGObject {
	public Game engine;
	public Hero(double x,double y,double speed, Game engine) {
		super("hero",true,x,y,1,"mstand4", 0,0,speed,speed,-1);
		this.engine = engine;
	}
	String lastGraphic = "mstand4";
	int i=0;
	int orientation = 12; //12-Forward, 3-Right, 6-Down, 9-Left
	public void move() {
		boolean flag=false;
		setDir(0,0);
		yspeed=2.5;
		xspeed=2.5;
		
		
		
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
		
		
		/*
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
		*/
		
		if (engine.getKey(JGEngine.KeyEnter) && i==0) {
			i=1;
			engine.clearKey(JGEngine.KeyEnter);
		}
		if (engine.getKey(JGEngine.KeyEnter)) {
			i=0;
			engine.clearKey(JGEngine.KeyEnter);
		}
//		gun(i);
//		machineGun(i);
		
		if (!flag){
			setGraphic(lastGraphic);
		}
	}
	
	public void machineGun(int i) {
		//Machine Gun is fully automatic, you can hold down the fire key.
		if (engine.getKey(JGEngineInterface.KeyFire) && engine.countObjects("bullet",0) < 50 && i==1) {
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
//		if (engine.getKey(JGEngine.KeyFiredown) && countObjects("bullet",0) < 2) {
//			new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
//			clearKey(key_fire);
//		}
	}
	
	public void gun(int i) {
		//The gun is a semi-automatic weapon, you must press the fire key each time to fire
		if (engine.getKey(JGEngine.KeyFire) && engine.countObjects("bullet",0) < 50 && i==0) {
			if (orientation == 9) {
				new JGObject("bullet", true, x, y, 4, "barx", -5, 0, -2);
				engine.clearKey(JGEngine.KeyFire);
			}
			if (orientation == 3) {
				new JGObject("bullet", true, x, y, 4, "barx", 5, 0, -2);
				engine.clearKey(JGEngine.KeyFire);
			}
			if (orientation == 12) {
				new JGObject("bullet", true, x, y, 4, "bary", 0, -5, -2);
				engine.clearKey(JGEngine.KeyFire);
			}
			if (orientation == 6){
				new JGObject("bullet", true, x, y, 4, "bary", 0, 5, -2);
				engine.clearKey(JGEngine.KeyFire);
			}

		}
//		if (JGEngine.getKey(engine.key_firedown) && countObjects("bullet",0) < 2) {
//			new JGObject("bullet",true,x,y,4,"bary", 0,-5, -2);
//			engine.clearKey(engine.key_fire);
//		}
	}
	
	public void hit(JGObject obj) {
		if (and(obj.colid,2)) engine.lifeLost();
		else {
			engine.score += 5;
			obj.remove();
		}
	}
	
}	