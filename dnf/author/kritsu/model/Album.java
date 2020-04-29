package dnf.author.kritsu.model;

import dnf.author.kritsu.handler.Handler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Album implements Iterable<SpriteData> {
    private String path;
    private int offset;
    private int length;
    private int version;
    private long indexLength;
    private int count;
    private List<SpriteData> list;
    private int paletteIndex;
    private List<List<byte[]>> palettes;
    private Handler handler;
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path=path;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length=length;
    }
    
    public void setOffset(int offset) {
        this.offset=offset;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version=version;
    }
    
    public long getIndexLength() {
        return indexLength;
    }
    
    public void setIndexLength(long indexLength) {
        this.indexLength=indexLength;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count=count;
    }
    
    public List<SpriteData> getList() {
        if(list!=null) {
            return list;
        }
        return  list=new ArrayList<SpriteData>();
    }
    
    public void setList(List<SpriteData> list) {
        this.list=list;
    }
    
    public Iterator<SpriteData> iterator() {
        return getList().iterator();
    }
    
    public Handler getHandler() {
        return handler;
    }
    
    public void setHandler(Handler handler) {
        this.handler=handler;
    }
    
    public int getPaletteIndex() {
        return paletteIndex;
    }
    
    public void setPaletteIndex(int paletteIndex) {
        this.paletteIndex=paletteIndex;
    }
    
    public List<List<byte[]>> getPalettes() {
        return palettes;
    }
    
    public void setPalettes(List<List<byte[]>> palettes) {
        this.palettes=palettes;
    }
    
    public List<byte[]> getCurrentPalette(){
        if(paletteIndex<palettes.size()&&paletteIndex>-1){
            return  palettes.get(paletteIndex);
        }
        return new ArrayList<byte[]>();
    }
    
    public String getName() {
        if (path == null || "".equals(path)) {
            return null;
        }
        int index=path.lastIndexOf("/");
        return path.substring(index + 1);
    }
    
    @Override
    public String toString() {
        return "Album{" +
                "path='" + path + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", version=" + version +
                ", indexLength=" + indexLength +
                ", count=" + count +
                ", paletteIndex=" + paletteIndex +
                ", palettes=" + palettes +
                ", handler=" + handler +
                '}';
    }
}
