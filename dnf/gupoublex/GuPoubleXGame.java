package dnf.gupoublex;

import java.io.File;
import java.text.SimpleDateFormat;
import dnf.character.Character;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import dnf.admin.ScreenAdmin;
import dnf.author.LazyBitmapFont;
import dnf.author.TextLabel;
import dnf.comic.ScreenComic;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.read.ImgLoader;
import dnf.gupoublex.set.SetAdmin;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetTown;
import dnf.instance.ScreenInstance;
import dnf.instance.StoreMap;
import dnf.town.ScreenTown;

public class GuPoubleXGame extends Game {
	private String desktopImg = "../ImagePacks/";
	private String desktopSod = desktopImg+"../SoundPacks/Sound/";
	private String desktopMic = desktopImg+"../SoundPacks/Music/";
	private String androidPath = "Pictures";//"Pictures";//"GuPoubleXGame";
	private String androidImg = "./"+androidPath+"/ImagePacks/";
	private String androidSod = androidImg+"../SoundPacks/Sound/";
	private String androidMic = androidImg+"../SoundPacks/Music/";
	private AssetManager manager = null;
	private FreeTypeFontGenerator generator = null;
	private Array<Preferences> pref_char = null;
	private Array<Character> list = null;
	private Character chose = null;
	private float scale = 1;
	private float scaleX = 0;
	private float scaleY = 0;
	private int channel = 0;
	private int createID = 0;
	private Vector2 scn = null;
	private Image loading = null;
	private Vector2 ver = null;
	private TextLabel info = null;
	private float infotime = 0;
	private LazyBitmapFont lazy = null;
	private String account = null;
	private boolean first = false;
	private String filepath = null;
	private String lastmapstr = null;
	private int lastlv = 1;
	private Array<Integer> lastinfo = null;
	private ScreenAdmin sa = null;
	private ScreenComic sc = null;
	private ScreenTown st = null;
	private ScreenInstance si = null;
	private TempScreen ts = null;
	public GuPoubleXGame(int width, int height, String path) {
		super();
		scn = new Vector2(width, height);
		filepath = path;
	}
	public Preferences getPref(String path) {
		switch(Gdx.app.getType()) {
			case Android:path += SetBase.pref_suffix;break;
			case iOS:path += SetBase.pref_suffix;break;
			case Desktop:path += SetBase.pref_suffix;break;
			case WebGL:path += SetBase.pref_suffix;break;
			default:break;
		}
		return Gdx.app.getPreferences(path);
	}
	public void delPref(String path) {
		switch(Gdx.app.getType()) {
			case Android:path = Gdx.files.getExternalStoragePath()+filepath+path+SetBase.pref_suffix;break;
			case iOS:path = Gdx.files.getExternalStoragePath()+filepath+path+SetBase.pref_suffix;break;
			case Desktop:path = Gdx.files.getExternalStoragePath()+filepath+path+SetBase.pref_suffix;break;
			case WebGL:path = Gdx.files.getExternalStoragePath()+filepath+path+SetBase.pref_suffix;break;
			default:break;
		}
		File f = new File(path);
		if(f.exists())
			f.delete();
	}
	public LazyBitmapFont getLazy() {
		return lazy;
	}
	private FileHandleResolver getFileHandleResolver() {
		FileHandleResolver resolvers = null;
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:resolvers = new ExternalFileHandleResolver();break;
			case Desktop:resolvers = new LocalFileHandleResolver();break;
			case WebGL:resolvers = new InternalFileHandleResolver();break;
			default:break;
		}
		return resolvers;
	}
	@Override
	public void create() {
		switch(Gdx.app.getType()) {
			case Android:createID = 1;break;
			case iOS:createID = 2;break;
			case Desktop:createID = 3;break;
			case WebGL:createID = 4;break;
			default:createID = 5;break;
		}
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		manager = new AssetManager();
		manager.setLoader(Img.class, new ImgLoader(getFileHandleResolver()));
		manager.setLoader(Music.class, new MusicLoader(getFileHandleResolver()));
		manager.setLoader(Sound.class, new SoundLoader(getFileHandleResolver()));
		list = new Array<Character>();
		generator = new FreeTypeFontGenerator(Gdx.files.internal(SetBase.fontKAITI));
		scaleX = 1.0f*SetBase.dir_width/scn.x;
		scaleY = 1.0f*SetBase.dir_height/scn.y;
		scale = Math.max(scaleX, scaleY);
		ver = new Vector2(scn.x*scale, scn.y*scale);
		check();
		lazy = new LazyBitmapFont(generator, 20);
		info = new TextLabel(this);
		info.setSize(ver.x, ver.y/30);
		info.setAlignment(Align.center, Align.left);
		info.setPosition(0, ver.y-info.getHeight());
		load(SetImg.characterinfo, "img");
		load(SetImg.common, "img");
		load(SetBase.common, "sound");
		new StoreMap(this);
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | 
				(Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
		super.render();
		manager.update(1);
		if(isload(SetImg.common, "nowloading.img", "img") && !first) {
			first = true;
			loading = new Image(getImg(SetImg.common, "nowloading.img").getIndex(0));
			chanegAdmin("login");
		}
	}
	@Override
	public void resume() {
		if(manager != null) {
			manager.update();
			Texture.setAssetManager(manager);
		}
	}
	@Override
	public void dispose() {
		unload(SetImg.characterinfo, "img");
		unload(SetImg.common, "img");
		unload(SetBase.common, "sound");
		manager.clear();
		manager.dispose();
		lazy.dispose();
		generator.dispose();
		super.dispose();
		Gdx.app.log(SetBase.TAG, "All release");
		Gdx.app.exit();
	}
	public void addList(Character ch) {
		list.add(ch);
	}
	public Array<Character> getList() {
		return list;
	}
	public String getPart(int part) {
		if(SetTown.lorien == part)
			return "lorien";
		else if(SetTown.granfloris1 == part)
			return "granfloris1";
		else if(SetTown.granfloris2 == part)
			return "granfloris2";
		return "";
	}
	public void change(String str, String exstr, int lv, Array<Integer> info) {
		if(str.equals("admin"))
			chanegAdmin(exstr);
		else if(str.equals("comic"))
			chanegComic(exstr);
		else if(str.equals("town"))
			chanegTown(exstr);
		else if(str.equals("instance")) {
			lastmapstr = exstr;
			lastlv = lv;
			lastinfo = info;
			chanegInstance(exstr, lv, info);
		}else if(str.equals("null")) {
			if(si != null)
				si.dispose();
			si = null;
			ts = new TempScreen(this);
			setScreen(ts);
		}
	}
	public void changeLastMap() {
		change("instance", lastmapstr, lastlv, lastinfo);
		ts.dispose();
		ts = null;
	}
	private void chanegAdmin(String str) {
		if(sc != null)
			sc.dispose();
		sc = null;
		if(st != null)
			st.dispose();
		st = null;
		if(chose != null) {
			chose.enterAdmin();
			chose.setTownPart(SetTown.hotel);
		}
		sa = new ScreenAdmin(this, ver, scaleX, scaleY, str);
		setScreen(sa);
	}
	private void chanegComic(String str) {
		if(sa != null)
			sa.dispose();
		sa = null;
		sc = new ScreenComic(this, str, ver, scaleX, scaleY);
		setScreen(sc);
	}
	private void chanegTown(String str) {
		if(sa != null)
			sa.dispose();
		sa = null;
		if(si != null)
			si.dispose();
		si = null;
		chose.enterTown();
		if(str.equals("hotel"))
			st = new ScreenTown(this, manager, ver, scn, scaleX, scaleY, chose, new Vector2(SetTown.init_x, SetTown.init_y));
		else
			st = new ScreenTown(this, manager, ver, scn, scaleX, scaleY, chose, chose.getLastTownP());
		st.change(str);
		setScreen(st);
	}
	private void chanegInstance(String str, int lv, Array<Integer> info) {
		if(st != null)
			st.dispose();
		st = null;
		si = new ScreenInstance(this, ver, scn, scaleX, scaleY, chose, str, lv, info);
		setScreen(si);
	}
	public void show(Stage stage, float delta) {
		if(info.getParent() == null || info.getParent() != stage.getRoot()) {
			info.remove();
			stage.addActor(info);
		}
		infotime += delta;
		if(infotime >= 0.1f) {
			infotime -= 0.1f;
			String text = "";
			if(isShowtime())
				text = "Time:"+new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())+" ";
			if(isShowfps())
				text += "FPS:"+Gdx.graphics.getFramesPerSecond()+" ";
			if(isShowmemery())
				text += "Heap<Native|Java>:"+(Gdx.app.getNativeHeap()/1000/1000)+"MB|"+(Gdx.app.getJavaHeap()/1000/1000)+"MB";
			info.setText(text);
			text = "";
		}
	}
	private void check() {
		boolean init = getPref(SetBase.saveGeneral).getBoolean("INIT");
		if(!init) {
			Preferences general = getPref(SetBase.saveGeneral);
			general.putBoolean("INIT", true);
			general.putInteger("CREATEID", createID);
			general.putFloat("SOUND_MUSIC", 1);
			general.putFloat("SOUND_ENVIRONMENT", 1);
			general.putFloat("SOUND_CHARACTER", 1);
			general.putFloat("SOUND_BUTTON", 1);
			general.putBoolean("TIME", false);
			general.putBoolean("FPS", true);
			general.putBoolean("MEMERY", true);
			general.putInteger("SERVER", 10);
			general.putBoolean("DEBUG", false);
			general.putBoolean("BUTTON", true);
			general.flush();
			Preferences hud = getPref(SetBase.saveHud);
			hud.putInteger("ESC", Keys.ESCAPE);
			hud.putInteger("UP", Keys.W);
			hud.putInteger("DOWN", Keys.S);
			hud.putInteger("RIGHT", Keys.D);
			hud.putInteger("LEFT", Keys.A);
			hud.putInteger("ATTACK", Keys.K);
			hud.putInteger("JUMP", Keys.L);
			hud.putInteger("HEAVYATTACK", Keys.J);
			hud.putInteger("SKILL_1", Keys.NUM_6);
			hud.putInteger("SKILL_2", Keys.NUM_7);
			hud.putInteger("SKILL_3", Keys.NUM_8);
			hud.putInteger("SKILL_4", Keys.NUM_9);
			hud.putInteger("SKILL_5", Keys.NUM_0);
			hud.putInteger("SKILL_6", Keys.MINUS);
			hud.putInteger("SKILL_7", Keys.Y);
			hud.putInteger("SKILL_8", Keys.U);
			hud.putInteger("SKILL_9", Keys.I);
			hud.putInteger("SKILL_10", Keys.O);
			hud.putInteger("SKILL_11", Keys.P);
			hud.putInteger("SKILL_12", Keys.LEFT_BRACKET);
			hud.flush();
			Preferences sword = getPref("swordman");
			sword.putString("PROFESSION", SetAdmin.swordman);
			sword.putFloat("TIME", 0);
			sword.putInteger("PL", SetCharProperty.PL_min);
			sword.putLong("EXP", 0);
			sword.putInteger("SP", 0);
			sword.putInteger("SPTOTAL", 0);
			sword.putFloat("TIME_INSTANCE", 0);
			sword.putString("NAME", SetAdmin.swordman);
			sword.putString("HAIR", "hair/sm_hair0000a");
			sword.putString("CAP", "");
			sword.putString("FACE", "");
    		sword.putString("NECK", "");
    		sword.putString("COAT", "coat/sm_coat0000a");
    		sword.putString("SKIN", "skin/sm_body0000");
    		sword.putString("BELT", "");
    		sword.putString("PANTS", "pants/sm_pants0000a");
    		sword.putString("SHOES", "shoes/sm_shoes0000a");
    		sword.putString("WEAPON", "weapon/lswd/lswd0000b");
    		sword.putInteger("TOWN", SetTown.elvengard);
    		sword.putFloat("WEAK", 1);
    		sword.putFloat("STR", 1);
    		sword.putFloat("INT", 1);
    		sword.putFloat("VIT", 100);
    		sword.putFloat("MEN", 1);
    		sword.putFloat("ATKPHY", 1);
    		sword.putFloat("ATKMIG", 1);
    		sword.putFloat("DEFPHY", 1);
    		sword.putFloat("DEFMIG", 1);
    		sword.putFloat("CRT", 0);
    		sword.putFloat("CRD", 100);
    		sword.putFloat("SPHY", 0);
    		sword.putFloat("SMIG", 0);
    		sword.putFloat("SMOV", 0);
    		sword.putInteger("SKILL1", 52);
    		sword.putInteger("SKILL2", 78);
    		sword.putInteger("SKILL3", 150);
    		sword.putInteger("SKILL4", 156);
    		sword.putInteger("SKILL5", 162);
    		sword.putInteger("SKILL6", 172);
    		sword.putInteger("SKILL7", -1);
    		sword.putInteger("SKILL8", -1);
    		sword.putInteger("SKILL9", -1);
    		sword.putInteger("SKILL10", -1);
    		sword.putInteger("SKILL11", -1);
    		sword.putInteger("SKILL12", -1); 
    		sword.flush();
		}
	}
	private void flush(String str, String key, Object value, Preferences pref) {
		int type = 0;
		if(str.equals("int"))
			type = 1;
		else if(str.equals("float"))
			type = 2;
		else if(str.equals("long"))
			type = 3;
		else if(str.equals("boolean"))
			type = 4;
		else if(str.equals("string"))
			type = 5;
		switch(type) {
			case 1:pref.putInteger(key, Integer.valueOf(value.toString()));break;
			case 2:pref.putFloat(key, Float.valueOf(value.toString()));break;
			case 3:pref.putLong(key, Long.valueOf(value.toString()));break;
			case 4:pref.putBoolean(key, Boolean.valueOf(value.toString()));break;
			case 5:pref.putString(key, value.toString());break;
		}
		if(type != 0)
			pref.flush();
	}
	public AssetManager getManager() {
		return manager;
	}
	public Array<Preferences> getPref_char() {
		return pref_char;
	}
	public void setPref_char(Array<Preferences> pref_char) {
		this.pref_char = pref_char;
	}
	public float getSound_music() {
		return getPref(SetBase.saveGeneral).getFloat("SOUND_MUSIC");
	}
	public void setSound_music(float sound_music) {
		flush("float", "sound_music".toUpperCase(), sound_music, getPref(SetBase.saveGeneral));
	}
	public float getSound_environment() {
		return getPref(SetBase.saveGeneral).getFloat("SOUND_ENVIRONMENT");
	}
	public void setSound_environment(float sound_environment) {
		flush("float", "sound_environment".toUpperCase(), sound_environment, getPref(SetBase.saveGeneral));
	}
	public float getSound_button() {
		return getPref(SetBase.saveGeneral).getFloat("SOUND_BUTTON");
	}
	public void setSound_button(float sound_button) {
		flush("float", "sound_button".toUpperCase(), sound_button, getPref(SetBase.saveGeneral));
	}
	public float getSound_character() {
		return getPref(SetBase.saveGeneral).getFloat("SOUND_CHARACTER");
	}
	public void setSound_character(float sound_character) {
		flush("float", "sound_character".toUpperCase(), sound_character, getPref(SetBase.saveGeneral));
	}
	private boolean isShowtime() {
		return getPref(SetBase.saveGeneral).getBoolean("TIME");
	}
	public void setShowtime(boolean showtime) {
		flush("boolean", "time".toUpperCase(), showtime, getPref(SetBase.saveGeneral));
	}
	private boolean isShowfps() {
		return getPref(SetBase.saveGeneral).getBoolean("FPS");
	}
	public void setShowfps(boolean showfps) {
		flush("boolean", "fps".toUpperCase(), showfps, getPref(SetBase.saveGeneral));
	}
	private boolean isShowmemery() {
		return getPref(SetBase.saveGeneral).getBoolean("MEMERY");
	}
	public void setShowmemery(boolean showmemery) {
		flush("boolean", "memery".toUpperCase(), showmemery, getPref(SetBase.saveGeneral));
	}
	public Image getLoading() {
		return loading;
	}
	public int getServer() {
		return getPref(SetBase.saveGeneral).getInteger("SERVER");
	}
	public void setServer(int server) {
		flush("int", "server".toUpperCase(), server, getPref(SetBase.saveGeneral));
	}
	public int getCreateID() {
		return getPref(SetBase.saveGeneral).getInteger("CREATEID");
	}
	public int getCreateUsers() {
		return getPref(SetBase.save_prefix+"Account").getInteger("CREATEUSERS");
	}
	public void setCreateUsers(int createusers) {
		flush("int", "createusers".toUpperCase(), createusers, getPref(SetBase.save_prefix+"Account"));
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Character getChose() {
		return chose;
	}
	public void setChose(Character chose) {
		this.chose = chose;
	}
	public void showLoad() {
		for(String s : manager.getAssetNames())
			System.out.println(s);
	}
	private FileHandle trans(String path, String type) {
		FileHandle handle = null;
		String s = null;
		switch(Gdx.app.getType()) {
			case Android:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			case iOS:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			case Desktop:s = type.equals("music")?desktopMic:(type.equals("sound")?desktopSod:desktopImg);break;
			case WebGL:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			default:break;
		}
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:handle = Gdx.files.external(s+path);break;
			case Desktop:handle = Gdx.files.local(s+path);break;
			case WebGL:handle = Gdx.files.internal(s+path);break;
			default:break;
		}
		return handle;
	}
	private FileHandle trans2(String path, String type) {
		FileHandle handle = null;
		String s = null;
		switch(Gdx.app.getType()) {
			case Android:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			case iOS:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			case Desktop:s = type.equals("music")?desktopMic:(type.equals("sound")?desktopSod:desktopImg);break;
			case WebGL:s = type.equals("music")?androidMic:(type.equals("sound")?androidSod:androidImg);break;
			default:break;
		}
		switch(Gdx.app.getType()) {
			case Android:
			case iOS:handle = Gdx.files.external(s+path+((type.equals("music")||type.equals("sound"))?".ogg":".img"));break;
			case Desktop:handle = Gdx.files.local(s+path+((type.equals("music")||type.equals("sound"))?".ogg":".img"));break;
			case WebGL:handle = Gdx.files.internal(s+path+((type.equals("music")||type.equals("sound"))?".ogg":".img"));break;
			default:break;
		}
		return handle;
	}
	public void load(String path, String type) {
		if(path == null)
			return;
		FileHandle handle = trans(path, type);
		if(handle.isDirectory()) {
			for(FileHandle f : handle.list()) {
				if(f.isDirectory())
					continue;
				if(type.equals("music")) {
					if(!manager.isLoaded(f.toString(), Music.class))
						manager.load(f.toString(), Music.class);
				}else if(type.equals("sound")) {
					if(!manager.isLoaded(f.toString(), Sound.class))
						manager.load(f.toString(), Sound.class);
				}else if(type.equals("img")) {
					if(!manager.isLoaded(f.toString(), Img.class))
						manager.load(f.toString(), Img.class);
				}
			}
		}else {
			handle = trans2(path, type);
			if(type.equals("music")) {
				if(!manager.isLoaded(handle.toString(), Music.class))
					manager.load(handle.toString(), Music.class);
			}else if(type.equals("sound")) {
				if(!manager.isLoaded(handle.toString(), Sound.class))
					manager.load(handle.toString(), Sound.class);
			}else if(type.equals("img")) {
				if(!manager.isLoaded(handle.toString(), Img.class))
					manager.load(handle.toString(), Img.class);
			}
		}
	}
	public boolean isload(String path, int index, String type) {
		boolean score = false;
		FileHandle handle = trans(path, type);
		if(handle.isDirectory() && index >= 0) {
			if(type.equals("music")) {
				if(manager.isLoaded(handle.list()[index].toString(), Music.class))
					score = true;
			}else if(type.equals("sound")) {
				if(manager.isLoaded(handle.list()[index].toString(), Sound.class))
					score = true;
			}else if(type.equals("img")) {
				if(manager.isLoaded(handle.list()[index].toString(), Img.class))
					score = true;
			}
		}else {
			handle = trans2(path, type);
			if(type.equals("music")) {
				if(manager.isLoaded(handle.toString(), Music.class))
					score = true;
			}else if(type.equals("sound")) {
				if(manager.isLoaded(handle.toString(), Sound.class))
					score = true;
			}else if(type.equals("img")) {
				if(manager.isLoaded(handle.toString(), Img.class))
					score = true;
			}
		}
		return score;
	}
	public boolean isload(String path, String type) {
		return isload(path, -1, type);
	}
	public boolean isload(String path, String name, String type) {
		boolean score = false;
		FileHandle handle = trans(path, type);
		if(!handle.isDirectory())
			return score;
		for(FileHandle f : handle.list()) {
			if(f.isDirectory())
				continue;
			if(f.name().equals(name))
				if(type.equals("music")) {
					if(manager.isLoaded(f.toString(), Music.class))
						score = true;
				}else if(type.equals("sound")) {
					if(manager.isLoaded(f.toString(), Sound.class))
						score = true;
				}else if(type.equals("img")) {
					if(manager.isLoaded(f.toString(), Img.class))
						score = true;
				}
		}
		return score;
	}
	public Music getMusic(String path, String name) {
		Music ob = null;
		FileHandle handle = trans(path, "music");
		if(!handle.isDirectory()) {
			handle = trans2(path, "music");
			if(manager.isLoaded(handle.toString(), Music.class))
				ob = manager.get(handle.toString(), Music.class);
		}else
			for(FileHandle f : handle.list()) {
				if(f.isDirectory())
					continue;
				if(f.name().equals(name))
					if(manager.isLoaded(f.toString(), Music.class)) {
						ob = manager.get(f.toString(), Music.class);
						break;
					}
			}
		return ob;
	}
	public Sound getSound(String path, String name) {
		Sound ob = null;
		FileHandle handle = trans(path, "sound");
		if(!handle.isDirectory()) {
			handle = trans2(path, "sound");
			if(manager.isLoaded(handle.toString(), Sound.class))
				ob = manager.get(handle.toString(), Sound.class);
		}else
			for(FileHandle f : handle.list()) {
				if(f.isDirectory())
					continue;
				if(f.name().equals(name))
					if(manager.isLoaded(f.toString(), Sound.class)) {
						ob = manager.get(f.toString(), Sound.class);
						break;
					}
			}
		return ob;
	}
	public Sound getSound(String path, String name[], float percent) {
		FileHandle handle = trans(path, "sound");
		Array<Sound> s = new Array<Sound>();
		for(FileHandle f : handle.list()) {
			if(f.isDirectory())
				continue;
			for(String n : name)
				if(f.name().indexOf(n) != -1)
					s.add(manager.get(f.toString(), Sound.class));
		}
		if(s.size == 0)
			return null;
		if(s.size == 1)
			return s.get(0);
		return s.get(((int) (percent*s.size))%s.size);
	}
	public Img getImg(String path, String name) {
		Img ob = null;
		FileHandle handle = trans(path, "img");
		if(!handle.isDirectory()) {
			handle = trans2(path, "img");
			if(manager.isLoaded(handle.toString(), Img.class))
				ob = manager.get(handle.toString(), Img.class);
		}else
			for(FileHandle f : handle.list()) {
				if(f.isDirectory())
					continue;
				if(f.name().equals(name))
					if(manager.isLoaded(f.toString(), Img.class)) {
						ob = manager.get(f.toString(), Img.class);
						break;
					}
			}
		return ob;
	}
	public Music getMusic(String path, int index) {
		Music ob = null;
		FileHandle handle = trans(path, "music");
		if(handle.isDirectory() && index >= 0) {
			if(manager.isLoaded(handle.list()[index].toString(), Music.class))
				ob = manager.get(handle.list()[index].toString(), Music.class);
		}else {
			handle = trans2(path, "music");
			if(manager.isLoaded(handle.toString(), Music.class))
				ob = manager.get(handle.toString(), Music.class);
		}
		return ob;
	}
	public Sound getSound(String path, int index) {
		Sound ob = null;
		FileHandle handle = trans(path, "sound");
		if(handle.isDirectory() && index >= 0) {
			if(manager.isLoaded(handle.list()[index].toString(), Sound.class))
				ob = manager.get(handle.list()[index].toString(), Sound.class);
		}else {
			handle = trans2(path, "suond");
			if(manager.isLoaded(handle.toString(), Sound.class))
				ob = manager.get(handle.toString(), Sound.class);
		}
		return ob;
	}
	public Img getImg(String path, int index) {
		Img ob = null;
		FileHandle handle = trans(path, "img");
		if(handle.isDirectory() && index >= 0) {
			if(manager.isLoaded(handle.list()[index].toString(), Img.class))
				ob = manager.get(handle.list()[index].toString(), Img.class);
		}else {
			handle = trans2(path, "img");
			if(manager.isLoaded(handle.toString(), Img.class))
				ob = manager.get(handle.toString(), Img.class);
		}
		return ob;
	}
	public void unload(String path, String type) {
		if(path == null)
			return;
		FileHandle handle = trans(path, type);
		if(handle.isDirectory()) {
			for(FileHandle f : handle.list()) {
				if(f.isDirectory())
					continue;
				if(type.equals("music")) {
					if(manager.isLoaded(f.toString(), Music.class))
						manager.unload(f.toString());
				}else if(type.equals("sound")) {
					if(manager.isLoaded(f.toString(), Sound.class))
						manager.unload(f.toString());
				}else if(type.equals("img")) {
					if(manager.isLoaded(f.toString(), Img.class)) {
						Img img = manager.get(f.toString(), Img.class);
						img.dispose();
						img = null;
						manager.unload(f.toString());
					}
				}
			}
		}else {
			handle = trans2(path, type);
			if(type.equals("music")) {
				if(manager.isLoaded(handle.toString(), Music.class))
					manager.unload(handle.toString());
			}else if(type.equals("sound")) {
				if(manager.isLoaded(handle.toString(), Sound.class))
					manager.unload(handle.toString());
			}else if(type.equals("img")) {
				if(manager.isLoaded(handle.toString(), Img.class)) {
					Img img = manager.get(handle.toString(), Img.class);
					img.dispose();
					img = null;
					manager.unload(handle.toString());
				}
			}
		}
	}
}