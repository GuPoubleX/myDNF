package dnf.character.roletype;

import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;

public class Beast extends Character {
	public Beast(GuPoubleXGame game, String rolename, int fixname, int emeny) {
		super(game, rolename, fixname, emeny);
		label.add(1);
	}
}