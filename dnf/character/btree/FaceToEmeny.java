package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;

public class FaceToEmeny extends LeafTask<Character> {
	@Override
	public Status execute() {
		getObject().currentPose().setMove(new Vector2(1*(getObject().isRight()?-1:1), 0));
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}