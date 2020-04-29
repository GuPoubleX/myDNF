package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;
import dnf.character.state.skill.common.*;

public class EffectControl extends LeafTask<Character> {
	@Override
	public Status execute() {
		if(getObject().currentPose().getClass().equals(Hurt.class) ||
				getObject().currentPose().getClass().equals(Lie.class)) {
			if(getObject().readySkill() != null)
				getObject().readySkill(null);
			return Status.FAILED;
		}else 
			return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}