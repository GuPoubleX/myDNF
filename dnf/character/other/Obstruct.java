package dnf.character.other;

import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;

public class Obstruct extends Character {
	public Obstruct(GuPoubleXGame game, String name, SpriteTexture st, boolean town, Vector2 fix) {
		super(game, name, st);
		if(town)
			enterTown();
		else
			enterInstance(new Vector2(0, 0));
		this.fix = fix;
	}
	public Obstruct(GuPoubleXGame game, String name, SpriteTexture st[], boolean town, Vector2 fix) {
		super(game, name, st);
		if(town)
			enterTown();
		else
			enterInstance(new Vector2(0, 0));
		this.fix = fix;
	}
}