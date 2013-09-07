package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;

public class MoveUp extends Move{

	public MoveUp(Game engine, Hero object, String moveAnimationLeft,
			String moveAnimationRight, String moveAnimationUp,
			String moveAnimationDown, String standStillLeft,
			String standStillRight, String standStillUp, String standStillDown) {
		super(engine, object, moveAnimationLeft, moveAnimationRight, moveAnimationUp,
				moveAnimationDown, standStillLeft, standStillRight, standStillUp,
				standStillDown);
	}

	@Override
	boolean processKey(int speed, boolean flag) {

		if(engine.getKey(JGEngine.KeyUp)){
			object.setGraphic(moveAnimationUp);
			object.setLastGraphic(standStillUp);
			object.orientation = Move.ORIENTATION_UP;
			object.ydir = -speed;
			flag=true;
		}

		return flag;
	}

}
