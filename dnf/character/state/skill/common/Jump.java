package dnf.character.state.skill.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.SkillPose;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetCharSkill;

public class Jump extends SkillPose {
	public Jump(GuPoubleXGame game) {
		super(game, SetCharSkill.lv2);
	}
	public Jump(GuPoubleXGame game, int lv) {
		super(game, lv);
		jump = false;
	}
	private float h = 0;
	private boolean change = false;
	private boolean jump = true;
	@Override
	public void start(Character ch) {
		getAnimation(ch);
		if(jump)
			ch.jump(5);
		else {
			h = ch.getZ();
			forceLv = SetCharSkill.lv2;
		}
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(move != null)
			ch.move(move.x/2, move.y/2);
		if(h == 0)
			h = ch.getZ();
		else {
			if(h <= ch.getZ())
				h = ch.getZ();
			if(h > ch.getZ() && !change) {
				change = true;
				changeAnimation(ch);
				time = 0;
			}
			if(ch.getZ() == 0)
				endPose = true;
		}
		for(RoleAnimation anima : an)
			anima.draw(false, false, batch, delta, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		h = 0;
		change = false;
		jump = true;
		super.end(ch);
	}
	@Override
	public void changeAnimation(Character ch) {
		resetSelf(ch);
		if(ch instanceof Swordman) {
			an = ch.getAn(18);
			if(ch.inInstance() && ch.getSalf(true).size == 0) {
				selfRP = new Array<Vector2>();
				Rectangle re = new Rectangle(0,0,33,60);
				selfRP.add(new Vector2(-13, 0));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,64,15);
				selfRP.add(new Vector2(-31, 60));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,44,7);
				selfRP.add(new Vector2(-24, 75));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,33,10);
				selfRP.add(new Vector2(-13, 82));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,16,8);
				selfRP.add(new Vector2(2, 92));
				ch.getSalf(true).add(re);
				getSelf(ch);
			}
		}
	}
	private void getAnimation(Character ch) {
		if(ch instanceof Swordman) {
			an = ch.getAn(19);
			if(ch.inInstance() && ch.getSalf(true).size == 0) {
				selfRP = new Array<Vector2>();
				Rectangle re = new Rectangle(0,0,12,15);
				selfRP.add(new Vector2(-25, 15));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,55,35);
				selfRP.add(new Vector2(-25, 30));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,52,25);
				selfRP.add(new Vector2(-30, 65));
				ch.getSalf(true).add(re);
				re = new Rectangle(0,0,16,10);
				selfRP.add(new Vector2(5, 90));
				ch.getSalf(true).add(re);
				getSelf(ch);
			}
		}
	}
}