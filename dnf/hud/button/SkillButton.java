package dnf.hud.button;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dnf.character.Character;
import dnf.character.part.BuffPose;
import dnf.character.part.Pose;
import dnf.character.roletype.humen.Swordman;
import dnf.character.state.buff.swordman.*;
import dnf.character.state.skill.common.*;
import dnf.character.state.skill.swordman.*;
import dnf.gupoublex.GuPoubleXGame;

public class SkillButton extends Actor {
	private GuPoubleXGame game = null;
	private Character ch = null;
	private Pose pose = null;
	private SkillColdDown scd = null;
	private SkillColdDown scd2 = null;
	private int index = -1;
	private Sprite front = null;
	private Sprite back = null;
	private float scale = 1;
	public SkillButton(Character ch, int id) {
    	super();
        this.ch = ch;
        setPose(id);
        setSkill_APC();
    }
	public SkillButton(GuPoubleXGame game, Character ch, Sprite front, Sprite back, float scale, Pose pose) {
		super();
		this.game = game;
		this.ch = ch;
		this.front = front;
		this.back = back;
		this.scale = scale;
		this.pose = pose;
		setSkill();
	}
	public SkillButton(GuPoubleXGame game, Character ch, int index, Sprite front, Sprite back, float scale) {
		super();
		this.game = game;
		this.ch = ch;
		this.index = index;
		this.front = front;
		this.back = back;
		this.scale = scale;
		setPose();
		setSkill();
	}
	public SkillButton(GuPoubleXGame game, Character ch, int index, Sprite front, Sprite back, float scale1, float scale2) {
		super();
		this.game = game;
		this.ch = ch;
		this.index = index;
		this.front = front;
		this.back = back;
		this.scale = scale1*scale2;
		setPose();
		setSkill(scale2);
	}
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(ch.inInstance())
			scd.setVisible(visible);
	}
    public Pose getPose() {
    	return pose;
    }
    public boolean BuffPose() {
    	return (pose instanceof BuffPose);
    }
	public float getWidth() {
		return scd.getWidth();
	}
	public float getHeight() {
		return scd.getHeight();
	}
	public float getWidth2() {
		return scd2.getWidth();
	}
	public float getHeight2() {
		return scd2.getHeight();
	}
	public void GroupAdd(Group group) {
		group.addActor(scd);
	}
	public void GroupAdd(Group group, Group group2) {
		group.addActor(scd);
		group2.addActor(scd2);
	}
	public boolean CDing() {
		return scd.cding();
	}
	public void addListener() {
		scd.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				play();
			}
		});
	}
	public void play() {
    	if(ch != null && pose != null) {
    		if(!scd.cding() && cantplayinfly()) {
				if(ch.getZ() == 0)
					if((pose instanceof Yinluo))
						return;
				if(pose instanceof Attack || pose instanceof Jump)
					ch.change(pose);
				else {
					if(!ch.currentPose().canForce(pose))
						return;
					boolean changed = ch.change(pose);
					if(changed) {
						scd.play();
						if(scd2 != null)
							scd2.play();
					}
				}
			}
		}
	}
	private boolean cantplayinfly() {
		boolean score = false;
		if(ch != null && pose != null) {
			score = true;
			if(ch.getZ() > 0) {
				score = false;
				if((pose instanceof Attack) || (pose instanceof Yinluo))
					score = true;
			}
		}
		return score;
	}
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		scd.setPosition(x, y);
	}
	public void setPosition(float x, float y, float x2, float y2) {
		super.setPosition(x, y);
		scd.setPosition(x, y);
		scd2.setPosition(x2, y2);
	}
	private void setPose() {
    	if(ch instanceof Swordman)
    		swordmanPose(ch.getSkillID()[index-1]);
    }
	private void setPose(int id) {
	    if(ch instanceof Swordman)
	    	swordmanPose(id);
	}
	private void swordmanPose(int i) {
    	switch(i) {
    		case 0:pose = new Attack(ch.getGame(), 0.3f);break;
    		case 1:break;
    		case 2:break;
    		case 3:break;
    		case 4:break;
    		case 5:break;
    		case 6:break;
    		case 7:break;
    		case 8:break;
    		case 9:break;
    		case 12:pose = new Yinluo(ch.getGame(), 8);break;
    		case 52:pose = new BuffPose(ch.getGame(), 1, 23, new Ghostkhazan(ch, 20), 40);break;
    		case 78:pose = new BuffPose(ch.getGame(), 0, 23, new Rage(), 10);break;
    		case 82:pose = new BuffPose(ch.getGame(), 1, 23, new Ghostsaya(ch, 20), 40);break;
    		case 84:pose = new BuffPose(ch.getGame(), 1, 23, new Ghostbremen(ch, 20), 40);break;
    		case 128:pose = new Vaneslash(ch.getGame(), 5);break;
    		case 132:pose = new Shizi(ch.getGame(), 5);break;
    		case 134:pose = new BuffPose(ch.getGame(), 1, 33, new Frenzy(), 10);break;
    		case 150:pose = new Chargecrash(ch.getGame(), 10);break;
    		case 156:pose = new Jianwu(ch.getGame(), 10);break;
    		case 162:pose = new Rapidmoveslash(ch.getGame(), 10);break;
    		case 172:pose = new Outragebreak(ch.getGame(), 10);break;
    		case 192:pose = new Hundredsword(ch.getGame(), 10);break;
    	}
    }
	public void setPoseMove(Vector2 move) {
		if(pose != null)
			pose.setMove(move);
	}
	private void setSkill_APC() {
		scd = new SkillColdDown(game, ch, pose.getCDtime());
	}
	private void setSkill() {
		scd = new SkillColdDown(game, ch, pose.getCDtime(), front, back, scale);
	}
	private void setSkill(float scale2) {
		scd = new SkillColdDown(game, ch, pose.getCDtime(), front, back, scale);
		scd2 = new SkillColdDown(game, ch, pose.getCDtime(), front, back, scale2);
	}
}