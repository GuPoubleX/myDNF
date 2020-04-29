package dnf.character.state.buff.swordman;

import dnf.character.part.Buf;
import dnf.character.part.BuffActor;

public class Saya extends Buf {
	public Saya(BuffActor actor) {
		super(0.5f, 1, 1, actor);
		str = -100;
		inT = -100;
	}
}