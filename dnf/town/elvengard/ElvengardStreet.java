package dnf.town.elvengard;

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

public class ElvengardStreet extends StageTown {
	public ElvengardStreet(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_elvengardstreet, SetTown.height_elvengardstreet);
		size_set = new float[]{0,10, 0,130, 700,130, 700+60,130+40, 700+105,130+40,
				700+165,130, 1568,130, 1568,10, 935+90,10, 935+90,-20, 935,-20, 935,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.elvengard, "01far1.img").getIndex(0));
		background.setScale(2.0f, 1.0f);
		addActor(background);
		Image image = null;
		for(int i = 0; i < 3; i++) {
			image = new Image(main.getGame().getImg(SetImg.elvengard, "01mid1.img").getIndex(0));
			image.setPosition(640*i, 560-380);
			super.addActor(image);
		}
		for(int i = 0; i < 7; i++) {
			image = new Image(main.getGame().getImg(SetImg.elvengard, "00tile.img").getIndex(0));
			image.setPosition(224*i, 0);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject05.img").getIndex(0));
		image.setPosition(224*0, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject06.img").getIndex(0));
		image.setPosition(224*1, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject07.img").getIndex(0));
		image.setPosition(224*2, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject08.img").getIndex(0));
		image.setPosition(224*3, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject05.img").getIndex(0));
		image.setPosition(224*4, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject06.img").getIndex(0));
		image.setPosition(224*5, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject07.img").getIndex(0));
		image.setPosition(224*6, 30);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvenltw.img").getIndex(0));
		image.setPosition(20, 140);
		addActor(image);
		Sprite s[] = new Sprite[3];
		s[0] = main.getGame().getImg(SetImg.elvengard, "blacksmith00.img").getIndex(0);
		s[1] = main.getGame().getImg(SetImg.elvengard, "blacksmith01.img").getIndex(0);
		s[2] = main.getGame().getImg(SetImg.elvengard, "blacksmith02.img").getIndex(0);
		AnActor an = new AnActor(0.2f, s);
		an.setPosition(210.0f, 140.0f);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "flags.img").getIndex(0));
		image.setPosition(470, 150);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "egseriahouse.img").getIndex(0));
		image.setPosition(560, 140);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj001.img").getIndex(0));
		image.setPosition(1100, 130);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj101.img").getIndex(0));
		image.setPosition(1160, 150);
		addActor(image);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC linus = new NPC(main.getGame(), SetNPC._linus, main.getGame().getImg(SetImg.npc+"linus", "linus.img"),
				intown, new Vector2(-3, 65), 20);
		linus.setTownWorld(world, 460, 140, ver);linus.set("", null);linus.lookat(ch);group.addActor(linus.getShadow());
		group.addActor(linus);
		NPC dj = new NPC(main.getGame(), SetNPC._dj, main.getGame().getImg(SetImg.npc+"npc_dj", "npc_dj.img"),
				intown, new Vector2(0, 55), 25);
		dj.setTownWorld(world, 1320, 140, ver);dj.set("", null);dj.lookat(ch);group.addActor(dj.getShadow());
		group.addActor(dj);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.elvengard, "elvenpath.img").getIndex(2));
		image.setPosition(224*4, 0);
		addActor(image);
		Sprite s = main.getGame().getImg(SetImg.elvengard, "elvenpath.img").getIndex(3);
		AnActor an = new AnActor(0.5f, new Sprite[]{s,
				new Sprite(new Texture((int) s.getWidth(), (int) s.getHeight(), Format.RGBA8888))});
		an.setPosition(224*4+14, 0);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() >= 163) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(477*bs, -12));
				ch.setTownPart(SetTown.hotel);
				main.change("hotel");
			}else if(ch.getX() >= 1560) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(8, ch.getY()));
				ch.setTownPart(SetTown.lorien);
				main.change("lorien");
			}else if(ch.getY() <= -13) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(209, 197));
				ch.setTownPart(SetTown.elvengardcross);
				main.change("elvengardcross");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(true);
				main.charNewLoction(new Vector2(3192, ch.getY()));
				ch.setTownPart(SetTown.municipal);
				ch.setTown(SetTown.hendonmyre);
				main.change("municipal");
			}
		}
	}
}