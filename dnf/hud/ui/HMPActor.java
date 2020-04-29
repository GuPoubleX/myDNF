package dnf.hud.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;

public class HMPActor extends Actor {
	private Sprite re = null;
	private TextLabel label = null;
	private Character character = null;
	private boolean isHP = false;
	private float lastpercent = 0;
	private float time = 0;
	private float scale = 1;
	private Image image = null;
	public HMPActor(Sprite re, GuPoubleXGame game, boolean hp, Character character, float scale) {
		super();
		this.re = re;
		this.character = character;
		isHP = hp;
		this.scale = scale;
		label = new TextLabel(game);
		label.setFontScale(0.6f);
		label.setSize(100, 20);
		label.setAlignment(Align.center);
		label.setText("0/0");
		setSize(re.getRegionWidth()/2*scale, re.getRegionHeight()/2*scale);
	}
	public void changeCharacter(Character ch) {
		character = ch;
	}
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x-getWidth()/2, y-getHeight()/2);
		label.setPosition(x-label.getWidth()/2, y-label.getHeight()/2);
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
		if(time >= 0.1f) {
			time -= 0.1f;
			setImage();
		}
	}
	private void setImage() {
		float height = 0;
		float maxheight = 0;
		height = character.getProperty(isHP?"hp":"mp");
		maxheight = character.getProperty(isHP?"hpmax":"mpmax");
		if(height < 0)
			height = 0;
		float percent = height / maxheight;
		String str = ((int) height)+"";
		if(percent <= 0.3f)
			str = "[RED]"+str+"[]";
		else if(percent <= 0.5f)
			str = "[GREEN]"+str+"[]";
		str += "/"+((int) maxheight);
		label.setText(str);
		if(percent != lastpercent)
			lastpercent = percent;
		else
			return;
		image = new Image(new TextureRegion(re, 0, (int) (re.getHeight()*(1-percent)),
				(int) re.getWidth(), (int) (re.getHeight()*percent)));
		image.setScale(scale);
		image.setPosition(getX()+getWidth()/2-image.getWidth()/2*scale,
				getY()+getHeight()/2-image.getWidth()/2*scale);
	}
}