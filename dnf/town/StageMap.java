package dnf.town;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.AnActor;
import dnf.author.GPXStage;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetTown;
import dnf.town.map.Granfloris;
import dnf.town.map.Lorien;
import dnf.town.map.Map;
import dnf.town.map.MapList;

public class StageMap extends GPXStage {
	private Stage anima = null;
	private float scaleX = 1;
	private float scaleY = 1;
	private Window window = null;
	private ScreenTown town = null;
	private GuPoubleXGame game = null;
	private Character c = null;
	private Array<MapWindow> map = null;
	private ImageButton back = null;
	private ImageButton enter = null;
	private ImageButton hell = null;
	private int selectmap = -1;
	private InputMultiplexer input = null;
	private float time = 0;
	private boolean addlistener = false;
	private AnActor an = null;
	private boolean addcount = false;
	protected boolean load = false;
	protected Array<Actor> btl = null;
	private boolean unload = false;
	public StageMap(ScreenTown town, Vector2 ver, Batch batch, Character c, Array<Character> ch, GuPoubleXGame game, InputMultiplexer input, String mapstr, String mapbg, String bgname) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		anima = new Stage(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		btl = new Array<Actor>();
		this.game = game;
		this.town = town;
		this.c = c;
		this.input = input;
		scale = 0.8f;
		World world = new World(new Vector2(0.0f, 0.0f), true);
		for(Character ct : ch) {
			ct.set("stay", null);
			ct.setTownWorld(world, 5000, 5000, ver);
			addActor(ct);
		}
		map = new Array<MapWindow>();
		window = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		window.setSize(SetBase.dir_width, SetBase.dir_height);
		scaleX = SetBase.dir_width/ver.x;
		scaleY = SetBase.dir_height/ver.y;
		Image image = new Image(game.getImg(mapstr+"back/"+bgname, bgname+".img").getIndex(0));
		image.setScale(1/Math.min(scaleX, scaleY));
		addActor(image);
		image = new Image(game.getImg(mapstr+"back/"+bgname, bgname+".img").getIndex(0));
		image.setScale(scale);
		window.addActor(image);
		window.setPosition(getWidth()/2-window.getWidth()/2, getHeight()/2-window.getHeight()/2);
		addActor(window);
		image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(7));
		image.setScale(scale);
		window.addActor(image);
		image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(3));
		image.setScale(scale);
		image.setPosition(window.getWidth()/2-image.getWidth()/2*image.getScaleX(), window.getHeight()-image.getHeight()*image.getScaleY());
		window.addActor(image);
		float y = image.getY();
	//	image = new Image(chs.findRegion("49"));
	//	image.setScale(scale);
	//	image.setPosition(window.getWidth()-image.getWidth()*image.getScaleX(), y-image.getHeight());
	//	window.addActor(image);
		image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(4));
		image.setScale(scale);
		image.setPosition(window.getWidth()/2-image.getWidth()/2*image.getScaleX(), 0);
		window.addActor(image);
		back = new ImageButton(new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(9)),
				new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(10)));
		enter = new ImageButton(new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(9)),
				new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(10)),
				new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(10)));
		hell = new ImageButton(new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(9)),
				new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(10)),
				new TextureRegionDrawable(game.getImg(mapstr, "selectdungeon02.img").getIndex(10)));
		back.setPosition(window.getWidth()-back.getWidth()-5, window.getHeight()-y);
		enter.setPosition(back.getX()-enter.getWidth()-5, back.getY());
		hell.setPosition(enter.getX()-hell.getWidth()-5, back.getY());
		window.addActor(back);
		window.addActor(enter);
		window.addActor(hell);
		back.setVisible(false);
		enter.setVisible(false);
		hell.setVisible(false);
		setMap(mapstr, mapbg, bgname);
		Sprite rg[] = new Sprite[10];
		for(int i = 0; i < rg.length; i++)
			rg[i] = game.getImg(mapstr, "selectdungeon.img").getIndex(9-i);
		an = new AnActor(1, rg);
		an.setPosition(window.getWidth()-an.getWidth()-10, window.getHeight()-an.getHeight()-10);
		window.addActor(an);
		an.setVisible(false);
		an.setSound(game.getSound(SetBase.map, "map_count.ogg"), game.getSound_button());
		image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(8));
		image.setScale(scale);
		image.setPosition(anima.getWidth()/2-image.getWidth()/2*scale, anima.getHeight()/2-image.getHeight()/2*scale);
		anima.addActor(image);
		alphaAction(image);
	}
	private void addListener() {
		for(MapWindow win : map) {
			win.addListener();
			btl.add(win);
		}
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(enterinstance)
					return true;
				if(load)
					return true;
				Preferences pref = game.getPref(SetBase.saveHud);
				int up = pref.getInteger("UP");
				int down = pref.getInteger("DOWN");
				int right = pref.getInteger("RIGHT");
				int left = pref.getInteger("LEFT");
				int cancel = pref.getInteger("ESC");
				if(keycode == up || keycode == Keys.UP) {
					if(selectmap == -1)
						selectmap = 0;
					else {
						if(selectmap+1 > map.size-1)
							selectmap = 0;
						else
							selectmap++;
					}
					map.get(selectmap).select();
				}else if(keycode == down || keycode == Keys.DOWN) {
					if(selectmap == -1)
						selectmap = 0;
					else {
						if(selectmap-1 < 0)
							selectmap = map.size-1;
						else
							selectmap--;
					}
					map.get(selectmap).select();
				}else if(keycode == right || keycode == Keys.RIGHT) {
					if(selectmap != -1)
						map.get(selectmap).addLv();
				}else if(keycode == left || keycode == Keys.LEFT) {
					if(selectmap != -1)
						map.get(selectmap).minusLv();
				}else if(keycode == cancel || keycode == Keys.ESCAPE) {
					clearLs();
					game.getSound(SetBase.map, "map_disappear.ogg").play(game.getSound_button());
					town.change(game.getPart(c.getTownPart()));
				}else if(keycode == Keys.SPACE) {
					if(selectmap == -1)
						return true;
					clearLs();
					game.getSound(SetBase.map, "map_mouse.ogg").play(game.getSound_button());
					choseInstance();
				}
				return true;
			}
		});
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!enterinstance) {
					clearLs();
					game.getSound(SetBase.map, "map_disappear.ogg").play(game.getSound_button());
					town.change(game.getPart(c.getTownPart()));
				}
			}
		});
		enter.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(selectmap == -1)
					return;
				if(!enterinstance) {
					clearLs();
					entered();
					game.getSound(SetBase.map, "map_appear.ogg").play(game.getSound_button());
					choseInstance();
				}
			}
		});
		hell.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(selectmap == -1)
					return;
				if(!enterinstance)
					game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
			}
		});
		btl.add(back);
		btl.add(enter);
		btl.add(hell);
		input.clear();
		input.addProcessor(this);
		Gdx.input.setInputProcessor(input);
	}
	private boolean enterinstance = false;
	public boolean isenter() {
		return enterinstance;
	}
	public void entered() {
		enterinstance = true;
	}
	private void alphaAction(Image image) {
		AlphaAction alpha1 = Actions.alpha(1, 1);
		AlphaAction alpha2 = Actions.alpha(0, 0.5f);
		Action end = Actions.run(new Runnable() {
			@Override
			public void run() {
				game.getSound(SetBase.map, "map_appear.ogg").play(game.getSound_button());
			}
		});
		SequenceAction sequence = Actions.sequence(alpha1, end, alpha2);
		image.addAction(sequence);
	}
	private void setMap(String mapstr, String mapbg, String bgname) {
		int townpart = c.getTownPart();
		MapList maplist = null;
		if(townpart == SetTown.lorien)
			maplist = new Lorien(game);
		else if(townpart == SetTown.granfloris1 || townpart == SetTown.granfloris2)
			maplist = new Granfloris(game);
		else
			;
		if(maplist != null) {
			int select = 0;
			for(Map m : maplist.getMap()) {
			//	if(m.getMaplv() > c.getLevel())
			//		continue;
				Array<Integer> info = new Array<Integer>();
				info.add(m.getBirthcount());
				info.add(m.getBosscount());
				for(Integer i : m.getBrith())
					info.add(i);
				for(Integer i : m.getBoss())
					info.add(i);
				MapWindow tmp = new MapWindow(this, window.getStyle(), m.getDirect(), map,
						m.getID(), m.isAc(), m.isDm(), game, town, mapstr, mapbg, bgname, m.getName(), m.getLv(), select, info);
				select++;
				Image image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(m.isAc()||m.isDm()?6:5));
				image.setPosition(m.getPoint().x-image.getWidth()/2, m.getPoint().y-image.getHeight()/2);
				window.addActor(image);
				tmp.setPosition(m.getPoint().x, m.getPoint().y);
				window.addActor(tmp);
				map.add(tmp);
			}
		}
	}
	protected void clearLs() {
		if(!unload) {
			unload = true;
			load = true;
			for(Actor ac : btl) {
				ac.setVisible(false);
				ac.clearListeners();
			}
		}
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		anima.act(delta);
		time += delta;
		if(addlistener) {
			if(!back.isVisible())
				back.setVisible(true);
			if(!enter.isVisible())
				enter.setVisible(true);
			if(!hell.isVisible())
				hell.setVisible(true);
		}
		if(time >= 2 && !addlistener) {
			addlistener = true;
			addListener();
		}
		if(time >= 20 && !addcount) {
			addcount = true;
			an.setVisible(true);
		}
		if(time >= 30) {
			game.getSound(SetBase.map, "map_disappear.ogg").play(game.getSound_button());
			town.change(game.getPart(c.getTownPart()));
		}
		if(load)
			if(!unload) {
				unload = true;
				for(Actor ac : btl)
					ac.clearListeners();
			}
	}
	@Override
	public void draw() {
		if(selectmap != -1) {
			enter.setChecked(false);
			hell.setChecked(false);
		}else {
			enter.setChecked(true);
			hell.setChecked(true);
		}
		super.draw();
		anima.draw();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	public void setselect(int select) {
		this.selectmap = select;
	}
	public void choseInstance() {
		c.enterInstance(town.getcharNewLoction());
		town.change(map.get(selectmap).getName(), map.get(selectmap).getLv(), map.get(selectmap).getInfo());
	}
}