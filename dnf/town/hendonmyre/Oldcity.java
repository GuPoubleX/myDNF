package dnf.town.hendonmyre;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class Oldcity extends StageTown {
	public Oldcity(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_oldcity, SetTown.height_oldcity);
		size_set = new float[]{0,10, 0,145, 1075,145, 1075-115,140+60, 1075-45,140+60,
				1075+70,145, 2100,145, 2240,70, 2240,10, 1070+90,10, 1070+90,-20,
				1070,-20, 1070,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmfar02.img").getIndex(0));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 5; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 120+60);
			addActor(image);
		}
		for(int i = 0; i < 10; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 18);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, -102);
			addActor(image);
			if(i == 4) {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(1));
				image.setPosition(224*i, 120+30);
				addActor(image);
			}else {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmwall02.img").getIndex(0));
				image.setPosition(224*i, 120+30);
				addActor(image);
			}
		}
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*4+60, 120+30+245-36-8);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(3));
		image.setPosition(224*2, 120+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstreetlamp.img").getIndex(0));
		image.setPosition(224*2+30, 120+30+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*2-10, 120+30+10);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*2+25, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(20));
		image.setPosition(2240-231, 15);
		addActor(image);
		an = new AnActor(0.2f, new Sprite[]{
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(15),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(16),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(17),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(18)});
		an.setPosition(2240-231-416+20, 120+30-7);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(12));
		image.setPosition(224*5+90, 120+30);
		addActor(image);
		an = new AnActor(0.2f, new Sprite[]{
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(5),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(6),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(7),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(8),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(9),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(10),
				main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(11)});
		an.setPosition(224*5+90+81, 120+30+40);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*5+30, 120+30+5);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*5+65, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*8+30, 120+30-10);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*8+155, 120+30-5);
		addActor(image);
		an = new AnActor(0.4f, new TextureRegion[]{
				main.getGame().getImg(SetImg.hendonmyre, "smallbear.img").getIndex(0),
				main.getGame().getImg(SetImg.hendonmyre, "smallbear.img").getIndex(1),
				main.getGame().getImg(SetImg.hendonmyre, "smallbear.img").getIndex(2),
				main.getGame().getImg(SetImg.hendonmyre, "smallbear.img").getIndex(3)});
		an.setPosition(300.0f-200.0f, 185.0f-35.0f);
		addActor(an);
		an = new AnActor(0.4f, new TextureRegion[]{
				main.getGame().getImg(SetImg.hendonmyre, "bigrabbit.img").getIndex(0),
				main.getGame().getImg(SetImg.hendonmyre, "bigrabbit.img").getIndex(1),
				main.getGame().getImg(SetImg.hendonmyre, "bigrabbit.img").getIndex(2),
				main.getGame().getImg(SetImg.hendonmyre, "bigrabbit.img").getIndex(3)});
		an.setPosition(300.0f-130.0f, 190.0f-35.0f);
		addActor(an);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC or = new NPC(main.getGame(), SetNPC._or, main.getGame().getImg(SetImg.npc+"npc_or", "npc_or.img"),
				intown, new Vector2(-5, 45), 0);
		or.setTownWorld(world, 185, 160, ver);or.set("", null);or.lookat(ch);group.addActor(or.getShadow());
		group.addActor(or);
		NPC kanna = new NPC(main.getGame(), SetNPC._kanna, main.getGame().getImg(SetImg.npc+"kanna", "kanna.img"),
				intown, new Vector2(0, 68), 10);
		kanna.setTownWorld(world, 700, 160, ver);kanna.set("", null);kanna.lookat(ch);group.addActor(kanna.getShadow());
		group.addActor(kanna);
		NPC sin = new NPC(main.getGame(), SetNPC._sin, main.getGame().getImg(SetImg.npc+"npc_sin", "npc_sin.img"),
				intown, new Vector2(-3, 45), 5);
		sin.setTownWorld(world, 1480, 160, ver);sin.set("", null);sin.lookat(ch);group.addActor(sin.getShadow());
		group.addActor(sin);
		NPC gsd = new NPC(main.getGame(), SetNPC._gsd, main.getGame().getImg(SetImg.npc+"npc_gsd", "npc_gsd.img"),
				intown, new Vector2(0, 40), 5);
		gsd.setTownWorld(world, 1700, 160, ver);gsd.set("", null);gsd.lookat(ch);group.addActor(gsd.getShadow());
		group.addActor(gsd);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(0));
		image.setPosition(224*4+150, 0);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*4+150+23, 98-11);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() <= -13) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1240, 300));
				ch.setTownPart(SetTown.square);
				main.change("square");
			}else if(ch.getY() >= 193) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(517, -12));
				ch.setTownPart(SetTown.backstreet);
				main.change("backstreet");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1112, ch.getY()));
				ch.setTownPart(SetTown.hendonmyrecross);
				main.change("hendonmyrecross");
			}
		}
	}
}