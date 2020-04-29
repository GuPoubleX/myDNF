package dnf.author;

public class StringHex {
	private int getDigit(char c) {
		if(c == '0')
			return 0;
		if(c == '1')
			return 1;
		if(c == '2')
			return 2;
		if(c == '3')
			return 3;
		if(c == '4')
			return 4;
		if(c == '5')
			return 5;
		if(c == '6')
			return 6;
		if(c == '7')
			return 7;
		if(c == '8')
			return 8;
		if(c == '9')
			return 9;
		if(c == 'a')
			return 10;
		if(c == 'b')
			return 11;
		if(c == 'c')
			return 12;
		if(c == 'd')
			return 13;
		if(c == 'e')
			return 14;
		if(c == 'f')
			return 15;
		return 0;
	}
	private char getDigit(int i) {
		if(i == 0)
			return '0';
		if(i == 1)
			return '1';
		if(i == 2)
			return '2';
		if(i == 3)
			return '3';
		if(i == 4)
			return '4';
		if(i == 5)
			return '5';
		if(i == 6)
			return '6';
		if(i == 7)
			return '7';
		if(i == '8')
			return '8';
		if(i == 9)
			return '9';
		if(i == 10)
			return 'a';
		if(i == 11)
			return 'b';
		if(i == 12)
			return 'c';
		if(i == 13)
			return 'd';
		if(i == 14)
			return 'e';
		if(i == 15)
			return 'f';
		return '0';
	}
	public int StrtoHex(String str) {
		char[] bs = str.toCharArray();
		int h = 0;
		for(int i = 0; i < bs.length; i++)
			h += getDigit(bs[i]) << 4*(bs.length-i-1);
		return h;
	}
	public String HextoStr(int h, boolean has5) {
		int s5 = h >> 4*5 & 0x0f;
		int s4 = h >> 4*4 & 0x0f;
		int s3 = h >> 4*3 & 0x0f;
		int s2 = h >> 4*2 & 0x0f;
		int s1 = h >> 4*1 & 0x0f;
		int s0 = h >> 4*0 & 0x0f;
		return ""+(has5?getDigit(s5):"")+getDigit(s4)+getDigit(s3)+getDigit(s2)+getDigit(s1)+getDigit(s0);
	}
}