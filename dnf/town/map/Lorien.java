package dnf.town.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetInstance;

public class Lorien extends MapList {
	public Lorien(GuPoubleXGame game) {
		super(game);
		boolean ac = false;
		boolean dm = false;
		boolean sp = false;
		String rightup = "rightup";
		String rightdown = "rightdown";
		String leftup = "leftup";
		String leftdown = "leftdown";
		Array<Integer> brith = new Array<Integer>();
		Array<Integer> boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.lorien, game, 1, new Vector2(490, 320), ac, dm, rightup, sp, 0, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(5);
		add(SetInstance.loriendeep, game, 2, new Vector2(380, 198), ac, dm, leftup, sp, 1, 1, 1, brith, boss);
		ac = true;
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(6);
		add(SetInstance.jixieniu, game, 47, new Vector2(542, 424), ac, dm, leftdown, sp, 2, 1, 1, brith, boss);
		brith = new Array<Integer>();
		boss = new Array<Integer>();
		brith.add(1);
		boss.add(6);
		add(SetInstance.chongwang, game, 53, new Vector2(197, 278), ac, dm, rightdown, sp, 3, 1, 1, brith, boss);
	}
}