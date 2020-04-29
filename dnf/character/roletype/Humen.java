package dnf.character.roletype;

import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;

public class Humen extends Character {
	public Humen(GuPoubleXGame game, String rolename, int fixname, int emeny) {
		super(game, rolename, fixname, emeny);
		label.add(10);
	}
	public Humen(GuPoubleXGame game, int id, int fixname, int emeny) {
		super(game, id, fixname, emeny);
		label.add(10);
	}
}