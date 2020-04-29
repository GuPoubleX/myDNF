package dnf.hud;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import dnf.character.Character;
import dnf.author.ScrollWindow;
import dnf.author.TextLabel;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetHUD;
import dnf.gupoublex.set.SetImg;
import dnf.hud.info.StateWindow;

public class InfoWindow extends Window {
	private Window window = null;
	private StateWindow stay = null;
	private Character ch = null;
	private GuPoubleXGame game = null;
	private WindowStyle style = null;
	private ScrollWindow scroll = null;
	public void changeCharacter(Character ch) {
		this.ch = ch;
		resetWindow();
	}
	private void resetWindow() {
		Image image = new Image(game.getImg(SetImg.hud, "inventory.img").getIndex(15));
		image.setScale(getWidth()/(image.getWidth()-5), getHeight()/(image.getHeight()-5));
		image.setPosition(getWidth()/2-image.getWidth()*image.getScaleX()/2,
				getHeight()/2-image.getHeight()*image.getScaleY()/2);
		addActor(image);
		TextLabel label = new TextLabel(game);
		label.setText(SetHUD.info);
		label.setFontScale(0.7f);
		label.setSize(226, 18);
		label.setAlignment(Align.center);
		label.setPosition(getWidth()/2-label.getWidth()/2, getHeight()-18);
		image = new Image(game.getImg(SetImg.hud, "titleaccessory.img").getIndex(9));//biaoti
		image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()-18);
		addActor(image);
		addActor(label);
		image = new Image(game.getImg(SetImg.hud, "inventory.img").getIndex(0));
		image.setScale(getWidth()/image.getWidth());
		image.setPosition(0, getHeight()-image.getHeight()*image.getScaleY()-label.getHeight());
		addActor(image);
		image = new Image(game.getImg(SetImg.hud, "inventory.img").getIndex(9));
		image.setScale(getWidth()/image.getWidth());
		image.setPosition(getWidth()/2-image.getWidth()/2*image.getScaleX(), getHeight()-20-image.getHeight()*image.getScaleY());
		addActor(image);
		stay = new StateWindow(style, new Vector2(230, 120), ch, true);
		stay.setPosition(0, 304-65);
		addActor(stay);
		image = new Image(game.getImg(SetImg.hud, "inventory.img").getIndex(15));
		image.setScale(getWidth()/(image.getWidth()-5), 0.5f);
		image.setPosition(getWidth()/2-image.getWidth()*image.getScaleX()/2, stay.getY()-15-image.getHeight()*image.getScaleY());
		addActor(image);
		TextLabel infolabel = new TextLabel(game);
		infolabel.setText("Lv."+((int) ch.getProperty("LV"))+" "+ch.getName()+"\n[RED]"+ch.getProfession()+"[]");
		infolabel.setFontScale(0.5f);
		infolabel.setSize(226, 25);
		infolabel.setAlignment(Align.center);
		infolabel.setPosition(0, image.getY()+image.getHeight()*image.getScaleY()/2-infolabel.getHeight()/2);
		addActor(infolabel);
		scroll = new ScrollWindow(style, window, 5);
		scroll.setSize(getWidth(), 327-190);
		scroll.setPosition(0, 60);
		float w = scroll.getWidth()/2-5-15-7-4-5;
		for(int i = 0; i < 29; i++) {
			Image img = new Image(game.getImg(SetImg.hud, "profile_icon.img").getIndex(i));
			Vector2 ver = new Vector2(i%2==0?5:119, scroll.getHeight()-5-img.getHeight()-(img.getHeight()+5)*(i/2));
			img.setPosition(ver.x, ver.y);
			TextLabel text = new TextLabel(game);
			text.setSize(w/2, 15);
			text.setFontScale(0.5f);
			text.setAlignment(Align.left);
			text.setText(SetHUD.property[i]);
			ver = new Vector2(img.getX()+img.getWidth()+2, img.getY());
			text.setPosition(ver.x, ver.y);
			TextLabel val = new TextLabel(ch, SetHUD.value[i], game);
			val.setSize(w/2, 15);
			val.setFontScale(0.5f);
			val.setAlignment(Align.right);
			ver = new Vector2(text.getX()+text.getWidth(), text.getY());
			val.setPosition(ver.x, ver.y);
			scroll.addActor(img, true);
			scroll.addActor(text, false);
			scroll.addActor(val, false);
		}
		setPropertyEx(label, w, ch);
		addActor(scroll);
	}
	public InfoWindow(WindowStyle style, GuPoubleXGame game, Character ch, Window window) {
		super("", style);
		this.style = style;
		this.ch = ch;
		this.game = game;
		this.window = window;
		setSize(230, 324+50);
		resetWindow();
	}
	private void setPropertyEx(TextLabel label, float w, Character ch) {
		Image img = new Image(game.getImg(SetImg.hud, "profile_icon.img").getIndex(27));
		Vector2 ver = new Vector2(119, scroll.getHeight()-5-img.getHeight()-(img.getHeight()+5)*((31-4)/2));
		img.setPosition(ver.x, ver.y);
		TextLabel text = new TextLabel(game);
		text.setSize(w/2, 15);
		text.setFontScale(0.5f);
		text.setAlignment(Align.left);
		text.setText(SetHUD.property[27]);
		ver = new Vector2(img.getX()+img.getWidth()+2, img.getY());
		text.setPosition(ver.x, ver.y);
		TextLabel val = new TextLabel(ch, SetHUD.value[27], game);
		val.setSize(w/2, 15);
		val.setFontScale(0.5f);
		val.setAlignment(Align.right);
		ver = new Vector2(text.getX()+text.getWidth(), text.getY());
		val.setPosition(ver.x, ver.y);
		scroll.addActor(img, true);
		scroll.addActor(text, false);
		scroll.addActor(val, false);
		img = new Image(game.getImg(SetImg.hud, "profile_icon.img").getIndex(28));
		img.setPosition(5, scroll.getHeight()-5-img.getHeight()-(img.getHeight()+5)*((32-4)/2));
		text = new TextLabel(game);
		text.setSize(w/2, 15);
		text.setFontScale(0.5f);
		text.setAlignment(Align.left);
		text.setText(SetHUD.property[28]);
		ver = new Vector2(img.getX()+img.getWidth()+2, img.getY());
		text.setPosition(ver.x, ver.y);
		val = new TextLabel(ch, SetHUD.value[28], game);
		val.setSize(w/2, 15);
		val.setFontScale(0.5f);
		val.setAlignment(Align.right);
		ver = new Vector2(text.getX()+text.getWidth(), text.getY());
		val.setPosition(ver.x, ver.y);
		scroll.addActor(img, true);
		scroll.addActor(text, false);
		scroll.addActor(val, false);
		img = new Image(game.getImg(SetImg.hud, "profile_icon.img").getIndex(29));
		img.setPosition(119, scroll.getHeight()-5-img.getHeight()-(img.getHeight()+5)*((33-4)/2));
		text = new TextLabel(game);
		text.setSize(w/2, 15);
		text.setFontScale(0.5f);
		text.setAlignment(Align.left);
		text.setText(SetHUD.property[29]);
		text.setPosition(img.getX()+img.getWidth()+2, img.getY());
		val = new TextLabel(ch, SetHUD.value[29], game);
		val.setSize(w/2, 15);
		val.setFontScale(0.5f);
		val.setAlignment(Align.right);
		ver = new Vector2(text.getX()+text.getWidth(), text.getY());
		val.setPosition(ver.x, ver.y);
		scroll.addActor(img, true);
		scroll.addActor(text, false);
		scroll.addActor(val, false);
	}
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		stay.setVisible(visible);
	}
	public ScrollWindow getScroll() {
		return scroll;
	}
}