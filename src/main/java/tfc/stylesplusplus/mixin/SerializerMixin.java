package tfc.stylesplusplus.mixin;

import com.google.gson.*;
import net.minecraft.server.command.TellRawCommand;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.stylesplusplus.api.ExtraStyle;
import tfc.stylesplusplus.api.ExtraStyleData;
import tfc.stylesplusplus.api.StyleRegistry;
import tfc.stylesplusplus.builtin.PlaceholderStyle;

import java.lang.reflect.Type;

@Mixin(Style.Serializer.class)
public class SerializerMixin {
	@Inject(at = @At("RETURN"), method = "serialize")
	public void postSerialze(Style style, Type type, JsonSerializationContext jsonSerializationContext, CallbackInfoReturnable<JsonElement> cir) {
		JsonElement element = cir.getReturnValue();
		JsonObject object = (JsonObject) element;
		JsonArray array = new JsonArray();
		for (ExtraStyle extraStyle : ((ExtraStyleData) style).getExtraStyles()) array.add(extraStyle.serialize());
		object.add("stylesplusplus:extra_styles", array);
	}
	
	// TODO: check that json elements are instances of what they should be instead of assuming
	@Inject(at = @At("RETURN"), method = "deserialize")
	public void postDeserialize(@NotNull JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, @NotNull CallbackInfoReturnable<Style> cir) {
		Style style = cir.getReturnValue();
		if (!jsonElement.getAsJsonObject().has("stylesplusplus:extra_styles")) return;
		JsonArray array = jsonElement.getAsJsonObject().getAsJsonArray("stylesplusplus:extra_styles");
		for (int i = 0; i < array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject();
			ExtraStyle style1 = StyleRegistry.get(new Identifier(object.getAsJsonPrimitive("registryName").getAsString()));
			if (style1 instanceof PlaceholderStyle) {
				JsonElement element = object.get("held");
				if (element instanceof JsonObject) {
					JsonObject held = (JsonObject) element;
					style1 = StyleRegistry.get(new Identifier(held.getAsJsonPrimitive("registryName").getAsString()));
				}
			}
			style1.deserialize(object);
			((ExtraStyleData)style).addStyle(style1);
		}
	}
}
