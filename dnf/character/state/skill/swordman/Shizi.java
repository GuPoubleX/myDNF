package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import dnf.character.Character;
import dnf.character.part.SkillPose;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public class Shizi extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	public Shizi(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(ex != null) {
				for(Animation<TextureRegion> anima : ex)
					if(anima.getFrameDuration() != 0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
						anima.setFrameDuration(0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
				an = ex;
				ex = null;
				time = 0;
			}else
				endPose = true;
		}
		for(Animation<TextureRegion> anima : an) {
			Sprite sp = null;//new Sprite(getAnimation(ch, anima, true, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
					ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			sp.draw(batch, parentAlpha);
		}
		drawPolygon(ch, batch, delta);
	}
	private void getAnimation(Character ch) {
		an = null;//ch.getAn(1);
		ex = null;//ch.getAn(31);
		for(Animation<TextureRegion> anima : an)
			if(anima.getFrameDuration() != 0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
	}
}