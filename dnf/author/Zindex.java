package dnf.author;

import java.util.Comparator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Zindex implements Comparator<Actor> {
	@Override
	public int compare(Actor frontActor, Actor backActor) {
		if(frontActor.getY() < backActor.getY())
			return 1;
		else if(frontActor.getY() == backActor.getY())
			return 0;
		else
			return -1;
	}
}