package dnf.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImgButton extends Button {
	private Image up = null;
	private Image down = null;
	public ImgButton(TextureRegion re_down, TextureRegion re_up) {
		super(new Button.ButtonStyle());
		up = new Image(re_up);
		down = new Image(re_down);
		setSize(up.getWidth(), up.getHeight());
	}
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		up.setPosition(x, y);
		down.setPosition(x, y);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(!isChecked())
			down.draw(batch, parentAlpha);
		else
			up.draw(batch, parentAlpha);
	}
}
