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
	
	public abstract void apply(int index, int count, char c, Matrix4f stack, String str, Style fullStyle);
	
	public abstract int modifyWidth(int currentWidth, int index, int count, char c, String str, Style fullStyle);
	
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
}
