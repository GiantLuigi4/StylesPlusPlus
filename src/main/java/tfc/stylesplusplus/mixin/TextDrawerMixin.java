package tfc.stylesplusplus.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.stylesplusplus.StringAware;
import tfc.stylesplusplus.api.Color;
import tfc.stylesplusplus.api.ExtraStyle;
import tfc.stylesplusplus.api.ExtraStyleData;
import tfc.stylesplusplus.api.StyleColorSettingHack;

@Mixin(targets = "net.minecraft.client.font.TextRenderer$Drawer")
public class TextDrawerMixin implements StringAware {
	@Unique
	String text;
	
	@Override
	public String getString() {
		return text;
	}
	
	@Override
	public void setString(String str) {
		text = str;
	}
	
	@Shadow
	@Final
	private Matrix4f matrix;
	
	@Final
	@Shadow
	@Mutable
	private float red;
	@Final
	@Shadow
	@Mutable
	private float green;
	@Final
	@Shadow
	@Mutable
	private float blue;
	@Final
	@Shadow
	@Mutable
	private float alpha;
	@Shadow
	@Final
	private float brightnessMultiplier;
	@Unique
	private Matrix4f oldMatrix;
	
	@Unique
	Color srcColor;
	@Unique
	Color modifiedColor;
	
	@Unique
	TextColor srcTextColor;
	
	/**
	 * helps avoid color leaking, cuz that's an actual issue which happened
	 */
	@Inject(at = @At(value = "HEAD"), method = "accept")
	public void preAccept(int i, Style style, int j, CallbackInfoReturnable<Boolean> cir) {
		if (this.text == null) return;
		if (((ExtraStyleData)style).getExtraStyles().isEmpty()) return;
		srcTextColor = style.getColor();
		Color color = new Color((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
		srcColor = color;
		if (srcTextColor != null) {
			int k = srcTextColor.getRgb();
			int m = (int) ((k >> 16 & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15)));
			int n = (int)((k >> 8 & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15)));
			int o = (int)((k & (LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE | 15)));
			color = new Color((int) (m * 255), (int) (n * 255), (int) (o * 255), (int) (alpha * 255));
		}
		for (ExtraStyle extraStyle : ((ExtraStyleData) style).getExtraStyles())
			color = extraStyle.modifyColor(color, i, text.length(), (char) j, text, style, brightnessMultiplier);
		red = color.getRed() / 255f;
		green = color.getGreen() / 255f;
		blue = color.getBlue() / 255f;
		alpha = color.getAlpha() / 255f;
		modifiedColor = color;
		((StyleColorSettingHack) style).setColor(null);
	}
	
	/**
	 * game is weird
	 */
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Style;getColor()Lnet/minecraft/text/TextColor;"), method = "accept")
	public void postGetColor(int i, Style style, int j, CallbackInfoReturnable<Boolean> cir) {
		if (((ExtraStyleData)style).getExtraStyles().isEmpty()) return;
		red = modifiedColor.getRed() / 255f;
		green = modifiedColor.getGreen() / 255f;
		blue = modifiedColor.getBlue() / 255f;
		alpha = modifiedColor.getAlpha() / 255f;
	}
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawGlyph(Lnet/minecraft/client/font/GlyphRenderer;ZZFFFLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFI)V", shift = At.Shift.BY, by = -2), method = "accept")
	private void preVisit(int i, Style style, int j, CallbackInfoReturnable<Boolean> cir) {
		if (((ExtraStyleData)style).getExtraStyles().isEmpty()) return;
		oldMatrix = matrix.copy();
		if (this.text == null) return;
		for (ExtraStyle extraStyle : ((ExtraStyleData) style).getExtraStyles())
			extraStyle.apply(i, text.length(), (char) j, matrix, text, style);
	}
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawGlyph(Lnet/minecraft/client/font/GlyphRenderer;ZZFFFLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFI)V", shift = At.Shift.AFTER), method = "accept")
	private void postVisit(int i, Style style, int j, CallbackInfoReturnable<Boolean> cir) {
		if (((ExtraStyleData)style).getExtraStyles().isEmpty()) return;
		matrix.load(oldMatrix);
	}
	
	/**
	 * helps avoid color leaking, cuz that's an actual issue which happened
	 */
	@Inject(at = @At(value = "RETURN"), method = "accept")
	private void finish(int i, Style style, int j, CallbackInfoReturnable<Boolean> cir) {
		if (((ExtraStyleData)style).getExtraStyles().isEmpty()) return;
		red = srcColor.getRed() / 255f;
		green = srcColor.getGreen() / 255f;
		blue = srcColor.getBlue() / 255f;
		alpha = srcColor.getAlpha() / 255f;
		((StyleColorSettingHack) style).setColor(srcTextColor);
	}
}
