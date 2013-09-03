package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Hero;

public class Block extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	String lastGraphic;

	public Block(double x, double y, String graphic, Game engine, Hero hero, String lastGraphic) {
		super("block", true, x, y, 2, graphic, -2);
		this.engine = engine;
		this.hero = hero;
		this.lastGraphic = lastGraphic;
	}
	
	public void move() {
		setPos(this.x, this.y);
	}

	public void hit(JGObject o) {
		
	}
}
