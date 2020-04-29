package dnf.gupoublex.read;

import java.io.InputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import dnf.gupoublex.set.SetBase;
import dnf.author.kritsu.handler.FirstHandler;
import dnf.author.kritsu.handler.FourthHandler;
import dnf.author.kritsu.handler.Handler;
import dnf.author.kritsu.handler.SecondHandler;
import dnf.author.kritsu.handler.SixthHandler;
import dnf.author.kritsu.lib.Streams;
import dnf.author.kritsu.model.Album;

public class Img implements Disposable {
	private int version = 0;
	private int count = 0;
	private Album album = null;
	private FileHandle file = null;
	private Array<SpriteTexture> st = null;
	public Img(FileHandle file) {
		super();
		this.file = file;
	}
	public int getCount() {
		return count;
	}
	public void read() {
		try {
			InputStream is = file.read();
			for(int i = 0; i < 4; i++)
				/*string = "Neople Img File "*/Streams.readInt(is);
			/*indexsize = */Streams.readInt(is);
			/*unknow = */Streams.readInt(is);
			version = Streams.readInt(is);
			count = Streams.readInt(is);
			album = new Album();
			album.setCount(count);
			Handler handler = null;
			switch(version) {
				case 1:handler = new FirstHandler(album, is);break;
				case 2:handler = new SecondHandler(album, is);break;
				case 4:handler = new FourthHandler(album, is);break;
				case 6:handler = new SixthHandler(album, is);break;
			}
			st = new Array<SpriteTexture>();
			for(int i = 0; i < count; i++)
				st.add(new SpriteTexture(handler.decodes(i), handler.getXY(i), handler.getWH(i), handler.getVWH(i)));
			album = null;
			is.close();
			
		} catch(Exception e) {
			Gdx.app.log(SetBase.TAG, "read file error:"+file.name());
		}
	}
	public SpriteTexture getIndexST(int i) {
		return st.get(i);
	}
	public Sprite getIndex(int i) {
		return st.get(i).getSprite();
	}
	@Override
	public void dispose() {
		if(st != null) {
			for(SpriteTexture s : st)
				s.dispose();
			st.clear();
		}
		st = null;
	}
}