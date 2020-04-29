package dnf.hud.ui;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;

public class SkillWindow extends Window {
	private GuPoubleXGame game = null;
	private Preferences pref = null;
	private int skill[] = null;
	private float scale = 1;
	private Array<Image> key = null;
	public SkillWindow(GuPoubleXGame game, WindowStyle style, String hudpref, float scale) {
		super("", style);
		this.game = game;
		this.scale = scale;
		pref = game.getPref(hudpref);
		setSize(181*scale, 36*2*scale);
		init();
	}
	public void resetKey() {
		skill = new int[12];
		key = new Array<Image>();
		for(int i = 0; i < skill.length; i++) {
			skill[i] = pref.getInteger("SKILL_"+(i+1));
			SpriteTexture sp = game.getImg(SetImg.hud, "keyshortcut.img").getIndexST(keytoImage(skill[i]));
			Image k = new Image(sp.getSprite());
			k.setScale(scale);
			k.setPosition(((sp.getVWH().x+2)*(i%6)+sp.getXY().x)*scale, ((i/6==1)?4:39)*scale);
			addActor(k);
			key.add(k);
		}
	}
	private void init() {
		Sprite s = new Sprite(game.getImg(SetImg.hud, "hud.img").getIndex(0), 536, 47, 180, 30);
		Image image = new Image(s);
		image.setScale(scale);
		image.setPosition(0, 1*scale);
		addActor(image);
		image = new Image(s);
		image.setScale(scale);
		image.setPosition(0, 35*scale);
		addActor(image);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(skill != null)
			for(int i = 0; i < skill.length; i++)
				if(skill[i] != pref.getInteger("SKILL_"+(i+1))) {
					skill[i] = pref.getInteger("SKILL_"+(i+1));
					key.get(i).setDrawable(new TextureRegionDrawable(game.getImg(SetImg.hud, "keyshortcut.img").getIndex(keytoImage(skill[i]))));
				}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	private int keytoImage(int keys) {
		int key = 0;
		switch(keys) {
			case Keys.UP:key = 2;break;
			case Keys.DOWN:key = 3;break;
			case Keys.RIGHT:key = 0;break;
			case Keys.LEFT:key = 1;break;
			case Keys.A:key = 55;break;
			case Keys.B:key = 56;break;
			case Keys.C:key = 57;break;
			case Keys.D:key = 58;break;
			case Keys.E:key = 59;break;
			case Keys.F:key = 60;break;
			case Keys.G:key = 61;break;
			case Keys.H:key = 62;break;
			case Keys.I:key = 63;break;
			case Keys.J:key = 64;break;
			case Keys.K:key = 65;break;
			case Keys.L:key = 66;break;
			case Keys.M:key = 67;break;
			case Keys.N:key = 68;break;
			case Keys.O:key = 69;break;
			case Keys.P:key = 70;break;
			case Keys.Q:key = 71;break;
			case Keys.R:key = 72;break;
			case Keys.S:key = 73;break;
			case Keys.T:key = 74;break;
			case Keys.U:key = 75;break;
			case Keys.V:key = 76;break;
			case Keys.W:key = 77;break;
			case Keys.X:key = 78;break;
			case Keys.Y:key = 79;break;
			case Keys.Z:key = 80;break;
			case Keys.NUM_0:key = 45;break;
			case Keys.NUM_1:key = 46;break;
			case Keys.NUM_2:key = 47;break;
			case Keys.NUM_3:key = 48;break;
			case Keys.NUM_4:key = 49;break;
			case Keys.NUM_5:key = 50;break;
			case Keys.NUM_6:key = 51;break;
			case Keys.NUM_7:key = 52;break;
			case Keys.NUM_8:key = 53;break;
			case Keys.NUM_9:key = 54;break;
			case Keys.NUMPAD_0:key = 35;break;
			case Keys.NUMPAD_1:key = 36;break;
			case Keys.NUMPAD_2:key = 37;break;
			case Keys.NUMPAD_3:key = 38;break;
			case Keys.NUMPAD_4:key = 39;break;
			case Keys.NUMPAD_5:key = 40;break;
			case Keys.NUMPAD_6:key = 41;break;
			case Keys.NUMPAD_7:key = 42;break;
			case Keys.NUMPAD_8:key = 43;break;
			case Keys.NUMPAD_9:key = 44;break;
			case Keys.MINUS:key = 24;break;
			case Keys.EQUALS:key = 23;break;
			case Keys.LEFT_BRACKET:key = 104;break;
			case Keys.RIGHT_BRACKET:key = 105;break;
			case Keys.SEMICOLON:key = 27;break;
			case Keys.APOSTROPHE:key = 106;break;
			case Keys.COMMA:key = 28;break;
			case Keys.PERIOD:key = 29;break;
			case Keys.SLASH:key = 30;break;
		}
		return key;
	}
}