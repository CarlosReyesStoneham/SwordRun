package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Hero;

public class Block extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	String lastGraphic;
	boolean flag = false;
	public Block(double x, double y, String graphic, Game engine, Hero hero, String lastGraphic) {
		super("block", true, x, y, 5, graphic, -2);
		this.engine = engine;
		this.hero = hero;
		this.lastGraphic = lastGraphic;
	}
	
	public void move() {
		setPos(this.x, this.y);
	
		
		if (!flag) {
			setGraphic("boulder1");
			xspeed = 0;
			yspeed = 0;
		}
		flag = false;
	}

	public void hit(JGObject o) {
		if (and(o.colid, 5)) {
			flag = true;
			setGraphic("roll");
			xspeed = hero.xspeed;
			yspeed = hero.yspeed;
			xdir = hero.xdir;
			ydir = hero.ydir;
		}
	}
}
