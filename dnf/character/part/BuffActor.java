package dnf.character.part;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class BuffActor extends Actor {
	protected String buffstr = null;
	protected Character ch = null;
	protected Buf buff = null;
	private float posetime = 0;
	private Vector2 point = null;
	private Vector2 radius = null;
	protected float time = 0;
	private float duringtime = 0;
	private boolean removed = false;
	protected AddActor add = null;
	protected RoleAnimation start = null;
	protected RoleAnimation last = null;
	protected RoleAnimation an = null;
	protected RoleAnimation end = null;
	private boolean emenyuse = false;
	public BuffActor(Character ch, float posetime, Vector2 point, Vector2 radius, float duringtime) {
		super();
		this.ch = ch;
		this.point = point;
		this.radius = radius;
		this.posetime = posetime;
		this.duringtime = duringtime;
	}
	public BuffActor(Character ch, float posetime, Vector2 point, Vector2 radius, float duringtime, boolean toemeny) {
		super();
		this.ch = ch;
		this.point = point;
		this.radius = radius;
		this.posetime = posetime;
		this.duringtime = duringtime;
		emenyuse = toemeny;
	}
	public float Radius() {
		return radius.x;
	}
	public float getPoseTime() {
		return posetime;
	}
	public void play() {
		if(ch.isRight())
			setPosition(ch.getX()+point.x, ch.getY()+point.y);
		else
			setPosition(ch.getX()-point.x, ch.getY()+point.y);
		time = 0;
		if(add != null) {
			add.reset();
			removed = false;
			if(ch.isRight())
				add.setPosition(getX(), getY()+last.getHeight()/2);
			else
				add.setPosition(getX(), getY()+last.getHeight()/2);
			ch.getParent().addActor(add);
		}
		ch.getParent().addActor(this);
	}
	public boolean Removed() {
		return removed;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		if(time >= duringtime)
			removed = remove();
		if(!removed)
			for(Actor ac : getParent().getChildren())
				if(ac instanceof Character) {
					if(!emenyuse && ((Character) ac).isEmeny(ch.getEmeny()))
						addbuff((Character) ac);
					else if(emenyuse && !((Character) ac).isEmeny(ch.getEmeny()))
						addbuff((Character) ac);
				}
	}
	private void addbuff(Character ac) {
		int x = (int) Math.abs(ac.getX()-getX());
		int y = (int) (Math.abs(ac.getY()-getY())*SetBase.fixradius);
		int r = (int) (radius.x/2);
		if(x*x+y*y <= r*r) {
			try {
				if(buffstr != null) {
					if(buff == null) {
						Class<?> clazz = Class.forName(buffstr);
						java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(BuffActor.class);
						buff = (Buf) constructor.newInstance(this);
					}else if(!ac.havBuff(buff)) {
						Class<?> clazz = Class.forName(buffstr);
						java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(BuffActor.class);
						buff = (Buf) constructor.newInstance(this);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(buff != null && !((Character) ac).havBuff(buff))
				ac.addBuff(buff);
		}
	}
}