package dnf.character.state;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.Pose;
import dnf.gupoublex.GuPoubleXGame;

public class ObstructState extends Pose {
	public ObstructState(GuPoubleXGame game) {
		super(game);
	}
	private Array<RoleAnimation> an = null;
	@Override
	public void start(Character ch) {
		an = ch.getAn(0);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		for(RoleAnimation anima : an)
			anima.draw(false, true, batch, delta, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
	}
}