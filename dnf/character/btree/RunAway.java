package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.set.SetCharProperty;

public class RunAway extends LeafTask<Character> {
	@Override
	public Status execute() {
		int x = ((int) (Math.random()*100))%10;
		float right = getObject().emenyCharacter().getX()-getObject().getX();
		float up = getObject().emenyCharacter().getY()-getObject().getY();
		up = up>0?-1:1;
		boolean dx = x>2?true:false;
		getObject().set("move", new Vector2((dx?0:1)*(right<0?1:-1)*SetCharProperty.instance_x, up*SetCharProperty.instance_y));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}