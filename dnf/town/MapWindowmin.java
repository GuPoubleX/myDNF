package dnf.town;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import dnf.gupoublex.GuPoubleXGame;

public class MapWindowmin extends Window {
	public MapWindowmin(WindowStyle style, String mapstr, String bg, String bgname, GuPoubleXGame game, int id, boolean ac, boolean d) {
		super("", style);
		setSize(116+(ac||d?2:0), 49+(ac||d?13:0));
		Image image = new Image(game.getImg(bg, bgname+".img").getIndex(id));
		image.setScale((getWidth()-(ac||d?2:0))/image.getWidth(), (getHeight()-(ac||d?13:0))/image.getHeight());
		image.setPosition(getWidth()/2-image.getWidth()/2*image.getScaleX(), getHeight()-image.getHeight()*image.getScaleY()-(ac||d?13:0));
		addActor(image);
		image = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(0));
		image.setScale((getWidth()-(ac||d?2:0))/image.getWidth(), (getHeight()-(ac||d?13:0))/image.getHeight());
		image.setPosition(getWidth()/2-image.getWidth()/2*image.getScaleX(), getHeight()-image.getHeight()*image.getScaleY()-(ac||d?13:0));
		addActor(image);
		if(ac || d) {
			int i = 6;
			if(ac)
				i = 8;
			image = new Image(game.getImg(mapstr, "selectdungeonwarning.img").getIndex(i));
			image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()-image.getHeight());
			addActor(image);
		}
	}
}