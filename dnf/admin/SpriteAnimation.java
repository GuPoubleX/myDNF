package dnf.admin;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;

public class SpriteAnimation extends Actor {
	private Animation<SpriteTexture> an = null;
	private SpriteTexture st = null;
	private Sprite sprite = null;
	private float time = 0;
	private boolean midx = false;
	private boolean midy = false;
	private boolean loop = true;
	private boolean wait = false;
	private boolean show = false;
	public SpriteAnimation(float time, GuPoubleXGame game, String main, String str, int id[], boolean midx, boolean midy) {
		super();
		this.midx = midx;
		this.midy = midy;
		SpriteTexture s[] = new SpriteTexture[id.length];
		for(int i = 0; i < id.length; i++) {
			if(id[i] == -1)
				s[i] = new SpriteTexture(new Texture(1,1,Pixmap.Format.RGBA8888)); 
			else
				s[i] = game.getImg(main, str).getIndexST(id[i]);
		}
		an = new Animation<SpriteTexture>(time, s);
		an.setPlayMode(PlayMode.LOOP);
		show = true;
	}
	public SpriteAnimation(float time, GuPoubleXGame game, String main, String str, int id[], boolean midx, boolean midy, boolean waitloop) {
		super();
		this.midx = midx;
		this.midy = midy;
		this.loop = waitloop;
		SpriteTexture s[] = new SpriteTexture[id.length];
		for(int i = 0; i < id.length; i++) {
			if(id[i] == -1)
				s[i] = new SpriteTexture(new Texture(1,1,Pixmap.Format.RGBA8888)); 
			else
				s[i] = game.getImg(main, str).getIndexST(id[i]);
		}
		an = new Animation<SpriteTexture>(time, s);
		an.setPlayMode(loop?PlayMode.LOOP:PlayMode.NORMAL);
	}
	public float getWidth() {
		return an.getKeyFrames()[0].getVWH().x;
	}
	public float getHeight() {
		return an.getKeyFrames()[0].getVWH().y;
	}
	public void show() {
		show = true;
	}
	public void resetWait() {
		wait = true;
		time = 0;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(show)
			time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(!show)
			return;
		if(an.getKeyFrame(time, true) != st) {
			if(!loop) {
				if(!wait) {
					wait = true;
					st = an.getKeyFrame(time, loop);
					sprite = st.getSprite();
					sprite.setPosition(getX()+st.getXY().x/(midx?2:1), getY()+(!loop?(st.getVWH().y-st.getXY().y):st.getXY().y)/(midy?2:1));
				}
			}else {
				wait = true;
				st = an.getKeyFrame(time, loop);
				sprite = st.getSprite();
				sprite.setPosition(getX()+st.getXY().x/(midx?2:1), getY()+(!loop?(st.getVWH().y-st.getXY().y):st.getXY().y)/(midy?2:1));
			}
		}
		if(sprite != null)
			sprite.draw(batch, parentAlpha);
	}
}