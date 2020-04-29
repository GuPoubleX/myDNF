package dnf.author;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnActor extends Actor {
	private Animation<TextureRegion> animation = null;
	private Animation<Sprite> animations = null;
	private float time = 0;
	private float extime = -1;
	private Sound sound = null;
	private float vol = 0;
	private int count = 0;
	public AnActor(float frameDuration, TextureRegion textureregion[]) {
		super();
		animation = new Animation<TextureRegion>(frameDuration, textureregion);
		animation.setPlayMode(Animation.PlayMode.LOOP);
		setSize(textureregion[0].getRegionWidth(), textureregion[0].getRegionHeight());
	}
	public void setSound(Sound sound, float vol) {
		this.vol = vol;
		this.sound = sound;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(isVisible()) {
			time += delta;
			if(sound != null) {
				if(extime == -1) {
					sound.play(vol);
					extime = 0;
				}
				extime += delta;
			}
		}
		if(animation != null) {
			if(extime > animation.getFrameDuration()) {
				count++;
				extime -= animation.getFrameDuration();
				if(count < animation.getKeyFrames().length)
					sound.play(vol);
			}
		}else if(animations != null) {
			if(extime > animations.getFrameDuration()) {
				count++;
				extime -= animations.getFrameDuration();
				if(count < animations.getKeyFrames().length)
					sound.play(vol);
			}
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(isVisible()) {
			Sprite sp = null;
			if(animation != null)
				sp = new Sprite(animation.getKeyFrame(time, true));
			else if(animations != null)
				sp = new Sprite(animations.getKeyFrame(time, true));
			if(sp != null) {
				sp.setPosition(getX(), getY());
				sp.draw(batch, parentAlpha);
			}
		}
	}
}