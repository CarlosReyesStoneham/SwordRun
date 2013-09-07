package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;

public class MoveLeft extends Move{

	public MoveLeft(Game engine, Hero object, String moveAnimationLeft,
			String moveAnimationRight, String moveAnimationUp,
			String moveAnimationDown, String standStillLeft,
			String standStillRight, String standStillUp, String standStillDown) {
		super(engine, object, moveAnimationLeft, moveAnimationRight, moveAnimationUp,
				moveAnimationDown, standStillLeft, standStillRight, standStillUp,
				standStillDown);
	}

	@Override
	boolean processKey(int speed, boolean flag) {
		if(engine.getKey(JGEngine.KeyLeft)){
			object.setGraphic(moveAnimationLeft);
			object.setLastGraphic(standStillLeft);
			object.orientation = Move.ORIENTATION_LEFT;
			object.xdir = -speed;
			flag=true;
		}

		return flag;
	}

}
