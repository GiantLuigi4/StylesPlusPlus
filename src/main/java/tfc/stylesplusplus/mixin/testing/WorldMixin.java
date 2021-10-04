package tfc.stylesplusplus.mixin.testing;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.stylesplusplus.api.ExtraStyleData;
import tfc.stylesplusplus.builtin.WavyStyle;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public abstract class WorldMixin {
	@Shadow @Final private List<AbstractClientPlayerEntity> players;
	
//	@Inject(at = @At("HEAD"), method = "onSpawn()V")
//	public void preAdd(CallbackInfo ci) {
//		Style style = Style.EMPTY;
//		((ExtraStyleData) style).addStyle(new TestStyle());
//		this.sendMessage(new LiteralText("test").setStyle(style), false);
//	}
	@Inject(at = @At("HEAD"), method = "tick")
	public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		Style style = Style.EMPTY.withBold(false);
		LiteralText text = new LiteralText("");
		WavyStyle wavyStyle = new WavyStyle(1, 1, 3, 1, 0, 0.5, 12, 12);
		wavyStyle.globalTime = true;
		((ExtraStyleData) style).addStyle(wavyStyle);
		for (AbstractClientPlayerEntity player : players) {
			Text text1 = new LiteralText("aaaaaaaaaaaaaaaaaaaa").setStyle(style);
			text.append(text1);
			text.append(new LiteralText("b"));
			player.sendMessage(text, true);
		}
	}
}
