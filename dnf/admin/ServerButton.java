package dnf.admin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class ServerButton extends ImageButton {
	private GuPoubleXGame game = null;
	private Sprite text = null;
	private Sprite check = null;
	private boolean checked = false;
	private Array<ServerButton> list = null;
	private int server = 0;
	private Image background = null;
	public ServerButton(GuPoubleXGame game, int server, Image background) {
		super(new TextureRegionDrawable(game.getImg(SetImg.admin, "channel_base.img").getIndex(3)));
		this.game = game;
		this.server = server;
		this.background = background;
		text = new Sprite(game.getImg(SetImg.admin, "channelbackground.img").getIndex(transSever(server)));
		check = new Sprite(game.getImg(SetImg.admin, "channel_base.img").getIndex(4));
	}
	private int transSever(int i) {
		int index = 0;
		switch(i) {
			case 1:index = 2;break;
			case 2:index = 12;break;
			case 3:index = 8;break;
			case 4:index = 10;break;
			case 5:index = 6;break;
			case 6:index = 4;break;
			case 7:index = 17;break;
			case 8:index = 14;break;
			case 9:index = 20;break;
			case 10:index = 0;break;
		}
		return index;
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
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(checked)
			check.draw(batch, parentAlpha);
		text.draw(batch, parentAlpha);
	}
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		check.setPosition(x, y);
		text.setPosition(x+getWidth()/2-text.getWidth()/2, y+getHeight()/2-text.getHeight()/2);
	}
	public void addGroup(Array<ServerButton> list) {
		this.list = list;
	}
	public void resetCheck() {
		checked = false;
	}
	public void setCheck() {
		checked = true;
	}
	public void setListener() {
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!checked) {
					for(ServerButton button : list)
						button.resetCheck();
					checked = true;
					game.getSound(SetBase.common, "click.ogg").play(game.getSound_button());
					game.setServer(server);
					background.setDrawable(new TextureRegionDrawable(game.getImg(SetImg.admin, "channelbackground.img").getIndex(transSeverBG(server))));
				}
			}
		});
	}
}