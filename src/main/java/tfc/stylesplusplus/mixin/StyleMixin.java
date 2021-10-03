package tfc.stylesplusplus.mixin;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.stylesplusplus.api.ExtraStyle;
import tfc.stylesplusplus.api.ExtraStyleData;
import tfc.stylesplusplus.api.StyleColorSettingHack;

import java.util.ArrayList;

@Mixin(Style.class)
public class StyleMixin implements ExtraStyleData, StyleColorSettingHack {
	@Shadow @Final @Nullable @Mutable private TextColor color;
	@Unique
	private final ArrayList<ExtraStyle> styles = new ArrayList<>();
	
	@Override
	public ArrayList<ExtraStyle> getExtraStyles() {
		return styles;
	}
	
	@Override
	public void addStyle(ExtraStyle style) {
		styles.add(style);
	}
	
	@Inject(at = @At("RETURN"), method = "withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;")
	public void copyFormatting(Formatting formatting, CallbackInfoReturnable<Style> cir) {
		if (formatting != Formatting.RESET)
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;")
	public void copyFormatting(Formatting[] formattings, CallbackInfoReturnable<Style> cir) {
		for (Formatting formatting : formattings) if (formatting == Formatting.RESET) return;
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withBold")
	public void copyFormattingBold(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withItalic")
	public void copyFormattingItalic(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withUnderline")
	public void copyFormattingUnderline(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "obfuscated")
	public void copyFormattingObfuscated(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withStrikethrough")
	public void copyFormattingStrikethrough(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withClickEvent")
	public void copyFormattingClickEvent(ClickEvent clickEvent, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withHoverEvent")
	public void copyFormattingHoverEvent(HoverEvent hoverEvent, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withInsertion")
	public void copyFormattingInsertion(String insertion, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withFont")
	public void copyFormattingFont(Identifier font, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withExclusiveFormatting")
	public void copyFormattingExclusive(Formatting formatting, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withColor(Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/Style;")
	public void copyFormattingColor(TextColor color, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData)cir.getReturnValue()).addStyle(style.copy());
	}
	
	@Inject(at = @At("RETURN"), method = "withParent")
	public void copyFormattingParent(Style parent, CallbackInfoReturnable<Style> cir) {
		if (((ExtraStyleData)cir.getReturnValue()).getExtraStyles() != styles) {
			if (((ExtraStyleData) cir.getReturnValue()).getExtraStyles() != ((ExtraStyleData)parent).getExtraStyles()) {
				for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
				for (ExtraStyle style : ((ExtraStyleData) parent).getExtraStyles())
					((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
			}
		}
	}
	
	@Override
	public void setColor(@Nullable TextColor color) {
		this.color = color;
	}
}
