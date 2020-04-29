package dnf.instance.reward;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;

public class ExpNumberWindow extends Window {
	private GuPoubleXGame game = null;
	private Array<Float> per = null;
	private Image imgexpshow[] = null;
	private SpriteTexture expshowadd[] = null;
	private int exp = 0;
	private float expadd = 0;
	private float percent = 0;
	private float time = 0;
	public ExpNumberWindow(int exp, GuPoubleXGame game, Array<Float> per) {
		super("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		super.setSize(30*7, 23);
		this.exp = exp;
		this.game = game;
		this.per = per;
	}
	public int getEXP() {
		return (int) (exp*(1+percent));
	}
	private int getCount(int index) {
		int i = 0;
		while(index > 0) {
			index /= 10;
			i++;
		}
		return i;
	}
	public Image[] getShow() {
		return imgexpshow;
	}
	public float changePer(int n) {
		return per.get(n);
	}
	public void change(int n) {
		if(n != -1) {
			if(imgexpshow != null)
				for(int i = 0; i < imgexpshow.length; i++)
					imgexpshow[i].remove();
			if(per.get(n) == 0)
				return;
			expadd = per.get(n);
			percent += per.get(n);
		}
		if(n != -1) {
			time = 0;
			expshowadd = setNum((int) (exp*expadd), true);
		}else
			expshowadd = null;
		SpriteTexture expshow[] = setNum((int) (exp*(1+percent)), false);
		addGroup(expshow);
	}
	private SpriteTexture[] setNum(int exp, boolean add) {
		int n = getCount(exp);
		SpriteTexture expshow[] = new SpriteTexture[n];
		int bw = 0;
		int sw = 0;
		int w = 0;
		int q = 0;
		int b = 0;
		int s = 0;
		int g = 0;
		if(n >= 7) {
			bw = exp/1000000%10;
			sw = exp/100000%10;
			w = exp/10000%10;
			q = exp/1000%10;
			b = exp/100%10;
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
			expshow[2] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+b);
			expshow[3] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+q);
			expshow[4] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+w);
			expshow[5] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+sw);
			expshow[6] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+bw);
		}else if(n >= 6) {
			sw = exp/100000%10;
			w = exp/10000%10;
			q = exp/1000%10;
			b = exp/100%10;
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
			expshow[2] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+b);
			expshow[3] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+q);
			expshow[4] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+w);
			expshow[5] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+sw);
		}else if(n >= 5) {
			w = exp/10000%10;
			q = exp/1000%10;
			b = exp/100%10;
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
			expshow[2] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+b);
			expshow[3] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+q);
			expshow[4] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+w);
		}else if(n >= 4) {
			q = exp/1000%10;
			b = exp/100%10;
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
			expshow[2] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+b);
			expshow[3] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+q);
		}else if(n >= 3) {
			b = exp/100%10;
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
			expshow[2] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+b);
		}else if(n >= 2) {
			s = exp/10%10;
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
			expshow[1] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+s);
		}else {
			g = exp%10;
			expshow[0] = game.getImg(SetImg.instance, "result.img").getIndexST((add?61:50)+g);
		}
		return expshow;
	}
	private void addGroup(SpriteTexture expshow[]) {
		imgexpshow = new Image[expshow.length];
		int count = 0;
		float w = 25;
		for(int i = expshow.length-1; i >= 0; i--, count++) {
			imgexpshow[i] = new Image(expshow[i].getSprite());
			imgexpshow[i].setPosition(w/2-imgexpshow[i].getWidth()/2+w*count, expshow[i].getXY().y);
			addActor(imgexpshow[i]);
		}
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(expshowadd != null && time <= 0.5f) {
			int w = 25;
			int count = 0;
			float dh = Math.min(1, Interpolation.exp5Out.apply(time/0.5f));
			Sprite st = game.getImg(SetImg.instance, "result.img").getIndex(71);
			st.setPosition(st.getWidth()+getX(), 30*dh+getY());
			st.draw(batch, parentAlpha);
			for(int i = expshowadd.length-1; i >= 0; i--, count++) {
				Sprite s = expshowadd[i].getSprite();
				s.setPosition(st.getX()+st.getWidth()+w/2-expshowadd[i].getWH().x/2+w*count, st.getY());
				s.draw(batch, parentAlpha);
			}
		}
	}
}