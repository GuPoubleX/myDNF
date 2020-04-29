package dnf.author;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dnf.character.Character;
import dnf.character.state.skill.swordman.*;
import dnf.gupoublex.set.SetBase;

public class GPXCamera {
	private Camera camera = null;
	private float x = 0;
	private float y = 0;
	private float cx = 0;
	private float cy = 0;
	private float w = 0;
	private float h = 0;
	private float sw = 0;
	private float sh = 0;
	private Character ch = null;
	private OrthographicCamera cam = null;
	private boolean skillstart = false;
	private boolean direct = true;
	public GPXCamera(Camera camera) {
		this.camera = camera;
		cam = new OrthographicCamera(camera.viewportWidth/SetBase.scale, camera.viewportHeight/SetBase.scale);
		cam.position.set(0, 0, 0);
	}
	public OrthographicCamera getCamera() {
		return cam;
	}
	public void init(Character ch,
			float width, float height, float swidth, float sheight) {
		this.ch = ch;
		this.w = width;//screen width
		this.h = height;//screen height
		this.sw = swidth;//stage width
		this.sh = sheight;//stage height
	}
	private float border(float i, float d, float max) {
		if(i-d/2 <= 0)
			i = d/2;
		if(i+d/2 >= max)
			i = max-d/2;
		return i;
	}
	private void set() {
		cx = camera.position.x;
		cy = camera.position.y;
		x = border(ch.getX(), w, sw);
		if(ch.currentPose() instanceof Hundredsword) {
			if(!skillstart) {
				skillstart = true;
				direct = ch.isRight();
			}
			x = border(ch.getX()+(direct?1:-1)*200, w, sw);
		}else
			skillstart = false;
		y = border(ch.getY()+100, h, sh);
		camera.position.set(x, y, 0.0f);
		cam.position.add((x-cx)/SetBase.scale, (y-cy)/SetBase.scale, 0.0f);
		camera.update();
		cam.update();
	}
	public void update() {
		if(ch != null)
			set();
	}
}