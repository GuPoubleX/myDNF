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

public class Rapidmoveslash extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	public String path = SetCharSkill.swordmanskill+"rapidmoveslash/rapidmoveslash.atlas";
	public Rapidmoveslash(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	private int count = 2;
	private Vector2 mv = null;
	private TextureAtlas atlas = null;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
		forceLv = SetCharSkill.lv9;
		mv = move;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(count > 0) {
				if(move != null) {
					if(move.x > 0)
						ch.setDirect(true);
					else if(move.x < 0)
						ch.setDirect(false);
				}
				mv = move;
				int pose = 30;
				anstart = (int) ch.getPoseSet().get(pose).x;
				an = null;//ch.getAn(pose);
				setAttack(ch, 1, SetCharSkill.hitback, new Vector2(3, 0));
				int index = ((int) (Math.random()*100))%2;
				ch.getSound(index==0?72:73, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				for(Animation<TextureRegion> anima : an)
					if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
						anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
				TextureRegion re[] = new TextureRegion[3];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("face/"+i));
				anup = new Animation<TextureRegion>(0.5f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[4];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("s-01/"+i));
				andown = new Animation<TextureRegion>(0.5f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				count--;
				time = 0;
			}else if(count == 0) {
				if(move != null) {
					if(move.x > 0)
						ch.setDirect(true);
					else if(move.x < 0)
						ch.setDirect(false);
				}
				mv = move;
				int pose = 31;
				anstart = (int) ch.getPoseSet().get(pose).x;
				an = null;//ch.getAn(pose);
				setAttack(ch, 1, SetCharSkill.hitfly, new Vector2(0, 5));
				ch.getSound(74, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
				for(Animation<TextureRegion> anima : an)
					if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
						anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
				TextureRegion re[] = new TextureRegion[3];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("face/"+i));
				anup = new Animation<TextureRegion>(0.5f/re.length, re);
				anup.setPlayMode(PlayMode.LOOP);
				re = new TextureRegion[4];
				for(int i = 0; i < re.length; i++)
					re[i] = new TextureRegion(atlas.findRegion("s-02/"+i));
				andown = new Animation<TextureRegion>(0.5f/re.length, re);
				andown.setPlayMode(PlayMode.LOOP);
				count--;
				time = 0;
			}else
				endPose = true;
		}
		ch.move(10*(ch.isRight()?1:-1), (mv==null||mv.y==0)?0:3/SetBase.fix*(mv.y>0?1:-1));
		for(Animation<TextureRegion> anima : an) {
			Sprite sp = null;//new Sprite(getAnimation(ch, anima, true, false));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
					ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			sp.draw(batch, parentAlpha);
		}
		Sprite sp = null;//new Sprite(getAnimation(ch, andown, false, time));
		sp.flip(!ch.isRight(), false);
		sp.setPosition(ch.getX()-sp.getWidth()/2+(ch.isRight()?1:-1)*40, ch.getY()-sp.getHeight()/2+ch.getZ()+60);
		sp.draw(batch, parentAlpha);
		sp = null;//new Sprite(getAnimation(ch, anup, false));
		sp.flip(!ch.isRight(), false);
		sp.setOrigin(sp.getWidth()/2, sp.getHeight()/2);
		sp.setScale(1.5f, 1.5f);
		sp.setPosition(ch.getX()-sp.getWidth()/2+(ch.isRight()?1:-1)*20, ch.getY()-sp.getHeight()/2+ch.getZ()+40);
		sp.draw(batch, parentAlpha);
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		count = 2;
		forceLv = SetCharSkill.lv2;
		mv = null;
		super.end(ch);
	}
	private void setAttack(Character ch, float during, int hittype, Vector2 Charactermove) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, false, ch.isRight(), ch, during, 0.5f, hittype, ch.getLineV(), Charactermove, 0);
		tmp.setAttack(new Rectangle(0,0,144,106), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
	private void getAnimation(Character ch) {
		int pose = 30;
		anstart = (int) ch.getPoseSet().get(pose).x;
		an = null;//ch.getAn(pose);
		setAttack(ch, 1, SetCharSkill.hitback, new Vector2(1, 0));
		ch.getSound(75, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		for(Animation<TextureRegion> anima : an)
			if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
		atlas = ch.getManager().get(path, TextureAtlas.class);
		TextureRegion re[] = new TextureRegion[3];
		for(int i = 0; i < re.length; i++)
			re[i] = new TextureRegion(atlas.findRegion("face/"+i));
		anup = new Animation<TextureRegion>(0.5f/re.length, re);
		anup.setPlayMode(PlayMode.LOOP);
		re = new TextureRegion[4];
		for(int i = 0; i < re.length; i++)
			re[i] = new TextureRegion(atlas.findRegion("s-01/"+i));
		andown = new Animation<TextureRegion>(0.1f, re);
		andown.setPlayMode(PlayMode.LOOP);
	}
}