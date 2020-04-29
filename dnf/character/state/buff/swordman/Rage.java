package dnf.character.state.buff.swordman;

import dnf.character.part.Buf;

public class Rage extends Buf {
	public Rage() {
		super(20, 1, 1);
		str = 100;
		inT = -50;
		sphy = 1.5f;
		smig = 1.0f;
		smov = 0.5f;
	}
}