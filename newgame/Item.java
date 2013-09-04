package newgame;

import jgame.JGObject;
import newgame.Hero;

public class Item extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	boolean flag = false;
	public Item(double x, double y, String graphic, Game engine, Hero hero) {
		super("item", true, x, y, 6, graphic, -2);
		this.engine = engine;
		this.hero = hero;
	}
	
	public void move() {
		setPos(this.x, this.y);
		setGraphic("block1");
	}

	public void hit(JGObject o) {

	}
}
