package newgame;

import jgame.JGObject;
import jgame.platform.JGEngine;

public class MoveDown extends Move{

	public MoveDown(Game engine, Hero object, String moveAnimationLeft,
			String moveAnimationRight, String moveAnimationUp,
			String moveAnimationDown, String standStillLeft,
			String standStillRight, String standStillUp, String standStillDown) {
		super(engine, object, moveAnimationLeft, moveAnimationRight, moveAnimationUp,
				moveAnimationDown, standStillLeft, standStillRight, standStillUp,
				standStillDown);
	}

	@Override
	boolean processKey(int speed, boolean flag) {

		if(engine.getKey(JGEngine.KeyDown)){
			object.setGraphic(moveAnimationDown);
			object.setLastGraphic(standStillDown);
			object.orientation = Move.ORIENTATION_DOWN;
			object.ydir = speed;
			flag=true;
		}

		return flag;
	}

}
