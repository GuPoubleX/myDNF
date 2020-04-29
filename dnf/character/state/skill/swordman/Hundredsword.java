package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import dnf.author.FlipActor;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.AttackJudge;
import dnf.character.part.SkillPose;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetCharSkill;
import dnf.gupoublex.set.SetSwordmanSkill;

public class Hundredsword extends SkillPose {
	public Hundredsword(GuPoubleXGame game, float cd) {
		super(game, SetCharSkill.lv2, cd);
	}
	private Interpolation ip2 = Interpolation.exp5Out;
	private float extime = 0;
	private float endtime = 0;
	private boolean addcircle = false;
	private boolean addweapon = false;
	private boolean endaction = false;
	private float actiontime = 0;
	private int add = 0;
	private boolean direct = true;
	private boolean startdo = false;
	private boolean addattack = false;
	private Vector2 first = null;
	private boolean firstdir = true;
	private boolean release = false;
	private boolean waitxy = false;
	private boolean firstdo = false;
	private Array<HundredswordWeapon> weapon = null;
	private Array<HundredswordWeapon> weaponr = null;
	private Array<HundredswordWeapon> weaponl = null;
	private HundredswordWeapon current = null;
	private Array<FlipActor> floor = null;
	private float startx = 0;
	private float starty = 0;
	private float startz = 0;
	private float endx = 0;
	private float endy = 0;
	private float endz = 0;
	private float excutetime = 0;
	@Override
	public void start(Character ch) {
		ch.move(0, 0);
		first = new Vector2(ch.getX(), ch.getY());
		firstdir = ch.isRight();
		getAnimation(ch);
		forceLv = SetCharSkill.lv9;
	}
	@Override
	public void running(Character ch, Batch batch, float parentAlpha, float delta) {
		time += delta;
		if(time < 3.5f) {
			direct = ch.isRight();
			for(RoleAnimation anima : an)
				anima.draw(true, false, batch, time, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		}else {
			if(!release) {
				release = true;
				ch.drawShadow(false);
				resetSelf(ch);
			}
			moveUpdeate(batch, parentAlpha, ch, delta);
		}
		if(time < 1.5f) {
			float x = ch.getX()+(ch.isRight()?-230:-410);
			float y = ch.getY()+ch.getZ()-125;
			anup.draw(batch, delta, parentAlpha, x, y, !ch.isRight(), true, true);
		}
		if(time >= 1 && !addcircle) {
			addcircle = true;
			int count = 6;
			for(int i = 0; i < count; i++) {
				HundredswordCircle hc = new HundredswordCircle(ch.getGame(),
						new Vector3(ch.getX()+(ch.isRight()?1:-1)*200, ch.getY()+ch.getZ(), 250), new Vector3(360/count*i, 0, 150));
				ch.getParent().addActor(hc);
			}
		}
		if(time >= 2 && !addweapon) {
			addweapon = true;
			weapon = new Array<HundredswordWeapon>();
			int count = 24;
			for(int i = 0; i < count; i++) {
				HundredswordWeapon hw = new HundredswordWeapon(ch.getGame(),
						new Vector3(ch.getX()+(ch.isRight()?1:-1)*200, ch.getY()+ch.getZ(), 250),
						new Vector3(360/count*i+(ch.isRight()?1:-1)*90, 0, 150), i%5, i%2==0?true:false);
				ch.getParent().addActor(hw);
				weapon.add(hw);
			}
			floor = new Array<FlipActor>();
			FlipActor fa = new FlipActor(game.getImg(SetSwordmanSkill.hundredsword, "floor_circle.img").getIndex(0), false, false);
			fa.setPosition(ch.getX()+(ch.isRight()?1:-1)*200-fa.getWidth(), ch.getY()+fa.getHeight());
			ch.getParent().addActor(fa);
			floor.add(fa);
			fa = new FlipActor(game.getImg(SetSwordmanSkill.hundredsword, "floor_circle.img").getIndex(0), true, false);
			fa.setPosition(ch.getX()+(ch.isRight()?1:-1)*200+fa.getWidth(), ch.getY()+fa.getHeight());
			ch.getParent().addActor(fa);
			floor.add(fa);
			fa = new FlipActor(game.getImg(SetSwordmanSkill.hundredsword, "floor_circle.img").getIndex(0), false, true);
			fa.setPosition(ch.getX()+(ch.isRight()?1:-1)*200-fa.getWidth(), ch.getY());
			ch.getParent().addActor(fa);
			floor.add(fa);
			fa = new FlipActor(game.getImg(SetSwordmanSkill.hundredsword, "floor_circle.img").getIndex(0), true, true);
			fa.setPosition(ch.getX()+(ch.isRight()?1:-1)*200+fa.getWidth(), ch.getY());
			ch.getParent().addActor(fa);
			floor.add(fa);
		}
		drawPolygon(ch, batch, delta);
	}
	@Override
	public void end(Character ch) {
		forceLv = SetCharSkill.lv2;
		extime = 0;
		endtime = 0;
		addcircle = false;
		addweapon = false;
		endaction = false;
		release = false;
		if(weapon != null)
			weapon.clear();
		weapon = null;
		if(weaponr != null)
			weaponr.clear();
		weaponr = null;
		if(weaponl != null)
			weaponl.clear();
		weaponl = null;
		if(floor != null)
			floor.clear();
		floor = null;
		actiontime = 0;
		add = 0;
		addattack = false;
		waitxy = false;
		firstdo = false;
		super.end(ch);
	}
	private void moveUpdeate(Batch batch, float parentAlpha, Character ch, float delta) {
		actiontime += delta;
		if(!waitxy) {
			waitxy = true;
			anup = null;
			weaponr = new Array<HundredswordWeapon>();
			weaponl = new Array<HundredswordWeapon>();
			for(HundredswordWeapon hw : weapon) {
				if(hw.isRight())
					weaponr.add(hw);
				else
					weaponl.add(hw);
			}
		}
		if(add < weapon.size) {
			if(!startdo) {
				startdo = true;
				if(!firstdo) {
					firstdo = true;
					int min = 0;
					int id = 0;
					int i = 0;
					for(HundredswordWeapon hw : (ch.isRight()?weaponr:weaponl)) {
						if(hw.getZ() == 0) {
							i++;
							continue;
						}
						if(min == 0) {
							id = 0;
							min = (int) Math.pow((int) (ch.getX()-hw.getX()), 2)+(int) Math.pow((int) (ch.getY()-hw.getY()), 2);
						}else if(min > (int) Math.pow((int) (ch.getX()-hw.getX()), 2)+(int) Math.pow((int) (ch.getY()-hw.getY()), 2)) {
							id = i;
							min = (int) Math.pow((int) (ch.getX()-hw.getX()), 2)+(int) Math.pow((int) (ch.getY()-hw.getY()), 2);
						}
						i++;
					}
					current = (ch.isRight()?weaponr:weaponl).get(id);
					(ch.isRight()?weaponr:weaponl).removeValue(current, true);
					excutetime = 0.6f;
					an = ch.getAn(19, false, false);
					startx = ch.getX();
					starty = ch.getY();
					startz = ch.getZ();
				}else {
					startx = current.getX();
					starty = current.getY();
					startz = current.getZ();
					Array<HundredswordWeapon> tmp = current.isRight()?weaponl:weaponr;
					int i = ((int) (Math.random()*tmp.size*10))%tmp.size;
					current = tmp.get(i);
					tmp.removeValue(current, true);
					int speed = (weapon.size-add)/6;
					excutetime = speed>3?0.3f:(speed>2?0.2f:0.1f);
					ch.setDirect(!current.isRight());
					an = ch.getAn(current.getPose(), false, true);
					SpriteTexture re[] = new SpriteTexture[3];
					for(int ik = 0; ik < re.length; ik++)
						re[ik] = ch.getGame().getImg(SetSwordmanSkill.hundredsword, "dash.img").getIndexST(ik);
					anup = new RoleAnimation(excutetime/re.length, re);
				}
				endx = current.getX();
				endy = current.getY();
				endz = current.getZ();
			}
			if(actiontime < excutetime) {
				float progress = Math.min(1, actiontime/excutetime);
				float alpha = ip2.apply(progress);
				float x = startx+(endx-startx)*alpha;
				float y = starty+(endy-starty)*alpha;
				float z = startz+(endz-startz)*alpha;
				if(alpha > 0.25f) {
					if(!addattack) {
						addattack = true;
						setAttack(ch, SetCharSkill.hitback, new Vector2(-20, -5));
					}
					current.show(excutetime);
				}
				float degree = (float) (Math.atan2(endy-starty+endz-startz, endx-startx)*(180/Math.PI));
				for(RoleAnimation anima : an) {
				//	Sprite sp = null;//new Sprite(getAnimation(ch, anima, false, actiontime));
				//	sp.flip(!ch.isRight(), false);
				//	if(add == 0) {
				//		sp.setPosition(x-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
				//				y-sp.getHeight()/2+ch.getFix().y+z-50);
				//	}else {
				//		sp.setOrigin(sp.getWidth()/2-ch.getFix().x-(ch.isRight()?0:ch.getFix2().x), sp.getHeight()/2-ch.getFix().y);
				//		sp.setRotation(ch.isRight()?degree:degree-180);
				//		sp.setPosition(x-sp.getWidth()/2+ch.getFix().x+(ch.isRight()?0:ch.getFix2().x),
				//				y-sp.getHeight()/2+ch.getFix().y+z);
				//	}
					if(add == 0)
						anima.draw(false, false, batch, actiontime, parentAlpha, x, y+z-50, ch.getFix(), ch.getFix2(), !ch.isRight());
					else
						anima.draw(batch, actiontime, parentAlpha, x, y+z, ch.getFix(), ch.getFix2(), ch.isRight()?degree:degree-180, !ch.isRight());
				}
				if(anup != null) {
				//	Sprite sp = null;//new Sprite(getAnimation(ch, anup, false, actiontime));
				//	sp.flip(!ch.isRight(), false);
				//	sp.setOrigin(sp.getWidth()/2+(ch.isRight()?1:-1)*150, sp.getHeight()/2);
				//	sp.setRotation(ch.isRight()?degree:degree-180);
				//	sp.setPosition(x-sp.getWidth()/2+(ch.isRight()?-1:1)*150, y+z-sp.getHeight()/2+50);
				//	sp.draw(batch, parentAlpha);
				//	anup.draw(false, false, batch, time, parentAlpha, x, y, ch.getFix(), ch.getFix2(), !ch.isRight());
					anup.draw(batch, actiontime, parentAlpha, x, y+z+50, new Vector2(0, 0), new Vector2(0, 0), ch.isRight()?degree:degree-180, !ch.isRight());
				}
			}else {
				startdo = false;
				addattack = false;
				actiontime -= excutetime;
				add += 1;
			}
		}else
			endAttack(batch, parentAlpha, ch, delta);
	}
	private void endAttack(Batch batch, float parentAlpha, Character ch, float delta) {
		endtime += delta;
		if(!endaction) {
			endaction = true;
			ch.setDirect(direct);
			ch.drawShadow(true);
			for(FlipActor fa : floor)
				fa.remove();
			int pose = 31;
			anstart = (int) ch.getPoseSet().get(pose).x;
			an = ch.getAn(pose);
			for(RoleAnimation anima : an)
				if(anima.getFrameDuration() != 1.0f/anima.getKeyFrames().length)
					anima.setFrameDuration(1.0f/anima.getKeyFrames().length);
			Img img = ch.getGame().getImg(SetSwordmanSkill.hundredsword, "finish_slash.img");
			SpriteTexture re[] = new SpriteTexture[img.getCount()+2];
			for(int i = 0; i < re.length; i++) {
				if(i >= 5)
					re[i] = new SpriteTexture(new Texture(1, 1, Format.RGBA8888));
				else
					re[i] = img.getIndexST(i);
			}
			anup = new RoleAnimation(1.0f/re.length, re);
		}
		for(RoleAnimation anima : an)
			anima.draw(true, false, batch, endtime, parentAlpha, ch.getX(), ch.getY()+ch.getZ(), ch.getFix(), ch.getFix2(), !ch.isRight());
		if(endtime >= an.get(0).getFrameDuration()*2) {
			if(!addattack) {
				addattack = true;
				setAttack2(ch, SetCharSkill.hitfly, new Vector2(3, 5));
			}
			anup.draw(batch, delta, parentAlpha,
					ch.getX()+(ch.isRight()?0:-1)*anup.getWidth()+(ch.isRight()?-1:1)*80, ch.getY()+ch.getZ(), !ch.isRight(), false, true);
		}
		if(endtime >= 2)
			endPose = true;
	}
	private void getAnimation(Character ch) {
		int pose = 26;
		anstart = (int) ch.getPoseSet().get(pose).x;
		an = ch.getAn(pose, false, false);
		//ch.getSound(75, 0).play(ch.getGame().getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER"));
		for(RoleAnimation anima : an)
			if(anima.getFrameDuration() != 1.5f/anima.getKeyFrames().length)
				anima.setFrameDuration(1.5f/anima.getKeyFrames().length);
		SpriteTexture re[] = new SpriteTexture[13];
		re[0] = new SpriteTexture(new Texture(640, 480, Format.RGBA8888));
		re[1] = new SpriteTexture(new Texture(640, 480, Format.RGBA8888));
		re[2] = new SpriteTexture(new Texture(640, 480, Format.RGBA8888));
		for(int i = 3; i < re.length-1; i++)
			re[i] = ch.getGame().getImg(SetSwordmanSkill.hundredsword, "cast.img").getIndexST(i-3);
		re[re.length-1] = new SpriteTexture(new Texture(640, 480, Format.RGBA8888));
		anup = new RoleAnimation(1.5f*(re.length/15.0f)/re.length, re);
	}
	private void setAttack(Character ch, int hittype, Vector2 charactermove) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, true, firstdir, true, ch, 0.1f, 0.5f, hittype, new Vector2(0, 0), charactermove);
		tmp.setAttack(new Rectangle(0,0,500,200), new Vector2(first.x+(firstdir?-1:1)*50, first.y), new Vector2(0, 0), SetCharSkill.circle);
		aj.add(tmp);
	}
	public void setAttack2(Character ch, int hittype, Vector2 charactermove) {
		if(aj == null)
			aj = new Array<AttackJudge>();
		AttackJudge tmp = new AttackJudge(true, 100, 0, false, ch.isRight(), ch, 0.8f, 0.5f, hittype, ch.getLineV(), charactermove, 0);
		tmp.setAttack(new Rectangle(0,0,200,180), new Vector2(0, 0), new Vector2(0, 0), SetCharSkill.arch);
		aj.add(tmp);
	}
}