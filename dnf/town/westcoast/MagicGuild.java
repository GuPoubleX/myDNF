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

public class MagicGuild extends StageTown {
	public MagicGuild(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_magicguild, SetTown.height_magicguild);
		size_set = new float[]{0,10, 0,150, 393,150, 393-100,150+50, 393-30,150+50, 393+70,150,
				1290,150, 1290-100,150+50, 1290-30,150+50, 1290+60,150,
				1800,150, 1800,10, 350+90,10, 350+90,-20, 350,-20, 350,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.westcoast, "hmfar02.img").getIndex(0));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 3; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 120+60);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree00.img").getIndex(0));
		image.setPosition(100, 120+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree01.img").getIndex(0));
		image.setPosition(224*7, 120+30);
		addActor(image);
		for(int i = 0; i < 9; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 18);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, -102);
			addActor(image);
			if(i == 1) {
				image = new Image(main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(1));
				image.setPosition(224*i, 120+30);
				super.addActor(image);
				Sprite sp = main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(4);
				AnActor an = new AnActor(0.5f, new Sprite[]{sp,
						new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
				an.setPosition(224*i+60, 120+30+245-36-8);
				addActor(an);
			}else if(i == 5) {
				image = new Image(main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(2));
				image.setPosition(224*i, 120+30);
				addActor(image);
				Sprite sp = main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(4);
				AnActor an = new AnActor(0.5f, new Sprite[]{sp,
						new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
				an.setPosition(224*i+60, 120+30+245-36-8);
				addActor(an);
			}else {
				image = new Image(main.getGame().getImg(SetImg.westcoast, "hmwall02.img").getIndex(0));
				image.setPosition(224*i, 120+30);
				addActor(image);
			}
		}
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmstreetlamp.img").getIndex(0));
		image.setPosition(30+180, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(1));
		image.setPosition(224+100, 120+30+13);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmobj01.img").getIndex(19));
		image.setPosition(224*3-200, 120+30+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(0));
		image.setPosition(224*3-210, 120+30+45);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(3));
		image.setPosition(224*3-115, 120+30+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgflowerpot.img").getIndex(0));
		image.setPosition(224*3-60, 120+30+20);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(1));
		image.setPosition(224*4, 120+30+16);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbggrass.img").getIndex(0));
		image.setPosition(224*4+100, 120+30+20);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtree01.img").getIndex(0));
		image.setPosition(224*3+150, 120+30+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(3));
		image.setPosition(224*4+150+50, 120+30+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(3));
		image.setPosition(224*4+150-10, 120+30+30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmstreetlamp.img").getIndex(0));
		image.setPosition(224*6-20, 120+30+15);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40+50, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40+50, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40, 120+30+25);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40+50, 120+30+25+32);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40, 120+30+25+32);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmbgbox.img").getIndex(1));
		image.setPosition(224*6+40+30, 120+30+25+32+32);
		addActor(image);
	//	image = new Image(main.getGame().getImg(SetImg.westcoast[10]).getIndex(3));
	//	image.setPosition(1800-164-50, 120+30+25);
	//	addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC sr = new NPC(main.getGame(), SetNPC._sr, main.getGame().getImg(SetImg.npc+"npc_sr", "npc_sr.img"),
				intown, new Vector2(0, 65), 50);
		sr.setTownWorld(world, 765, 170, ver);sr.set("", null);sr.lookat(ch);group.addActor(sr.getShadow());
		group.addActor(sr);
		NPC ir = new NPC(main.getGame(), SetNPC._ir, main.getGame().getImg(SetImg.npc+"npc_ir", "npc_ir.img"),
				intown, new Vector2(-5, 55), 25);
		ir.setTownWorld(world, 965, 170, ver);ir.set("", null);ir.lookat(ch);group.addActor(ir.getShadow());
		group.addActor(ir);
		NPC dj3 = new NPC(main.getGame(), SetNPC._dj3, main.getGame().getImg(SetImg.npc+"npc_dj3", "npc_dj3.img"),
				intown, new Vector2(0, 55), 25);
		dj3.setTownWorld(world, 1550, 170, ver);dj3.set("", null);dj3.lookat(ch);group.addActor(dj3.getShadow());
		group.addActor(dj3);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(0));
		image.setPosition(224+100, 0);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.westcoast, "hendonpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224+100+23, 98-11);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() <= -12) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(345, 295));
				ch.setTownPart(SetTown.businessarea);
				main.change("businessarea");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1110, ch.getY()));
				ch.setTownPart(SetTown.deadcanyon);
				main.change("deadcanyon");
			}
		}
	}
}