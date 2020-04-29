package dnf.admin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.author.ScrollWindow;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetChannel;
import dnf.gupoublex.set.SetImg;

public class Channel extends StageAdmin {
	private ImageButton fresh = null;
	private ImageButton exit = null;
	public Channel(ScreenAdmin main, Vector2 ver, Batch batch, float scale) {
		super(main, ver, batch, 0, scale, "selectcharacter.img");
	}
	@Override
	protected void setStage() {
		ca = new Array<ChannelButton>();
		main.getGame().setChannel(0);
		SpriteAnimation can = new SpriteAnimation(0.1f, main.getGame(), SetImg.admin, "selectcharactereffectanimation.img",
				new int[]{0,1,2,3,4,5,6,7,8,9,10,11}, true, false);
		can.setPosition(window.getWidth()/2-can.getWidth()/2, window.getHeight()-can.getHeight());
		window.addActor(can);
		Image image = new Image(main.getGame().getImg(SetImg.admin, "channelbackground.img").getIndex(transSeverBG(main.getGame().getServer())));
		image.setPosition(0, window.getHeight()-image.getHeight());
		window.addActor(image);
		Sprite sp = new Sprite(main.getGame().getImg(SetImg.admin, "channelbackground.img").getIndex(transSeverBG(main.getGame().getServer())));
		sp.flip(true, false);
		image = new Image(sp);
		image.setPosition(window.getWidth()-image.getWidth(), window.getHeight()-image.getHeight());
		window.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.admin, "channelbase.img").getIndex(0));
		image.setPosition(window.getWidth()/2-image.getWidth()/2, window.getHeight()-image.getHeight()-10);
		window.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.admin, "channelbase.img").getIndex(4));
		image.setPosition(window.getWidth()/2-image.getWidth()/2, window.getHeight()-image.getHeight()-42);
		window.addActor(image);
		fresh = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "channelbase.img").getIndex(2)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "channelbase.img").getIndex(3)));
		fresh.setPosition(image.getX()+image.getWidth()-fresh.getWidth()-15,
				image.getY()+image.getHeight()/2-fresh.getHeight()/2);
		window.addActor(fresh);
		enter = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		enter.setPosition(window.getWidth()/2-enter.getWidth()/2, 10);
		window.addActor(enter);
		exit = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		exit.setPosition(window.getWidth()-exit.getWidth()-30, enter.getY());
		window.addActor(exit);
		setScrollwindow();
		int ch = 88;
		ChannelButton cat = new ChannelButton(main, main.getGame().getLazy(), SetChannel.lv0, "ch."+ch+SetChannel.wzxg, ch, 12, (int) (Math.random()*4), ca);ch++;
		float dx = 30+(cat.getWidth()+11)*0;
		float dy = 110-cat.getHeight()-5;
		cat.addActor(window, dx, dy);
		ca.add(cat);
		cat = new ChannelButton(main, main.getGame().getLazy(), SetChannel.lv0, "ch."+ch+SetChannel.wzxg, ch, 12, (int) (Math.random()*4), ca);ch++;
		dx = 30+(cat.getWidth()+11)*1;
		dy = 110-cat.getHeight()-5;
		cat.addActor(window, dx, dy);
		ca.add(cat);
		cat = new ChannelButton(main, main.getGame().getLazy(), SetChannel.lv0, "ch."+ch+SetChannel.pmh, ch, 13, (int) (Math.random()*4), ca);ch++;
		dx = 30+(cat.getWidth()+11)*2;
		dy = 110-cat.getHeight()-5;
		cat.addActor(window, dx, dy);
		ca.add(cat);
	}
	private int transSeverBG(int i) {
		int index = 0;
		switch(i) {
			case 1:index = 1;break;
			case 2:index = 11;break;
			case 3:index = 7;break;
			case 4:index = 9;break;
			case 5:index = 5;break;
			case 6:index = 3;break;
			case 7:index = 16;break;
			case 8:index = 13;break;
			case 9:index = 19;break;
			case 10:index = 21;break;
		}
		return index;
	}
	private void setScrollwindow() {
		if(scrollw != null) {
			scrollw.clear();
			scrollw.remove();
		}
		scrollw = new ScrollWindow(window.getStyle(), window, 5);
		scrollw.setSize(600, 275);
		int ch = 10;
		for(int k = 0; k < 6*13; k++) {
			String lv = "";
			String chl = "";
			int type = 0;
			if(k < 6) {
				lv = SetChannel.lv1;
				chl = ch+SetChannel.ll;
				type = 0;
			}else if(k < 6*2) {
				lv = SetChannel.lv2;
				chl = ch+SetChannel.glzs;
				type = 1;
			}else if(k < 6*3) {
				lv = SetChannel.lv3;
				chl = ch+SetChannel.tkzc;
				type = 2;
			}else if(k < 6*4) {
				lv = SetChannel.lv4;
				chl = ch+SetChannel.twjs;
				type = 3;
			}else if(k < 6*5) {
				lv = SetChannel.lv5;
				chl = ch+SetChannel.nypl;
				type = 4;
			}else if(k < 6*6) {
				lv = SetChannel.lv6;
				chl = ch+SetChannel.ahc;
				type = 4;
			}else if(k < 6*7) {
				lv = SetChannel.lv7;
				chl = ch+SetChannel.wnxs;
				type = 5;
			}else if(k < 6*8) {
				lv = SetChannel.lv8;
				chl = ch+SetChannel.nsmr;
				type = 6;
			}else if(k < 6*9) {
				lv = SetChannel.lv9;
				chl = ch+SetChannel.dlzd;
				type = 7;
			}else if(k < 6*10) {
				lv = SetChannel.lv9;
				chl = ch+SetChannel.hasfj;
				type = 8;
			}else if(k < 6*11) {
				lv = SetChannel.lv9;
				chl = ch+SetChannel.atblxg;
				type = 9;
			}else if(k < 6*12) {
				lv = SetChannel.lv9;
				chl = ch+SetChannel.yg;
				type = 10;
			}else if(k < 6*13) {
				lv = SetChannel.lv9;
				chl = ch+SetChannel.ycylf;
				type = 11;
			}
			ChannelButton chactor = new ChannelButton(main, main.getGame().getLazy(), lv, "ch."+chl, ch, type, (int) (Math.random()*4), ca);
			float dx = 10+(186+11)*(k%3);
			float dy = scrollw.getHeight()-40-5-(40+5)*(k/3);
			chactor.addActors(scrollw, dx, dy);
			ch++;
			ca.add(chactor);
		}
		scrollw.setPosition(window.getWidth()/2-scrollw.getWidth()/2, 110+5);
		window.addActor(scrollw);
	}
	@Override
	protected void setListener() {
		for(ChannelButton cactor : ca) {
			cactor.addListener();
			btl.add(cactor);
		}
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				Gdx.app.exit();
			}
		});
		enter.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(main.getGame().getChannel() != 0) {
					clearLs();
					main.getGame().getSound(SetBase.admin, "button_02.ogg").play(main.getGame().getSound_button());
					main.change("select");
				}
			}
		});
		fresh.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				for(ChannelButton cactor : ca) {
					cactor.reset();
					cactor.changestate((int) (Math.random()*4));
				}
				scrollw.resetscroll();
				main.getGame().setChannel(0);
			}
		});
		btl.add(exit);
		btl.add(enter);
		btl.add(fresh);
	}
}