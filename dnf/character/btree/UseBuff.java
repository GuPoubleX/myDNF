package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;

public class UseBuff extends LeafTask<Character> {
	@Override
	public Status execute() {
		getObject().readySkill().play();
		getObject().readySkill(null);
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}