package newgame;


import jgame.platform.JGEngine;

abstract class Move {
	
	protected Game engine;
	protected Hero object;
	
	protected String moveAnimationLeft;
	protected String moveAnimationRight;
	protected String moveAnimationUp;
	protected String moveAnimationDown;
	
	protected String standStillLeft;
	protected String standStillRight;
	protected String standStillUp;
	protected String standStillDown;
	
	protected final static int LEFT = JGEngine.KeyLeft;
	protected final static int RIGHT = JGEngine.KeyRight;
	protected final static int UP = JGEngine.KeyUp;
	protected final static int DOWN = JGEngine.KeyDown;
	
	protected final static int WALK_SPEED = 1;
	protected final static int RUN_SPEED = 2;
	
	//Orientation
	protected final static int ORIENTATION_LEFT = 9;
	protected final static int ORIENTATION_RIGHT = 3;
	protected final static int ORIENTATION_UP = 12;
	protected final static int ORIENTATION_DOWN = 6;
	
	public Move(Game engine, Hero object, String moveAnimationLeft, String moveAnimationRight, String moveAnimationUp, String moveAnimationDown,
			String standStillLeft, String standStillRight, String standStillUp, String standStillDown) {

		this.engine = engine;
		this.object = object;
		
		this.moveAnimationLeft = moveAnimationLeft;
		this.moveAnimationRight = moveAnimationRight;
		this.moveAnimationUp = moveAnimationUp;
		this.moveAnimationDown = moveAnimationDown;
		
		this.standStillLeft = standStillLeft;
		this.standStillRight = standStillRight;
		this.standStillUp = standStillUp;
		this.standStillDown = standStillDown;
	}
	abstract boolean processKey(int speed, boolean flag );	
	
}
