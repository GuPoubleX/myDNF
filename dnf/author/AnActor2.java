package dnf.author;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnActor2 extends Actor {
	private Animation<TextureRegion> an = null;
	private float time = 0;
	public AnActor2(float during, TextureRegion re[]) {
		super();
		an = new Animation<TextureRegion>(during/re.length, re);
		an.setPlayMode(PlayMode.LOOP);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		if(time > an.getAnimationDuration())
			remove();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Sprite sp = new Sprite(an.getKeyFrame(time, true));
		sp.setPosition(getX()-sp.getWidth()/2, getY()-sp.getHeight()/2);
		sp.draw(batch, parentAlpha);
	}
}