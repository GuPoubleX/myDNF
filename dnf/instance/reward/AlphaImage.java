package dnf.instance.reward;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AlphaImage extends Image {
	public AlphaImage(Sprite sprite) {
		super(sprite);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha*0.75f);
	}
}