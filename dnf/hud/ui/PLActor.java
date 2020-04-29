package dnf.hud.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;

public class PLActor extends Actor {
	private GuPoubleXGame game = null;
	private TextLabel label = null;
	private Character ch = null;
	private float time = 0;
	private float scale = 1;
	private Image image = null;
	public PLActor(Character ch, GuPoubleXGame game, float scale) {
		super();
		this.game = game;
		this.ch = ch;
		this.scale = scale;
		setSize((516-318)*scale, 4*scale);
		label = new TextLabel(game);
		label.setSize(getWidth(), getHeight());
		label.setAlignment(Align.center);
		label.setFontScale(0.5f);
	}
	public void changeCharacter(Character ch) {
		this.ch = ch;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(image != null)
			image.draw(batch, parentAlpha);
		label.draw(batch, parentAlpha);
		if(time >= 0.5f) {
			time -= 0.5f;
			setImage();
		}
	}
	private void setImage() {
		clear();
		int now_pl = (int) ch.getProperty("pl");
		int now_max = ch.getProperty("lv")>=40?SetCharProperty.PL_max:SetCharProperty.PL_min;
		float percent = 1.0f*now_pl/now_max;
		if(percent >= 1)
			percent = 1.0f;
		label.setText((now_pl<=10?"[RED]":"")+now_pl+(now_pl<=10?"[]":"")+"/"+now_max);
		image = new Image(new TextureRegion(game.getImg(SetImg.hud, "hud.img").getIndex(22), 5, 0, 1, 1));
		image.setScale(percent*getWidth(), 2*scale);
		image.setPosition(getX(), getY()+getHeight()/2-image.getHeight()/2*image.getScaleY());
		label.setPosition(getX(), getY()+getHeight()/2-label.getHeight()/2);
	}
}