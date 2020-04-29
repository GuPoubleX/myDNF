package dnf.town;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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
import dnf.character.other.NPC;
import dnf.gupoublex.set.SetBase;

public abstract class StageTown extends GPXStage {
	protected float bs = 1.25f;
	protected StageTown context = null;
	protected ScreenTown main = null;
	protected TextureAtlas atlas = null;
	protected Vector2 size = null;
	protected Vector2 ver = null;
	protected Array<Character> all = null;
	protected Character ch = null;
	protected Vector2 charLocation = null;
	private Box2DDebugRenderer mDebugRender = null;
	protected float size_set[] = null;
	private GPXCamera camera = null;
	private float accumulator = 0.0f;
	protected Image background = null;
	protected boolean changestage = false;
	protected HashMap<String, String> npc = null;
	public void resetScale(float scale) {
		this.scale = scale;
	}
	private void doPhysicsStep(float delta) {
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:
				//world.step(delta, 10, 4);break;
			case Desktop:
				float frameTime = Math.min(delta, SetBase.max_step);
				accumulator += frameTime;
				while(accumulator >= SetBase.step) {
					world.step(SetBase.step, 10, 4);
					accumulator -= SetBase.step;
				}
				if(accumulator != 0.0f) {
					world.step(accumulator, 10, 4);
					accumulator = 0.0f;
				}
			case WebGL:break;
			default:break;
		}
	}
	private float deltaTime(float delta) {
		switch(Gdx.app.getType()) {
			case Android:break;
			case iOS:break;
			case Desktop:delta = SetBase.step;break;
			case WebGL:break;
			default:break;
		}
		return delta;
	}
	public StageTown(ScreenTown main, Vector2 ver, Batch batch, float scale,
			Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(new ScalingViewport(Scaling.fit, ver.x, ver.y), batch);
		context = this;
		this.main = main;
		this.ch = ch;
		this.ver = ver;
		this.scale = scale;
		this.all = all;
		this.charLocation = charLocation;
		this.npc = npc;
		group = new Group();
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG"))
			mDebugRender = new Box2DDebugRenderer();
		world = new World(new Vector2(0.0f, 0.0f), true);
		camera = new GPXCamera(getCamera());
		setInfo();
		setFloor();
		setGround();
		setBackground();
		setMiddle();
		setFront();
		camera.init(ch, ver.x, ver.y, size.x, size.y);
	}
	public void destoryWorld() {
		if(all != null) {
			for(Character c : all)
				c.clearWorld(world, null);
		}
		if(world != null)
			world.dispose();
		world = null;
	}
	@Override
	public void act(float delta) {
		delta = deltaTime(delta);
		super.act(delta);
		doPhysicsStep(delta);
	}
	@Override
	public void draw() {
		super.draw();
		setBackground();
		Sort.instance().sort(group.getChildren(), new Zindex());
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG") && mDebugRender != null)
			mDebugRender.render(world, camera.getCamera().combined);
		camera.update();
		change();
	//	if(world != null)
	//		System.out.println(all.get(0).getX()+","+all.get(0).getY());
	}
	private void setBackground() {
		if(background != null)
			background.setPosition(
					BGImage.set(all.get(0).getX()-background.getWidth()*background.getScaleX()*0.5f,
							0.0f, size.x, background.getWidth()*background.getScaleX(), ver.x),
					size.y-background.getHeight());
	}
	public abstract void setInfo();
	public void setFloor() {
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
	public abstract void setGround();
	public void setMiddle() {
		for(Character ch : all) {
			group.addActor(ch.getShadow());
			ch.set("stay", null);
			ch.setTownWorld(world, charLocation.x, charLocation.y, ver);
			group.addActor(ch);
		}
		addActor(group);
	}
	public abstract void setFront();
	public abstract void change();
	@Override
	public void dispose() {
		destoryWorld();
		for(Actor actor :group.getChildren())
			if(actor instanceof NPC)
				((NPC) actor).dispose();
		if(main.getGame().getPref(SetBase.saveGeneral).getBoolean("DEBUG") && mDebugRender != null)
			mDebugRender.dispose();
		super.dispose();
	}
}