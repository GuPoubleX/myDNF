package dnf.town.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;

public class Map {
	private String name = null;
	private int lv = 0;
	private int maplv = 0;
	private Vector2 point = null;
	private boolean ac = false;
	private boolean dm = false;
	private String direct = null;
	private boolean sp = false;
	private int iconID = 0;
	private float scale = 0.8f;
	private int birthcount = 1;
	private int bosscount = 1;
	private Array<Integer> brith = null;
	private Array<Integer> boss = null;
	public Map(String name, GuPoubleXGame game, String pref, int maplv, Vector2 point, boolean ac, boolean dm,
			String direct, boolean sp, int iconID, int birthcount, int bosscount, Array<Integer> brith, Array<Integer> boss) {
		super();
		this.name = name;
		this.lv = game.getPref(pref).getInteger(name);
		this.maplv = maplv;
		this.point = new Vector2(point.x*scale, (600-point.y)*scale);
		this.ac = ac;
		this.dm = dm;
		this.direct = direct;
		this.sp = sp;
		this.iconID = iconID;
		this.birthcount = birthcount;
		this.bosscount = bosscount;
		this.brith = brith;
		this.boss = boss;
	}
	public Array<Integer> getBrith(){
		return brith;
	}
	public Array<Integer> getBoss() {
		return boss;
	}
	public String getDirect() {
		return direct;
	}
	public String getName() {
		return name;
	}
	public int getLv() {
		return lv;
	}
	public int getMaplv() {
		return maplv;
	}
	public int getID() {
		return iconID;
	}
	public Vector2 getPoint() {
		return point;
	}
	public boolean isAc() {
		return ac;
	}
	public boolean isDm() {
		return dm;
	}
	public boolean isSp() {
		return sp;
	}
	public int getBirthcount() {
		return birthcount;
	}
	public int getBosscount() {
		return bosscount;
	}
}