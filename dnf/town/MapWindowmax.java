package dnf.town;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.GuPoubleXGame;

public class MapWindowmax extends Window {
	private MapLevel level = null;
	private MapChose chose = null;
	private String name = null;
	private StageMap map = null;
	private Image lvimg = null;
	private Animation<Sprite> an = null;
	private Array<Integer> info = null;
	public MapWindowmax(WindowStyle style, int id, boolean ac, boolean d, String mapstr, String bg, String bgname, GuPoubleXGame game,
			String name, int lv, StageMap map, Array<Integer> info) {
		super("", style);
		this.name = name;
		this.info = info;
		this.map = map;
		level = new MapLevel(style, mapstr, bg, game, lv, map, this);
		setSize(164+(ac||d?4:0), 69+(ac||d?25:0)+level.getHeight()/2*level.getScaleY());
		Image image = new Image(game.getImg(bg, bgname+".img").getIndex(id));
		image.setScale(getWidth()/image.getWidth());
		image.setPosition(getWidth()/2-image.getWidth()/2*image.getScaleX(),
				getHeight()-image.getHeight()*image.getScaleY()-(ac||d?19:-2));
		addActor(image);
		Image images = new Image(game.getImg(mapstr, "selectdungeon02.img").getIndex(0));
		images.setScale((getWidth()-(ac||d?2:0))/images.getWidth(),
				(getHeight()-(ac||d?22:0)-level.getHeight()/2*level.getScaleY())/images.getHeight());
		images.setPosition(getWidth()/2-images.getWidth()*images.getScaleX()/2,
				getHeight()-images.getHeight()*images.getScaleY()-(ac||d?23:0));
		addActor(images);
		level.setPosition(getWidth()/2-level.getWidth()/2*level.getScaleX(), (ac||d)?2:2);
		addActor(level);
		chose = new MapChose(style, game, map);
		chose.setPosition(getWidth()/2-chose.getWidth()/2, level.getY()+level.getHeight());
		addActor(chose);
		if(ac || d) {
			int i = 5;
			if(ac)
				i = 7;
			image = new Image(game.getImg(mapstr, "selectdungeonwarning.img").getIndex(i));
			image.setPosition(getWidth()/2-image.getWidth()/2, getHeight()-image.getHeight()+5);
			addActor(image);
		}
		Sprite sp[] = new Sprite[4];
		sp[0] = new Sprite(new Texture(61, 25, Format.RGBA8888));
		sp[1] = new Sprite(game.getImg(mapstr, "charnakridge.img").getIndex(0).getTexture(), 0, 41, 61, 25);
		sp[2] = new Sprite(game.getImg(mapstr, "charnakridge.img").getIndex(1).getTexture(), 0, 41, 61, 25);
		sp[3] = new Sprite(game.getImg(mapstr, "charnakridge.img").getIndex(2).getTexture(), 0, 41, 61, 25);
		an = new Animation<Sprite>(1, sp);
		lvimg = new Image(sp[0]);
		lvimg.setPosition(images.getX(), images.getY()+5);
		addActor(lvimg);
	}
	public void set(int id) {
		lvimg.setDrawable(new TextureRegionDrawable(an.getKeyFrames()[id]));
	}
	public MapLevel getLevel() {
		return level;
	}
	public int getLv() {
		return level.getLv();
	}
	public Array<Integer> getInfo() {
		return info;
	}
	public String getName() {
		return name;
	}
	public void addListener() {
		chose.addListener();
		level.addListener();
		map.btl.add(chose);
		map.btl.add(level);
	}
}