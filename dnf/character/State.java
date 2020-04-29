package dnf.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.character.part.Pose;
import dnf.character.part.SkillPose;
import dnf.character.state.Idle;
import dnf.character.state.Move;
import dnf.character.state.Stay;
import dnf.character.state.skill.common.*;

public class State implements Disposable {
	private Character ch = null;
	private boolean start = false;
	private boolean newo = false;
	private Array<Pose> pose = null;
	public State(Character character) {
		super();
		this.ch = character;
		pose = new Array<Pose>();
		pose.add(new Idle(ch.getGame()));
	}
	public void act(Batch batch, float parentAlpha, float delta) {
		for(Pose p : pose) {
			if(!start) {
				start = true;
				p.start(ch);
			}
			p.running(ch, batch, parentAlpha, delta);
			if(newo || p.end()) {
				newo = false;
				if(p.end()) {
					if(ch.getLineV().x != 0 || ch.getLineV().y != 0)
						pose.add(new Move(ch.getGame(), ch.getLineV()));
					else
						pose.add(new Stay(ch.getGame()));
				}
				p.end(ch);
				pose.removeValue(p, true);
				start = false;
			}
			break;
		}
		if(pose.size > 1) {
			int i = 0;
			for(Pose ps : pose) {
				if(i != 0)
					pose.removeValue(ps, true);
				else
					i++;
			}
		}
	}
	public Pose currentPose() {
		return pose.get(0);
	}
	public void set(Pose p) {
		if(pose.get(0) instanceof SkillPose) {
			if(ch.inInstance())
				return;
			else {
				pose.add(p);
				newo = true;
			}
		}
		if(!p.getClass().equals(pose.get(0).getClass())) {
			pose.add(p);
			newo = true;
		}else if(p.getClass().equals(Move.class))
			pose.get(0).setMove(p);
	}
	public boolean change(Pose p) {
		boolean addmore = false;
		if((p instanceof Attack) && (pose.get(0) instanceof Jump)) {
			pose.add(p);
			newo = true;
		}else {
			if(pose.get(0).canForce(p)) {
				if(pose.size == 1) {
					pose.add(p);
					newo = true;
					addmore = true;
				}
			}else if(p instanceof Attack) {
				if(pose.get(0).canPress() <= 0.2f)
					pose.get(0).changeAnimation(ch);
			}else if(p instanceof Hurt)
				pose.get(0).changeAnimation(ch, p);
		}
		return addmore;
	}
	@Override
	public void dispose() {
		for(Pose p : pose)
			p.end(ch);
		pose.clear();
	}
}