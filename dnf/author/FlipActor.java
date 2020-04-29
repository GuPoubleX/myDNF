package dnf.author;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlipActor extends Actor {
	private boolean x = false;
	private boolean y = false;
	private float addtime = 0;
	private Sprite sp = null;
	private Interpolation ip = Interpolation.exp5In;
	public FlipActor(Sprite re, boolean x, boolean y) {
		super();
		this.sp = new Sprite(re);
		this.x = x;
		this.y = y;
	}
	public float getWidth() {
		return sp.getWidth();
	}
	public float getHeight() {
		return sp.getHeight();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		addtime += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float progress = Math.min(1, addtime/1);
		float alpha = ip.apply(progress);
		sp.setPosition(getX()+(x?-getWidth()*(1-alpha):0), getY()+(y?0:-getHeight()));
		sp.setOrigin((x?0:1)*sp.getWidth(), 0);
		sp.setScale(alpha*(x?-1:1), alpha*(y?-1:1));
		sp.draw(batch, parentAlpha);
	}
}