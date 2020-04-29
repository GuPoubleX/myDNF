package dnf.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import dnf.author.RoleAnimation;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;

public class TitleActor extends Actor {
	private RoleAnimation an = null;
	private float time = 0;
	private float deltaadd = 0;
	private float delta = 0;
	public TitleActor(Img img) {
		super();
		addTitle(img);
	}
	private void addTitle(Img img) {
		SpriteTexture st[] = new SpriteTexture[img.getCount()];
		for(int i = 0; i < img.getCount(); i++)
			st[i] = img.getIndexST(i);
		an = new RoleAnimation(3.0f/st.length, st);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		if(time >= an.getAnimationDuration()-1)
			deltaadd += delta;
		this.delta = delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(time <= an.getAnimationDuration()-0.5f)
			an.draw(false, false, batch, delta, parentAlpha, getX(), getY(), new Vector2(0, 0), new Vector2(0, 0), false);
		else if(time <= an.getAnimationDuration()) {
			float alpha = 1 - Math.min(1, Interpolation.linear.apply(deltaadd/0.5f));
			an.draw(false, false, batch, delta, alpha, getX(), getY(), new Vector2(0, 0), new Vector2(0, 0), false);
		}else
			remove();
	}
}