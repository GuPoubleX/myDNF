package dnf.town;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetTown;

public class MapWindow extends Window {
	private StageMap map = null;
	private GuPoubleXGame gg = null;
	private Window minmap = null;
	private Window maxmap = null;
	private MapWindowmin min = null;
	private MapWindowmax max = null;
	private String direct = null;
	private float scale = 1.4f;
	private boolean checked = false;
	private int selected = 0; 
	private Array<MapWindow> listwin = null;
	public void reset() {
		checked = false;
		minmap.setVisible(true);
		maxmap.setVisible(false);
	}
	public void addLv() {
		max.getLevel().addLv();
	}
	public void minusLv() {
		max.getLevel().minusLv();
	}
	public void select() {
		gg.getSound(SetBase.map, "map_mouse.ogg").play(gg.getSound_button());
		for(MapWindow win : listwin)
			win.reset();
		checked = true;
		map.setselect(selected);
		minmap.setVisible(false);
		maxmap.setVisible(true);
	}
	public void addListener() {
		min.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(checked)
					return;
				if(map.isenter())
					return;
				gg.getSound(SetBase.map, "map_mouse.ogg").play(gg.getSound_button());
				for(MapWindow win : listwin)
					win.reset();
				checked = true;
				map.setselect(selected);
				minmap.setVisible(false);
				maxmap.setVisible(true);
			}
		});
		max.addListener();
		map.btl.add(min);
		map.btl.add(max);
		map.btl.add(minmap);
		map.btl.add(maxmap);
	}
	public MapWindow(StageMap map, WindowStyle style, String direct, Array<MapWindow> list,
			 int id, boolean ac, boolean d, GuPoubleXGame game, ScreenTown town, String mapstr, String bg, String bgname,
			 String name, int lv, int select, Array<Integer> info) {
		super("", style);
		this.map = map;
		this.direct = direct;
		this.listwin = list;
		this.gg = game;
		this.selected = select;
		Sprite sp = new Sprite(gg.getImg(mapstr, "selectdungeon02.img").getIndex(1));
		boolean down = (direct.equals(SetTown.leftdown)||direct.equals(SetTown.rightdown)?true:false);
		max = new MapWindowmax(style, id, ac, d, mapstr, bg, bgname, game, name, lv, map, info);
		min = new MapWindowmin(style, mapstr, bg, bgname, game, id, ac, d);
		if(direct.equals(SetTown.rightup) || direct.equals(SetTown.leftup)) {
			minmap = new Window("", style);
			minmap.setSize(150, 78+(ac||d?13:0));
			maxmap = new Window("", style);
			maxmap.setSize(150*scale, 78*scale+(ac||d?26:0));
			setSize(maxmap.getWidth(), maxmap.getHeight());
			sp.flip(direct.equals(SetTown.rightup)?true:false, true);
			Image image = new Image(sp);
			minmap.addActor(image);
			image = new Image(sp);
			image.setScale(scale);
			maxmap.addActor(image);
			min.setPosition(direct.equals(SetTown.rightup)?minmap.getWidth()-min.getWidth()-1:0, minmap.getHeight()-min.getHeight()-1);
			minmap.addActor(min);
			max.setPosition(direct.equals(SetTown.rightup)?maxmap.getWidth()-max.getWidth()-1:0, maxmap.getHeight()-max.getHeight()-1);
			maxmap.addActor(max);
			if(direct.equals(SetTown.rightup))
				minmap.setPosition(0, 0);
			else
				minmap.setPosition(getWidth()-minmap.getWidth(), 0);
			addActor(minmap);
			addActor(maxmap);
			maxmap.setVisible(false);
		}else {
			minmap = new Window("", style);
			minmap.setSize(150, 78);
			maxmap = new Window("", style);
			maxmap.setSize(150*scale, 78*scale);
			setSize(maxmap.getWidth(), maxmap.getHeight());
			sp.flip(direct.equals(SetTown.rightdown)?true:false, false);
			Image image = new Image(sp);
			minmap.addActor(image);
			image = new Image(sp);
			image.setScale(scale);
			if(down)
				image.setPosition(direct.equals(SetTown.rightdown)?-12:12, 11);
			maxmap.addActor(image);
			min.setPosition(direct.equals(SetTown.rightdown)?minmap.getWidth()-min.getWidth()-1:0, 1);
			minmap.addActor(min);
			max.setPosition(direct.equals(SetTown.rightdown)?maxmap.getWidth()-max.getWidth()-12:12, 1);
			maxmap.addActor(max);
			if(direct.equals(SetTown.rightdown))
				minmap.setPosition(0, getHeight()-minmap.getHeight());
			else
				minmap.setPosition(getWidth()-minmap.getWidth(), getHeight()-minmap.getHeight());
			addActor(minmap);
			addActor(maxmap);
			maxmap.setVisible(false);
		}
	}
	@Override
	public void setPosition(float x, float y) {
		if(direct.equals("rightup"))
			;
		else if(direct.equals("rightdown"))
			y -= getHeight();
		else if(direct.equals("leftup"))
			x -= getWidth();
		else {
			x -= getWidth();
			y -= getHeight();
		}
		super.setPosition(x, y);
	}
	public String getName() {
		return max.getName();
	}
	public int getLv() {
		return max.getLv();
	}
	public Array<Integer> getInfo() {
		return max.getInfo();
	}
}