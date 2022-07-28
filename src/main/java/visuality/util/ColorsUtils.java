package visuality.util;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class ColorsUtils {
	public static final ArrayList<Integer> RAINBOW = new ArrayList<>();

	public static final int RED = add(16735855);
	public static final int ORANGE = add(16751185);
	public static final int YELLOW = add(16318324);
	public static final int GREEN = add(7077755);
	public static final int BLUE = add(8245247);
	public static final int PURPLE = add(16750831);

	private static int add(int color) {
		RAINBOW.add(color);
		return color;
	}

	public static int getRandomColorOfRainbow(Random random) {
		return RAINBOW.get(random.nextInt(RAINBOW.size()));
	}
}
