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
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetTown;
import dnf.town.ScreenTown;
import dnf.town.StageTown;

public class ElvengardCross extends StageTown {
	public ElvengardCross(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_elvengardcross, SetTown.height_elvengardcross);
		size_set = new float[]{170,50, 170,170, 200,205, 220,205, 250,170, 600+224,170,
				650+224,160, 896+224,160, 896+224,50, 896+224,30, 560,30, 500,50, 170,50};
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
		for(int i = 0; i < 5; i++){
			image = new Image(main.getGame().getImg(SetImg.elvengard, "00tile.img").getIndex(1));
			image.setPosition(224*i, 0);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvenpath.img").getIndex(0));
		image.setPosition(140, 190);
		addActor(image);
		Sprite s = main.getGame().getImg(SetImg.elvengard, "elvenpath.img").getIndex(1);
		AnActor an = new AnActor(0.5f, new Sprite[]{s, new Sprite(new Texture((int) s.getWidth(), (int) s.getHeight(), Format.RGBA8888))});
		an.setPosition(150, 190+68);
		addActor(an);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj101.img").getIndex(0));
		image.setPosition(-30, 140);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj102.img").getIndex(0));
		image.setPosition(300, 180);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj111.img").getIndex(0));
		image.setPosition(400, 170);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "00obj2.img").getIndex(0));
		image.setPosition(725, 175);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj109.img").getIndex(0));
		image.setPosition(825, 170);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj106.img").getIndex(0));
		image.setPosition(940, 190);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj107.img").getIndex(0));
		image.setPosition(1050, 170);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvenrtw.img").getIndex(0));
		image.setPosition(960, 160);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject05.img").getIndex(0));
		image.setPosition(0, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject06.img").getIndex(0));
		image.setPosition(224, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject07.img").getIndex(0));
		image.setPosition(224*2, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject08.img").getIndex(0));
		image.setPosition(224*3, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject05.img").getIndex(0));
		image.setPosition(224*4, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject06.img").getIndex(0));
		image.setPosition(224*5, 50);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj111.img").getIndex(0));
		image.setPosition(-130, 70);
		addActor(image);
	}
	@Override
	public void setFront() {
		Image image = new Image(main.getGame().getImg(SetImg.elvengard, "egobj01.img").getIndex(0));
		addActor(image);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() >= 198) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(980, -12));
				ch.setTownPart(SetTown.elvengardstreet);
				main.change("elvengardstreet");
			}
		}
	}
}