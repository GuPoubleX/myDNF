package dnf.admin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;

public class Create extends StageAdmin {
	private Create ct = null;
	private ImageButton back = null;
	private Array<CreateButton> btn = null;
	public Create(ScreenAdmin main, Vector2 ver, Batch batch, float scale) {
		super(main, ver, batch, 0, scale, "createcharacter.img");
		ct = this;
	}
	@Override
	protected void setStage() {
		SpriteAnimation can = new SpriteAnimation(0.1f, main.getGame(), SetImg.admin, "charactercreate2.img",
				new int[]{7,8,9,10,11,12}, true, false);
		can.setPosition(window.getWidth()/2-can.getWidth()/2, window.getHeight()-can.getHeight());
		window.addActor(can);
		Image image = new Image(main.getGame().getImg(SetImg.admin, "createcharacter.img").getIndex(3));
		image.setPosition(window.getWidth()/2-image.getWidth()/2,
				window.getHeight()-image.getHeight()-50);
		window.addActor(image);
		image = new Image(main.getGame().getImg(SetImg.admin, "createcharacter.img").getIndex(1));
		image.setPosition(-100, window.getHeight()/2-image.getHeight()/2);
		window.addActor(image);
		rotateImageAction(image);
		Sprite re = main.getGame().getImg(SetImg.admin, "createcharacter.img").getIndex(2);
		Sprite sp = new Sprite(re, 0, 0, re.getRegionWidth()*5/6, re.getRegionHeight());
		sp.flip(true, false);
		image = new Image(sp);
		image.setPosition(0, window.getHeight()/3-image.getHeight());
		window.addActor(image);
		createex = new ImageButton(
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(1)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(2)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate.img").getIndex(2)));
		createex.setPosition(
				image.getWidth()+(window.getWidth()-image.getWidth())/2-createex.getWidth()/2,
				image.getY()+image.getHeight()/2-createex.getHeight()/2);
		window.addActor(createex);
		back = new ImageButton(
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate2.img").getIndex(2)),
				new TextureRegionDrawable(main.getGame().getImg(SetImg.admin, "charactercreate2.img").getIndex(3)));
		back.setPosition(window.getWidth()-back.getWidth(),
				window.getHeight()-back.getHeight());
		window.addActor(back);
		chose = new Array<Integer>();
		chose.add(0);
		btn = new Array<CreateButton>();
		for(int i = 0; i < 5; i++) {
			CreateButton ib = new CreateButton(i, btn, chose, main.getGame(), window);
			btn.add(ib);
			if(i == 0)
				ib.check();
		}
		for(int i = 0; i < btn.size; i++) {
			btn.get(i).setPosition(image.getX()+10+(btn.get(i).getWidth()+16)*i, image.getY()+11);
			window.addActor(btn.get(i));
		}
	}
	@Override
	protected void setListener() {
		for(CreateButton cb : btn) {
			cb.addListener();
			btl.add(cb);
		}
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearLs();
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				main.change("select");
			}
		});
		createex.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				main.getGame().getSound(SetBase.common, "click.ogg").play(main.getGame().getSound_button());
				CreateDialog dialog = new CreateDialog(ct, main.getGame().getLazy(), main.getGame(), createex, window);
				dialog.setPosition(window.getWidth()/2-dialog.getWidth()/2, window.getHeight()/2-dialog.getHeight()/2);
				window.addActor(dialog);
			}
		});
		btl.add(back);
		btl.add(createex);
	}
	private void rotateImageAction(Image image) {
		image.setOrigin(image.getWidth()/2, image.getHeight()/2);
		RotateByAction rotate = Actions.rotateBy(0.1f);
		RepeatAction repeat = Actions.forever(rotate);
		SequenceAction alpha = Actions.sequence(repeat);
		image.addAction(alpha);
	}
	public Array<Integer> getChose() {
		return chose;
	}
	public ScreenAdmin getAdmin() {
		return main;
	}
}