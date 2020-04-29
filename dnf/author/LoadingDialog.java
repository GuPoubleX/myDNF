package dnf.author;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class LoadingDialog extends Dialog {
	private Image progress = null;
	private float time = 0;
	private GuPoubleXGame game = null;
	public LoadingDialog(WindowStyle windowStyle, GuPoubleXGame game) {
		super("", windowStyle);
		this.game = game;
		Image image = new Image(game.getImg(SetImg.common, "actionpointsystem.img").getIndex(1));
		setSize(image.getWidth(), image.getHeight());
		addActor(image);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(progress == null) {
			Sprite s = game.getImg(SetImg.common, "actionpointsystem.img").getIndex(0);
			Sprite sp = new Sprite(s, 0, 0, (int) (s.getWidth()*game.getManager().getProgress()*game.getList().size), (int) s.getHeight());
			progress = new Image(sp);
			addActor(progress);
		}else {
			if(time >= 0.1f) {
				time -= 0.1f;
				if(progress != null)
					progress.remove();
				Sprite s = game.getImg(SetImg.common, "actionpointsystem.img").getIndex(0);
				Sprite sp = new Sprite(s, 0, 0, (int) (s.getWidth()*game.getManager().getProgress()*game.getList().size), (int) s.getHeight());
				progress = new Image(sp);
				addActor(progress);
			}
		}
		super.draw(batch, parentAlpha);
	}
}