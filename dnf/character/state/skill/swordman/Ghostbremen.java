package dnf.character.state.skill.swordman;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.part.AddActor;
import dnf.character.part.BuffActor;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetSwordmanSkill;

public class Ghostbremen extends BuffActor {
	private boolean right = true;
	private float delta = 0;
	private RoleAnimation an1 = null;
	//private RoleAnimation an2 = null;
	public Ghostbremen(Character ch, float duringtime) {
		super(ch, 1, new Vector2(100, 0), new Vector2(500, 0), duringtime, true);
		right = ch.isRight();
		buffstr = "dnf.character.state.buff.swordman.Bremen";
		if(ch.inInstance()) {
			Img img1 = ch.getGame().getImg(SetSwordmanSkill.ghost, "ghostbremen1.img");
			Img img2 = ch.getGame().getImg(SetSwordmanSkill.ghost, "summonarea.img");
			//Img img3 = ch.getGame().getImg(SetSwordmanSkill.khazan[1]);
			SpriteTexture re[] = new SpriteTexture[8];
			for(int i = 0; i < re.length; i++)
				re[i] = img2.getIndexST(i);
			start = new RoleAnimation(1.0f/re.length, re);
			re = new SpriteTexture[10];
			for(int i = 0; i < re.length; i++)
				re[i] = img2.getIndexST(10+i);
			last = new RoleAnimation(1.0f/re.length, re);
			re = new SpriteTexture[5];
			for(int i = 0; i < re.length; i++)
				re[i] = img2.getIndexST(18+i);
			end = new RoleAnimation(1.0f/re.length, re);
			SpriteTexture re1[] = new SpriteTexture[8];
			//SpriteTexture re2[] = new SpriteTexture[8];
			for(int i = 0; i < re1.length; i++) {
				re1[i] = img1.getIndexST(i);
				//re2[i] = img3.getIndexST(i);
			}
			an1 = new RoleAnimation(1.0f/re1.length, re1);
			//an2 = new RoleAnimation(1.0f/re2.length, re2);
			add = new AddActor(duringtime, start, last, end);
		}
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		this.delta = delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		an1.draw(batch, delta, parentAlpha, getX()-an1.getWidth()/2, getY(), !right, false);
	}
}