package dnf.character.state;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.Pose;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;

public class Move extends Pose {
	public Move(GuPoubleXGame game, Vector2 move) {
		super(game, move);
	}
	@Override
	public void start(Character ch) {
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		getAnimation(ch);
		if(move != null) {
			if(move.x > 0)
				ch.setDirect(true);
			else if(move.x <0)
				ch.setDirect(false);
			ch.move(move.x, move.y);
		}
		for(RoleAnimation anima : an) {
			int i = anima.draw(false, true, batch, delta, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			getBody(ch, i, true, false);
		}
		drawPolygon(ch, batch, delta);
	}
	private void getAnimation(Character ch) {
		int pose = 0;
		if(ch instanceof Swordman) {
			pose = ch.inInstance()?15:28;
			anstart = (int) ch.getPoseSet().get(pose).x;
		}
		if(an != ch.getAn(pose)) {
			an = ch.getAn(pose);
			for(RoleAnimation anima : an) {
				if(ch.inInstance()) {
					if(anima.getFrameDuration() != 2.0f/(1+ch.getProperty("SMOV"))/anima.getKeyFrames().length)
						anima.setFrameDuration(2.0f/(1+ch.getProperty("SMOV"))/anima.getKeyFrames().length);
				}else {
					if(anima.getFrameDuration() != 2.0f/anima.getKeyFrames().length)
						anima.setFrameDuration(2.0f/anima.getKeyFrames().length);
				}
			}
		}
	}
}