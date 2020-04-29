package dnf.character.part;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharSkill;

public abstract class Pose {
	protected GuPoubleXGame game = null;
	protected Vector2 move = null;
	protected float time = 0;
	protected float cdtime = 0;
	protected float duringtime = 0;
	protected boolean endPose = false;
	protected int index = -1;
	protected int forceLv = SetCharSkill.lv1;
	protected Buf buff = null;
	protected BuffActor buffactor = null;
	protected float buffduring = 0;
	protected int type = 0;
	protected int sound = 0;
	protected RoleAnimation anup = null;
	protected Array<RoleAnimation> an = null;
	protected RoleAnimation andown = null;
	protected Array<RoleAnimation> ex = null;
	protected Array<AttackJudge> aj = null;
	private Array<Sprite> selfR = null;
	private Array<Sprite> selfL = null;
	protected Array<Vector2> selfRP = null;
	protected Array<Vector2> selfLP = null;
	protected int anstart = 0;
	private int currentframe = -1;
	protected void getBody(Character ch, int index, boolean loop, boolean lose) {
		if(ch.inInstance()) {
			if(anstart+index != currentframe && !lose) {
				currentframe = anstart+index;
				selfRP = new Array<Vector2>();
				if(selfRP != null) {
					ch.setSelf(currentframe, ch.getSalf(true), selfRP);
					getSelf(ch);
				}
			}
			if(lose)
				loseBody(ch);
		}
	}
	public Pose(GuPoubleXGame game) {
		super();
		this.game = game;
	}
	public Pose(GuPoubleXGame game, Vector2 move) {
		super();
		this.game = game;
		this.move = move;
	}
	public Pose(GuPoubleXGame game, int forceLv) {
		super();
		this.game = game;
		this.forceLv = forceLv;
	}
	public Pose(GuPoubleXGame game, int forceLv, float cd) {
		super();
		this.game = game;
		this.forceLv = forceLv;
		this.cdtime = cd;
	}
	public Pose(GuPoubleXGame game, Array<RoleAnimation> an, int index, int forceLv) {
		super();
		this.game = game;
		this.ex = an;
		this.index = index;
		this.forceLv = forceLv;
	}
	public Pose(GuPoubleXGame game, int forceLv, Vector2 move) {
		super();
		this.game = game;
		this.forceLv = forceLv;
		this.move = move;
	}
	public float getCDtime() {
		return cdtime;
	}
	public float getDuringtime() {
		return duringtime;
	}
	public boolean end() {
		return endPose;
	}
	public void setMove(Pose p) {
		move = p.getMove();
	}
	public void setMove(Vector2 move) {
		this.move = move;
	}
	public Vector2 getMove() {
		return move;
	}
	public void setForce(int forceLv) {
		this.forceLv = forceLv;
	}
	public int getForce() {
		return forceLv;
	}
	public boolean canForce(Pose p) {
		if(forceLv >= p.getForce())
			return false;
		else
			return true;
	}
	public float canPress() {
		if(an != null)
			return an.get(0).getAnimationDuration()-time;
		else
			return 0;
	}
	public void resetSelf(Character ch) {
		ch.getSalf(true).clear();
		ch.getSalf(false).clear();
		if(selfLP != null)
			selfLP.clear();
		if(selfRP != null)
			selfRP.clear();
		if(game.getPref(SetBase.saveGeneral).getBoolean("DEBUG")) {
			if(selfL != null)
				selfL.clear();
			if(selfR != null)
				selfR.clear();
			selfL = null;
			selfR = null;
		}
	}
	protected void getSelf(Character ch) {
		ch.getSalf(false).clear();
		selfLP = new Array<Vector2>();
		for(int i = 0; i < ch.getSalf(true).size; i++) {
			Rectangle rg = new Rectangle(ch.getSalf(true).get(i));
			selfLP.add(new Vector2(-(ch.getSalf(true).get(i).getWidth()+selfRP.get(i).x), selfRP.get(i).y));
			ch.getSalf(false).add(rg);
		}
		if(!game.getPref(SetBase.saveGeneral).getBoolean("DEBUG"))
			return;
		if(selfR == null)
			selfR = new Array<Sprite>();
		if(selfL == null)
			selfL = new Array<Sprite>();
		selfR.clear();
		selfL.clear();
		for(int i = 0; i < ch.getSalf(true).size; i++) {
			Rectangle rg =  ch.getSalf(true).get(i);
			int w = (int) rg.getWidth();
			int h = (int) rg.getHeight();
			Pixmap pix = new Pixmap(w+1, h+1, Pixmap.Format.RGBA8888);
			pix.setColor(Color.GREEN);
			pix.drawLine(0, 0, 0, h);
			pix.drawLine(0, h, w, h);
			pix.drawLine(w, h, w, 0);
			pix.drawLine(w, 0, 0, 0);
			selfR.add(new Sprite(new Texture(pix)));pix.dispose();
			rg =  ch.getSalf(false).get(i);
			w = (int) rg.getWidth();
			h = (int) rg.getHeight();
			pix = new Pixmap(w+1, h+1, Pixmap.Format.RGBA8888);
			pix.setColor(Color.GREEN);
			pix.drawLine(0, 0, 0, h);
			pix.drawLine(0, h, w, h);
			pix.drawLine(w, h, w, 0);
			pix.drawLine(w, 0, 0, 0);
			selfL.add(new Sprite(new Texture(pix)));pix.dispose();
		}
	}
	protected void loseBody(Character ch) {
		if(ch.getSalf(true) != null)
			ch.getSalf(true).clear();
		if(selfRP != null)
			selfRP.clear();
		if(ch.getSalf(false) != null)
			ch.getSalf(false).clear();
		if(selfLP != null)
			selfLP.clear();
		if(game.getPref(SetBase.saveGeneral).getBoolean("DEBUG")) {
			if(selfR != null)
				selfR.clear();
			if(selfL != null)
				selfL.clear();
			selfR = null;
			selfL = null;
			selfRP = null;
			selfLP = null;
		}
	}
	protected void resetPoly(Character ch) {
		loseBody(ch);
		if(aj != null) {
			for(AttackJudge ajg : aj)
				ajg.dispose();
			aj.clear();
			aj = null;
		}
	}
	protected void drawPolygon(Character ch, Batch batch, float delta) {
		if(!ch.inInstance()) {
			resetPoly(ch);
			return;
		}
		if(ch.getSalf(true).size != 0)
			for(int i = 0; i < ch.getSalf(true).size; i++) {
				ch.getSalf(true).get(i).setPosition(ch.getX()+selfRP.get(i).x, ch.getY()+ch.getZ()+selfRP.get(i).y);
				ch.getSalf(false).get(i).setPosition(ch.getX()+selfLP.get(i).x, ch.getY()+ch.getZ()+selfLP.get(i).y);
			}
		if(aj != null)
			for(AttackJudge ajudge : aj)
				ajudge.update(batch, delta);
		if(game.getPref(SetBase.saveGeneral).getBoolean("DEBUG")) {
			if(ch.isRight()) {
				if(selfR != null && selfR.size != 0)
					for(int i = 0; i < ch.getSalf(true).size; i++)
						batch.draw(selfR.get(i), ch.getSalf(true).get(i).getX(), ch.getSalf(true).get(i).getY());
			}else {
				if(selfL != null && selfL.size != 0)
					for(int i = 0; i < ch.getSalf(false).size; i++)
						batch.draw(selfL.get(i), ch.getSalf(false).get(i).getX(), ch.getSalf(false).get(i).getY());
			}
		}
	}
	public void changeAnimation(Character ch) {}
	public void changeAnimation(Character ch, Pose p) {}
	public abstract void start(Character ch);
	public abstract void running(Character ch, Batch batch, float parentAlpha, float delta);
	public void end(Character ch) {
		if(buff == null) {
			an = null;
			ex = null;
		}
		anup = null;
		andown = null;
		index = -1;
		time = 0;
		duringtime = 0;
		move = null;
		endPose = false;
		currentframe = -1;
		anstart = 0;
		resetPoly(ch);
	}
}