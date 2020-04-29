package dnf.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Rocker extends Touchpad {
	private StageHUD hud = null;
	private Image down1 = null;
	private Image down2 = null;
	private static int sx = 30;
	private static int sy = 114;
	private static int ex = 390;
	private static int ey = 474;
	private float scale = 0.75f;
	public Rocker(StageHUD hud, Sprite down, Sprite up, Sprite downex) {
		super(10, new TouchpadStyle(null, new Image(up).getDrawable()));
		setSize((ex-sx)*scale, (ey-sy)*scale);
		this.hud = hud;
		this.down1 = transImage(down);
		this.down1.setScale(scale*0.8f);
		this.down2 = transImage(downex);
		this.down2.setScale(scale*0.8f);
		rotateImageAction(down1, 0.1f);
		rotateImageAction(down2, 0.1f);
	}
	private static Image transImage(Sprite sprite) {
		return new Image(new TextureRegion(sprite, sx, sy, ex-sx, ey-sy));
	}
	private void movelogic() {
		if(isTouched()) {
			int x = (int) (Math.acos(getKnobPercentX())*180/Math.PI);
			int y = (int) (Math.asin(getKnobPercentY())*180/Math.PI);
			int right = 0;
			int up = 0;
			if(x < 90) {
				if(x < 22.5f)
					right = 3;
				else if(x > 67.5f)
					right = 1;
				else
					right = 2;
			}else if(x == 90)
				;
			else {
				if(x > 157.5f)
					right = -3;
				else if(x < 112.5f)
					right = -1;
				else
					right = -2;
			}
			if(y > 0) {
				if(Math.abs(y) < 22.5f)
					up = 1;
				else if(Math.abs(y) < 67.5f)
					up = 2;
				else
					up = 3;
			}else if(y == 0)
				;
			else {
				if(Math.abs(y) < 22.5f)
					up = -1;
				else if(Math.abs(y) < 67.5f)
					up = -2;
				else
					up = -3;
			}
			if(right == 3 || right == -3)
				hud.rockerUpdate(false, false, right==3?true:false, right==-3?true:false);
			else if(right == 2 || right == -2)
				hud.rockerUpdate(up>0?true:false, up<0?true:false, right==2?true:false, right==-2?true:false);
			else if(right == 1 || right == -1 || right == 0)
				hud.rockerUpdate(up>0?true:false, up<0?true:false, false, false);
		}else
			hud.resetrockerUpdate();
	}
	@Override
	public void setPosition(float x, float y) {
		down1.setPosition(x-down1.getWidth()/2, y-down1.getHeight()/2);
		down2.setPosition(x-down2.getWidth()/2, y-down2.getHeight()/2);
		super.setPosition(x-getWidth()/2, y-getHeight()/2);
	}
	@Override
	public void act(float delta) {
		down1.act(delta);
		down2.act(delta);
		super.act(delta);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		down1.draw(batch, parentAlpha*0.75f);
		if(isTouched())
			down2.draw(batch, parentAlpha*0.75f);
		super.draw(batch, parentAlpha*0.75f);
		movelogic();
	}
	private void rotateImageAction(Image image, float degree) {
		image.setOrigin(image.getWidth()/2, image.getHeight()/2);
		RotateByAction rotate = Actions.rotateBy(degree);
		RepeatAction repeat = Actions.forever(rotate);
		Action endAction = Actions.run(new Runnable() {
			@Override
			public void run() {}
		});
		SequenceAction sequence = Actions.sequence(repeat, endAction);
		image.addAction(sequence);
	}
}