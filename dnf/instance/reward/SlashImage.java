package dnf.instance.reward;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dnf.author.RoleAnimation;

public class SlashImage extends Image {
	private RoleAnimation an = null;
	private boolean started = false;
	private float time = 0;
	public SlashImage(Sprite sprite, RoleAnimation an) {
		super(sprite);
		this.an = an;
	}
	public void start() {
		started = true;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(started)
			time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(started)
			an.draw(true, false, batch, time, parentAlpha, getX()+getWidth()/2+160, getY()+getHeight()-65, new Vector2(0, 0), new Vector2(0, 0), false);
	}
}