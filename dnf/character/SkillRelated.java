package dnf.character;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import dnf.character.roletype.humen.*;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetCharProperty;
import dnf.gupoublex.set.SetSwordmanSkill;

public class SkillRelated implements Disposable {
	private Character ch = null;
	private GuPoubleXGame game = null;
	private Array<String> skill = null;
	private String effect_path = null;
	private String sound_path = null;
	private String weapon_path = null;
	public SkillRelated(GuPoubleXGame game, Character ch, Preferences pref) {
		super();
		this.ch = ch;
		this.game = game;
		skill = new Array<String>();
		if(ch instanceof Swordman) {
			effect_path = SetCharProperty.path_swordman+"effect/";
			sound_path = SetCharProperty.path_swordman+"sound/";
			weapon_path = SetCharProperty.path_swordman+"weapon/";
			for(int i = 1; i < 13; i++)
				swordmanPose(pref.getInteger("SKILL"+i));
		}
	}
	private void swordmanPose(int i) {
    	switch(i) {
    		case 0:break;
    		case 1:break;
    		case 2:break;
    		case 3:break;
    		case 4:break;
    		case 5:break;
    		case 6:break;
    		case 7:break;
    		case 8:break;
    		case 9:break;
    		case 12:break;
    		case 78:break;
    		case 83:break;
    		case 52:
    		case 82:
    		case 84:skill.add(SetSwordmanSkill.ghost);break;
    		case 128://pose = new Vaneslash(ch.getGame(), 5);break;
    		case 132://pose = new Shizi(ch.getGame(), 5);break;
    		case 133:break;
    		case 134:skill.add(SetSwordmanSkill.frenzy);break;
    		case 150://pose = new Chargecrash(ch.getGame(), 10);break;
    		case 156://pose = new Jianwu(ch.getGame(), 10);break;
    		case 162://pose = new Rapidmoveslash(ch.getGame(), 10);break;
    		case 172://pose = new Outragebreak(ch.getGame(), 10);break;
    		case 191:break;
    		case 192:skill.add(SetSwordmanSkill.hundredsword);break;
    		default:break;
    	}
    }
	public Sound getSound(int id, int type) {
		if(type == SetCharProperty.sound)
			return game.getSound(sound_path, id);
		else if(type == SetCharProperty.effect)
			return game.getSound(effect_path, id);
		else if(type == SetCharProperty.weapon)
			return game.getSound(weapon_path, id);
		else
			return null;
	}
	public void loadInstance() {
		if(ch.inInstance()) {
			game.load(sound_path, "sound");
			game.load(effect_path, "sound");
			game.load(weapon_path, "sound");
			for(String s : skill)
				game.load(s, "img");
		}
	}
	public void unloadInstance() {
		if(!ch.inInstance()) {
			game.unload(sound_path, "sound");
			game.unload(effect_path, "sound");
			game.unload(weapon_path, "sound");
			for(String s : skill)
				game.unload(s, "img");
		}
	}
	@Override
	public void dispose() {
		if(!ch.inInstance()) {
			game.unload(sound_path, "sound");
			game.unload(effect_path, "sound");
			game.unload(weapon_path, "sound");
			for(String s : skill)
				game.unload(s, "img");
			skill.clear();
		}
		skill = null;
	}
}