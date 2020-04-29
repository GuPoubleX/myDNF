package dnf.admin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ChannelImage extends Image {
	public ChannelImage(Sprite re) {
		super(re);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, 0.75f);
	}
}