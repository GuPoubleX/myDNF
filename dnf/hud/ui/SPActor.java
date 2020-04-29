package dnf.hud.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class SPActor extends Window {
	private GuPoubleXGame game = null;
	private Character ch = null;
	private float time = 0;
	public SPActor(WindowStyle style, Character ch, GuPoubleXGame game) {
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
		int sp = (int) ch.getProperty("sp");
		int q = sp/1000;
		int b = sp/100%10;
		int s = sp/10%10;
		int g = sp%10;
		Image ver_q = getSizeNum(q, sp, 4);
		Image ver_b = getSizeNum(b, sp, 3);
		Image ver_s = getSizeNum(s, sp, 2);
		Image ver_g = getSizeNum(g, sp, 1);
		if(ver_q != null)
			addActor(ver_q);
		if(ver_b != null)
			addActor(ver_b);
		if(ver_s != null)
			addActor(ver_s);
		if(ver_g != null)
			addActor(ver_g);
	}
	private Image getSizeNum(int sign, int sp, int qbsg) {
		Image image = new Image(game.getImg(SetImg.hud, "level02.img").getIndex(sign));
		if(sp >= 1000) {
			if(qbsg == 4)
				image.setPosition(sign==1?2:0, 1);
			else if(qbsg == 3)
				image.setPosition((sign==1?2:0)+12, 1);
			else if(qbsg == 2)
				image.setPosition((sign==1?2:0)+24, 1);
			else if(qbsg == 1)
				image.setPosition((sign==1?2:0)+36, 1);
		}else if(sp >= 100) {
			if(qbsg == 3)
				image.setPosition((sign==1?2:0)+6, 1);
			else if(qbsg == 2)
				image.setPosition((sign==1?2:0)+18, 1);
			else if(qbsg == 1)
				image.setPosition((sign==1?2:0)+30, 1);
			else
				return null;
		}else if(sp >= 10) {
			if(qbsg == 2)
				image.setPosition((sign==1?2:0)+12, 1);
			else if(qbsg == 1)
				image.setPosition((sign==1?2:0)+24, 1);
			else
				return null;
		}else {
			if(qbsg == 1)
				image.setPosition((sign==1?2:0)+18, 1);
			else
				return null;
		}
		return image;
	}
}