package dnf.character.part;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dnf.author.RoleAnimation;
import dnf.gupoublex.GuPoubleXGame;

public abstract class SkillPose extends Pose {
	public SkillPose(GuPoubleXGame game) {
		super(game);
	}
	public SkillPose(GuPoubleXGame game, Vector2 move) {
		super(game, move);
	}
	public SkillPose(GuPoubleXGame game, int forceLv) {
		super(game, forceLv);
	}
	public SkillPose(GuPoubleXGame game, int forceLv, float cd) {
		super(game, forceLv, cd);
	}
	public SkillPose(GuPoubleXGame game, Array<RoleAnimation> an, int index, int forceLv) {
		super(game, an, index, forceLv);
	}
	public SkillPose(GuPoubleXGame game, int forceLv, Vector2 move) {
		super(game, forceLv, move);
	}
}