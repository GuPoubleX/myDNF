package dnf.admin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class Server extends StageAdmin {
	private Image serverTexture = null;
	private ImageButton exit = null;
	private ServerButton button[] = null;
	private Array<ServerButton> blist = null;
	public Server(ScreenAdmin main, Vector2 ver, Batch batch, float scale) {
		super(main, ver, batch, 0, scale, "crackofdimension.img");
	}
	@Override
	protected void setStage() {
		exit = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "channel_base.img").getIndex(1))
				, new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "channel_base.img").getIndex(2)));
		exit.setPosition(window.getWidth()-exit.getWidth()-5, window.getHeight()-exit.getHeight()-5);
		window.addActor(exit);
		serverTexture = new Image(main.getGame().getImg(SetImg.admin, "channelbackground.img").getIndex(transSeverBG(main.getGame().getServer())));
		serverTexture.setPosition(0, window.getHeight()-serverTexture.getHeight());
		window.addActor(serverTexture);
		button = new ServerButton[10];
		blist = new Array<ServerButton>();
		for(int i = 0; i < button.length; i++) {
			button[i] = new ServerButton(main.getGame(), i+1, serverTexture);
			blist.add(button[i]);
			button[i].addGroup(blist);
			if(i%2 == 0)
				button[i].setPosition(window.getWidth()/3, window.getHeight()*3/4-(10+button[i].getHeight())*(i/2));
			else
				button[i].setPosition(window.getWidth()/3+20+button[i].getWidth(),
						window.getHeight()*3/4-(10+button[i].getHeight())*(i/2));
			window.addActor(button[i]);
			if(main.getGame().getServer() == i+1)
				button[i].setCheck();
		}
		Image image = new Image(main.getGame().getImg(SetImg.admin, "channel_base.img").getIndex(0));
		image.setPosition(window.getWidth()-(window.getWidth()-button[0].getX())/2-image.getWidth()/2,
				window.getHeight()*7/8);
		window.addActor(image);
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
	@Override
	protected void setListener() {
		for(int i = 0; i < button.length; i++) {
			button[i].setListener();
			btl.add(button[i]);
		}
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				main.change("login");
			}
		});
		btl.add(exit);
	}
}