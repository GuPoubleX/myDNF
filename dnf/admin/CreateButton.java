package dnf.admin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class CreateButton extends ImageButton {
	private GuPoubleXGame game = null;
	private Array<CreateButton> btn = null;
	private Array<Integer> chose = null;
	private Image ch = null;
	private Window window = null;
	private Image info1 = null;
	private Image info2 = null;
	private boolean check = false;
	private int id = 0;
	public CreateButton(int id, Array<CreateButton> btn, Array<Integer> chose, GuPoubleXGame game, Window window) {
		super(new TextureRegionDrawable(game.getImg(SetImg.admin, "createcharacter.img").getIndex(upIndex(id))),
				new TextureRegionDrawable(game.getImg(SetImg.admin, "createcharacter.img").getIndex(downIndex(id))),
				new TextureRegionDrawable(game.getImg(SetImg.admin, "createcharacter.img").getIndex(downIndex(id))));
		this.game = game;
		this.btn = btn;
		this.chose = chose;
		this.id = id;
		this.window = window;
	}
	private static int upIndex(int id) {
		switch(id) {
			case 0:id = 4;break;
			case 1:id = 6;break;
			case 2:id = 11;break;
			case 3:id = 14;break;
			case 4:id = 17;break;
		}
		return id;
	}
	private static int downIndex(int id) {
		switch(id) {
			case 0:id = 5;break;
			case 1:id = 7;break;
			case 2:id = 12;break;
			case 3:id = 15;break;
			case 4:id = 18;break;
		}
		return id;
	}
	private int jobIndex(int id) {
		switch(id) {
			case 0:id = 8;break;
			case 1:id = 9;break;
			case 2:id = 10;break;
			case 3:id = 13;break;
			case 4:id = 16;break;
		}
		return id;
	}
	public void check() {
		excute();
	}
	private void excute() {
		for(CreateButton cb : btn)
			cb.reset();
		check = true;
		chose.clear();
		chose.add(id);
		ch = new Image(game.getImg(SetImg.admin, "characterillust.img").getIndex(id));
		ch.setOrigin(0, 0);
		ch.setScale(0.5f);
		ch.setPosition(0, window.getHeight()/3);
		window.addActor(ch);
		info1 = new Image(game.getImg(SetImg.admin, "character_text.img").getIndex(id));
		info1.setPosition(window.getWidth()/2-info1.getWidth()/2, window.getHeight()/2-info1.getHeight()/2);
		window.addActor(info1);
		info2 = new Image(game.getImg(SetImg.admin, "createcharacter.img").getIndex(jobIndex(id)));
		info2.setPosition(window.getWidth()/2-info2.getWidth()/2, window.getHeight()/2-info2.getHeight()/2+70);
		window.addActor(info2);
		game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
	}
	public void reset() {
		check = false;
		setChecked(false);
		if(ch != null)
			ch.remove();
		ch = null;
		if(info1 != null)
			info1.remove();
		info1 = null;
		if(info2 != null)
			info2.remove();
		info2 = null;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(check)
			setChecked(true);
		super.draw(batch, parentAlpha);
	}
	public void addListener() {
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!check)
					excute();
			}
		});
	}
}
