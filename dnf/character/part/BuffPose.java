package dnf.character.part;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;
import dnf.character.state.buff.swordman.Frenzy;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;
import dnf.gupoublex.set.SetSwordmanSkill;

public class BuffPose extends SkillPose {
	public BuffPose(GuPoubleXGame game, int type, int sound, Buf buff, float cd) {
		super(game, SetCharSkill.lv2, cd);
		this.buff = buff;
		this.buffduring = buff.getPoseTime();
		this.type = type;
		this.sound = sound;
	}
	public BuffPose(GuPoubleXGame game, int type, int sound, BuffActor actor, float cd) {
		super(game, SetCharSkill.lv2, cd);
		this.buffactor = actor;
		this.buffduring = actor.getPoseTime();
		this.type = type;
		this.sound = sound;
	}
	private Vector2 fix = null;
	@Override
	public void start(Character ch) {
		if(buff != null)
			if(buff.isOpen() && ch.havBuff(buff)) {
				ch.addBuff(buff);
				endPose = true;
				return;
			}
		fix = new Vector2(0, 0);
		setBuffAnimation(ch);
		ch.move(0, 0);
		getAnimation(ch);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(buff != null)
				ch.addBuff(buff);
			if(buffactor != null)
				buffactor.play();
			endPose = true;
		}
		if(andown != null)
			andown.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(),
					new Vector2(ch.getFix().x+(ch.isRight()?1:-1)*fix.x, ch.getFix().y+fix.y), ch.getFix2(), !ch.isRight());
		for(RoleAnimation anima : an) {
			int i = anima.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
			getBody(ch, i, true, false);
		}
		if(anup != null)
			anup.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(),
					new Vector2(ch.getFix().x+(ch.isRight()?1:-1)*fix.x, ch.getFix().y+fix.y), ch.getFix2(), !ch.isRight());
		drawPolygon(ch, batch, delta);
	}
	private void getAnimation(Character ch) {
		int pose = 0;
		if(ch instanceof Swordman) {
			pose = (type==0?10:26);
			anstart = (int) ch.getPoseSet().get(pose).x;
			if(buff != null && !(buff.isOpen() && ch.havBuff(buff)))
				ch.getSound(sound, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
			if(buffactor != null)
				ch.getSound(sound, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		}
		an = ch.getAn(pose);
		for(RoleAnimation anima : an)
			if(anima.getFrameDuration() != buffduring*ch.getProperty("SMIG_WEAPON")/anima.getKeyFrames().length)
				anima.setFrameDuration(buffduring*ch.getProperty("SMIG_WEAPON")/anima.getKeyFrames().length);
	}
	private void setBuffAnimation(Character ch) {
		if(!ch.inInstance())
			return;
		RoleAnimation animation = null;
		if(buff != null) {
		/*	if(buff.getClass().equals(Rage.class)) {
				TextureAtlas atlas = ch.getManager().get(SetCharSkill.swordmanskill+"rage/looping_dodge/looping_dodge.atlas", TextureAtlas.class);
				TextureRegion re[] = new TextureRegion[] {
						atlas.findRegion("0"), atlas.findRegion("1"), atlas.findRegion("2"), atlas.findRegion("3"),
						atlas.findRegion("4"), atlas.findRegion("5"), atlas.findRegion("6")};
				animation = new Animation<TextureRegion>(1.0f/re.length, re);
				animation.setPlayMode(PlayMode.LOOP);
			}else*/ if(buff.getClass().equals(Frenzy.class)) {
				Img img = ch.getGame().getImg(SetSwordmanSkill.frenzy, "blood-spirits.img");
				SpriteTexture st[] = new SpriteTexture[img.getCount()];
				for(int i = 0; i < st.length; i++)
					st[i] = img.getIndexST(i);
				animation = new RoleAnimation(1.0f/st.length, st);
				img = ch.getGame().getImg(SetSwordmanSkill.frenzy, "blood-start.img");
				st = new SpriteTexture[img.getCount()];
				for(int i = 0; i < st.length; i++)
					st[i] = img.getIndexST(i);
				anup = new RoleAnimation(buffduring*ch.getProperty("SMIG_WEAPON")/st.length, st);
				fix = new Vector2(-10, 15);
			}
			buff.setAnimation(animation);
		}
	}
}