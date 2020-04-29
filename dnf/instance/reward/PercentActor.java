package dnf.instance.reward;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;

public class PercentActor extends Actor {
	private GuPoubleXGame game = null;
	private Image back = null;
	private Image front = null;
	private float percent = 0;
	private float time = 0;
	private float p = 0;
	private Image digit0 = null;
	private Image digit1 = null;
	private Image digit2 = null;
	private Image digit3 = null;
	private boolean started = false;
	public PercentActor(float percent, GuPoubleXGame game, float x, float y) {
		super();
		setPosition(x, y);
		this.game = game;
		this.percent = percent/100;
		back = new Image(game.getImg(SetImg.instance, "result.img").getIndex(40));
		back.setPosition(getX(), getY());
		digit0 = new Image(game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndex(11));
		digit0.setPosition(getX()+200, getY()+back.getHeight()/2-digit0.getHeight()/2);
		SpriteTexture st = game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndexST(1);
		digit1 = new Image(st.getSprite());
		digit1.setPosition(digit0.getX()-st.getVWH().x+st.getXY().x, digit0.getY());
	}
	public void start() {
		started = true;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(started)
			time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		digit0.draw(batch, parentAlpha);
		digit1.draw(batch, parentAlpha);
		if(digit2 != null)
			digit2.draw(batch, parentAlpha);
		if(digit3 != null)
			digit3.draw(batch, parentAlpha);
		back.draw(batch, parentAlpha);
		if(started && p != 1) {
			p = Math.min(1, Interpolation.exp5Out.apply(time/0.7f));
			float per = Math.min(1, percent*p);
			Sprite s = game.getImg(SetImg.instance, "result.img").getIndex(17);
			front = new Image(new Sprite(s, 0, 0, (int) (s.getWidth()*per), (int) s.getHeight()));
			front.setPosition(getX()+2, getY()+2);
			if(per == 1.0f) {
				SpriteTexture st = game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndexST(1);
				digit1 = new Image(st.getSprite());
				digit1.setPosition(digit0.getX()-st.getVWH().x+st.getXY().x, digit0.getY());
				digit2 = new Image(st.getSprite());
				digit2.setPosition(digit0.getX()-st.getVWH().x*2+st.getXY().x, digit0.getY());
				st = game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndexST(2);
				digit3 = new Image(st.getSprite());
				digit3.setPosition(digit0.getX()-st.getVWH().x*3+st.getXY().x, digit0.getY());
			}else {
				int sw = ((int) Math.round(per*100))/10;
				int gw = ((int) Math.round(per*100))%10;
				SpriteTexture st = game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndexST(gw+1);
				digit1 = new Image(st.getSprite());
				digit1.setPosition(digit0.getX()-st.getVWH().x+st.getXY().x, digit0.getY());
				if(sw != 0) {
					st = game.getImg(SetImg.instance, "result2_mainhud_numset.img").getIndexST(sw+1);
					digit2 = new Image(st.getSprite());
					digit2.setPosition(digit0.getX()-st.getVWH().x*2+st.getXY().x, digit0.getY());
				}
			}
		}
		if(front != null)
			front.draw(batch, parentAlpha);
	}
}