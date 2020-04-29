package dnf.admin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import dnf.author.InfomationDialog;
import dnf.author.TextLabel;
import dnf.gupoublex.set.SetAdmin;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetChannel;
import dnf.gupoublex.set.SetImg;

public class Select extends StageAdmin {
	private ImageButton channel = null;
	private ImageButton exit = null;
	public Select(ScreenAdmin main, Vector2 ver, Batch batch, float scale) {
		super(main, ver, batch, 0, scale, "selectcharacter.img");
	}
	@Override
	protected void setStage() {
		ground = new Image(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(0));
		SpriteAnimation can = new SpriteAnimation(0.1f, main.getGame(), SetImg.admin, "selectcharactereffectanimation.img",
				new int[]{0,1,2,3,4,5,6,7,8,9,10,11}, true, false);
		can.setPosition(window.getWidth()/2-can.getWidth()/2, window.getHeight()-can.getHeight());
		window.addActor(can);
		Image image = new Image(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(1));
		image.setPosition(window.getWidth()/2-image.getWidth()/2, window.getHeight()-image.getHeight()-20);
		window.addActor(image);
		float y = image.getY()+image.getHeight()/2;
		image = new Image(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(2));
		image.setScale(0.8f, 1.5f);
		image.setPosition(window.getWidth()-image.getWidth()*image.getScaleX()-10, y-image.getHeight()/2*image.getScaleY());
		window.addActor(image);
		TextLabel label = new TextLabel(main.getGame());
		label.setText(SetAdmin.selectinfo1+"[ORANGE]"+getChannelStr(main.getGame().getChannel()-10)+"[]"+SetAdmin.selectinfo2);
		label.setFontScale(0.7f);
		label.setSize(image.getWidth()*image.getScaleX(), image.getHeight()*image.getScaleY());
		label.setAlignment(Align.center);
		label.setPosition(image.getX(), image.getY());
		window.addActor(label);
		create = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(1)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(2)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(2)));
		delete = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		enter = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		channel = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		exit = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(3)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "selectcharacter.img").getIndex(4)));
		enter.setPosition(window.getWidth()/2-enter.getWidth()/2, 10);
		delete.setPosition(enter.getX()-10-delete.getWidth(), enter.getY());
		create.setPosition(delete.getX()-10-create.getWidth(), delete.getY());
		channel.setPosition(enter.getX()+enter.getWidth()+10, enter.getY());
		exit.setPosition(channel.getX()+channel.getWidth()+10, channel.getY());
		window.addActor(create);
		window.addActor(delete);
		window.addActor(enter);
		window.addActor(channel);
		window.addActor(exit);
		swindow = new Array<SelectWindow>();
		for(int i = 0; i < 12; i++) {
			SelectWindow sw = new SelectWindow(main.getGame(), swindow, context);
			if(list != null && i < list.size)
				sw.isChar(list.get(i), list);
			else
				sw.unlock();
			if(i/6 == 0)
				sw.setPosition(window.getWidth()/6*(i%6)+window.getWidth()/12-sw.getWidth()/2,
					window.getHeight()*3/4-sw.getHeight()/2-31);
			else
				sw.setPosition(window.getWidth()/6*(i%6)+window.getWidth()/12-sw.getWidth()/2,
						window.getHeight()/4-sw.getHeight()/2+32);
			window.addActor(sw);
			swindow.add(sw);
		}
	}
	@Override
	protected void setListener() {
		for(SelectWindow sw : swindow) {
			sw.addListener();
			btl.add(sw);
		}
		create.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(main.getGame().getList().size != 12) {
					clearLs();
					main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
					main.change("create");
				}
			}
		});
		delete.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!chosetodel.getName().equals("")) {
					main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
					InfomationDialog dialog = new InfomationDialog("", main.getGame(),
							context, main.getGame().getLazy(), SetBase.dialog_okcancel, SetAdmin.delcharacter);
					dialog.setPosition(window.getWidth()/2-dialog.getWidth()/2,
							window.getHeight()/2-dialog.getHeight()/2);
					window.addActor(dialog);
				}
			}
		});
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				Gdx.app.exit();
			}
		});
		channel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				main.change("channel");
			}
		});
		enter.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!chosetodel.getName().equals("")) {
					clearLs();
					main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
					main.getGame().change("town", "hotel", 0, null);
				}
			}
		});
		btl.add(create);
		btl.add(delete);
		btl.add(enter);
		btl.add(channel);
		btl.add(exit);
	}
	private String getChannelStr(int channel) {
		String chl = "";
		if(channel < 6)
			chl = SetChannel.ll;
		else if(channel < 6*2)
			chl = SetChannel.glzs;
		else if(channel < 6*3)
			chl = SetChannel.tkzc;
		else if(channel < 6*4)
			chl = SetChannel.twjs;
		else if(channel < 6*5)
			chl = SetChannel.nypl;
		else if(channel < 6*6)
			chl = SetChannel.ahc;
		else if(channel < 6*7)
			chl = SetChannel.wnxs;
		else if(channel < 6*8)
			chl = SetChannel.nsmr;
		else if(channel < 6*9)
			chl = SetChannel.dlzd;
		else if(channel < 6*10)
			chl = SetChannel.hasfj;
		else if(channel < 6*11)
			chl = SetChannel.atblxg;
		else if(channel < 6*12)
			chl = SetChannel.yg;
		else if(channel < 6*13)
			chl = SetChannel.ycylf;
		else if(channel < 6*13+2)
			chl = SetChannel.wzxg;
		else
			chl = SetChannel.pmh;
		return chl;
	}
}