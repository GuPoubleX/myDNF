package dnf.gupoublex;

import com.badlogic.gdx.Screen;

public class TempScreen implements Screen {
	private GuPoubleXGame game = null;
	private float time = 0;
	public TempScreen(GuPoubleXGame game) {
		super();
		this.game = game;
	}
	@Override
	public void dispose() {
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void render(float delta) {
		time += delta;
		if(time >= 0.5f)
			game.changeLastMap();
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void resume() {
	}
	@Override
	public void show() {
	}
}