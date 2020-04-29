package dnf.town.westcoast;

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

public class Deadcanyon extends StageTown {
	public Deadcanyon(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_deadcanyon, SetTown.height_deadcanyon);
		size_set = new float[]{0,10, 0,150, 896+224,150, 896+224,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(main.getGame().getImg(SetImg.westcoast, "hmdtfar00.img").getIndex(0));
		background.setScale(1.4f, 1.0f);
		addActor(background);
		Image image = null;
		for(int i = 0; i < 3; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmmid01.img").getIndex(0));
			image.setPosition(895*i, 120+60);
			addActor(image);
		}
		for(int i = 0; i < 6; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtile.img").getIndex(0));
			image.setPosition(224*i, 18);
			addActor(image);
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmtileex.img").getIndex(0));
			image.setPosition(224*i, -102);
			addActor(image);
		}
		for(int i = 0; i < 6; i++) {
			image = new Image(main.getGame().getImg(SetImg.westcoast, "hmdtwall00.img").getIndex(0));
			image.setPosition(242*i, 120+5+30);
			addActor(image);
		}
		image = new Image(main.getGame().getImg(SetImg.westcoast, "hmpoll.img").getIndex(2));
		image.setPosition(20, 120+25);
		addActor(image);
	}
	@Override
	public void setFront() {
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() > 1113) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(10, ch.getY()));
				ch.setTownPart(SetTown.magicguild);
				main.change("magicguild");
			}
		}
	}
}