package dnf.instance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import dnf.author.BGImage;
import dnf.author.GPXCamera;
import dnf.author.Zindex;
import dnf.author.GPXStage;
import dnf.character.Character;
import dnf.character.ShadowImage;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;

public class StageInstance extends GPXStage {
	private ScreenInstance main = null;
	private Vector2 size = null;
	private Vector2 ver = null;
	private Vector2 loc = null;
	private Character character = null;
	private Array<Character> team = null;
	private Array<Character> screenemeny = null;
	private Array<Character> emeny = null;
	private Array<Character> boss = null;
	private Array<DoorActor> dorlist = null;
	private Box2DDebugRenderer mDebugRender = null;
	private float size_set[] = null;
	private GPXCamera camera = null;
	private float accumulator = 0.0f;
	private Image background = null;
	private Image background2 = null;
	private Image background3 = null;
	private int bg1_dh = 0;
	private int bg2_dh = 0;
	private int bg3_dh = 0;
	private int dorloc = -1;
	private Preferences pref = null;
	private int mapain = 0;
	private boolean bosdie = false;
	public void resetScale(float scale) {
		this.scale = scale;
	}
	public Group getGroup() {
		return group;
	}
	public World getWorld() {
		return world;
	}
	public World getGWorld() {
		return world_g;
	}
	private void doPhysicsStep(float delta) {
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:
			case Desktop:
				if(world == null || world_g == null)
					return;
				float frameTime = Math.min(delta, SetBase.max_step);
				accumulator += frameTime;
				while(accumulator >= SetBase.step) {
					world.step(SetBase.step, 10, 4);
					world_g.step(SetBase.step, 10, 4);
					accumulator -= SetBase.step;
				}
				if(accumulator != 0.0f) {
					world.step(accumulator, 10, 4);
					world_g.step(accumulator, 10, 4);
					accumulator = 0.0f;
				}
			case WebGL:break;
			default:break;
		}
	}
	public StageInstance(ScreenInstance main, String pref, Vector2 ver, Vector2 loc, Batch batch, float scale, Array<Character> team, Array<Character> emeny, int lv) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		this.main = main;
		this.ver = ver;
		this.scale = scale;
		this.team = team;
		this.character = team.get(0);
		this.screenemeny = emeny;
		this.emeny = new Array<Character>();
		this.boss = new Array<Character>();
		this.dorlist = new Array<DoorActor>();
		this.loc = loc;
		this.pref = main.getGame().getPref(pref);
		mapain = lv;
		group = new Group();
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG"))
			mDebugRender = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		world_g = new World(new Vector2(0, -10), true);
		initGworld();
		camera = new GPXCamera(getCamera());
		setInfo();
		setFloor();
		setGround();
		setBackgroun();
		setMiddle();
		setFront();
		camera.init(character, ver.x, ver.y, size.x, size.y);
	}
	public StageInstance(ScreenInstance main, String pref, Vector2 ver, int dor, Batch batch, float scale, Array<Character> team, Array<Character> emeny, int lv) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		this.main = main;
		this.ver = ver;
		this.scale = scale;
		this.team = team;
		this.character = team.get(0);
		this.screenemeny = emeny;
		this.emeny = new Array<Character>();
		this.boss = new Array<Character>();
		this.dorlist = new Array<DoorActor>();
		this.dorloc = dor;
		this.pref = main.getGame().getPref(pref);
		mapain = lv;
		group = new Group();
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG"))
			mDebugRender = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		world_g = new World(new Vector2(0, -10), true);
		initGworld();
		camera = new GPXCamera(getCamera());
		setInfo();
		setFloor();
		setGround();
		setBackgroun();
		setMiddle();
		setFront();
		camera.init(character, ver.x, ver.y, size.x, size.y);
	}
	private void imgAction(Image image, String c[]) {
		if(c.length >= 7)
			if(c[6].equals("true"))
				updown(image, ((int) (Math.random()*100))%2==0?true:false);
	}
	private void updown(Image image, boolean up) {
		MoveToAction moveup = Actions.moveTo(image.getX(), image.getY()+3, 1.0f);
		MoveToAction movedown = Actions.moveTo(image.getX(), image.getY()-3, 1.0f);
		MoveToAction movebefore = Actions.moveTo(image.getX(), image.getY()+3, 1.0f);
		MoveToAction moveafter = Actions.moveTo(image.getX(), image.getY()-3, 1.0f);
		SequenceAction sequence = Actions.sequence(up?movebefore:moveafter, up?moveafter:movebefore);
		RepeatAction repeat = Actions.forever(sequence);
		SequenceAction alpha1 = Actions.sequence(up?moveup:movedown, repeat);
		image.addAction(alpha1);
	}
	private void initGworld() {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyType.StaticBody;
		floorDef.position.set(1, -1);
		Body floor_g = world_g.createBody(floorDef);
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(3, 1);
		FixtureDef floorFixDef = new FixtureDef();
		floorFixDef.shape = polygon;
		floor_g.createFixture(floorFixDef);
		polygon.dispose();
	}
	public void destoryWorld() {
		if(team != null) {
			for(Character c : team)
				c.clearWorld(world, world_g);
		}
		if(emeny != null) {
			for(Character c : emeny)
				c.clearWorld(world, world_g);
			emeny.clear();
		}
	}
	public void resetLoc(int dor) {
		float x = dorlist.get(dor-1).getCenter().x;
		float y = dorlist.get(dor-1).getCenter().y;
		for(Character ch : team) {
			group.addActor(ch.getShadow());
			ch.set("stay", null);
			ch.setInstanceWorld(world, world_g, x, y, ver);
			group.addActor(ch);
		}
		for(DoorActor d : dorlist)
			d.reset();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		doPhysicsStep(delta);
	}
	@Override
	public void draw() {
		super.draw();
		setBackgroun();
		Sort.instance().sort(group.getChildren(), new Zindex());
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG") && mDebugRender != null)
			mDebugRender.render(world, camera.getCamera().combined);
		for(Character c : emeny)
			if(c.getEmenyType() == SetCharProperty.boss && !bosdie)
				if(c.getProperty("HP") <= 0) {
					bosdie = true;
					bossDIE(c);
				}
	//	if(character.getHMP(true) <= 0) {
	//		for(Character c : team)
	//			if(c.getHMP(true) > 0)
	//				camera.init(c, ver.x, ver.y, size.x, size.y);
	//	}else
			camera.update();
	//	if(world != null)
	//		System.out.println(character.getX()+","+character.getY());
	}
	private void setBackgroun() {
		if(background != null)
			background.setPosition(
					BGImage.set(character.getX()-background.getWidth()*background.getScaleX()*0.5f,
							0.0f, size.x, background.getWidth()*background.getScaleX(), ver.x),
					size.y-background.getHeight()*background.getScaleY()+bg1_dh);
		if(background2 != null)
			background2.setPosition(
					BGImage.set(character.getX()-background2.getWidth()*background2.getScaleX()*0.5f,
							0.0f, size.x, background2.getWidth()*background2.getScaleX(), ver.x),
					size.y-background2.getHeight()*background2.getScaleY()+bg2_dh);
		if(background3 != null)
			background3.setPosition(
					BGImage.set(character.getX()-background3.getWidth()*background3.getScaleX()*0.5f,
							0.0f, size.x, background3.getWidth()*background3.getScaleX(), ver.x),
					size.y-background3.getHeight()*background3.getScaleY()+bg3_dh);
	}
	private void setInfo() {
		int w = pref.getInteger("WIDTH");
		int h = pref.getInteger("HEIGHT");
		size = new Vector2(w, h);
		String s = pref.getString("BLOCK");
		String c[] = s.split(",");
		size_set = new float[c.length];
		float x_l = -1;
		float x_r = -1;
		for(int i = 0; i < c.length; i++) {
			size_set[i] = Integer.parseInt(c[i]);
			if(x_l == -1 && x_r == -1) {
				x_l = size_set[0];
				x_r = size_set[0];
			}else if(i%2 == 0) {
				if(size_set[i] < x_l)
					x_l = size_set[i];
				if(size_set[i] > x_r)
					x_r = size_set[i];
			}
		}
		sizeVec = new Vector2(x_l, x_r);
	}
	private void setFloor() {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyType.StaticBody;
		floorDef.position.set(-ver.x/SetBase.scale/2, -ver.y/SetBase.scale/2);
		floorDef.fixedRotation = true;
		Body floor = world.createBody(floorDef);
		ChainShape chainShape = new ChainShape();
		for(int i = 0; i < size_set.length; i++)
			size_set[i] /= SetBase.scale;
		chainShape.createChain(size_set);
		FixtureDef floorFDef = new FixtureDef();
		floorFDef.shape = chainShape;
		floor.createFixture(floorFDef);
		chainShape.dispose();
	}
	private void setGround() {
		for(int i = 0; i < 3; i++) {
			String s = pref.getString("BACKGROUND"+i);
			String c[] = s.split(",");
			if(i == 0 && c.length != 1) {
				background = new Image(main.getGame().getImg(main.getMappath(), c[0]+".img").getIndex(Integer.parseInt(c[1])));
				background.setScale(Float.parseFloat(c[2]), Float.parseFloat(c[3]));
				bg1_dh = Integer.parseInt(c[5]);
				addActor(background);
			}else if(i == 1 && c.length != 1) {
				background2 = new Image(main.getGame().getImg(main.getMappath(), c[0]+".img").getIndex(Integer.parseInt(c[1])));
				background2.setScale(Float.parseFloat(c[2]), Float.parseFloat(c[3]));
				bg2_dh = Integer.parseInt(c[5]);
				addActor(background2);
			}else if(c.length != 1) {
				background3 = new Image(main.getGame().getImg(main.getMappath(), c[0]+".img").getIndex(Integer.parseInt(c[1])));
				background3.setScale(Float.parseFloat(c[2]), Float.parseFloat(c[3]));
				bg3_dh = Integer.parseInt(c[5]);
				addActor(background3);
			}
		}
		for(int i = 0; i < 100; i++) {
			String s = pref.getString("BIMAGE"+i);
			String c[] = s.split(",");
			if(c.length != 1) {
				Image img = new Image(main.getGame().getImg(main.getMappath(), c[0]+".img").getIndex(Integer.parseInt(c[1])));
				img.setScale(Float.parseFloat(c[2]), Float.parseFloat(c[3]));
				img.setPosition(Integer.parseInt(c[4]), Integer.parseInt(c[5]));
				addActor(img);
				imgAction(img, c);
			}
		}
	}
	private void setMiddle() {
		for(int i = 0; i < 4; i++) {
			String s = pref.getString("DOOR"+i);
			String c[] = s.split(",");
			if(c.length != 1) {
				int k = 8;
				Sprite cas = null;
				if(!c[k].equals("-1"))
					cas = main.getGame().getImg(main.getMappath(), c[k]+".img").getIndex(Integer.parseInt(c[k+1]));
				Sprite dor = main.getGame().getImg(main.getMappath(), c[k+2]+".img").getIndex(Integer.parseInt(c[k+3]));
				Sprite blk = main.getGame().getImg(main.getMappath(), c[k+4]+".img").getIndex(Integer.parseInt(c[k+5]));
				Vector2 dor_offset = new Vector2(Integer.parseInt(c[2]), Integer.parseInt(c[3]));
				Vector2 blk_offset = new Vector2(Integer.parseInt(c[4]), Integer.parseInt(c[5]));
				Vector2 blk_offset2 = new Vector2(Integer.parseInt(c[6]), Integer.parseInt(c[7]));
				Vector2 center = new Vector2(Integer.parseInt(c[k+8]), Integer.parseInt(c[k+9]));
				DoorActor d = new DoorActor(this, world, world_g, cas, dor, blk, blk, dor_offset, blk_offset, blk_offset2, team, emeny,
						Integer.parseInt(c[k+6]), Integer.parseInt(c[k+7]), center);
				d.setPosition(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
				group.addActor(d);
				dorlist.add(d);
			}
		}
		if(dorloc == -1)
			for(Character ch : team) {
				group.addActor(ch.getShadow());
				ch.set("stay", null);
				ch.setInstacne(main);
				ch.setInstanceWorld(world, world_g, loc.x, loc.y, ver);
				group.addActor(ch);
			}
		else {
			float x = dorlist.get(dorloc-1).getCenter().x;
			float y = dorlist.get(dorloc-1).getCenter().y;
			for(Character ch : team) {
				group.addActor(ch.getShadow());
				ch.set("stay", null);
				ch.setInstacne(main);
				ch.setInstanceWorld(world, world_g, x, y, ver);
				group.addActor(ch);
			}
		}
		for(int i = 0; i < 10; i++) {
			String s = pref.getString("EMENY"+i);
			String c[] = s.split(",");
			if(c.length != 1) {
				String name = c[2];
				Character ctr = null;
				if(isSwordman(name))
					ctr = new Swordman(main.getGame(), name, SetCharProperty.emeny);
				else if(isFighter(name))
					ctr = new Swordman(main.getGame(), name, SetCharProperty.emeny);
				else if(isGunner(name))
					ctr = new Swordman(main.getGame(), name, SetCharProperty.emeny);
				else if(isMage(name))
					ctr = new Swordman(main.getGame(), name, SetCharProperty.emeny);
				else if(isPriest(name))
					ctr = new Swordman(main.getGame(), name, SetCharProperty.emeny);
				if(ctr != null) {
					if(Integer.parseInt(c[3]) == SetCharProperty.boss) {
						boss.add(ctr);
						ctr.setShadow(new ShadowImage(main.getGame().getImg(SetImg.instance, "auramaster.img").getIndex(Integer.parseInt(c[5])), ctr));
					}
					ctr.enterInstance();
					ctr.getAnimation();
					ctr.loadSkill();
					ctr.setEmenyType(Integer.parseInt(c[3]));
					group.addActor(ctr.getShadow());
					ctr.set("stay", null);
					ctr.changeProperty("LV", Integer.parseInt(c[4]), false, false);
					//ctr.enStrong(mapain);
					ctr.setInstacne(main);
					ctr.setInstanceWorld(world, world_g, Integer.parseInt(c[0]), Integer.parseInt(c[1]), ver);
					group.addActor(ctr);
					emeny.add(ctr);
					screenemeny.add(ctr);
				}
			}
		}
		addActor(group);
	}
	private boolean isSwordman(String name) {
		boolean in = false;
		for(String str : SetCharProperty.swordman_names)
			if(name.equals(str)) {
				in = true;
				break;
			}
		return in;
	}
	private boolean isFighter(String name) {
		boolean in = false;
		for(String str : SetCharProperty.fighter_names)
			if(name.equals(str)) {
				in = true;
				break;
			}
		return in;
	}
	private boolean isGunner(String name) {
		boolean in = false;
		for(String str : SetCharProperty.gunner_names)
			if(name.equals(str)) {
				in = true;
				break;
			}
		return in;
	}
	private boolean isMage(String name) {
		boolean in = false;
		for(String str : SetCharProperty.mage_names)
			if(name.equals(str)) {
				in = true;
				break;
			}
		return in;
	}
	private boolean isPriest(String name) {
		boolean in = false;
		for(String str : SetCharProperty.priest_names)
			if(name.equals(str)) {
				in = true;
				break;
			}
		return in;
	}
	private void setFront() {
		for(int i = 0; i < 100; i++) {
			String s = pref.getString("FIMAGE"+i);
			String c[] = s.split(",");
			if(c.length != 1) {
				Image img = new Image(main.getGame().getImg(main.getMappath(), c[0]+".img").getIndex(Integer.parseInt(c[1])));
				img.setScale(Float.parseFloat(c[2]), Float.parseFloat(c[3]));
				img.setPosition(Integer.parseInt(c[4]), Integer.parseInt(c[5]));
				addActor(img);
				imgAction(img, c);
			}
		}
	}
	private void bossDIE(Character boss) {
		actionimage.clear();
		float degree = (float) (Math.random()*360);
		int r = 200;
		for(int i = 0; i < 8; i++) {
			Image img = new Image(main.getGame().getImg(SetImg.instance, "finish.img").getIndex(0));
			img.setOrigin(0, img.getHeight()/2);
			img.setRotation(45*i+degree);
			img.setPosition(boss.getX()+(int) (Math.cos((45*i+degree)*(Math.PI/180.0f))*r),
					boss.getY()+boss.getFix().y/2+(int) (Math.sin((45*i+degree)*(Math.PI/180.0f))*r));
			addActor(img);
			actionimage.add(img);
		}
	}
	@Override
	public void dispose() {
		destoryWorld();
		if(world != null) {
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for(Body body : bodies)
				world.destroyBody(body);
			bodies.clear();
			world.dispose();
			world = null;
		}
		if(world_g != null) {
			Array<Body> bodies = new Array<Body>();
			world_g.getBodies(bodies);
			for(Body body : bodies)
				world_g.destroyBody(body);
			bodies.clear();
			world_g.dispose();
			world_g = null;
		}
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG") && mDebugRender != null)
			mDebugRender.dispose();
		super.dispose();
	}
	public ScreenInstance getMain() {
		return main;
	}
}