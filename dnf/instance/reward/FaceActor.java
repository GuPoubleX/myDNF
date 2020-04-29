package dnf.instance.reward;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import dnf.admin.SpriteAnimation;
import dnf.author.TextLabel;
import dnf.gupoublex.GuPoubleXGame;

public class FaceActor extends Actor {
	private Image face = null;
	private TextLabel label = null;
	private SpriteAnimation sa = null;
	public FaceActor(GuPoubleXGame game, Sprite sprite, String name, boolean boss) {
		super();
		face = new Image(sprite);
		label = new TextLabel(game);
		label.setAlignment(Align.left);
		label.setSize(getWidth(), getHeight());
		label.setText((boss?"[PINK]领主-":"[PURPLE]")+name+(boss?"[]":"[]"));
	}
	public void showSprite() {
		if(sa != null)
			sa.show();
	}
	public void setSpriteAnimation(SpriteAnimation sa) {
		this.sa = sa;
	}
	public Image getImage() {
		return face;
	}
	public TextLabel getLabel() {
		return label;
	}
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		face.setPosition(x, y);
		label.setPosition(x+face.getWidth()+10, y+face.getHeight()/2-label.getHeight()/2);
	}
	public void addGroup(Group group) {
		group.addActor(this);
		group.addActor(face);
		group.addActor(label);
	}
}