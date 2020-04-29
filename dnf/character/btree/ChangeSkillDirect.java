package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.character.state.skill.swordman.Rapidmoveslash;

public class ChangeSkillDirect extends LeafTask<Character> {
	@Override
	public Status execute() {
		Character ch = getObject();
		Character emeny = ch.emenyCharacter();
		float x = emeny.getX()-ch.getX();
		float y = emeny.getY()-ch.getY();
		if(ch.currentPose().getClass().equals(Rapidmoveslash.class))
			ch.currentPose().setMove(new Vector2(x, y));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}