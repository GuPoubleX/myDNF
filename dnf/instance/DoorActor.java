package dnf.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class DoorActor extends Actor {
	private StageInstance main = null;
	private Array<Character> team = null;
	private Array<Character> emeny = null;
	private Sprite frame = null;
	private Sprite dor = null;
	private Sprite blink = null;
	private Sprite blink2 = null;
	private Vector2 dor_offset = null;
	private Vector2 blk_offset = null;
	private Vector2 blk_offset2 = null;
	private Vector2 center = null;
	private boolean alldie = false;
	private float time = 0;
	private int toid = 0;
	private int nextdor = 0;
	private float change = 0;
	private boolean allin = false;
	private World world = null;
	private World world_g = null;
	public DoorActor(StageInstance main, World world, World world_g, Sprite frame, Sprite dor, Sprite blink, Sprite blink2, Vector2 dor_offset,
			Vector2 blk_offset, Vector2 blk_offset2, Array<Character> team, Array<Character> emeny, int toid, int nextdor, Vector2 center) {
		super();
		this.main = main;
		if(frame != null)
			this.frame = new Sprite(frame);
		this.dor = new Sprite(dor);
		this.blink = new Sprite(blink);
		this.blink2 = new Sprite(blink2);
		this.team = team;
		this.emeny = emeny;
		this.dor_offset = dor_offset;
		this.blk_offset = blk_offset;
		this.blk_offset2 = blk_offset2;
		this.center = center;
		this.toid = toid;
		this.nextdor = nextdor;
		this.world = world;
		this.world_g = world_g;
	}
	public void reset() {
		allin = false;
		change = 0;
	}
	public int toDoor() {
		return nextdor;
	}
	public Vector2 getCenter() {
		return center;
	}
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(frame != null)
			frame.setPosition(x, y);
		dor.setPosition(x+dor_offset.x, y+dor_offset.y);
		blink.setPosition(x+blk_offset.x, y+blk_offset.y);
		if(blink2 != null)
			blink2.setPosition(x+blk_offset2.x, y+blk_offset2.y);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(alldie) {
			time += delta;
			if(time >= 0.8f)
				time -= 0.8f;
		}
		boolean die = true;
		for(Character ch : emeny)
			if(ch.getProperty("HP") > 0) {
				die = false;
				break;
			}
		if(die != alldie)
			alldie = die;
		if(allin && !main.getMain().bossAllDie())
			change += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(frame != null)
			frame.draw(batch, parentAlpha);
		if(!alldie)
			dor.draw(batch, parentAlpha);
		else {
			if(time >= 0.4f) {
				blink.draw(batch, parentAlpha);
				if(blink2 != null)
					blink2.draw(batch, parentAlpha);
			}
			boolean in = true;
			for(Character ch : team) {
				int dx = (int) (ch.getX() - center.x);
				int dy = (int) ((ch.getY() - center.y)*SetBase.fixradius);
				if(dx*dx+dy*dy >= 20*20) {
					in = false;
					break;
				}
			}
			if(in != allin)
				allin = in;
			if(change >= 0.5f && toid != 0) {
				destoryWorld();
				for(Character ch : team) {
					ch.getShadow().remove();
					ch.remove();
				}
				main.getMain().changeStage(toid, this);
			}
		}
	}
	public void destoryWorld() {
		if(team != null) {
			for(Character c : team)
				c.clearWorld(world, world_g);
		}
	}
}