package dnf.admin;

import java.text.SimpleDateFormat;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import dnf.author.InfomationDialog;
import dnf.author.LazyBitmapFont;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetAdmin;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetTown;

public class CreateDialog extends Dialog {
	private GuPoubleXGame game = null;
	private ImageButton create = null;
	private TextField name = null;
	private LazyBitmapFont ly = null;
	private Group gp = null;
	private Create crt = null;
	private ImageButton ib = null;
	public CreateDialog(Create ct, LazyBitmapFont lazy, GuPoubleXGame game, ImageButton button, Group group) {
		super("", new Window.WindowStyle(lazy, Color.WHITE, null));
		this.game = game;
		this.ly = lazy;
		this.gp = group;
		this.crt = ct;
		this.ib = button;
		getTitleLabel().setAlignment(Align.center);
		setMovable(false);
		Image image = new Image(game.getImg(SetImg.common, "cs_shop.img").getIndex(2));
		setSize(image.getWidth()*2, image.getHeight()*2);
		image.setScale(2, 2);
		addActor(image);
	//	image = new Image(atlas.findRegion("create/20"));
	//	image.setPosition(getWidth()/2-image.getWidth()/2,
	//			getHeight()-image.getHeight());
	//	addActor(image);
		image = new Image(game.getImg(SetImg.admin, "charactercreate.img").getIndex(0));
		image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()-image.getHeight()-20);
		addActor(image);
		create = new ImageButton(
				new TextureRegionDrawable(game.getImg(SetImg.admin, "charactercreate2.img").getIndex(1)),
				new TextureRegionDrawable(game.getImg(SetImg.admin, "charactercreate2.img").getIndex(0)),
				new TextureRegionDrawable(game.getImg(SetImg.admin, "charactercreate2.img").getIndex(0)));
		create.setPosition(getWidth()/2-create.getWidth()/2, 20);
		addActor(create);
		create.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(name.getText().equals("")) {
            		name.setText("");
            	}else if(allspace(name.getText())) {
            		getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
	            	InfomationDialog dialog = new InfomationDialog("", getGame(), crt, ly, SetBase.dialog_ok, SetAdmin.nameworning);
					dialog.setPosition(gp.getWidth()/2-dialog.getWidth()/2, gp.getHeight()/2-dialog.getHeight()/2);
			        gp.addActor(dialog);
			        name.setText("");
            	}else {
            		String path = SetBase.save_prefix+getGame().getAccount()+"."+getGame().getServer();
            		String s = getGame().getPref(path).getString(name.getText());
            		if(s.equals("")) {
            			crt.btl.add(create);
            			crt.clearLs();
	            		int length = getGame().getCreateUsers()+1;
	            		int id = getGame().getCreateID()*SetBase.createusersBlock+length;
	            		getGame().getPref(path).putInteger(name.getText(), id);
	            		getGame().getPref(path).flush();
	            		Preferences pref = getGame().getPref(getGame().getAccount()+"."+id);
	            		pref.putString("PROFESSION", getProfession(crt.getChose().get(0)));
	            		pref.putFloat("TIME", 0);
	            		pref.putInteger("PL", SetCharProperty.PL_min);
	            		pref.putLong("EXP", 0);
	            		pref.putInteger("SP", 0);
	            		pref.putInteger("SPTOTAL", 0);
	            		pref.putFloat("TIME_INSTANCE", 0);
	            		pref.putString("CREATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis()));
	            		pref.putString("NAME", name.getText());
	            		pref.putString("HAIR", "hair/sm_hair0000a");
	            		pref.putString("CAP", "");
	            		pref.putString("FACE", "");
	            		pref.putString("NECK", "");
	            		pref.putString("COAT", "coat/sm_coat0000a");
	            		pref.putString("SKIN", "skin/sm_body0000");
	            		pref.putString("BELT", "");
	            		pref.putString("PANTS", "pants/sm_pants0000a");
	            		pref.putString("SHOES", "shoes/sm_shoes0000a");
	            		pref.putString("WEAPON", "weapon/lswd/lswd0000b");
	            		pref.putInteger("TOWN", SetTown.elvengard);
	            		pref.putInteger("SKILL1", 78);
	            		pref.putInteger("SKILL2", 134);
	            		pref.putInteger("SKILL3", 52);
	            		pref.putInteger("SKILL4", 128);
	            		pref.putInteger("SKILL5", -1);
	            		pref.putInteger("SKILL6", 192);
	            		pref.putInteger("SKILL7", 12);
	            		pref.putInteger("SKILL8", 132);
	            		pref.putInteger("SKILL9", 150);
	            		pref.putInteger("SKILL10", 156);
	            		pref.putInteger("SKILL11", 162);
	            		pref.putInteger("SKILL12", 172);
	            		int i = crt.getChose().get(0);
	            		int str = 0;
	            		int inT = 0;
	            		int vit = 0;
	            		int men = 0;
	            		if(i == 0) {
	            			str = SetCharProperty.swordmanstrong;
	            			inT = SetCharProperty.swordmanintelligence;
	            			vit = SetCharProperty.swordmanvitamin;
	            			men = SetCharProperty.swordmanspirit;
	            		}else if(i == 1) {
	            			str = SetCharProperty.fighterstrong;
	            			inT = SetCharProperty.fighterintelligence;
	            			vit = SetCharProperty.fightervitamin;
	            			men = SetCharProperty.fighterspirit;
	            		}else if(i == 2) {
	            			str = SetCharProperty.gunnerstrong;
	            			inT = SetCharProperty.gunnerintelligence;
	            			vit = SetCharProperty.gunnervitamin;
	            			men = SetCharProperty.gunnerspirit;
	            		}else if(i == 3) {
	            			str = SetCharProperty.magestrong;
	            			inT = SetCharProperty.mageintelligence;
	            			vit = SetCharProperty.magevitamin;
	            			men = SetCharProperty.magespirit;
	            		}else if(i == 4) {
	            			str = SetCharProperty.prieststrong;
	            			inT = SetCharProperty.priestintelligence;
	            			vit = SetCharProperty.priestvitamin;
	            			men = SetCharProperty.priestspirit;
	            		}else {
	            			str = SetCharProperty.thiefstrong;
	            			inT = SetCharProperty.thiefintelligence;
	            			vit = SetCharProperty.thiefvitamin;
	            			men = SetCharProperty.thiefspirit;
	            		}
	            		pref.putFloat("WEAK", 1);
	            		pref.putFloat("STR", str);
	            		pref.putFloat("INT", inT);
	            		pref.putFloat("VIT", vit);
	            		pref.putFloat("MEN", men);
	            		pref.putFloat("ATKPHY", 1);
	            		pref.putFloat("ATKMIG", 1);
	            		pref.putFloat("DEFPHY", 1);
	            		pref.putFloat("DEFMIG", 1);
	            		pref.putFloat("CRT", 0);
	            		pref.putFloat("CRD", 100);
	            		pref.putFloat("SPHY", 0);
	            		pref.putFloat("SMIG", 0);
	            		pref.putFloat("SMOV", 0);
	            		pref.flush();
	            		Character ch = null;
	            		if(crt.getChose().get(0) == 0)
							ch = new Swordman(getGame(), id, SetCharProperty.companion);
						else if(crt.getChose().get(0) == 1)
							ch = new Swordman(getGame(), id, SetCharProperty.companion);
						else if(crt.getChose().get(0) == 2)
							ch = new Swordman(getGame(), id, SetCharProperty.companion);
						else if(crt.getChose().get(0) == 3)
							ch = new Swordman(getGame(), id, SetCharProperty.companion);
						else if(crt.getChose().get(0) == 4)
							ch = new Swordman(getGame(), id, SetCharProperty.companion);
	            		getGame().addList(ch);
	            		getGame().setCreateUsers(length);
	            	//	pref = game.getPref(SetBase.saveGeneral);
	            	//	pref.putInteger("CHARACTER_"+game.getServer(),
	            	//			pref.getInteger("CHARACTER_"+game.getServer())+1);
	            	//	pref.flush();
	            		getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
	            		getGame().change("comic", crt.getChose().get(0).toString(), 0, null);
            		}else {
            			getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
            			InfomationDialog dialog = new InfomationDialog("", getGame(), crt, ly, SetBase.dialog_ok, SetAdmin.namerepeat);
    					dialog.setPosition(gp.getWidth()/2-dialog.getWidth()/2, gp.getHeight()/2-dialog.getHeight()/2);
    			        gp.addActor(dialog);
    			        name.setText("");
            		}
            	}
            }
        });
		ImageButton cancel = new ImageButton(
				new TextureRegionDrawable(game.getImg(SetImg.admin, "charactercreate2.img").getIndex(5)),
				new TextureRegionDrawable(game.getImg(SetImg.admin, "charactercreate2.img").getIndex(6)));
		cancel.setPosition(getWidth()-cancel.getWidth(), getHeight()-cancel.getHeight());
		addActor(cancel);
		cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	getGame().getSound(SetBase.common, "click.ogg").play(getGame().getSound_button());
            	remove();
            	ib.setChecked(false);
            }
        });
		image = new Image(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(7));
		image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()*2/3-image.getHeight()/2);
		addActor(image);
		image = new Image(game.getImg(SetImg.admin, "charactercreate.img").getIndex(3));
		image.setScale(1.25f);
		image.setPosition(15, getHeight()/2-image.getHeight()/2*image.getScaleY());
		addActor(image);
		TextField.TextFieldStyle style = new TextField.TextFieldStyle();
		
		style.background = new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(0));
		style.cursor = new TextureRegionDrawable(game.getImg(SetImg.common, "windowcommon.img").getIndex(9));
		style.font = lazy;
		style.fontColor = Color.WHITE;
		name = new TextField("", style);
		name.setBlinkTime(1.0f);
		name.setMaxLength(SetAdmin.maxnamelength);
		name.setAlignment(Align.left);
		name.setMessageText(SetAdmin.namehint);
		name.setWidth(180.0f);
		name.setHeight(25.0f);
		name.setPosition(image.getX()+image.getWidth()*image.getScaleX()+5,
				image.getY()+image.getHeight()/2*image.getScaleY()-name.getHeight()/2);
		addActor(name);
		image = new Image(game.getImg(SetImg.admin, "selectcharacter.img").getIndex(7));
		image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()/3-image.getHeight()/2);
		addActor(image);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(name.getText().length() == 0)
			create.setChecked(true);
		else
			create.setChecked(false);
		super.draw(batch, 0.9f*parentAlpha);
	}
	private GuPoubleXGame getGame() {
		return game;
	}
	private boolean allspace(String str) {
		String s[] = str.split(" ");
		if(s.length == 0)
			return true;
		return false;
	}
	private String getProfession(int type) {
		if(type == 0)
			return SetAdmin.swordman;
		else if(type == 1)
			return SetAdmin.fighter;
		else if(type == 2)
			return SetAdmin.gunner;
		else if(type == 3)
			return SetAdmin.mage;
		else if(type == 4)
			return SetAdmin.priest;
		else
			return "";
	}
}