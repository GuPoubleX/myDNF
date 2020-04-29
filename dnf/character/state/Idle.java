package dnf.character.state;

import com.badlogic.gdx.graphics.g2d.Batch;
import dnf.character.part.Pose;
import dnf.gupoublex.GuPoubleXGame;
import dnf.character.Character;

public class Idle extends Pose {
	public Idle(GuPoubleXGame game) {
		super(game);
	}
	@Override
	public void start(Character ch) {
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
	}
}