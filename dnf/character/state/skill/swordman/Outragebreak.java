package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dnf.character.Character;
import dnf.character.part.AttackJudge;
import dnf.character.part.SkillPose;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public class Outragebreak extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	private String path = SetCharSkill.swordmanskill+"outragebreak/outragebreak.atlas";
	private Array<Animation<TextureRegion>> ep = null;
	private TextureAtlas atlas = null;
	private float h = 0;
	private boolean attack = false;
	private boolean change = false;
	private boolean first = false;
	private float lasttime = 1;
	private Vector2 p = null;
	public Outragebreak(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		p = new Vector2(0, 0);
		getAnimation(ch);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(h == 0)
			h = ch.getZ();
		else {
			if(h <= ch.getZ())
				h = ch.getZ();
			if(h > ch.getZ() && !change) {
				change = true;
				for(Animation<TextureRegion> anima : ep)
					if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
						anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
				an = ep;
				ch.move(ch.isRight()?0.5f:-0.5f, 0);
				ch.jump(-3);
				TextureRegion re[] = new TextureRegion[6];
				re[0] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+12));
				re[1] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+11));
				re[2] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+13));
				re[3] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+14));
				re[4] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+14));
				re[5] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+14));
				andown = new Animation<TextureRegion>(0.5f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[6];
				re[0] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+12));
				re[1] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+11));
				re[2] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+13));
				re[3] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+14));
				re[4] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+14));
				re[5] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+14));
				anup = new Animation<TextureRegion>(0.5f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				p = new Vector2((ch.isRight()?1:-1)*160, 67);
				ep = null;
				time = 0;
			}
		}
		if(change && ch.getZ() == 0) {
			if(!first) {
				first = true;
				TextureRegion re[] = new TextureRegion[5];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+(15+i)));
				andown = new Animation<TextureRegion>(0.5f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[5];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+(15+i)));
				anup = new Animation<TextureRegion>(0.5f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				p = new Vector2((ch.isRight()?1:-1)*160, 65);
				OutragebreakFloor of = new OutragebreakFloor(atlas, 5, new Vector2(ch.getX()+(ch.isRight()?1:-1)*100, ch.getY()),ch.isRight());
				ch.getParent().addActor(of);
				setAttack(true, ch, 0.1f, SetCharSkill.hitfly, new Vector2(0, 0), new Vector2(0, 2.5f), 0);
			}
			lasttime -= delta;
			ch.move(0, 0);
			if(!attack && lasttime <= 0.5f) {
				attack = true;
				setAttack(true, ch, 0.1f, SetCharSkill.hitfly, new Vector2(0, 0), new Vector2(0, 1.2f), 4);
			}
		}
		if(ch.getZ() == 0 && change && lasttime <= 0)
			endPose = true;
		if(time >= an.get(0).getAnimationDuration()-SetBase.step && !change) {
			if(ex != null) {
				an = ex;
				ch.jump(5);
				ch.move(ch.isRight()?2:-2, 0);
				TextureRegion re[] = new TextureRegion[1];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+(8+i)));
				andown = new Animation<TextureRegion>(0.3f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[1];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+(8+i)));
				anup = new Animation<TextureRegion>(0.3f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				p = new Vector2((ch.isRight()?1:-1)*163, 78);
				ex = null;
				time = 0;
			}
		}
		for(Animation<TextureRegion> anima : an) {
			if(time >= anima.getAnimationDuration()-SetBase.step && change)
				time = anima.getAnimationDuration()-SetBase.step;
				Sprite sp = new Sprite(anima.getKeyFrame(time, true));
				sp.flip(!ch.isRight(), false);
				sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
						ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
				sp.draw(batch, parentAlpha);
		}
		if(andown != null) {
			Sprite sp = null;//new Sprite(getAnimation(ch, andown, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+p.x, ch.getY()-sp.getHeight()/2+ch.getZ()+p.y);
			sp.draw(batch, parentAlpha);
		}
		if(anup != null) {
			Sprite sp = null;//new Sprite(getAnimation(ch, anup, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+p.x, ch.getY()-sp.getHeight()/2+ch.getZ()+p.y);
			sp.draw(batch, parentAlpha);
		}
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		h = 0;
		attack = false;
		lasttime = 1;
		first = false;
		change = false;
		super.end(ch);
	}
	private void setAttack(boolean addactor, Character ch, float during, int hittype, Vector2 Attackmove, Vector2 Charactermove, int loop) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, addactor, ch.isRight(), ch, during, 0.5f, hittype, Attackmove, Charactermove, loop);
		tmp.setAttack(new Rectangle(0,0,300,100), new Vector2(ch.getX(), ch.getY()+ch.getZ()), new Vector2(-100, 0), SetCharSkill.circle);
	}
	private void getAnimation(Character ch) {
		an = null;//ch.getAn(17, false, true);
		ex = null;//ch.getAn(18, false, true);
		ep = null;//ch.getAn(32, false, true);
		for(Animation<TextureRegion> anima : an)
			if(anima.getFrameDuration() != 0.3f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.3f/anima.getKeyFrames().length);
		for(Animation<TextureRegion> anima : ex)
			if(anima.getFrameDuration() != 0.3f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.3f/anima.getKeyFrames().length);
		atlas = ch.getManager().get(path, TextureAtlas.class);
		TextureRegion re[] = new TextureRegion[8];
		for(int i = 0; i < re.length; i++)
			re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_ldodge/"+i));
		andown = new Animation<TextureRegion>(0.3f/re.length, re);
		andown.setPlayMode(PlayMode.LOOP);
		re = new TextureRegion[8];
		for(int i = 0; i < re.length; i++)
			re[i] = new TextureRegion(atlas.findRegion("outragebreak_bloodsword_none/"+i));
		anup = new Animation<TextureRegion>(0.3f/re.length, re);
		anup.setPlayMode(PlayMode.LOOP);
		p = new Vector2((ch.isRight()?1:-1)*163, 68);
	}
}