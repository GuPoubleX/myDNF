package dnf.hud;

import java.util.HashMap;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.GPXStage;
import dnf.character.Character;
import dnf.character.part.SkillPose;
import dnf.character.state.skill.common.Attack;
import dnf.character.state.skill.common.Jump;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.hud.button.*;
import dnf.hud.ui.ExpActor;
import dnf.hud.ui.HMPActor;
import dnf.hud.ui.HPLineActor;
import dnf.hud.ui.LVActor;
import dnf.hud.ui.PLActor;
import dnf.hud.ui.SkillWindow;
import dnf.hud.ui.SPActor;
import dnf.instance.ScreenInstance;
import dnf.town.ScreenTown;

public class StageHUD extends GPXStage {
	private GuPoubleXGame game = null;
	private GestureDetector gesture = null;
	private Stage controlbtn = null;
	private Stage hudbutton = null;
	private float scales = 0.8f;
	private Character ch = null;
	private ScreenTown town = null;
	private ScreenInstance instance = null;
	private boolean controled = false;
	private HashMap<Integer, Float> press = null;
	private HashMap<String, Float> pressrocker = null;
	private float time = 0;
	private Window window = null;
	private SkillWindow skillex = null;
	private HPLineActor hpa = null;
	private Rocker rocker = null;
	private ImgButton info = null;
	private ImgButton bag = null;
	private ImgButton skill = null;
	private ImgButton mission = null;
	private ImgButton ac = null;
	private ImgButton menu = null;
	private ImgButton shop = null;
	private HMPActor hp = null;
	private HMPActor mp = null;
	
	private InfoWindow winfo = null;
	private LVActor lv = null;
	private SPActor sp = null;
	private ExpActor exp = null;
	private PLActor pl = null;
	private Window hud = null;
	private SkillButton sk2[] = null;
	
	private SkillButton attack = null;
	private SkillButton jump = null;
	
