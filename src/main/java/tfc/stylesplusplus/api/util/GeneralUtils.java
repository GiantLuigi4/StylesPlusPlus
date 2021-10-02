package tfc.stylesplusplus.api.util;

public class GeneralUtils {
	private static int time;
	
	private static GeneralUtils INSTANCE;
	
	public GeneralUtils() {
		if (INSTANCE != null) throw new RuntimeException("NO, BAD");
	}
	
	public static int getTime() {
		return time;
	}
	
	public void setTime(int newTime) {
		time = newTime;
	}
}
