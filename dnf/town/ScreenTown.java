package dnf.town;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetNPC;
import dnf.gupoublex.set.SetTown;
import dnf.hud.StageHUD;
import dnf.town.elvengard.*;
import dnf.town.hendonmyre.*;
import dnf.town.hotel.*;
import dnf.town.westcoast.*;
import dnf.author.GPXStage;
import dnf.character.Character;

public class ScreenTown implements Screen {
	private InputMultiplexer input = null;
	private Character ch = null;
	private GuPoubleXGame game = null;
	private Music music = null;
	private Vector2 ver = null;
	private Vector2 screen = null;
	private String changestage = null;
	private String musicpath = null;
	private String mapstr = null;
	private String mapbg = null;
	private String mapname = null;
	private String lasttheme[] = null;
	private String currenttheme[] = null;
	private HashMap<String, String> npc = null;
	private Stage back = null;
	private GPXStage stage = null;
	private StageHUD hud = null;
	private Stage info = null;
	private Stage theme = null;
	private Stage title = null;
	private float scalex = 1;
	private float scaley = 1;
	private Array<Character> all = null;
	private Vector2 charLocation = null;
	private boolean changemusic = false;
	private boolean isbar = false;
	private boolean showtheme = false;
	private int changetown = -1;
	public ScreenTown(GuPoubleXGame game, AssetManager manager, Vector2 ver, Vector2 screen, float scaleX, float scaleY, Character ch, Vector2 ptown) {
		super();
		this.ch = ch;
		this.game = game;
		this.ver = ver;
		this.screen = screen;
		this.scalex = scaleX;
		this.scaley = scaleY;
		if(ptown != null)
			charLocation = ptown;
		all = new Array<Character>();
		npc = new HashMap<String, String>();
	}
	public void charNewLoction(Vector2 ver) {
		charLocation = ver;
	}
	public Vector2 getcharNewLoction() {
		return charLocation;
	}
	public void change(String name, int lv, Array<Integer> info) {
		stage.destoryWorld();
		game.change("instance", name, lv, info);
	}
	public void change(String str) {
		Stage temp = null;
		if(stage != null) {
			if(input != null)
				input.removeProcessor(stage);
			stage.destoryWorld();
			temp = stage;
			stage = null;
			if(temp != null) {
				temp.dispose();
				unload(npc);
			}
		}
		if(str.equals("map")) {
			if(input != null)
				hud.removeListener(input);
			if(hud != null)
				hud.dispose();
			hud = null;
			initmap();
		}else {
			init(str);
		}
		changestage = str;
	}
	private void changeStage() {
		if(changestage != null) {
			all.clear();
			all.add(ch);
			//all.add(other); sym to add other role
			if(changestage.equals("map"))
				stage = new StageMap(this, ver, back.getBatch(), ch, all, game, input, mapstr, mapbg, mapname);
			else if(changestage.equals("hotel"))
				stage = new Hotel(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("elvengardstreet"))
				stage = new ElvengardStreet(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("lorien"))
				stage = new Lorien(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("elvengardcross"))
				stage = new ElvengardCross(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("municipal"))
				stage = new Municipal(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("granfloris2"))
				stage = new Granfloris2(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("granfloris1"))
				stage = new Granfloris1(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("square"))
				stage = new Square(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("backstreet"))
				stage = new BackStreet(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("oldcity"))
				stage = new Oldcity(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("hendonmyrecross"))
				stage = new HendonmyreCross(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("bar"))
				stage = new Bar(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("businessarea"))
				stage = new BusinessArea(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("magicguild"))
				stage = new MagicGuild(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			else if(changestage.equals("deadcanyon"))
				stage = new Deadcanyon(this, ver, back.getBatch(), Math.max(scalex, scaley), ch, all, charLocation, npc);
			input.addProcessor(stage);
		}
		changestage = null;
	}
	@Override
	public void dispose() {
		if(music.isPlaying())
			music.stop();
		if(ch.getTownPart() == SetTown.hotel)
			unload(SetImg.hotel, "hotel/");
		if(ch.getTown() == SetTown.elvengard)
			unload(SetImg.elvengard, "elvengard/");
		else if(ch.getTown() == SetTown.hendonmyre)
			unload(SetImg.hendonmyre, "hendonmyre/");
		else if(ch.getTown() == SetTown.westcoast)
			unload(SetImg.westcoast, "westcoast/");
		else if(ch.getTown() == SetTown.alfhlyra)
			;//unload("alfhlyra.atlas", "alfhlyra/alfhlyra.ogg");
		else if(ch.getTown() == SetTown.stormpass)
			;//unload("stormpass.atlas", "stormpass/stormpass.ogg");
		else if(ch.getTown() == SetTown.gent)
			;//unload("gent.atlas", "gent/gent.ogg");
		unload(npc);
		game.unload(SetImg.skillicon, "img");
		game.unload(SetImg.hud, "img");
		game.unload(mapstr, "img");
		game.unload(mapbg, "img");
		game.unload(mapstr+"back/"+mapname, "img");
		game.unload(SetBase.map, "sound");
		if(currenttheme != null)
			for(String s : currenttheme)
				game.unload(s, "img");
		back.dispose();
		if(hud != null)
			hud.dispose();
		info.dispose();
		if(stage != null)
			stage.dispose();
		theme.dispose();
		title.dispose();
		Gdx.app.log(SetBase.TAG, "ScreenTown release");
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void render(float delta) {
		if(isbar){
			if(game.isload(musicpath, 1, "music") && music == null) {
				music = game.getMusic(musicpath, 1);
				music.setLooping(true);
				music.setVolume(game.getSound_music());
				music.play();
			}
		}else if(game.isload(musicpath, 0, "music") && music == null) {
			music = game.getMusic(musicpath, 0);
			music.setLooping(true);
			music.setVolume(game.getSound_music());
			music.play();
			theme.clear();
		}
		if(currenttheme != null && showtheme && game.isload(currenttheme[0], 0, "img") && game.isload(currenttheme[1], 0, "img")) {
			showtheme = false;
			theme.clear();
			Image img = new Image(game.getImg(currenttheme[0], 0).getIndex(0));
			img.setScale(theme.getWidth()/img.getWidth());
			img.setPosition(theme.getWidth()/2-img.getWidth()/2*img.getScaleX(), theme.getHeight()/2-img.getHeight()/2*img.getScaleY());
			theme.addActor(img);
			AlphaImage(img, 0.5f, 1.5f);
			title.clear();
			Image img2 = new Image(game.getImg(currenttheme[1], 0).getIndex(0));
			img2.setPosition(title.getWidth()/2-img2.getWidth()/2, title.getHeight()/2-img2.getHeight()/2);
			title.addActor(img2);
			AlphaImage(img2, 0.5f, 1.5f);
		}
		if(game.getManager().update() && changestage != null) {
			if(input == null)
				input = new InputMultiplexer();
			changeStage();
		}
		if(game.getManager().update() && hud == null && !(stage instanceof StageMap) && stage != null) {
			hud = new StageHUD(ver, screen, back.getBatch(), ch, game, Math.max(scalex, scaley));
			hud.inScreenTown(this);
			hud.addListener(input);
			Gdx.input.setInputProcessor(input);
		}
		game.show(info, delta);
		back.act(delta);
		if(stage != null) {
			stage.act(delta);
			if(hud != null)
				hud.act(delta);
		}
		theme.act(delta);
		title.act(delta);
		info.act(delta);
		back.draw();
		if(stage != null) {
			stage.draw();
			if(hud != null)
				hud.draw();
		}
		theme.draw();
		title.draw();
		info.draw();
	}
	@Override
	public void resize(int width, int height) {
		screen = new Vector2(width, height);
		scalex = 1.0f*SetBase.dir_width/width;
		scaley = 1.0f*SetBase.dir_height/height;
		back.getViewport().update(width, height, false);
		if(stage != null) {
			stage.resetScale(Math.max(scalex, scaley), screen);
			stage.getViewport().update(width, height, false);
			if(hud != null) {
				hud.resetScale(Math.max(scalex, scaley), screen);
				hud.getViewport().update(width, height, false);
			}
		}
		theme.getViewport().update(width, height, false);
		title.getViewport().update(width, height, false);
		info.getViewport().update(width, height, false);
	}
	@Override
	public void resume() {
	}
	@Override
	public void show() {
		back = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y));
		theme = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		title = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		info = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		game.getLoading().setPosition(back.getWidth()-game.getLoading().getWidth(), back.getHeight()/8);
		back.addActor(game.getLoading());
		game.load(SetImg.hud, "img");
		game.load(SetImg.skillicon, "img");
		game.load(SetBase.map, "sound");
	}
	private void AlphaImage(Image image, float alphaTime, float delayTime) {
		AlphaAction alphaAction = Actions.fadeOut(alphaTime, Interpolation.pow3);
		DelayAction delayAction = Actions.delay(delayTime);
		Action hideAction = Actions.hide();
		SequenceAction sequence = Actions.sequence(delayAction, alphaAction, hideAction);
		image.addAction(sequence);
	}
	private void init(String str) {
		if(changemusic) {
			changemusic = false;
			if(music != null && music.isPlaying())
				music.stop();
			music = null;
		}
		if(ch.getTownPart() == SetTown.hotel) {
			init(SetImg.hotel, "hotel", str, "hotel/");
			unload(SetImg.elvengard, "elvengard/");
			unload(SetImg.hendonmyre, "hendonmyre/");
			unload(SetImg.westcoast, "westcoast/");
		}else if(ch.getTown() == SetTown.elvengard) {
			currenttheme = SetImg.elvengardtheme;
			init(SetImg.elvengard, "elvengard", str, "elvengard/");
			unload(SetImg.hotel, "hotel/");
			unload(SetImg.hendonmyre, "hendonmyre/");
			unload(SetImg.westcoast, "westcoast/");
		}else if(ch.getTown() == SetTown.hendonmyre) {
			currenttheme = SetImg.hendonmyretheme;
			init(SetImg.hendonmyre, "hendonmyre", str, "hendonmyre/");
			unload(SetImg.hotel, "hotel/");
			unload(SetImg.elvengard, "elvengard/");
			unload(SetImg.westcoast, "westcoast/");
		}else if(ch.getTown() == SetTown.westcoast) {
			currenttheme = SetImg.westcoasttheme;
			init(SetImg.westcoast, "westcoast", str, "westcoast/");
			unload(SetImg.hotel, "hotel/");
			unload(SetImg.elvengard, "elvengard/");
			unload(SetImg.hendonmyre, "hendonmyre/");
		}
	}
	public void initmap() {
		if(ch.getTownPart() == SetTown.lorien) {
			mapname = "lorien";
			mapstr = SetImg.map;
			mapbg = SetImg.lorienbg;
		}else if(ch.getTownPart() == SetTown.granfloris1 || ch.getTownPart() == SetTown.granfloris2) {
			mapname = "lorien";
			mapstr = SetImg.map;
			mapbg = SetImg.lorienbg;
		}
		game.load(mapstr, "img");
		game.load(mapbg, "img");
		game.load(mapstr+"back/"+mapname, "img");
	}
	public void init(String text, String str, String loc, String music) {
		if(mapstr != null) {
			game.unload(mapstr, "img");
			game.unload(mapbg, "img");
			mapstr = null;
		}
		musicpath = SetTown.musicpath+music;
		game.load(text, "img");
		game.load(musicpath, "music");
		if(currenttheme != null) {
			if(changetown == -1) {
				changetown = ch.getTown();
				showtheme = true;
			}else if(changetown != ch.getTown()) {
				changetown = ch.getTown();
				showtheme = true;
			}
			if(showtheme)
				for(String s : currenttheme)
					game.load(s, "img");
		}
		if(loc.indexOf("hotel") != -1) {
			npc.put(SetNPC.seria, SetImg.npc+"npc_se");
		}else if(str.indexOf("elvengard") != -1) {
			if(loc.equals("elvengardstreet")) {
				npc.put(SetNPC.linus, SetImg.npc+"linus");
				npc.put(SetNPC.dj0, SetImg.npc+"npc_dj");
			}
		}else if(str.indexOf("hendonmyre") != -1) {
			if(loc.equals("municipal")) {
				npc.put(SetNPC.al, SetImg.npc+"npc_al");
				npc.put(SetNPC.gd, SetImg.npc+"npc_gd");
				npc.put(SetNPC.bk, SetImg.npc+"npc_bk");
			}else if(loc.equals("square")) {
				npc.put(SetNPC.pj, SetImg.npc+"npc_pj");
				npc.put(SetNPC.dj1, SetImg.npc+"npc_dj1");
				npc.put(SetNPC.rt, SetImg.npc+"npc_rt");
				npc.put(SetNPC.kr, SetImg.npc+"npc_kr");
			}else if(loc.equals("oldcity")) {
				npc.put(SetNPC.pj, SetImg.npc+"npc_or");
				npc.put(SetNPC.dj1, SetImg.npc+"kanna");
				npc.put(SetNPC.rt, SetImg.npc+"npc_sin");
				npc.put(SetNPC.kr, SetImg.npc+"npc_gsd");
			}else if(loc.equals("bar"))
				npc.put(SetNPC.su, SetImg.npc+"npc_su");
		}else if(str.indexOf("westcoast") != -1) {
			if(loc.equals("businessarea")) {
				npc.put(SetNPC.lo, SetImg.npc+"npc_lo");
				npc.put(SetNPC.kg, SetImg.npc+"npc_kg");
				npc.put(SetNPC.rg, SetImg.npc+"npc_rg");
				npc.put(SetNPC.dp, SetImg.npc+"npc_dp");
				npc.put(SetNPC.dj2, SetImg.npc+"npc_dj2");
			}else if(loc.equals("magicguild")) {
				npc.put(SetNPC.sr, SetImg.npc+"npc_sr");
				npc.put(SetNPC.ir, SetImg.npc+"npc_ir");
				npc.put(SetNPC.dj3, SetImg.npc+"npc_dj3");
			}
		}
		init(npc);
	}
	private void init(HashMap<String, String> npc) {
		if(npc.isEmpty())
			return;
		for(String s : npc.values())
			game.load(s, "img");
	}
	private void unload(HashMap<String, String> npc) {
		if(npc.isEmpty())
			return;
		for(String s : npc.values())
			game.unload(s, "img");
		npc.clear();
	}
	private void unload(String text, String music) {
		game.unload(text, "img");
		game.unload(SetTown.musicpath+music, "music");
	}
	public GuPoubleXGame getGame() {
		return game;
	}
	public void setChangemusic(boolean changemusic) {
		lasttheme = currenttheme;
		if(lasttheme != null)
			for(String s : lasttheme)
				game.unload(s, "img");
		lasttheme = null;
		this.changemusic = changemusic;
	}
	public void setChangemusic(boolean changemusic, boolean bar) {
		this.changemusic = changemusic;
		this.isbar = bar;
	}
}