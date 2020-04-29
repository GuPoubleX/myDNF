package dnf.author.kritsu.handler;

import dnf.author.kritsu.lib.Colors;
import dnf.author.kritsu.lib.Streams;
import dnf.author.kritsu.model.Album;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FourthHandler extends SecondHandler {
    
    protected int paletteCount;
    
    public FourthHandler(Album album,InputStream in) throws Exception {
        super(album, in);
    }
    
    public void init(InputStream in) throws Exception {
        List<List<byte[]>> palettes=new ArrayList<List<byte[]>>();
        for (int i=0; i < Math.max(1,paletteCount); i++) {
            int paletteSize=Streams.readInt(in);
            List<byte[]> table=Colors.readPalette(in, paletteSize);
            palettes.add(table);
        }
        album.setPalettes(palettes);
        super.init(in);
    }
}
