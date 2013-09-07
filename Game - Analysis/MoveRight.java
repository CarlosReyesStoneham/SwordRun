package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;

public class MoveRight extends Move{

	public MoveRight(Game engine, Hero object, String moveAnimationLeft,
			String moveAnimationRight, String moveAnimationUp,
			String moveAnimationDown, String standStillLeft,
			String standStillRight, String standStillUp, String standStillDown) {
		super(engine, object, moveAnimationLeft, moveAnimationRight, moveAnimationUp,
				moveAnimationDown, standStillLeft, standStillRight, standStillUp,
				standStillDown);
	}

	@Override
	boolean processKey(int speed, boolean flag) {

		if(engine.getKey(JGEngine.KeyRight)){
			object.setGraphic(moveAnimationRight);
			object.setLastGraphic(standStillRight);
			object.orientation = Move.ORIENTATION_RIGHT;
			object.xdir = speed;
			flag=true;
		}

		return flag;
	}

}
