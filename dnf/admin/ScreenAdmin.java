package dnf.admin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetAdmin;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class ScreenAdmin implements Screen {
	private GuPoubleXGame game = null;
	private Music music = null;
	private Vector2 ver = null;
	private String changestage = null;
	private Image image = null;
	private Stage back = null;
	private StageAdmin stage = null;
	private Stage info = null;
	private float scalex = 1;
	private float scaley = 1;
	public ScreenAdmin(GuPoubleXGame game, Vector2 ver, float scaleX, float scaleY, String str) {
		super();
		this.game = game;
		this.ver = ver;
		this.scalex = scaleX;
		this.scaley = scaleY;
		changestage = str;
	}
	@Override
	public void show() {
		game.load(SetAdmin.music+"main", "music");
		game.load(SetImg.admin, "img");
		game.load(SetBase.admin, "sound");
		back = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y));
		info = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		game.getLoading().setPosition(back.getWidth()-game.getLoading().getWidth(), back.getHeight()/8);
		back.addActor(game.getLoading());
	}
	@Override
	public void dispose() {
		if(music.isPlaying())
			music.stop();
		music = null;
		game.unload(SetAdmin.music+"main", "music");
		game.unload(SetImg.admin, "img");
		game.unload(SetBase.admin, "sound");
		back.dispose();
		info.dispose();
		if(stage != null)
			stage.dispose();
		Gdx.app.log(SetBase.TAG, "ScreenAdmin release");
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	public void change(String str) {
		changestage = str;
	}
	@Override
	public void render(float delta) {
		if(game.isload(SetAdmin.music, "main.ogg", "music") && music == null) {
			music = game.getMusic(SetAdmin.music, "main.ogg");
			music.setLooping(true);
			music.setVolume(game.getSound_music());
			music.play();
		}
		if(game.getManager().update() && changestage != null) {
			if(stage != null)
				stage.dispose();
			if(image != null)
				image.remove();
			if(changestage.equals("login"))
				stage = new Login(this, ver, back.getBatch(), Math.max(scalex, scaley));
			else if(changestage.equals("server"))
				stage = new Server(this, ver, back.getBatch(), Math.max(scalex, scaley));
			else if(changestage.equals("channel"))
				stage = new Channel(this, ver, back.getBatch(), Math.max(scalex, scaley));
			else if(changestage.equals("select")) {
				for(Character ch : game.getList())
					ch.getAnimation();
				stage = new Select(this, ver, back.getBatch(), Math.max(scalex, scaley));
			}else if(changestage.equals("create"))
				stage = new Create(this, ver, back.getBatch(), Math.max(scalex, scaley));
			changestage = null;
			image = new Image(stage.getGround().getDrawable());
			image.setScale(back.getWidth()/image.getWidth());
			image.setPosition(back.getWidth()/2-image.getWidth()/2*image.getScaleX(),
					back.getHeight()/2-image.getHeight()/2*image.getScaleY());
			back.addActor(image);
		}
		game.show(info, delta);
		back.act(delta);
		if(stage != null)
			stage.act(delta);
		info.act(delta);
		back.draw();
		if(stage != null)
			stage.draw();
		info.draw();
	}
	@Override
	public void resize(int width, int height) {
		Vector2 screen = new Vector2(width, height);
		scalex = 1.0f*SetBase.dir_width/width;
		scaley = 1.0f*SetBase.dir_height/height;
		back.getViewport().update(width, height, false);
		if(stage != null) {
			stage.resetScale(Math.max(scalex, scaley), screen);
			stage.getViewport().update(width, height, false);
		}
		info.getViewport().update(width, height, false);
	}
	@Override
	public void resume() {
	}
	public GuPoubleXGame getGame() {
		return game;
	}
}