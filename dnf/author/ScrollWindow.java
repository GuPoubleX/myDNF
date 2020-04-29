package dnf.author;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;

public class ScrollWindow extends Window {
	private Array<Actor> property = null;
	private Array<Actor> propertyex = null;
	private Array<Vector2> propertyp = null;
	private Array<Vector2> propertyexp = null;
	private Window window = null;
	private float during = 1;
	public ScrollWindow(WindowStyle style, Window window, float during) {
		super("", style);
		this.window = window;
		this.during = during;
	}
	public void addActor(Actor actor, boolean main) {
		super.addActor(actor);
		if(property == null) {
			property = new Array<Actor>();
			propertyp = new Array<Vector2>();
		}
		if(propertyex == null) {
			propertyex = new Array<Actor>();
			propertyexp = new Array<Vector2>();
		}
		if(main) {
			property.add(actor);
			propertyp.add(new Vector2(actor.getX(), actor.getY()));
		}else {
			propertyex.add(actor);
			propertyexp.add(new Vector2(actor.getX(), actor.getY()));
		}
	}
	public void resetscroll() {
		for(int i = 0; i < property.size; i++)
			property.get(i).setPosition(propertyp.get(i).x, propertyp.get(i).y);
		for(int i = 0; i < propertyex.size; i++)
			propertyex.get(i).setPosition(propertyexp.get(i).x, propertyexp.get(i).y);
	}
	public boolean scroll(float x, float y, float deltaX, float deltaY, float scale) {
		if(!isVisible())
			return false;
		//window->main->this
		Window main = (Window) getParent();
		float startx = main.getX()+getX();
		float endx = main.getX()+getX()+getWidth();
		float starty = main.getY()+getY();
		float endy = main.getY()+getY()+getHeight();
		if(x >= startx/scale && x <= endx/scale &&
				y <= (window.getHeight()-starty)/scale &&
				y >= (window.getHeight()-endy)/scale) {
			boolean up = false;
			boolean down = false;
			if(property.get((int) (property.size-1-1)).getY()>=during/2)
				up = true;
			if(property.get(0).getY()+property.get(0).getHeight()+during/2 <= getHeight())
				down = true;
			for(int i = 0; i < property.size; i++)
				property.get(i).setPosition(property.get(i).getX(),
						property.get(i).getY()-((up&&deltaY<0)||(down&&deltaY>0)?0:
							(deltaY>0?getHeight()/20:(deltaY<0?-getHeight()/20:0))));
			for(int i = 0; i < propertyex.size-1; i++)
				propertyex.get(i).setPosition(propertyex.get(i).getX(),
						propertyex.get(i).getY()-((up&&deltaY<0)||(down&&deltaY>0)?0:
							(deltaY>0?getHeight()/20:(deltaY<0?-getHeight()/20:0))));
			return true;
		}else
			return false;
	}
}