package dnf.town.elvengard;

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

public class Lorien extends StageTown {
	public Lorien(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_lorien, SetTown.height_lorien);
		size_set = new float[]{0,10, 0,130, 896+224,130, 896+224,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.elvengard, "01far1.img").getIndex(0));
		background.setScale(2.0f, 1.0f);
		addActor(background);
		Image image = null;
		for(int i = 0; i < 2; i++) {
			image = new Image(main.getGame().getImg(SetImg.elvengard, "01mid1.img").getIndex(0));
			image.setPosition(640*i, 560-380);
			addActor(image);
		}
		for(int i = 0; i < 5; i++) {
			image = new Image(main.getGame().getImg(SetImg.elvengard, "00tile.img").getIndex(0));
			image.setPosition(224*i, 0);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.elvengard, "elvengardobject05.img").getIndex(0));
			image.setPosition(224*i, 30);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.elvengard, "01obj101.img").getIndex(0));
		image.setPosition(250, 140);
		addActor(image);
		image = new Image(main.getGame().getImg(SetImg.elvengard, "elvenrdg.img").getIndex(0));
		image.setPosition(710+224, 140);
		addActor(image);
	}
	@Override
	public void setFront() {
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(1560-1, ch.getY()));
				ch.setTownPart(SetTown.elvengardstreet);
				main.change("elvengardstreet");
			}else if(ch.getX() >= 889+224) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(888+224, ch.getY()));
				main.change("map");
			}
		}
	}
}