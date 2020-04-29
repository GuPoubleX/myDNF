package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;
import dnf.character.state.skill.swordman.Rapidmoveslash;

public class SpecialSkillCDing extends LeafTask<Character> {
	@Override
	public Status execute() {
		boolean direct = false;
		if(getObject().currentPose().getClass().equals(Rapidmoveslash.class))
			direct = true;
		return direct?Status.SUCCEEDED:Status.FAILED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}