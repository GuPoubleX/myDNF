package dnf.instance.reward;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dnf.admin.SpriteAnimation;
import dnf.author.TextLabel;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetInstance;
import dnf.character.Character;

public class RewardCard extends Window {
	private RewardCard context = null;
	private GuPoubleXGame game = null;
	private SpriteAnimation sa = null;
	private Character ch = null;
	private boolean bottom = false;
	private boolean click = false;
	private boolean clicked = false;
	private boolean hide = false;
	private int val = 0;
	private float time = 0;
	private int index = 0;
	private boolean init = false;
	public RewardCard(GuPoubleXGame game, boolean bottom, int val, int maplv, int index) {
		super("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		context = this;
		this.game = game;
		this.val = val;
		this.bottom = bottom;
		this.index = index;
		setSize(153, 97);
		setOrigin(getWidth()/2, getHeight()/2);
		if(bottom)
			sa = new SpriteAnimation(0.5f/4, game, SetImg.instance, "reward_ver2.img", new int[]{95}, false, false);
		else
			sa = new SpriteAnimation(0.5f/4, game, SetImg.instance, "reward_ver2.img", new int[]{35, 36, 37, 38}, false, false);
		sa.setPosition(0, 0);
		addActor(sa);
		if(bottom)
			getCount(val);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		if(time >= 10 && !click && !hide) {
			hide = true;
			clearListeners();
			if(game.getChose().getRewardCard(true) == null && index == 0) {
				ch = game.getChose();
				game.getChose().setRewardCard(true, context);
				game.getSound(SetInstance.reward, "reward_open3.ogg").play(game.getSound_music());
				scaleTo(true);
			}else {
				if(!bottom)
					scaleTo(true);
				else
					hideTo();
			}
		}
	}
	private void getCount(int val) {
		SpriteTexture img[] = null;
		if(val >= 100) {
			int b = val/100%10;
			int s = val/10%10;
			int g = val%10;
			img = new SpriteTexture[5];
			img[0] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+b);
			img[1] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+s);
			img[2] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+g);
		}else if(val >= 10) {
			int s = val/10%10;
			int g = val%10;
			img = new SpriteTexture[4];
			img[0] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+s);
			img[1] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+g);
		}else {
			int g = val%10;
			img = new SpriteTexture[3];
			img[0] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96+g);
		}
		img[img.length-1] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96);
		img[img.length-2] = game.getImg(SetImg.instance, "reward_ver2.img").getIndexST(96);
		for(int i = img.length-1, count = 0; i >= 0; i--, count++) {
			Image image = new Image(img[i].getSprite());
			image.setPosition(88-20*count+20-image.getWidth()/2, 24);
			addActor(image);
		}
	}
	private void set() {
		clear();
		boolean havereward = (((int) (Math.random()*2))==0);
		Image img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(42+(havereward?0:1)));
		img.setPosition(6+3, 31+3);
		addActor(img);
		img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(107));
		img.setPosition(3, 3);
		addActor(img);
		if(ch != null) {
			img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(58));
			img.setPosition(getX()+getWidth()/2-img.getWidth()/2, getY()+getHeight()-10);
			getParent().addActor(img);
			Image face = new Image(game.getImg(SetImg.hud, "defaultfaces.img").getIndex(ch.getFace()));
			face.setPosition(img.getX()+7, img.getY()+img.getHeight()-face.getHeight()-4);
			getParent().addActor(face);
			TextLabel label = new TextLabel(game);
			label.setText(ch.getName());
			label.setPosition(face.getX()+face.getWidth(), face.getY()+face.getHeight()/2-label.getHeight()/2);
			getParent().addActor(label);
		}
	}
	public void show() {
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!click && game.getChose().getRewardCard(!bottom) == null) {
					click = true;
					boolean suc = game.getChose().changeProperty("GOLD", -val*100, false, false);
					if(!bottom)
						suc = true;
					if(suc) {
						ch = game.getChose();
						game.getChose().setRewardCard(!bottom, context);
						game.getSound(SetInstance.reward, "reward_open3.ogg").play(game.getSound_music());
						scaleTo(true);
					}else {
						click = false;
						if(!init) {
							init = true;
							clear();
							game.getChose().setRewardCard(!bottom, context);
							Image img = new Image(game.getImg(SetImg.instance, "reward_ver2.img").getIndex(106));
							img.setPosition(3, 3);
							addActor(img);
						}
					}
				}
			}
		});
	}
	private void scaleTo(boolean to0) {
		ScaleToAction start = to0?Actions.scaleTo(1, 1):Actions.scaleTo(0, 1);
		ScaleToAction end = to0?Actions.scaleTo(0, 1, 0.5f, Interpolation.exp5Out):Actions.scaleTo(1, 1, 0.5f, Interpolation.exp5Out);
		Action dothing = Actions.run(new Runnable() {
			@Override
			public void run() {
				if(!clicked) {
					clicked = true;
					set();
					scaleTo(false);
				}
			}});
		SequenceAction sequence = Actions.sequence(start, end, dothing);
		addAction(sequence);
	}
	private void hideTo() {
		MoveByAction move = Actions.moveBy(0, -200, 1f);
		AlphaAction hid = Actions.fadeOut(0.5f);
		ParallelAction parallel = Actions.parallel(move, hid);
		addAction(parallel);
	}
}