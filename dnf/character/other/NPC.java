package dnf.character.other;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.read.Img;
import dnf.gupoublex.set.SetBase;
import dnf.gupoublex.set.SetImg;
import dnf.gupoublex.set.SetNPC;

public class NPC extends Character {
	private String path = null;
	private float time = 0;
	private Character control = null;
	private float randomtime = 0;
	private boolean start = false;
	public NPC(GuPoubleXGame game, String name, Img img, boolean town, Vector2 fix, int fixname) {
		super(game, name, img, fixname);
		if(town)
			enterTown();
		else {
			enterInstance(new Vector2(0, 0));
			enterInstance();
		}
		this.fix = fix;
		loadSound(name);
		boolean right = ((int) (Math.random()*100))%2==0?true:false;
		randomtime = (float) (Math.random()*1.5f*(right?1:-1));
	}
	public void lookat(Character ch) {
		control = ch;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(getName().equals(SetNPC._fire)) {
			if(!start && getGame().isload(path, "amb_fire.ogg", "music")) {
				start = true;
				Music s = getGame().getMusic(path, "amb_fire.ogg");
				s.setLooping(true);
				s.setVolume((getGame().getSound_character()));
				s.play();
			}
		}else if(near()) {
			time += delta;
			if(time >= 5+randomtime) {
				time -= 5+randomtime;
				boolean right = ((int) (Math.random()*100))%2==0?true:false;
				randomtime = (float) (Math.random()*1.5f*(right?1:-1));
				Sound s = getGame().getSound(path, new String[]{"_amb_", "_tk_"}, (float) (Math.random()));
				if(s != null)
					s.play(getGame().getSound_character());
			}
		}
	}
	private boolean near() {
		if(control == null)
			return false;
		else {
			int x = (int) (getX() - control.getX());
			int y = (int) ((getY() - control.getY())*SetBase.fixradius/2);
			if(x*x+y*y <= 300*300)
				return true;
			else
				return false;
		}
	}
	private void loadSound(String name) {
		if(name.equals(SetNPC._seria))
			path = SetImg.npc_sound+"hotel/npc_seria/";
		else if(name.equals(SetNPC._linus))
			path = SetImg.npc_sound+"elvengard/npc_rs/";
		else if(name.equals(SetNPC._dj))
			path = SetImg.npc_sound+"elvengard/npc_dj0/";
		else if(name.equals(SetNPC._al))
			path = SetImg.npc_sound+"hendonmyre/npc_al/";
		else if(name.equals(SetNPC._bk))
			path = SetImg.npc_sound+"hendonmyre/npc_bk/";
		else if(name.equals(SetNPC._dj1))
			path = SetImg.npc_sound+"hendonmyre/npc_dj1/";
		else if(name.equals(SetNPC._gd))
			path = SetImg.npc_sound+"hendonmyre/npc_gd/";
		else if(name.equals(SetNPC._gsd))
			path = SetImg.npc_sound+"hendonmyre/npc_gsd/";
		else if(name.equals(SetNPC._kanna))
			path = SetImg.npc_sound+"hendonmyre/npc_kanna/";
		else if(name.equals(SetNPC._kr))
			path = SetImg.npc_sound+"hendonmyre/npc_kr/";
		else if(name.equals(SetNPC._or))
			path = SetImg.npc_sound+"hendonmyre/npc_or/";
		else if(name.equals(SetNPC._pj))
			path = SetImg.npc_sound+"hendonmyre/npc_pj/";
		else if(name.equals(SetNPC._su))
			path = SetImg.npc_sound+"hendonmyre/npc_su/";
		else if(name.equals(SetNPC._rt))
			path = SetImg.npc_sound+"hendonmyre/npc_rt/";
		else if(name.equals(SetNPC._sin))
			path = SetImg.npc_sound+"hendonmyre/npc_sin/";
		else if(name.equals(SetNPC._dj2))
			path = SetImg.npc_sound+"westcoast/npc_dj2/";
		else if(name.equals(SetNPC._dj3))
			path = SetImg.npc_sound+"westcoast/npc_dj3/";
		else if(name.equals(SetNPC._ir))
			path = SetImg.npc_sound+"westcoast/npc_ir/";
		else if(name.equals(SetNPC._kg))
			path = SetImg.npc_sound+"westcoast/npc_kg/";
		else if(name.equals(SetNPC._lo))
			path = SetImg.npc_sound+"westcoast/npc_lo/";
		else if(name.equals(SetNPC._dp))
			path = SetImg.npc_sound+"westcoast/npc_dp/";
		else if(name.equals(SetNPC._marlene))
			path = SetImg.npc_sound+"westcoast/npc_marlene/";
		else if(name.equals(SetNPC._op))
			path = SetImg.npc_sound+"westcoast/npc_op/";
		else if(name.equals(SetNPC._rg))
			path = SetImg.npc_sound+"westcoast/npc_rg/";
		else if(name.equals(SetNPC._sr))
			path = SetImg.npc_sound+"westcoast/npc_sr/";
		else if(name.equals(SetNPC._deliah))
			path = SetImg.npc_sound+"special/npc_deliah/";
		else if(name.equals(SetNPC._fire))
			path = SetImg.instance_enviromnet+"reward/";
		if(name.equals(SetNPC._fire))
			getGame().load(path+"amb_fire", "music");
		else
			getGame().load(path, "sound");
	}
	public void dispose() {
		if(getName().equals(SetNPC._fire))
			getGame().unload(path, "music");
		else
			getGame().unload(path, "sound");
		super.dispose();
	}
}