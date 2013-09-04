package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;
import newgame.Hero;

public class Item extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;
	String lastGraphic;
	boolean flag = false;
	public Item(double x, double y, String graphic, Game engine, Hero hero, String lastGraphic) {
		super("item", true, x, y, 6, graphic, -2);
		this.engine = engine;
		this.hero = hero;
		this.lastGraphic = lastGraphic;
	}
	
	public void move() {
		setPos(this.x, this.y);
		setGraphic("block1");
	}

	public void hit(JGObject o) {
		//if the hero hits the item, remove the item
//		if(and(o.colid, 1) && o instanceof Hero){
//			this.remove();
//		}

	}
}
