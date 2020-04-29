package dnf.gupoublex.set;

public class SetCharProperty {
	public static int normal = 0;
	public static int hero = 1;
	public static int special = 2;
	public static int boss = 3;
	public static int weakresume = 60;
	public static int emeny = -1;
	public static int neutral = 0;
	public static int companion = 1;
	public static String tree = "tree/CharacterBTree.tree";
	public static int sound = 0;
	public static int effect = 1;
	public static int weapon = 2;
	public static String swordman_names[] = {
			"swordman"
	};
	public static String fighter_names[] = {
			"fighter"
	};
	public static String gunner_names[] = {
			"gunner"
	};
	public static String mage_names[] = {
			"mage"
	};
	public static String priest_names[] = {
			"priest"
	};
	public static String path_swordman = "character/swordman/";
	
	public static float AttackRateMax = 0.75f;
	public static float SkillRateMax = 0.75f;
	public static float SwordmanSpeed = 0.87f;
	public static float FighterSpeed = 0.93f;
	public static float GunnerSpeed = 0.84f;
	public static float MageSpeed = 0.82f;
	public static float PriestSpeed = 0.8f;
	public static float ThiefSpeed = 1.05f;
	public static int maxLevel = 60;
	public static int PL_min = 156;
	public static int PL_max = 188;
	public static float town_x = 8;
	public static float town_y = town_x/SetBase.fix;
	public static float instance_x = 2;
	public static float instance_y = instance_x/SetBase.fix;

	public static int swordmanstrong = 15;
	public static int swordmanintelligence = 10;
	public static int swordmanvitamin = 30;
	public static int swordmanspirit = 20;
	public static int swordmanlevelstrong = 2;
	public static int swordmanlevelintelligence = 1;
	public static int swordmanlevelvitamin = 2;
	public static int swordmanlevelspirit = 1;
	public static int strongberserker = 3;
	public static int intelligenceberserker = 0;
	public static int vitaminberserker = 1;
	public static int spiritberserker = 0;
	public static int strongweaponmaster = 2;
	public static int intelligenceweaponmaster = 0;
	public static int vitaminweaponmaster = 2;
	public static int spiritweaponmaster = 0;
	public static int strongasura = 0;
	public static int vitaminasura = 1;
	public static int intelligenceasura = 2;
	public static int spiritasura = 1;
	public static int strongsoulbringer = 0;
	public static int vitaminsoulbringer = 0;
	public static int intelligencesoulbringer = 3;
	public static int spiritsoulbringer = 2;
	
	public static int fighterstrong = 15;
	public static int fighterintelligence = 10;
	public static int fightervitamin = 30;
	public static int fighterspirit = 20;
	public static int gunnerstrong = 15;
	public static int gunnerintelligence = 10;
	public static int gunnervitamin = 30;
	public static int gunnerspirit = 20;
	public static int magestrong = 15;
	public static int mageintelligence = 10;
	public static int magevitamin = 30;
	public static int magespirit = 20;
	public static int prieststrong = 15;
	public static int priestintelligence = 10;
	public static int priestvitamin = 30;
	public static int priestspirit = 20;
	public static int thiefstrong = 15;
	public static int thiefintelligence = 10;
	public static int thiefvitamin = 30;
	public static int thiefspirit = 20;
	
