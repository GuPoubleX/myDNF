package dnf.town.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetInstance;

public class Granfloris extends MapList {
	public Granfloris(GuPoubleXGame game) {
		super(game);
		boolean ac = false;
		boolean dm = false;
		boolean sp = false;
		String rightup = "rightup";
		String rightdown = "rightdown";
		String leftup = "leftup";
		Array<Integer> brith = new Array<Integer>();
		Array<Integer> boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.milin, game, 3, new Vector2(193, 373), ac, dm, rightdown, sp, 0, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.milindeep, game, 4, new Vector2(305, 304), ac, dm, leftup, sp, 1, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.leiming, game, 6, new Vector2(433, 399), ac, dm, leftup, sp, 2, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.leimingpoison, game, 8, new Vector2(506, 352), ac, dm, rightup, sp, 3, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.milinice, game, 9, new Vector2(534, 264), ac, dm, rightup, sp, 4, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.gelaka, game, 11, new Vector2(466, 195), ac, dm, rightup, sp, 5, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.gelakafire, game, 13, new Vector2(358, 164), ac, dm, leftup, sp, 6, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.leimingdark, game, 16, new Vector2(505, 475), ac, dm, rightup, sp, 7, 1, 1, brith, boss);
	}
}