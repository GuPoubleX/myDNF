package dnf.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dnf.character.other.NPC;
import dnf.gupoublex.set.SetCharProperty;

public class ShadowImage extends Actor {
	private Character ch = null;
	private Sprite sprite = null;
	private float time = 0;
	public ShadowImage(Sprite sprite, Character ch) {
		super();
		this.sprite = new Sprite(sprite);
		this.ch = ch;
	}
	public float getY() {
		return ch.getY()+sprite.getHeight()/2*getScaleY();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(!(ch instanceof NPC))
			if(ch.getEmenyType() == SetCharProperty.boss) {
				if(time >= 1)
					time -= 1;
				time += delta;
			}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(!(ch instanceof NPC) && ch.getProperty("HP") <= 0) {
			if(ch.getEmenyType() != SetCharProperty.boss)
				remove();
			else {
				if(time <= 0.5f)
					sprite.setScale(0.8f+0.2f*Math.min(1, Interpolation.exp5.apply(time/0.5f)));
				else
					sprite.setScale(1-0.2f*(1-Math.min(1, Interpolation.exp5.apply((time-0.5f)/0.5f))));
				super.draw(batch, parentAlpha);
				sprite.draw(batch, parentAlpha);
			}
		}else {
			if(!(ch instanceof NPC)) {
				if(ch.getEmenyType() == SetCharProperty.boss) {
					if(time <= 0.5f)
						sprite.setScale(0.8f+0.2f*Math.min(1, Interpolation.exp5Out.apply(time/0.5f)));
					else
						sprite.setScale(1-0.2f*(1-Math.min(1, Interpolation.exp5In.apply((time-0.5f)/0.5f))));
				}
			}
			if(sprite.getX() != ch.getX()-sprite.getWidth()/2 || sprite.getY() != ch.getY()-sprite.getHeight()/2)
				sprite.setPosition(ch.getX()-sprite.getWidth()/2, ch.getY()-sprite.getHeight()/2);
			super.draw(batch, parentAlpha);
			sprite.draw(batch, parentAlpha);
		}
	}
}