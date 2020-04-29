package dnf.character;

import java.util.HashMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.part.Dress;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetDress;

public class Surface {
	private String mainpath = null;
	private GuPoubleXGame game = null;
	private HashMap<Integer, Dress> list = null;
	private Array<Vector2> pose = null;
	private Array<RoleAnimation> an = null;
	public Surface(SpriteTexture re) {
		super();
		an = new Array<RoleAnimation>();
		an.add(new RoleAnimation(1, new SpriteTexture[]{re}));
	}
	public Surface(SpriteTexture re[]) {
		super();
		an = new Array<RoleAnimation>();
		an.add(new RoleAnimation(2.0f/re.length, re));
	}
	public Surface(GuPoubleXGame game, String mainpath, Array<Vector2> pose) {
		super();
		this.game = game;
		this.mainpath = mainpath;
		this.pose = pose;
		list = new HashMap<Integer, Dress>();
	}
	public HashMap<Integer, Dress> getDress() {
		return list;
	}
	public Array<RoleAnimation> getAn(int pose) {
		if(list == null)
			return an;
		Array<RoleAnimation> ans = new Array<RoleAnimation>();
		for(Dress dress : list.values())
			dress.getPose(ans, pose, false, false);
		return ans;
	}
	public Array<RoleAnimation> getAn(int pose, boolean skin, boolean weapon) {
		if(list == null)
			return an;
		Array<RoleAnimation> ans = new Array<RoleAnimation>();
		for(Dress dress : list.values())
			dress.getPose(ans, pose, skin, weapon);
		return ans;
	}
	public void changeDress(int key, String path) {
		list.put(transKey(key), new Dress(game, mainpath, path, pose, key));
	}
	//show sort
	private int transKey(int key) {
		if(key == SetDress.hair)
			return 7;
		if(key == SetDress.hat)
			return 8;
		if(key == SetDress.face)
			return 6;
		if(key == SetDress.chest)
			return 5;
		if(key == SetDress.coat)
			return 3;
		if(key == SetDress.skin)
			return 0;
		if(key == SetDress.belt)
			return 4;
		if(key == SetDress.pant)
			return 2;
		if(key == SetDress.shoe)
			return 1;
		if(key == SetDress.weapon)
			return 9;
		else
			return -1;
	}
}