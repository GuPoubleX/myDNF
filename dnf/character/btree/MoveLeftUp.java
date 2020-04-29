package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.set.SetCharProperty;

public class MoveLeftUp extends LeafTask<Character> {
	@Override
	public Status execute() {
		getObject().set("move", new Vector2(-SetCharProperty.instance_x, SetCharProperty.instance_y));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}