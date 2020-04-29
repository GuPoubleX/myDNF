package dnf.comic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetComic;
import dnf.gupoublex.set.SetImg;

public class ScreenComic implements Screen {
	private GuPoubleXGame game = null;
	private Vector2 ver = null;
	private int roleid = 0;
	private int max = 0;
	private float scalex = 1;
	private float scaley = 1;
	private Music music = null;
	private Stage back = null;
	private Stage info = null;
	private StageComic stage = null;
	private String role = null;
	public ScreenComic(GuPoubleXGame game, String role, Vector2 ver, float scalex, float scaley) {
		super();
		this.game = game;
		this.roleid = Integer.parseInt(role);
		this.ver = ver;
		this.scalex = scalex;
		this.scaley = scaley;
	}
	@Override
	public void dispose() {
		if(music.isPlaying())
			music.stop();
		music = null;
		game.unload(SetComic.music+"intro", "music");
		game.unload(SetImg.comiccut, "img");
		back.dispose();
		info.dispose();
		if(stage != null)
			stage.dispose();
		Gdx.app.log(SetBase.TAG, "ScreenComic release");
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void render(float delta) {
		if(game.isload(SetComic.music, "intro.ogg", "music") && music == null) {
			music = game.getMusic(SetComic.music, "intro.ogg");
			music.setLooping(true);
			music.setVolume(game.getSound_music());
			music.play();
		}
		if(game.getManager().update() && stage == null) {
			max = game.getImg(role, "img").getCount();
			stage = new Comic(this, ver, back, Math.max(scalex, scaley), max, role);
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
	@Override
	public void show() {
		game.load(SetComic.music+"intro", "music");
		game.load(SetImg.comiccut+"openningcontroller", "img");
		switch(roleid) {
			case 0:role = SetImg.comiccut+"comiccutswordman";break;
			case 1:role = SetImg.comiccut+"comiccutfighter";break;
			case 2:role = SetImg.comiccut+"comiccutgunner";break;
			case 3:role = SetImg.comiccut+"comiccutmage";break;
			case 4:role = SetImg.comiccut+"comiccutpriest";break;
			case 5:role = SetImg.comiccut+"comiccutswordman";break;
		}
		game.load(role, "img");
		back = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y));
		info = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		game.getLoading().setPosition(back.getWidth()-game.getLoading().getWidth(), back.getHeight()/8);
		back.addActor(game.getLoading());
	}
	public GuPoubleXGame getGame() {
		return game;
	}
}