package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.character.state.*;
import dnf.gupoublex.set.SetCharProperty;

public class CloseEmeny extends LeafTask<Character> {
	@Override
	public Status execute() {
		Character emeny = getObject().emenyCharacter();
		int x = 0;
		int y = 0;
		boolean right = getObject().isRight();
		if(((int) emeny.getX())-10 > ((int) getObject().getX())) {
			x = 1;
			right = true;
		}else if(((int) emeny.getX())+10 < ((int) getObject().getX())) {
			x = -1;
			right = false;
		}else
			x = 0;
		if(((int) emeny.getY())-5 > ((int) getObject().getY()))
			y = 1;
		else if(((int) emeny.getY())+5 < ((int) getObject().getY()))
			y = -1;
		else
			 y = 0;
		if(getObject().currentPose().getClass().equals(Move.class))
			getObject().setDirect(right);
		getObject().set("move", new Vector2(x*SetCharProperty.instance_x, y*SetCharProperty.instance_y));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}