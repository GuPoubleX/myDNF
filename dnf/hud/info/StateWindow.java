package dnf.hud.info;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;

import dnf.author.RoleAnimation;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;

public class StateWindow extends Window {
	private Character ch = null;
	private Array<RoleAnimation> an = null;
	private boolean fight = false;
	private float time = 0;
	public StateWindow(WindowStyle style, Vector2 size, Character ch, boolean fight) {
		super("", style);
		setSize(size.x, size.y);
		this.ch = ch;
		this.fight = fight;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(an == null) {
			if(ch instanceof Swordman)
				an = ch.getAn(fight?11:27);
		}
		if(an != null)
			for(RoleAnimation anima : an)
				anima.draw(true, true, batch, time, parentAlpha, getX()+getWidth()/2, getY(), ch.getFix(), ch.getFix2(), false);
	}
}
