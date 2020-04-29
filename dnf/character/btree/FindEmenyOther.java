package dnf.character.btree;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class FindEmenyOther extends LeafTask<Character> {
	@TaskAttribute(required=true)
	public int radius;
	public FindEmenyOther() {
		this(1);
	}
	public FindEmenyOther(int radius) {
		this.radius = radius;
	}
	@Override
	public Status execute() {
		int min = 0;
		for(Actor ac : getObject().getParent().getChildren())
			if((ac instanceof Character) && ((Character) ac).isEmeny(getObject().getEmeny())) {
				int x = (int) (((Character) ac).getX()-getObject().getX());
				int y = (int) ((((Character) ac).getY()-getObject().getY())*SetBase.fixradius);
				boolean see = false;
				if(getObject().isRight()) {
					if(ac.getX() >= getObject().getX())
						see = true;
				}else {
					if(ac.getX() <= getObject().getX())
						see = true;
				}
				if(see) {
					Array<Character> emeny = new Array<Character>();
					if(x*x+y*y <= radius*radius) {
						if(min == 0) {
							min = x*x+y*y;
							emeny.add((Character) ac);
						}else if(min > x*x+y*y) {
							min = x*x+y*y;
							emeny.add((Character) ac);
						}
					}
					if(emeny.size != 0) {
						int i = ((int) (Math.random()*emeny.size))%emeny.size;
						getObject().findEmeny(emeny.get(i));
					}
				}
			}
		return Status.SUCCEEDED;
	}
	@Override
	protected Task<Character> copyTo(Task<Character> task) {
		((FindEmenyOther) task).radius = radius;
		return task;
	}
}