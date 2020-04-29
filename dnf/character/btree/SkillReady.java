package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;
import dnf.hud.button.SkillButton;

public class SkillReady extends LeafTask<Character> {
	@Override
	public Status execute() {
		if(getObject().getSkillList() == null || getObject().getSkillList().size == 0)
			return Status.SUCCEEDED;
		boolean cdcomplete = false;
		for(SkillButton sb : getObject().getSkillList())
			if(!sb.CDing()) {
				cdcomplete = true;
				break;
			}
		return (!cdcomplete)?Status.SUCCEEDED:Status.FAILED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}