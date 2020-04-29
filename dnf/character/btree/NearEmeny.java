package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class NearEmeny extends LeafTask<Character> {
	@TaskAttribute(required=true)
	public int radius;
	public NearEmeny() {
		this(1);
	}
	public NearEmeny(int radius) {
		this.radius = radius;
	}
	@Override
	public Status execute() {
		Character emeny = getObject().emenyCharacter();
		int x = (int) (emeny.getX()-getObject().getX());
		int y = (int) ((emeny.getY()-getObject().getY())*SetBase.fixradius);
		if(x*x+y*y <= radius*radius) {
			getObject().set("stay", null);
			return Status.FAILED;
		}else
			return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		((NearEmeny) task).radius = radius;
		return task;
	}
}