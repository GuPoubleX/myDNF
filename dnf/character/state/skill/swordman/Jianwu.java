package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.character.Character;
import dnf.character.part.AttackJudge;
import dnf.character.part.SkillPose;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public class Jianwu extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	public Jianwu(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	private Array<Animation<TextureRegion>> play = null;
	private Array<Animation<TextureRegion>> ep = null;
	private Array<Animation<TextureRegion>> ep1 = null;
	private Array<Animation<TextureRegion>> ep2 = null;
	private Array<Animation<TextureRegion>> ep3 = null;
	private int count = 23;
	private int current = 3;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
		forceLv = SetCharSkill.lv9;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(count == 0)
				endPose = true;
			else {
				count--;
				int i = ((int) (Math.random()*400))%4;
				if(i == 0) {
					if(current != i) {
						for(Animation<TextureRegion> anima : ep1)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(0).x;
						an = ep1;
						current = i;
					}else {
						for(Animation<TextureRegion> anima : ep2)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(1).x;
						an = ep2;
						current = 1;
					}
					setAttack(ch);
					ch.getSound(46, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}else if(i == 1) {
					if(current != i) {
						for(Animation<TextureRegion> anima : ep2)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(1).x;
						an = ep2;
						current = i;
					}else {
						for(Animation<TextureRegion> anima : ep3)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(29).x;
						an = ep3;
						current = 2;
					}
					setAttack(ch);
					ch.getSound(47, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}else if(i == 2) {
					if(current != i) {
						for(Animation<TextureRegion> anima : ep3)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(29).x;
						an = ep3;
						current = i;
					}else {
						for(Animation<TextureRegion> anima : play)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(30).x;
						an = play;
						current = 3;
					}
					setAttack(ch);
					ch.getSound(48, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}else {
					if(current != i) {
						for(Animation<TextureRegion> anima : play)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(30).x;
						an = play;
						current = i;
					}else {
						for(Animation<TextureRegion> anima : ep1)
							if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
								anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
						anstart = (int) ch.getPoseSet().get(0).x;
						an = ep1;
						current = 0;
					}
					setAttack(ch);
					ch.getSound(49, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}
				if(count == 1) {
					for(Animation<TextureRegion> anima : ex)
						if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
							anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
					anstart = (int) ch.getPoseSet().get(31).x;
					an = ex;
					setAttack(ch);
					ch.getSound(46, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}else if(count == 0) {
					for(Animation<TextureRegion> anima : ep)
						if(anima.getFrameDuration() != 0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
							anima.setFrameDuration(0.5f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
					anstart = (int) ch.getPoseSet().get(32).x;
					an = ep;
					setAttack(true, ch, 0.3f, SetCharSkill.hitback, new Vector2(5, 0), new Vector2(10, 0), 4);
					ch.getSound(50, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				}
				time = 0;
			}
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
	@Override
	public void end(Character ch) {
		count = 39;
		current = 3;
		play = null;
		ep = null;
		ep1 = null;
		ep2 = null;
		ep3 = null;
		forceLv = SetCharSkill.lv2;
		super.end(ch);
	}
	private void setAttack(Character ch) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 50, 0, ch, 0.5f, 0.5f);
		tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
	private void setAttack(boolean addactor, Character ch, float during, int hittype, Vector2 Attackmove, Vector2 Charactermove, int loop) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 50, 0, addactor, ch.isRight(), ch, during, 0.5f, hittype, Attackmove, Charactermove, loop);
		tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(ch.getX(), ch.getY()+ch.getZ()), new Vector2(0, 0), SetCharSkill.arch);
	}
	private void getAnimation(Character ch) {
		play = null;//ch.getAn(30);
		ep1 = null;//ch.getAn(0);
		ep2 = null;//ch.getAn(1);
		ep3 = null;//ch.getAn(29);
		ex = null;//ch.getAn(31);
		ep = null;//ch.getAn(32);
		an = play;
		anstart = (int) ch.getPoseSet().get(30).x;
		ch.getSound(46, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		for(Animation<TextureRegion> anima : play)
			if(anima.getFrameDuration() != 0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
				anima.setFrameDuration(0.15f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
	}
}