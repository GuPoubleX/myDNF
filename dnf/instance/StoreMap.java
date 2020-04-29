package dnf.instance;

import com.badlogic.gdx.Preferences;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetInstanceMap;

public class StoreMap {
	public StoreMap(GuPoubleXGame game) {
		draconian_tower1(game);
		draconian_tower2(game);
		draconian_tower3(game);
		draconian_tower4(game);
	}
	private void draconian_tower1(GuPoubleXGame game) {
		Preferences pref = game.getPref(SetInstanceMap.draconian_tower+"_1");
		if(!pref.getString("BLOCK").equals(""))
			return;
		pref.putInteger("WIDTH", 1340);
		pref.putInteger("HEIGHT", 650);
		pref.putString("BLOCK", "0,225,1340,225,1340,0,320,0,0,170,0,225");
		//back
		pref.putString("BACKGROUND0", "far_dr,0,2,1.5,0,0");//imgname,index,scalex,scaley,x,y
		pref.putString("BACKGROUND1", "dccloud,0,2,1.5,0,-270");
		pref.putString("BIMAGE0", "200tile0,4,1,1,0,55");
		pref.putString("BIMAGE1", "200tile0,4,1,1,224,55");
		pref.putString("BIMAGE2", "200tile0,3,1,1,448,55");
		pref.putString("BIMAGE3", "200tile0,0,1,1,672,55");
		pref.putString("BIMAGE4", "200tile0,0,1,1,896,55");
		pref.putString("BIMAGE5", "200tile0,1,1,1,1120,55");
		pref.putString("BIMAGE6", "bakalsymbol,0,1,1,0,406");
		pref.putString("BIMAGE7", "bakalsymbol,0,1,1,224,406");
		pref.putString("BIMAGE8", "bakalsymbol,0,1,1,448,406");
		pref.putString("BIMAGE9", "bakalsymbol,0,1,1,672,406");
		pref.putString("BIMAGE10", "bakalsymbol,0,1,1,896,406");
		pref.putString("BIMAGE11", "bakalsymbol,0,1,1,1120,406");
		pref.putString("BIMAGE12", "200amonnewex,0,1,1,0,0");
		pref.putString("BIMAGE13", "200amonnewex,0,1,1,224,0");
		pref.putString("BIMAGE14", "200amonnewex,0,1,1,448,0");
		pref.putString("BIMAGE15", "200amonnewex,0,1,1,672,0");
		pref.putString("BIMAGE16", "200amonnewex,0,1,1,896,0");
		pref.putString("BIMAGE17", "200amonnewex,0,1,1,1120,0");
		//front
		pref.putString("FIMAGE0", "02exitside01,3,1,1,-10,100");
		pref.putString("FIMAGE1", "02hurdle01,0,1,1,123,60");
		pref.putString("FIMAGE2", "02hurdle01,0,1,1,186,30");
		pref.putString("FIMAGE3", "02hurdle01,0,1,1,249,0");
		//middle
		pref.putString("DOOR0", "0,110,"//x,y,
								+"25,10,-35,110,55,75,"//door_offset(2),blink_offset(2),blink_offset2(2),
								+"02exitside01,0,02exitside01,1,02light01,0,"//door(2),case(2),blink(2),
								+"2,2,63,147");//toid,nextdor,center(2)
	//	pref.putString("EMENY0", "200,100,"//x,y
	//							+"swordman,0,1");//name,isboss,level
		pref.flush();
	}
	private void draconian_tower2(GuPoubleXGame game) {
		Preferences pref = game.getPref(SetInstanceMap.draconian_tower+"_2");
	//	if(!pref.getString("BLOCK").equals(""))
	//		return;
		pref.putInteger("WIDTH", 895);
		pref.putInteger("HEIGHT", 730);
		pref.putString("BLOCK", "0,410,580,410,895,240,895,0,0,0,0,410");
		//back
		pref.putString("BACKGROUND0", "far_dr,0,2,1.5,0,100");//locname,index,scalex,scaley,x,y
		pref.putString("BACKGROUND1", "dccloud,0,2,1.5,0,-170");
		pref.putString("BIMAGE0", "200tile0,0,1,1,0,240");
		pref.putString("BIMAGE1", "200tile0,1,1,1,224,240");
		pref.putString("BIMAGE2", "200tile0,0,1,1,448,240");
		pref.putString("BIMAGE3", "200tile0,0,1,1,672,240");
		pref.putString("BIMAGE4", "200amonnewex,0,1,1,0,120");
		pref.putString("BIMAGE5", "200amonnewex,0,1,1,0,0");
		pref.putString("BIMAGE6", "200amonnewex,0,1,1,224,120");
		pref.putString("BIMAGE7", "200amonnewex,0,1,1,224,0");
		pref.putString("BIMAGE8", "200amonnewex,0,1,1,448,120");
		pref.putString("BIMAGE9", "200amonnewex,0,1,1,448,0");
		pref.putString("BIMAGE10", "200amonnewex,0,1,1,672,120");
		pref.putString("BIMAGE11", "200amonnewex,0,1,1,672,0");
		pref.putString("BIMAGE12", "200amonnewex,0,1,1,896,120");
		pref.putString("BIMAGE13", "02exitside01,2,1,1,831,265");
		pref.putString("BIMAGE14", "02hurdle01,0,1,1,718,300");
		pref.putString("BIMAGE15", "02hurdle01,0,1,1,645,340");
		pref.putString("BIMAGE16", "02hurdle01,0,1,1,572,380");
		pref.putString("BIMAGE17", "02exitside01,0,1,1,782,240");
		//front
		//middle
		pref.putString("DOOR0", "350,0,"//x,y,
								+"42,0,-33,50,130,50,"//door_offset(2),blink_offset(2),blink_offset2(2),
								+"02exitdown01,0,02exitdown01,1,02light01,0,"//door(2),case(2),blink(2),
								+"3,1,450,7");//toid,nextdor,center(2)
		pref.putString("DOOR1", "782,240,"
								+"25,13,-35,110,55,75,"
								+"-1,-1,02exitside01,1,02light01,0,"
								+"1,1,828,266");
		pref.putString("EMENY0", "200,100,"//x,y
								+"swordman,3,1,0");//name,isboss,level,shadow
		pref.putString("EMENY1", "200,200,"//x,y
								+"swordman,0,1,0");
		pref.putString("EMENY2", "200,210,"//x,y
								+"swordman,1,1,0");
		pref.putString("EMENY3", "200,250,"//x,y
								+"swordman,2,1,0");
		pref.putString("EMENY4", "200,220,"//x,y
								+"swordman,1,1,0");
		pref.flush();
	}
	private void draconian_tower3(GuPoubleXGame game) {
		Preferences pref = game.getPref(SetInstanceMap.draconian_tower+"_3");
		if(!pref.getString("BLOCK").equals(""))
			return;
		pref.putInteger("WIDTH", 1340);
		pref.putInteger("HEIGHT", 680);
		pref.putString("BLOCK", "0,285,1120,285,1340,170,1340,30,430,30,275,95,0,95,0,285");
		//back
		pref.putString("BACKGROUND0", "far_dr,0,2,1.5,0,100");//locname,index,scalex,scaley,x,y
		pref.putString("BACKGROUND1", "dccloud,0,2,1.5,0,-170");
		pref.putString("BIMAGE0", "02exitup01,6,1,1,305,555,true");
		pref.putString("BIMAGE1", "02exitup01,7,1,1,405,555,true");
		pref.putString("BIMAGE2", "02exitup01,5,1,1,240,445,true");
		pref.putString("BIMAGE3", "02exitup01,4,1,1,140,425,true");
		pref.putString("BIMAGE4", "200tile0,4,1,1,0,120");
		pref.putString("BIMAGE5", "200amonnewex,1,1,1,0,0");
		pref.putString("BIMAGE6", "200tile0,1,1,1,224,120");
		pref.putString("BIMAGE7", "200amonnewex,2,1,1,224,0");
		pref.putString("BIMAGE8", "200tile0,3,1,1,448,120");
		pref.putString("BIMAGE9", "200tile0,3,1,1,672,120");
		pref.putString("BIMAGE10", "02exitup01,3,1,1,220,320");
		pref.putString("BIMAGE11", "02exitup01,2,1,1,350,275");
		pref.putString("BIMAGE12", "200amonnewex,6,1,1,448,0");
		pref.putString("BIMAGE13", "200amonnewex,6,1,1,672,0");
		pref.putString("BIMAGE14", "200amonnewex,6,1,1,896,0");
		pref.putString("BIMAGE15", "200amonnewex,6,1,1,1120,0");
		pref.putString("BIMAGE16", "200tile0,4,1,1,896,120");
		pref.putString("BIMAGE17", "200tile0,2,1,1,1120,120");
		pref.putString("BIMAGE18", "02exitup01,0,1,1,310,280");
		pref.putString("BIMAGE19", "02exitside01,2,1,1,1271,210");
		pref.putString("BIMAGE20", "02hurdle01,0,1,1,1158,235");
		pref.putString("BIMAGE21", "02hurdle01,0,1,1,1115,260");
		pref.putString("BIMAGE22", "02exitside01,0,1,1,1222,175");
		//front
		
		//middle
		pref.putString("DOOR0", "310,280,"//x,y,
								+"42,0,-35,62,93,62,"//door_offset(2),blink_offset(2),blink_offset2(2),
								+"02exitup01,0,02exitup01,1,02light01,0,"//door(2),case(2),blink(2),
								+"2,1,382,277");//toid,nextdor,center(2)
		pref.putString("DOOR1", "1222,175,"
								+"25,13,-35,110,55,75,"
								+"-1,-1,02exitside01,1,02light01,0,"
								+"4,1,1260,200");
		pref.flush();
	}
	private void draconian_tower4(GuPoubleXGame game) {
		Preferences pref = game.getPref(SetInstanceMap.draconian_tower+"_4");
		if(!pref.getString("BLOCK").equals(""))
			return;
		pref.putInteger("WIDTH", 1150);
		pref.putInteger("HEIGHT", 650);
		pref.putString("BLOCK", "0,225,1150,225,1150,0,320,0,0,170,0,225");
		//back
		pref.putString("BACKGROUND0", "far_dr,0,2,1.5,0,100");//locname,index,scalex,scaley,x,y
		pref.putString("BACKGROUND1", "dccloud,0,2,1.5,0,-210");
		pref.putString("BIMAGE0", "200tile0,5,1,1,0,55");
		pref.putString("BIMAGE1", "200tile0,5,1,1,224,55");
		pref.putString("BIMAGE2", "200tile0,5,1,1,448,55");
		pref.putString("BIMAGE3", "200tile0,5,1,1,672,55");
		pref.putString("BIMAGE4", "200tile0,5,1,1,896,55");
		pref.putString("BIMAGE5", "200tile0,5,1,1,1120,55");
		pref.putString("BIMAGE6", "200amonnewex,0,1,1,0,-65");
		pref.putString("BIMAGE7", "200amonnewex,0,1,1,224,-65");
		pref.putString("BIMAGE8", "200amonnewex,0,1,1,448,-65");
		pref.putString("BIMAGE9", "200amonnewex,0,1,1,672,-65");
		pref.putString("BIMAGE10", "200amonnewex,0,1,1,896,-65");
		pref.putString("BIMAGE11", "200amonnewex,0,1,1,1120,-65");
		//front
		pref.putString("FIMAGE0", "02exitside01,3,1,1,-10,100");
		pref.putString("FIMAGE1", "02hurdle01,0,1,1,123,60");
		pref.putString("FIMAGE2", "02hurdle01,0,1,1,186,30");
		pref.putString("FIMAGE3", "02hurdle01,0,1,1,249,0");
		//middle
		pref.putString("DOOR0", "0,100,"//x,y,
								+"25,10,-35,110,55,75,"//door_offset(2),blink_offset(2),blink_offset2(2),
								+"02exitside01,0,02exitside01,1,02light01,0,"//door(2),case(2),blink(2),
								+"3,2,63,147");//toid,nextdor,center(2)
		pref.putString("DOOR1", "900,0,"
								+"42,0,-33,50,130,50,"
								+"02exitdown01,0,02exitdown01,1,02light01,0,"
								+"0,2,995,7");
		pref.flush();
	}
}