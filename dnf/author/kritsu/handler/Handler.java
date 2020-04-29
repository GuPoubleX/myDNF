package dnf.author.kritsu.handler;

import java.io.InputStream;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Handler {
	public void init(InputStream in) throws Exception;
    public TextureRegion decodes(int index) throws Exception;
    public Vector2 getXY(int index);
    public Vector2 getWH(int index);
    public Vector2 getVWH(int index);
}
