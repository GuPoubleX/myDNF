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

public class Square extends StageTown {
	public Square(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_square, SetTown.height_square);
		size_set = new float[]{0,10, 0,258, 1290,258, 1290-100,258+50, 1290-30,258+50,
				1290+70,258, 1550,258, 1550+35,305, 1550+105,305, 1550+140, 258,3308,
				258, 3308-80,258+40, 3310,258+40, 3310+90,258, 3600,258, 3600,10,
				1295+90,10, 1295+90,-20, 1295,-20, 1295,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmfar02.img").getIndex(0));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 5; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 138+120+30);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbrltile01.img").getIndex(0));
		image.setPosition(0, 120);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "brtile03.img").getIndex(0));
		image.setPosition(0, 0);
		addActor(image);
		for(int i = 1; i < 17; i++) {
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 120);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, 0);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "brtile03.img").getIndex(1));
		image.setPosition(224, 0);
		addActor(image);
		for(int i = 0; i < 17; i++) {
			if(i == 5) {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(1));
				image.setPosition(224*i, 138+120);
				addActor(image);
			}else {
				image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmwall02.img").getIndex(0));
				image.setPosition(224*i, 138+120);
				addActor(image);
			}
		}
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(4);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*5+60, 138+120+245-36-8);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(13));
		image.setPosition(224*2-140, 138+120+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbgflowerpot.img").getIndex(0));
		image.setPosition(224*2-140-34, 138+120+45);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*2-140, 138+120+40);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*2+80, 138+120+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstreetlamp.img").getIndex(0));
		image.setPosition(224*2-100, 138+120+20);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(2));
		image.setPosition(224*3-50, 138+120+20);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbgbox.img").getIndex(3));
		image.setPosition(224*3-10+66-10-65, 138+120+28);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbgbox.img").getIndex(4));
		image.setPosition(224*3-10-65, 138+120+28);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstreetlamp.img").getIndex(1));
		image.setPosition(224*3+270, 138+120+20);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*4+100, 138+120+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmseriahouse.img").getIndex(0));
		image.setPosition(224*6+50, 138+120-30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(14));
		image.setPosition(224*8+30, 138+120+18);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*8+95, 138+120+10);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmplazawall01.img").getIndex(0));
		image.setPosition(224*11-50-160, 10+22);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmplazawall02.img").getIndex(0));
		image.setPosition(224*11+628-50-160, 22);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmplaza.img").getIndex(0));
		image.setPosition(224*11+50-160, 138+120+2);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmplazatest.img").getIndex(0));
		image.setPosition(224*14-140, 138+120+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmobj01.img").getIndex(1));
		image.setPosition(224*15-275, 138+120);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstreetlamp.img").getIndex(1));
		image.setPosition(224*15+50-5, 138+120+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(0));
		image.setPosition(224*15+40-15, 138+120+16);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmpoll.img").getIndex(1));
		image.setPosition(224*15+100-30, 138+120+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbggrass.img").getIndex(1));
		image.setPosition(224*15+175-30, 138+120+20);
		addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC dj1 = new NPC(main.getGame(), SetNPC._dj1, main.getGame().getImg(SetImg.npc+"npc_dj1", "npc_dj1.img"),
				intown, new Vector2(0, 55), 30);
		dj1.setTownWorld(world, 185, 270, ver);dj1.set("", null);dj1.lookat(ch);group.addActor(dj1.getShadow());
		group.addActor(dj1);
		NPC pj = new NPC(main.getGame(), SetNPC._pj, main.getGame().getImg(SetImg.npc+"npc_pj", "npc_pj.img"),
				intown, new Vector2(0, 63), 30);
		pj.setTownWorld(world, 495, 270, ver);pj.set("", null);pj.lookat(ch);group.addActor(pj.getShadow());
		group.addActor(pj);
		NPC rt = new NPC(main.getGame(), SetNPC._rt, main.getGame().getImg(SetImg.npc+"npc_rt", "npc_rt.img"),
				intown, new Vector2(-3, 45), 10);
		rt.setTownWorld(world, 840, 270, ver);rt.set("", null);rt.lookat(ch);group.addActor(rt.getShadow());
		group.addActor(rt);
		NPC kr = new NPC(main.getGame(), SetNPC._kr, main.getGame().getImg(SetImg.npc+"npc_kr", "npc_kr.img"),
				intown, new Vector2(0, 75), 45);
		kr.setTownWorld(world, 2050, 270, ver);kr.set("", null);kr.lookat(ch);group.addActor(kr.getShadow());
		group.addActor(kr);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.hendonmyre, "brwall00.img").getIndex(0));
		image.setPosition(0, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "brwall02.img").getIndex(2));
		image.setPosition(224, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(0));
		image.setPosition(224*5+150, 0);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "hendonpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*5+150+23, 98-11);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() <= -13) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1231, 192));
				ch.setTownPart(SetTown.municipal);
				main.change("municipal");
			}else if(ch.getX() > 1500 && ch.getY() >= 298) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(477*bs, -12));
				ch.setTownPart(SetTown.hotel);
				main.change("hotel");
			}else if(ch.getX() < 1500 && ch.getY() >= 301) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1115, -12));
				ch.setTownPart(SetTown.oldcity);
				main.change("oldcity");
			}else if(ch.getX() > 3270 && ch.getY() >= 290) {
				changestage = true;
				main.setChangemusic(true, true);
				main.charNewLoction(new Vector2(125, 170));
				ch.setTownPart(SetTown.bar);
				main.change("bar");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(888, ch.getY()>=233?233:ch.getY()<=56?56:ch.getY()));
				ch.setTownPart(SetTown.granfloris1);
				main.change("granfloris1");
			}else if(ch.getX() >= 3590) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(10, ch.getY()));
				ch.setTownPart(SetTown.businessarea);
				ch.setTown(SetTown.westcoast);
				main.change("businessarea");
			}
		}
	}
}