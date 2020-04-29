package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;

public class UserControl extends LeafTask<Character> {
	@Override
	public Status execute() {
		if(getObject().isControlled())
			return Status.FAILED;
		else
			return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}