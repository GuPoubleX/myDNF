package dnf.author.kritsu.handler;

import dnf.author.kritsu.lib.Streams;
import dnf.author.kritsu.model.Album;
import java.io.InputStream;

public class SixthHandler extends FourthHandler{
    public SixthHandler(Album album,InputStream in) throws Exception {
        super(album,in);
    }
    
    public void init(InputStream in) throws Exception {
        paletteCount=Streams.readInt(in);
        super.init(in);
    }
    
}
