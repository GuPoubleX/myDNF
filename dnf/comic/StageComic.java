package dnf.comic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.GPXStage;
import dnf.gupoublex.set.SetBase;

public abstract class StageComic extends GPXStage {
	protected Stage stagefront = null;
	protected ScreenComic mm = null;
	protected Image ground = null;
	protected Window window = null;
	protected int maxex = 0;
	protected int page = 0;
	protected float scaleground = 1;
	protected Image image = null;
	protected ImageButton front = null;
	protected ImageButton back = null;
	protected ImageButton finish = null;
	protected boolean loading = false;
	private boolean btc = false;
	protected Array<Actor> btl = null;
	protected int index = 0;
	protected String role = null;
	private String getRole() {
		return role;
	}
	public StageComic(ScreenComic main, Vector2 ver, Stage stage, float Scale, int max, String role) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), stage.getBatch());
		stagefront = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), stage.getBatch());
		btl = new Array<Actor>();
		this.mm = main;
		this.maxex = max-1;
		this.role = role;
		ground = new Image(mm.getGame().getImg(role, "img").getIndex(0));
		window = new Window("", new Window.WindowStyle(main.getGame().getLazy(), Color.WHITE, null));
		window.setSize(SetBase.dir_width, SetBase.dir_height);
		window.setPosition(getWidth()/2-window.getWidth()/2, getHeight()/2-window.getHeight()/2);
		scaleground = stagefront.getWidth()/ground.getWidth()+0.05f;
		ground.setScale(scaleground);
		ground.setPosition(getWidth()/2-ground.getWidth()*scaleground/2, getHeight()/2-ground.getHeight()*scaleground/2);
		stagefront.addActor(ground);
		stagefront.addActor(window);
		setStage();
		setListener();
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(loading)
					return true;
				int key_front = mm.getGame().getPref(SetBase.saveHud).getInteger("LEFT");
				int key_back = mm.getGame().getPref(SetBase.saveHud).getInteger("RIGHT");
				int key_esc = mm.getGame().getPref(SetBase.saveHud).getInteger("ESC");
				if(keycode == key_front || keycode == Keys.LEFT)
					if(page != 0) {
						mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
						page--;
						image.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(getRole(), getStr()+"img").getIndex(page)));
						ground.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(getRole(), getStr()+"img").getIndex(page)));
						ground.setScale(scaleground);
					}
				if(keycode == key_back || keycode == Keys.RIGHT)
					if(page != maxex) {
						mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
						page++;
						image.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(getRole(), getStr()+"img").getIndex(page)));
						ground.setDrawable(new TextureRegionDrawable(mm.getGame().getImg(getRole(), getStr()+"img").getIndex(page)));
						ground.setScale(scaleground);
					}
				if(keycode == key_esc || keycode == Keys.SPACE || keycode == Keys.ENTER) {
					clearLs();
					mm.getGame().getSound(SetBase.common, "click.ogg").play(mm.getGame().getSound_button());
					mm.getGame().change("admin", "select", 0, null);
				}
				return true;
			}
		});
		InputMultiplexer input = new InputMultiplexer();
		input.addProcessor(this);
		Gdx.input.setInputProcessor(input);
	}
	private String getStr() {
		String str = "comiccut";
		if(getRole().indexOf("swordman") != -1)
			str += "sowrdman";
		else if(getRole().indexOf("fighter") != -1)
			str += "fighter";
		else if(getRole().indexOf("gunner") != -1)
			str += "gunner";
		else if(getRole().indexOf("mage") != -1)
			str += "mage";
		else if(getRole().indexOf("priest") != -1)
			str += "priest";
		else if(getRole().indexOf("thief") != -1)
			str += "sowrdman";
		return str;
	}
	public Image getGround() {
		return ground;
	}
	protected void clearLs() {
		if(!btc) {
			btc = true;
			loading = true;
			for(Actor bt : btl)
				bt.clearListeners();
		}
	}
	@Override
	public void act(float delta) {
		stagefront.act(delta);
		super.act(delta);
	}
	@Override
	public void draw() {
		if(front != null) {
			if(page == 0)
				front.setChecked(true);
			else
				front.setChecked(false);
		}
		if(back != null) {
			if(page == maxex)
				back.setChecked(true);
			else
				back.setChecked(false);
		}
		stagefront.draw();
		super.draw();
	}
	protected abstract void setStage();
	protected abstract void setListener();
}