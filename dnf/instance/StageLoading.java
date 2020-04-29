package dnf.instance;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.LoadingDialog;
import dnf.author.GPXStage;
import dnf.gupoublex.set.SetBase;

public class StageLoading extends GPXStage {
	private ScreenInstance instance = null;
	private boolean play = false;
	public StageLoading(ScreenInstance instance, Vector2 ver, Batch batch, String ground, String name) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		this.instance = instance;
		Image image = new Image(instance.getGame().getImg(ground, name+".img").getIndex(0));
		image.setScale(Math.max(ver.x/image.getWidth(), ver.y/image.getHeight()));
		image.setPosition(getWidth()/2-image.getWidth()/2*image.getScaleX(),
				getHeight()/2-image.getHeight()/2*image.getScaleY());
		addActor(image);
		Window win = new Window("", new Window.WindowStyle(instance.getGame().getLazy(), Color.WHITE, null));
		win.setSize(SetBase.dir_width, SetBase.dir_height);
		win.setPosition(getWidth()/2-win.getWidth()/2, getHeight()/2-win.getHeight()/2);
		addActor(win);
		image = new Image(instance.getGame().getImg(ground, name+".img").getIndex(0));
		image.setScale(0.8f);
		win.addActor(image);
		LoadingDialog ld = new LoadingDialog(new Window.WindowStyle(instance.getGame().getLazy(), Color.WHITE, null), instance.getGame());
		ld.setPosition(getWidth()/2-ld.getWidth()/2, 0);
		addActor(ld);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(instance.getGame().getManager().update() && !play) {
			play = true;
			instance.startGame();
		}
	}
}