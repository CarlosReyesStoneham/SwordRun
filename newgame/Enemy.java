package newgame;

import jgame.JGObject;

public class Enemy extends JGObject {
	public Game engine;
	int health = 70;
	String graphic;
	//We have both a direction and a facing so that the path of
	//movement and the direction they are facing becomes clear
	char direction;
	char facing;
	double timer=0;
	boolean automate;
	public Enemy(double x, double y, double speed, char direction, String graphic, int health, Game engine, char facing, boolean automate) {
		super("enemy",true,x,y,
				2, graphic,
				speed, speed, -2 );
		this.direction = direction;
		this.engine = engine;
		this.facing = facing;
		this.automate = automate;
		
		if (direction=='y') {
			yspeed=speed;
			xspeed=0;
		}
		if (direction=='x') {
			xspeed=speed;
			yspeed=0;
		}
	}
	
	public void move() {
		if(automate==true)
			lurk();
	}
	
	
	/*
	 * When the enemy hits the edge of the screen he'll turn around
	 * This depends on the direction he is facing and the pixel coordinate he's at
	 * If you writing a similar method remeber to use <= or >= NOT ==
	 * >= or <= will always happen, == wil NOT
	 */
	public void lurk() {
		//facing (u)p (d)own (l)eft (r)ight
		if (y >= engine.pfHeight()-50 && facing == 'd') {
			setGraphic("ewalkf");
			facing = 'u';
			yspeed = -yspeed;
		}
		else if (y <= 5 && facing == 'u') {
			setGraphic("ewalkb");
			facing = 'd';
			yspeed = -yspeed;
		}
		else if (x >= engine.pfWidth()-50 && facing == 'r') {
			setGraphic("ewalkl");
			facing = 'l';
			xspeed = -xspeed;
		}
		else if (x <= 5 && facing == 'l') {
			setGraphic("ewalkr");
			facing = 'r';
			xspeed = -xspeed;
		}
		
	}
	
	public void hit(JGObject o) {
//		System.out.println(this.health);
		this.health += -1;
        new JGObject ("blood", true, x, y, 0, "blood", 0, 0, 20);
		if (!(o instanceof Hero)){
			o.remove();
		}
		if (this.health==0){
	        new JGObject ("explo", true, x, y, 0, "explo", 0, 0, 32);
			remove();
			o.remove();
			engine.score += 5;
		}
	}
}