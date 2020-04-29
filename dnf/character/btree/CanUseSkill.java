package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.utils.Array;
import dnf.character.Character;
import dnf.hud.button.SkillButton;

public class CanUseSkill extends LeafTask<Character> {
	@Override
	public Status execute() {
		if(getObject().readySkill() != null && !getObject().readySkill().BuffPose())
			return Status.SUCCEEDED;
		Array<SkillButton> sba = new Array<SkillButton>();
		for(SkillButton sb : getObject().getSkillList())
			if(sb.getPose() != null && !sb.BuffPose() && !sb.CDing())
				sba.add(sb);
		if(sba.size == 0)
			return Status.FAILED;
		int i = ((int) (Math.random()*sba.size))%sba.size;
		getObject().readySkill(sba.get(i));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}