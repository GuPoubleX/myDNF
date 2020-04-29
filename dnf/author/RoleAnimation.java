package dnf.author;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import dnf.gupoublex.read.SpriteTexture;

public class RoleAnimation extends Animation<SpriteTexture> {
	private float time = 0;
	private float frameDuration = 0;
	private SpriteTexture st[] = null;
	private Sprite sprite = null;
	private float scale = 1;
	public void resetTime() {
		time = 0;
	}
	public RoleAnimation(float during, SpriteTexture st[]) {
		super(during, st);
		setPlayMode(PlayMode.LOOP);
		this.st = st;
		frameDuration = during;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public float getFrameDuration() {
		return frameDuration;
	}
	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
		super.setFrameDuration(frameDuration);
	}
	public SpriteTexture[] getKeyFrames() {
		return super.getKeyFrames();
	}
	public float getWidth() {
		return st[0].getVWH().x*scale;
	}
	public float getHeight() {
		return st[0].getVWH().y*scale;
	}
	private float getWidth(int i) {
		return st[i].getWH().x*scale;
	}
	private float getHeight(int i) {
		return st[i].getWH().y*scale;
	}
	public void draw(Batch batch, float delta, float alpha, float x, float y) {
		time += delta;
		if(time >= frameDuration*st.length)
			time -= frameDuration*st.length;
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		sprite.setPosition(x, y);
		sprite.draw(batch, alpha);
	}
	public void drawWH(Batch batch, float delta, float alpha, float x, float y, float degrees) {
		time += delta;
		if(time >= frameDuration*st.length)
			time -= frameDuration*st.length;
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setOrigin(0, 0);
		sprite.setScale(scale);
		sprite.rotate((360+degrees)%360);
		sprite.setPosition(x, y);
		sprite.draw(batch, alpha);
	}
	private void draw(Batch batch, float delta, float alpha, float x, float y, boolean flip) {
		time += delta;
		if(time >= frameDuration*st.length)
			time -= frameDuration*st.length;
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		sprite.setPosition(x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a))), y);
		sprite.draw(batch, alpha);
	}
	private void draw(Batch batch, float delta, float alpha, float x, float y, boolean flip, int i, boolean end) {
		time += delta;
		if(time >= frameDuration*st.length) {
			if(end)
				time = frameDuration*st.length-frameDuration;
			else
				time -= frameDuration*st.length;
		}
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		sprite.setPosition(x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a))), y);
		sprite.draw(batch, alpha);
	}
	public void draw(Batch batch, float delta, float alpha, float x, float y, boolean flip, boolean addy, boolean end) {
		if(!addy) {
			if(end)
				draw(batch, delta, alpha, x, y, flip, 0, end);
			else
				draw(batch, delta, alpha, x, y, flip);
			return;
		}
		time += delta;
		if(time >= frameDuration*st.length) {
			if(end)
				time = frameDuration*st.length-frameDuration;
			else
				time -= frameDuration*st.length;
		}
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		sprite.setPosition(x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a))), y+(getHeight()-st[a].getXY().y-getHeight(a)));
		sprite.draw(batch, alpha);
	}
	public void draw(Batch batch, float delta, float alpha, float x, float y, boolean flip, boolean addy) {
		if(!addy) {
			draw(batch, delta, alpha, x, y, flip);
			return;
		}
		time += delta;
		if(time >= frameDuration*st.length)
			time -= frameDuration*st.length;
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		sprite.setPosition(x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a))), y+(getHeight()-st[a].getXY().y-getHeight(a)));
		sprite.draw(batch, alpha);
	}
	public void draw(Batch batch, float delta, float alpha, float x, float y, Vector2 fix, Vector2 fix2, float degree, boolean flip) {
		time += delta;
		if(time >= frameDuration*st.length)
			time = frameDuration*st.length;
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		sprite.setOrigin((st[a].getWH().x-(getWidth()/2-st[a].getXY().x))-fix.x-(flip?0:fix2.x),
				(st[a].getWH().y-(getHeight()/2-st[a].getXY().y))-fix.y);
		float dx = x-getWidth()/2+fix.x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a)))+(!flip?0:fix2.x);
		float dy = y-getHeight()/2+fix.y+(getHeight()-st[a].getXY().y-st[a].getSprite().getHeight());
		sprite.setPosition(dx, dy);
		sprite.setRotation(degree);
		sprite.draw(batch, alpha);
	}
	public int draw(boolean time, boolean loop, Batch batch, float delta, float alpha, float x, float y, Vector2 fix, Vector2 fix2, boolean flip) {
		if(!time)
			return draw(batch, delta, alpha, x, y, fix, fix2, flip, loop);
		else
			return draw(batch, delta, alpha, x, y, fix, fix2, flip, loop, 0);
	}
	private int draw(Batch batch, float delta, float alpha, float x, float y, Vector2 fix, Vector2 fix2, boolean flip, boolean loop) {
		time += delta;
		if(time >= frameDuration*st.length) {
			if(!loop)
				time = frameDuration*st.length-frameDuration;
			else
				time -= frameDuration*st.length;
		}
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		float dx = x-getWidth()/2+fix.x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-getWidth(a)))+(!flip?0:fix2.x);
		float dy = y-getHeight()/2+fix.y+(getHeight()-st[a].getXY().y-st[a].getSprite().getHeight());
		sprite.setPosition(dx, dy);
		sprite.draw(batch, alpha);
		return a;
	}
	private int draw(Batch batch, float time, float alpha, float x, float y, Vector2 fix, Vector2 fix2, boolean flip, boolean loop, int i) {
		if(time >= frameDuration*st.length) {
			if(!loop)
				time = frameDuration*st.length-frameDuration;
			else
				time -= frameDuration*st.length;
		}
		int a = ((int) Math.floor(time/frameDuration))%st.length;
		sprite = new Sprite(st[a].getSprite());
		sprite.setScale(scale);
		if(flip)
			sprite.flip(true, false);
		float dx = x-getWidth()/2+fix.x+(!flip?st[a].getXY().x:(getWidth()-st[a].getXY().x-st[a].getSprite().getWidth()))+(!flip?0:fix2.x);
		float dy = y-getHeight()/2+fix.y+(getHeight()-st[a].getXY().y-st[a].getSprite().getHeight());
		sprite.setPosition(dx, dy);
		sprite.draw(batch, alpha);
		return a;
	}
}