package dnf.hud.button;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import dnf.author.RoleAnimation;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;

public class SkillColdDown extends Actor {
	private GuPoubleXGame game = null;
	private Character ch = null;
	private RoleAnimation leftup = null;
	private RoleAnimation rightup = null;
	private RoleAnimation leftdown = null;
	private RoleAnimation rightdown = null;
	private float time = 0;
	private float delta = 0;
	private float during = 0;
	private boolean start = false;
	private Sprite icon = null;
	private Sprite cdingicon = null;
	private TextLabel label = null;
	private float scale = 1;
	private float showAlpha = 0.3f;
	public SkillColdDown(GuPoubleXGame game, Character ch, float cd) {
		super();
		this.game = game;
		this.ch = ch;
		this.during = cd;
	}
	public SkillColdDown(GuPoubleXGame game, Character ch, float cd, Sprite icon, Sprite cdingicon, float scale) {
		super();
		this.game = game;
		this.ch = ch;
		this.during = cd;
		this.scale = scale;
		this.icon = new Sprite(icon);
		this.cdingicon = new Sprite(cdingicon);
		this.icon.setScale(scale);
		this.cdingicon.setScale(scale);
		setSize(this.icon.getWidth()*scale, this.icon.getHeight()*scale);
		if(ch.inInstance()) {
			label = new TextLabel(game);
			label.setAlignment(Align.center);
			label.setFontScale(0.5f);
			setAnimation(cd);
		}
	}
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		icon.setPosition(x+getWidth()/2-icon.getWidth()/2, y+getHeight()/2-icon.getHeight()/2);
		if(ch.inInstance()) {
			cdingicon.setPosition(x+getWidth()/2-cdingicon.getWidth()/2, y+getHeight()/2-cdingicon.getHeight()/2);
			if(label != null)
				label.setPosition(x+getWidth()/2-label.getWidth()/2, y+getHeight()/2-label.getHeight()/2);
		}
	}
	private void setAnimation(float during) {
		if(ch.inInstance()) {
			Img img = game.getImg(SetImg.hud, "circlecooltime.img");
			SpriteTexture re = new SpriteTexture(new Texture(28, 28, Pixmap.Format.RGBA8888));
			SpriteTexture st[] = new SpriteTexture[img.getCount()+1];
			for(int i = 0; i < st.length-1; i++)
				st[i] = new SpriteTexture(img.getIndexST(i).getSprite());
			st[st.length-1] = re;
			rightup = new RoleAnimation(during/4/st.length, st);
			rightup.setScale(scale);
			st = new SpriteTexture[img.getCount()+1];
			for(int i = 0; i < st.length-1; i++)
				st[i] = new SpriteTexture(img.getIndexST(i).getSprite());
			st[st.length-1] = re;
			rightdown = new RoleAnimation(during/4/st.length, st);
			rightdown.setScale(scale);
			st = new SpriteTexture[img.getCount()+1];
			for(int i = 0; i < st.length-1; i++)
				st[i] = new SpriteTexture(img.getIndexST(i).getSprite());
			st[st.length-1] = re;
			leftdown = new RoleAnimation(during/4/st.length, st);
			leftdown.setScale(scale);
			st = new SpriteTexture[img.getCount()+1];
			for(int i = 0; i < st.length-1; i++)
				st[i] = new SpriteTexture(img.getIndexST(i).getSprite());
			st[st.length-1] = re;
			leftup = new RoleAnimation(during/4/st.length, st);
			leftup.setScale(scale);
		}
	}
	public boolean cding() {
		return start;
	}
	public void play() {
		if(!start)
			start = true;
	}
	public void act(float delta) {
		super.act(delta);
		if(start) {
			time += delta;
			this.delta = delta;
			if(label != null)
				label.setText(new DecimalFormat("#0.0").format(during-time));
		}
	}
	public void draw(Batch batch, float parentAlpha) {
		if(ch.inInstance())
			parentAlpha = 0.75f;
		super.draw(batch, parentAlpha);
		if(!start)
			icon.draw(batch, parentAlpha);
		if(start && ch.inInstance()) {
			cdingicon.draw(batch, parentAlpha);
			if(time < during/4) {
				rightup.drawWH(batch, delta, showAlpha, getX()+rightup.getWidth(), getY()+rightup.getHeight(), 0);
				rightdown.getKeyFrames()[0].getSprite().setPosition(getX()+rightdown.getWidth(), getY());
				rightdown.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				rightdown.getKeyFrames()[0].getSprite().setScale(scale);
				rightdown.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
				leftdown.getKeyFrames()[0].getSprite().setPosition(getX(), getY());
				leftdown.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				leftdown.getKeyFrames()[0].getSprite().setScale(scale);
				leftdown.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
				leftup.getKeyFrames()[0].getSprite().setPosition(getX(), getY()+leftup.getHeight());
				leftup.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				leftup.getKeyFrames()[0].getSprite().setScale(scale);
				leftup.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
			}else if(time < during/4*2) {
				rightdown.drawWH(batch, delta, showAlpha, getX()+rightdown.getWidth(), getY()+rightdown.getHeight(), 270);
				leftdown.getKeyFrames()[0].getSprite().setPosition(getX(), getY());
				leftdown.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				leftdown.getKeyFrames()[0].getSprite().setScale(scale);
				leftdown.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
				leftup.getKeyFrames()[0].getSprite().setPosition(getX(), getY()+leftup.getHeight());
				leftup.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				leftup.getKeyFrames()[0].getSprite().setScale(scale);
				leftup.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
			}else if(time < during/4*3) {
				leftdown.drawWH(batch, delta, showAlpha, getX()+leftdown.getWidth(), getY()+leftdown.getHeight(), 180);
				leftup.getKeyFrames()[0].getSprite().setPosition(getX(), getY()+leftup.getHeight());
				leftup.getKeyFrames()[0].getSprite().setOrigin(0, 0);
				leftup.getKeyFrames()[0].getSprite().setScale(scale);
				leftup.getKeyFrames()[0].getSprite().draw(batch, showAlpha);
			}else if(time < during/4*4) {
				leftup.drawWH(batch, delta, showAlpha, getX()+leftup.getWidth(), getY()+leftup.getHeight(), 90);
			}else {
				start = false;
				time = 0;
			}
			if(label != null)
				label.draw(batch, parentAlpha);
		}
	}
}