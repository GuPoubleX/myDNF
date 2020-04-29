package dnf.town.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;

public class MapList {
	protected Array<Map> list = null;
	protected GuPoubleXGame game = null;
	public MapList(GuPoubleXGame game) {
		super();
		this.game = game;
		list = new Array<Map>();
	}
	public void add(String name, GuPoubleXGame game, int maplv, Vector2 point, boolean ac, boolean dm,
			String direct, boolean sp, int id, int brithcount, int bosscount, Array<Integer> brith, Array<Integer> boss) {
		String pref = SetBase.save_prefix+game.getAccount()+"."+game.getServer();
		Map map = new Map(name, game, pref, maplv, point, ac, dm, direct, sp, id, brithcount, bosscount, brith, boss);
		list.add(map);
	}
	public Array<Map> getMap() {
		return list;
	}
}