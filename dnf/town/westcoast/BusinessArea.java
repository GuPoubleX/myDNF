package dnf.town.westcoast;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
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

public class BusinessArea extends StageTown {
	public BusinessArea(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_businessarea, SetTown.height_businessarea);
		size_set = new float[]{0,10, 0,258, 395,258, 395-100,258+50, 395-30,258+50, 395+70,258,
				835,258, 835+35,305, 835+105,305, 835+140,258,
				3100,258, 3100,10, 2590+90,10, 2590+90,-20, 2590,-20, 2590,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.westcoast, "hmfar02.img").getIndex(0));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 5; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 138+120+30);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree00.img").getIndex(0));
		image.setPosition(100, 138+120);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree01.img").getIndex(0));
		image.setPosition(224*7, 138+120);
		addActor(image);
		for(int i = 0; i < 14; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, 0);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 120);
			addActor(image);
			if(i == 1) {
				image = new Image(main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(1));
				image.setPosition(224*i, 138+120);
				addActor(image);
			}else if(i != 13) {
				image = new Image(main.getGame().getImg(SetImg.westcoast, "hmwall02.img").getIndex(0));
				image.setPosition(224*i, 138+120);
				addActor(image);
			}
		}
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtileex.img").getIndex(0));
		image.setPosition(224*13, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbrrtile02.img").getIndex(0));
		image.setPosition(224*13, 120);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbrrtile01.img").getIndex(0));
		image.setPosition(224*13, 120+138);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(4);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224+60, 120+138+245-36-8);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmpoll.img").getIndex(0));
		image.setPosition(30, 120+138+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmstreetlamp.img").getIndex(0));
		image.setPosition(30+180, 120+138+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(1));
		image.setPosition(224+100, 120+138+13);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(0));
		image.setPosition(224*2+100, 120+138+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmseriahouse.img").getIndex(0));
		image.setPosition(224*3+10, 120+138-30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(3));
		image.setPosition(224*3+471, 120+138+5);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmstreetlamp.img").getIndex(0));
		image.setPosition(224*3+471, 120+138+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*3+471+470+30, 120+138+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(0));
		image.setPosition(224*3+471+470+5, 120+138+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(1));
		image.setPosition(224*3+471+470+6, 120+138+13);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(12));
		image.setPosition(224*3+471+470+115, 120+138+5);
		addActor(image);
		an = new AnActor(0.2f, new Sprite[]{
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(5),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(6),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(7),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(8),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(9),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(10),
				main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(11)});
		an.setPosition(224*3+471+470+115+81, 120+138+5+40);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree00.img").getIndex(0));
		image.setPosition(224*3+471+470+550+50+240, 120+138+35);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "goldencart.img").getIndex(0));
		image.setPosition(224*3+471+470+550+50, 120+138+30);
		addActor(image);
		an = new AnActor(0.25f, new Sprite[]{
				main.getGame().getImg(SetImg.westcoast, "horsemane.img").getIndex(0),
				main.getGame().getImg(SetImg.westcoast, "horsemane.img").getIndex(1),
				main.getGame().getImg(SetImg.westcoast, "horsemane.img").getIndex(2),
				main.getGame().getImg(SetImg.westcoast, "horsemane.img").getIndex(3)});
		an.setPosition(224*3+471+470+550+50, 120+138+164-35);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(2));
		image.setPosition(224*3+471+470+550+50+380, 120+138+45);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(0));
		image.setPosition(3100-200-164+60, 120+138+35);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(1));
		image.setPosition(3100-200-164-10, 120+138+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmstreetlamp.img").getIndex(1));
		image.setPosition(3100-200-164+20, 120+138+25);
		addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC lo = new NPC(main.getGame(), SetNPC._lo, main.getGame().getImg(SetImg.npc+"npc_lo", "npc_lo.img"),
				intown, new Vector2(15, 65), 20);
		lo.setTownWorld(world, 1395, 270, ver);lo.set("", null);lo.lookat(ch);group.addActor(lo.getShadow());
		group.addActor(lo);
		NPC kg = new NPC(main.getGame(), SetNPC._kg, main.getGame().getImg(SetImg.npc+"npc_kg", "npc_kg.img"),
				intown, new Vector2(0, 65), 40);
		kg.setTownWorld(world, 1955, 270, ver);kg.set("", null);kg.lookat(ch);group.addActor(kg.getShadow());
		group.addActor(kg);
		NPC rg = new NPC(main.getGame(), SetNPC._rg, main.getGame().getImg(SetImg.npc+"npc_rg", "npc_rg.img"),
				intown, new Vector2(0, 75), 60);
		rg.setTownWorld(world, 2340, 270, ver);rg.set("", null);rg.lookat(ch);group.addActor(rg.getShadow());
		group.addActor(rg);
		NPC dp = new NPC(main.getGame(), SetNPC._dp, main.getGame().getImg(SetImg.npc+"npc_dp", "npc_dp.img"),
				intown, new Vector2(-5, 50), 20);
		dp.setTownWorld(world, 2530, 270, ver);dp.set("", null);dp.lookat(ch);group.addActor(dp.getShadow());
		group.addActor(dp);
		NPC dj2 = new NPC(main.getGame(), SetNPC._dj2, main.getGame().getImg(SetImg.npc+"npc_dj2", "npc_dj2.img"),
				intown, new Vector2(0, 55), 25);
		dj2.setTownWorld(world, 2820, 270, ver);dj2.set("", null);dj2.lookat(ch);group.addActor(dj2.getShadow());
		group.addActor(dj2);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(0));
		image.setPosition(224*11+100, 0);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*11+100+23, 98-11);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(3585, ch.getY()));
				ch.setTownPart(SetTown.square);
				ch.setTown(SetTown.hendonmyre);
				main.change("square");
			}else if(ch.getX() > 700 && ch.getX() < 1000 && ch.getY() >= 298) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(477*bs, -12));
				ch.setTownPart(SetTown.hotel);
				main.change("hotel");
			}else if(ch.getX() < 460 && ch.getY() > 300) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(395, -10));
				ch.setTownPart(SetTown.magicguild);
				main.change("magicguild");
			}
		}
	}
}