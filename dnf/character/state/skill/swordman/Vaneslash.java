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
import dnf.character.part.GrapPose;
import dnf.character.part.GraspAction;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public class Vaneslash extends GrapPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	public Vaneslash(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	private AttackJudge tmp = null;
	private boolean lose = false;
	private boolean loop = true;
	private GraspAction ga = null;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		getAnimation(ch);
		forceLv = SetCharSkill.lv9;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(loop && time >= an.get(0).getAnimationDuration()-SetBase.step) {
			if(tmp != null && tmp.hit() != null) {
				an = ex;
				time = 0;
				lose = true;
				loop = false;
				anstart = (int) ch.getPoseSet().get(32).x;
				ga = new GraspAction(ch, tmp.hit().first(), new Vector2(150, 0), new Vector2(0, 50), 3.5f);
			}else
				endPose = true;
		}else if(!loop) {
			if(time >= 1) {
				tmp.hit().first().Grasp(false);
				tmp.hit().first().setZ(0, tmp.hit().first().isGrasp());
				tmp.hit().first().move(4*(tmp.hit().first().isRight()?-1:1), 0);
				endPose = true;
			}else
				ga.update(delta);
		}
		for(Animation<TextureRegion> anima : an) {
			Sprite sp = null;//new Sprite(getAnimation(ch, anima, loop, lose));
			sp.flip(!ch.isRight(), false);
			sp.setPosition(ch.getX()-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
					ch.getY()-sp.getHeight()/2+ch.getFix().y+ch.getZ());
			sp.draw(batch, parentAlpha);
		}
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		forceLv = SetCharSkill.lv2;
		ga = null;
		tmp = null;
		lose = false;
		loop = true;
		super.end(ch);
	}
	private void setAttack(Character ch, float during, int hittype, Vector2 Charactermove) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		tmp = new AttackJudge(false, -100, 0, ch, during, 1, hittype, true);
		tmp.setAttack(new Rectangle(0,0,100,65), new Vector2(0, 0), new Vector2(-52, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
	private void getAnimation(Character ch) {
		int pose = 31;
		an = null;//ch.getAn(pose);
		ex = null;//ch.getAn(32);
		anstart = (int) ch.getPoseSet().get(pose).x;
		setAttack(ch, 0.5f, SetCharSkill.hitgrap, new Vector2(0, 0));
		for(Animation<TextureRegion> anima : an)
			if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
		for(Animation<TextureRegion> anima : ex)
			if(anima.getFrameDuration() != 0.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(0.5f/anima.getKeyFrames().length);
	}
}