package dnf.character.state;

import dnf.character.part.Pose;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import com.badlogic.gdx.graphics.g2d.Batch;
import dnf.author.RoleAnimation;
import dnf.character.Character;

public class Stay extends Pose {
	public Stay(GuPoubleXGame game) {
		super(game);
	}
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		getAnimation(ch);
		for(RoleAnimation anima : an) {
			int i = anima.draw(false, true, batch, delta, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			getBody(ch, i, true, false);
		}
		drawPolygon(ch, batch, delta);
	}
	private void getAnimation(Character ch) {
		int pose = 0;
		if(ch instanceof Swordman) {
			pose = ch.inInstance()?11:27;
			anstart = (int) ch.getPoseSet().get(pose).x;
		}
		if(an != ch.getAn(pose)) {
			an = ch.getAn(pose);
			for(RoleAnimation anima : an)
				if(anima.getFrameDuration() != 2.0f/anima.getKeyFrames().length)
					anima.setFrameDuration(2.0f/anima.getKeyFrames().length);
		}
	}
}