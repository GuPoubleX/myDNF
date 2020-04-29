package dnf.character.part;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.gupoublex.GuPoubleXGame;

public abstract class GrapPose extends SkillPose {
	public GrapPose(GuPoubleXGame game) {
		super(game);
	}
	public GrapPose(GuPoubleXGame game, Vector2 move) {
		super(game, move);
	}
	public GrapPose(GuPoubleXGame game, int forceLv) {
		super(game, forceLv);
	}
	public GrapPose(GuPoubleXGame game, int forceLv, float cd) {
		super(game, forceLv, cd);
	}
	public GrapPose(GuPoubleXGame game, Array<RoleAnimation> an, int index, int forceLv) {
		super(game, an, index, forceLv);
	}
	public GrapPose(GuPoubleXGame game, int forceLv, Vector2 move) {
		super(game, forceLv, move);
	}
}