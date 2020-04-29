package dnf.hud.info;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class UpDownImage extends Image {
	public UpDownImage(GuPoubleXGame game) {
		super(new Texture(7, 7, Format.RGBA8888));
	}
	public UpDownImage(GuPoubleXGame game, boolean up) {
		super((up?game.getImg(SetImg.hud, "profile.img").getIndex(0):game.getImg(SetImg.hud, "profile.img").getIndex(1)));
	}
}