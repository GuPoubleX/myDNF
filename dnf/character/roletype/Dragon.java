package dnf.character.roletype;

import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;

public class Dragon extends Character {
	public Dragon(GuPoubleXGame game, String rolename, int fixname, int emeny) {
		super(game, rolename, fixname, emeny);
		label.add(9);
	}
}