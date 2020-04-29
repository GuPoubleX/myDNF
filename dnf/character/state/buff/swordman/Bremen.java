package dnf.character.state.buff.swordman;

import dnf.character.part.Buf;
import dnf.character.part.BuffActor;

public class Bremen extends Buf {
	public Bremen(BuffActor actor) {
		super(0.5f, 1, 1, actor);
		defphy = -200;
		defmig = -200;
	}
}