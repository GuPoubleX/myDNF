package dnf.town.hendonmyre;

import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dnf.character.Character;
import dnf.gupoublex.set.SetTown;
import dnf.town.ScreenTown;
import dnf.town.StageTown;

public class Granfloris2 extends StageTown {
	public Granfloris2(ScreenTown main, Vector2 ver, Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_granfloris, SetTown.height_granfloris);
		size_set = new float[]{0,10, 0,200, 300,200, 350,240, 896,240, 896,50, 530,50,
			440,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(atlas.findRegion("hendonmyre/23"));
		addActor(background);
		Image image = null;
		for(int i = 0; i < 2; i++) {
			image = new Image(atlas.findRegion("hendonmyre/8"));
			image.setPosition(895*i, 200);
			addActor(image);}
		image = new Image(atlas.findRegion("hendonmyre/59"));
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/56"));
		image.setPosition(224, 0);
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/57"));
		image.setPosition(224*2, 0);
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/58"));
		image.setPosition(224*3, 0);
		addActor(image);
	}
	@Override
	public void setFront() {
		Image image = new Image(atlas.findRegion("hendonmyre/86"));
		image.setPosition(896-224-147, 20);
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/87"));
		image.setPosition(896-224, 20-8);
		addActor(image);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getX() >= 889) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(8, ch.getY()>=143?143:ch.getY()));
				ch.setTownPart(SetTown.municipal);
				main.change("municipal");
			}else if(ch.getX() <= 7) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(8, ch.getY()));
				main.change("map");
			}
		}
	}
}