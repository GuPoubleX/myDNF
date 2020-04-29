package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;

public class HaveEmeny  extends LeafTask<Character> {
	@Override
	public Status execute() {
		return getObject().emenyCharacter()!=null?Status.SUCCEEDED:Status.FAILED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}