package newgame;

import jgame.JGObject;
import newgame.Hero;

public class Block extends JGObject {
	public Hero hero;
	public Game engine;
	String graphic;

	public Block(double x, double y, String graphic, Game engine, Hero hero) {
		super("block", true, x, y, 2, graphic, -2);
		this.engine = engine;
		this.hero = hero;
	}

	public void move() {
		setPos(this.x, this.y);
	}

	public void hit(JGObject o) {
		if (hero.orientation == 9) {
			this.x  -= 5;
		}
		else if (hero.orientation == 12) {
			this.y -= 5;
		}
		else if (hero.orientation == 3) {
			this.x += 5;
		}
		else if (hero.orientation == 6) {
			this.y += 5;
		}
	}
}
