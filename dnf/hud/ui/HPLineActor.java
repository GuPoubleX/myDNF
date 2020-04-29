package dnf.hud.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import dnf.author.TextLabel;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.character.Character;
import dnf.character.roletype.humen.Swordman;

public class HPLineActor extends Window {
	private GuPoubleXGame game = null;
	private Window norline = null;
	private Window norlineD = null;
	private Window norlinelos = null;
	private Window norlineDlos = null;
	private Window bosline = null;
	private Window boslineD = null;
	private Window boslinelos = null;
	private Window boslineDlos = null;
	private Window line = null;
	private Sprite face = null;
	private Sprite die = null;
	private Array<Sprite> label = null;
	private TextLabel name = null;
	private Character ch = null;
	private Array<Sprite> list = null;
	private Img img = null;
	private int bosshpmove = 0;
	private boolean isboss = false;
	private float time = 0;
	private float lostime = 0;
	private int maplv = 1;
	private int widths = 597;
	private float hpcurrent = 0;
	private float hpinit = 0;
	private float showtime = 0;
	private boolean visible = false;
	public HPLineActor(GuPoubleXGame game) {
		super("", new Window.WindowStyle(game.getLazy(), Color.WHITE, null));
		setSize(640, 36);
		this.game = game;
		name = new TextLabel(game);
		name.setSize(100, 36);
		list = new Array<Sprite>();
		label = new Array<Sprite>();
		line = new Window("", getStyle());
		norline = new Window("", getStyle());
		norlineD = new Window("", getStyle());
		norlinelos = new Window("", getStyle());
		norlineDlos = new Window("", getStyle());
		bosline = new Window("", getStyle());
		boslineD = new Window("", getStyle());
		boslinelos = new Window("", getStyle());
		boslineDlos = new Window("", getStyle());
	}
	public void setRole(Character ch, int maplv) {
		if(this.ch != ch) {
			if(this.ch != null) {
				if(this.ch.getEmenyType() <= ch.getEmenyType())
					setCh(ch, maplv);
			}else
				setCh(ch, maplv);
		}else {
			if(hpinit == ch.getProperty("HP"))
				hpinit = ch.getProperty("HP");
			else
				hpinit = ch.getProperty("HP")+(hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
			lostime = 0;
		}
	}
	private void setCh(Character ch, int maplv) {
		this.ch = ch;
		this.maplv = maplv;
		hpinit = ch.getProperty("HP");
		lostime = 0;
		isboss = false;
		clean();
		name.setText((ch.getEmenyType() == SetCharProperty.boss?"[PINK]":"")+"Lv."+((int) ch.getProperty("LV"))+" "+ch.getName()+
				(ch.getEmenyType() == SetCharProperty.boss?"[]":""));
		img = game.getImg(SetImg.hud, "monster_hp.img");
		if(die == null)
			die = new Sprite(img.getIndex(0));
		if(ch.getFace() != -1) {
			if(ch instanceof Swordman)
				face = new Sprite(game.getImg(SetImg.hud, "defaultfaces.img").getIndex(ch.getFace()));
			else
				face = new Sprite(game.getImg(SetImg.hud, "monsterface.img").getIndex(ch.getFace()));
		}
		if(ch.getEmenyType() == SetCharProperty.normal) {
			getLabel(ch.getEmenyType());
			normal(ch, img, maplv);
			name.setFontScale(0.75f);
			name.setPosition(label.get(label.size-1).getX()+30, getHeight()/2-name.getHeight()/2+6);
			face.setPosition(getX()+2, getY()+5);
		}else if(ch.getEmenyType() == SetCharProperty.boss) {
			isboss = true;
			getLabel(ch.getEmenyType());
			boss(ch, img, maplv);
			name.setFontScale(0.75f);
			name.setPosition(label.get(label.size-1).getX()+30, getHeight()/2-name.getHeight()/2+2);
			face.setPosition(getX()+4, getY()+7);
		}else {
			getLabel(ch.getEmenyType());
			essence(ch, img, maplv);
			name.setFontScale(0.75f);
			name.setPosition(label.get(label.size-1).getX()+30, getHeight()/2-name.getHeight()/2+9);
			face.setPosition(getX()+4, getY()+7);
		}
		die.setPosition(face.getX(), face.getY());
		drawHP(maplv, 0);
		visible = true;
	}
	private void getLabel(int emenytype) {
		Array<Integer> type = ch.getLabel();
		int w = 0;
		int h = 0;
		if(emenytype == SetCharProperty.boss) {
			w = 36;
			h = 20;
		}else if(emenytype == SetCharProperty.normal) {
			w = 33;
			h = 25;
		}else {
			w = 37;
			h = 28;
		}
		for(int i= 0; i < type.size; i++) {
			label.add(game.getImg(SetImg.hud, "category.img").getIndex(type.get(i)));
			label.get(i).setPosition(getX()+(32+2)*i+w, getY()+h-label.get(i).getHeight()/2);
		}
	}
	private void drawHP(int maplv, float time) {
		if(ch.getEmenyType() == SetCharProperty.normal)
			normalHP(maplv, time);
		else if(ch.getEmenyType() == SetCharProperty.boss)
			bossHP(maplv, time);
		else
			essenceHP(maplv, time);
	}
	private void clean() {
		label.clear();
		list.clear();
		line.clear();
		norline.clear();
		norlineD.clear();
		norlinelos.clear();
		norlineDlos.clear();
		bosline.clear();
		boslineD.clear();
		boslinelos.clear();
		boslineDlos.clear();
		line.remove();
		name.remove();
		norline.remove();
		norlineD.remove();
		norlinelos.remove();
		norlineDlos.remove();
		bosline.remove();
		boslineD.remove();
		boslinelos.remove();
		boslineDlos.remove();
	}
	private void normal(Character ch, Img img, int maplv) {
		float hpmax = ch.getProperty("HPMAX");
		float linehp = getLineHP(maplv);
		if(hpmax <= linehp) {
			Sprite sprite = new Sprite(img.getIndex(1), 0, 0, 30, 32);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			line.setSize(widths*(hpmax/linehp)+2+2, 18);
			line.setPosition(sprite.getWidth()-2, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(1), 80, 0, 10, 32));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(1));
			image.setPosition(-30, 0);
			line.addActor(image);
			image = new Image(img.getIndex(8));
			image.setPosition(line.getWidth()-image.getWidth(), 0);
			line.addActor(image);
		}else {
			Sprite sprite = new Sprite(img.getIndex(1), 0, 0, 30, 32);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			line.setSize(widths+2+2, 18);
			line.setPosition(sprite.getWidth()-2, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(1), 80, 0, 10, 32));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(1));
			image.setPosition(-30, 0);
			line.addActor(image);
			image = new Image(img.getIndex(8));
			image.setPosition(line.getWidth()-image.getWidth(), 0);
			line.addActor(image);
		}
	}
	private void normalHP(int maplv, float time) {
		name.remove();
		norline.clear();
		norlineD.clear();
		norlinelos.clear();
		norlineDlos.clear();
		norline.remove();
		norlineD.remove();
		norlinelos.remove();
		norlineDlos.remove();
		hpcurrent = ch.getProperty("HP");
		float linehp = getLineHP(maplv);
		if(hpcurrent <= linehp) {
			Image s = new Image(img.getIndex(2));
			norline.setSize(widths*hpcurrent/linehp, s.getHeight());
			norline.setPosition(29, 6);
			norline.addActor(s);
			addActor(norline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = hpcurrent/linehp;
					if(p >= 0) {
						if(p == 0) {
							hpinit = hpcurrent;
							lostime = 0;
						}
						s = new Image(img.getIndex(7));
						norlinelos.setSize(widths*(p/linehp+pup>1?1-pup:p/linehp), s.getHeight());
						norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
						norlinelos.addActor(s);
						addActor(norlinelos);
					}
					if(p/linehp+pup > 1) {
						s = new Image(img.getIndex(7));
						norlineDlos.setSize(widths*(p/linehp+pup-((int) (p/linehp+pup))), s.getHeight());
						norlineDlos.setPosition(norline.getX(), norline.getY());
						norlineDlos.addActor(s);
						addActor(norlineDlos);
					}
				}
			}
		}else {
			int count = (int) (hpcurrent/linehp);
			if(count >= 1) {
				Image s = new Image(img.getIndex((count-1)%5+2));
				norlineD.setSize(s.getWidth(), s.getHeight());
				norlineD.addActor(s);
				norlineD.setPosition(29, 6);
				addActor(norlineD);
			}
			Image s = new Image(img.getIndex(count%5+2));
			norline.setSize(s.getWidth()*(hpcurrent-linehp*count)/linehp, s.getHeight());
			norline.addActor(s);
			norline.setPosition(29, 6);
			addActor(norline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = (hpcurrent-linehp*count)/linehp;
					if(p/linehp+pup <= 1) {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							s = new Image(img.getIndex(7));
							norlinelos.setSize(widths*p/linehp, s.getHeight());
							norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
							norlinelos.addActor(s);
							addActor(norlinelos);
						}
					}else {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							s = new Image(img.getIndex(7));
							norlinelos.setSize(widths*(1-pup), s.getHeight());
							norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
							norlinelos.addActor(s);
							addActor(norlinelos);
							s = new Image(img.getIndex(7));
							norlineDlos.setSize(widths*(pup+p/linehp-((int) (p/linehp+pup))), s.getHeight());
							norlineDlos.setPosition(norlineD.getX(), norlineD.getY());
							norlineDlos.addActor(s);
							addActor(norlineDlos);
						}
					}
				}
			}
		}
		addActor(name);
	}
	private void essence(Character ch, Img img, int maplv) {
		float hpmax = ch.getProperty("HPMAX");
		float linehp = getLineHP(maplv);
		if(hpmax <= linehp) {
			Sprite sprite = new Sprite(img.getIndex(9), 0, 0, 36, 36);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			line.setSize(widths*(hpmax/linehp)+4+2, 20);
			line.setPosition(sprite.getWidth()-1, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(9), 80, 0, 10, 36));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(9));
			image.setPosition(-36, 0);
			line.addActor(image);
			image = new Image(img.getIndex(10));
			image.setPosition(line.getWidth()-image.getWidth()-1, 1);
			line.addActor(image);
		}else {
			Sprite sprite = new Sprite(img.getIndex(9), 0, 0, 36, 36);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			line.setSize(widths+4+2, 20);
			line.setPosition(sprite.getWidth()-1, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(9), 80, 0, 10, 36));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(9));
			image.setPosition(-36, 0);
			line.addActor(image);
			image = new Image(img.getIndex(10));
			image.setPosition(line.getWidth()-image.getWidth()-1, 1);
			line.addActor(image);
		}
	}
	private void essenceHP(int maplv, float time) {
		name.remove();
		norline.clear();
		norlineD.clear();
		norlinelos.clear();
		norlineDlos.clear();
		norline.remove();
		norlineD.remove();
		norlinelos.remove();
		norlineDlos.remove();
		hpcurrent = ch.getProperty("HP");
		float linehp = getLineHP(maplv);
		if(hpcurrent <= linehp) {
			Image s = new Image(img.getIndex(2));
			norline.setSize((widths-2)*hpcurrent/linehp, s.getHeight());
			norline.setPosition(36, 8);
			norline.addActor(s);
			addActor(norline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = hpcurrent/linehp;
					if(p >= 0) {
						if(p == 0) {
							hpinit = hpcurrent;
							lostime = 0;
						}
						s = new Image(img.getIndex(7));
						norlinelos.setSize(widths*(p/linehp+pup>1?1-pup:p/linehp), s.getHeight());
						norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
						norlinelos.addActor(s);
						addActor(norlinelos);
					}
					if(p/linehp+pup > 1) {
						s = new Image(img.getIndex(7));
						norlineDlos.setSize(widths*(p/linehp+pup-((int) (p/linehp+pup))), s.getHeight());
						norlineDlos.setPosition(norline.getX(), norline.getY());
						norlineDlos.addActor(s);
						addActor(norlineDlos);
					}
				}
			}
		}else {
			int count = (int) (hpcurrent/linehp);
			if(count >= 1) {
				Image s = new Image(img.getIndex((count-1)%5+2));
				norlineD.setSize(s.getWidth(), s.getHeight());
				norlineD.addActor(s);
				norlineD.setPosition(36, 8);
				addActor(norlineD);
			}
			Image s = new Image(img.getIndex(count%5+2));
			norline.setSize(s.getWidth()*(hpcurrent-linehp*count)/linehp, s.getHeight());
			norline.addActor(s);
			norline.setPosition(36, 8);
			addActor(norline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = (hpcurrent-linehp*count)/linehp;
					if(p/linehp+pup <= 1) {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							s = new Image(img.getIndex(7));
							norlinelos.setSize(widths*p/linehp, s.getHeight());
							norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
							norlinelos.addActor(s);
							addActor(norlinelos);
						}
					}else {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							s = new Image(img.getIndex(7));
							norlinelos.setSize(widths*(1-pup), s.getHeight());
							norlinelos.setPosition(norline.getX()+norline.getWidth(), norline.getY());
							norlinelos.addActor(s);
							addActor(norlinelos);
							s = new Image(img.getIndex(7));
							norlineDlos.setSize(widths*(pup+p/linehp-((int) (p/linehp+pup))), s.getHeight());
							norlineDlos.setPosition(norlineD.getX(), norlineD.getY());
							norlineDlos.addActor(s);
							addActor(norlineDlos);
						}
					}
				}
			}
		}
		addActor(name);
	}
	private void boss(Character ch, Img img, int maplv) {
		float hpmax = ch.getProperty("HPMAX");
		float linehp = getLineHP(maplv);
		if(hpmax <= linehp) {
			Sprite sprite = new Sprite(img.getIndex(11), 0, 0, 32, 36);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			line.setSize(widths*(hpmax/linehp)+4+2, 36);
			line.setPosition(sprite.getWidth()-1, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(11), 80, 0, 10, 36));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(11));
			image.setPosition(-32, 0);
			line.addActor(image);
			image = new Image(img.getIndex(12));
			image.setPosition(line.getWidth()-image.getWidth(), 0);
			line.addActor(image);
		}else {
			Sprite sprite = new Sprite(img.getIndex(11), 0, 0, 32, 36);
			sprite.setPosition(getX(), getY());
			list.add(sprite);
			list.add(sprite);
			line.setSize(widths+4+2, 36);
			line.setPosition(sprite.getWidth()-1, 0);
			addActor(line);
			Image image = new Image(new Sprite(img.getIndex(11), 80, 0, 10, 36));
			image.setPosition(line.getWidth()-image.getWidth()-2, 0);
			line.addActor(image);
			image = new Image(img.getIndex(11));
			image.setPosition(-32, 0);
			line.addActor(image);
			image = new Image(img.getIndex(12));
			image.setPosition(line.getWidth()-image.getWidth(), 0);
			line.addActor(image);
		}
	}
	private void bossHP(int maplv, float time) {
		name.remove();
		bosline.clear();
		boslineD.clear();
		boslinelos.clear();
		boslineDlos.clear();
		bosline.remove();
		boslineD.remove();
		boslinelos.remove();
		boslineDlos.remove();
		hpcurrent = ch.getProperty("HP");
		float linehp = getLineHP(maplv);
		if(hpcurrent <= linehp) {
			bosline.setSize(widths*hpcurrent/linehp, 36);
			bosline.setPosition(33, 1);
			int count = (int) Math.floor(widths/img.getIndex(13).getWidth());
			for(int i = 0; i < count+2; i++) {
				Image image = new Image(img.getIndex(13));
				image.setPosition(i*image.getWidth()-bosshpmove, 6);
				bosline.addActor(image);
			}
			addActor(bosline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = hpcurrent/linehp;
					if(p >= 0) {
						if(p == 0) {
							hpinit = hpcurrent;
							lostime = 0;
						}
						count = (int) Math.floor((p/linehp+pup>1?1-pup:p/linehp)*widths/img.getIndex(18).getWidth());
						boslinelos.setSize(widths*(p/linehp+pup>1?1-pup:p/linehp), 36);
						boslinelos.setPosition(bosline.getX()+bosline.getWidth(), bosline.getY());
						for(int i = 0; i < count+2; i++) {
							Image image = new Image(img.getIndex(18));
							image.setPosition(i*image.getWidth()-bosshpmove, 6);
							boslinelos.addActor(image);
						}
						addActor(boslinelos);
						if(p/linehp+pup > 1) {
							count = (int) Math.floor((p/linehp+pup-((int) (p/linehp+pup)))*widths/img.getIndex(18).getWidth());
							boslineDlos.setSize(widths*(p/linehp+pup-((int) (p/linehp+pup))), 36);
							boslineDlos.setPosition(bosline.getX(), bosline.getY());
							for(int i = 0; i < count+3; i++) {
								Image image = new Image(img.getIndex(18));
								image.setPosition(i*image.getWidth()-bosshpmove, 6);
								boslineDlos.addActor(image);
							}
							addActor(boslineDlos);
						}
					}
				}
			}
		}else {
			int count = (int) (hpcurrent/linehp);
			boslineD.setSize(widths, 36);
			boslineD.setPosition(33, 1);
			if(count-1 >= 0) {
				int index = (int) Math.floor(widths/img.getIndex(13).getWidth());
				for(int i = 0; i < index+3; i++) {
					Image image = new Image(img.getIndex((count-1)%5+13));
					image.setPosition(i*image.getWidth()-bosshpmove, 6);
					boslineD.addActor(image);
				}
				addActor(boslineD);
			}
			bosline.setSize(widths*(hpcurrent-count*linehp)/linehp, 36);
			bosline.setPosition(33, 1);
			int index = (int) Math.floor(((hpcurrent-count*linehp)/linehp*widths)/img.getIndex(13).getWidth());
			for(int i = 0; i < index+2; i++) {
				Image image = new Image(img.getIndex(count%5+13));
				image.setPosition(i*image.getWidth()-bosshpmove, 6);
				bosline.addActor(image);
			}
			addActor(bosline);
			if(time != 0) {
				if(hpinit != hpcurrent) {
					lostime += time;
					float p = (hpinit-hpcurrent)*(1-Math.min(1, Interpolation.exp5Out.apply(lostime/0.2f)));
					float pup = (hpcurrent-linehp*count)/linehp;
					if(p/linehp+pup <= 1) {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							count = (int) Math.floor(p/linehp*widths/img.getIndex(18).getWidth());
							boslinelos.setSize(widths*p/linehp, 36);
							boslinelos.setPosition(bosline.getX()+bosline.getWidth(), bosline.getY());
							for(int i = 0; i < count+2; i++) {
								Image image = new Image(img.getIndex(18));
								image.setPosition(i*image.getWidth()-bosshpmove, 6);
								boslinelos.addActor(image);
							}
							addActor(boslinelos);
						}
					}else {
						if(p >= 0) {
							if(p == 0) {
								hpinit = hpcurrent;
								lostime = 0;
							}
							count = (int) Math.floor((1-pup)*widths/img.getIndex(18).getWidth());
							boslinelos.setSize(widths*(1-pup), 36);
							boslinelos.setPosition(bosline.getX()+bosline.getWidth(), bosline.getY());
							for(int i = 0; i < count+3; i++) {
								Image image = new Image(img.getIndex(18));
								image.setPosition(i*image.getWidth()-bosshpmove, 6);
								boslinelos.addActor(image);
							}
							addActor(boslinelos);
							count = (int) Math.floor((p/linehp+pup-((int) (p/linehp+pup)))*widths/img.getIndex(18).getWidth());
							boslineDlos.setSize(widths*(p/linehp+pup-1), 36);
							boslineDlos.setPosition(boslineD.getX(), boslineD.getY());
							for(int i = 0; i < count+3; i++) {
								Image image = new Image(img.getIndex(18));
								image.setPosition(i*image.getWidth()-bosshpmove, 6);
								boslineDlos.addActor(image);
							}
							addActor(boslineDlos);
						}
					}
				}
			}
		}
		addActor(name);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(ch != null && visible) {
			if(hpcurrent == ch.getProperty("HP")) {
				showtime += delta;
				if(showtime >= 3) {
					ch = null;
					clean();
					showtime = 0;
					hpinit = 0;
					lostime = 0;
					visible = false;
				}
			}else
				showtime = 0;
			time += delta;
			if(time >= 0.05f) {
				time -= 0.05f;
				if(isboss) {
					bosshpmove += 1;
					if(bosshpmove >= 38)
						bosshpmove = 0;
				}else {
					time = 0;
					bosshpmove = 0;
				}
				if(ch != null)
					drawHP(maplv, delta);
			}
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(visible) {
			for(Sprite s : list)
				s.draw(batch, parentAlpha);
			super.draw(batch, parentAlpha);
			for(Sprite s : label)
				s.draw(batch, parentAlpha);
			face.draw(batch, parentAlpha);
			if(ch != null && ch.getProperty("HP") == 0)
				die.draw(batch, parentAlpha);
		}
	}
	private float getLineHP(int lv) {
		float hp = 100;
		switch(lv) {
			case 1:hp = 100;break;
			case 2:hp = 200;break;
			case 3:hp = 300;break;
			case 4:hp = 400;break;
			case 5:hp = 500;break;
			case 6:hp = 700;break;
			case 7:hp = 900;break;
			case 8:hp = 1100;break;
			case 9:hp = 1300;break;
			case 10:hp = 1500;break;
			case 11:hp = 1800;break;
			case 12:hp = 2100;break;
			case 13:hp = 2400;break;
			case 14:hp = 2700;break;
			case 15:hp = 3000;break;
			case 16:hp = 3400;break;
			case 17:hp = 3800;break;
			case 18:hp = 4200;break;
			case 19:hp = 4600;break;
			case 20:hp = 5000;break;
			case 21:hp = 5500;break;
			case 22:hp = 6000;break;
			case 23:hp = 6500;break;
			case 24:hp = 7000;break;
			case 25:hp = 7500;break;
			case 26:hp = 8100;break;
			case 27:hp = 8700;break;
			case 28:hp = 9300;break;
			case 29:hp = 9900;break;
			case 30:hp = 10500;break;
			case 31:hp = 11700;break;
			case 32:hp = 12400;break;
			case 33:hp = 13100;break;
			case 34:hp = 13800;break;
			case 35:hp = 14500;break;
			case 36:hp = 15300;break;
			case 37:hp = 16100;break;
			case 38:hp = 16900;break;
			case 39:hp = 17700;break;
			case 40:hp = 18500;break;
			case 41:hp = 19400;break;
			case 42:hp = 20300;break;
			case 43:hp = 21200;break;
			case 44:hp = 22100;break;
			case 45:hp = 23000;break;
			case 46:hp = 24000;break;
			case 47:hp = 25000;break;
			case 48:hp = 26000;break;
			case 49:hp = 27000;break;
			case 50:hp = 28100;break;
			case 51:hp = 29200;break;
			case 52:hp = 30300;break;
			case 53:hp = 31400;break;
			case 54:hp = 32500;break;
			case 55:hp = 33600;break;
			case 56:hp = 34800;break;
			case 57:hp = 36000;break;
			case 58:hp = 37200;break;
			case 59:hp = 38400;break;
			case 60:hp = 39600;break;
		}
		return hp;
	}
}