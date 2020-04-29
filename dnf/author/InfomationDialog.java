package dnf.author;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import dnf.admin.Select;
import dnf.admin.StageAdmin;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class InfomationDialog extends Dialog {
	private GuPoubleXGame game = null;
	private StageAdmin ad = null;
	public InfomationDialog(String title, GuPoubleXGame game, StageAdmin admin, LazyBitmapFont lazy, int type, String txt) {
		super(title,  new Window.WindowStyle(lazy, Color.WHITE, null));
		this.ad = admin;
		this.game = game;
		getTitleLabel().setAlignment(Align.center);
		setMovable(false);
		if(type == SetBase.dialog_ok)
			ok(admin, game, lazy, txt);
		else if(type == SetBase.dialog_okcancel)
			okcancel(admin, game, lazy, txt);
	}
	private void ok(StageAdmin admin, GuPoubleXGame game, LazyBitmapFont lazy, String txt) {
		Image image = new Image(game.getImg(SetImg.common, "cs_shop.img").getIndex(2));
		setSize(image.getWidth()*2, image.getHeight()*2);
		image.setScale(2);
		addActor(image);
		ImageButton btnok = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(4)),
				new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(5)));
		btnok.setPosition(getWidth()/2-btnok.getWidth()/2, 20);
		addActor(btnok);
		btnok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
            	remove();
            }
        });
		TextLabel label = new TextLabel(getGame());
		label.setSize(getWidth()-60, getHeight()-100);
		label.setPosition(getWidth()/2-label.getWidth()/2, getHeight()/2-label.getHeight()/2);
		addActor(label);
		label.setWrap(true);
		label.setText(txt);
	}
	private void okcancel(StageAdmin admin, GuPoubleXGame game, LazyBitmapFont lazy, String txt) {
		Image image = new Image(game.getImg(SetImg.common, "cs_shop.img").getIndex(2));
		setSize(image.getWidth()*2, image.getHeight()*2);
		image.setScale(2);
		addActor(image);
		ImageButton btnok = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(4)),
				new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(5)));
		ImageButton btncancel = new ImageButton(new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(4)),
				new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(5)));
		btnok.setPosition(getWidth()/2-btnok.getWidth()-10, 20);
		addActor(btnok);
		btncancel.setPosition(getWidth()/2+10, 20);
		addActor(btncancel);
		btnok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
            	if(ad instanceof Select)
            		ad.delcharacter();
            	remove();
            }
        });
		btncancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
            	remove();
            }
        });
		TextLabel label = new TextLabel(getGame());
		label.setSize(getWidth()-60, getHeight()-100);
		label.setPosition(getWidth()/2-label.getWidth()/2, getHeight()/2-label.getHeight()/2);
		addActor(label);
		label.setWrap(true);
		label.setText(txt);
	}
	private GuPoubleXGame getGame() {
		return game;
	}
}