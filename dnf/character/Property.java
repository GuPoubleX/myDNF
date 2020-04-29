package dnf.character;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Disposable;
import dnf.gupoublex.set.SetCharProperty;

public class Property implements Disposable {
	private Preferences pref = null;
	private Location loc = null;
	private int hmp_rate = 10;
	private String profession = null;
	private boolean initlv = false;
	private long exp = 0;
	private int pl = 0;
	private int lv = 1;
	private int sp = 0;
	private int sptotal = 0;
	private float weak = 1;
	private boolean firsthp = false;
	private float hp = 0;
	private boolean firstmp = false;
	private float mp = 0;
	private float str = 0;
	private float inT = 0;
	private float vit = 0;
	private float men = 0;
	private float atkphy = 0;
	private float atkmig = 0;
	private float defphy = 0;
	private float defmig = 0;
	private float crt = 0;
	private float crd = 0;
	private float sphy = 0;
	private float smig = 0;
	private float smov = 0;
	private float gold = 0;
	//equip
	private float eqhpmax = 0;
	private float eqmpmax = 0;
	private float eqstr = 0;
	private float eqinT = 0;
	private float eqvit = 0;
	private float eqmen = 0;
	private float eqatkphy = 0;
	private float eqatkmig = 0;
	private float eqdefphy = 0;
	private float eqdefmig = 0;
	private float eqcrt = 0;
	private float eqcrd = 0;
	private float eqsphy = 0;
	private float eqsmig = 0;
	private float eqsmov = 0;
	private float eqsphy_weapon = 1;
	private float eqsmig_weapon = 1;
	//ex
	private float exhpmax = 0;
	private float exmpmax = 0;
	private float exstr = 0;
	private float exinT = 0;
	private float exvit = 0;
	private float exmen = 0;
	private float exatkphy = 0;
	private float exatkmig = 0;
	private float exdefphy = 0;
	private float exdefmig = 0;
	private float excrt = 0;
	private float excrd = 0;
	private float exsphy = 0;
	private float exsmig = 0;
	private float exsmov = 0;
	//type
	private int type = SetCharProperty.normal;
	@Override
	public void dispose() {
	}
	public void setEmenyType(int type) {
		this.type = type;
	}
	public int getEmenyType() {
		return type;
	}
	public Property(Preferences pref, Location loc) {
		super();
		this.pref = pref;
		this.loc = loc;
		profession = pref.getString("PROFESSION");
		weak = pref.getFloat("WEAK");
		exp = pref.getLong("EXP");
		pl = pref.getInteger("PL");
		sp = pref.getInteger("SP");
		sptotal = pref.getInteger("SPTOTAL");
		str = pref.getFloat("STR");
		inT = pref.getFloat("INT");
		vit = pref.getFloat("VIT");
		men = pref.getFloat("MEN");
		atkphy = pref.getFloat("ATKPHY");
		atkmig = pref.getFloat("ATKMIG");
		defphy = pref.getFloat("DEFPHY");
		defmig = pref.getFloat("DEFMIG");
		crt = pref.getFloat("CRT");
		crd = pref.getFloat("CRD");
		sphy = pref.getFloat("SPHY");
		smig = pref.getFloat("SMIG");
		smov = pref.getFloat("SMOV");
		gold = pref.getFloat("GOLD");
	}
	private float time = 0;
	private long start = 0;
	public void act(float delta) {
		if(loc.inTown())
			if(weak < 1) {
				if(start != 0)
					time += (System.currentTimeMillis()-start)*0.001f;
				start = System.currentTimeMillis();
				if(time >= SetCharProperty.weakresume) {
					time -= SetCharProperty.weakresume;
					weak += 0.1f;
					pref.putFloat("WEAK", weak);
					pref.flush();
				}
			}
	}
	public void resetTime() {
		time = 0;
		start = 0;
	}
	public float getProperty(String str) {
		str = str.toLowerCase();
		if(str.equals("hp")) {
			if(getHp()*weak <= 1 && loc.inTown())
				return 1;
			else
				return getHp()*weak;
		}else if(str.equals("mp")) {
			if(getMp()*weak <= 1 && loc.inTown())
				return 1;
			else
				return getMp()*weak;
		}else if(str.equals("hpmax"))
			return getHpmax();
		else if(str.equals("mpmax"))
			return getMpmax();
		else if(str.equals("str"))
			return getStr();
		else if(str.equals("int"))
			return getInt();
		else if(str.equals("vit"))
			return getVit();
		else if(str.equals("men"))
			return getMen();
		else if(str.equals("atkphy"))
			return getAtkphy();
		else if(str.equals("atkmig"))
			return getAtkmig();
		else if(str.equals("defphy"))
			return getDefphy();
		else if(str.equals("defmig"))
			return getDefmig();
		else if(str.equals("crt"))
			return getCrt();
		else if(str.equals("crd"))
			return getCrd();
		else if(str.equals("sphy"))
			return getSphy();
		else if(str.equals("smig"))
			return getSmig();
		else if(str.equals("sphy_weapon"))
			return getSphyWeapon();
		else if(str.equals("smig_weapon"))
			return getSmigWeapon();
		else if(str.equals("smov"))
			return getSmov();
		else if(str.equals("exp"))
			return getEXP();
		else if(str.equals("lvexp"))
			return getLvEXP();
		else if(str.equals("sp"))
			return getSP();
		else if(str.equals("sptotal"))
			return getSPtotal();
		else if(str.equals("lv"))
			return getLv();
		else if(str.equals("lvnext"))
			return Levelinfo((int) getLv());
		else if(str.equals("lvnow"))
			return getLvEXP();
		else if(str.equals("pl"))
			return getPL();
		else if(str.equals("gold"))
			return getGold();
		else
			return 0;
	}
	public float getBuffProperty(String str) {
		str = str.toLowerCase();
		if(str.equals("hp"))
			return 0;
		else if(str.equals("mp"))
			return 0;
		else if(str.equals("hpmax"))
			return exhpmax;
		else if(str.equals("mpmax"))
			return exmpmax;
		else if(str.equals("str"))
			return exstr;
		else if(str.equals("int"))
			return exinT;
		else if(str.equals("vit"))
			return exvit;
		else if(str.equals("men"))
			return exmen;
		else if(str.equals("atkphy"))
			return exatkphy;
		else if(str.equals("atkmig"))
			return exatkmig;
		else if(str.equals("defphy"))
			return exdefphy;
		else if(str.equals("defmig"))
			return exdefmig;
		else if(str.equals("crt"))
			return excrt;
		else if(str.equals("crd"))
			return excrd;
		else if(str.equals("sphy"))
			return exsphy;
		else if(str.equals("smig"))
			return exsmig;
		else if(str.equals("smov"))
			return exsmov;
		else
			return 0;
	}
	public boolean changeProperty(String string, float val, boolean equip, boolean buff) {
		boolean suc = false;
		string = string.toLowerCase();
		if(string.equals("hp"))
			changeHMP(true, val);
		else if(string.equals("mp"))
			changeHMP(false, val);
		else if(string.equals("hpmax"))
			changeHMPmax(true, val, equip, buff);
		else if(string.equals("mpmax"))
			changeHMPmax(false, val, equip, buff);
		else if(string.equals("str"))
			changeSTR(val, equip, buff);
		else if(string.equals("int"))
			changeINT(val, equip, buff);
		else if(string.equals("vit"))
			changeVIT(val, equip, buff);
		else if(string.equals("men"))
			changeMEN(val, equip, buff);
		else if(string.equals("atkphy"))
			changeATKphy(val, equip, buff);
		else if(string.equals("atkmig"))
			changeATKmig(val, equip, buff);
		else if(string.equals("defphy"))
			changeDEFphy(val, equip, buff);
		else if(string.equals("defmig"))
			changeDEFmig(val, equip, buff);
		else if(string.equals("crt"))
			changeCRT(val, equip, buff);
		else if(string.equals("crd"))
			changeCRD(val, equip, buff);
		else if(string.equals("sphy"))
			changeSPHY(val, equip, buff);
		else if(string.equals("smig"))
			changeSMIG(val, equip, buff);
		else if(string.equals("smov"))
			changeSMOV(val, equip, buff);
		else if(string.equals("exp"))
			addEXP((int) val);
		else if(string.equals("sp")) {
			addSP((int) val);
			addSPtotal((int) val);
		}else if(string.equals("lv"))
			setLv((int) val);
		else if(string.equals("pl"))
			minusPL();
		else if(string.equals("resetpl"))
			resetPL();
		else if(string.equals("gold"))
			return changeGold((int) val);
		return suc;
	}
	private void changeHMP(boolean ishp, float val) {
		if(ishp)
			hp += val;
		else
			mp += val;
	}
	private void changeHMPmax(boolean ishp, float val, boolean eq, boolean buf) {
		if(ishp) {
			if(eq)
				eqhpmax += val;
			else if(buf)
				exhpmax += val;
		}else {
			if(eq)
				eqmpmax += val;
			else if(buf)
				exmpmax += val;
		}
	}
	private void changeSTR(float val, boolean eq, boolean buf) {
		if(eq)
			eqstr += val;
		else if(buf)
			exstr += val;
		else {
			str += val;
			pref.putFloat("STR", str);
			pref.flush();
		}
	}
	private void changeINT(float val, boolean eq, boolean buf) {
		if(eq)
			eqinT += val;
		else if(buf)
			exinT += val;
		else {
			inT += val;
			pref.putFloat("INT", inT);
			pref.flush();
		}
	}
	private void changeVIT(float val, boolean eq, boolean buf) {
		if(eq)
			eqvit += val;
		else if(buf)
			exvit += val;
		else {
			vit += val;
			pref.putFloat("VIT", vit);
			pref.flush();
		}
	}
	private void changeMEN(float val, boolean eq, boolean buf) {
		if(eq)
			eqmen += val;
		else if(buf)
			exmen += val;
		else {
			men += val;
			pref.putFloat("MEN", men);
			pref.flush();
		}
	}
	private void changeATKphy(float val, boolean eq, boolean buf) {
		if(eq)
			eqatkphy += val;
		else if(buf)
			exatkphy += val;
		else {
			atkphy += val;
			pref.putFloat("ATKPHY", atkphy);
			pref.flush();
		}
	}
	private void changeATKmig(float val, boolean eq, boolean buf) {
		if(eq)
			eqatkmig += val;
		else if(buf)
			exatkmig += val;
		else {
			atkmig += val;
			pref.putFloat("ATKMIG", atkmig);
			pref.flush();
		}
	}
	private void changeDEFphy(float val, boolean eq, boolean buf) {
		if(eq)
			eqdefphy += val;
		else if(buf)
			exdefphy += val;
		else {
			defphy += val;
			pref.putFloat("DEFPHY", defphy);
			pref.flush();
		}
	}
	private void changeDEFmig(float val, boolean eq, boolean buf) {
		if(eq)
			eqdefmig += val;
		else if(buf)
			exdefmig += val;
		else {
			defmig += val;
			pref.putFloat("DEFMIG", defmig);
			pref.flush();
		}
	}
	private void changeCRT(float val, boolean eq, boolean buf) {
		if(eq)
			eqcrt += val;
		else if(buf)
			excrt += val;
		else
			crt += val;
	}
	private void changeCRD(float val, boolean eq, boolean buf) {
		if(eq)
			eqcrt += val;
		else if(buf)
			excrd += val;
		else
			crd += val;
	}
	private void changeSPHY(float val, boolean eq, boolean buf) {
		if(eq)
			eqsphy += val;
		else if(buf)
			exsphy += val;
		else
			sphy += val;
	}
	private void changeSMIG(float val, boolean eq, boolean buf) {
		if(eq)
			eqsmig += val;
		else if(buf)
			exsmig += val;
		else
			smig += val;
	}
	private void changeSMOV(float val, boolean eq, boolean buf) {
		if(eq)
			eqsmov += val;
		else if(buf)
			exsmov += val;
		else
			smov += val;
	}
	private boolean changeGold(int val) {
		if(gold+val >= 0) {
			gold += val;
			pref.putFloat("GOLD", gold);
			pref.flush();
			return true;
		}else
			return false;
	}
	public void clear() {
		exhpmax = 0;
		exmpmax = 0;
		exstr = 0;
		exinT = 0;
		exvit = 0;
		exmen = 0;
		exatkphy = 0;
		exatkmig = 0;
		exdefphy = 0;
		exdefmig = 0;
		excrt = 0;
		excrd = 0;
		exsphy = 0;
		exsmig = 0;
		exsmov = 0;
	}
	public float getWeak() {
		return weak;
	}
	public void currentWeak() {
		weak = 0.1f;
		pref.putFloat("WEAK", this.weak);
		pref.flush();
	}
	public void setWeak(float weak) {
		if(weak <= 0.1f)
			this.weak = 0.1f;
		else
			this.weak = weak;
		pref.putFloat("WEAK", this.weak);
		pref.flush();
	}
	public String getProfession() {
		return profession;
	}
	public float getHp() {
		if(loc.inTown())
			hp = getHpmax();
		else if(!firsthp) {
			firsthp  = true;
			hp = getHpmax();
		}else if(hp < 0)
			hp = 0;
		return hp;
	}
	public float getHpmax() {
		if(getVit()*hmp_rate+exhpmax+eqhpmax < 1)
			return 1;
		return getVit()*hmp_rate+exhpmax+eqhpmax;
	}
	public float getMp() {
		if(loc.inTown())
			mp = getMpmax();
		else if(!firstmp) {
			firstmp  = true;
			mp = getMpmax();
		}else if(hp < 0)
			hp = 0;
		return mp;
	}
	public float getMpmax() {
		if(getMen()*hmp_rate+exmpmax+eqmpmax < 1)
			return 1;
		return getMen()*hmp_rate+exmpmax+eqmpmax;
	}
	public float getStr() {
		if(weak*(str+eqstr)+exstr < 0)
			return 0;
		return weak*(str+eqstr)+exstr;
	}
	public float getInt() {
		if(weak*(inT+eqinT)+exinT < 0)
			return 0;
		return weak*(inT+eqinT)+exinT;
	}
	public float getVit() {
		if(weak*(vit+eqvit)+exvit < 1)
			return 1;
		return weak*(vit+eqvit)+exvit;
	}
	public float getMen() {
		if(weak*(men+eqmen)+exmen < 1)
			return 1;
		return weak*(men+eqmen)+exmen;
	}
	public float getAtkphy() {
		if(weak*(atkphy+eqatkphy)+exatkphy < 0)
			return 0;
		return weak*(atkphy+eqatkphy)+exatkphy;
	}
	public float getAtkmig() {
		if(weak*(atkmig+eqatkmig)+exatkmig < 0)
			return 0;
		return weak*(atkmig+eqatkmig)+exatkmig;
	}
	public float getDefphy() {
		if(weak*(defphy+eqdefphy)+exdefphy < 0)
			return 0;
		return weak*(defphy+eqdefphy)+exdefphy;
	}
	public float getDefmig() {
		if(weak*(defmig+eqdefmig)+exdefmig < 0)
			return 0;
		return weak*(defmig+eqdefmig)+exdefmig;
	}
	public float getCrt() {
		if(crt+eqcrt+excrt < 0)
			return 0;
		return crt+eqcrt+excrt;
	}
	public float getCrd() {
		if(crd+eqcrd+excrd < 1)
			return 1;
		return crd+eqcrd+excrd;
	}
	public float getSphy() {
		return sphy+eqsphy+exsphy;
	}
	public float getSmig() {
		return smig+eqsmig+exsmig;
	}
	public float getSphyWeapon() {
		return 1/(eqsphy_weapon*(1+eqsphy)*(1+sphy+exsphy));
	}
	public float getSmigWeapon() {
		return 1/(eqsmig_weapon*(1+eqsmig)*(1+smig+exsmig));
	}
	public float getSmov() {
		return smov+eqsmov+exsmov;
	}
	private void minusPL() {
		pl--;
		if(pl <= 0)
			pl = 0;
		pref.putFloat("PL", pl);
		pref.flush();
	}
	public int getPL() {
		return pl;
	}
	private void resetPL() {
		pl = getLv()>=40?SetCharProperty.PL_max:SetCharProperty.PL_min;
		pref.putFloat("PL", pl);
		pref.flush();
	}
	private float getEXP() {
		return exp;
	}
	private float getLvEXP() {
		return expinfo(exp, (int) getLv());
	}
	private void addEXP(int val) {
		exp += val;
		pref.putLong("EXP", this.exp);
		pref.flush();
	}
	private float getSP() {
		return sp;
	}
	private void addSP(int val) {
		if(sp+val >= 0) {
			sp += val;
			pref.putInteger("SP", this.sp);
			pref.flush();
		}
	}
	private float getSPtotal() {
		return sptotal;
	}
	private void addSPtotal(int val) {
		if(sptotal+val >= 0) {
			sptotal += val;
			pref.putInteger("SPTOTAL", this.sptotal);
			pref.flush();
		}
	}
	private float getGold() {
		return gold;
	}
	private float getLv() {
		if(!initlv)
			lv = lvinfo(exp);
		return lv;
	}
	private void setLv(int lv) {
		initlv = true;
		this.lv = lv;
	}
	private long expinfo(long exp, int lv) {
		lv -= 1;
		while(lv>=1) {
			exp -= Levelinfo(lv);
			lv--;
		}
		return exp;
	}
	private int lvinfo(long exp) {
		int level = 1;
		long e = Levelinfo(level);
		while(exp-e>=0) {
			level++;
			exp -= e;
			e = Levelinfo(level);
			if(level >= SetCharProperty.maxLevel)
				break;
		}
		return level;
	}
	private long Levelinfo(int n) {
		long exp = 0;
		switch(n) {
			case 1:exp = SetCharProperty.level_1;break;
			case 2:exp = SetCharProperty.level_2;break;
			case 3:exp = SetCharProperty.level_3;break;
			case 4:exp = SetCharProperty.level_4;break;
			case 5:exp = SetCharProperty.level_5;break;
			case 6:exp = SetCharProperty.level_6;break;
			case 7:exp = SetCharProperty.level_7;break;
			case 8:exp = SetCharProperty.level_8;break;
			case 9:exp = SetCharProperty.level_9;break;
			case 10:exp = SetCharProperty.level_10;break;
			case 11:exp = SetCharProperty.level_11;break;
			case 12:exp = SetCharProperty.level_12;break;
			case 13:exp = SetCharProperty.level_13;break;
			case 14:exp = SetCharProperty.level_14;break;
			case 15:exp = SetCharProperty.level_15;break;
			case 16:exp = SetCharProperty.level_16;break;
			case 17:exp = SetCharProperty.level_17;break;
			case 18:exp = SetCharProperty.level_18;break;
			case 19:exp = SetCharProperty.level_19;break;
			case 20:exp = SetCharProperty.level_20;break;
			case 21:exp = SetCharProperty.level_21;break;
			case 22:exp = SetCharProperty.level_22;break;
			case 23:exp = SetCharProperty.level_23;break;
			case 24:exp = SetCharProperty.level_24;break;
			case 25:exp = SetCharProperty.level_25;break;
			case 26:exp = SetCharProperty.level_26;break;
			case 27:exp = SetCharProperty.level_27;break;
			case 28:exp = SetCharProperty.level_28;break;
			case 29:exp = SetCharProperty.level_29;break;
			case 30:exp = SetCharProperty.level_30;break;
			case 31:exp = SetCharProperty.level_31;break;
			case 32:exp = SetCharProperty.level_32;break;
			case 33:exp = SetCharProperty.level_33;break;
			case 34:exp = SetCharProperty.level_34;break;
			case 35:exp = SetCharProperty.level_35;break;
			case 36:exp = SetCharProperty.level_36;break;
			case 37:exp = SetCharProperty.level_37;break;
			case 38:exp = SetCharProperty.level_38;break;
			case 39:exp = SetCharProperty.level_39;break;
			case 40:exp = SetCharProperty.level_40;break;
			case 41:exp = SetCharProperty.level_41;break;
			case 42:exp = SetCharProperty.level_42;break;
			case 43:exp = SetCharProperty.level_43;break;
			case 44:exp = SetCharProperty.level_44;break;
			case 45:exp = SetCharProperty.level_45;break;
			case 46:exp = SetCharProperty.level_46;break;
			case 47:exp = SetCharProperty.level_47;break;
			case 48:exp = SetCharProperty.level_48;break;
			case 49:exp = SetCharProperty.level_49;break;
			case 50:exp = SetCharProperty.level_50;break;
			case 51:exp = SetCharProperty.level_51;break;
			case 52:exp = SetCharProperty.level_52;break;
			case 53:exp = SetCharProperty.level_53;break;
			case 54:exp = SetCharProperty.level_54;break;
			case 55:exp = SetCharProperty.level_55;break;
			case 56:exp = SetCharProperty.level_56;break;
			case 57:exp = SetCharProperty.level_57;break;
			case 58:exp = SetCharProperty.level_58;break;
			case 59:exp = SetCharProperty.level_59;break;
			case 60:exp = SetCharProperty.level_60;break;
		}
		return exp;
	}
}