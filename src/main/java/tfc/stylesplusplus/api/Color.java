package tfc.stylesplusplus.api;

public class Color {
	public int value;
	
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	public Color(int r, int g, int b, int a) {
		value = ((a & 0xFF) << 24) |
				((r & 0xFF) << 16) |
				((g & 0xFF) << 8) |
				((b & 0xFF));
	}
	
	public Color(int rgba, boolean hasalpha) {
		if (hasalpha) value = rgba;
		else value = 0xff000000 | rgba;
	}
	
	public Color(int rgb) {
		this.value = rgb;
	}
	
	public int getRed() {
		return (getRGB() >> 16) & 0xFF;
	}
	
	public int getGreen() {
		return (getRGB() >> 8) & 0xFF;
	}
	
	public int getBlue() {
		return (getRGB()) & 0xFF;
	}
	
	public int getAlpha() {
		return (getRGB() >> 24) & 0xff;
	}
	
	public int getRGB() {
		return value;
	}
}
