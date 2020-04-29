package dnf.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetImg;

public class DemageActor extends Actor {
	private GuPoubleXGame game = null;
	private Array<Sprite> list = null;
	private Array<Integer> listnum = null;
	private float time = 0;
	private boolean crt = false;
	private float t = 0;
	private int c = 0;
	private Vector2 fix = null;
	private boolean phy = true;
	private float flo = 0;
	public DemageActor(boolean phy, GuPoubleXGame game, boolean crt, int demage, Vector2 fix) {
		super();
		this.game = game;
		this.phy = phy;
		this.crt = crt;
		this.fix = fix;
		flo = ((int) (Math.random()*20))*0.01f;
		if(demage >= 1000000000)//billion
			demage = 1000000000-1;
		int first = returnFirstNum(demage, 1);
		int y = demage/100000000%10;
		int qw = demage/10000000%10;
		int bw = demage/1000000%10;
		int sw = demage/100000%10;
		int w = demage/10000%10;
		int q = demage/1000%10;
		int b = demage/100%10;
		int s = demage/10%10;
		int g = demage%10;
		if(first > 8)
			getTexture(crt, y);
		if(first > 7)
			getTexture(crt, qw);
		if(first > 6)
			getTexture(crt, bw);
		if(first > 5)
			getTexture(crt, sw);
		if(first > 4)
			getTexture(crt, w);
		if(first > 3)
			getTexture(crt, q);
		if(first > 2)
			getTexture(crt, b);
		if(first > 1)
			getTexture(crt, s);
		getTexture(crt, g);
	}
	private int returnFirstNum(int demage, int i) {
		demage = demage/10;
		if(demage > 0)
			return returnFirstNum(demage, i+1);
		else
			return i;
	}
	private void getTexture(boolean crt, int num) {
		int i = 0;
		switch(num) {
			case 0:i = crt?54:!phy?74:88;break;
			case 1:i = crt?55:!phy?75:89;break;
			case 2:i = crt?56:!phy?76:90;break;
			case 3:i = crt?57:!phy?77:91;break;
			case 4:i = crt?58:!phy?78:92;break;
			case 5:i = crt?59:!phy?79:93;break;
			case 6:i = crt?60:!phy?80:94;break;
			case 7:i = crt?61:!phy?81:95;break;
			case 8:i = crt?62:!phy?82:96;break;
			case 9:i = crt?63:!phy?83:97;break;
		}
		if(list == null) {
			list = new Array<Sprite>();
			listnum = new Array<Integer>();
		}
		if(i != 0) {
			list.add(new Sprite(game.getImg(SetImg.hud, "combo.img").getIndex(i)));
			listnum.add(num);
		}
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		t += delta;
		if(t >= 0.1f) {
			t -= 0.1f;
			c++;
		}
		if(time >= 1)
			remove();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(list != null) {
			int w = 0;
			if(list.size%2 == 0)
				w = list.size/2*(crt?18:14);
			else
				w = list.size/2*(crt?18:14)+(crt?18:14)/2;
			int i = 0;
			for(Sprite sp : list) {
				sp.setPosition(getX()-w+(crt?18:14)*i+(list.size%2==1?(crt?18:14)/2:0)+(listnum.get(i)==1?(crt?5:4):0),
						getY()+fix.y/2+new Interpolation.PowOut(3).apply(c*0.1f)*70*(1+flo));
				sp.draw(batch, parentAlpha);
				i++;
			}
		}
	}
}