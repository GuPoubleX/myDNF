package dnf.town.hendonmyre;

import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dnf.character.Character;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetTown;
import dnf.town.ScreenTown;
import dnf.town.StageTown;

public class HendonmyreCross extends StageTown {
	public HendonmyreCross(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_hendonmyrecross, SetTown.height_hendonmyrecross);
		size_set = new float[]{0,10, 0,145, 1120,145, 1120,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmfar02.img").getIndex(0));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 2; i++) {
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
			image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmwall02.img").getIndex(0));
			image.setPosition(224*i, 120+30);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstwaywall01.img").getIndex(0));
		image.setPosition(0, 0+10);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstwaywall02.img").getIndex(0));
		image.setPosition(225, 104+10);
		addActor(image);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstwayfence01.img").getIndex(0));
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstwayfence01.img").getIndex(0));
		image.setPosition(210, 0);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.hendonmyre, "hmstwayfence01.img").getIndex(0));
		image.setPosition(210*2, 0);
		addActor(image);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() >= 1113) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(8, ch.getY()));
				ch.setTownPart(SetTown.oldcity);
				main.change("oldcity");
			}
		}
	}
}