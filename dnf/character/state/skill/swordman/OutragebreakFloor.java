package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import dnf.gupoublex.set.SetBase;

public class OutragebreakFloor extends Actor {
	private float during = 0;
	private float time = 0;
	private Sprite sprite = null;
	private boolean right = true;
	private Vector2 p = null;
	private Animation<TextureRegion> floor = null;
	private Animation<TextureRegion> magma = null;
	private float alphatime = 0;
	private Interpolation ip = Interpolation.exp5Out;
	public OutragebreakFloor(TextureAtlas atlas, float during, Vector2 point, boolean right) {
		super();
		p = new Vector2(210+130, 205);
		this.during = during;
		this.right = right;
		TextureRegion re[] = new TextureRegion[5];
		for(int i = 0; i < re.length; i++) {
			if(i == 4)
				re[i] = new TextureRegion(new Texture(re[i-1].getRegionWidth(), re[i-1].getRegionHeight(), Format.RGBA8888));
			else
				re[i] = atlas.findRegion("outragebreak_floor/"+(2+i));
		}
		magma = new Animation<TextureRegion>(0.5f/re.length, re);
		magma.setPlayMode(PlayMode.LOOP);
		re = new TextureRegion[5];
		for(int i = 0; i < re.length; i++) {
			int n = 6+i;
			if(i == 0)
				n = 1;
			re[i] = atlas.findRegion("outragebreak_floor/"+n);
		}
		floor = new Animation<TextureRegion>(0.5f/re.length, re);
		floor.setPlayMode(PlayMode.LOOP);
		setPosition(point.x, point.y+75);
		sprite = new Sprite(atlas.findRegion("outragebreak_floor/"+0));
		sprite.flip(!right, false);
		sprite.setPosition(getX()-p.x+(!right?50:0), getY()-p.y);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		if(time >= during)
			alphatime += delta;
		if(alphatime >= 1)
			remove();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(alphatime > 0) {
			float progress = Math.min(1, alphatime/1);
			parentAlpha = 1-ip.apply(progress);
		}
		sprite.draw(batch, parentAlpha);
		Sprite sp = new Sprite(getAnimation(floor, false, time));
		sp.flip(!right, false);
		sp.setPosition(getX()-p.x+(!right?50:0), getY()-p.y);
		sp.draw(batch, parentAlpha);
		sp = new Sprite(getAnimation(magma, false, time));
		sp.flip(!right, false);
		sp.setPosition(getX()-p.x+(!right?50:0), getY()-p.y);
		sp.draw(batch, parentAlpha);
	}
	private TextureRegion getAnimation(Animation<TextureRegion> an, boolean loop, float time) {
		if(!loop) {
			if(time >= an.getAnimationDuration()-SetBase.step)
				return an.getKeyFrames()[an.getKeyFrames().length-1];
			else
				return an.getKeyFrame(time, true);
		}else
			return an.getKeyFrame(time, true);
	}
}