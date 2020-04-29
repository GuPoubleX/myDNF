package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import dnf.author.AnActor2;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetSwordmanSkill;

public class HundredswordCircle extends Actor {
	private Array<Sprite> frame = null;
	private GuPoubleXGame game = null;
	private float time = 0;
	private float addtime = 0;
	private Vector3 center = null;
	private Vector3 first = null;
	private Interpolation ip = Interpolation.exp5In;
	private float x = 0;
	private float y = 0;
	public HundredswordCircle(GuPoubleXGame game, Vector3 center, Vector3 first) {
		super();
		this.center = center;
		this.first = first;
		this.game = game;
		y = (float) (Math.sin(Math.PI/180*(((int) ((time/0.8f)*360+first.x))))*center.z/3.4f)+center.y;
		x = (float) (Math.cos(Math.PI/180*(((int) ((time/0.8f)*360+first.x))))*center.z)+center.x;
		setPosition(x, y);
		frame = new Array<Sprite>();
		for(int i = 0; i < 3; i++) {
			Sprite sp = game.getImg(SetSwordmanSkill.hundredsword, "sword_light.img").getIndex(i);
			frame.add(sp);
		}
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		addtime += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float progress = Math.min(1, time/1);
		float alpha = ip.apply(progress);
		
		y = (float) (Math.sin(Math.PI/180*(((int) ((time/1.25f)*360+first.x))))*center.z*(1-alpha)/3.4f)+center.y;
		x = (float) (Math.cos(Math.PI/180*(((int) ((time/1.25f)*360+first.x))))*center.z*(1-alpha))+center.x;
		setPosition(x, y);
		
		if(addtime >= 0.01f) {
			addtime -= 0.01f;
			TextureRegion tr[] = new TextureRegion[4];
			for(int i = 0; i < tr.length; i++)
				tr[i] = game.getImg(SetSwordmanSkill.hundredsword, "sword_light_tail.img").getIndex(i);
			AnActor2 aa = new AnActor2(0.3f, tr);
			aa.setPosition(x, y+first.z);
			getParent().addActor(aa);
		}
		for(Sprite sp : frame) {
			sp.setOrigin(sp.getWidth()/2, sp.getHeight()/2);
			sp.setScale(1+alpha/3, 1+alpha/3);
			sp.setRotation(360*alpha);
			sp.setPosition(getX()-sp.getWidth()/2, getY()-sp.getHeight()/2+first.z);
			sp.draw(batch, parentAlpha);
		}
		if(alpha >= 1)
			remove();
	}
}