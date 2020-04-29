package dnf.character;

import java.util.HashMap;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.author.RoleAnimation;
import dnf.author.TextLabel;
import dnf.character.part.Buf;
import dnf.character.part.Dress;
import dnf.character.part.Pose;
import dnf.character.roletype.humen.*;
import dnf.character.state.Move;
import dnf.character.state.ObstructState;
import dnf.character.state.Stay;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetDress;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetNPC;
import dnf.hud.button.SkillButton;
import dnf.instance.ScreenInstance;
import dnf.instance.reward.RewardCard;

public class Character extends Actor implements Disposable {
	protected float str_perLV = 0;
	protected float inT_perLV = 0;
	protected float vit_perLV = 0;
	protected float men_perLV = 0;
	protected float atkphy_perLV = 0;
	protected float atkmig_perLV = 0;
	protected float defphy_perLV = 0;
	protected float defmig_perLV = 0;
	
	protected HashMap<Integer, Array<Rectangle>> self = null;
	protected HashMap<Integer, Array<Vector2>> selfpoint = null;
	protected int radius = 0;
	protected int face = -1;
	protected Array<Integer> label = null;
	private boolean isObstruct = false;
	private boolean isNPC = false;
	private GuPoubleXGame game = null;
	private ScreenInstance instance = null;
	private Location location = null;
	private Property property = null;
	private Surface surface = null;
	private Buff buff = null;
	private Preferences pref = null;
	private SkillRelated related = null;
	private BehaviorTree<Character> btree = null;
	private Character lookat = null;
	private State state = null;
	protected String mainpath = null;
	private ShadowImage shadow = null;
	protected Array<Vector2> pose = null;
	protected Vector2 fix = null;//fix world location
	protected Vector2 fix2 = null;//fix left or right world location
	protected float instancespeed = 1;
	private Vector2 infoP = null;
	private float deltatime = 0;
	private float time = 0;
	private boolean grasp = false;
	private TextLabel namelabel = null;
	protected int fixname = 0;
	private int skillid[] = null;
	private int emeny = SetCharProperty.neutral;
	private boolean becontrol = false;
	private Array<SkillButton> skilllist = null;
	private SkillButton readyplay = null;
	private float x_time = 0;
	private float y_time = 0;
	private float x_stand = 0;
	private float y_stand = 0;
	private boolean died = false;
	private RewardCard up = null;
	private RewardCard down = null;
	private int lv = 1;
	private boolean lvAn = false;
	private RoleAnimation show1 = null;
	private RoleAnimation show2 = null;
	public void setRewardCard(boolean up, RewardCard card) {
		if(up)
			this.up = card;
		else
			down = card;
	}
	public RewardCard getRewardCard(boolean up) {
		return up?this.up:down;
	}
	public void resetRewardCard() {
		up = null;
		down = null;
	}
	public void drawShadow(boolean draw) {
		shadow.setVisible(draw);
	}
	public void setShadow(ShadowImage shadow) {
		this.shadow = shadow;
	}
	public ShadowImage getShadow() {
		return shadow;
	}
	public boolean isGrasp() {
		return grasp;
	}
	public void Grasp(boolean grasp) {
		this.grasp = grasp;
	}
	private void step(float delta) {
		if(time == 0) {
			x_stand = getX();
			y_stand = getY();
		}
		if(x_stand != getX()) {
			x_stand = getX();
			x_time = 0;
		}else
			x_time += delta;
		if(y_stand != getY()) {
			y_stand = getY();
			y_time = 0;
		}else
			y_time += delta;
	}
	public boolean Xovertime() {
		return x_time>=0.5f?true:false;
	}
	public boolean Yovertime() {
		return y_time>=0.5f?true:false;
	}
	public Character(GuPoubleXGame game) {
		super();
		this.game = game;
		setName("");
	}
	//object
	public Character(GuPoubleXGame game, String name, SpriteTexture re) {
		super();
		this.game = game;
		setName(name);
		isObstruct = true;
		location = new Location();
		surface = new Surface(re);
		state = new State(this);
	}
	//object
	public Character(GuPoubleXGame game, String name, SpriteTexture st[]) {
		super();
		this.game = game;
		setName(name);
		isObstruct = true;
		location = new Location();
		surface = new Surface(st);
		state = new State(this);
	}
	//npc
	public Character(GuPoubleXGame game, String name, Img img, int fixname) {
		super();
		label = new Array<Integer>();
		this.game = game;
		setName(name);
		isNPC = true;
		location = new Location();
		if(!name.equals(SetNPC._fire)) {
			SpriteTexture st[] = new SpriteTexture[img.getCount()];
			for(int i = 0; i < st.length; i++)
				st[i] = img.getIndexST(i);
			surface = new Surface(st);
		}else {
			SpriteTexture st[] = new SpriteTexture[img.getCount()-1];
			for(int i = 0; i < st.length; i++)
				st[i] = img.getIndexST(i);
			surface = new Surface(st);
		}
		state = new State(this);
		namelabel = new TextLabel(game);
		namelabel.setFontScale(0.7f);
		this.fixname = fixname;
		if(!name.equals(SetNPC._fire))
			shadow = new ShadowImage(game.getImg(SetImg.characterinfo, "sunkenbg.img").getIndex(0), this);
		else
			shadow = new ShadowImage(img.getIndex(img.getCount()-1), this);
	}
	//apc
	public Character(GuPoubleXGame game, String rolename, int fixname, int emeny) {
		super();
		label = new Array<Integer>();
		this.game = game;
		this.emeny = emeny;
		this.pref = game.getPref(rolename);
		this.fixname = fixname;
		location = new Location(pref);
		property = new Property(pref, location);
		pose = new Array<Vector2>();
		state = new State(this);
		namelabel = new TextLabel(game);
		namelabel.setFontScale(0.7f);
		shadow = new ShadowImage(game.getImg(SetImg.characterinfo, "sunkenbg.img").getIndex(0), this);
	}
	//role
	public Character(GuPoubleXGame game, int id, int fixname, int emeny) {
		super();
		label = new Array<Integer>();
		this.game = game;
		this.emeny = emeny;
		this.becontrol = true;
		this.pref = game.getPref(game.getAccount()+"."+id);
		this.fixname = fixname;
		location = new Location(pref);
		property = new Property(pref, location);
		pose = new Array<Vector2>();
		state = new State(this);
		namelabel = new TextLabel(game);
		namelabel.setFontScale(0.7f);
		shadow = new ShadowImage(game.getImg(SetImg.characterinfo, "sunkenbg.img").getIndex(0), this);
	}
	public Array<SkillButton> getSkillList() {
		return skilllist;
	}
	public SkillButton readySkill() {
		return readyplay;
	}
	public void readySkill(SkillButton sk) {
		readyplay = sk;
	}
	public void findEmeny(Character find) {
		this.lookat = find;
	}
	public Character emenyCharacter() {
		return lookat;
	}
	public int getEmeny() {
		return emeny;
	}
	public boolean isEmeny(int emeny) {
		return this.emeny==emeny;
	}
	public boolean isControlled() {
		return becontrol;
	}
	public GuPoubleXGame getGame() {
		return game;
	}
	public AssetManager getManager() {
		return game.getManager();
	}
	public int[] getSkillID() {
		return skillid;
	}
	public void resetSkillID(int skillid[]) {
		this.skillid = skillid;
		for(int i = 0; i < skillid.length; i++)
			pref.putInteger("SKILL"+(i+1), skillid[i]);
		pref.flush();
	}
	public void clearWorld(World world, World world_g) {
		location.clearWorld(world, world_g);
	}
	public boolean isclearWorld() {
		return location.isclearWorld();
	}
	public Vector2 getLineV() {
		return location.getXYLineV();
	}
	public Vector2 getZLineV() {
		return location.getZLineV();
	}
	public void setZLineV(Vector2 v) {
		location.setZLineV(v);
	}
	public void resetTime() {
		property.resetTime();
	}
	public Array<Rectangle> getSalf(boolean right) {
		return location.getSalf(right);
	}
	public Array<Vector2> getPoseSet() {
		return pose;
	}
	public int getPoseRadius() {
		return radius;
	}
	public void setSelf(int id, Array<Rectangle> r, Array<Vector2> p) {
		r.clear();
		p.clear();
		for(Rectangle re : getRectangleSet(id))
			r.add(re);
		for(Vector2 vec : getVector2Set(id))
			p.add(vec);
	}
	private Array<Rectangle> getRectangleSet(int id) {
		if(self.get(id) == null)
			return getRectangleSet(id-1);
		return self.get(id);
	}
	private Array<Vector2> getVector2Set(int id) {
		if(selfpoint.get(id) == null)
			return getVector2Set(id-1);
		return selfpoint.get(id);
	}
	protected void addSelf(int index, Array<Vector2> p) {
		Array<Rectangle> r = new Array<Rectangle>();
		for(int i = 0; i < p.size; i++) {
			Rectangle re = new Rectangle(0, 0, 10, 10);
			r.add(re);
		}
		self.put(index, r);
	}
	protected void init() {
		skillid = new int[12];
		for(int i = 0; i < skillid.length; i++)
			skillid[i] = pref.getInteger("SKILL"+(i+1));
		setName(pref.getString("NAME"));
		namelabel.setText(pref.getString("NAME"));
		related = new SkillRelated(game, this, pref);
		surface = new Surface(game, mainpath, pose);
		buff = new Buff(this);
		skilllist = new Array<SkillButton>();
		BehaviorTreeLibraryManager btlm = BehaviorTreeLibraryManager.getInstance();
		//btlm.setLibrary(new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH));
		btree = btlm.createBehaviorTree(SetCharProperty.tree, this);
		surface.changeDress(SetDress.hair, pref.getString("HAIR"));
		surface.changeDress(SetDress.hat, pref.getString("CAP"));
		surface.changeDress(SetDress.face, pref.getString("FACE"));
		surface.changeDress(SetDress.chest, pref.getString("NECK"));
		surface.changeDress(SetDress.coat, pref.getString("COAT"));
		surface.changeDress(SetDress.skin, pref.getString("SKIN"));
		surface.changeDress(SetDress.belt, pref.getString("BELT"));
		surface.changeDress(SetDress.pant, pref.getString("PANTS"));
		surface.changeDress(SetDress.shoe, pref.getString("SHOES"));
		surface.changeDress(SetDress.weapon, pref.getString("WEAPON"));
		location.setTown(pref.getInteger("TOWN"));
		if(becontrol) {
			SpriteTexture st[] = new SpriteTexture[15];
			for(int i = 0; i < st.length; i++)
				st[i] = game.getImg(SetImg.characterinfo, "blast1.img").getIndexST(i);
			show1 = new RoleAnimation(1.5f/st.length, st);
			st = new SpriteTexture[11];
			for(int i = 0; i < st.length; i++)
				st[i] = game.getImg(SetImg.characterinfo, "blast2.img").getIndexST(i);
			show2 = new RoleAnimation(1.5f/st.length, st);
		}
		lv = (int) getProperty("LV");
		enterAdmin();
	}
	public void loadSkill() {
		if(!becontrol)
			for(int i = 1; i <= 12; i++)
				if(skillid[i-1] != -1)
					skilllist.add(new SkillButton(this, skillid[i-1]));
	}
	public String getProfession() {
		return property.getProfession();
	}
	public float getProperty(String str) {
		return property.getProperty(str);
	}
	public float getBuffProperty(String str) {
		return property.getBuffProperty(str);
	}
	public boolean changeProperty(String str, float val, boolean eq, boolean buf) {
		return property.changeProperty(str, val, eq, buf);
	}
	public void currentWeak() {
		property.currentWeak();
	}
	public void resumeWeak() {
		property.setWeak(1);
	}
	public boolean isRight() {
		return location.isRight();
	}
	public void setDirect(boolean right) {
		location.setDirect(right);
	}
	public void set(String str, Vector2 move) {
		if(isObstruct || isNPC)
			state.set(new ObstructState(game));
		if(str.equals("stay"))
			state.set(new Stay(game));
		else if(str.equals("move"))
			state.set(new Move(game, move));
	}
	public Pose currentPose() {
		return state.currentPose();
	}
	public boolean change(Pose pose) {
		return state.change(pose);
	}
	public void jump(float power) {
		location.jump(power);
	}
	public void moveto(Vector2 moving) {
		location.moveto(new Vector2(moving.x*(1+getProperty("SMOV")), moving.y*(1+getProperty("SMOV"))));
	}
	public Array<RoleAnimation> getAn(int pose) {
		return surface.getAn(pose);
	}
	public Array<RoleAnimation> getAn(int pose, boolean skin, boolean weapon) {
		return surface.getAn(pose, skin, weapon);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		deltatime = delta;
		time += deltatime;
		if(property != null && property.getProperty("HP") <= 0) {
			die();
			return;
		}
		if(property != null && lv != getProperty("LV")) {
			lvAn = true;
			int d = (int) (getProperty("LV")-lv);
			lv = (int) getProperty("LV");
			changeProperty("STR", str_perLV*d, false, false);
			changeProperty("INT", inT_perLV*d, false, false);
			changeProperty("VIT", vit_perLV*d, false, false);
			changeProperty("MEN", men_perLV*d, false, false);
			changeProperty("ATKPHY", atkphy_perLV*d, false, false);
			changeProperty("ATKMIG", atkmig_perLV*d, false, false);
			changeProperty("DEFPHY", defphy_perLV*d, false, false);
			changeProperty("DEFMIG", defmig_perLV*d, false, false);
			show1.resetTime();
			show2.resetTime();
		}
		location.update(delta);
		if(inInstance())
			step(delta);
		if(!becontrol && inInstance() && !isNPC)
			for(SkillButton sb : skilllist)
				sb.act(delta);
		if(!isObstruct && !isNPC) {
			if(property != null)
				property.act(delta);
			if(becontrol && !location.inAdmin() && time >= 1) {//in town or instance
				time -= 1;
				pref.putFloat("TIME", pref.getFloat("TIME")+1);
				if(!location.inTown())//in instance
					pref.putFloat("TIME_INSTANCE", pref.getFloat("TIME_INSTANCE")+1);
				pref.flush();
			}
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(property != null && property.getProperty("HP") <= 0)
			return;
		if(!location.inAdmin() && namelabel != null && !getName().equals(SetNPC._fire)) {
			namelabel.setPosition(getX()-namelabel.getWidth()/2, getY()+100+fixname+getZ());
			namelabel.draw(batch, parentAlpha);
			namelabel.setText((property==null?"":(boss()?"[PINK]":hero()?"[PURPLE]":special()?"[GREEN]":"[WHITE]")+
				("Lv."+((int) getProperty("LV"))+" "))+getName()+(property==null?"":"[]"));
		}
		if(lvAn && becontrol) {
			int s = show1.draw(false, false, batch, deltatime, parentAlpha, getX(), getY(), new Vector2(7, 217), new Vector2(0, 0), false);
			if(s == show1.getKeyFrames().length-1)
				lvAn = false;
		}
		state.act(batch, parentAlpha, deltatime);
		if(lvAn && becontrol)
			show2.draw(false, false, batch, deltatime, parentAlpha, getX(), getY(), new Vector2(7, 217), new Vector2(0, 0), false);
		if(buff != null && inInstance())
			buff.update(batch, parentAlpha, deltatime);
		if(btree != null && inInstance())
			btree.step();
	}
	private boolean boss() {
		return getEmenyType()==SetCharProperty.boss;
	}
	private boolean hero() {
		return getEmenyType()==SetCharProperty.hero;
	}
	private boolean special() {
		return getEmenyType()==SetCharProperty.special;
	}
	@Override
	public void dispose() {
		if(surface.getDress() != null)
			for(Dress dress : surface.getDress().values())
				dress.dispose();
		location.dispose();
		if(property != null)
			property.dispose();
		state.dispose();
		if(related != null)
			related.dispose();
	}
	public Array<Integer> getLabel() {
		return label;
	}
	public int getFace() {
		return face;
	}
	public void setEmenyType(int type) {
		property.setEmenyType(type);
	}
	public int getEmenyType() {
		return property.getEmenyType();
	}
	public void getAnimation() {
		for(Dress dress : surface.getDress().values())
			dress.setAnimation();
	}
	public Location getLocation() {
		return location;
	}
	public void setTownWorld(World world, float x, float y, Vector2 ver) {
		location.setTownWorld(world, x, y, ver);
	}
	public void setInstanceWorld(World world, World world_g, float x, float y, Vector2 ver) {
		location.setInstanceWorld(world, world_g, x, y, ver);
	}
	public void move(float x, float y) {
		if(!location.inTown()) {
			if(this instanceof Swordman)
				location.move(x*instancespeed*(1+getProperty("SMOV")),
						y*instancespeed*(1+getProperty("SMOV")));
			else
				location.move(x, y);
		}else
			location.move(x, y);
	}
	public void moveadd(float x, float y) {
		if(!location.inTown())
			location.moveadd(x, y);
	}
	public void setPosition(float x, float y) {
		location.setPosition(x, y);
	}
	public float getX() {
		return location.getX();
	}
	public float getY() {
		return location.getY();
	}
	public float getZ() {
		return location.getZ();
	}
	public void setZ(float z, boolean reset) {
		location.setZ(z, reset);
	}
	public void setInfoPosition(Vector2 infoP) {
		this.infoP = infoP;
	}
	public Vector2 getInfoPosition() {
		return infoP;
	}
	public void enterAdmin() {
		location.enterAdmin();
	}
	public void enterTown() {
		instance = null;
		location.enterTown();
		if(property != null)
			property.clear();
		if(related != null)
			related.unloadInstance();
		if(buff != null)
			buff.clear();
	}
	public void enterInstance(Vector2 ver) {
		location.enterInstance(ver);
	}
	public void setInstacne(ScreenInstance instance) {
		this.instance = instance;
	}
	public ScreenInstance getInstance() {
		return instance;
	}
	public void enterInstance() {
		location.enterInstance();
		if(property != null)
			property.clear();
		if(related != null)
			related.loadInstance();
		if(buff != null)
			buff.clear();
	}
	public Sound getSound(int id, int type) {
		return related.getSound(id, type);
	}
	public Vector2 getLastTownP() {
		return location.getLastTownP();
	}
	public boolean inInstance() {
		return !location.inTown() && !location.inAdmin();
	}
	public Vector2 getFix() {
		return fix;
	}
	public Vector2 getFix2() {
		return fix2;
	}
	public int getTown() {
		return location.getTown();
	}
	public void setTown(int town) {
		location.setTown(town);
	}
	public int getTownPart() {
		return location.getTownPart();
	}
	public void setTownPart(int townpart) {
		location.setTownPart(townpart);
	}
	public boolean havBuff(Buf buf) {
		if(buff == null)
			return true;
		return buff.havBuf(buf);
	}
	public void addBuff(Buf buf) {
		if(buff != null)
			buff.addBuff(buf);
	}
	public void removeBuff(Buf buf) {
		if(buff != null)
			buff.removeBuf(buf);
	}
	private void die() {
		if(!died) {
			died = true;
			buff.clear();
			getSalf(true).clear();
			getSalf(false).clear();
		}
	}
}