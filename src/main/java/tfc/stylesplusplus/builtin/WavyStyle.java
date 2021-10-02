package tfc.stylesplusplus.builtin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import tfc.stylesplusplus.api.Color;
import tfc.stylesplusplus.api.ExtraStyle;
import tfc.stylesplusplus.api.util.GeneralUtils;

public class WavyStyle extends ExtraStyle {
	public WavyStyle() {
		super(new Identifier("stylesplusplus:wavy"));
		tickStart = GeneralUtils.getTime();
		sclX = 1;
		sclY = 1;
		speedX = 1;
		speedY = 1;
		offX = 0;
		offY = 0;
		indexMultiplierX = 12;
		indexMultiplierY = 12;
	}
	
	private int tickStart;
	
	public WavyStyle(double sclX, double sclY, double speedX, double speedY, double offX, double offY, double indexMultiplierX, double indexMultiplierY) {
		super(new Identifier("stylesplusplus:wavy"));
		tickStart = GeneralUtils.getTime();
		this.sclX = sclX;
		this.sclY = sclY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.offX = offX;
		this.offY = offY;
		this.indexMultiplierX = indexMultiplierX;
		this.indexMultiplierY = indexMultiplierY;
	}
	
	private double sclX;
	private double sclY;
	private double speedX;
	private double speedY;
	private double offX;
	private double offY;
	private double indexMultiplierX;
	private double indexMultiplierY;
	private boolean globalTime = false;
	
	@Override
	public JsonObject serialize() {
		JsonObject object = super.serialize();
		
		object.add("x", create(sclX, speedX, offX, indexMultiplierX));
		object.add("y", create(sclY, speedY, offY, indexMultiplierY));
		object.addProperty("globalTime", globalTime);
		
		return object;
	}
	
	@Override
	public void deserialize(JsonObject object) {
		if (object.has("x")) {
			JsonElement xElement = object.get("x");
			if (xElement instanceof JsonObject) {
				JsonObject x = (JsonObject) xElement;
				sclX = get(x, "scale", 1);
				speedX = get(x, "speed", 1);
				offX = get(x, "startOffset", 0);
				indexMultiplierX = get(x, "indexMultiplier", 0);
			}
		}
		if (object.has("y")) {
			JsonElement yElement = object.get("y");
			if (yElement instanceof JsonObject) {
				JsonObject y = (JsonObject) yElement;
				sclY = get(y, "scale", 1);
				speedY = get(y, "speed", 1);
				offY = get(y, "startOffset", 0);
				indexMultiplierY = get(y, "indexMultiplier", 0);
			}
		}
		if (object.has("globalTime")) {
			JsonElement globalTimeElement = object.get("globalTime");
			if (globalTimeElement instanceof JsonPrimitive) globalTime = globalTimeElement.getAsBoolean();
		}
		super.deserialize(object);
	}
	
	public double get(JsonObject object, String name, double defaultValue) {
		if (object.has(name)) {
			JsonElement element = object.get(name);
			if (element instanceof JsonPrimitive) return element.getAsDouble();
		}
		return defaultValue;
	}
	
	public JsonObject create(double scl, double spd, double off, double indxMul) {
		JsonObject object = new JsonObject();
		object.addProperty("scale", scl);
		object.addProperty("speed", spd);
		object.addProperty("startOffset", off);
		object.addProperty("indexMultiplier", indxMul);
		return object;
	}
	
	@Override
	public void apply(int index, int count, char c, Matrix4f matrix, String str, Style fullStyle) {
		int tick = GeneralUtils.getTime() - (globalTime ? 0 : tickStart);
		matrix.multiply(Matrix4f.translate(
				(float) (Math.cos(Math.toRadians((tick * speedX) + (index * indexMultiplierX) + offX)) * sclX),
				(float) (Math.cos(Math.toRadians((tick * speedY) + (index * indexMultiplierY) + offY)) * sclY),
				0
		));
	}
	
	public Color modifyColor(Color color, int index, int count, char c, String str, Style fullStyle, float brightnessMultiplier) {
		return color;
/*
		double tick = GeneralUtils.getTime() - tickStart;
		tick = tick / 10d;
		double v = Math.cos(tick) + 1;
		v /= 2;
		if (((int) (v * count)) == index) return new Color((int)(255 * brightnessMultiplier), 0, 0, (int)((tick*10) % 255));
		return new Color((int)(255 * brightnessMultiplier), (int)(255 * brightnessMultiplier), (int)(255 * brightnessMultiplier), 255);
*/
	}
}