	public StageHUD(Vector2 ver, Vector2 screen, Batch batch, Character ch, GuPoubleXGame game, float scale) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		controlbtn = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		hudbutton = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		gesture = new GestureDetector(new StageHUDGestureListener());
		window = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		window.setSize(getWidth(), getHeight());
		hud = new Window("", window.getStyle());
		hud.setSize(SetBase.dir_width, SetBase.dir_height);
		hud.setPosition(getWidth()/2-hud.getWidth()/2, 0);
		hudbutton.addActor(hud);
		controlbtn.addActor(window);
		this.ch = ch;
		this.game = game;
		this.scale = scale;
		this.screen = screen;
		press = new HashMap<Integer, Float>();
		pressrocker = new HashMap<String, Float>();
		initHud();
	}
	public void adactor(Actor ac) {
		window.addActor(ac);
	}
	public void changeCharacter(Character ch) {
		this.ch = ch;
		if(lv != null)
			lv.changeCharacter(ch);
		if(exp != null)
			exp.changeCharacter(ch);
		if(pl != null)
			pl.changeCharacter(ch);
		if(sp != null)
			sp.changeCharacter(ch);
		if(hp != null)
			hp.changeCharacter(ch);
		if(mp != null)
			mp.changeCharacter(ch);
	}
	@Override
	public void act(float delta) {
		time += delta;
		super.act(delta);
		hudbutton.act(delta);
		controlbtn.act(delta);
	}
	@Override
	public void draw() {
		super.draw();
		movelogic();
		hudbutton.draw();
		controlbtn.draw();
		if(game.isload(SetImg.hud, "monster_hp.img", "img") && hpa == null && instance != null) {
			hpa = new HPLineActor(game);
			hpa.setPosition(0, getHeight()*6/7);
			addActor(hpa);
		}
	}
	public void showHPline(Character ch, int maplv) {
		if(instance != null)
			hpa.setRole(ch, maplv);
	}
	private void movelogic() {
		if(!press.isEmpty()) {
			controled = true;
			resetrockerUpdate();
			Preferences pref = game.getPref(SetBase.saveHud);
			Vector2 move = null;
			Object __up = press.get(pref.getInteger("UP"));
			Object __down = press.get(pref.getInteger("DOWN"));
			Object __right = press.get(pref.getInteger("RIGHT"));
			Object __left = press.get(pref.getInteger("LEFT"));
			float ___up = 0;
			float ___down = 0;
			float ___right = 0;
			float ___left = 0;
			if(__up != null)
				___up = Float.parseFloat(__up.toString());
			if(__down != null)
				___down = Float.parseFloat(__down.toString());
			if(__right != null)
				___right = Float.parseFloat(__right.toString());
			if(__left != null)
				___left = Float.parseFloat(__left.toString());
			float up = 0;
			float right = 0;
			up = (___up==0&&___down==0)?0:(Math.max(___up, ___down)==___up?1:-1);
			right = (___right==0&&___left==0)?0:(Math.max(___right, ___left)==___right?1:-1);
			move = new Vector2(right*(instance!=null?SetCharProperty.instance_x:SetCharProperty.town_x),
					up*(instance!=null?SetCharProperty.instance_y:SetCharProperty.town_y));
			if(!(ch.currentPose() instanceof SkillPose)) {
				if(right == 1)
					ch.setDirect(true);
				else if(right == -1)
					ch.setDirect(false);
				ch.set("move", move);
			}else {
				attack.setPoseMove(move);
				jump.setPoseMove(move);
				for(SkillButton sb : sk2)
					if(sb != null)
						sb.setPoseMove(move);
			}
		}else {
			controled = false;
			if(!pressrocker.isEmpty()) {
				Vector2 move = null;
				Object __up = pressrocker.get("UP");
				Object __down = pressrocker.get("DOWN");
				Object __right = pressrocker.get("RIGHT");
				Object __left = pressrocker.get("LEFT");
				float ___up = 0;
				float ___down = 0;
				float ___right = 0;
				float ___left = 0;
				if(__up != null)
					___up = Float.parseFloat(__up.toString());
				if(__down != null)
					___down = Float.parseFloat(__down.toString());
				if(__right != null)
					___right = Float.parseFloat(__right.toString());
				if(__left != null)
					___left = Float.parseFloat(__left.toString());
				float up = 0;
				float right = 0;
				up = (___up==0&&___down==0)?0:(Math.max(___up, ___down)==___up?1:-1);
				right = (___right==0&&___left==0)?0:(Math.max(___right, ___left)==___right?1:-1);
				move = new Vector2(right*(instance!=null?SetCharProperty.instance_x:SetCharProperty.town_x),
						up*(instance!=null?SetCharProperty.instance_y:SetCharProperty.town_y));
				if(!(ch.currentPose() instanceof SkillPose)) {
					if(right == 1)
						ch.setDirect(true);
					else if(right == -1)
						ch.setDirect(false);
					ch.set("move", move);
				}else {
					attack.setPoseMove(move);
					jump.setPoseMove(move);
					for(SkillButton sb : sk2)
						if(sb != null)
							sb.setPoseMove(move);
				}
			}else {
				if(!(ch.currentPose() instanceof SkillPose))
					ch.set("stay", null);
				else {
					if(instance != null) {
						attack.setPoseMove(new Vector2(0, 0));
						jump.setPoseMove(new Vector2(0, 0));
						for(SkillButton sb : sk2)
							if(sb != null)
								sb.setPoseMove(new Vector2(0, 0));
					}
				}
			}
		}
	}
	public void rockerUpdate(boolean up, boolean down, boolean right, boolean left) {
		if(up)
			pressrocker.put("UP", time);
		else
			pressrocker.remove("UP");
		if(down)
			pressrocker.put("DOWN", time);
		else
			pressrocker.remove("DOWN");
		if(right)
			pressrocker.put("RIGHT", time);
		else
			pressrocker.remove("RIGHT");
		if(left)
			pressrocker.put("LEFT", time);
		else
			pressrocker.remove("LEFT");
	}
	public void resetrockerUpdate() {
		pressrocker.clear();
	}
	private void visibleBtn(Window w, boolean visible) {
		w.setVisible(visible);
		if(game.getPref(SetBase.saveGeneral).getBoolean("BUTTON")) {
			rocker.setVisible(!visible);
			if(attack != null)
				attack.setVisible(!visible);
			if(jump != null)
				jump.setVisible(!visible);
			for(SkillButton sb : sk2)
				if(sb != null)
					sb.setVisible(!visible);
		}
	}
	private void initHud() {
		Image image = new Image(game.getImg(SetImg.hud, "hud.img").getIndex(0));
		image.setScale(scales);
		hud.addActor(image);
		hp = new HMPActor(game.getImg(SetImg.hud, "hud.img").getIndex(1), game, true, ch, scales);
		mp = new HMPActor(game.getImg(SetImg.hud, "hud.img").getIndex(2), game, false, ch, scales);
		hp.setPosition(33, 38);
		mp.setPosition(hud.getWidth()-33, 38);
		hud.addActor(hp);
		hud.addActor(mp);
		lv = new LVActor(window.getStyle(), ch, game);
		lv.setScale(scales);
		lv.setPosition(7, 3);
		hud.addActor(lv);
		exp = new ExpActor(ch, game, scales);
		exp.setPosition(70.5f, 2);
		hud.addActor(exp);
		pl = new PLActor(ch, game, scales);
		pl.setPosition(254, 35);
		hud.addActor(pl);
		sp = new SPActor(window.getStyle(), ch, game);
		sp.setScale(scales);
		sp.setPosition(592, 3);
		hud.addActor(sp);
		Window btn = new Window("", window.getStyle());
		btn.setSize(231, 25);
		info = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(5), game.getImg(SetImg.hud, "hud.img").getIndex(6));
		info.setPosition(0, 0);
		btn.addActor(info);
		bag = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(8), game.getImg(SetImg.hud, "hud.img").getIndex(9));
		bag.setPosition(info.getX()+info.getWidth()+2, 0);
		btn.addActor(bag);
		mission = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(11), game.getImg(SetImg.hud, "hud.img").getIndex(12));
		mission.setPosition(bag.getX()+bag.getWidth()+2, 0);
		btn.addActor(mission);
		skill = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(14), game.getImg(SetImg.hud, "hud.img").getIndex(15));
		skill.setPosition(mission.getX()+mission.getWidth()+2, 0);
		btn.addActor(skill);
		ac = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(17), game.getImg(SetImg.hud, "hud.img").getIndex(18));
		ac.setPosition(skill.getX()+skill.getWidth()+2, 0);
		btn.addActor(ac);
		menu = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(68), game.getImg(SetImg.hud, "hud.img").getIndex(69));
		menu.setPosition(ac.getX()+ac.getWidth()+2, 0);
		btn.addActor(menu);
		shop = new ImgButton(game.getImg(SetImg.hud, "hud.img").getIndex(72), game.getImg(SetImg.hud, "hud.img").getIndex(73));
		shop.setPosition(menu.getX()+menu.getWidth()+2, 0);
		btn.addActor(shop);
		btn.setScale(scales);
		btn.setPosition(283*scales, 15*scales);
		hud.addActor(btn);
		skillex = new SkillWindow(game, new Window.WindowStyle(game.getLazy(), Color.WHITE, null), SetBase.saveHud, scales);
		skillex.setPosition(536*scales, 12*scales);
		hud.addActor(skillex);
	}
	private void initButton() {
		if(rocker == null) {
			Sprite down = game.getImg(SetImg.hud, "cs_avatar_roulette_jp.img").getIndex(2);
			Sprite up = game.getImg(SetImg.hud, "cs_avatar_roulette_jp.img").getIndex(5);
			Sprite downex = game.getImg(SetImg.hud, "cs_avatar_roulette_jp.img").getIndex(3);
			rocker = new Rocker(this, down, up, downex);
			rocker.setPosition(window.getWidth()/6, window.getHeight()/4);
			window.addActor(rocker);
		}
		if(town != null) {
			if(sk2 == null) {
				sk2 = new SkillButton[12];
				for(int i = 0; i < 12; i++) {
					SkillButton sb = null;
					if(ch.getSkillID()[i] != -1) {
						sb = new SkillButton(game, ch, i+1, game.getImg(SetImg.skillicon, "swordman_skillicon.img").getIndex(ch.getSkillID()[i]),
								game.getImg(SetImg.skillicon, "swordman_skillicon.img").getIndex(ch.getSkillID()[i]+1), scales);
						if(i < 6)
							sb.setPosition(1.5f+sb.getWidth()*(i%6)+1.5f*(i%6), sb.getHeight()+6);
						else
							sb.setPosition(1.5f+sb.getWidth()*(i%6)+1.5f*(i%6), 1);
						sb.GroupAdd(skillex);
					}
					sk2[i] = sb;
				}
				skillex.resetKey();
			}
			if(!game.getPref(SetBase.saveGeneral).getBoolean("BUTTON"))
				rocker.setVisible(false);
		}else if(instance != null) {
			if(attack == null) {
				attack = new SkillButton(game, ch, game.getImg(SetImg.hud, "social.img").getIndex(0), game.getImg(SetImg.hud, "social.img").getIndex(2), scales, new Attack(ch.getGame()));
				attack.setPosition(getWidth()*4/5, getHeight()/15);
				attack.GroupAdd(window);
			}
			if(jump == null) {
				jump = new SkillButton(game, ch, game.getImg(SetImg.hud, "social.img").getIndex(0), game.getImg(SetImg.hud, "social.img").getIndex(2), scales, new Jump(ch.getGame()));
				jump.setPosition(attack.getX()-70, attack.getY()+100);
				jump.GroupAdd(window);
			}
			if(sk2 == null) {
				sk2 = new SkillButton[12];
				float x1 = 0;
				float x2 = 0;
				float y1 = 0;
				float y2 = 0;
				float _x = getWidth()/2;
				float _y = getHeight()/7;
				float sbscale = 1.3f;
				for(int i = 0; i < 12; i++) {
					SkillButton sb = null;
					if(ch.getSkillID()[i] != -1) {
						sb = new SkillButton(game, ch, i+1, game.getImg(SetImg.skillicon, "swordman_skillicon.img").getIndex(ch.getSkillID()[i]),
								game.getImg(SetImg.skillicon, "swordman_skillicon.img").getIndex(ch.getSkillID()[i]+1), sbscale, scales);
						if(i < 6) {
							x1 = _x+(sb.getWidth()*sbscale+5)*((i%6)-3);
							y1 = _y+(sb.getHeight()*sbscale+5)*1;
							x2 = 1.5f+sb.getWidth2()*(i%6)+1.5f*(i%6);
							y2 = sb.getHeight2()+6;
						}else {
							x1 = _x+(sb.getWidth()*sbscale+5)*((i%6)-3);
							y1 = _y+(sb.getHeight()*sbscale+5)*0;
							x2 = 1.5f+sb.getWidth2()*(i%6)+1.5f*(i%6);
							y2 = 1;
						}
						sb.setPosition(x1, y1, x2, y2);
						sb.GroupAdd(window, skillex);
					}
					sk2[i] = sb;
				}
				skillex.resetKey();
			}
			if(!game.getPref(SetBase.saveGeneral).getBoolean("BUTTON")) {
				rocker.setVisible(false);
				attack.setVisible(false);
				jump.setVisible(false);
				for(SkillButton sb : sk2)
					if(sb != null)
						sb.setVisible(false);
			}
		}
	}
	public void instanceOrtown() {
		Window win = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		win.setSize(270, 54+30*3+17);
		win.setPosition(getWidth()-win.getWidth(), getHeight()-win.getHeight());
		//Image img = new Image(game.getImg(SetImg.instance, "eplpnew.img").getIndex(9));
		//img.setScale(win.getWidth()/img.getWidth(), win.getHeight()/img.getHeight());
		//win.addActor(img);
		Image img = new Image(game.getImg(SetImg.instance, "eplpnew.img").getIndex(0));
		img.setPosition(0, win.getHeight()-img.getHeight());
		win.addActor(img);
		float y = img.getY();
		for(int i = 0; i < 3; i++) {
			img = new Image(game.getImg(SetImg.instance, "eplpnew.img").getIndex(1));
			img.setPosition(0, y-img.getHeight()*(i+1));
			win.addActor(img);
		}
		img = new Image(game.getImg(SetImg.instance, "eplpnew.img").getIndex(2));
		img.setPosition(1, 0);
		win.addActor(img);
		reinstance = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(3)),
				new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(4)));
		reinstance.setPosition(img.getX()+img.getWidth()/2-reinstance.getWidth()/2, 17+10);
		win.addActor(reinstance);
		otherinstance = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(5)),
				new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(6)));
		otherinstance.setPosition(img.getX()+img.getWidth()/2-otherinstance.getWidth()/2, 17+40);
		win.addActor(otherinstance);
		returntown = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(7)),
				new TextureRegionDrawable(game.getImg(SetImg.instance, "eplpnew.img").getIndex(8)));
		returntown.setPosition(img.getX()+img.getWidth()/2-returntown.getWidth()/2, 17+70);
		win.addActor(returntown);
		addActor(win);
		reinstance.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				game.change("null", "", 0, null);
			}
		});
		otherinstance.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				instance.choseMap();
			}
		});
		returntown.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				instance.returnTown();
			}
		});
	}
	private ImageButton returntown = null;
	private ImageButton reinstance = null;
	private ImageButton otherinstance = null;
	public void inScreenTown(ScreenTown town) {
		this.town = town;
		instance = null;
	}
	public void inScreenInstance(ScreenInstance instance) {
		this.instance = instance;
		town = null;
	}
	public ScreenInstance getInstance() {
		return instance;
	}
	public void setButtonVisible(boolean visible) {
		rocker.setVisible(visible);
		attack.setVisible(visible);
		jump.setVisible(visible);
		for(SkillButton sb : sk2)
			if(sb != null)
				sb.setVisible(visible);
	}
	public void removeListener(InputMultiplexer input) {
		input.removeProcessor(gesture);
		input.removeProcessor(controlbtn);
		input.removeProcessor(hudbutton);
		input.removeProcessor(this);
	}
	public void removListener(InputMultiplexer input) {
		initButton();
		controlListener();
		buttonListener();
		input.removeProcessor(gesture);
		input.removeProcessor(controlbtn);
		input.removeProcessor(hudbutton);
		input.removeProcessor(this);
	}
	public void addListener(InputMultiplexer input) {
		initButton();
		controlListener();
		buttonListener();
		input.addProcessor(gesture);
		input.addProcessor(controlbtn);
		input.addProcessor(hudbutton);
		input.addProcessor(this);
	}
	public boolean isKeybord() {
		return controled;
	}
	private void buttonListener() {
		info.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				if(info.isChecked()) {
					if(winfo == null) {
						winfo = new InfoWindow(window.getStyle(), game, ch, window);
						winfo.setPosition(0, window.getHeight()-winfo.getHeight()-20);
						window.addActor(winfo);
					}
					visibleBtn(winfo, true);
				}else
					visibleBtn(winfo, false);
			}
		});
		bag.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!ch.inInstance()) {
					game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
					//ch.changeProperty("EXP", 10000, false, false);
				}
			}
		});
		mission.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
			}
		});
		skill.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				showHPline(ch, 3);
			}
		});
	//	ac
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
				if(town != null) {
					ch.resetTime();
					town.getGame().change("admin", "select", 0, null);
				}else if(instance != null) {
					if(!instance.passInstance())
						ch.currentWeak();
					instance.returnTown();
				}
			}
		});
	//	shop
		if(ch.inInstance()) {
			if(attack != null)
				attack.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						attack.play();
					}
				});
			if(jump != null)
				jump.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						jump.play();
					}
				});
			for(SkillButton sb : sk2)
				if(sb != null)
					sb.addListener();
		}
	}
	private void controlListener() {
		controlbtn.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
			//	System.out.println(keycode);
			//	Preferences pref = Gdx.app.getPreferences(SetBase.saveHud);
			//	pref.putInteger("SKILL_1", pref.getInteger("SKILL_1")+1);
			//	pref.flush();
				presskey(keycode, true);
				return false;
			}
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				presskey(keycode, false);
			//	hudmoved(keycode);
				return false;
			}
			@Override
			public boolean keyTyped(InputEvent event, char c) {
				presskey(presskey(c), true);
			//	hudmoving(c);
				hudcontrol(c);
				return false;
			}
		});
	}
	private void presskey(int keycode, boolean presing) {
		Preferences pref = game.getPref(SetBase.saveHud);
		if(presing) {
			pressing(pref.getInteger("UP"), keycode);
			pressing(pref.getInteger("DOWN"), keycode);
			pressing(pref.getInteger("RIGHT"), keycode, ch.isRight());
			pressing(pref.getInteger("LEFT"), keycode, ch.isRight());
		}else {
			pressed(pref.getInteger("UP"), keycode);
			pressed(pref.getInteger("DOWN"), keycode);
			pressed(pref.getInteger("RIGHT"), keycode, ch.isRight());
			pressed(pref.getInteger("LEFT"), keycode, ch.isRight());
		}
	}
	private void pressing(int key, int keycode) {
		if(key == keycode)
			press.put(keycode, time);
	}
	private void pressed(int key, int keycode) {
		if(key == keycode)
			press.remove(keycode);
	}
	private void pressing(int key, int keycode, boolean right) {
		if(key == keycode)
			press.put(keycode, time);
	}
	private void pressed(int key, int keycode, boolean right) {
		if(key == keycode)
			press.remove(keycode);
	}
	private boolean hudcontrol(char c) {
		if(ch.isclearWorld() || instance == null)
			return false;
		Preferences pref = game.getPref(SetBase.saveHud);
		int attackkey = pref.getInteger("ATTACK");
		int jumpkey = pref.getInteger("JUMP");
		int skillpress[] = new int[12];
		for(int i = 0; i < skillpress.length; i++)
			skillpress[i] = pref.getInteger("SKILL_"+(i+1));
		if(attackkey == presskey(c))
			attack.play();
		else if(jumpkey == presskey(c))
			jump.play();
		else
			for(int i = 0; i < skillpress.length; i++)
				if(skillpress[i] == presskey(c) && sk2.length >= i)
					if(sk2[i] != null)
						sk2[i].play();
		return false;
	}
	private int presskey(char c) {
		int key = 0;
		switch((int) c) {
			case 'a':key = Keys.A;break;
			case 'b':key = Keys.B;break;
			case 'c':key = Keys.C;break;
			case 'd':key = Keys.D;break;
			case 'e':key = Keys.E;break;
			case 'f':key = Keys.F;break;
			case 'g':key = Keys.G;break;
			case 'h':key = Keys.H;break;
			case 'i':key = Keys.I;break;
			case 'j':key = Keys.J;break;
			case 'k':key = Keys.K;break;
			case 'l':key = Keys.L;break;
			case 'm':key = Keys.M;break;
			case 'n':key = Keys.N;break;
			case 'o':key = Keys.O;break;
			case 'p':key = Keys.P;break;
			case 'q':key = Keys.Q;break;
			case 'r':key = Keys.R;break;
			case 's':key = Keys.S;break;
			case 't':key = Keys.T;break;
			case 'u':key = Keys.U;break;
			case 'v':key = Keys.V;break;
			case 'w':key = Keys.W;break;
			case 'x':key = Keys.X;break;
			case 'y':key = Keys.Y;break;
			case 'z':key = Keys.Z;break;
			case '0':key = Keys.NUM_0;break;
			case '1':key = Keys.NUM_1;break;
			case '2':key = Keys.NUM_2;break;
			case '3':key = Keys.NUM_3;break;
			case '4':key = Keys.NUM_4;break;
			case '5':key = Keys.NUM_5;break;
			case '6':key = Keys.NUM_6;break;
			case '7':key = Keys.NUM_7;break;
			case '8':key = Keys.NUM_8;break;
			case '9':key = Keys.NUM_9;break;
		//	case Keys.NUMPAD_0:key = 35;break;
		//	case Keys.NUMPAD_1:key = 36;break;
		//	case Keys.NUMPAD_2:key = 37;break;
		//	case Keys.NUMPAD_3:key = 38;break;
		//	case Keys.NUMPAD_4:key = 39;break;
		//	case Keys.NUMPAD_5:key = 40;break;
		//	case Keys.NUMPAD_6:key = 41;break;
		//	case Keys.NUMPAD_7:key = 42;break;
		//	case Keys.NUMPAD_8:key = 43;break;
		//	case Keys.NUMPAD_9:key = 44;break;
			case '-':key = Keys.MINUS;break;
			case '=':key = Keys.EQUALS;break;
			case '[':key = Keys.LEFT_BRACKET;break;
			case ']':key = Keys.RIGHT_BRACKET;break;
			case ';':key = Keys.SEMICOLON;break;
			case '\'':key = Keys.APOSTROPHE;break;
			case ',':key = Keys.COMMA;break;
			case '.':key = Keys.PERIOD;break;
			case '/':key = Keys.SLASH;break;
		}
		return key;
	}
	private class StageHUDGestureListener implements GestureListener {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
		//	System.out.println(x+"---"+y);
		//	if(x < 300) {
		//		ch.changeProperty("EXP", 23, false, false);
		//		ch.changeProperty("SP", 1, false, false);
		//	}
		//	if(x < 300) {
		//		ch.changeProperty("VIT", -1, false, true);
		//		ch.changeProperty("STR", -1, false, true);
		//		ch.changeProperty("INT", 1, false, true);
		//	}if(y < 300) {
		//		ch.changeProperty("MEN", -1, false, true);
		//		ch.changeProperty("STR", 1, false, true);
		//	}
			return false;
		}
		@Override
		public boolean tap(float x, float y, int count, int button) {
			return false;
		}
		@Override
		public boolean longPress(float x, float y) {
			return false;
		}
		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			return false;
		}
		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			return false;
		}
		@Override
		public void pinchStop () {
		}
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			boolean score = propertyWindowScroll(x, y, deltaX, deltaY);
			return score;
		}
		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			return false;
		}
		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			return false;
		}
	}
	private boolean propertyWindowScroll(float x, float y, float deltaX, float deltaY) {
		if(winfo != null)
			return winfo.getScroll().scroll(x, y, deltaX, deltaY, scale);
		else
			return false;
	}
}