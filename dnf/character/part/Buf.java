package dnf.character.part;

import com.badlogic.gdx.graphics.g2d.Batch;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class Buf {
	private BuffActor actor = null;
	private float posetime = 0;
	private int max = 1;
	private int current = 0;
	private boolean startup = false;
	private float during = 0;
	private float firstduring = 0;
	protected float hpmax = 0;
	protected float mpmax = 0;
	protected float str = 0;
	protected float inT = 0;
	protected float vit = 0;
	protected float men = 0;
	protected float atkphy = 0;
	protected float atkmig = 0;
	protected float defphy = 0;
	protected float defmig = 0;
	protected float crt = 0;
	protected float crd = 0;
	protected float sphy = 0;
	protected float smig = 0;
	protected float smov = 0;
	private RoleAnimation an = null;
	private boolean open = false;
	public Buf(float during, int max, float posetime, BuffActor actor) {
		super();
		this.actor = actor;
		this.max = max;
		this.during = during;
		if(during == -1)
			open = true;
		this.firstduring = during;
		this.posetime = posetime;
	}
	public Buf(float during, int max, float posetime) {
		super();
		this.max = max;
		this.during = during;
		if(during == -1)
			open = true;
		this.firstduring = during;
		this.posetime = posetime;
	}
	public BuffActor getActor() {
		return actor;
	}
	public float getDuring() {
		return firstduring;
	}
	public float getPoseTime() {
		return posetime;
	}
	public boolean isOpen() {
		return open;
	}
	public int getMax() {
		return max;
	}
	public int getCurrent() {
		return current;
	}
	public void setAnimation(RoleAnimation an) {
		this.an = an;
	}
	public void readd() {
		startup = false;
		during = firstduring;
	}
	public void start(Character ch) {
		current += 1;
		ch.changeProperty("HPMAX", hpmax, false, true);
		ch.changeProperty("MPMAX", mpmax, false, true);
		ch.changeProperty("STR", str, false, true);
		ch.changeProperty("INT", inT, false, true);
		ch.changeProperty("VIT", vit, false, true);
		ch.changeProperty("MEN", men, false, true);
		ch.changeProperty("ATKPHY", atkphy, false, true);
		ch.changeProperty("ATKMIG", atkmig, false, true);
		ch.changeProperty("DEFPHY", defphy, false, true);
		ch.changeProperty("DEFMIG", defmig, false, true);
		ch.changeProperty("CRT", crt, false, true);
		ch.changeProperty("CRD", crd, false, true);
		ch.changeProperty("SPHY", sphy, false, true);
		ch.changeProperty("SMIG", smig, false, true);
		ch.changeProperty("SMOV", smov, false, true);
	}
	public void end(Character ch) {
		ch.changeProperty("HPMAX", -hpmax, false, true);
		ch.changeProperty("MPMAX", -mpmax, false, true);
		ch.changeProperty("STR", -str, false, true);
		ch.changeProperty("INT", -inT, false, true);
		ch.changeProperty("VIT", -vit, false, true);
		ch.changeProperty("MEN", -men, false, true);
		ch.changeProperty("ATKPHY", -atkphy, false, true);
		ch.changeProperty("ATKMIG", -atkmig, false, true);
		ch.changeProperty("DEFPHY", -defphy, false, true);
		ch.changeProperty("DEFMIG", -defmig, false, true);
		ch.changeProperty("CRT", -crt, false, true);
		ch.changeProperty("CRD", -crd, false, true);
		ch.changeProperty("SPHY", -sphy, false, true);
		ch.changeProperty("SMIG", -smig, false, true);
		ch.changeProperty("SMOV", -smov, false, true);
		if(an != null)
			an = null;
	}
	public boolean using() {
		if(during < firstduring)
			return true;
		else
			return false;
	}
	public boolean update(Batch batch, float parentAlpha, Character ch, float delta) {
		if(!startup) {
			startup = true;
			start(ch);
		}
		if(actor == null) {
			during -= delta;
			if(an != null) {
				float x = ch.getX()-an.getWidth()/2;
				float y = ch.getY()+ch.getZ()+20;
				an.draw(batch, delta, parentAlpha, x, y, !ch.isRight(), true, false);
			}
			if(open) {
				;
			}else {
				if(during <= 0) {
					for(int i = 0; i < current; i++)
						end(ch);
					current = 0;
					startup = false;
					during = firstduring;
					return true;
				}
			}
			return false;
		}else {
			if(actor.Removed()) {
				for(int i = 0; i < current; i++)
					end(ch);
				return true;
			}else {
				int x = (int) Math.abs(ch.getX()-actor.getX());
				int y = (int) (Math.abs(ch.getY()-actor.getY())*SetBase.fixradius);
				int r = (int) (actor.Radius()/2);
				if(x*x+y*y > r*r) {
					for(int i = 0; i < current; i++)
						end(ch);
					return true;
				}
			}
			return false;
		}
	}
}