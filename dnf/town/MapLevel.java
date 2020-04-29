package dnf.town;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;

public class MapLevel extends Window {
	private GuPoubleXGame gg = null;
	private int lv = 0;
	private int maxlv = 0;
	private ImageButton level_minus = null;
	private ImageButton level_plus = null;
	private StageMap map = null;
	private MapWindowmax max = null;
	public MapLevel(WindowStyle style, String mapstr, String bg, GuPoubleXGame game, int maxlv, StageMap map, MapWindowmax max) {
		super("", style);
		this.gg = game;
		this.maxlv = maxlv;
		this.map = map;
		this.max = max;
		setSize(44, 14);
		level_minus = new ImageButton(
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(10)),
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(13)),
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(13)));
		level_plus = new ImageButton(
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(11)),
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(14)),
				new TextureRegionDrawable(gg.getImg(mapstr, "selectdungeon.img").getIndex(14)));
		level_minus.setPosition(0, getHeight()/2-level_minus.getHeight()/2);
		level_plus.setPosition(getWidth()-level_plus.getWidth(),
				getHeight()/2-level_plus.getHeight()/2);
		addActor(level_minus);
		addActor(level_plus);
	}
	public int getLv() {
		return lv;
	}
	public void addLv() {
		if(maxlv == 0 || lv == maxlv)
			return;
		lv++;
		if(lv >= maxlv)
			lv = maxlv;
		gg.getSound(SetBase.map, "map_select.ogg").play(gg.getSound_button());
		max.set(lv);
	}
	public void minusLv() {
		if(maxlv == 0 || lv == 0)
			return;
		lv--;
		if(lv <= 0)
			lv = 0;
		gg.getSound(SetBase.map, "map_select.ogg").play(gg.getSound_button());
		max.set(lv);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(maxlv == 0) {
			level_minus.setChecked(true);
			level_plus.setChecked(true);
		}else {
			if(lv == 0) {
				level_minus.setChecked(true);
				level_plus.setChecked(false);
			}else if(lv == maxlv) {
				level_plus.setChecked(true);
				level_minus.setChecked(false);
			}else {
				level_plus.setChecked(false);
				level_minus.setChecked(false);
			}
		}
		super.draw(batch, parentAlpha);
	}
	public void addListener() {
		level_minus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(map.isenter())
					return;
				if(maxlv == 0)
					return;
				if(lv == 0)
					return;
				else {
					lv--;
					gg.getSound(SetBase.map, "map_select.ogg").play(gg.getSound_button());
					max.set(lv);
				}
			}
		});
		level_plus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(map.isenter())
					return;
				if(maxlv == 0)
					return;
				if(lv == maxlv)
					return;
				else {
					lv++;
					gg.getSound(SetBase.map, "map_select.ogg").play(gg.getSound_button());
					max.set(lv);
				}
			}
		});
		map.btl.add(level_minus);
		map.btl.add(level_plus);
	}
}