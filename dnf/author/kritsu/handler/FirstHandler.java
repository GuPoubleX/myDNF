package dnf.author.kritsu.handler;

import dnf.author.kritsu.constants.ColorBits;
import dnf.author.kritsu.constants.CompressMode;
import dnf.author.kritsu.lib.Streams;
import dnf.author.kritsu.model.Album;
import dnf.author.kritsu.model.SpriteData;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FirstHandler extends SecondHandler {
    public FirstHandler(Album album, InputStream in) throws Exception {
        super(album, in);
    }
    
    public void init(InputStream in) throws Exception {
        List<SpriteData> list=new ArrayList<>();
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
            if (sprite.getCompressMode() == CompressMode.NONE) {
                sprite.setLength(sprite.getWidth() * sprite.getHeight() * (sprite.getColorBits() == ColorBits.ARGB_8888 ? 4 : 2));
            }
            byte[] data=new byte[sprite.getLength()];;
            in.read(data);
            sprite.setData(data);
        }
        album.setList(list);
    }
    
}
