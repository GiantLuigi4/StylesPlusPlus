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
public abstract class StyleMixin implements ExtraStyleData, StyleColorSettingHack {
	@Shadow
	@Final
	@Nullable
	@Mutable
	private TextColor color;
	
	@Shadow public abstract Style withBold(@Nullable Boolean bold);
	
	@Shadow public abstract boolean isBold();
	
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
	
	boolean hasReset = false;
	
	@Override
	public void setHasReset(boolean hasReset) {
		this.hasReset = hasReset;
	}
	
	@Inject(at = @At("RETURN"), method = "withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;")
	public void copyFormatting(Formatting formatting, CallbackInfoReturnable<Style> cir) {
		if (formatting != Formatting.RESET)
			for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		else {
			((ExtraStyleData)cir.getReturnValue()).clear();
			hasReset = true;
		}
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;")
	public void copyFormatting(Formatting[] formattings, CallbackInfoReturnable<Style> cir) {
		for (Formatting formatting : formattings) {
			if (formatting == Formatting.RESET) {
				((ExtraStyleData)cir.getReturnValue()).clear();
				hasReset = true;
				return;
			}
		}
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withBold")
	public void copyFormattingBold(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withItalic")
	public void copyFormattingItalic(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withUnderline")
	public void copyFormattingUnderline(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "obfuscated")
	public void copyFormattingObfuscated(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withStrikethrough")
	public void copyFormattingStrikethrough(Boolean bold, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withClickEvent")
	public void copyFormattingClickEvent(ClickEvent clickEvent, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withHoverEvent")
	public void copyFormattingHoverEvent(HoverEvent hoverEvent, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withInsertion")
	public void copyFormattingInsertion(String insertion, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withFont")
	public void copyFormattingFont(Identifier font, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withExclusiveFormatting")
	public void copyFormattingExclusive(Formatting formatting, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withColor(Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/Style;")
	public void copyFormattingColor(TextColor color, CallbackInfoReturnable<Style> cir) {
		for (ExtraStyle style : styles) ((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Inject(at = @At("RETURN"), method = "withParent", cancellable = true)
	public void copyFormattingParent(Style parent, CallbackInfoReturnable<Style> cir) {
		if (cir.getReturnValue() == (Object)this) cir.setReturnValue(this.withBold(this.isBold()));
		if (((ExtraStyleData)cir.getReturnValue()).skipParent() || hasReset) {
			((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//			((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
			return;
		}
		if (((ExtraStyleData) cir.getReturnValue()).getExtraStyles() != styles) {
			if (((ExtraStyleData) cir.getReturnValue()).getExtraStyles() != ((ExtraStyleData) parent).getExtraStyles()) {
				for (ExtraStyle style : styles)
					((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
				for (ExtraStyle style : ((ExtraStyleData) parent).getExtraStyles())
					((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
			}
		}
		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent());
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
	
	@Override
	public void setColor(@Nullable TextColor color) {
		this.color = color;
	}
	
	boolean shouldSkipParent = false;
	
	@Override
	public void setSkipParent(boolean value) {
		shouldSkipParent = value;
	}
	
	@Override
	public boolean skipParent() {
		return shouldSkipParent;
	}
	
	@Override
	public void clear() {
		styles.clear();
	}
}
