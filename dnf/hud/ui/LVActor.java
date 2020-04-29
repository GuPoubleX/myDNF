package dnf.hud.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class LVActor extends Window {
	private GuPoubleXGame game = null;
	private Character ch = null;
	private float time = 0;
	public LVActor(WindowStyle style, Character ch, GuPoubleXGame game) {
		super("", style);
		this.game = game;
		this.ch = ch;
		setSize(51, 13);
		setImage();
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
		if(time >= 0.5f) {
			time -= 0.5f;
			setImage();
		}
	}
	private void setImage() {
		clear();
		Image img = new Image(game.getImg(SetImg.hud, "level02.img").getIndex(10));
		img.setPosition(8, getHeight()/2-img.getHeight()/2);
		addActor(img);
		float x = img.getX()+img.getWidth();
		int s = ((int) ch.getProperty("lv"))/10;
		int g = ((int) ch.getProperty("lv"))%10;
		Image ver_s = getSizeNum(x, s, true, s>0);
		Image ver_g = getSizeNum(x, g, false, s>0);
		if(ver_s != null)
			addActor(ver_s);
		if(ver_g != null)
			addActor(ver_g);
	}
	private Image getSizeNum(float x, int sign, boolean s, boolean has_s) {
		Image image = new Image(game.getImg(SetImg.hud, "level02.img").getIndex(sign));
		if(s) {
			if(has_s)
				image.setPosition(x+(sign==1?2:0), 1);
			else
				return null;
		}else
			image.setPosition(x+(sign==1?2:0)+12, 1);
		return image;
	}
}