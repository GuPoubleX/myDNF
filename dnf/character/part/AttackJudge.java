package dnf.character.part;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.character.Character;
import dnf.character.state.skill.common.*;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;
import dnf.instance.DemageActor;

public class AttackJudge extends Actor implements Disposable {
	private boolean phy = true;
	private float skill_per = 0;
	private float skill_fix = 0;
	private Character ch = null;
	private Array<Character> hitlist = null;
	private float during = 0;
	private float firstduring = 0;
	private float excutetime = 0;
	private float stun = 0;
	private int loop = 0;
	private int type = SetCharSkill.rectangle;
	private int hittype = SetCharSkill.hit;
	private boolean addactor = false;
	private Vector2 attackmove = null;
	private Vector2 charactermove = null;
	private Vector2 use = null;
	private Vector2 fix = null;
	private boolean singlegrasp = true;
	private boolean hittypedirect = false;
	private boolean change = false;
	private Array<AttackJudge> aj = null;
	private Array<Rectangle> atkrgLeft = null;
	private Array<Vector2> atklocLeft = null;
	private Array<Rectangle> atkrgRight = null;
	private Array<Vector2> atklocRight = null;
	private Array<Sprite> left = null;
	private Array<Sprite> right = null;
	private Interpolation ip = Interpolation.linear;
	private float alpha = 1;
	public AttackJudge(boolean phy, float skill_per, float skill_fix, Character ch, float during, float stun) {
		super();
		this.phy = phy;
		this.skill_per = skill_per;
		this.skill_fix = skill_fix;
		this.ch = ch;
		this.during = during;
		this.firstduring = during;
		this.stun = stun;
		this.hittypedirect = ch.isRight();
	}
	public AttackJudge(boolean phy, float skill_per, float skill_fix, Character ch, float during, float stun, int hittype, boolean singlegrasp) {
		super();
		this.phy = phy;
		this.skill_per = skill_per;
		this.skill_fix = skill_fix;
		this.ch = ch;
		this.during = during;
		this.firstduring = during;
		this.stun = stun;
		this.hittype = hittype;
		this.hittypedirect = ch.isRight();
		this.singlegrasp = singlegrasp;
	}
	public AttackJudge(boolean phy, float skill_per, float skill_fix,
			boolean addactor, boolean chdirect, Character ch, float during, float stun, int hittype,
			Vector2 attackmove, Vector2 charactermove, int loop) {
		super();
		this.phy = phy;
		this.skill_per = skill_per;
		this.skill_fix = skill_fix;
		this.ch = ch;
		this.during = during;
		this.firstduring = during;
		this.stun = stun;
		this.loop = loop;
		this.hittype = hittype;
		this.attackmove = attackmove;
		this.charactermove = charactermove;
		this.hittypedirect = ch.isRight();
		this.addactor = addactor;
	}
	public AttackJudge(boolean phy, float skill_per, float skill_fix,
			boolean addactor, boolean direct, boolean change, Character ch, float during, float stun, int hittype,
			Vector2 attackmove, Vector2 charactermove) {
		super();
		this.phy = phy;
		this.skill_per = skill_per;
		this.skill_fix = skill_fix;
		this.ch = ch;
		this.during = during;
		this.firstduring = during;
		this.stun = stun;
		this.hittype = hittype;
		this.attackmove = attackmove;
		this.charactermove = charactermove;
		this.hittypedirect = direct;
		this.change = change;
		this.addactor = addactor;
	}
	public AttackJudge(boolean phy, float skill_per, float skill_fix,
			Array<AttackJudge> aj, boolean chdirect, Character ch, float during, float stun, int hittype,
			Vector2 attackmove, Vector2 charactermove, int loop) {
		super();
		this.phy = phy;
		this.skill_per = skill_per;
		this.skill_fix = skill_fix;
		this.ch = ch;
		this.during = during;
		this.firstduring = during;
		this.stun = stun;
		this.loop = loop;
		this.hittype = hittype;
		this.attackmove = attackmove;
		this.charactermove = charactermove;
		this.hittypedirect = chdirect;
		this.aj = aj;
	}
	public void setAttack(Rectangle rg, Vector2 point, Vector2 fix, int type) {
		if(addactor) {
			setPosition(point.x+(hittypedirect?1:-1)*fix.x, point.y+fix.y);
			ch.getParent().addActor(this);
		}
		this.fix = fix;
		this.type = type;
		if(atkrgRight == null)
			atkrgRight = new Array<Rectangle>();
		if(atklocRight == null)
			atklocRight = new Array<Vector2>();
		atkrgRight.add(rg);
		atklocRight.add(new Vector2(point.x+fix.x, point.y+fix.y));
		if(atkrgLeft == null)
			atkrgLeft = new Array<Rectangle>();
		if(atklocLeft == null)
			atklocLeft = new Array<Vector2>();
		atkrgLeft.add(new Rectangle(rg));
		atklocLeft.add(new Vector2(-rg.getWidth()+point.x-fix.x, point.y+fix.y));
		if(!ch.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG"))
			return;
		if(left == null)
			left = new Array<Sprite>();
		if(right == null)
			right = new Array<Sprite>();
		int w = (int) rg.getWidth();
		int h = (int) rg.getHeight();
		Pixmap pix = new Pixmap(w+1, h+1, Pixmap.Format.RGBA8888);
		pix.setColor(Color.RED);
		pix.drawLine(0, 0, 0, h);
		pix.drawLine(0, h, w, h);
		pix.drawLine(w, h, w, 0);
		pix.drawLine(w, 0, 0, 0);
		right.add(new Sprite(new Texture(pix)));
		left.add(new Sprite(new Texture(pix)));
		pix.dispose();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		excutetime = delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		update(batch, excutetime);
	}
	public void update(Batch batch, float delta) {
		if(during <= 0) {
			if(addactor) {
				if(loop > 0) {
					Vector2 using = new Vector2((hittypedirect?atkrgRight.get(0).x:atkrgLeft.get(0).x+atkrgLeft.get(0).getWidth()),
							(hittypedirect?atkrgRight.get(0).y:atkrgLeft.get(0).y));
					loop -= 1;
					AttackJudge tmp = new AttackJudge(phy, skill_per, skill_fix,
							addactor, hittypedirect, ch, firstduring, stun, hittype, attackmove, charactermove, loop);
					tmp.setAttack(atkrgRight.get(0), using, fix, type);
				}
			}else if(aj != null) {
				if(atkrgRight == null)
					;
				else if(loop > 0) {
					loop -= 1;
					AttackJudge tmp = new AttackJudge(phy, skill_per, skill_fix,
							aj, hittypedirect, ch, firstduring, stun, hittype, attackmove, charactermove, loop);
					if(loop != 0)
						tmp.setAttack(atkrgRight.get(0), new Vector2(0, 0), fix, type);
					else
						tmp.setAttack(atkrgRight.get(0), new Vector2(0, 0), fix, SetCharSkill.hit);
					aj.add(tmp);
				}
			}
			remove();
			dispose();
			return;
		}
		if(attackmove != null) {
			if(use == null)
				use = attackmove;
			else
				use = new Vector2(use.x+attackmove.x, use.y+attackmove.y);
		}
		for(int i = 0; i < atkrgRight.size; i++) {
			if(addactor)
				atkrgRight.get(i).setPosition(use.x+atklocRight.get(i).x+(fix.x==0?0:-fix.x),
						use.y+atklocRight.get(i).y);
			else
				atkrgRight.get(i).setPosition(ch.getX()+atklocRight.get(i).x,
					ch.getY()+ch.getZ()+atklocRight.get(i).y);
		}
		for(int i = 0; i < atkrgLeft.size; i++) {
			if(addactor)
				atkrgLeft.get(i).setPosition(-use.x+atklocLeft.get(i).x+(fix.x==0?0:fix.x),
						use.y+atklocLeft.get(i).y);
			else 
				atkrgLeft.get(i).setPosition(ch.getX()+atklocLeft.get(i).x,
					ch.getY()+ch.getZ()+atklocLeft.get(i).y);
		}
		if(ch.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG")) {
			if(addactor) {
				if(hittypedirect)
					for(int i = 0; i < right.size; i++)
						batch.draw(right.get(i), atkrgRight.get(i).getX(), atkrgRight.get(i).getY());
				else 
					for(int i = 0; i < left.size; i++)
						batch.draw(left.get(i), atkrgLeft.get(i).getX(), atkrgLeft.get(i).getY());
			
			}else {
				if(ch.isRight())
					for(int i = 0; i < right.size; i++)
						batch.draw(right.get(i), atkrgRight.get(i).getX(), atkrgRight.get(i).getY());
				else 
					for(int i = 0; i < left.size; i++)
						batch.draw(left.get(i), atkrgLeft.get(i).getX(), atkrgLeft.get(i).getY());
			}
		}
		during -= delta;
		judge();
	}
	@Override
	public void dispose() {
		if(atkrgRight != null)
			atkrgRight.clear();
		atkrgRight = null;
		if(atklocRight != null)
			atklocRight.clear();
		atklocRight = null;
		if(atkrgLeft != null)
			atkrgLeft.clear();
		atkrgLeft = null;
		if(atklocLeft != null)
			atklocLeft.clear();
		atklocLeft = null;
		if(right != null)
			right.clear();
		right = null;
		if(left != null)
			left.clear();
		left = null;
		use = null;
	}
	private boolean overlapsRectangle(Rectangle rg1, Array<Rectangle> rg2, Character ch) {
		boolean score = false;
		if(ch.getPoseRadius() == 0)
			return score;
		Rectangle down = null;
		Rectangle up = null;
		Rectangle right = null;
		Rectangle left = null;
		for(Rectangle rg : rg2) {
			if(down == null) {
				down = rg;
				up = rg;
				right = rg;
				left = rg;
			}else {
				if(down.getY() > rg.getY())
					down = rg;
				if(up.getY()+up.getHeight() < rg.getY()+rg.getHeight())
					up = rg;
				if(left.getX() > rg.getX())
					left = rg;
				if(right.getX()+right.getWidth() < rg.getY()+rg.getWidth())
					right = rg;
			}
		}
		//--
		for(Rectangle rg : rg2) {
			score = isOverlap(rg1, rg);
			if(score)
				break;
		}
		return score;
	}
	private boolean overlaps(Rectangle rg1, Array<Rectangle> rg2) {
		boolean score = false;
		for(Rectangle rg : rg2) {
			score = isOverlap(rg1, rg);
			if(score)
				break;
		}
		return score;
	}
	private boolean overlapsCircle(Rectangle rg1, Array<Rectangle> rg2, Character ch) {
		boolean score = false;
		float r = rg1.getWidth()/2;
		float h = rg1.getHeight();
		Vector2 center = new Vector2(rg1.getX()+r, rg1.getY());
		Vector2 p = new Vector2(ch.getX(), ch.getY());
		Rectangle down = null;
		Rectangle up = null;
		for(Rectangle rg : rg2) {
			if(down == null) {
				down = rg;
				up = rg;
			}else {
				if(down.getY() > rg.getY())
					down = rg;
				if(up.getY()+up.getHeight() < rg.getY()+rg.getHeight())
					up = rg;
			}
		}
		boolean d = ((int) Math.pow(center.x-p.x, 2))+((int) Math.pow((center.y-p.y)*SetBase.fixradius, 2))<=((int) Math.pow(r, 2));
		boolean z = down.getY()<h+rg1.getY() && up.getY()+up.getHeight()>rg1.getY();
		if(d && z)
			score = true;
		return score;
	}
	private boolean isOverlap(Rectangle rg1, Rectangle rg2) {
		return Intersector.overlaps(rg1, rg2);
	}
	public Array<Character> hit() {
		if(hitlist == null || hitlist.size == 0)
			return null;
		else
			return hitlist;
	}
	public void judge() {
		for(Actor ac : ch.getParent().getChildren())
			if((ac instanceof Character) && !((Character) ac).isEmeny(ch.getEmeny()) 
					&& ((Character) ac).getProperty("HP") > 0 && ((Character) ac).getSalf(true).size != 0) {
				if(hitlist != null) {
					boolean have = false;
					for(Character c : hitlist)
						if(c == ac) {
							have = true;
							break;
						}
					if(have)
						break;
				}else
					hitlist = new Array<Character>();
				Array<Rectangle> emeny = ((Character) ac).getSalf(((Character) ac).isRight());
				Array<Rectangle> self = addactor?(hittypedirect?atkrgRight:atkrgLeft):ch.isRight()?atkrgRight:atkrgLeft;
				boolean hit = false;
				for(Rectangle rg : self) {
					if(type == SetCharSkill.rectangle)
						hit = overlapsRectangle(rg, emeny, (Character) ac);
					else if(type == SetCharSkill.arch)
						hit = overlaps(rg, emeny);
					else if(type == SetCharSkill.circle)
						hit = overlapsCircle(rg, emeny, (Character) ac);
					if(hit)
						break;
				}
				if(hit) {
					if(change) {
						float r = atkrgRight.get(0).width/2;
						float dx = getX()+(hittypedirect?1:-1)*r-((Character) ac).getX();
						float dy = (getY()-((Character) ac).getY())*SetBase.fixradius;
						float progress = Math.min(1, Math.min(0.2f, (dx*dx+dy*dy)/(r*r)));
					//	System.out.println(r+"/"+dx+"-"+dy+",,"+progress+"\n\t"+getX()+"?"+((Character) ac).getX()+"??"+getY()+"?"+((Character) ac).getY());
						alpha = ip.apply(progress);
					}
					if(hitlist.size == 0) {
						hitlist.add((Character) ac);
						demage(ch, (Character) ac);
						float up = ch.getY() - ((Character) ac).getY();
						if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9)
							((Character) ac).setDirect(addactor?!(change?!ch.isRight():hittypedirect):
								ac.getX()>ch.getX()?false:(ac.getX()<ch.getX()?true:((Character) ac).isRight()));
						if(hittype == SetCharSkill.hit) {
							if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp())
								((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
						}else if(hittype == SetCharSkill.hitback) {
							if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp()) {
								((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
								if(charactermove != null)
									((Character) ac).moveto(new Vector2(alpha*charactermove.x*(((Character) ac).isRight()?-1:1),
											alpha*charactermove.y*(up==0?0:(up>0?-1:1))));
							}
						}else if(hittype == SetCharSkill.hitfly) {
							if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp()) {
								((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
								if(charactermove != null) {
									((Character) ac).moveto(new Vector2(charactermove.x*(((Character) ac).isRight()?-1:1), 0));
									((Character) ac).jump(charactermove.y);
								}
							}
						}else if(hittype == SetCharSkill.hitlie) {
							if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp())
								((Character) ac).change(new Lie(((Character) ac).getGame()));
						}else if(hittype == SetCharSkill.hitgrap) {
							if(!((Character) ac).isGrasp())
								((Character) ac).Grasp(true);
							((Character) ac).setDirect(!ch.isRight());
							((Character) ac).change(new Hurt(((Character) ac).getGame(), stun, SetCharSkill.lv10));
						}
					}else {
						if(hittype == SetCharSkill.hitgrap && singlegrasp)
							return;
						boolean canadd = true;
						for(Character c : hitlist)
							if(ac == c) {
								canadd = false;
								break;
							}
						if(canadd) {
							hitlist.add((Character) ac);
							demage(ch, (Character) ac);
							float up = ch.getY() - ((Character) ac).getY();
							if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9)
								((Character) ac).setDirect(addactor?!(change?!ch.isRight():hittypedirect):
										ac.getX()>ch.getX()?false:(ac.getX()<ch.getX()?true:((Character) ac).isRight()));
							if(hittype == SetCharSkill.hit) {
								if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp())
									((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
							}else if(hittype == SetCharSkill.hitback) {
								if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp()) {
									((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
									if(charactermove != null)
										((Character) ac).moveto(new Vector2(alpha*charactermove.x*(((Character) ac).isRight()?-1:1),
												alpha*charactermove.y*(up==0?0:(up>0?-1:1))));
								}
							}else if(hittype == SetCharSkill.hitfly) {
								if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp()) {
									((Character) ac).change(new Hurt(((Character) ac).getGame(), stun));
									if(charactermove != null) {
										((Character) ac).moveto(new Vector2(charactermove.x*(((Character) ac).isRight()?-1:1), 0));
										((Character) ac).jump(charactermove.y);
									}
								}
							}else if(hittype == SetCharSkill.hitlie) {
								if(((Character) ac).currentPose().getForce() != SetCharSkill.lv9 && !((Character) ac).isGrasp())
									((Character) ac).change(new Lie(((Character) ac).getGame()));
							}else if(hittype == SetCharSkill.hitgrap) {
								if(!((Character) ac).isGrasp())
									((Character) ac).Grasp(true);
								((Character) ac).setDirect(!ch.isRight());
								((Character) ac).change(new Hurt(((Character) ac).getGame(), stun, SetCharSkill.lv10));
							}
						}
					}
				}
			}
	}
	private void demage(Character attack, Character hit) {
		float alv = attack.getProperty("LV");
		float hlv = attack.getProperty("LV");
		float crt = attack.getProperty("CRT");
		float crd = attack.getProperty("CRD");
		boolean crit = ((int) (Math.random()*100))<(crt*100+50)?true:false;
		float lv_mis = (alv-hlv)/5>-1?0:-0.9f;//level
		float def_mis = 1;//defense
		float def_elm = 1;//element
		float scale_per = 1;
		float scale_fix = 1;
		if(phy) {
			float str = attack.getProperty("STR");
			float atkphy = attack.getProperty("ATKPHY");
			float defphy = hit.getProperty("DEFPHY");
			def_mis = 1-(defphy/(defphy+100*hit.getProperty("LV")));
			def_elm = 1;
			scale_fix = 1+str/100;
			scale_per = atkphy*(skill_per/100)*scale_fix;
		}else {
			float inT = attack.getProperty("INT");
			float atkmig = attack.getProperty("ATKMIG");
			float defmig = hit.getProperty("DEFMIG");
			def_mis = 1-(defmig/(defmig+100*hit.getProperty("LV")));
			def_elm = 1;
			scale_fix = 1+inT/100;
			scale_per = atkmig*(skill_per/100)*scale_fix;
		}
		float demage = (scale_per+scale_fix*skill_fix)*(crit?crd/100:1)*(1+lv_mis)*(def_mis*def_elm)/**mapmis*/;
		if(demage != 0) {
			hit.getInstance().showHPline(hit);
			boolean right = ((int) (Math.random()*100))%2==0?true:false;
			float per = 1+(right?1:-1)*0.01f*((int) (Math.random()*SetBase.demageFlow));
			hit.changeProperty("HP", -demage*per, false, false);
			DemageActor da = new DemageActor(phy, hit.getGame(), crit, (int) (demage*per), hit.getFix());
			da.setPosition(hit.getX(), hit.getY());
			hit.getParent().addActor(da);
		}
	}
}