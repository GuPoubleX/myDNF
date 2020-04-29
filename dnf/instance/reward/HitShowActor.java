package dnf.instance.reward;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class HitShowActor extends Actor {
	private GuPoubleXGame game = null;
	private float hit = 0;
	private float time = 0;
	private float p = 0;
	private Image digit0 = null;
	private Image digit1 = null;
	private Image digit2 = null;
	private Image sign = null;
	private boolean started = false;
	public HitShowActor(int hit, GuPoubleXGame game, float x, float y) {
		super();
		setPosition(x, y);
		this.game = game;
		this.hit = hit;
		digit0 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(30));
		digit0.setPosition(getX()-digit0.getWidth(), getY()-digit0.getHeight()/2);
		if(hit > 0) {
			sign = new Image(game.getImg(SetImg.instance, "result.img").getIndex(29));
			sign.setPosition(getX()-digit0.getWidth()-sign.getWidth()-2, getY()-sign.getHeight()/2);
		}
	}
	public void start() {
		if(hit != 0)
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
		if(digit1 != null)
			digit1.draw(batch, parentAlpha);
		if(digit2 != null)
			digit2.draw(batch, parentAlpha);
		if(sign != null && p > 0)
			sign.draw(batch, parentAlpha);
		if(started && p != 1) {
			p = Math.min(1, Interpolation.exp5Out.apply(time/0.7f));
			int count = (int) (Math.min(1, p)*hit);
			if(count >= 999) {
				digit0 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(39));
				digit0.setPosition(getX()-digit0.getWidth(), getY()-digit0.getHeight()/2);
				digit1 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(39));
				digit1.setPosition(getX()-digit0.getWidth()*2, digit0.getY());
				digit2 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(39));
				digit2.setPosition(getX()-digit0.getWidth()*3, digit0.getY());
				sign.setPosition(getX()-digit0.getWidth()*3-sign.getWidth()-2, getY()-sign.getHeight()/2);
			}else {
				int bw = count/100;
				int sw = (count-bw*100)/10;
				int gw = count%10;
				digit0 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(gw+30));
				digit0.setPosition(getX()-digit0.getWidth(), getY()-digit0.getHeight()/2);
				if(bw != 0 || (bw == 0 && sw != 0)) {
					digit1 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(sw+30));
					digit1.setPosition(getX()-digit0.getWidth()-digit1.getWidth(), digit0.getY());
				}
				if(bw != 0) {
					digit2 = new Image(game.getImg(SetImg.instance, "result.img").getIndex(bw+30));
					digit2.setPosition(getX()-digit0.getWidth()-digit1.getWidth()-digit2.getWidth(), digit0.getY());
				}
				float x = digit0.getWidth()+(digit1!=null?digit1.getWidth():0)+(digit2!=null?digit2.getWidth():0);
				sign.setPosition(getX()-x-sign.getWidth()-2, getY()-sign.getHeight()/2);
			}
		}
	}
}