	//exp
	/*info
   level   vs_lv-1_add_exp  vs_lv-1	          need_exp
    1			1000		0%					1000
	2			1035		104%				2035
	3			1280		124%				3315
	4			1945		152%				5260
	5			3240		167%				8500
	6			5375		166%				13875
	7			8560		159%				22435
	8			13005		152%				35440
	9			18920		145%				54360
	10			26515		140%				80875
	11			36000		136%				116875
	12			47585		132%				164460
	13			61480		129%				225940
	14			88880		145%				314820
	15			110760		125%				425580
	16			136000		123%				561580
	17			164840		121%				726420
	18			197520		120%				923940
	19			234280		119%				1158220
	20			275360		118%				1433580
	21			321000		117%				1754580
	22			371440		116%				2126020
	23			426920		115%				2552940
	24			487680		114%				3040620
	25			553960		114%				3594580
	26			626000		113%				4220580
	27			704040		112%				4924620
	28			788320		112%				5712940
	29			879080		112%				6592020
	30			976560		111%				7568580
	31			1081000		111%				8649580
	32			1192640		110%				9842220
	33			1311720		110%				11153940
	34			1438480		110%				12592420
	35			1573160		109%				14165580
	36			1716000		109%				15881580
	37			1867240		109%				17748820
	38			2027120		109%				19775940
	39			2195880		108%				21971820
	40			2373760		108%				24345580
	41			2561000		108%				26906580
	42			2757840		108%				29664420
	43			2964520		107%				32628940
	44			3181280		107%				35810220
	45			3408360		107%				39218580
	46			3646000		107%				42864580
	47			3894440		107%				46759020
	48			4153920		107%				50912940
	49			5530600		133%				56443540
	50			5883450		106%				62326990
	51			6251000		106%				68577990
	52			6633550		106%				75211540
	53			7031400		106%				82242940
	54			8933620		127%				91176560
	55			9448840		106%				100625400
	56			9983500		106%				110608900
	57			10537960	106%				121146860
	58			11112580	105%				132259440
	59			11707720	105%				143967160
	60			12323740	105%				156290900
	61			12961000	105%				169251900
	62			13619860	105%				182871760
	63			14300680	105%				197172440
	64			15003820	105%				212176260
	65			15729640	105%				227905900
	66			16478500	105%				244384400
	67			17250760	105%				261635160
	68			18046780	105%				279681940
	69			23583400	131%				303265340
	70			24639175	104%				327904515
	*/
	public static long level_1 = 450;
	public static long level_2 = level_1+1014;
	public static long level_3 = level_2+2046;
	public static long level_4 = level_3+3486;
	public static long level_5 = level_4+5366;
	public static long level_6 = level_5+7718;
	public static long level_7 = level_6+10574;
	public static long level_8 = level_7+13966;
	public static long level_9 = level_8+17926;
	public static long level_10 = level_9+22486;
	public static long level_11 = level_10+30038;
	public static long level_12 = level_11+36739;
	public static long level_13 = level_12+44286;
	public static long level_14 = level_13+52721;
	public static long level_15 = level_14+62089;
	public static long level_16 = level_15+83080;
	public static long level_17 = level_16+100194;
	public static long level_18 = level_17+119883;
	public static long level_19 = level_18+139195;
	public static long level_20 = level_19+163957;
	public static long level_21 = level_20+205824;
	public static long level_22 = level_21+240789;
	public static long level_23 = level_22+280150;
	public static long level_24 = level_23+318319;
	public static long level_25 = level_24+366185;
	public static long level_26 = level_25+412062;
	public static long level_27 = level_26+469513;
	public static long level_28 = level_27+532999;
	public static long level_29 = level_28+593079;
	public static long level_30 = level_29+668036;
	public static long level_31 = level_30+879085;
	public static long level_32 = level_31+990868;
	public static long level_33 = level_32+1113237;
	public static long level_34 = level_33+1230530;
	public static long level_35 = level_34+1372901;
	public static long level_36 = level_35+1508090;
	public static long level_37 = level_36+1672417;
	public static long level_38 = level_37+1850285;
	public static long level_39 = level_38+2017411;
	public static long level_40 = level_39+2220596;
	public static long level_41 = level_40+3953368;
	public static long level_42 = level_41+4388407;
	public static long level_43 = level_42+4857901;
	public static long level_44 = level_43+5318965;
	public static long level_45 = level_44+5852818;
	public static long level_46 = level_45+6371193;
	public static long level_47 = level_46+6974574;
	public static long level_48 = level_47+7620211;
	public static long level_49 = level_48+8238729;
	public static long level_50 = level_49+10962686;
	public static long level_51 = level_50+14809915;
	public static long level_52 = level_51+16175505;
	public static long level_53 = level_52+17631362;
	public static long level_54 = level_53+19057749;
	public static long level_55 = level_54+20680257;
	public static long level_56 = level_55+22253979;
	public static long level_57 = level_56+24054312;
	public static long level_58 = level_57+25961775;
	public static long level_59 = level_58+27789097;
	public static long level_60 = level_59+1;
}