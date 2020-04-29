package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;

public class StandOneLineX extends LeafTask<Character> {
	@Override
	public Status execute() {
		return getObject().Xovertime()?Status.FAILED:Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}