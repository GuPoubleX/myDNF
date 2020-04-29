package dnf.admin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class SelectWindow extends Window {
	private StageAdmin stage = null;
	private SelectWindow context = null;
	private GuPoubleXGame game = null;
	private Image lock = null;
	private Image back = null;
	private TextLabel info = null;
	private boolean unl = false;
	private Character character = null;
	private Array<Character> list = null;
	private Array<SelectWindow> window = null;
	private float scale = 0.8f;
	private boolean cha = false;
	private boolean checked = false;
	private Array<RoleAnimation> an = null;
	private float delta = 0;
	private int pose = 0;
	public SelectWindow(GuPoubleXGame game, Array<SelectWindow> window, StageAdmin stage) {
		super("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		context = this;
		this.game = game;
		this.window = window;
		this.stage = stage;
		setSize(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(6).getWidth()*scale,
				game.getImg(SetImg.admin, "selectcharacter.img").getIndex(6).getHeight()*scale);
	}
	public void unlock() {
		unl = true;
		lock = new Image(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(6));
		lock.setScale(getWidth()/lock.getWidth(), getHeight()/lock.getHeight());
	}
	public void isChar(Character character, Array<Character> list) {
		this.character = character;
		this.list = list;
		character.set("stay", null);
		character.setDirect(true);
		back = new Image(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(5));
		back.setScale(scale);
		back.setPosition(getWidth()/2-back.getWidth()/2*back.getScaleX(), 25);
		info = new TextLabel(game);
		info.setSize(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(6).getWidth()*scale, 30*scale);
		info.setFontScale(0.5f);
		info.setAlignment(Align.center);
		info.setPosition(0, 0);
		info.setText("Lv."+((int) character.getProperty("LV"))+" "+character.getName()+"\n"+character.getProfession());
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		this.delta = delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(lock != null && unl) {
			unl = false;
			addActor(lock);
		}else {
			if(back != null && character != null && !cha) {
				cha = true;
				addActor(back);
				addActor(info);
			}
		}
		super.draw(batch, parentAlpha);
		if(character != null) {
			int p = 0;
			if(character instanceof Swordman)
				p = checked?28:27;
			if(pose == 0) {
				pose = p;
				an = character.getAn(pose);
				for(RoleAnimation anima : an)
					if(anima.getFrameDuration() != 2.0f/anima.getKeyFrames().length)
						anima.setFrameDuration(2.0f/anima.getKeyFrames().length);
			}
			if(pose != p) {
				pose = p;
				an = character.getAn(pose);
				for(RoleAnimation anima : an)
					if(anima.getFrameDuration() != 2.0f/anima.getKeyFrames().length)
						anima.setFrameDuration(2.0f/anima.getKeyFrames().length);
			}
			for(RoleAnimation anima : an)
				anima.draw(false, true, batch, delta, parentAlpha, getX()+character.getX(),
						getY()+character.getY(), character.getFix(), character.getFix2(), false);
		}
	}
	public void reset() {
		checked = false;
	}
	public Character getCharacter() {
		return character;
	}
	public void addListener() {
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(checked || character == null)
					return;
				if(!checked)
					checked = true;
				for(SelectWindow sw : window)
					if(sw != context)
						sw.reset();
				for(Character ch : list)
					if(ch != character)
						ch.set("stay", null);
				character.set("move", null);
				stage.setDelete(character);
				game.setChose(character);
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
			}
		});
	}
}
