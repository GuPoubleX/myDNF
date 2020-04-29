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
import dnf.gupoublex.set.SetCharSkill;

public class Yinluo extends SkillPose {
	private Array<Animation<TextureRegion>> an = null;
	private Array<Animation<TextureRegion>> ex = null;
	private Animation<TextureRegion> anup = null;
	private Animation<TextureRegion> andown = null;
	public Yinluo(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv3, cd);
	}
	private float power = -4;
	private float h = 0;
	@Override
	public void start(Character ch) {
		getAnimation(ch);
		h = ch.getZ();
		ch.jump(0);
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(ch.getZ() > 0) {
			ch.move((ch.isRight()?1:-1), 0);
			ch.jump(power);
		}else {
			ch.move(0, 0);
			if(ex != null) {
				an = ex;
				ex = null;
				time = 0;
				if(aj == null)
					aj = new Array<AttackJudge>();
				AttackJudge tmp = new AttackJudge(true, 50, 0, ch, 0.5f, 0.5f);
				tmp.setAttack(new Rectangle(0,0,60,10), new Vector2(-30, 0), new Vector2(0, 0), SetCharSkill.circle);
				aj.add(tmp);
				if(h >= 80) {
					tmp = new AttackJudge(true, 50, 0, ch, 0.5f, 0.2f);
					tmp.setAttack(new Rectangle(0,0,60,10), new Vector2(-30, 0), new Vector2(0, 0), SetCharSkill.circle);
					aj.add(tmp);
				}
			}else if(time >= 0.2f)
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
	@Override
	public void end(Character ch) {
		h = 0;
		super.end(ch);
	}
	private void getAnimation(Character ch) {
		an = null;//ch.getAn(34);
		ex = null;//ch.getAn(25);
	}
}