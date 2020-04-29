package dnf.character.part;

import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.set.SetBase;

public class GraspAction {
	private Character attack = null;
	private Character hit = null;
	private Vector2 movexy = null;
	private Vector2 movez = null;
	private float dw = 0;
	private float dh = 0;
	private float jumppower = 0;
	private boolean first = false;
	private float vz = 0;
	public GraspAction(Character attack, Character hit, Vector2 xy, Vector2 z, float jumppower) {
		super();
		this.attack = attack;
		this.hit = hit;
		this.jumppower = jumppower;
		movexy = new Vector2((attack.isRight()?1:-1)*xy.x, xy.y);
		movez = z;
		dw = attack.getX()+movexy.x-hit.getX();
		dh = attack.getY()+movexy.y-hit.getY();
	}
	public void update(float delta) {
		float dx = 4*(dw>0?1:-1);
		float dy = 4/SetBase.fix*(dh>0?1:-1);
		if(Math.abs(hit.getX()-(attack.getX()+movexy.x)) <= 5)
			dx = 0;
		if(Math.abs(hit.getY()-(attack.getY()+movexy.y)) <= 5)
			dy = 0;
		hit.move(dx, dy);
		if(hit.getZ() == 0 && hit.getZLineV().y == 0)
			hit.jump(jumppower);
		else if(hit.getZ() <= movez.y && hit.getZLineV().y < 0) {
			if(!first) {
				first = true;
				vz = -hit.getZLineV().y;
			}
			hit.setZLineV(new Vector2(0, vz));
			hit.setZ(movez.y, hit.isGrasp());
		}
	}
}