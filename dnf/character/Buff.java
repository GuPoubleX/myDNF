package dnf.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import dnf.character.part.Buf;

public class Buff {
	private Character ch = null;
	private Array<Buf> list = null;
	public Buff(Character ch) {
		super();
		this.ch = ch;
		list = new Array<Buf>();
	}
	public void clear() {
		list.clear();
	}
	public boolean havBuf(Buf buf) {
		boolean have = false;
		for(Buf b : list)
			if(b.getClass().equals(buf.getClass())) {
				have = true;
				break;
			}
		return have;
	}
	public void removeBuf(Buf buf) {
		for(Buf b : list)
			if(b.getClass().equals(buf.getClass())) {
				list.removeValue(b, true);
				b.end(ch);
				break;
			}
	}
	public void addBuff(Buf buf) {
		if(list.size == 0)
			list.add(buf);
		else {
			if(buf.isOpen()) {
				boolean hav = false;
				for(Buf b : list)
					if(b.getClass().equals(buf.getClass())) {
						hav = true;
						list.removeValue(b, true);
						b.end(ch);
						break;
					}
				if(!hav)
					list.add(buf);
			}else {
				boolean canadd = true;
				for(Buf b : list)
					if(b.getClass().equals(buf.getClass())) {
						if(b.getCurrent() < b.getMax()) {
							b.readd();
							canadd = false;
							break;
						}else {
							canadd = false;
							break;
						}
					}
				if(canadd)
					list.add(buf);
			}
		}
	}
	public void update(Batch batch, float parentAlpha, float delta) {
		for(Buf b :list) {
			boolean end = b.update(batch, parentAlpha, ch, delta);
			if(end)
				list.removeValue(b, true);
		}
	}
}