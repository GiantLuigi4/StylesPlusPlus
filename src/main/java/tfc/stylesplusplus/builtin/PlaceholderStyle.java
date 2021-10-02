package tfc.stylesplusplus.builtin;

import com.google.gson.JsonObject;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import tfc.stylesplusplus.api.ExtraStyle;

public class PlaceholderStyle extends ExtraStyle {
	public PlaceholderStyle() {
		super(new Identifier("stylesplusplus:placeholder"));
	}
	
	JsonObject held = new JsonObject();
	
	@Override
	public void apply(int index, int count, char c, Matrix4f matrix, String str, Style fullStyle) {
	}
	
	@Override
	public JsonObject serialize() {
		JsonObject object = super.serialize();
		object.add("held", held);
		return object;
	}
	
	@Override
	public void deserialize(JsonObject object) {
		super.deserialize(object);
		if (object.get("held") instanceof JsonObject) held = object.getAsJsonObject("held");
	}
}
