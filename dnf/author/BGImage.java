package dnf.author;

public class BGImage {
	// i = x
	// start = 0.0f
	// end = max
	// d = width
	public static float set(float i, float start, float end, float d, float width) {
		if(i <= start+(width-d)*0.5f)
			return (width-d)*0.5f;
		if(i >= end-(width-d)*0.5f-d)
			return end-(width-d)*0.5f-d;
		return i;
	}
}