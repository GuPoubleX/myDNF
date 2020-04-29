package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.set.SetCharProperty;

public class RightOrLeftMove extends LeafTask<Character> {
	@Override
	public Status execute() {
		int x = ((int) (Math.random()*10))%2;
		getObject().set("move", new Vector2((x==0?1:-1)*SetCharProperty.instance_x, 0));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}