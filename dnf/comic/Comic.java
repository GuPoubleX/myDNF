package dnf.comic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class Comic extends StageComic {
	public Comic(ScreenComic main, Vector2 ver, Stage stage, float Scale, int max, String role) {
		super(main, ver, stage, Scale, max, role);
	}
	@Override
	protected void setStage() {
		image = new Image(mm.getGame().getImg(role, "img").getIndex(0));
		scale = window.getWidth()/image.getWidth();
		image.setScale(scale);
		image.setPosition(window.getWidth()/2-image.getWidth()/2*scale,
				window.getHeight()/2-image.getHeight()/2*scale);
		window.addActor(image);
		Window press = new Window("", window.getStyle());
		press.setSize(window.getWidth(), mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(0).getHeight());
		press.setPosition(window.getX(), 0);
		addActor(press);
		Image img = new Image(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(0));
		img.setPosition(press.getWidth()/2-img.getWidth()/2, 0);
		press.addActor(img);
		front = new ImageButton(new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(1)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(2)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(2)));
		back = new ImageButton(new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(3)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(4)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(4)));
		finish = new ImageButton(new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(5)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(6)),
				new TextureRegionDrawable(mm.getGame().getImg(SetImg.comiccut+"openningcontroller", "img").getIndex(6)));
		finish.setPosition(press.getWidth()/2-finish.getWidth()/2,
				press.getHeight()/2-finish.getHeight()/2);
		press.addActor(finish);
		front.setPosition(finish.getX()-10-front.getWidth(), finish.getY());
		press.addActor(front);
		back.setPosition(finish.getX()+finish.getWidth()+10, finish.getY());
		press.addActor(back);
	}
	@Override
	protected void setListener() {
		front.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(page == 0)
					return;
				mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
				page--;
				image.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(role, "img").getIndex(page)));
				image.setScale(scale);
				ground.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(role, "img").getIndex(page)));
				ground.setScale(scaleground);
			}
		});
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(page == maxex)
					return;
				mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
				page++;
				image.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(role, "img").getIndex(page)));
				image.setScale(scale);
				ground.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(role, "img").getIndex(page)));
				ground.setScale(scaleground);
			}
		});
		finish.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
				mm.getGame().change("admin", "select", 0, null);
			}
		});
		btl.add(front);
		btl.add(back);
		btl.add(finish);
	}
}