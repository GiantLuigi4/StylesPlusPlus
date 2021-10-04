package tfc.stylesplusplus.api;

import com.google.gson.JsonObject;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public abstract class ExtraStyle {
	public final Identifier name;
	
	public ExtraStyle(Identifier name) {
		this.name = name;
	}
	
	public abstract void apply(int index, int count, char c, Matrix4f matrix, String str, Style fullStyle);
	
	public int modifyWidth(int currentWidth, int index, int count, char c, String str, Style fullStyle) {
		return currentWidth;
	}
	
	public JsonObject serialize() {
		JsonObject object = new JsonObject();
		object.addProperty("registryName", name.toString());
		return object;
	}
	
	public void deserialize(JsonObject object) {
	}
	
	public Color modifyColor(Color color, int index, int count, char c, String str, Style fullStyle, float brightnessMultiplier) {
		return color;
	}
	
	public ExtraStyle copy() {
		ExtraStyle copy = StyleRegistry.get(name);
		copy.deserialize(serialize());
		return copy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(this.getClass())) return false;
		return serialize().equals(((ExtraStyle) obj).serialize());
	}
}
