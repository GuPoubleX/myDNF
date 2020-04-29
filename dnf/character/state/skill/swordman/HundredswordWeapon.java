package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetSwordmanSkill;

public class HundredswordWeapon extends Actor {
	private GuPoubleXGame game = null;
	private Array<SpriteTexture> frame = null;
	private RoleAnimation an = null;
	private RoleAnimation andown = null;
	private float time = 0;
	private float time2 = 0;
	private boolean down = false;
	private Vector3 center = null;
	private Vector3 first = null;
	private Interpolation ip = Interpolation.exp5In;
	private float x = 0;
	private float y = 0;
	private int pose = 0;
	private float alpha = 0;
	private float alpha2 = 0;
	private boolean right = true;
	private boolean getAn = false;
	private boolean show = false;
	private float showtime = 0;
	private float weapontime = 0;
	private RoleAnimation exan = null;
	private float delta = 0;
	private int rotate = 0;
	public HundredswordWeapon(GuPoubleXGame game, Vector3 center, Vector3 first, int type, boolean down) {
		super();
		this.center = center;
		this.first = first;
		this.down = down;
		this.game = game;
		setPosition(center.x, center.y);
		int r = ((int) (Math.random()*100))%2;
		rotate = ((int) (Math.random()*20))*(r==0?1:-1);
		int weapon = 14;
		if(type == 0) {
			weapon = 0;
			pose = 31;
		}else if(type == 1) {
			weapon = 1;
			pose = 1;
		}else if(type == 2) {
			weapon = 2;
			pose = 29;
		}else if(type == 3) {
			weapon = 3;
			pose = 0;
		}else {
			weapon = 4;
			pose = 30;
		}
		frame = new Array<SpriteTexture>();
		frame.add(game.getImg(SetSwordmanSkill.hundredsword, "sword0"+weapon+".img").getIndexST(1));
		frame.add(game.getImg(SetSwordmanSkill.hundredsword, "sword0"+weapon+".img").getIndexST(0));
		frame.add(game.getImg(SetSwordmanSkill.hundredsword, "sword0"+weapon+".img").getIndexST(1));
		frame.add(game.getImg(SetSwordmanSkill.hundredsword, "sword0"+weapon+".img").getIndexST(0));
		Img img = game.getImg(SetSwordmanSkill.hundredsword, "sword_back_glow.img");
		SpriteTexture re[] = new SpriteTexture[img.getCount()];
		for(int i = 0; i < re.length; i++)
			re[i] = img.getIndexST(i);
		an = new RoleAnimation(0.5f/re.length, re);
		img = game.getImg(SetSwordmanSkill.hundredsword, "floor_glow.img");
		re = new SpriteTexture[img.getCount()-1];
		for(int i = 0; i < re.length; i++)
			re[i] = img.getIndexST(i);
		andown = new RoleAnimation(0.3f/re.length, re);
	}
	public void show(float excutetime) {
		if(!show) {
			show = true;
			exan.setFrameDuration(excutetime);
			weapontime = excutetime;
		}
	}
	public boolean isRight() {
		return right;
	}
	public int getPose() {
		return pose;
	}
	public float getZ() {
		return down?0:first.z;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		this.delta = delta;
		if(alpha == 1 && down)
			time2 += delta;
		if(show)
			showtime += delta;
		if(showtime >= 0.3f)
			remove();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		alpha = ip.apply(Math.max(0, Math.min(1, time/1)));
		if(alpha < 1) {
			y = (float) (Math.sin(Math.PI/180*(((int) ((time/0.8f)*360+first.x))))*center.z*alpha/3.4f)+center.y;
			x = (float) (Math.cos(Math.PI/180*(((int) ((time/0.8f)*360+first.x))))*center.z*alpha)+center.x;
			setPosition(x, y);
		}else {
			if(!getAn) {
				getAn = true;
				if(getX() < center.x)
					right = true;
				else
					right = false;
				Img img = game.getImg(SetSwordmanSkill.hundredsword, down?"exp2.img":"exp1.img");
				SpriteTexture exre[] = new SpriteTexture[6];
				for(int i = 0; i < exre.length; i++) {
					if(i == exre.length-1)
						exre[i] = new SpriteTexture(new Texture(640, 480, Format.RGBA8888));
					else
						exre[i] = img.getIndexST(i);
				}
				exan = new RoleAnimation(0.3f/exre.length, exre);
			}
		}
		if(time2 >= 0.5f) {
			alpha2 = ip.apply(Math.max(0, Math.min(1, (time2-0.5f)/0.1f)));
			if(weapontime <= showtime/2)
				andown.draw(false, false, batch, delta, parentAlpha, getX(), getY(), new Vector2(215, 140), new Vector2(0, 0), false);
		}
		if(alpha2 == 0 && weapontime <= showtime/2)
			an.draw(batch, delta, parentAlpha, getX()-an.getWidth()/2, getY()+first.z-20, true, false, false);
		if(down && alpha2 > 0) {
			if(alpha2 < 1)
				for(int i = 0; i < frame.size/2; i++) {
					SpriteTexture s = frame.get(i+frame.size/2);
					float y = first.z*2/3-(s.getVWH().y-s.getXY().y-s.getWH().y);
					SpriteTexture sp = new SpriteTexture(new TextureRegion(s.getSprite().getTexture(),
							(int) s.getSprite().getWidth(), (int) (s.getSprite().getHeight()-y*alpha2)));
					frame.set(i, sp);
				}
			if(weapontime <= showtime/2)
				for(int i = 0; i < frame.size/2; i++) {
					SpriteTexture sp = frame.get(i);
					Sprite sprite = new Sprite(sp.getSprite());
					sprite.setPosition(getX()-sprite.getWidth()/2+2, getY()+(first.z-50)*(1-alpha2));
					sprite.draw(batch, parentAlpha);
				}
		}else
			if(weapontime <= showtime/2)
				for(int i = 0; i < frame.size/2; i++) {
					SpriteTexture sp = frame.get(i);
					Sprite sprite = new Sprite(sp.getSprite());
					sprite.setOrigin(sp.getWH().x-(sp.getVWH().x/2-sp.getXY().x), sp.getWH().y-(sp.getVWH().y/2-sp.getXY().y));
					sprite.setRotation(rotate);
					sprite.setPosition(getX()-sp.getVWH().x/2+sp.getXY().x, getY()-sp.getVWH().y+sp.getXY().y+first.z-100+210);
					sprite.draw(batch, parentAlpha);
				}
		if(show) {
		//	Sprite sp = new Sprite(getAnimation(exan, false, showtime));
		//	sp.setPosition(getX()+(right?-(down?170:150):((down?170:150)-sp.getWidth())), getY()+(down?0-10:first.z)-230);
		//	sp.draw(batch, parentAlpha);
			exan.draw(false, false, batch, delta, parentAlpha,
					getX(), getY()+(down?150:-500), new Vector2(0, 0), new Vector2(0, 0), center.x>getX()?true:false);
		}
	}
}