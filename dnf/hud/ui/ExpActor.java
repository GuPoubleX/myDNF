package dnf.hud.ui;

import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class ExpActor extends Actor {
	private GuPoubleXGame game = null;
	private TextLabel label = null;
	private Character ch = null;
	private float time = 0;
	private float scale = 1;
	private Image image = null;
	public ExpActor(Character ch, GuPoubleXGame game, float scale) {
		super();
		this.game = game;
		this.ch = ch;
		this.scale = scale;
		setSize(625*scale, 10*scale);
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
		long next_exp = (long) ch.getProperty("LVNEXT");
		long now_exp = (long) ch.getProperty("LVNOW");
		float percent = 1.0f*now_exp/next_exp;
		if(percent >= 1)
			percent = 1.0f;
		label.setText(now_exp+"/"+next_exp+"("+new DecimalFormat("#0.00").format(percent*100)+"%)");
		image = new Image(new Sprite(game.getImg(SetImg.hud, "hud.img").getIndex(29), 0, 0,
				(int) (game.getImg(SetImg.hud, "hud.img").getIndex(29).getWidth()*percent),
				(int) game.getImg(SetImg.hud, "hud.img").getIndex(29).getHeight()));
		image.setScale(scale);
		image.setPosition(getX(), getY()+getHeight()/2-image.getHeight()/2);
		label.setPosition(getX(), getY()+getHeight()/2-label.getHeight()/2);
	}
}