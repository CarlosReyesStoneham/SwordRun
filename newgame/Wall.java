package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Hero;

public class Wall extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	String lastGraphic;
	boolean flag = false;
	public Wall(double x, double y, String graphic, Game engine, Hero hero, String lastGraphic) {
		super("wall", true, x, y, 6, graphic, -2);
		this.engine = engine;
		this.hero = hero;
		this.lastGraphic = lastGraphic;
	}
	
	public void move() {
		setPos(this.x, this.y);
		setGraphic("boulder4");
	}

	public void hit(JGObject o) {
		//if the hero hits the wall, prevent him from moving forward
		if(and(o.colid, 1) && o instanceof Hero){
			hero.setPos(hero.getLastX(), hero.getLastY());
		}

	}
}
