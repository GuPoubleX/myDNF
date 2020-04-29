package dnf.character.state.skill.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.AttackJudge;
import dnf.character.part.SkillPose;
import dnf.character.roletype.humen.Swordman;
import dnf.character.state.buff.swordman.Frenzy;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;
import dnf.gupoublex.set.SetSwordmanSkill;

public class Attack extends SkillPose {
	public Attack(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	public Attack(GuPoubleXGame game) {
		super(game, SetCharSkill.lv2);
	}
	private int pose = 0;
	private int count = 1;
	private int stage = 1;
	private int exanstart = 0;
	private boolean jumping = false;
	private boolean change1 = false;
	private boolean change2 = false;
	@Override
	public void start(Character ch) {
		getAnimation(ch);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(ch.getZ() == 0 && jumping)
			endPose = true;
		if(move != null) {
			if(ch.getZ() > 0)
				ch.move(move.x/2, move.y/2);
			else
				ch.move((ch.isRight()&&move.x>0)?move.x/5:((!ch.isRight()&&move.x<0)?move.x/5:0), 0);
		}
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(ex != null) {
				stage++;
				getSwordmanChange(ch);
				an = ex;
				anstart = exanstart;
				change1 = false;
				change2 = false;
				//resetRectangle(ch);
				ex = null;
				time = 0;
			}else {
				if(ch.getZ() > 0)
					ch.change(new Jump(ch.getGame(), SetCharSkill.lv3));
				else
					endPose = true;
			}
		}
		if(time >= an.get(0).getAnimationDuration()*1/3 && !change1) {
			change1 = true;
			resetRectangle1(ch);
		}
		if(time >= an.get(0).getAnimationDuration()*2/3 && !change2) {
			change2 = true;
			resetRectangle2(ch);
		}
		if(andown != null)
			andown.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		for(RoleAnimation anima : an) {
			int i = anima.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			getBody(ch, i, true, false);
		}
		if(anup != null)
			anup.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		pose = 0;
		count = 1;
		stage = 1;
		change1 = false;
		change2 = false;
		jumping = false;
		super.end(ch);
	}
	@Override
	public void changeAnimation(Character ch) {
		if(jumping)
			return;
		int lg = ch.havBuff(new Frenzy())?4:3;
		if(count < lg && ex == null) {
			if(ch instanceof Swordman) {
				pose += 1;
				exanstart = (int) ch.getPoseSet().get(pose).x;
			}
			ex = ch.getAn(pose);
			count++;
			for(RoleAnimation anima : ex)
				if(anima.getFrameDuration() != 1.0f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
					anima.setFrameDuration(1.0f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
		}
	}
	private void resetRectangle1(Character ch) {
		if(ch instanceof Swordman) {
			if(stage == 0) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
				if(ch.getZ() > 0 && count == 1) {
					resetSelf(ch);
				}
			}else if(stage == 1) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}else if(stage == 2) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}else if(stage == 3) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}
		}
	}
	private void resetRectangle2(Character ch) {
		if(!ch.havBuff(new Frenzy()))
			return;
		if(ch instanceof Swordman) {
			if(stage == 0) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
				if(ch.getZ() > 0 && count == 1)
					resetSelf(ch);
			}else if(stage == 1) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}else if(stage == 2) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, ch, 0.5f, 0.8f);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}else if(stage == 3) {
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 100, 0, false, ch.isRight(), ch, 0.5f, 0.8f, SetCharSkill.hitlie, null, null, 0);
				tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
				aj.add(tmp);
			}
		}
	}
	private void getAnimation(Character ch) {
		if(ch instanceof Swordman) {
			pose = ch.havBuff(new Frenzy())?29:0;
			anstart = (int) ch.getPoseSet().get(pose).x;
			if(ch.getZ() > 0) {
				jumping = true;
				pose = 22;
			}else
				getSwordmanChange(ch);
		}
		an = ch.getAn(pose);
		for(RoleAnimation anima : an)
			if(anima.getFrameDuration() != 1.0f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
				anima.setFrameDuration(1.0f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
	}
	private void getSwordmanChange(Character ch) {
		if(ch.havBuff(new Frenzy())) {
			int start = 0;
			int length = 0;
			if(stage == 1) {
				start = 0;
				length = 6;
			}else if(stage == 2) {
				start = 6;
				length = 5;
			}else if(stage == 3) {
				start = 11;
				length = 5;
			}else if(stage == 4) {
				start = 16;
				length = 6;
			}
			Img img1 = ch.getGame().getImg(SetSwordmanSkill.frenzy, "sword_blood_under.img");
			Img img2 = ch.getGame().getImg(SetSwordmanSkill.frenzy, "sword_blood_upper.img");
			SpriteTexture st1[] = new SpriteTexture[length];
			SpriteTexture st2[] = new SpriteTexture[length];
			for(int j = 0; j < length; j++) {
				st1[j] = img1.getIndexST(start+j);
				st2[j] = img2.getIndexST(start+j);
			}
			andown = new RoleAnimation(1.0f*ch.getProperty("SPHY_WEAPON")/length, st1);
			anup = new RoleAnimation(1.0f*ch.getProperty("SPHY_WEAPON")/length, st2);
		}else {
			if(andown != null)
				andown = null;
			if(anup != null)
				anup = null;
		}
	}
}