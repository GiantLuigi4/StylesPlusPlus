package tfc.stylesplusplus.util;

import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.stylesplusplus.api.ExtraStyle;
import tfc.stylesplusplus.api.ExtraStyleData;

import java.util.List;

public class StyleMixinHelper {
	public static void copyFormattingParent(Style current, List<ExtraStyle> styles, boolean hasReset, boolean skipParent, Style parent, CallbackInfoReturnable<Style> cir) {
		if (
				parent == Style.EMPTY &&
						(!styles.isEmpty() || !((ExtraStyleData)parent).getExtraStyles().isEmpty())
		) {
			cir.setReturnValue(current.withBold(parent.isBold()));
		} else if (parent == Style.EMPTY) return;
		if (((ExtraStyleData) cir.getReturnValue()).getExtraStyles() != styles) {
			if (((ExtraStyleData) cir.getReturnValue()).getExtraStyles() != ((ExtraStyleData) parent).getExtraStyles()) {
				for (ExtraStyle style : styles)
					((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
				for (ExtraStyle style : ((ExtraStyleData) parent).getExtraStyles())
					((ExtraStyleData) cir.getReturnValue()).addStyle(style.copy());
			}
		}
//		((ExtraStyleData) cir.getReturnValue()).setSkipParent(skipParent);
//		((ExtraStyleData) cir.getReturnValue()).setHasReset(hasReset);
	}
}
