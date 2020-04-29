package dnf.character.state.buff.swordman;

import dnf.character.part.Buf;
import dnf.character.part.BuffActor;

public class Khazan extends Buf {
	public Khazan(BuffActor actor) {
		super(0.5f, 1, 1, actor);
		str = 200;
		inT = 200;
	}
}