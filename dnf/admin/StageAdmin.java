package dnf.admin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.ScrollWindow;
import dnf.author.GPXStage;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public abstract class StageAdmin extends GPXStage {
	protected StageAdmin context = null;
	protected boolean loading = false;
	protected ScreenAdmin main = null;
	protected Image ground = null;
	protected Window window = null;
	protected TextField username = null;
	protected TextField password = null;
	protected ScrollPane scroll = null;
	protected ImageButton login = null;
	protected ImageButton exit = null;
	protected ImageButton server = null;
	protected ImageButton enter = null;
	protected ImageButton delete = null;
	protected ImageButton create = null;
	protected ImageButton createex = null;
	protected Array<Integer> chose = null;
	protected Array<Character> list = null;
	protected Character chosetodel = null;
	protected Array<SelectWindow> swindow = null;
	protected ScrollWindow scrollw = null;
	protected Array<ChannelButton> ca = null;
	private boolean btc = false;
	protected Array<Actor> btl = null;
	private String str = null;
	private int groundid = 0;
	public StageAdmin(ScreenAdmin main, Vector2 ver, Batch batch, int groundid, float scale, String str) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		btl = new Array<Actor>();
		context = this;
		this.main = main;
		this.scale = scale;
		this.str = str;
		this.groundid = groundid;
		if(main.getGame().getList().size != 0)
			list = main.getGame().getList();
		if(this instanceof Select)
			chosetodel = new Character(main.getGame());
		window = new Window("", new Window.WindowStyle(main.getGame().getLazy(), Color.WHITE, null));
		window.setSize(SetBase.dir_width, SetBase.dir_height);
		window.setPosition(getWidth()/2-window.getWidth()/2, getHeight()/2-window.getHeight()/2);
		addActor(window);
		setGround();
		setStage();
		setListener();
		InputMultiplexer input = new InputMultiplexer();
		input.addProcessor(new GestureDetector(new StageAdminGestureListener()));
		input.addProcessor(this);
		Gdx.input.setInputProcessor(input);
	}
	public void setGround() {
		ground = new Image(main.getGame().getImg(SetImg.admin, str).getIndex(groundid));
		float s = getClass().equals(Server.class)?0.2f:0.1f;
		float scl = window.getWidth()/ground.getWidth()+s;
		ground.setScale(scl);
		ground.setPosition(window.getWidth()/2-ground.getWidth()*ground.getScaleX()/2,
				window.getHeight()/2-ground.getHeight()*ground.getScaleY()/2);
		window.addActor(ground);
	}
	@Override
	public void draw() {
		if(enter != null) {
			if(getClass() == Channel.class) {
				if(main.getGame().getChannel() == 0)
					enter.setChecked(true);
				else
					enter.setChecked(false);
			}else if(getClass() == Select.class) {
				if(chosetodel.getName().equals(""))
					enter.setChecked(true);
				else
					enter.setChecked(false);
			}
		}
		if(delete != null) {
			if(chosetodel.getName().equals(""))
				delete.setChecked(true);
			else
				delete.setChecked(false);
		}
		if(create != null) {
			if(main.getGame().getList().size == 12)
				create.setChecked(true);
			else
				create.setChecked(false);
		}
		super.draw();
	}
	protected void clearLs() {
		if(!btc) {
			btc = true;
			loading = true;
			for(Actor bt : btl)
				bt.clearListeners();
		}
	}
	public Image getGround() {
		return ground;
	}
	protected abstract void setStage();
	protected abstract void setListener();
	private class StageAdminGestureListener implements GestureListener {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
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
			if(scrollw != null)
				return scrollw.scroll(x, y, deltaX, deltaY, scale);
			else
				return false;
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
	public void setDelete(Character ch) {
		chosetodel = ch;
	}
	public void delcharacter() {
		for(SelectWindow sw : swindow) {
			sw.remove();
			if(sw.getCharacter() == null)
				continue;
			if(sw.getCharacter() == chosetodel) {
				SelectWindow swnew = new SelectWindow(main.getGame(), swindow, context);
				swnew.unlock();
				swindow.removeValue(sw, true);
				swindow.add(swnew);
				Preferences pref = main.getGame().getPref(SetBase.save_prefix+main.getGame().getAccount()+"."+main.getGame().getServer());
				int id = pref.getInteger(chosetodel.getName());;
				pref.remove(chosetodel.getName());
				pref.flush();
				pref = main.getGame().getPref(main.getGame().getAccount()+"."+id);
				pref.clear();
				pref.flush();
				main.getGame().delPref(main.getGame().getAccount()+"."+id);
				main.getGame().getList().removeValue(chosetodel, true);
				chosetodel.dispose();
				main.getGame().setChose(null);
				chosetodel = new Character(main.getGame());
			}
		}
		int i = 0;
		for(SelectWindow sw : swindow) {
			if(i/6 == 0)
				sw.setPosition(window.getWidth()/6*(i%6)+window.getWidth()/12-sw.getWidth()/2, window.getHeight()*3/4-sw.getHeight()/2-31);
			else
				sw.setPosition(window.getWidth()/6*(i%6)+window.getWidth()/12-sw.getWidth()/2, window.getHeight()/4-sw.getHeight()/2+32);
			window.addActor(sw);
			i++;
		}
	}
}