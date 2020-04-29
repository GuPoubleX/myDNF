package dnf.author.kritsu.handler;

import dnf.author.kritsu.constants.ColorBits;
import dnf.author.kritsu.constants.CompressMode;
import dnf.author.kritsu.lib.Bitmaps;
import dnf.author.kritsu.lib.Streams;
import dnf.author.kritsu.lib.Zlibs;
import dnf.author.kritsu.model.Album;
import dnf.author.kritsu.model.SpriteData;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
/**
 *
 *  Ver2 IMG处理器
 * @author kritsu
 */
public class SecondHandler implements Handler{
    protected Album album;
    private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;
	private int vw = 0;
	private int vh = 0;
	
	public Vector2 getXY() {
		return new Vector2(x, y);
	}
    public Vector2 getWH() {
		return new Vector2(w, h);
	}
    public Vector2 getVWH() {
		return new Vector2(vw, vh);
	}
    
    public SecondHandler(Album album,InputStream in) throws Exception {
        this.album=album;
        init(in);
    }
    
    public void init(InputStream in) throws Exception {
        List<SpriteData> list=new ArrayList<SpriteData>();
        for (int i=0; i < album.getCount(); i++) {
            SpriteData sprite=new SpriteData();
            sprite.setColorBits(Streams.readInt(in));
            list.add(sprite);
            if (sprite.getColorBits() == ColorBits.LINK) {
                sprite.setTargetIndex(Streams.readInt(in));
                continue;
            }
            sprite.setCompressMode(Streams.readInt(in));
            sprite.setWidth(Streams.readInt(in));
            sprite.setHeight(Streams.readInt(in));
            sprite.setLength(Streams.readInt(in));
            sprite.setX(Streams.readInt(in));
            sprite.setY(Streams.readInt(in));
            sprite.setFrameWidth(Streams.readInt(in));
            sprite.setFrameHeight(Streams.readInt(in));
        }
        for (SpriteData sprite : list) {
            if (sprite.getColorBits() == ColorBits.LINK) {
            	album.getList().add(album.getList().get(sprite.getTargetIndex()));
            	continue;
            }
            if (sprite.getCompressMode() == CompressMode.NONE)
                sprite.setLength(sprite.getWidth() * sprite.getHeight() * (sprite.getColorBits() == ColorBits.ARGB_8888 ? 4 : 2));
            byte[] data=new byte[sprite.getLength()];
            in.read(data);
            sprite.setData(data);
            album.getList().add(sprite);
        }
    }
    
    protected SpriteData getSprite(int index){
        SpriteData sprite=album.getList().get(index);
        if (sprite.getColorBits() == ColorBits.LINK) {
            index=sprite.getTargetIndex();
            sprite=album.getList().get(index);
        }
        return sprite;
    }
    
    public Vector2 getXY(int index) {
    	return new Vector2(getSprite(index).getX(), getSprite(index).getY());
    }
    public Vector2 getWH(int index) {
    	return new Vector2(getSprite(index).getWidth(), getSprite(index).getHeight());
    }
    public Vector2 getVWH(int index) {
    	return new Vector2(getSprite(index).getFrameWidth(), getSprite(index).getFrameHeight());
    }
    public TextureRegion decodes(int index) throws Exception {
        SpriteData sprite=getSprite(index);
        byte[] data=sprite.getData();
        int colorBits=sprite.getColorBits();
        int width=sprite.getWidth();
        int height=sprite.getHeight();
        int len=width * height * (colorBits == ColorBits.ARGB_8888 ? 4 : 2);
        if (sprite.getCompressMode() == CompressMode.ZLIB)
            data=Zlibs.decompress(data, len);
        return Bitmaps.createTexture(data, width, height, colorBits);
    }
}
