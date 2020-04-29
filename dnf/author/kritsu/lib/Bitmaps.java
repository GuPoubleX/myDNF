package dnf.author.kritsu.lib;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bitmaps {
    
	public static TextureRegion createTexture(byte[] data, int width, int height, int bits) throws Exception {
		InputStream ms=new ByteArrayInputStream(data);
	    Pixmap pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
	    for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
            	byte[] temp=Colors.readColor(ms, bits);
                int r=((int) temp[2]) & 0xff;//Byte.toUnsignedInt(temp[2]);
                int g=((int) temp[1]) & 0xff;//Byte.toUnsignedInt(temp[1]);
                int b=((int) temp[0]) & 0xff;//Byte.toUnsignedInt(temp[0]);
                int a=((int) temp[3]) & 0xff;//Byte.toUnsignedInt(temp[3]);
                pix.drawPixel(x, y, Color.rgba8888(r/256.0f, g/256.0f, b/256.0f, a/256.0f));
            }
        }
	    ms.close();
	    TextureRegion re = new TextureRegion(new Texture(pix));
	    pix.dispose();
	    return re;
	}
}
