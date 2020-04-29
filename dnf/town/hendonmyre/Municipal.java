package dnf.town.hendonmyre;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dnf.author.AnActor;
import dnf.character.Character;
import dnf.character.other.NPC;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetNPC;
import dnf.gupoublex.set.SetTown;
import dnf.town.ScreenTown;
import dnf.town.StageTown;

public class Municipal extends StageTown {
	public Municipal(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_municipal, SetTown.height_municipal);
		size_set = new float[]{0,10, 0,150, 1290,150, 1290-100,150+50, 1290-30,150+50,
				1290+70,150, 1490,150, 1540,130, 2380,130, 2400,150, 3200,150, 3200,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmfar02.img").getIndex(0));
		background.setScale(1.4f, 1.0f);
		addActor(background);
		Image image = null;
		for(int i = 0; i < 5; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 120+60);
			addActor(image);
		}
		for(int i = 0; i < 15; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 150-image.getHeight());
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, 12-image.getHeight());
			addActor(image);
			if(i == 5) {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(1));
				image.setPosition(224*i, 120+30);
				addActor(image);
			}else {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmwall02.img").getIndex(0));
				image.setPosition(224*i, 120+30);
				addActor(image);
			}
		}
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(4);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp, new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*5+60, 120+30+245-36-8);
		super.addActor(an);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "basilica.img").getIndex(0));
		image.setPosition(30+300, 120+30+20);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(0));
		image.setPosition(224*6+30, 120+30-10);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(24));
		image.setPosition(224*6+30+640, 120+30-10);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "board.img").getIndex(0));
		image.setPosition(2150, 120+30);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtree00.img").getIndex(0));
		image.setPosition(2300, 120+30+30);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(4));
		image.setPosition(2400, 120+30+20);
		super.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmpoll.img").getIndex(1));
		image.setPosition(3000, 120+30+25);
		super.addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC al = new NPC(main.getGame(), SetNPC._al, main.getGame().getImg(SetImg.npc+"npc_al", "npc_al.img"),
				intown, new Vector2(-3, 60), 20);
		al.setTownWorld(world, 2550, 160, ver);al.set("", null);al.lookat(ch);group.addActor(al.getShadow());
		group.addActor(al);
		NPC bk = new NPC(main.getGame(), SetNPC._bk, main.getGame().getImg(SetImg.npc+"npc_bk", "npc_bk.img"),
				intown, new Vector2(0, 80), 50);
		bk.setTownWorld(world, 1620, 160, ver);bk.set("", null);bk.lookat(ch);group.addActor(bk.getShadow());
		group.addActor(bk);
		NPC gd = new NPC(main.getGame(), SetNPC._gd, main.getGame().getImg(SetImg.npc+"npc_gd", "npc_gd.img"),
				intown, new Vector2(5, 50), 20);
		gd.setTownWorld(world, 705, 160, ver);gd.set("", null);gd.lookat(ch);group.addActor(gd.getShadow());
		group.addActor(gd);
	}
	@Override
	public void setFront() {
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() >= 3193) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(8, ch.getY()>=123?123:ch.getY()));
				ch.setTownPart(SetTown.elvengardstreet);
				ch.setTown(SetTown.elvengard);
				main.change("elvengardstreet");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(888, ch.getY()<=57?57:ch.getY()));
				ch.setTownPart(SetTown.granfloris2);
				main.change("granfloris2");
			}else if(ch.getY() >= 193) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1335, -12));
				ch.setTownPart(SetTown.square);
				main.change("square");
			}
		}
	}
}