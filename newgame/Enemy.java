package newgame;

import jgame.JGObject;

public class Enemy extends JGObject {
	public Game engine;
	int health = 70;
	String graphic;
	char direction;
	double timer=0;
	public Enemy(double x, double y, double speed, char direction, String graphic, int health, Game engine) {
		super("enemy",true,x,y,
				2, graphic,
				speed, speed, -2 );
		this.direction = direction;
		this.engine = engine;
	}
	
	public void move() {
		
		double startingX=this.x;
		double startingY=this.y;
		
		
		if (this.direction=='y') {
			y += 0.5;
			
			y = 0;
			if (y> engine.pfWidth()) {
				y = -8;	
			}
		}
		if (this.direction=='x') {
			x += 0.5;
			
			x = 0;
			if (x > engine.pfWidth()) {
				x = -8;	
			}
		}
		lurk(startingX, startingY);
	}
	
	public void lurk(double startingX, double startingY) {
		if (this.direction=='w') {
			y += -(this.xspeed*2);
			x = 0;
			if (y> engine.pfWidth()) {
				y = -8;	
			}
		}
		if (this.direction=='s') {
			x = 0;
			if (y> engine.pfWidth()) {
				y = -8;	
			}
		}
		if (this.y>=engine.pfHeight()-50) {
			this.direction = 'w';
			y += -(this.yspeed);
			setGraphic("ewalkf");
		}
		
		if (this.y <= 10) {
			this.direction = 's';
			y += (this.xspeed);
			setGraphic("ewalkb");
			
		}
		
	}
	
	public void hit(JGObject o) {
//		System.out.println(this.health);
		this.health += -1;
		if (this.health==0){
	        new JGObject ("explo", true, x, y, 0, "explo", 0, 0, 32);
			remove();
			o.remove();
			engine.score += 5;
		}
	}
}