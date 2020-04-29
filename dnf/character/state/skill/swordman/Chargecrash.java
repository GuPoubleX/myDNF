package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.Texture;
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

public class Chargecrash extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	private Texture getAnimation(Character ch, Animation<TextureRegion> an, boolean a, boolean b) {
		return null;
	}
	private Texture getAnimation(Character ch, Animation<TextureRegion> an, boolean a) {
		return null;
	}
	private String path = SetCharSkill.swordmanskill+"chargecrash/chargecrash.atlas";
	public Chargecrash(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	private int fix_x = 60;
	private boolean moving = true;
	private TextureAtlas atlas = null;
	private Vector2 p = null;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
		p = new Vector2(0, 0);
		forceLv = SetCharSkill.lv9;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(moving)
			ch.move(ch.isRight()?3:-3, 0);
		else
			ch.move(0, 0);
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(ex != null) {
				an = ex;
				ex = null;
				anstart = (int) ch.getPoseSet().get(31).x;
				for(Animation<TextureRegion> anima : an)
					if(anima.getFrameDuration() != 0.3f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length)
						anima.setFrameDuration(0.3f*ch.getProperty("SPHY_WEAPON")/anima.getKeyFrames().length);
				moving = false;
				
				TextureRegion re[] = new TextureRegion[4];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("dustdashlast/"+i));
				andown = new Animation<TextureRegion>(0.4f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[5];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("up-slash/"+i));
				anup = new Animation<TextureRegion>(0.5f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				p = new Vector2((ch.isRight()?1:-1)*40, 50);
				time = 0;
				setAttack(ch, 0.5f, SetCharSkill.hitfly, new Vector2(3, 4));
			}else
				endPose = true;
		}
		for(Animation<TextureRegion> anima : an) {
			Sprite sp = new Sprite(getAnimation(ch, anima, true, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+
					(ch.isRight()?0:ch.getFix2().x)+(moving?1:0)*(ch.isRight()?-1:1)*fix_x,
					ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			sp.draw(batch, parentAlpha);
		}
		if(andown != null) {
			Sprite sp = new Sprite(getAnimation(ch, andown, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2, ch.getY()-sp.getHeight()/2+ch.getZ());
			sp.draw(batch, parentAlpha);
		}
		if(anup != null) {
			Sprite sp = new Sprite(getAnimation(ch, anup, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+p.x, ch.getY()-sp.getHeight()/2+ch.getZ()+30+p.y);
			sp.draw(batch, parentAlpha);
		}
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		moving = true;
		forceLv = SetCharSkill.lv2;
		super.end(ch);
	}
	private void setAttack(Character ch, float during, int hittype, Vector2 Charactermove, int loop) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, aj, ch.isRight(), ch, during, 0.5f, hittype, ch.getLineV(), Charactermove, loop);
		tmp.setAttack(new Rectangle(0,0,100,65), new Vector2(0, 0), new Vector2(-52, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
	private void setAttack(Character ch, float during, int hittype, Vector2 Charactermove) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, false, ch.isRight(), ch, during, 0.5f, hittype, ch.getLineV(), Charactermove, 0);
		tmp.setAttack(new Rectangle(0,0,100,180), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
	private void getAnimation(Character ch) {
		int pose = 33;
		//an = ch.getAn(pose);
		//ex = ch.getAn(31);
		anstart = (int) ch.getPoseSet().get(pose).x;
		setAttack(ch, 0.1f, SetCharSkill.hitback, new Vector2(3.5f, 0), 3);
		for(Animation<TextureRegion> anima : an)
			if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
		atlas = ch.getManager().get(path, TextureAtlas.class);
		TextureRegion re[] = new TextureRegion[4];
		for(int i = 0; i < re.length; i++)
			re[i] = new TextureRegion(atlas.findRegion("dash/"+i));
		anup = new Animation<TextureRegion>(0.5f/re.length, re);
		anup.setPlayMode(PlayMode.LOOP);
	}
}