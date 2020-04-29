package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;
import dnf.character.state.skill.common.*;

public class EmenyLie extends LeafTask<Character> {
	@Override
	public Status execute() {
		return getObject().emenyCharacter().currentPose().getClass().equals(Lie.class)?Status.FAILED:Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}