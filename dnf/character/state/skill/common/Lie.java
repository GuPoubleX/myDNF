package dnf.character.state.skill.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.SkillPose;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public class Lie extends SkillPose {
	public Lie(GuPoubleXGame game, Array<RoleAnimation> an, int index) {
		super(game, an, index, SetCharSkill.lv8);
		duringtime = 1;
	}
	public Lie(GuPoubleXGame game) {
		super(game, SetCharSkill.lv8);
		duringtime = 1;
	}
	private float lietime = 0;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		if(index != -1) {
			if(lietime >= 0.05f) {
				time += delta;
				duringtime -= delta;
				resetSelf(ch);
				ch.findEmeny(null);
				forceLv = SetCharSkill.lv8;
			}else
				forceLv = SetCharSkill.lv6;
			lietime += delta;
		}
		if(duringtime <= 0)
			endPose = true;
		if(index == -1) {
			time += delta;
			duringtime -= delta;
			for(RoleAnimation anima : an) {
				if(time >= anima.getAnimationDuration()-SetBase.step)
					time = anima.getAnimationDuration()-SetBase.step;
		//		Sprite sp = null;//new Sprite(getAnimation(ch, anima, true, true));
		//		sp.flip(!ch.isRight(), false);
		//		sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
		//				ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
		//		sp.draw(batch, parentAlpha);
				anima.draw(false, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			}
		}else {
			if(lietime <= 0.05f)
				for(RoleAnimation anima : ex)
			//		Sprite sp = null;//new Sprite(getAnimation(ch, anima, true, false));
			//		sp.flip(!ch.isRight(), false);
			//		sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
			//				ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			//		sp.draw(batch, parentAlpha);
			//	}
					anima.draw(false, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			else
				for(RoleAnimation anima : an)
			//		if(time >= anima.getAnimationDuration()-SetBase.step)
			//			time = anima.getAnimationDuration()-SetBase.step;
			//			Sprite sp = null;//new Sprite(getAnimation(ch, anima, true, true));
			//			sp.flip(!ch.isRight(), false);
			//			sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
			//					ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			//			sp.draw(batch, parentAlpha);
			//	}
					anima.draw(false, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		}
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		lietime = 0;
		super.end(ch);
	}
	private void getAnimation(Character ch) {
		int pose = 0;
		if(ch instanceof Swordman) {
			pose = 14;
			anstart = (int) ch.getPoseSet().get(index==0?13:12).x;
		}
		an = ch.getAn(pose);
		for(RoleAnimation anima : an)
			if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
	}
}