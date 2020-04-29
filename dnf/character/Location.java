package dnf.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetTown;

public class Location implements Disposable {
	private boolean admin = true;
	private boolean town = false;
	private Preferences pref = null;
	private Vector2 p_admin = null;
	private Vector2 p_town = null;
	private Vector2 ver = null;
	private boolean right = true;
	private Body body = null;
	private Body body_g = null;
	private float freshheight = 0;
	private boolean destory = true;
	private float time = 0;
	private float z = 0;
	private boolean reseth = false;
	private boolean hitback = false;
	private float hittime = 0.1f;
	private int in_town = SetTown.elvengard;
	private int in_town_part = SetTown.hotel;
	private Array<Rectangle> salfL = null;
	private Array<Rectangle> salfR = null;
	public Location(Preferences pref) {
		super();
		this.pref = pref;
		salfL = new Array<Rectangle>();
		salfR = new Array<Rectangle>();
	}
	@Override
	public void dispose() {
	}
	public Location() {
		super();
	}
	public boolean inAdmin() {
		return admin;
	}
	public boolean inTown() {
		return town;
	}
	public void enterAdmin() {
		admin = true;
		town = false;
	}
	public void enterTown() {
		admin = false;
		town = true;
	}
	public void enterInstance(Vector2 ver) {
		p_town = ver;
	}
	public void enterInstance() {
		if(salfL != null)
			salfL.clear();
		if(salfR != null)
			salfR.clear();
		admin = false;
		town = false;
	}
	public Array<Rectangle> getSalf(boolean right) {
		if(right)
			return salfR;
		else
			return salfL;
	}
	public Vector2 getLastTownP() {
		return p_town;
	}
	public void setPosition(float x, float y) {
		if(admin)
			p_admin = new Vector2(x, y);
	}
	public void setTownWorld(World world, float x, float y, Vector2 ver) {
		if(this.ver == null)
			this.ver = ver;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set((x-ver.x/2)/SetBase.scale, (y-ver.y/2)/SetBase.scale);
		body = world.createBody(bodyDef);
		FixtureDef bodyFDef = new FixtureDef();
		PolygonShape bodyBox  = new PolygonShape();
		float l = 0.05f;
		bodyBox.setAsBox(l, l, new Vector2(0, 0), 0);
		bodyFDef.shape = bodyBox;
		bodyFDef.density = 0.1f;
		bodyFDef.restitution = 0.0f;
		bodyFDef.filter.groupIndex = -1;
		body.createFixture(bodyFDef);
		bodyBox.dispose();
		destory = false;
	}
	public void setInstanceWorld(World world, World world_g, float x, float y, Vector2 ver) {
		setTownWorld(world, x, y, ver);
		//g
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(1, 1);
		body_g = world_g.createBody(bodyDef);
		FixtureDef bodyFDef = new FixtureDef();
		PolygonShape bodyBox = new PolygonShape();
		bodyBox.setAsBox(1, 1, new Vector2(0, 0), 0);
		bodyFDef.shape = bodyBox;
		bodyFDef.density = 0.1f;
		bodyFDef.restitution = 0.0f;
		bodyFDef.filter.groupIndex = -1;
		body_g.createFixture(bodyFDef);
		bodyBox.dispose();
		destory = false;
	}
	public void clearWorld(World world, World world_g) {
		destory = true;
		time = 0;
		if(world != null && body != null) {
			world.destroyBody(body);
			body = null;
		}
		if(world_g != null && body_g != null) {
			world_g.destroyBody(body_g);
			body_g = null;
		}
	}
	public boolean isclearWorld() {
		return destory;
	}
	public void move(float x, float y) {
		if(!destory)
			if(!(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0 && x == 0 && y == 0))
				body.setLinearVelocity(new Vector2(x, y));
	}
	public void moveto(Vector2 moving) {
		if(!destory) {
			hitback = true;
			move(moving.x, moving.y);
		}
	}
	public void moveadd(float x, float y) {
		if(!destory)
			body.setTransform((getX()+x-ver.x/2)/SetBase.scale, (getY()+y-ver.y/2)/SetBase.scale, 0);
	}
	public void jump(float power) {
		if(!destory)
			body_g.setLinearVelocity(0, power);
	}
	public Vector2 getXYLineV() {
		if(!destory)
			return body.getLinearVelocity();
		else
			return null;
	}
	public void setZLineV(Vector2 v) {
		if(!destory)
			body_g.setLinearVelocity(v);
	}
	public Vector2 getZLineV() {
		if(!destory)
			return body_g.getLinearVelocity();
		else
			return null;
	}
	public float getX() {
		if(admin)
			return p_admin.x;
		else if(destory)
			return 0;
		else if(town)
			return body.getPosition().x*SetBase.scale+ver.x/2;
		else
			return body.getPosition().x*SetBase.scale+ver.x/2;
	}
	public float getY() {
		if(admin)
			return p_admin.y;
		else if(destory)
			return 0;
		else if(town)
			return body.getPosition().y*SetBase.scale+ver.y/2;
		else
			return body.getPosition().y*SetBase.scale+ver.y/2;
	}
	public float getZ() {
		if(admin)
			return 0;
		else if(destory)
			return 0;
		else if(town)
			return 0;
		else {
			if(reseth)
				return z;
			else {
				float h = (body_g.getPosition().y-freshheight)*SetBase.scale;
				return ((int) (h*10))/10.0f;
			}
		}
	}
	public void setZ(float z, boolean reset) {
		reseth = reset;
		this.z = z;
	}
	public Body getBody() {
		return body;
	}
	public int getTown() {
		return in_town;
	}
	public void setTown(int in_town) {
		this.in_town = in_town;
		pref.putInteger("TOWN", in_town);
		pref.flush();
	}
	public int getTownPart() {
		return in_town_part;
	}
	public void setTownPart(int in_town_part) {
		this.in_town_part = in_town_part;
	}
	public boolean isRight() {
		return right;
	}
	public void setDirect(boolean right) {
		this.right = right;
	}
	public void update(float delta) {
		if(body_g != null && !destory && !admin && !town) {
			if(body_g.getLinearVelocity().y == 0) {
				if(time >= Gdx.graphics.getDeltaTime()*3);
					freshheight = body_g.getPosition().y;
				time += delta;
			}else
				time = 0;
			if(hitback) {
				hittime -= delta;
				if(hittime <= 0) {
					hittime = 0.1f;
					hitback = false;
					move(0, 0);
				}
			}
		}
	}
}