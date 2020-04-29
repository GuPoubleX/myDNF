package dnf.author;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GPXStage extends Stage {
	protected Array<Image> actionimage = null;
	protected Vector2 sizeVec = null;
	protected Group group = null;
	protected World world = null;
	protected World world_g = null;
	public GPXStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
		actionimage = new Array<Image>();
	}
	protected float scale = 1;
	protected Vector2 screen = new Vector2(0, 0);
	public void resetScale(float scale, Vector2 screen) {
		this.scale = scale;
		this.screen = screen;
	}
	public void resetLoc(int dor) {
	}
	public void destoryWorld() {
	}
	public Array<Image> getActionImg() {
		return actionimage;
	}
	public Group getGroup() {
		return group;
	}
	public World getWorld() {
		return world;
	}
	public World getGWorld() {
		return world_g;
	}
	public Vector2 SizeVector() {
		return sizeVec;
	}
}