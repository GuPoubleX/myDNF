package dnf.gupoublex.read;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class SpriteTexture implements Disposable {
	private Sprite sprite = null;
	private Vector2 xy = null;
	private Vector2 wh = null;
	private Vector2 vwh = null;
	private int index = -1;
	public Vector2 getXY() {
		return xy;
	}
	public Vector2 getWH() {
		return wh;
	}
	public Vector2 getVWH() {
		return vwh;
	}
	public int getIndex() {
		return index;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public SpriteTexture(TextureRegion re) {
		super();
		sprite = new Sprite(re);
		xy = new Vector2(0, 0);
		wh = new Vector2(re.getRegionWidth(), re.getRegionHeight());
		vwh = new Vector2(re.getRegionWidth(), re.getRegionHeight());
	}
	public SpriteTexture(Texture re) {
		super();
		sprite = new Sprite(re);
		xy = new Vector2(0, 0);
		wh = new Vector2(re.getWidth(), re.getHeight());
		vwh = new Vector2(re.getWidth(), re.getHeight());
	}
	public void setScale(float scaleXY) {
		if(sprite != null)
			sprite.setScale(scaleXY);
	}
	public void flip(boolean x, boolean y) {
		if(sprite != null)
			sprite.flip(x, y);
	}
	public SpriteTexture(byte data[], int col, int zlib,
			int w, int h, int x, int y, int vw, int vh) {
		super();
		this.xy = new Vector2(x, y);
		this.wh = new Vector2(w, h);
		this.vwh = new Vector2(vw, vh);
		draw(col, (zlib==0x06?unzlib(data):data), w, h);
	}
	public SpriteTexture(byte data[], Vector2 xy, Vector2 wh, Vector2 vwh) {
		this.xy = xy;
		this.wh = wh;
		this.vwh = vwh;
		draw(data);
	}
	public SpriteTexture(TextureRegion re, Vector2 xy, Vector2 wh, Vector2 vwh) {
		this.xy = xy;
		this.wh = wh;
		this.vwh = vwh;
		Texture texture = re.getTexture();
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
	}
	private void draw(byte data[]) {
		Pixmap pix = new Pixmap(data, 0, data.length);
		Texture texture = new Texture(pix);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		pix.dispose();
	}
	public SpriteTexture(int index) {
		this.index = index;
	}
	private byte[] unzlib(byte data[]) {
		byte output[] = new byte[0];
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);
		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte buf[] = new byte[4096];
			while(!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		decompresser.end();
		return output;
	}
	private void draw(int col, byte data[], int w, int h) {
		Pixmap pix = new Pixmap(w, h, Pixmap.Format.RGBA8888);
		for(int i = 0, j = 0; j < h; i++) {
			pix.drawPixel(i, j, pixel(col, data, i+j*w));
			if(i == w-1) {
				i = -1;
				j += 1;
			}
		}
		Texture texture = new Texture(pix);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		pix.dispose();
	}
	private int pixel(int col, byte data[], int i) {
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		switch(col) {
			case 0x0e:
				r = ((data[i*2+1] & 127) >> 2) << 3;
				g = (((data[i*2+1] & 0x0003) << 3) | ((data[i*2] >> 5) & 0x0007)) << 3;
				b = (data[i*2] & 0x003f) << 3;
				a = (data[i*2+1] >> 7) == 0 ? 0 : 255;
				break;
			case 0x0f:
				r = (data[i*2+1] & 0x0f) << 4;
				g = ((data[i*2] & 0xf0) >> 4) << 4;
				b = (data[i*2] & 0x0f) << 4;
				a = ((data[i*2+1] & 0xf0) >> 4) << 4;
				break;
			case 0x10:
				r = data[i*4+2];
				g = data[i*4+1];
				b = data[i*4];
				a = data[i*4+3];
				break;
		}
		return Color.rgba8888(new Color((r<<24)+(g<<16)+(b<<8)+a));
	}
	@Override
	public void dispose() {
		sprite = null;
		xy = null;
		wh = null;
		vwh = null;
	}
}