package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import dnf.character.Character;

public class FaceEmeny extends LeafTask<Character> {
	@Override
	public Status execute() {
		Character emeny = getObject().emenyCharacter();
		boolean face = getObject().isRight();
		boolean faceto = false;
		if(emeny.getX()>=getObject().getX() && face)
			faceto = true;
		if(emeny.getX()<=getObject().getX() && !face)
			faceto = true;
		return faceto?Status.FAILED:Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		return task;
	}
}