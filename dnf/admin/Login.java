package dnf.admin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import dnf.author.InfomationDialog;
import dnf.author.LoadingDialog;
import dnf.author.TextLabel;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.set.SetAdmin;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;

public class Login extends StageAdmin {
	public Login(ScreenAdmin main, Vector2 ver, Batch batch, float scale) {
		super(main, ver, batch, 0, scale, "loginbase.img");
	}
	@Override
	protected void setStage() {
		Window press = new Window("", window.getStyle());
		press.setSize(400, 300);
		press.setPosition(window.getWidth()/2-press.getWidth()/2, window.getHeight()/2-press.getHeight()/2);
		window.addActor(press);
		Image image = new Image(main.getGame().getImg(SetImg.admin, "loginbase.img").getIndex(1));
		image.setScale(press.getWidth()/image.getWidth()*1.5f, press.getHeight()/image.getHeight()*1.5f);
		image.setPosition(press.getWidth()/2-image.getWidth()/2*image.getScaleX(),
				press.getHeight()/2-image.getHeight()/2*image.getScaleY());
		press.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.admin, "channelbackground.img").getIndex(transSever(main.getGame().getServer())));
		image.setScale(1.5f);
		image.setPosition(30, 240);
		press.addActor(image);
		TextLabel text = new TextLabel(main.getGame());
		text.setText(SetAdmin.account);
		text.setFontScale(1.5f);
		text.setAlignment(Align.center, Align.center);
		text.setPosition(35, 200);
		press.addActor(text);
		text = new TextLabel(main.getGame());
		text.setText(SetAdmin.password);
		text.setFontScale(1.5f);
		text.setAlignment(Align.center, Align.center);
		text.setPosition(35, 150);
		press.addActor(text);
		
		TextField.TextFieldStyle style = new TextField.TextFieldStyle();
		style.background = new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(0));
		style.cursor = new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(8));
		style.font = main.getGame().getLazy();
		style.fontColor = Color.WHITE;
		
		username = new TextField("", style);
		username.setBlinkTime(1.0f);
		username.setMaxLength(25);
		username.setSize(220.0f, 30.0f);
		username.setAlignment(Align.left);
		username.setMessageText(SetAdmin.accountinfo);
		username.setPosition(120, 195);
		username.setText(SetBase.TAG.toLowerCase());
		press.addActor(username);
		password = new TextField("", style);
		password.setBlinkTime(1.0f);
		password.setMaxLength(25);
		password.setSize(220.0f, 30.0f);
		password.setAlignment(Align.left);
		password.setMessageText(SetAdmin.passwordinfo);
		password.setPosition(120, 145);
		password.setPasswordMode(true);
		password.setPasswordCharacter('*');
		password.setText(SetBase.TAG+" is test");
		press.addActor(password);
		btl.add(username);
		btl.add(password);
		
		exit = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "cs_shop.img").getIndex(0)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "cs_shop.img").getIndex(1)));
		exit.setPosition(press.getWidth()-exit.getWidth()-5, press.getHeight()-exit.getHeight()-5);
		press.addActor(exit);
		server = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(4)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(5)));
		server.setPosition(50, 50);
		press.addActor(server);
		login = new ImageButton(new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(4)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.common, "windowcommon.img").getIndex(5)));
		login.setPosition(press.getWidth()-50-login.getWidth(), 50);
		press.addActor(login);
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
	@Override
	protected void setListener() {
		server.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				//manager.get("../SoundPacks/sounds_amb.npk", Sog.class).play(0, 1, main.getGame());
				main.change("server");
			}
		});
		login.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				logic(username.getText(), password.getText());
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
		btl.add(server);
		btl.add(login);
		btl.add(exit);
	}
	private void logic(String user, String pswd) {
		Preferences pref = main.getGame().getPref(SetBase.save_prefix+"Account");
		if(username.getText().equals("") || password.getText().equals("")) {
			InfomationDialog dialog = new InfomationDialog("", main.getGame(), context, main.getGame().getLazy(), SetBase.dialog_ok, SetAdmin.accountworning);
			dialog.setPosition(window.getWidth()/2-dialog.getWidth()/2, window.getHeight()/2-dialog.getHeight()/2);
			window.addActor(dialog);
			username.setText("");
			password.setText("");
		}else {
			String s = pref.getString(username.getText());
			if(s.equals("")) {//no in pref
				clearLs();
				pref.putString(username.getText(), password.getText());
				main.getGame().setAccount(username.getText());
				pref.flush();
				main.change("channel");
			}else {
				if(!s.equals(password.getText())) {
					InfomationDialog dialog = new InfomationDialog("", main.getGame(), context, main.getGame().getLazy(), SetBase.dialog_ok, SetAdmin.accountworning);
					dialog.setPosition(window.getWidth()/2-dialog.getWidth()/2, window.getHeight()/2-dialog.getHeight()/2);
					window.addActor(dialog);
					username.setText("");
					password.setText("");
				}else {//login
					clearLs();
					main.getGame().setAccount(username.getText());
					Preferences pf = main.getGame().getPref(SetBase.save_prefix+main.getGame().getAccount()+"."+main.getGame().getServer());
					//pf.putInteger(SetInstance.lorien, pf.getInteger(SetInstance.lorien));
					//pf.putInteger(SetInstance.loriendeep, pf.getInteger(SetInstance.loriendeep));
					//pf.putInteger(SetInstance.jixieniu, pf.getInteger(SetInstance.jixieniu));
					//pf.putInteger(SetInstance.chongwang, pf.getInteger(SetInstance.chongwang));
					//pf.putInteger(SetInstance.milin, pf.getInteger(SetInstance.milin));
					//pf.putInteger(SetInstance.milindeep, pf.getInteger(SetInstance.milindeep));
					//pf.putInteger(SetInstance.leiming, pf.getInteger(SetInstance.leiming));
					//pf.putInteger(SetInstance.leimingpoison, pf.getInteger(SetInstance.leimingpoison));
					//pf.putInteger(SetInstance.milinice, pf.getInteger(SetInstance.milinice));
					//pf.putInteger(SetInstance.gelaka, pf.getInteger(SetInstance.gelaka));
					//pf.putInteger(SetInstance.gelakafire, pf.getInteger(SetInstance.gelakafire));
					//pf.putInteger(SetInstance.leimingdark, pf.getInteger(SetInstance.leimingdark));
					//pf.flush();
					for(Object ob : pf.get().values()) {
						Preferences pfr = main.getGame().getPref(username.getText()+"."+ob);
						String profession = pfr.getString("PROFESSION");
						Character ch = null;
						if(profession.equals(SetAdmin.swordman))
							ch = new Swordman(main.getGame(), Integer.parseInt(ob.toString()), SetCharProperty.companion);
						else if(profession.equals(SetAdmin.fighter))
							ch = new Swordman(main.getGame(), Integer.parseInt(ob.toString()), SetCharProperty.companion);
						else if(profession.equals(SetAdmin.gunner))
							ch = new Swordman(main.getGame(), Integer.parseInt(ob.toString()), SetCharProperty.companion);
						else if(profession.equals(SetAdmin.mage))
							ch = new Swordman(main.getGame(), Integer.parseInt(ob.toString()), SetCharProperty.companion);
						else if(profession.equals(SetAdmin.priest))
							ch = new Swordman(main.getGame(), Integer.parseInt(ob.toString()), SetCharProperty.companion);
						if(ch != null)
							main.getGame().addList(ch);
					}
					LoadingDialog ld = new LoadingDialog(window.getStyle(), main.getGame());
					ld.setPosition(window.getWidth()/2-ld.getWidth()/2, window.getHeight()/2-ld.getHeight()/2);
					window.addActor(ld);
					main.change("channel");
				}
			}
		}
	}
}