package dnf.admin;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import dnf.author.LazyBitmapFont;
import dnf.author.ScrollWindow;
import dnf.author.TextLabel;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class ChannelButton extends ImageButton {
	private ScreenAdmin main = null;
	private int chose = 0;
	private ChannelImage chosed = null;
	private Image state = null;
	private TextLabel inf = null;
	private TextLabel lv = null;
	private boolean check = false;
	private Array<ChannelButton> list = null;
	public ChannelButton(ScreenAdmin main, LazyBitmapFont lazy, String level, String info, int chose, int type, int sta, Array<ChannelButton> list) {
		super(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "channelslot.img").getIndex(offset(type))));
		this.main = main;
		this.chose = chose;
		this.list = list;
		chosed = new ChannelImage(main.getGame().getImg(SetImg.admin, "channelbase.img").getIndex(1));
		chosed.setVisible(false);
		lv = new TextLabel(main.getGame());
		lv.setFontScale(0.7f);
		lv.setSize(100, 20);
		lv.setAlignment(Align.center, Align.right);
		lv.setText(level);
		lv.setPosition(getWidth()-lv.getWidth()-38, 2.5f);
		inf = new TextLabel(main.getGame());
		inf.setFontScale(0.7f);
		inf.setSize(100, 20);
		inf.setAlignment(Align.center, Align.right);
		inf.setText(info);
		inf.setPosition(getWidth()-inf.getWidth()-38, 17.5f);
		state = new Image(main.getGame().getImg(SetImg.admin, "community.img").getIndex(3-sta));
		state.setPosition(getWidth()-state.getWidth()-5, getHeight()/2-state.getHeight()/2);
	}
	private static int offset(int i) {
		switch(i) {
			case 0:i = 4;break;
			case 1:i = 0;break;
			case 2:i = 1;break;
			case 3:i = 2;break;
			case 4:i = 3;break;
			case 5:i = 7;break;
			case 6:i = 6;break;
			case 7:i = 11;break;
			case 8:i = 12;break;
			case 9:i = 10;break;
			case 10:i = 9;break;
			case 11:i = 13;break;
			case 12:i = 8;break;
			case 13:i = 5;break;
		}
		return i;
	}
	public void addActors(ScrollWindow win, float x, float y) {
		setPosition(getX()+x, getY()+y);
		chosed.setPosition(chosed.getX()+x, chosed.getY()+y);
		lv.setPosition(lv.getX()+x, lv.getY()+y);
		inf.setPosition(inf.getX()+x, inf.getY()+y);
		state.setPosition(state.getX()+x, state.getY()+y);
		win.addActor(this, true);
		win.addActor(chosed, false);
		win.addActor(lv, false);
		win.addActor(inf, false);
		win.addActor(state, false);
	}
	public void addActor(Window win, float x, float y) {
		setPosition(getX()+x, getY()+y);
		chosed.setPosition(chosed.getX()+x, chosed.getY()+y);
		lv.setPosition(lv.getX()+x, lv.getY()+y);
		inf.setPosition(inf.getX()+x, inf.getY()+y);
		state.setPosition(state.getX()+x, state.getY()+y);
		win.addActor(this);
		win.addActor(chosed);
		win.addActor(lv);
		win.addActor(inf);
		win.addActor(state);
	}
	public void changestate(int i) {
		state.setDrawable(new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "community.img").getIndex(3-i)));
	}
	public void addListener() {
		lv.setTouchable(Touchable.disabled);
		inf.setTouchable(Touchable.disabled);
		state.setTouchable(Touchable.disabled);
		chosed.setTouchable(Touchable.disabled);
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!check) {
					for(ChannelButton ca : list)
						if(ca.ischecked())
							ca.reset();
					check = true;
					chosed.setVisible(true);
					main.getGame().setChannel(chose);
					main.getGame().getSound(SetBase.admin, "button_04.ogg").play(main.getGame().getSound_button());
				}
			}
		});
	}
	public boolean ischecked() {
		return check;
	}
	public void reset() {
		check = false;
		chosed.setVisible(false);
	}
}