package dnf.character.part;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dnf.author.RoleAnimation;
import dnf.gupoublex.set.SetBase;

public class AddActor extends Actor {
	private float time = 0;
	private float delta = 0;
	private float during = 0;
	private boolean removed = false;
	private RoleAnimation start = null;
	private RoleAnimation last = null;
	private RoleAnimation end = null;
	public AddActor(float during, RoleAnimation start, RoleAnimation last, RoleAnimation end) {
		super();
		this.during = during;
		this.start = start;
		this.last = last;
		this.end = end;
	}
	public void reset() {
		time = 0;
		removed = false;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		this.delta = delta;
		if(time >= during)
			removed = remove();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(removed)
			return;
		if(time <= start.getAnimationDuration()-SetBase.step)
			start.draw(batch, delta, parentAlpha, getX()-start.getWidth()/2, getY()-start.getHeight());
		else if(time >= during-end.getAnimationDuration())
			end.draw(batch, delta, parentAlpha, getX()-end.getWidth()/2, getY()-end.getHeight());
		else
			last.draw(batch, delta, parentAlpha, getX()-last.getWidth()/2, getY()-last.getHeight());
	}
}