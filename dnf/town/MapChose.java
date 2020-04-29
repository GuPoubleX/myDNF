package dnf.town;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;

public class MapChose extends Window {
	private Image btn = null;
	private GuPoubleXGame game = null;
	private StageMap map = null;
	public MapChose(WindowStyle style, GuPoubleXGame game, StageMap map) {
		super("", style);
		this.game = game;
		this.map = map;
		setSize(164, 45);
		btn = new Image(new Texture((int) getWidth(), (int) getHeight(), Format.RGBA8888));
		addActor(btn);
	}
	public void addListener() {
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(map.isenter())
					return;
				map.clearLs();
				game.getSound(SetBase.map, "map_mouse.ogg").play(game.getSound_button());
				map.choseInstance();
			}
		});
		map.btl.add(btn);
	}
}