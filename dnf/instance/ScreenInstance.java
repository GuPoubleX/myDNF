package dnf.instance;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.admin.SpriteAnimation;
import dnf.author.GPXStage;
import dnf.character.Character;
import dnf.character.other.NPC;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetInstance;
import dnf.gupoublex.set.SetNPC;
import dnf.hud.StageHUD;
import dnf.instance.reward.ExpNumberWindow;
import dnf.instance.reward.FaceActor;
import dnf.instance.reward.HitShowActor;
import dnf.instance.reward.PercentActor;
import dnf.instance.reward.SlashImage;

public class ScreenInstance implements Screen {
	private InputMultiplexer input = null;
	private Character ch = null;
	private GuPoubleXGame game = null;
	private Music music = null;
	private Music musicb = null;
	private Vector2 loc = null;
	private Vector2 ver = null;
	private Vector2 screen = null;
	private String map = null;
	private String mappath = null;
	private String loadpath = null;
	private String loadname = null;
	private String musicpath = null;
	private String titlepath = null;
	private Stage back = null;
	private GPXStage stage = null;
	private StageHUD hud = null;
	private Stage info = null;
	private StageReward reward = null;
	private int changestage = 0;
	private float scalex = 1;
	private float scaley = 1;
	private int lv = 0;
	private Array<Character> team = null;
	private Array<Character> emeny = null;
	private boolean startgame = false;
	private int brith = 1;
	private int boss = 1;
	private int bosscount = 1;
	private DoorActor dor = null;
	private String mappref = null;
	private HashMap<Integer, GPXStage> hashmap = null;
	private float time = 0;
	private float delaytime = 0;
	private boolean appearstage = false;
	private Array<SlashImage> addexp = null;
	private Array<FaceActor> facelist = null;
	private boolean win1 = false;
	private boolean win2 = false;
	private boolean win3 = false;
	private float faceaction = 0;
	private NPC deliah = null;
	private NPC fire = null;
	private boolean pass = false;
	private boolean returntown = false;
	private float returntime = 0;
	public boolean passInstance() {
		return pass;
	}
	private float deltaTime(float delta) {
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:
			case Desktop:delta = SetBase.step;
			case WebGL:break;
			default:break;
		}
		return delta;
	}
	public ScreenInstance(GuPoubleXGame game, Vector2 ver, Vector2 screen, float scaleX, float scaleY, Character ch, String map, int lv, Array<Integer> inf) {
		super();
		this.ch = ch;
		this.game = game;
		this.ver = ver;
		this.screen = screen;
		this.scalex = scaleX;
		this.scaley = scaleY;
		this.lv = lv;
		this.map = map;
		team = new Array<Character>();
		emeny = new Array<Character>();
		ch.resetRewardCard();
		team.add(ch);
		brith = ((int) (Math.random()*inf.get(0)))%inf.get(0);
		boss = ((int) (Math.random()*inf.get(1)))%inf.get(1);
		brith = inf.get(2+brith);
		boss = inf.get(2+inf.get(0)+boss);
		brith = 1;
		changestage = brith;
		boss = 2;
		hashmap = new HashMap<Integer, GPXStage>();
		loc = new Vector2(200, 200);
	}
	public void startGame() {
		startgame = true;
		disappear(stage, back);
	}
	@Override
	public void dispose() {
		if(music.isPlaying())
			music.stop();
		if(deliah != null)
			deliah.dispose();
		if(fire != null)
			fire.dispose();
		game.unload(SetImg.npc+"delilah", "img");
		game.unload(SetImg.npc+"campfire", "img");
		game.unload(SetImg.hud, "img");
		game.unload(mappath, "img");
		game.unload(loadpath, "img");
		game.unload(SetImg.instance, "img");
		game.unload(SetImg.hiteffect, "img");
		game.unload(SetImg.skillicon, "img");
		game.unload(musicpath, "music");
		game.unload(SetImg.maptitle+titlepath, "img");
		game.unload(SetBase.map+"map_disappear", "sound");
		game.unload(SetInstance.reward, "sound");
		game.unload(SetInstance.itemdrop, "sound");
		game.unload(SetInstance.coin, "sound");
		back.dispose();
		hud.dispose();
		reward.dispose();
		info.dispose();
		for(int i = 0; i < hashmap.size(); i++)
			hashmap.get(i).dispose();
		hashmap.clear();
		for(Character c : emeny)
			c.dispose();
		Gdx.app.log(SetBase.TAG, "ScreenInstance release");
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	private void disappear(Stage stage1, Stage stage2) {
		stage2.clear();
		game.getSound(SetBase.map, "map_disappear.ogg").play(game.getSound_button());
		AlphaAction alpha = Actions.fadeOut(0.5f, Interpolation.linear);
		SequenceAction sequence = Actions.sequence(alpha);
		stage1.addAction(sequence);
	}
	private void appear(Stage stage) {
		AlphaAction alpha1 = Actions.fadeOut(0);
		AlphaAction alpha2 = Actions.fadeIn(1, Interpolation.linear);
		TitleActor ta = new TitleActor(game.getImg(SetImg.maptitle+titlepath, titlepath+".img"));
		ta.setPosition(hud.getWidth()/2, hud.getHeight()/2);
		hud.adactor(ta);
		Action endAction = Actions.run(new Runnable() {
			@Override
			public void run() {
				game.getLoading().setPosition(back.getWidth()-game.getLoading().getWidth(), back.getHeight()/8);
				back.addActor(game.getLoading());
				if(input == null)
					input = new InputMultiplexer();
				input.addProcessor(reward);
				hud.addListener(input);
				Gdx.input.setInputProcessor(input);
			}
		});
		SequenceAction sequence = Actions.sequence(alpha1, alpha2, endAction);
		stage.addAction(sequence);
		hud.addAction(sequence);
	}
	private void action(Image img, float r) {
		DelayAction wait1 = Actions.delay(0.1f);
		MoveToAction move = Actions.moveTo(img.getX()-(int) (r*3*Math.cos(img.getRotation()*(Math.PI/180.0f))),
				img.getY()-(int) (r*3*Math.sin(img.getRotation()*(Math.PI/180.0f))), 0.2f, Interpolation.exp10In);
		ScaleToAction scale = Actions.scaleTo(0.5f, 0.5f, 0.2f, Interpolation.exp10In);
		ParallelAction parallel = Actions.parallel(move, scale);
		SequenceAction sequence1 = Actions.sequence(wait1, parallel);
		AlphaAction alpha = Actions.fadeOut(0.1f, Interpolation.exp10In);
		DelayAction wait2 = Actions.delay(0.2f);
		SequenceAction sequence2 = Actions.sequence(wait2, alpha);
		ParallelAction paralle2 = Actions.parallel(sequence1, sequence2);
		SequenceAction sequence = Actions.sequence(paralle2);
		img.addAction(sequence);
	}
	private void showreward(StageReward stage, float delay) {
		AlphaAction hid0 = Actions.fadeOut(0);
		VisibleAction hid1 = Actions.hide();
		DelayAction wait = Actions.delay(delay);
		VisibleAction show0 = Actions.show();
		AlphaAction show1 = Actions.fadeIn(1);
		Action end = Actions.run(new Runnable() {
			@Override
			public void run() {
				game.getSound(SetInstance.reward, "reward_divide.ogg").play(game.getSound_music());
			}
		});
		ParallelAction paral = Actions.parallel(show1, end);
		SequenceAction sequence1 = Actions.sequence(hid0, hid1, wait, show0, paral);
		stage.addAction(sequence1);
	}
	private void delayshow1(Window win, float delay) {
		VisibleAction hid = Actions.hide();
		DelayAction wait = Actions.delay(delay);
		VisibleAction draw = Actions.show();
		DelayAction wait1 = Actions.delay(1);
		Action endAction = Actions.run(new ShowAction(win));
		SequenceAction sequence = Actions.sequence(hid, wait, draw, wait1, endAction);
		win.addAction(sequence);
	}
	private float delayshow2(Window win, float delay) {
		VisibleAction hid = Actions.hide();
		DelayAction wait = Actions.delay(delay);
		VisibleAction draw = Actions.show();
		DelayAction wait1 = Actions.delay(1);
		RotateByAction rotate = Actions.rotateBy(5);
		Action end = Actions.forever(rotate);
		SequenceAction sequence1 = Actions.sequence(hid, wait, draw, wait1, end);
		hid = Actions.hide();
		wait = Actions.delay(delay);
		draw = Actions.show();
		ScaleToAction scale = Actions.scaleTo(1, 1, 1, Interpolation.exp5In);
		end = Actions.run(new Runnable() {
			@Override
			public void run() {
				game.getSound(SetInstance.reward, "result_c.ogg").play(game.getSound_music());
			}
		});
		SequenceAction sequence2 = Actions.sequence(hid, wait, draw, scale, end);
		win.getChildren().get(1).addAction(sequence1);
		win.getChildren().get(2).addAction(sequence2);
		ExpNumberWindow ena = null;
		for(int i = 3; i < 12; i++) {
			hid = Actions.hide();
			wait = Actions.delay(delay);
			draw = Actions.show();
			sequence2 = Actions.sequence(hid, wait, draw);
			win.getChildren().get(i).addAction(sequence2);
			if(addexp == null)
				addexp = new Array<SlashImage>();
			if(win.getChildren().get(i) instanceof SlashImage)
				addexp.add((SlashImage) win.getChildren().get(i));
			if(win.getChildren().get(i) instanceof ExpNumberWindow)
				ena = (ExpNumberWindow) win.getChildren().get(i);
		}
		int i = 1;
		int index = 1;
		for(SlashImage si : addexp) {
			if(ena.changePer(i-1) != 0) {
				wait = Actions.delay(0.5f*index+1.5f+delay);
				end = Actions.run(new SlashAction(si, ena, i-1));
				sequence1 = Actions.sequence(wait, end);
				si.addAction(sequence1);
				index++;
			}
			i++;
		}
		return 0.5f*index+3+delay;
	}
	private void delayshow3(float delay) {
		DelayAction wait = Actions.delay(delay);
		Action end = Actions.run(new Runnable() {
			@Override
			public void run() {
				reward.window3();
				win1 = true;
			}
		});
		SequenceAction sequence = Actions.sequence(wait, end);
		reward.addAction(sequence);
	}
	private void delayshow4(Window win) {
		if(facelist == null) {
			facelist = new Array<FaceActor>();
			Array<SpriteAnimation> sa = reward.getReward();
			for(int i = 0, count = 1; i < win.getChildren().size; i++)
				if(win.getChildren().get(i) instanceof FaceActor) {
					facelist.add((FaceActor) win.getChildren().get(i));
					if(count < sa.size)
						((FaceActor) win.getChildren().get(i)).setSpriteAnimation(sa.get(count));
					count++;
				}
		}
		float imgx = facelist.get(0).getImage().getX();
		float imgy = facelist.get(0).getImage().getY();
		float labelx = facelist.get(0).getLabel().getX();
		float labely = facelist.get(0).getLabel().getY();
		for(int i = facelist.size-1; i >= 0; i--) {
			if(i > 0) {
				imgx = facelist.get(i-1).getImage().getX();
				imgy = facelist.get(i-1).getImage().getY();
				labelx = facelist.get(i-1).getLabel().getX();
				labely = facelist.get(i-1).getLabel().getY();
			}
			SequenceAction sequence = null;
			MoveToAction moveto = null;
			Action end = Actions.run(new ShowReward(facelist.get(i)));
			if(i > 0) {
				moveto = Actions.moveTo(imgx, imgy, 0.1f, Interpolation.circleOut);
				sequence = Actions.sequence(moveto);
			}else {
				moveto = Actions.moveTo(imgx+250, imgy, 0.2f, Interpolation.circleOut);
				sequence = Actions.sequence(moveto, end);
			}
			facelist.get(i).getImage().addAction(sequence);
			if(i > 0)
				facelist.get(i).getLabel().addActions(new Vector2(labelx, labely), 0.1f);
			else
				facelist.get(i).getLabel().addActions(new Vector2(labelx+250, labely), 0.2f);
		}
		facelist.removeIndex(0);
		if(facelist.size == 0) {
			win1 = false;
			win2 = true;
		}
	}
	private void delayshow5(Image img) {
		win2 = false;
		img.setOrigin(img.getWidth()/2, img.getHeight()/2);
		DelayAction wait1 = Actions.delay(0.5f);
		DelayAction wait2 = Actions.delay(1f);
		DelayAction wait3 = Actions.delay(1f);
		VisibleAction show = Actions.show();
		VisibleAction hide = Actions.hide();
		AlphaAction fadein = Actions.fadeIn(1f);
		AlphaAction fadeout = Actions.fadeOut(2f);
		RotateByAction rotate = Actions.rotateBy(20);
		ScaleByAction scale = Actions.scaleBy(0.6f, 0.6f, 0.2f);
		RepeatAction repeat1 = Actions.forever(rotate);
		RepeatAction repeat2 = Actions.forever(scale);
		ParallelAction parallel1 = Actions.parallel(repeat1, repeat2);
		Action end1 = Actions.run(new Runnable() {
			@Override
			public void run() {
				game.getSound(SetInstance.reward, "reward_glitter.ogg").play(game.getSound_music());
			}
		});
		Action end2 = Actions.run(new Runnable() {
			@Override
			public void run() {
				reward.window4(lv);
				win3 = true;
			}
		});
		SequenceAction sequence1 = Actions.sequence(fadein, wait2, end2, wait3, fadeout, hide);
		ParallelAction parallel2 = Actions.parallel(sequence1, parallel1);
		SequenceAction sequence2 = Actions.sequence(wait1, show, end1, parallel2);
		img.addAction(sequence2);
	}
	private void delayshow6(StageReward stages, float delay) {
		win3 = false;
		VisibleAction hid = Actions.hide();
		AlphaAction show = Actions.fadeOut(1);
		DelayAction wait = Actions.delay(delay);
		Action end = Actions.run(new Runnable() {
			@Override
			public void run() {
				ch.changeProperty("EXP", reward.getEXP(), false, false);
				float x = ch.getX();
				boolean right = ((int) (Math.random()*100))%2==0;
				Vector2 sizex = stage.SizeVector();
				if(x < sizex.x+150) {
					x = sizex.x+150;
					right = false;
				}else if(x > sizex.y-150) {
					x = sizex.y-150;
					right = true;
				}
				deliah = new NPC(game, SetNPC._deliah, game.getImg(SetImg.npc+"delilah", "delilah.img"), false, new Vector2(-3, 68), 17);
				deliah.setInstanceWorld(stage.getWorld(), stage.getGWorld(), x+75*(right?1:-1), ch.getY(), ver);deliah.set("", null);
				deliah.lookat(ch);stage.getGroup().addActor(deliah.getShadow());stage.getGroup().addActor(deliah);
				fire = new NPC(game, SetNPC._fire, game.getImg(SetImg.npc+"campfire", "campfire.img"), false, new Vector2(-3, 75), 0);
				fire.setInstanceWorld(stage.getWorld(), stage.getGWorld(), x+75*(right?-1:1), ch.getY(), ver);fire.set("", null);
				fire.lookat(ch);stage.getGroup().addActor(fire.getShadow());stage.getGroup().addActor(fire);
				//ch.addItem();
				pass = true;
				returntown = true;
				hud.instanceOrtown();
			}
		});
		SequenceAction sequence = Actions.sequence(wait, show, hid, end);
		stages.addAction(sequence);
	}
	private class ShowAction implements Runnable {
		private Window win = null;
		public ShowAction(Window win) {
			super();
			this.win = win;
		}
		@Override
		public void run() {
			game.getSound(SetInstance.reward, "result_count.ogg").play(game.getSound_music());
			for(Actor actor : win.getChildren()) {
				if(actor instanceof PercentActor)
					((PercentActor) actor).start();
				if(actor instanceof HitShowActor)
					((HitShowActor) actor).start();
			}
		}
	}
	private class SlashAction implements Runnable {
		private SlashImage img = null;
		private ExpNumberWindow ena = null;
		private int i = -1;
		public SlashAction(SlashImage img, ExpNumberWindow ena, int i) {
			super();
			this.img = img;
			this.ena = ena;
			this.i = i;
		}
		@Override
		public void run() {
			game.getSound(SetInstance.reward, "result_flash.ogg").play(game.getSound_music());
			img.start();
			ena.change(i);
		}
	}
	private class ShowReward implements Runnable {
		private FaceActor fa = null;
		public ShowReward(FaceActor fa) {
			super();
			this.fa = fa;
		}
		@Override
		public void run() {
			game.getSound(SetInstance.reward, "reward_icon_off.ogg").play(game.getSound_music());
			fa.showSprite();
		}
	}
	private float rankLvToPer(int ranklv) {
		float per = 0;
		switch(ranklv) {
			case 0:per = 0.2f;break;
			case 1:per = 0.15f;break;
			case 2:per = 0.11f;break;
			case 3:per = 0.09f;break;
			case 4:per = 0.07f;break;
			case 5:per = 0.05f;break;
			case 6:per = 0.03f;break;
			case 7:per = 0.02f;break;
			case 8:per = 0.01f;break;
		}
		return per;
	}
	@Override
	public void render(float delta) {
		delta = deltaTime(delta);
		if(startgame && time < 0.5f)
			time += delta;
		if(bossAllDie() && !reward.isshow()) {
			for(Character ch : emeny)
				ch.changeProperty("HP", -Integer.MAX_VALUE, false, false);
			Array<Integer> rank = new Array<Integer>();
			rank.add((int) (Math.random()*100));
			rank.add((int) (Math.random()*100));
			rank.add((int) (Math.random()*1000));
			int ranklv = (int) (Math.random()*9);
			Array<Float> rankper = new Array<Float>();
			rankper.add(rankLvToPer(ranklv));
			rankper.add(((int) (Math.random()*100))%2==0?0.05f:0);
			rankper.add(((int) (Math.random()*100))%2==0?0.05f:0);
			rankper.add(((int) (Math.random()*100))%2==0?0.2f:0);
			rankper.add(((int) (Math.random()*100))%2==0?0.2f:0);
			rankper.add(((int) (Math.random()*100))%2==0?0.1f:0);
			rankper.add(((int) (Math.random()*100))%2==0?0.1f:0);
			reward.show(rank, emeny, ranklv, rankper);
			showreward(reward, 1);
			for(Image img : stage.getActionImg())
				action(img, 200);
			delayshow1(reward.getShowActor().get(0), 3);
			float ranktime = delayshow2(reward.getShowActor().get(1), 5.5f);
			delayshow3(ranktime);
		}
		if(faceaction >= 0.2f && win1) {
			faceaction -= 0.2f;
			delayshow4(reward.getShowActor().get(1));
		}else if(win1)
			faceaction += delta;
		if(win2)
			delayshow5(reward.getRotateImg());
		if(win3)
			delayshow6(reward, 12);
		if(bossAllDie()) {
			delaytime += delta;
			if(1 != Math.min(1, Interpolation.linear.apply(delaytime/3)))
				delta = delta/10;
		}
		if(!startgame && game.isload(loadpath, "img")) {
			stage = new StageLoading(this, ver, back.getBatch(), loadpath, loadname);
			hashmap.put(0, stage);
		}
		if(game.getManager().update() && time >= 0.5f && changestage != -1) {
			changeStage();
			if(boss == changestage) {
				musicb = game.getMusic(musicpath, 1);
				musicb.setLooping(true);
				musicb.setVolume(game.getSound_music());
				musicb.play();
			}else if(music == null) {
				music = game.getMusic(musicpath, 0);
				music.setLooping(true);
				music.setVolume(game.getSound_music());
				music.play();
			}else {
				if(musicb != null)
					musicb.stop();
				music.play();
			}
			changestage = -1;
		}
		if(game.getManager().update() && time >= 0.5f && hud == null) {
			hud = new StageHUD(ver, screen, back.getBatch(), ch, game, Math.max(scalex, scaley));
			hud.inScreenInstance(this);
		}
		if(music != null && !appearstage) {
			appearstage = true;
			appear(stage);
		}
		game.show(info, delta);
		back.act(delta);
		if(stage != null) {
			stage.act(delta);
			if(!(stage instanceof StageLoading))
				hud.act(delta);
		}
		if(reward.isshow())
			reward.act(delta);
		info.act(delta);
		back.draw();
		if(stage != null) {
			stage.draw();
			if(!(stage instanceof StageLoading))
				hud.draw();
		}
		if(reward.isshow())
			reward.draw();
		info.draw();
		if(returntown)
			returntime += delta;
		if(returntime >= 30 && returntown) {
			returntown = false;
			returnTown();
		}
	}
	public boolean bossAllDie() {
		boolean c = true;
		if(emeny.size == 0)
			return false;
		int n = 0;
		for(Character ch : emeny)
			if(ch.getEmenyType() == SetCharProperty.boss)
				if(ch.getProperty("HP") > 0) {
					c = false;
					break;
				}else
					n++;
		if(c && n < bosscount)
			c = false;
		return c;
	}
	public String getMappath() {
		return mappath;
	}
	public void changeStage(int stage, DoorActor dor) {
		changestage = stage;
		if(boss == changestage)
			if(music != null)
				music.stop();
		this.dor = dor;
	}
	private void changeStage() {
	//	if(map.equals(SetInstance.longrenzhita))
			mappref = "draconian_tower_";
		if(hashmap.get(changestage) == null) {
			if(dor == null)
				stage = new StageInstance(this, mappref+changestage, ver, loc, back.getBatch(), Math.max(scalex, scaley),team, emeny, lv);
			else
				stage = new StageInstance(this, mappref+changestage, ver, dor.toDoor(), back.getBatch(), Math.max(scalex, scaley),team, emeny, lv);
			hashmap.put(changestage, stage);
		}else {
			stage = hashmap.get(changestage);
			stage.resetLoc(dor.toDoor());
		}
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
			if(!(stage instanceof StageLoading)) {
				hud.resetScale(Math.max(scalex, scaley), screen);
				hud.getViewport().update(width, height, false);
			}
		}
		if(reward.isshow())
			reward.getViewport().update(width, height, false);
		info.getViewport().update(width, height, false);
	}
	@Override
	public void resume() {
	}
	@Override
	public void show() {
		setMappath();
		back = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y));
		info = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch());
		reward = new StageReward(new ScalingViewport(Scaling.fit, ver.x, ver.y), back.getBatch(), game);
		game.load(SetImg.npc+"delilah", "img");
		game.load(SetImg.npc+"campfire", "img");
		game.load(loadpath, "img");
		game.load(SetBase.map+"map_disappear", "sound");
		game.load(SetImg.maptitle+titlepath, "img");
		game.load(SetImg.hud, "img");
		game.load(mappath, "img");
		game.load(SetImg.instance, "img");
		game.load(SetImg.hiteffect, "img");
		game.load(SetImg.skillicon, "img");
		game.load(SetInstance.reward, "sound");
		game.load(SetInstance.itemdrop, "sound");
		game.load(SetInstance.coin, "sound");
		game.load(musicpath, "music");
		ch.enterInstance();
		game.getLoading().setPosition(back.getWidth()-game.getLoading().getWidth(), back.getHeight()/8);
		back.addActor(game.getLoading());
	}
	private void setMappath() {
		if(map.equals(SetInstance.lorien) || map.equals(SetInstance.loriendeep) ||
				map.equals(SetInstance.jixieniu) || map.equals(SetInstance.chongwang)) {
			mappath = SetImg.act2;
			loadname = "lorien";
			loadpath = SetImg.mapload+loadname;
			if(map.equals(SetInstance.lorien)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "lorien";
			}else if(map.equals(SetInstance.loriendeep)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "lorieninside";
			}else if(map.equals(SetInstance.jixieniu)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "vilmarkempirelabor";
			}else if(map.equals(SetInstance.chongwang)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "screamingcave";
			}
		}else if(map.equals(SetInstance.milin) || map.equals(SetInstance.milindeep) ||
				map.equals(SetInstance.leiming) || map.equals(SetInstance.leimingpoison) ||
				map.equals(SetInstance.milinice) || map.equals(SetInstance.gelaka) ||
				map.equals(SetInstance.gelakafire) || map.equals(SetInstance.leimingdark)) {
			mappath = SetImg.act2;
			loadname = "lorien";
			loadpath = SetImg.mapload+loadname;
			if(map.equals(SetInstance.milin)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "mirkwood";
			}else if(map.equals(SetInstance.milindeep)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "mirkwooddeep";
			}else if(map.equals(SetInstance.leiming)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "sunderland";
			}else if(map.equals(SetInstance.leimingpoison)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "sunderlandpoison";
			}else if(map.equals(SetInstance.milinice)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "mirkwoodfrost";
			}else if(map.equals(SetInstance.gelaka)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "grakkarak";
			}else if(map.equals(SetInstance.gelakafire)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "grakkarakburning";
			}else if(map.equals(SetInstance.leimingdark)) {
				musicpath = SetInstance.draconian_tower;
				titlepath = "sunderlanddark";
			}
		}
	}
	public GuPoubleXGame getGame() {
		return game;
	}
	public void showHPline(Character ch) {
		hud.showHPline(ch, lv);
	}
	public void returnTown() {
		stage.destoryWorld();
		game.change("town", game.getPart(ch.getTownPart()), 0, null);
	}
	public void choseMap() {
		stage.destoryWorld();
		game.change("town", "map", 0, null);
	}
}