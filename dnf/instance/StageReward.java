package dnf.instance;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import dnf.admin.SpriteAnimation;
import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.instance.reward.AlphaImage;
import dnf.instance.reward.ExpNumberWindow;
import dnf.instance.reward.FaceActor;
import dnf.instance.reward.HitShowActor;
import dnf.instance.reward.PercentActor;
import dnf.instance.reward.RewardCard;
import dnf.instance.reward.SlashImage;

public class StageReward extends Stage {
	private boolean show1 = false;
	private GuPoubleXGame game = null;
	private Window window = null;
	private Array<Window> showwindow = null;
	private int exp = 0;
	private Image rotate = null;
	private RoleAnimation an = null;
	private Array<Character> emeny = null;
	private Array<SpriteAnimation> reward = null;
	public StageReward(Viewport viewport, Batch batch, GuPoubleXGame game) {
		super(viewport, batch);
		this.game = game;
		window = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		window.setSize(getWidth(), getHeight());
		addActor(window);
	}
	public Array<Window> getShowActor() {
		return showwindow;
	}
	public Array<SpriteAnimation> getReward() {
		return reward;
	}
	public Image getRotateImg() {
		return rotate;
	}
	public int getEXP() {
		return exp;
	}
	public void show(Array<Integer> rank, Array<Character> emeny, int ranklv, Array<Float> rankper) {
		show1 = true;
		this.emeny = emeny;
		for(Character c : emeny)
			exp += c.getProperty("EXP");
		exp += (int) (Math.random()*10000);
		reset(rank, ranklv, rankper);
	}
	public boolean isshow() {
		return show1;
	}
	public RoleAnimation getAnimation() {
		return an;
	}
	private void reset(Array<Integer> rank, int ranklv, Array<Float> rankper) {
		AlphaImage alpha = new AlphaImage(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(33));
		alpha.setScale(1.8f);
		alpha.setPosition(window.getWidth()/2-alpha.getWidth()/2*alpha.getScaleX(), window.getHeight()/2-alpha.getHeight()/2*alpha.getScaleY());
		window.addActor(alpha);
		SpriteTexture st[] = new SpriteTexture[4];
		for(int i = 0; i < st.length; i++)
			st[i] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(48+i);
		st[3] = new SpriteTexture(new Texture(0, 0, Pixmap.Format.RGBA8888));
		an = new RoleAnimation(0.2f/st.length, st);
		window1(rank);
		window2(ranklv, rankper);
	}
	private void window2(int ranklv, Array<Float> rankper) {
		Window win = new Window("", window.getStyle());
		win.setSize(window.getWidth(), window.getHeight());
		addActor(win);
		showwindow.add(win);
		Image img = new Image(game.getImg(SetImg.instance, "result.img").getIndex(2));
		img.setPosition(win.getWidth()/2-90-img.getWidth()/2, win.getHeight()/2-100-img.getHeight()/2);
		img.setOrigin(img.getWidth()/2, img.getHeight()/2);
		img.setScale(0.8f);
		win.addActor(img);
		Image rankimg = new Image(game.getImg(SetImg.instance, "result.img").getIndex(3+ranklv));
		rankimg.setPosition(img.getX()+img.getWidth()/2-rankimg.getWidth()/2, img.getY()+img.getHeight()/2-rankimg.getHeight()/2);
		rankimg.setOrigin(rankimg.getWidth()/2, rankimg.getHeight()/2);
		rankimg.setScale(2);
		win.addActor(rankimg);
		img = new Image(game.getImg(SetImg.instance, "result.img").getIndex(49));
		img.setPosition(win.getWidth()/2+130-img.getWidth()/2, win.getHeight()/2-100-img.getHeight()/2);
		img.setScale(0.7f, 1);
		win.addActor(img);
		SlashImage si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(7), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*1);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(0), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*2);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(1), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*3);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(2), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*4);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(12), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*5);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(9), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*6);
		win.addActor(si);
		si = new SlashImage(game.getImg(SetImg.instance, "result_eventtag.img").getIndex(10), an);
		si.setPosition(img.getX()+img.getWidth()/2*img.getScaleX()-si.getWidth()/2, img.getY()+img.getHeight()-3-(22+3)*7);
		win.addActor(si);
		ExpNumberWindow ena = new ExpNumberWindow(exp, game, rankper);
		ena.setPosition(getWidth()/2-50, getHeight()-103);
		win.addActor(ena);
		ena.change(-1);
	}
	private void window1(Array<Integer> rank) {
		showwindow = new Array<Window>();
		Window win = new Window("", window.getStyle());
		win.setSize(getWidth(), getHeight());
		addActor(win);
		showwindow.add(win);
		Image image = new Image(game.getImg(SetImg.instance, "result.img").getIndex(12));
		image.setPosition(win.getWidth()/2-image.getWidth()/2, win.getHeight()-image.getHeight());
		win.addActor(image);
		image = new Image(new Sprite(game.getImg(SetImg.instance, "result.img").getIndex(0), 67, 0, 440-63, 190));
		image.setPosition(win.getWidth()/2-image.getWidth()/2, win.getHeight()-image.getHeight()-55);
		win.addActor(image);
		float x = image.getX();
		float y = image.getY();
		float z = 0;
		for(int i = 0; i < 3; i++) {
			image = new Image(game.getImg(SetImg.instance, "result.img").getIndex(14+i));
			image.setPosition(x+50-image.getWidth()/2, y+100-33*i-image.getHeight()/2);
			win.addActor(image);
			if(i == 0)
				z = image.getX();
			if(i < 2) {
				PercentActor pa = new PercentActor(rank.get(i), game, z+110, image.getY());
				win.addActor(pa);
			}else {
				HitShowActor sa = new HitShowActor(rank.get(i), game, z+325, image.getY()+10);
				win.addActor(sa);
			}
		}
	}
	public void window3() {
		for(Window win : showwindow) {
			win.remove();
			win.clear();
		}
		showwindow.clear();
		reward = new Array<SpriteAnimation>();
		Window win = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		win.setSize(getWidth(), getHeight());
		addActor(win);
		showwindow.add(win);
		Image img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(34));
		img.setPosition(win.getWidth()/2-img.getWidth()/2, win.getHeight()/3*2-img.getHeight()/2);
		win.addActor(img);
		for(int i = 0; i < emeny.size; i++)
			if(emeny.get(i).getEmenyType() == SetCharProperty.boss) {
				FaceActor face = null;
				if(emeny.get(i) instanceof Swordman)
					face = new FaceActor(game, game.getImg(SetImg.hud, "defaultfaces.img").getIndex(emeny.get(i).getFace()), emeny.get(i).getName(), true);
				else
					face = new FaceActor(game, game.getImg(SetImg.hud, "monsterface.img").getIndex(emeny.get(i).getFace()), emeny.get(i).getName(), true);
				face.setPosition(img.getX()+26, img.getY()+221);
				face.addGroup(win);
				break;
			}
		win = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		win.setSize(200, 152);
		win.setPosition(img.getX()+25, img.getY()+40);
		addActor(win);
		showwindow.add(win);
		int count = 0;
		for(int i = 0; i < emeny.size; i++)
			if(emeny.get(i).getEmenyType() == SetCharProperty.hero) {
				FaceActor face = null;
				if(emeny.get(i) instanceof Swordman)
					face = new FaceActor(game, game.getImg(SetImg.hud, "defaultfaces.img").getIndex(emeny.get(i).getFace()), emeny.get(i).getName(),false);
				else
					face = new FaceActor(game, game.getImg(SetImg.hud, "monsterface.img").getIndex(emeny.get(i).getFace()), emeny.get(i).getName(), false);
				face.setPosition(0, win.getHeight()-count*31-26);
				face.addGroup(win);
				count++;
			}
		win = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		win.setSize(getWidth(), getHeight());
		addActor(win);
		showwindow.add(win);
		SpriteAnimation sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{21, 22, 23}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		for(int i = 0; i < 12; i++)
			reward.add(sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{0, 1, 2}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(1, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{24, 25, 26}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(9, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{27, 28, 29}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(10, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{30, 31, 32}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(11, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{51, 52, 53}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-120);
		win.addActor(sa);
		sa.show();
		reward.set(0, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{3, 4, 5}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(2, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{6, 7, 8}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(3, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{15, 16, 17}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(6, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{9, 10, 11}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(4, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{12, 13, 14}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(5, sa);
		sa = new SpriteAnimation(0.5f/3, game, SetImg.instance, "reward_ver2.img", new int[]{18, 19, 20}, false, false, false);
		sa.setPosition(img.getX()+200, img.getY()-25);
		win.addActor(sa);
		reward.set(7, sa);
		rotate = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(56));
		rotate.setPosition(getWidth()/2-rotate.getWidth()/2, getHeight()/2-rotate.getHeight()/2);
		win.addActor(rotate);
		rotate.setVisible(false);
	}
	public void window4(int maplv) {
		for(Window win : showwindow) {
			win.remove();
			win.clear();
		}
		showwindow.clear();
		Window win = new Window("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		win.setSize(getWidth(), getHeight());
		addActor(win);
		showwindow.add(win);
		Image img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(60));
		img.setPosition(getWidth()/2-img.getWidth()/2, getHeight()-img.getHeight()-15);
		win.addActor(img);
		Image img1 = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(74));
		img1.setPosition(getWidth()/2-img1.getWidth()/2, 50);
		win.addActor(img1);
		Image img2 = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(74));
		img2.setPosition(getWidth()/2-img2.getWidth()/2, img2.getHeight()+150);
		win.addActor(img2);
		img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(73));
		img.setPosition(getWidth()/2-img.getWidth()/2, img2.getHeight()+115-img.getHeight()/2);
		win.addActor(img);
		int gold = transGold((int) (Math.random()*60));
		for(int i = 0; i < 8; i++) {
			RewardCard rc = new RewardCard(game, i<4?false:true, gold, maplv, i);
			if(i >= 4)
				rc.setPosition(img1.getX()+img1.getWidth()/2-rc.getWidth()*2-15+(rc.getWidth()+10)*(i-4), img1.getY()+img1.getHeight()/2-rc.getHeight()/2-3);
			else
				rc.setPosition(img2.getX()+img2.getWidth()/2-rc.getWidth()*2-15+(rc.getWidth()+10)*i, img2.getY()+img2.getHeight()/2-rc.getHeight()/2-3);
			win.addActor(rc);
			rc.show();
		}
		SpriteAnimation sa = new SpriteAnimation(1, game, SetImg.instance, "reward_ver2.img",
				new int[]{94,93,92,91,90,89,88,87,86,85,-1,-1,-1,-1,-1}, false, false);
		sa.setPosition(getWidth()-sa.getWidth(), getHeight()-sa.getHeight());
		win.addActor(sa);
		sa = new SpriteAnimation(1, game, SetImg.instance, "reward_ver2.img",
				new int[]{84,83,82,81,80,79,78,77,76,75,-1,-1,-1,-1,-1}, false, false);
		sa.setPosition(getWidth()-sa.getWidth(), getHeight()-sa.getHeight());
		win.addActor(sa);
	}
	private int transGold(int maplv) {
		int gold = 1;
		if(maplv > 55)
			gold = 450;
		else if(maplv > 51)
			gold = 390;
		else if(maplv > 45)
			gold = 310;
		else if(maplv > 38)
			gold = 250;
		else if(maplv > 31)
			gold = 200;
		else if(maplv > 25)
			gold = 140;
		else if(maplv > 19)
			gold = 100;
		else if(maplv > 15)
			gold = 80;
		else if(maplv > 11)
			gold = 50;
		else if(maplv > 8)
			gold = 30;
		else if(maplv > 5)
			gold = 10;
		else if(maplv > 3)
			gold = 3;
		else
			gold = 1;
		return gold;
	}
}