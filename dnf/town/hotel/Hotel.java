package dnf.town.hotel;

import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dnf.town.ScreenTown;
import dnf.town.StageTown;
import dnf.character.Character;
import dnf.character.other.NPC;
import dnf.character.other.Obstruct;
import dnf.gupoublex.read.SpriteTexture;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetNPC;
import dnf.gupoublex.set.SetTown;

public class Hotel extends StageTown {
	public Hotel(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_hotel, SetTown.height_hotel);
		size_set = new float[]{740,45, 575,195, 380,195, 210,45, 378,45, 378,-20, 578,-20, 578,45, 740,45};
		for(int i = 0; i < size_set.length; i++)
			size_set[i] *= bs;
	}
	@Override
	public void setGround() {
		Image image = new Image(main.getGame().getImg(SetImg.hotel, "gateback.img").getIndex(0));
		image.setScale(bs);
		addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC seria = new NPC(main.getGame(), SetNPC._seria, main.getGame().getImg(SetImg.npc+"npc_se", "npc_se.img"),
				intown, new Vector2(-3, 60), 18);
		seria.setTownWorld(world, (954)/2*bs, 210*bs, ver);seria.set("", null);seria.lookat(ch);group.addActor(seria.getShadow());
		group.addActor(seria);
		SpriteTexture st[] = new SpriteTexture[main.getGame().getImg(SetImg.hotel, "gatestoragediamond.img").getCount()];
		for(int i = 0; i < st.length; i++)
			st[i] = main.getGame().getImg(SetImg.hotel, "gatestoragediamond.img").getIndexST(i);
		Obstruct store = new Obstruct(main.getGame(),"store", st, intown, new Vector2(-15, 30));
		Obstruct mail = new Obstruct(main.getGame(), "mail", main.getGame().getImg(SetImg.hotel, "postbox.img").getIndexST(0), intown, new Vector2(15, 30));
		store.setTownWorld(world, ((954)/2-230)*bs, 100*bs, ver);store.set("", null);
		mail.setTownWorld(world, ((954)/2+230)*bs, 100*bs, ver);mail.set("", null);
		group.addActor(store);
		group.addActor(mail);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.hotel, "gatepillar.img").getIndex(0));
		image.setScale(bs);
		image.setPosition((954.0f-239.0f)*0.5f*bs, 0.0f*bs);
		addActor(image);
	//	Sprite s = main.getGame().getImg(SetImg.hotel[2]).getIndex(0);
	//	Sprite re[] = new Sprite[]{s, new Sprite(new Texture((int) s.getWidth(), (int) s.getHeight(), Format.RGBA8888))};
	//	AnActor an = new AnActor(0.5f, re);
	//	//an.setScale(bs);
	//	an.setPosition((954.0f-305.0f)*0.5f*bs, 25.0f*bs);
	//	addActor(an);
	}
	@Override
	public void change() {
		if(!changestage)
			if(ch.getY() <= -13) {
				changestage = true;
				main.setChangemusic(true);
				int town = ch.getTown();
				if(town == SetTown.elvengard) {
					main.charNewLoction(new Vector2(780, 162));
					ch.setTownPart(SetTown.elvengardstreet);
					main.change("elvengardstreet");
				}else if(town == SetTown.hendonmyre) {
					main.charNewLoction(new Vector2(1616, 295));
					ch.setTownPart(SetTown.square);
					main.change("square");
				}else if(town == SetTown.westcoast) {
					main.charNewLoction(new Vector2(910, 295));
					ch.setTownPart(SetTown.businessarea);
					main.change("businessarea");
				}else if(town == SetTown.alfhlyra) {
					main.charNewLoction(new Vector2(ch.getX(), ch.getY()));
					main.change("");
				}else if(town == SetTown.stormpass) {
					main.charNewLoction(new Vector2(ch.getX(), ch.getY()));
					main.change("");
				}else if(town == SetTown.gent) {
					main.charNewLoction(new Vector2(ch.getX(), ch.getY()));
					main.change("");
				}
			}
	}
}