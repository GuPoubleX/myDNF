package dnf.town.hendonmyre;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dnf.author.AnActor;
import dnf.character.Character;
import dnf.gupoublex.set.SetTown;
import dnf.town.ScreenTown;
import dnf.town.StageTown;

public class BackStreet extends StageTown {
	public BackStreet(ScreenTown main, Vector2 ver,Batch batch, float scale, Character ch, Array<Character> all, Vector2 charLocation, HashMap<String, String> npc) {
		super(main, ver, batch, scale, ch, all, charLocation, npc);
	}
	@Override
	public void setInfo() {
		size = new Vector2(SetTown.width_backstreet, SetTown.height_backstreet);
		size_set = new float[]{0,10, 0,200, 925,200, 1120,95, 1120,10, 480+90,10,
				480+90,-20, 480,-20, 480,10, 0,10};
	}
	@Override
	public void setGround() {
		background = new Image(atlas.findRegion("hendonmyre/69"));
		background.setScale(1.4f, 1.0f);
		addActor(background);
		Image image = null;
		for(int i = 0; i < 2; i++) {
			image = new Image(atlas.findRegion("hendonmyre/83"));
			image.setPosition(895*i, 120+130);
			addActor(image);
		}
		for(int i = 0; i < 5; i++) {
			image = new Image(atlas.findRegion("hendonmyre/85"));
			image.setPosition(224*i, 0);
			addActor(image);
		}
		image = new Image(atlas.findRegion("hendonmyre/70"));
		image.setPosition(0, 170);
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/71"));
		image.setPosition(349, 190);
		addActor(image);
		image = new Image(atlas.findRegion("hendonmyre/72"));
		image.setPosition(349+382, 36);
		addActor(image);
	}
	@Override
	public void setFront() {
		Image image = new Image(atlas.findRegion("hendonmyre/84"));
		image.setPosition(450, 0);
		super.addActor(image);
		AnActor an = new AnActor(0.5f, new TextureRegion[]{atlas.findRegion("hendonmyre/18"),new TextureRegion(new Texture(
				atlas.findRegion("hendonmyre/18").getRegionWidth(), atlas.findRegion("hendonmyre/18").getRegionHeight(), Format.RGBA8888))});
		an.setPosition(450+23, 98-11);
		addActor(an);
	}
	@Override
	public void change() {
		if(!changestage) {
			if(ch.getY() <= -13) {
				changestage = true;
				main.setChangemusic(false);
				main.charNewLoction(new Vector2(999, 192));
				ch.setTownPart(SetTown.oldcity);
				main.change("oldcity");
			}
		}
	}
}