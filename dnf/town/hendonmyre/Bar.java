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

public class Bar extends StageTown {
	public Bar(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_bar, SetTown.height_bar);
		size_set = new float[]{105,10, 105,185, 180,185, 265,215, 580,215, 520,185,
				825,185, 865,200, 1150,200, 1170,180, 1260,180, 1260,140, 1325,70, 1325,10, 105,10};
	}
	@Override
	public void setGround() {
		Image image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbarfar00.img").getIndex(0));
		image.setPosition(200, 207);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbarmid00.img").getIndex(0));
		image.setPosition(150, 200);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbarmid00.img").getIndex(0));
		image.setPosition(150+311, 200);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbarmid00.img").getIndex(0));
		image.setPosition(150+311*2, 200);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbarmid00.img").getIndex(0));
		image.setPosition(150+311*3, 200);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile00.img").getIndex(0));
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile01.img").getIndex(0));
		image.setPosition(224, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile02.img").getIndex(0));
		image.setPosition(224*2, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile03.img").getIndex(0));
		image.setPosition(224*3, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile04.img").getIndex(0));
		image.setPosition(224*4, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmbartile06.img").getIndex(0));
		image.setPosition(224*5, 0);
		addActor(image);
		Sprite sp = main.getGame().getImg(SetImg.hendonmyre, "pubentrancelight.img").getIndex(0);
		AnActor an = new AnActor(0.5f, new Sprite[]{sp,
				new Sprite(new Texture((int) sp.getWidth(), (int) sp.getHeight(), Format.RGBA8888))});
		an.setPosition(224*5+98, 345);
		addActor(an);
	}
	@Override
	public void setMiddle() {
		super.setMiddle();
		boolean intown = true;
		NPC su = new NPC(main.getGame(), SetNPC._su, main.getGame().getImg(SetImg.npc+"npc_su", "npc_su.img"),
				intown, new Vector2(10, 65), 40);
		su.setTownWorld(world, 945, 210, ver);su.set("", null);su.lookat(ch);group.addActor(su.getShadow());
		group.addActor(su);
	}
	@Override
	public void setFront() {
	}
	@Override
	public void change() {
		if(!changestage)
			if(ch.getX() < 115 && ch.getY() > 175) {
				changestage = true;
				main.setChangemusic(true, false);
				main.charNewLoction(new Vector2(3275, 285));
				ch.setTownPart(SetTown.square);
				main.change("square");
			}
	}
}