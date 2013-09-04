package newgame;

import jgame.JGObject;
import newgame.Hero;

public class Block extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	boolean flag = false;
	public Block(double x, double y, String graphic, Game engine, Hero hero) {
		super("block", true, x, y, 5, graphic, -2);
		this.engine = engine;
		this.hero = hero;
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
		//if the hero hits the block, prevent him from moving forward
		if (and(o.colid, 5) && o instanceof Hero) {
			flag = true;
			setGraphic("roll");
			xspeed = hero.xspeed;
			yspeed = hero.yspeed;
			xdir = hero.xdir;
			ydir = hero.ydir;
		}
		//If the enemies hit the block, prevent them from moving forward
		else if (and(o.colid, 2) && o instanceof Enemy) {
			o.setPos(o.getLastX(), o.getLastY());
		}
		else if (and(o.colid, 6) && o instanceof Wall) {
			setPos(getLastX(), getLastY());
			hero.setPos(hero.getLastX(), hero.getLastY());
		}
	}
}
