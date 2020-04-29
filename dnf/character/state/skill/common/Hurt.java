package dnf.character.state.skill.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.Pose;
import dnf.character.part.SkillPose;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;
import dnf.gupoublex.set.SetImg;

public class Hurt extends SkillPose {
	public Hurt(GuPoubleXGame game, float time) {
		super(game, SetCharSkill.lv7);
		duringtime = time;
	}
	public Hurt(GuPoubleXGame game, float time, int forceLv) {
		super(game, forceLv);
		duringtime = time;
	}
	private Array<RoleAnimation> ep1 = null;
	private Array<RoleAnimation> ep2 = null;
	private RoleAnimation slashlarge = null;
	private int index = 0;
	private int index2 = 0;
	private float h = 0;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
		forceLv = SetCharSkill.lv7;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		duringtime -= delta;
		if(h < ch.getZ())
			h = ch.getZ();
		if(h > 0 && ch.getZ() == 0)
			ch.change(new Lie(ch.getGame(), an, index));
		else if(duringtime <= 0)
			if(h == 0)
				endPose = true;
		for(RoleAnimation anima : an) {
			int i = anima.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			getBody(ch, i, true, false);
		}
		if(time <= slashlarge.getAnimationDuration()-SetBase.step)
			slashlarge.draw(false, false, batch, delta, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), new Vector2(0, ch.getFix().y*2/3),  new Vector2(0, 0), !ch.isRight());
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		ep1 = null;
		ep2 = null;
		slashlarge = null;
		h = 0;
		super.end(ch);
	}
	private void getSlashlarge(Character ch, int i) {
		SpriteTexture st[] = null;
		if(i == 0) {
			st = new SpriteTexture[3];
			Img img = ch.getGame().getImg(SetImg.hiteffect, "slashlarge1.img");
			st[0] = img.getIndexST(0);
			st[1] = img.getIndexST(1);
			st[2] = img.getIndexST(2);
		}else if(i == 1) {
			st = new SpriteTexture[3];
			Img img = ch.getGame().getImg(SetImg.hiteffect, "slashlarge2.img");
			st[0] = img.getIndexST(0);
			st[1] = img.getIndexST(1);
			st[2] = img.getIndexST(2);
		}else if(i == 2) {
			st = new SpriteTexture[3];
			Img img = ch.getGame().getImg(SetImg.hiteffect, "slashlarge3.img");
			st[0] = img.getIndexST(0);
			st[1] = img.getIndexST(1);
			st[2] = img.getIndexST(2);
		}
		if(st != null) {
			slashlarge = new RoleAnimation(0.1f, st);
		}
	}
	private void getAnimation(Character ch) {
		index = ((int) (Math.random()*100))%2;
		index2 = ((int) (Math.random()*300))%3;
		if(ch instanceof Swordman) {
			ep1 = ch.getAn(12);
			ep2 = ch.getAn(13);
			anstart = (int) ch.getPoseSet().get(index==0?12:13).x;
			ch.getSound(index==0?28:29, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		}
		an = (index==0?ep1:ep2);
		getSlashlarge(ch, index2);
	}
	public void changeAnimation(Character ch, Pose p) {
		if(index == 1) {
			index = 0;
			ep1 = ch.getAn(12);
			an = ep1;
			anstart = (int) ch.getPoseSet().get(12).x;
		}else {
			index = 1;
			ep2 = ch.getAn(13);
			an = ep2;
			anstart = (int) ch.getPoseSet().get(13).x;
		}
		int i = ((int) (Math.random()*300))%3;
		if(i != index2)
			index2 = i;
		else {
			if(i == 0) {
				if(index == 0)
					index2 = 1;
				else
					index2 = 2;
			}else if(i == 1) {
				if(index == 0)
					index2 = 0;
				else
					index2 = 2;
			}else if(i == 2) {
				if(index == 0)
					index2 = 0;
				else
					index2 = 1;
			}
		}
		getSlashlarge(ch, index2);
		ch.getSound(index==0?28:29, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		if(p.getDuringtime() >= duringtime)
			duringtime = p.getDuringtime();
		time = 0;
	}
}