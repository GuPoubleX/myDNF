package dnf.character.part;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.author.RoleAnimation;
import dnf.author.StringHex;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetDress;

public class Dress implements Disposable {
	private GuPoubleXGame game = null;
	private String mainpath = null;
	private String path = null;
	private String path2 = null;
	private String path3 = null;
	private String path4 = null;
	private Array<Vector2> pose = null;
	private boolean load = false;
	private Array<RoleAnimation> poselist = null;
	private Array<RoleAnimation> poselist2 = null;
	private Array<RoleAnimation> poselist3 = null;
	private Array<RoleAnimation> poselist4 = null;
	public Dress(GuPoubleXGame game, String mainpath, String path, Array<Vector2> pose, int key) {
		super();
		this.game = game;
		this.mainpath = mainpath;
		this.path = path;
		this.pose = pose;
		if(!path.equals(""))
			game.load(mainpath+path, "img");
		StringHex sh = new StringHex();
		boolean has5 = false;
		if(key == SetDress.pant || key == SetDress.shoe) {
			has5 = false;
			int l = sh.StrtoHex(String.copyValueOf(path.toCharArray(), path.length()-5, 5));
			path2 = String.copyValueOf(path.toCharArray(), 0, path.length()-5)+sh.HextoStr(l+1, has5);
		}
		if(key == SetDress.weapon) {
			has5 = false;
			int l = sh.StrtoHex(String.copyValueOf(path.toCharArray(), path.length()-5, 5));
			path2 = String.copyValueOf(path.toCharArray(), 0, path.length()-5)+sh.HextoStr(l+1, has5);
		}
		if(path2 != null)
			game.load(mainpath+path2, "img");
		if(path3 != null)
			game.load(mainpath+path3, "img");
		if(path4 != null)
			game.load(mainpath+path4, "img");
	}
	@Override
	public void dispose() {
		if(load)
			return;
		if(!path.equals(""))
			game.unload(mainpath+path, "img");
		if(path2 != null)
			game.unload(mainpath+path2, "img");
		if(path3 != null)
			game.unload(mainpath+path3, "img");
		if(path4 != null)
			game.unload(mainpath+path4, "img");
	}
	public void setAnimation() {
		if(load)
			return;
		if(path.equals(""))
			return;
		load = true;
		poselist = new Array<RoleAnimation>();
		setAn(path, poselist);
		if(path2 != null) {
			poselist2 = new Array<RoleAnimation>();
			setAn(path2, poselist2);
		}
		if(path3 != null) {
			poselist3 = new Array<RoleAnimation>();
			setAn(path3, poselist3);
		}
		if(path4 != null) {
			poselist4 = new Array<RoleAnimation>();
			setAn(path4, poselist4);
		}
	}
	private void setAn(String path, Array<RoleAnimation> poselist) {
		Img img = game.getImg(mainpath+path, path+".img");
		for(Vector2 ver : pose) {
			int start = (int) ver.x;
			int length = (int) ver.y;
			int star = start;
			SpriteTexture re[] = new SpriteTexture[length];
			for(int i = start; i < start+length; i++)
				re[i-star] = img.getIndexST(i);
			poselist.add(new RoleAnimation(1.0f/re.length, re));
		}
	}
	public void getPose(Array<RoleAnimation> list, int pose, boolean skin, boolean weapon) {
		if(!skin && !weapon) // all
			;
		else {
			if(skin) {
				if(!(skin && path.indexOf("body") != -1)) // only skin
					return;
			}else
				if(weapon && path.indexOf("weapon") != -1) // none weapon
					return;
		}
		RoleAnimation an = getPose(pose, poselist);
		if(an != null)
			list.add(an);
		an = getPose(pose, poselist2);
		if(an != null)
			list.add(an);
		an = getPose(pose, poselist3);
		if(an != null)
			list.add(an);
		an = getPose(pose, poselist4);
		if(an != null)
			list.add(an);
	}
	private RoleAnimation getPose(int pose, Array<RoleAnimation> poselist) {
		if(poselist == null)
			return null;
		return poselist.get(pose);
	}
}