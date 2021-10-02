package tfc.stylesplusplus.mixin;

import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.stylesplusplus.StringAware;

@Mixin(TextVisitFactory.class)
public class TextVisitFactoryMixin {
	@Inject(at = @At("HEAD"), method = "visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z")
	private static void preVisit(String text, int startIndex, Style startingStyle, Style resetStyle, CharacterVisitor visitor, CallbackInfoReturnable<Boolean> cir) {
		if (visitor instanceof StringAware) ((StringAware) visitor).setString(text);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "visitForwards(Ljava/lang/String;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z")
	private static void preVisitForwards(String text, Style style, CharacterVisitor visitor, CallbackInfoReturnable<Boolean> cir) {
		if (visitor instanceof StringAware) ((StringAware) visitor).setString(text);
	}
}
