package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import jgame.platform.StdGame;
import newgame.Hero;

public class Weapons extends JGObject {
	public Game engine;
	public Game stdgame;
	public Hero hero;
	int i;
	public Weapons(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, Game engine, Game stdgame, Hero hero, int i) {
		super(name, unique_id, x, y, collisionid, gfxname);
		// TODO Auto-generated constructor stub
		this.engine = engine;
		this.stdgame = stdgame;
		this.i = i;
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
	
	public void machineGun(int i) {
		//Machine Gun is fully automatic, you can hold down the fire key.
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 50 && i==1) {
			if (hero.orientation == 9) {
				new JGObject("bullet", true, x, y, 4, "barx", -20, 0, -2);
				new JGObject("bullet", true, x-10, y-3, 4, "barx", -20, 0, -2);
				//clearKey(key_fire);
			}
			if (hero.orientation == 3) {
				new JGObject("bullet", true, x, y, 4, "barx", 20, 0, -2);
				new JGObject("bullet", true, x-8, y-3, 4, "barx", 20, 0, -2);
				//clearKey(key_fire);
			}
			if (hero.orientation == 12) {
				new JGObject("bullet", true, x, y, 4, "bary", 0, -20, -2);
				new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, -20, -2);
				//clearKey(key_fire);
			}
			if (hero.orientation == 6){
				new JGObject("bullet", true, x, y, 4, "bary", 0, 20, -2);
				new JGObject("bullet", true, x-3, y-8, 4, "bary", 0, 20, -2);
				//clearKey(key_fire);
			}

		}
	}
	
	public void gun(int automatic, String graphicH, String graphicV) {
		//The gun is a semi-automatic weapon, you must press the fire key each time to fire
		if (engine.getKey(stdgame.key_fire) && engine.countObjects("bullet",0) < 50 && i==0) {
			if (hero.orientation == 9) {
				new JGObject("bullet", true, x, y, 4, graphicH, -5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 3) {
				new JGObject("bullet", true, x, y, 4, graphicH, 5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 12) {
				new JGObject("bullet", true, x, y, 4, graphicV, 0, -5, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 6){
				new JGObject("bullet", true, x, y, 4, graphicV, 0, 5, -2);
				engine.clearKey(stdgame.key_fire);
			}

		}
	}
	
	// Throw sword
	public void sword(int i) {

		if (engine.getKey(stdgame.key_fire) && i==2) {
			if (hero.orientation == 9) {
				new JGObject("sword4", true, x, y, 4, "swing1", -5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 3) {
				new JGObject("sword4", true, x, y, 4, "swing1", 5, 0, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 12) {
				new JGObject("sword4", true, x, y, 4, "swing1", 0, -5, -2);
				engine.clearKey(stdgame.key_fire);
			}
			if (hero.orientation == 6){
				new JGObject("sword4", true, x, y, 4, "swing1", 0, 5, -2);
				engine.clearKey(stdgame.key_fire);
			}

		}
	}
}
